## Урок 1. Компиляция и интерпретация кода

Создать [проект из трёх классов](/src/main/java/ru/geekbrains/lesson1) (основной с точкой входа и два класса в другом пакете),
которые вместе должны составлять одну программу, позволяющую
производить четыре основных математических действия и осуществлять форматированный
вывод результатов пользователю (ИЛИ ЛЮБОЕ ДРУГОЕ ПРИЛОЖЕНИЕ НА ВАШ ВЫБОР, которое просто демонстрирует работу некоторого механизма).

- Необходимо установить Docker Desktop.
- Создать [Dockerfile](/src/main/dockerfile), позволяющий откопировать исходный код вашего приложения в образ для демонстрации работы вашего приложения при создании соответствующего контейнера.
```dockerfile
FROM bellsoft/liberica-openjdk-alpine:latest
COPY ./java ./src
RUN mkdir ./out
RUN javac -sourcepath ./src -d out src/ru/geekbrains/lesson1/sample/Main.java
CMD java -classpath ./out ru.geekbrains.lesson1.sample.Main
```

## Урок 2. Функции, манипулирующие данными
[link for lesson 2 programm](src/main/java/ru/geekbrains/lesson2/Program.java)
1. Полностью разобраться с кодом программы разработанной на семинаре, переписать программу. Это минимальная задача для сдачи домашней работы. [✓ done]()

Усложняем задачу:
2. ** Переработать метод проверки победы, логика проверки победы должна работать для поля 5х5 и
количества фишек 4. Очень желательно не делать это просто набором условий для каждой из
возможных ситуаций! Используйте вспомогательные методы, используйте циклы! 
 [✓ done]()

3. **** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока. [link for реализация AI programm](src/main/java/ru/geekbrains/lesson2/AI.java)  [✓ done]()

   ```java
    Подсказка преподавателя для реализации: я постараюсь расписать соответствующие алгоритмы о которых я говорил в конце семинара, применительно к разноразмерному полю (N на N клеток).

Начем с того, что мы должны написать универсальные методы проверки выигрыша, т е на вход, как минимум, они должны получать текущую координату фишки (с которой начинается поиск выигрышной комбинации)
и значение выигрышной комбинации (последовательность фишек).

Таким образом, как я и описывал в концее лекции, по нашему алгоритму, по каждому направлению строим проверки:

    //region Универсальная проверка победы игрока (задача 3*)

    /**
     * Проверка выигрыша игрока (человек или компьютер) горизонтали + вправо/вертикали + вниз
     * @param x начальная координата фишки
     * @param y начальная координата фишки
     * @param dir направление проверки (-1 => горизонтали + вправо/ 1 => вертикали + вниз)
     * @param win выигрышная комбинация
     * @return результат проверки
     */
    static boolean checkXY(int x, int y, int dir, int win) {
        char c = field[x][y]; // получим текущую фишку (игрок или компьютер)
        // Пройдем по всем ячейкам от начальной координаты (например 2,3) по горизонтали вправо и по вертикали вниз
        // (в зависимости от значения параметра dir)
        /*  +-1-2-3-4-5-
            1|.|.|.|.|.|
            2|.|.|.|.|.|
            3|.|X|X|X|X|
            4|.|X|.|.|.|
            5|.|X|.|.|.|
            ------------
        */
        for (int i = 1; i < win; i++)
            if (dir > 0 && (!isCellValid(x + i, y) || c != field[x + i][y])) return false;
            else if (dir < 0 && (!isCellValid(x, y + i) || c != field[x][y + i])) return false;
        return true;
    }

    /**
     * Проверка выигрыша игрока (человек или компьютер) по диагонали вверх + вправо/вниз + вправо
     * @param x начальная координата фишки
     * @param y начальная координата фишки
     * @param dir направление проверки (-1 => вверх + вправо/ 1 => вниз + вправо)
     * @param win кол-во фишек для победы
     * @return результат проверки
     */
    static boolean checkDiagonal(int x, int y, int dir, int win) {
        char c = field[x][y]; // получим текущую фишку (игрок или компьютер)
        // Пройдем по всем ячейкам от начальной координаты (например 3,3) по диагонали вверх и по диагонали вниз
        // (в зависимости от значения параметра dir)
        /*  +-1-2-3-4-5-
            1|.|.|.|.|X|
            2|.|.|.|X|.|
            3|.|.|X|.|.|
            4|.|.|.|X|.|
            5|.|.|.|.|X|
            ------------
        */
        for (int i = 1; i < win; i++)
            if (!isCellValid(x + i, y + i*dir) || c != field[x + i][y + i*dir]) return false;
        return true;
    }

    //endregion
Далее мы перерабатываем наш универсальный метод checkWin. Теперь он универсален для всех размерностей и последовательности выигрышей (начиная с 3),
тут мы как раз вызываем соответствующие методы (описанные выше), проверяем выигрышные комбинации, проходя по каждой клетке поля:

    /**
     * Проверка победы игрока
     * @param dot фишка игрока (человек или компьютер)
     * @param winCount кол-во фишек для победы
     * @return
     */
    static boolean checkWin(char dot, int winCount) {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == dot)
                    if (checkXY(y, x, 1, winCount) ||
                        checkXY(y, x, -1, winCount) ||
                        checkDiagonal(y, x, -1, winCount) ||
                        checkDiagonal(y, x, 1, winCount))
                        return true;
            }
        }
        return false;
    }
При этом методы gameChecks и main выглядят следующим образом, я добавил параметр значение выигрышной комбинации (последовательность фишек),
для того что бы сделать все методы в программе универсальными:

    /**
     * Метод проверки состояния игры
     * @param dot фишка игрока (человек/компьютер)
     * @param win выигрышная комбинация
     * @param s победное сообщение
     * @return результат проверки
     */
    private static boolean gameChecks(char dot, int win, String s) {
        if (checkWin(dot, win)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("draw!");
            return true;
        }
        return false;
    }

    /**
     * Главный метод, точка входа в программу
     * @param args
     */
    public static void main(String[] args) {
        while (true) {
            initField();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (gameChecks(DOT_HUMAN, WIN_COUNT, "Human win")) break;
                aiTurn();
                printField();
                if (gameChecks(DOT_AI, WIN_COUNT, "Computer win")) break;
            }
            System.out.println("Play again?");
            if (!SCANNER.next().equals("Y"))
                break;
        }
    }
Отлично, а теперь немного о AI компьютера, я доработаю соответствующий метод, теперь я буду проверять в первую очередь выигрышную последовательность для компьютера, в случае
если компьютер побеждает, ставлю фишку и завершаю работу. В случае, если нет, проверяю выигрышную комбинацию человека (WIN - 1). Ну а если, ни человек, ни компьютер не выигрывают - ставлю фишку, как и раньше в любое
доступное место, случайно:

//region Улучшенный ход компьютера (задача 4***)

    /**
     * Ход компьютера
     */
    private static void aiTurn() {

        // Побеждает ли компьютер в текущем ходе (при выигрышной комбинации WIN_COUNT)?
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == DOT_EMPTY){
                    field[y][x] = DOT_AI;
                    if (checkWin(DOT_AI, WIN_COUNT))
                        return;
                    else
                        field[y][x] = DOT_EMPTY;
                }
            }
        }

        // Побеждает ли игрок на текущий момент при выигрышной комбинации WIN_COUNT - 1?
        boolean f = checkWin(DOT_HUMAN, WIN_COUNT - 1);
        // Теперь, снова пройдем по всем свободным ячейкам игрового поля, если игрок уже побеждает при
        // выигрышной комбинации WIN_COUNT - 1, компьютер попытается закрыть последнюю выигрышную ячейку.
        // Если игрок НЕ побеждает при выигрышной комбинации WIN_COUNT - 1, компьютер будет действовать
        // на опережение, попытается заранее "подпортить" человеку выигрышную комбинацию.
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == DOT_EMPTY){
                    field[y][x] = DOT_HUMAN;
                    if (checkWin(DOT_HUMAN, WIN_COUNT - (f ? 0 : 1))) {
                        field[y][x] = DOT_AI;
                        return;
                    }
                    else
                        field[y][x] = DOT_EMPTY;
                }
            }
        }

        // Ни человек, ни компьютер не выигрывают, значит, компьютер ставит фишку случайным образом
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[y][x] = DOT_AI;
    }

    //endregion
Вот и все. Да, работа действительно сложная, попробуйте скопировать данные методы, ввести соответствующую константу:


private static final int WIN_COUNT = 4;

Поменять размерность поля, скажем 5 на 5 клеток и поработать с программой.

В конце продемонстрирую полный код программы:

import java.util.Random;
import java.util.Scanner;

public class Homework {
    private static final char DOT_HUMAN = 'X'; // Фишка игрока
    private static final char DOT_AI = 'O'; // Фишка компьютера
    private static final char DOT_EMPTY = '.'; // Признак пустой ячейки
    private static final int WIN_COUNT = 4; // Выигрышная комбинация
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static char[][] field; // Игровое поле
    private static int fieldSizeX; // Размерность игрового поля
    private static int fieldSizeY; // Размерность игрового поля

    /**
     * Инициализация игрового поля
     */
    private static void initField() {
        fieldSizeY = 5;
        fieldSizeX = 5;
        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++)
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        System.out.println();

        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++)
                System.out.print(field[i][j] + "|");
            System.out.println();
        }

        for (int i = 0; i <= fieldSizeX * 2 + 1; i++)
            System.out.print("-");
        System.out.println();
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     * @param x координата ячейки
     * @param y координата ячейки
     * @return результат проверки
     */
    private static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    /**
     * Проверка валидности ячейки
     * @param x координата ячейки
     * @param y координата ячейки
     * @return результат проверки
     */
    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Ход человека
     */
    private static void humanTurn() {
        int x;
        int y;
        do {
            System.out.print("Введите координаты хода X и Y (от 1 до 3) через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[y][x] = DOT_HUMAN;
    }

    /**
     * Проверка победы игрока
     * @param dot фишка игрока (человек или компьютер)
     * @param winCount кол-во фишек для победы
     * @return
     */
    static boolean checkWin(char dot, int winCount) {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == dot)
                    if (checkXY(y, x, 1, winCount) ||
                        checkXY(y, x, -1, winCount) ||
                        checkDiagonal(y, x, -1, winCount) ||
                        checkDiagonal(y, x, 1, winCount))
                        return true;
            }
        }
        return false;
    }

    /**
     * Проверка на ничью
     * @return результат проверки
     */
    private static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }


    //region Улучшенный ход компьютера (задача 4***)

    /**
     * Ход компьютера
     */
    private static void aiTurn() {

        // Побеждает ли компьютер в текущем ходе (при выигрышной комбинации WIN_COUNT)?
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == DOT_EMPTY){
                    field[y][x] = DOT_AI;
                    if (checkWin(DOT_AI, WIN_COUNT))
                        return;
                    else
                        field[y][x] = DOT_EMPTY;
                }
            }
        }

        // Побеждает ли игрок на текущий момент при выигрышной комбинации WIN_COUNT - 1?
        boolean f = checkWin(DOT_HUMAN, WIN_COUNT - 1);
        // Теперь, снова пройдем по всем свободным ячейкам игрового поля, если игрок уже побеждает при
        // выигрышной комбинации WIN_COUNT - 1, компьютер попытается закрыть последнюю выигрышную ячейку.
        // Если игрок НЕ побеждает при выигрышной комбинации WIN_COUNT - 1, компьютер будет действовать
        // на опережение, попытается заранее "подпортить" человеку выигрышную комбинацию.
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == DOT_EMPTY){
                    field[y][x] = DOT_HUMAN;
                    if (checkWin(DOT_HUMAN, WIN_COUNT - (f ? 0 : 1))) {
                        field[y][x] = DOT_AI;
                        return;
                    }
                    else
                        field[y][x] = DOT_EMPTY;
                }
            }
        }

        // Ни человек, ни компьютер не выигрывают, значит, компьютер ставит фишку случайным образом
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[y][x] = DOT_AI;
    }

    //endregion

    //region Универсальная проверка победы игрока (задача 3*)

    /**
     * Проверка выигрыша игрока (человек или компьютер) горизонтали + вправо/вертикали + вниз
     * @param x начальная координата фишки
     * @param y начальная координата фишки
     * @param dir направление проверки (-1 => горизонтали + вправо/ 1 => вертикали + вниз)
     * @param win выигрышная комбинация
     * @return результат проверки
     */
    static boolean checkXY(int x, int y, int dir, int win) {
        char c = field[x][y]; // получим текущую фишку (игрок или компьютер)
        // Пройдем по всем ячейкам от начальной координаты (например 2,3) по горизонтали вправо и по вертикали вниз
        // (в зависимости от значения параметра dir)
        /*  +-1-2-3-4-5-
            1|.|.|.|.|.|
            2|.|.|.|.|.|
            3|.|X|X|X|X|
            4|.|X|.|.|.|
            5|.|X|.|.|.|
            ------------
        */
        for (int i = 1; i < win; i++)
            if (dir > 0 && (!isCellValid(x + i, y) || c != field[x + i][y])) return false;
            else if (dir < 0 && (!isCellValid(x, y + i) || c != field[x][y + i])) return false;
        return true;
    }

    /**
     * Проверка выигрыша игрока (человек или компьютер) по диагонали вверх + вправо/вниз + вправо
     * @param x начальная координата фишки
     * @param y начальная координата фишки
     * @param dir направление проверки (-1 => вверх + вправо/ 1 => вниз + вправо)
     * @param win кол-во фишек для победы
     * @return результат проверки
     */
    static boolean checkDiagonal(int x, int y, int dir, int win) {
        char c = field[x][y]; // получим текущую фишку (игрок или компьютер)
        // Пройдем по всем ячейкам от начальной координаты (например 3,3) по диагонали вверх и по диагонали вниз
        // (в зависимости от значения параметра dir)
        /*  +-1-2-3-4-5-
            1|.|.|.|.|X|
            2|.|.|.|X|.|
            3|.|.|X|.|.|
            4|.|.|.|X|.|
            5|.|.|.|.|X|
            ------------
        */
        for (int i = 1; i < win; i++)
            if (!isCellValid(x + i, y + i*dir) || c != field[x + i][y + i*dir]) return false;
        return true;
    }

    //endregion

    /**
     * Метод проверки состояния игры
     * @param dot фишка игрока (человек/компьютер)
     * @param win выигрышная комбинация
     * @param s победное сообщение
     * @return результат проверки
     */
    private static boolean gameChecks(char dot, int win, String s) {
        if (checkWin(dot, win)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("draw!");
            return true;
        }
        return false;
    }

    /**
     * Главный метод, точка входа в программу
     * @param args
     */
    public static void main(String[] args) {
        while (true) {
            initField();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (gameChecks(DOT_HUMAN, WIN_COUNT, "Human win")) break;
                aiTurn();
                printField();
                if (gameChecks(DOT_AI, WIN_COUNT, "Computer win")) break;
            }
            System.out.println("Play again?");
            if (!SCANNER.next().equals("Y"))
                break;
        }
    }

}


## Урок 3. Классы и объекты
[link for directory](src/main/java/ru/geekbrains/lesson3/)

- Построить три класса ([базовый](src/main/java/ru/geekbrains/lesson3/Worker.java) и 2 потомка), описывающих некоторых [работников с почасовой оплатой](src/main/java/ru/geekbrains/lesson3/HourlyWorker.java) (один из потомков) и [фиксированной оплатой](src/main/java/ru/geekbrains/lesson3/FixedWorker.java) (второй потомок).
  - Описать в базовом классе абстрактный метод для расчёта среднемесячной заработной платы.
  ```java
   public abstract double calculateAverageSalary();
  ```
  Для «повременщиков» формула для расчета такова: «среднемесячная заработная плата = 20.8 * 8 * почасовая ставка», для работников с фиксированной оплатой «среднемесячная заработная плата = фиксированная месячная оплата».
  ```java
    @Override
    public double calculateAverageSalary() {
        return 20.8 * 8 * hourlyRate;
    }
  
    @Override
    public double calculateAverageSalary() {
        return fixedMonthlyPayment;
    }
  ```
  - Создать на базе абстрактного класса массив сотрудников и заполнить его.
  - ** Реализовать интерфейсы для возможности сортировки массива (обратите ваше внимание на интерфейсы Comparator, Comparable)
  - ** Создать класс, содержащий массив сотрудников, и реализовать возможность вывода данных с использованием foreach.
  ```java
  class WorkersArray {
      private Worker[] workers;
  
      public WorkersArray(Worker[] workers) {
          this.workers = workers;
      }
  
      // Метод для сортировки массива работников по имени
      public void sortByName() {
          Arrays.sort(workers, Comparator.comparing(Worker::getName));
      }
  
      // Метод для сортировки массива работников по заработной плате
      public void sortByAverageSalary() {
          Arrays.sort(workers, Comparator.comparing(Worker::calculateAverageSalary));
      }
  
      // Метод для вывода информации о работниках
      public void printWorkersInfo() {
          for (Worker worker : workers) {
              System.out.println("Name: " + worker.getName() + ", Average Salary: " + worker.calculateAverageSalary());
          }
      }
  }
  ```
  # Урок 4. Обработка исключений

[link for directory](src/main/java/ru/geekbrains/lesson4/)
1. Создать программу управления банковским счетом (Account).

Программа должна позволять пользователю вводить начальный баланс счета, сумму депозита и сумму снятия средств. При этом она должна обрабатывать следующие исключительные ситуации:

Попытка создать счет с отрицательным начальным балансом должна вызывать исключение IllegalArgumentException с соответствующим сообщением.
Попытка внести депозит с отрицательной суммой должна вызывать исключение IllegalArgumentException с соответствующим сообщением.
Попытка снять средства, сумма которых превышает текущий баланс, должна вызывать исключение InsufficientFundsException с сообщением о недостаточных средствах и текущим балансом.

Продемонстрируйте работу вашего приложения:
Программа должна обрабатывать все исключения с помощью конструкции try-catch, выводя соответствующие сообщения об ошибках.

2*.
Создать несколько типов счетов, унаследованных от Account, например: CreditAcciunt, DebitAccount.
Создать класс (Transaction), позволяющий проводить транзакции между счетами (переводить сумму с одного счета на другой)

Класс Transaction должен возбуждать исключение в случае неудачной попытки перевести деньги с одного счета на другой.

Продемонстрируйте работу вашего приложения:
Программа должна обрабатывать все исключения с помощью конструкции try-catch, выводя соответствующие сообщения об ошибках.

*Достаточно выпонить только первую задачу, вторая задача является дополнительной.
