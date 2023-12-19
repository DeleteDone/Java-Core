package ru.geekbrains.lesson3;

public abstract class BaseHuman {

    protected String name;
    protected int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkName(name);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 18){
            throw new RuntimeException("Некорректный возраст человека.");
        }
        this.age = age;
    }



    protected BaseHuman(String name){
        this(name, 18);
    }

    protected BaseHuman(String name, int age){
        checkName(name);
        if (age < 18){
            throw new RuntimeException("Некорректный возраст человека.");
        }
        else{
            this.age = age;
        }

    }

    private void checkName(String name){
        if (name == null || name.length() < 3){
            throw new RuntimeException("Некорректное имя человека.");
        }
        else {
            this.name = name;
        }
    }

    public abstract void printDisplayInfo();




}
