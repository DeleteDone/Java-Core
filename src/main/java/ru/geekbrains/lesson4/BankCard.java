package ru.geekbrains.lesson4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class BankCard {
    private String ownerName;
    private CurrencyType currency;
    private String date;
    private long cardNumber;
    private double balance;
 
    public BankCard() {
    }
 
    public BankCard(String ownerName, CurrencyType currency, String date, long cardNumber, int balance)
            throws MinusCardNumberException, ShortOwnerNameException {
        if (ownerName.length() < 5) throw new ShortOwnerNameException(ownerName);
        this.ownerName = ownerName;
        this.currency = currency;
        this.date = date;
        if (cardNumber < 0) throw new MinusCardNumberException(cardNumber);
        this.cardNumber = cardNumber;
        this.balance = balance;
    }
 
    public String getOwnerName() {
        return ownerName;
    }
 
    public void setOwnerName(String ownerName) throws ShortOwnerNameException {
        if (ownerName.length() < 5) throw new ShortOwnerNameException(ownerName);
        this.ownerName = ownerName;
    }
 
    public CurrencyType getCurrency() {
        return currency;
    }
 
    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }
 
    public String getDate() {
        return date;
    }
 
    public void setDate(String date) {
        this.date = date;
    }
 
    public long getCardNumber() {
        return cardNumber;
    }
 
    public void setCardNumber(long cardNumber) throws MinusCardNumberException {
        if (cardNumber < 0) throw new MinusCardNumberException(cardNumber);
        this.cardNumber = cardNumber;
    }
 
    public double getBalance() {
        return balance;
    }
 
    public void setBalance(double balance) {
        this.balance = balance;
    }
 
    public boolean getMoney(double sum) throws MinusBalanceException {
        if (balance < sum) throw new MinusBalanceException(balance, sum);
        balance -= sum;
        System.out.println("Баланс карты: " + balance);
        return true;
    }
 
    public void createBankCard() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Введите имя владельца карты");
            try {
                setOwnerName(reader.readLine());
                break;
            } catch (ShortOwnerNameException e) {
                System.out.println(e);
            }
        }
 
        int currencyType = 0;
        while (true) {
            System.out.println("Введите тип валюты: \n 1 - рубли \n 2 - доллары \n 3 - евро");
            try {
                currencyType = Integer.parseInt(reader.readLine());
                break;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
 
        switch (currencyType) {
            case 2:
                setCurrency(CurrencyType.DOLLAR);
                break;
            case 3:
                setCurrency(CurrencyType.EURO);
                break;
            default:
                setCurrency(CurrencyType.RUB);
        }
 
        System.out.println("Введите дату");
        setDate(reader.readLine());
        while (true) {
            try {
                System.out.println("Введите номер карты");
                setCardNumber(Long.parseLong(reader.readLine()));
                break;
            } catch (MinusCardNumberException e) {
                System.out.println(e);
            } catch (NumberFormatException eNumb) {
                eNumb.printStackTrace();
            }
        }
 
        while (true) {
            System.out.println("Введите баланс карты");
            try {
                setBalance(Double.parseDouble(reader.readLine()));
                break;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
 
        System.out.println("Банковская карта успешно создана \n" + this);
    }
 
    @Override
    public String toString() {
        return "BankCard{" +
                "ownerName='" + ownerName + '\'' +
                ", currency=" + currency +
                ", date='" + date + '\'' +
                ", cardNumber=" + cardNumber +
                ", balance=" + balance +
                '}';
    }
 
    public enum CurrencyType {
        RUB, EURO, DOLLAR
    }
 
    public class MinusCardNumberException extends Exception {
        public MinusCardNumberException(long cardNumber) {
            super("Номер счёта не может быть отрицательным "" + cardNumber + """);
        }
    }
 
    public class ShortOwnerNameException extends Exception {
        public ShortOwnerNameException(String ownerName) {
            super("Минимальная длина имени владельца - 5 символов "" + ownerName + """);
        }
    }
 
    public class MinusBalanceException extends Exception {
        public MinusBalanceException(double balance, double sum) {
            super("Недостаточно средств на счёте \n Баланс: " + balance + "\n Сумма: " + sum);
        }
    }
}
