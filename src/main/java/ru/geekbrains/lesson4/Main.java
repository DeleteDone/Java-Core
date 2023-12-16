package ru.geekbrains.lesson4;

/**
 * Сourse: java core 

 * @Author Student Oksana Askerova

 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class Main {
    public static void main(String[] args) throws IOException {
        BankCard myCard = new BankCard();
        myCard.createBankCard();
 
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Сколько вы хотите снять с карты?");
            try {
                myCard.getMoney(Double.parseDouble(reader.readLine()));
                break;
            } catch (BankCard.MinusBalanceException | NumberFormatException e) {
                System.out.println(e);
            }
        }
 
        System.out.println(myCard);
    }
}
