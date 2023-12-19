package ru.geekbrains.lesson3;

public class Championship {

    public static void main(String[] args) {
        Runner[] runners = {
                new Cat("Барсик", 1500, 300),
                new Cat("Персик", 1000, 210),
                new Robot("R2D2", 99999, 0),
                new Robot("C3PO", 5000, 50),
                Human.create("Петр", 23, 3000, 450),
                Human.create("Василий", 33, 2500, 300)
        };

        Obstacle[] obstacles = {
                new Track(1200),
                new Wall(30),
                new Track(2000),
                new Wall(140),
                new Track(3000),
                new Wall(300)
        };

        for (Runner runner: runners) {
            for (Obstacle obstacle: obstacles) {
                if (obstacle instanceof Track){
                    if (!runner.run(obstacle.getLength()))
                        break;
                }
                else if (obstacle instanceof Wall){
                    if (!runner.jump(obstacle.getHeight()))
                        break;
                }
            }

        }


    }

}
