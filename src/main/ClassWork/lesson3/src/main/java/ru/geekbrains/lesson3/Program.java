package ru.geekbrains.lesson3;

import java.util.ArrayList;

public class Program {

    public static void main(String[] args) {

        BaseHuman human1 = Human.create("Name #1", 28, 3000, 20);
        human1.setAge(22);
        human1.printDisplayInfo();

        Human human2 = Human.create("Name #1", 28, 3000, 20);

        Object human3 = Human.create("Name #1", 28, 3000, 20);

    }

}
