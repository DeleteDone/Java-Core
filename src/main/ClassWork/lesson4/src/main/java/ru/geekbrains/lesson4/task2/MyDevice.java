package ru.geekbrains.lesson4.task2;

public class MyDevice implements AutoCloseable{

    boolean status = true;

    @Override
    public void close() {
        status = false;

    }
}
