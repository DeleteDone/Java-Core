package ru.geekbrains.lesson3;

/**
 * Сourse: java core 

 * @Author Student Oksana Askerova

 */

/** Класс работника с почасовой оплатой труда
 *
  */
class HourlyWorker extends Worker {
    private double hourlyRate;

    public HourlyWorker(String name, double hourlyRate) {
        super(name);
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateAverageSalary() {
        return 20.8 * 8 * hourlyRate;
    }
}

