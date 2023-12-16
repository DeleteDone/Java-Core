package ru.geekbrains.JavaCoreForAndroid;

import java.util.Random;
import java.util.Scanner;

/**
 * Сourse: java core 

 * @Author Student Oksana Askerova


 */
public class AI {
    private static char[][] field;                                  // поле
    private static final char DOT_HUMAN = 'X';                      // символ хода человека
    private static final char DOT_AI = 'O';                         // символ хода машины
    private static final char DOT_EMPTY = '.';                      // обозначение пустой ячейки
    private static final Scanner SCANNER = new Scanner(System.in);  // я так поняла константой мы фиксируем тип, так как
    private static final Random RANDOM = new Random();              // ссылочный тип не может зафиксировать значение.
    private static int fieldSizeX = 5;                                  // размер поля по оси X (горизонталь)
    private static int fieldSizeY = 5;                                  // размер поля по оси Y (Вертикаль)
    private static int lenWinSize = 4;                                  // условие победы n-элементов в ряд
    private static int ht_x = 0;                                     // координата последнего хода человека по x
    private static int ht_y = 0;                                     // координата последнего хода человека по y


    public static void main(String[] args) {
        while (true) {                                              // формируем бесконечный игровой цикл
            initField();
            printField();

            while (true) {
                humanTurn();
                printField();
                if (checkGame(DOT_HUMAN, "Вы победили!!!")) break;
                aiTurn();
                printField();
                if (checkGame(DOT_AI, "Я победил, Искусственный Интеллект поработит мир!!")) break;
            }
            System.out.println("Сыграем еще?");
            if (!SCANNER.next().equals("y")) {
                SCANNER.close();
                break;
            }
        }

    }

    // метод инициализации игрового поля
    private static void initField() {
        //fieldSizeX = 3;
        //fieldSizeY = 3;
        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    // метод вывода визуализации поля в консоль
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

    // метод хода человека
    private static void humanTurn() {
        int x;
        int y;

        do {
            System.out.print("Введите координаты хода Х и У от 1 до 3 через пробел ->");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));

        field[y][x] = DOT_HUMAN;
        ht_x = x; // запоминаем последний ход противника. Алгоритм " Я тебя запомнил..."
        ht_y = y; // запоминаем последний ход противника. Алгоритм " Я тебя запомнил..."
    }

    // метод проверки свободна ли ячейка
    private static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    // метод проверки попал ли пользователь вообще в поле
    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    // метод проверки игры
    private static boolean checkGame(char dot, String s) {

        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Ничья !!!");
            return true;
        }
        return false;
    }

    /* метод проверки на получение выигрышной комбинации
    / Теперь метод универсальный и применим для поля любого размера с выигрышной комбинацией
    / любой длины не длиннее чем само поле */
    private static boolean checkWin(char c) {
        //Проверка наличия победной комбинации по горизонтали
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length - (lenWinSize - 1); x++) {
                if (field[y][x] == c) {
                    int z = 1;
                    do {
                        if (field[y][x + z] != c) {
                            break;
                        } else {
                            z++;
                            if (z >= lenWinSize) {
                                System.out.println("Обнаружена победная комбинация по горизонтали");
                                return true;
                            }
                        }
                    } while (true);
                }
            }
        }

        //Проверка наличия победной комбинации по вертикали
        for (int y = 0; y < field.length - (lenWinSize - 1); y++) {
            for (int x = 0; x < field[y].length; x++) {
                if (field[y][x] == c) {
                    int z = 1;
                    do {
                        if (field[y + z][x] != c) {
                            break;
                        } else {
                            z++;
                            if (z >= lenWinSize) {
                                System.out.println("Обнаружена победная комбинация по вертикали");
                                return true;
                            }
                        }
                    } while (true);
                }
            }
        }

        //Проверка наличия победной комбинации вдоль главной диагонали (лево верх - право низ)
        for (int y = 0; y < field.length - (lenWinSize - 1); y++) {
            for (int x = 0; x < field[y].length - (lenWinSize - 1); x++) {
                if (field[y][x] == c) {
                    int z = 1;
                    do {
                        if (field[y + z][x + z] != c) {
                            break;
                        } else {
                            z++;
                            if (z >= lenWinSize) {
                                System.out.println("Обнаружена победная комбинация вдоль главной диагонали слева направо");
                                return true;
                            }
                        }
                    } while (true);
                }
            }
        }

        //проверка наличия победной комбинации вдоль второстепенной диагонали (право верх - лево низ)
        for (int y = 0; y < field.length - (lenWinSize - 1); y++) {
            for (int x = lenWinSize - 1; x < field[y].length; x++) {
                if (field[y][x] == c) {
                    int z = 1;
                    do {
                        if (field[y + z][x - z] != c) {
                            break;
                        } else {
                            z++;
                            if (z >= lenWinSize) {
                                System.out.println("Обнаружена победная комбинация вдоль второстепенной справа налево");
                                return true;
                            }
                        }
                    } while (true);
                }
            }
        }
        return false;
    }

    // Проверка на ситуация "ничья"
    private static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    // Ход машины
    // Реализованы 3 дополнительных логических алгоритма (2 защитных один нападение)
    private static void aiTurn() {
        int x;
        int y;
        int decisionIsMade = 0;
        //базовая последовательность
        //Проверка на наличие условия выигрыша в 1 ход. Алгоритм "Шах и мат"
        //проверка на наличие условия неизбежного проигрыша в 2 хода. Алгоритм "Палка о двух концах"
        //Преследование противника, ход в непосредственной близости от хода противника. Алгоритм "Прилипала"
        //Алгоритм "Генератор случайного хода" -  оставлен базовым.

        // (НАПАДЕНИЕ) Алгоритм нападения "Шах и мат"  - выигрыш в один ход
        if (decisionIsMade == 0) {
            //(НАПАДЕНИЕ) проверка наличия победной комбинации по горизонтали (алгоритм шах и мат)
            //ищет комбинацию из непрерывной последовательности символов ИИ по горизонтали в которой нехватает только последнего хода
            for (int py = 0; py < field.length && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length - (lenWinSize - 2) && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_AI) { //найден ключевой (стартовый элемент, запускается поиск "хвоста"
                        int z = 1;
                        do {
                            if (field[py][px + z] != DOT_AI) {
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 1) {
                                    System.out.println("Обнаружена ПРЕД-победная комбинация по горизонтали");
                                    System.out.println("Проверяю возможность выигрыша при постановке значения в точку х = " + (px + z) + " y = " + py);
                                    if (isCellValid(px + z, py) && isCellEmpty(px + z, py)) {
                                        System.out.println("Решение принял алгоритм \"Шах и мат\" условие \"спереди\" --- ставлю в точку х = " + (px + z) + " y = " + py);
                                        field[py][px + z] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else if (isCellValid(px - 1, py) && isCellEmpty(px - 1, py)) {
                                        System.out.println("Решение принял алгоритм \"Шах и мат\" условие \"сзади\" --- ставлю в точку х = " + (px - 1) + " y = " + py);
                                        field[py][px - 1] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else {
                                        System.out.println("Не получилось ложная тревога");
                                    }
                                    //System.out.println("Я ДОЛЖЕН СХОДИТЬ! координаты х = "+ px +"y = "+ py);
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }

            //(НАПАДЕНИЕ) проверка наличия победной комбинации по вертикали (алгоритм шах и мат)
            //ищет комбинацию из непрерывной последовательности символов ИИ по вертикали в которой нехватает только последнего хода
            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_AI) { // запускаем поиск хвоста вниз
                        int z = 1;
                        do {
                            if (field[py + z][px] != DOT_AI) { //если последовательно сть прервалась
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 1) {
                                    System.out.println("Обнаружена ПРЕД-победная комбинация по вертикали");
                                    System.out.println("Проверяю возможность выигрыша при постановке значения в точку х = " + px + " y = " + (py + z));
                                    if (isCellValid(px, py + z) && isCellEmpty(px, py + z)) {
                                        System.out.println("Решение принял алгоритм \"Шах и мат\" условие \"снизу\" --- ставлю в точку х = " + px + " y = " + (py + z));
                                        field[py + z][px] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else if (isCellValid(px, py - 1) && isCellEmpty(px, py - 1)) {
                                        System.out.println("Решение принял алгоритм \"Шах и мат\" условие \"сверху\" --- ставлю в точку х = " + px + " y = " + (py - 1));
                                        field[py - 1][px] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else {
                                        System.out.println("Не получилось ложная тревога");
                                    }
                                    //System.out.println("Я ДОЛЖЕН СХОДИТЬ! координаты х = "+ px +"y = "+ py);
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }

            //(НАПАДЕНИЕ) проверка наличия победной комбинации вдоль главной диагонали (лево верх - право низ) (алгоритм шах и мат)
            //ищет комбинацию из непрерывной последовательности символов ИИ вдоль главной диагонали в которой нехватает только последнего хода
            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length - (lenWinSize - 2) && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_AI) { // запускаем поиск хвоста вниз вправо
                        int z = 1;
                        do {
                            if (field[py + z][px + z] != DOT_AI) { //если последовательно сть прервалась
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 1) {
                                    System.out.println("Обнаружена ПРЕД-победная комбинация вдоль главной диагонали");
                                    System.out.println("Проверяю возможность выигрыша при постановке значения в точку х = " + (px + z) + " y = " + (py + z));
                                    if (isCellValid(px + z, py + z) && isCellEmpty(px + z, py + z)) {
                                        System.out.println("Решение принял алгоритм \"Шах и мат\" условие \"снизу\" --- ставлю в точку х = " + (px + z) + " y = " + (py + z));
                                        field[py + z][px + z] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else if (isCellValid(px - 1, py - 1) && isCellEmpty(px - 1, py - 1)) {
                                        System.out.println("Решение принял алгоритм \"Шах и мат\" условие \"сверху\" --- ставлю в точку х = " + (px - 1) + " y = " + (py - 1));
                                        field[py - 1][px - 1] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else {
                                        System.out.println("Не получилось ложная тревога");
                                    }
                                    //System.out.println("Я ДОЛЖЕН СХОДИТЬ! координаты х = "+ px +"y = "+ py);
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }

            //(НАПАДЕНИЕ) проверка наличия победной комбинации вдоль второстепенной диагонали (право верх - лево низ) (алгоритм шах и мат)
            //ищет комбинацию из непрерывной последовательности символов ИИ вдоль второстепенной диагонали в которой нехватает только последнего хода
            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = lenWinSize - 2; px < field[py].length && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_AI) { // запускаем поиск хвоста вниз влево
                        int z = 1;
                        do {
                            if (field[py + z][px - z] != DOT_AI) { //если последовательность прервалась
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 1) {
                                    System.out.println("Обнаружена ПРЕД-победная комбинация вдоль главной диагонали");
                                    System.out.println("Проверяю возможность выигрыша при постановке значения в точку х = " + (px - z) + " y = " + (py + z));
                                    if (isCellValid(px - z, py + z) && isCellEmpty(px - z, py + z)) {
                                        System.out.println("Решение принял алгоритм \"Шах и мат\" условие \"снизу\" --- ставлю в точку х = " + (px - z) + " y = " + (py + z));
                                        field[py + z][px - z] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else if (isCellValid(px + 1, py - 1) && isCellEmpty(px + 1, py - 1)) {
                                        System.out.println("Решение принял алгоритм \"Шах и мат\" условие \"сверху\" --- ставлю в точку х = " + (px + 1) + " y = " + (py - 1));
                                        field[py - 1][px + 1] = DOT_AI;
                                        decisionIsMade = 1;
                                        break;
                                    } else {
                                        System.out.println("Не получилось ложная тревога");
                                    }
                                    //System.out.println("Я ДОЛЖЕН СХОДИТЬ! координаты х = "+ px +"y = "+ py);
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }
        }

        // (ЗАЩИТА) Алгоритм защиты "Палка о двух концах..."
        // (ЗАЩИТА) Ищет опасную проигрышную комбинацию при которой неизбежен проигрыш через 2 хода
        if (decisionIsMade == 0) {
            //Первая комбинация --- горизонт --- "Палка о двух концах..."
            //(ЗАЩИТА)проверка наличия опасной комбинации по горизонтали (проверено работает)
            for (int py = 0; py < field.length && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length - (lenWinSize - 2) && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_HUMAN) {
                        int z = 1;
                        do {
                            if (field[py][px + z] != DOT_HUMAN) {
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 2) {
                                    if (isCellValid(px - 1, py) && isCellEmpty(px - 1, py) && isCellValid(px + z, py) && isCellEmpty(px + z, py)) {
                                        System.out.println("Обнаружена ПРЕД-проигрышная комбинация по горизонтали !!!");
                                        System.out.println("Предлагаю поставить нолик в позицию х=" + (px - 1) + " y=" + py + " или х=" + (px + z) + " y=" + py);
                                        // выбор конкретно куда поставить в начало или в конец пока оставим за дополнительным алгоритмом подсчета соседей
                                        // алгоритм тупой пока.
                                        if (getBalansOfForses(px - 1, py) > getBalansOfForses(px + z, py)) {
                                            field[py][px - 1] = DOT_AI;
                                            System.out.println("Решение принял алгоритм \"Палка о двух концах...\" условие \"горизонт слева\" --- ставлю в точку х = " + (px - 1) + " y = " + py);
                                        } else {
                                            field[py][px + z] = DOT_AI;
                                            System.out.println("Решение принял алгоритм \"Палка о двух концах...\" условие \"горизонт справа\" --- ставлю в точку х = " + (px + z) + " y = " + py);
                                        }
                                        decisionIsMade = 1;
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }
            //Вторая комбинация --- вертикаль --- "Палка о двух концах..." (проверено работает)
            //(ЗАЩИТА) проверка наличия опасной комбинации по вертикали
            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_HUMAN) {
                        int z = 1;
                        do {
                            if (field[py + z][px] != DOT_HUMAN) {
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 2) {
                                    if (isCellValid(px, py - 1) && isCellEmpty(px, py - 1) && isCellValid(px, py + z) && isCellEmpty(px, py + z)) {
                                        System.out.println("Обнаружена ПРЕД-проигрышная комбинация по вертикали !!!");
                                        System.out.println("Предлагаю поставить нолик в позицию х=" + px + " y=" + (py - 1) + " или х=" + px + " y=" + (py + z));
                                        // выбор конкретно куда поставить в начало или в конец пока оставим за дополнительным алгоритмом подсчета соседей
                                        // алгоритм тупой пока.
                                        if (getBalansOfForses(px, py - 1) > getBalansOfForses(px, py + z)) {
                                            field[py - 1][px] = DOT_AI;
                                            System.out.println("Решение принял алгоритм \"Палка о двух концах...\" условие \"вертикаль сверху\" --- ставлю в точку х = " + px + " y = " + (py - 1));
                                        } else {
                                            field[py + z][px] = DOT_AI;
                                            System.out.println("Решение принял алгоритм \"Палка о двух концах...\" условие \"вертикаль снизу\" --- ставлю в точку х = " + px + " y = " + (py + z));
                                        }
                                        decisionIsMade = 1;
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }

            //Третья комбинация --- главная диагональ --- "Палка о двух концах..." (проверено работает)
            //(ЗАЩИТА) проверка наличия опасной комбинации вдоль главной диагонали
            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = 0; px < field[py].length - (lenWinSize - 2) && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_HUMAN) {
                        int z = 1;
                        do {
                            if (field[py + z][px + z] != DOT_HUMAN) {
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 2) {
                                    if (isCellValid(px - 1, py - 1) && isCellEmpty(px - 1, py - 1) && isCellValid(px + z, py + z) && isCellEmpty(px + z, py + z)) {
                                        System.out.println("Обнаружена ПРЕД-проигрышная комбинация вдоль главной диагонали !!!");
                                        System.out.println("Предлагаю поставить нолик в позицию х=" + (px - 1) + " y=" + (py - 1) + " или х=" + (px + z) + " y=" + (py + z));
                                        // выбор конкретно куда поставить в начало или в конец пока оставим за дополнительным алгоритмом подсчета соседей
                                        // алгоритм тупой пока.
                                        if (getBalansOfForses(px - 1, py - 1) > getBalansOfForses(px + z, py + z)) {
                                            field[py - 1][px - 1] = DOT_AI;
                                            System.out.println("Решение принял алгоритм \"Палка о двух концах...\" условие \"главная диагональ сверху\" --- ставлю в точку х = " + (px - 1) + " y = " + (py - 1));
                                        } else {
                                            field[py + z][px + z] = DOT_AI;
                                            System.out.println("Решение принял алгоритм \"Палка о двух концах...\" условие \"главная диагональ снизу\" --- ставлю в точку х = " + (px + z) + " y = " + (py + z));
                                        }
                                        decisionIsMade = 1;
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }
            //Четверная комбинация --- второстепенная диагональ --- "Палка о двух концах..." (проверено работает)
            //!!! (ЗАЩИТА) проверка наличия опасной комбинации вдоль второстепенной диагонали
            for (int py = 0; py < field.length - (lenWinSize - 2) && decisionIsMade == 0; py++) {
                for (int px = lenWinSize - 2; px < field[py].length && decisionIsMade == 0; px++) {
                    if (field[py][px] == DOT_HUMAN) {
                        int z = 1;
                        do {
                            if (field[py + z][px - z] != DOT_HUMAN) {
                                break;
                            } else {
                                z++;
                                if (z >= lenWinSize - 2) {
                                    if (isCellValid(px + 1, py - 1) && isCellEmpty(px + 1, py - 1) && isCellValid(px - z, py + z) && isCellEmpty(px - z, py + z)) {
                                        System.out.println("Обнаружена ПРЕД-проигрышная комбинация вдоль второстепенной диагонали !!!");
                                        System.out.println("Предлагаю поставить нолик в позицию х=" + (px + 1) + " y=" + (py - 1) + " или х=" + (px - z) + " y=" + (py + z));
                                        // выбор конкретно куда поставить в начало или в конец пока оставим за дополнительным алгоритмом подсчета соседей
                                        // алгоритм тупой пока.
                                        if (getBalansOfForses(px + 1, py - 1) > getBalansOfForses(px - z, py + z)) {
                                            field[py - 1][px + 1] = DOT_AI;
                                            System.out.println("Решение принял алгоритм \"Палка о двух концах...\" условие \"второстепенная диагональ сверху\" --- ставлю в точку х = " + (px - 1) + " y = " + (py - 1));
                                        } else {
                                            field[py + z][px - z] = DOT_AI;
                                            System.out.println("Решение принял алгоритм \"Палка о двух концах...\" условие \"второстепенная диагональ снизу\" --- ставлю в точку х = " + (px - z) + " y = " + (py + z));
                                        }
                                        decisionIsMade = 1;
                                    }
                                    break;
                                }
                            }
                        } while (decisionIsMade == 0);
                    }
                }
            }
        }

        // Алгоритм "Прилипала" / пока простенький, в следующей редакции к каждому варианту нужно прикрутить проверка на баланс.
        if (decisionIsMade == 0) {
            //Работа с элементами находящимися возле края или с занятыми клетками доработан чутка
            if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y - 1) && field[ht_y - 1][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x + 1, ht_y + 1) && isCellEmpty(ht_x + 1, ht_y + 1)) {
                decisionIsMade = 1;
                field[ht_y + 1][ht_x + 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y - 1) && field[ht_y - 1][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x - 2, ht_y - 2) && isCellEmpty(ht_x - 2, ht_y - 2)) {
                decisionIsMade = 1;
                field[ht_y - 2][ht_x - 2] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x, ht_y - 1) && field[ht_y - 1][ht_x] == DOT_HUMAN && isCellValid(ht_x, ht_y + 1) && isCellEmpty(ht_x, ht_y + 1)) {
                decisionIsMade = 1;
                field[ht_y + 1][ht_x] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x, ht_y - 1) && field[ht_y - 1][ht_x] == DOT_HUMAN && isCellValid(ht_x, ht_y - 2) && isCellEmpty(ht_x, ht_y - 2)) {
                decisionIsMade = 1;
                field[ht_y - 2][ht_x] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y - 1) && field[ht_y - 1][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x - 1, ht_y + 1) && isCellEmpty(ht_x - 1, ht_y + 1)) {
                decisionIsMade = 1;
                field[ht_y + 1][ht_x - 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y - 1) && field[ht_y - 1][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x + 2, ht_y - 2) && isCellEmpty(ht_x + 2, ht_y - 2)) {
                decisionIsMade = 1;
                field[ht_y - 2][ht_x + 2] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y) && field[ht_y][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x - 1, ht_y) && isCellEmpty(ht_x - 1, ht_y)) {
                decisionIsMade = 1;
                field[ht_y][ht_x - 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y) && field[ht_y][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x + 2, ht_y) && isCellEmpty(ht_x + 2, ht_y)) {
                decisionIsMade = 1;
                field[ht_y][ht_x + 2] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y + 1) && field[ht_y + 1][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x - 1, ht_y - 1) && isCellEmpty(ht_x - 1, ht_y - 1)) {
                decisionIsMade = 1;
                field[ht_y - 1][ht_x - 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x + 1, ht_y + 1) && field[ht_y + 1][ht_x + 1] == DOT_HUMAN && isCellValid(ht_x + 2, ht_y + 2) && isCellEmpty(ht_x + 2, ht_y + 2)) {
                decisionIsMade = 1;
                field[ht_y + 2][ht_x + 2] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x, ht_y + 1) && field[ht_y + 1][ht_x] == DOT_HUMAN && isCellValid(ht_x, ht_y - 1) && isCellEmpty(ht_x, ht_y - 1)) {
                decisionIsMade = 1;
                field[ht_y - 1][ht_x] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x, ht_y + 1) && field[ht_y + 1][ht_x] == DOT_HUMAN && isCellValid(ht_x, ht_y + 2) && isCellEmpty(ht_x, ht_y + 2)) {
                decisionIsMade = 1;
                field[ht_y + 2][ht_x] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y + 1) && field[ht_y + 1][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x + 1, ht_y - 1) && isCellEmpty(ht_x + 1, ht_y - 1)) {
                decisionIsMade = 1;
                field[ht_y - 1][ht_x + 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y + 1) && field[ht_y + 1][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x - 2, ht_y + 2) && isCellEmpty(ht_x - 2, ht_y + 2)) {
                decisionIsMade = 1;
                field[ht_y + 2][ht_x - 2] = DOT_AI;
            }
            if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y) && field[ht_y][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x + 1, ht_y) && isCellEmpty(ht_x + 1, ht_y)) {
                decisionIsMade = 1;
                field[ht_y][ht_x + 1] = DOT_AI;
            } else if (decisionIsMade == 0 && isCellValid(ht_x - 1, ht_y) && field[ht_y][ht_x - 1] == DOT_HUMAN && isCellValid(ht_x - 2, ht_y) && isCellEmpty(ht_x - 2, ht_y)) {
                decisionIsMade = 1;
                field[ht_y][ht_x - 2] = DOT_AI;
            }
            if (decisionIsMade != 0) {
                System.out.println("Решение приял алгоритм \"Прилипала\".");
            }
        }

        // В случае когда решение на более высоком уровне не принято назначение хода поручается генератору.
        // (НАПАДЕНИЕ) Алгоритм "Генератор случайного хода".
        if (decisionIsMade == 0) {
            do {
                x = RANDOM.nextInt(fieldSizeX);
                y = RANDOM.nextInt(fieldSizeY);
            } while (!isCellEmpty(x, y));
            System.out.println("Решение приял генератор случайного хода, x = " + x + ", y = " + y);
            field[y][x] = DOT_AI; // Записываем ход в массив
        }
    }

    // (АНАЛИТИКА) метод сопоставления баланса сил вокруг поля, где потенциально есть возможность сделать ход.
    // Знаю что код китайский, это предварительная разработка, оптимизация  - следующий этап
    private static int getBalansOfForses(int bx, int by) {
        int balans = 0;
        //
        if (isCellValid(bx - 1, by - 1) && field[by - 1][bx - 1] == DOT_AI) balans += 3;
        if (isCellValid(bx - 1, by - 1) && field[by - 1][bx - 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx - 1, by - 1) && field[by - 1][bx - 1] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx, by - 1) && field[by - 1][bx] == DOT_AI) balans += 3;
        if (isCellValid(bx, by - 1) && field[by - 1][bx] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx, by - 1) && field[by - 1][bx] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx + 1, by - 1) && field[by - 1][bx + 1] == DOT_AI) balans += 3;
        if (isCellValid(bx + 1, by - 1) && field[by - 1][bx + 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx + 1, by - 1) && field[by - 1][bx + 1] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx + 1, by) && field[by][bx + 1] == DOT_AI) balans += 3;
        if (isCellValid(bx + 1, by) && field[by][bx + 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx + 1, by) && field[by][bx + 1] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx - 1, by + 1) && field[by + 1][bx - 1] == DOT_AI) balans += 3;
        if (isCellValid(bx - 1, by + 1) && field[by + 1][bx - 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx - 1, by + 1) && field[by + 1][bx - 1] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx, by + 1) && field[by + 1][bx] == DOT_AI) balans += 3;
        if (isCellValid(bx, by + 1) && field[by + 1][bx] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx, by + 1) && field[by + 1][bx] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx + 1, by + 1) && field[by + 1][bx + 1] == DOT_AI) balans += 3;
        if (isCellValid(bx + 1, by + 1) && field[by + 1][bx + 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx + 1, by + 1) && field[by + 1][bx + 1] == DOT_EMPTY) balans += 1;
        //
        if (isCellValid(bx - 1, by) && field[by][bx - 1] == DOT_AI) balans += 3;
        if (isCellValid(bx - 1, by) && field[by][bx - 1] == DOT_HUMAN) balans += 2;
        if (isCellValid(bx - 1, by) && field[by][bx - 1] == DOT_EMPTY) balans += 1;
        return balans;
    }
}
