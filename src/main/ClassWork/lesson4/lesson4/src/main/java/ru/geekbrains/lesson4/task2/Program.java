package ru.geekbrains.lesson4.task2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 Задача 2
 ========
 Запишите в файл следующие строки:

 Анна=4
 Елена=5
 Марина=6
 Владимир=8
 Константин=10
 Иван=4

*/
public class Program {

    static Random random = new Random();

    public static void main(String[] args) {
        prepareFile();
    }

    static void prepareFile(){

        try (FileWriter fileWriter = new FileWriter("names.txt")){
            fileWriter.write("Анна=4\n");
            fileWriter.write("Елена=5\n");
            fileWriter.write("Марина=6\n");
            if (random.nextInt(2) == 0){
                throw new Exception("Internal error.");
            }
            fileWriter.write("Владимир=8\n");
            fileWriter.write("Константин=10\n");
            fileWriter.write("Иван=4\n");

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
