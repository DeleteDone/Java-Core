package ru.geekbrains.lesson3;

public interface Runner {

    String getName();

    int getMaxRun();

    int getMaxJump();


    default boolean jump(int height) {
        if (height <= getMaxJump()){
            System.out.printf("%s перепрыгнул через стену %d см.\n", getName(), height);
            return true;
        }
        else {
            System.out.printf("%s не смог перепрыгнуть стену высотой %d см. Остался на месте.\n", getName(), height);
            return false;
        }
    }


    default boolean run(int length) {
        if (length <= getMaxRun()){
            System.out.printf("%s успешно пробежал %d метров.\n", getName(), getMaxRun());
            return true;
        }
        else {
            System.out.printf("%s не смог пробежать %d метров, остался на месте.\n", getName(), getMaxRun());
            return false;
        }
    }
}
