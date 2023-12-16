package ru.geekbrains.lesson3;

public class Main {
    public static void main(String[] args) {
        Worker[] workers = new Worker[4];
        workers[0] = new HourlyWorker("W1", 100);
        workers[1] = new HourlyWorker("W2", 120);
        workers[2] = new FixedWorker("W3", 5000);
        workers[3] = new FixedWorker("W4", 7000);

        // Создание объекта класса WorkersArray
        WorkersArray workersArray = new WorkersArray(workers);

        // Сортировка массива работников по имени
        workersArray.sortByName();

        // Вывод информации о работниках
        System.out.println("Sorted by name:");
        workersArray.printWorkersInfo();

        // Сортировка массива работников по заработной плате
        workersArray.sortByAverageSalary();

        // Вывод информации о работниках
        System.out.println("Sorted by average salary:");
        workersArray.printWorkersInfo();
    }
}
