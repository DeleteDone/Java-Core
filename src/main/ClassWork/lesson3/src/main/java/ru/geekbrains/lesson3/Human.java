package ru.geekbrains.lesson3;

public class Human extends BaseHuman implements Runner{

    private int maxRun;
    private int maxJump;

    @Override
    public int getMaxRun() {
        return maxRun;
    }

    @Override
    public int getMaxJump() {
        return maxJump;
    }

    public Human(String name, int age, int maxRun, int maxJump) {
        super(name, age);
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }

    public static Human create(String name, int age, int maxRun, int maxJump){
        if (name == null || name.length() < 3){
            throw new RuntimeException("Некорректное имя человека.");
        }
        if (age < 18){
            throw new RuntimeException("Некорректный возраст человека.");
        }
        return new Human(name, age, maxRun, maxJump);
    }

    @Override
    public void printDisplayInfo() {
        System.out.printf("%s - %d - %d - %d\n", name, age, maxRun, maxJump);
    }
}
