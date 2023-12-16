package ru.geekbrains.lesson3;

/**
 * Сourse: java core 

 * @Author Student Oksana Askerova

 */

/** Класс работника с фиксированной оплатой труда
 *
  */
class FixedWorker extends Worker {
    private double fixedMonthlyPayment;

    public FixedWorker(String name, double fixedMonthlyPayment) {
        super(name);
        this.fixedMonthlyPayment = fixedMonthlyPayment;
    }

    @Override
    public double calculateAverageSalary() {
        return fixedMonthlyPayment;
    }
}
