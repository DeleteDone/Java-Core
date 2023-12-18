package model;

/**
 * Сourse: java core 

 * @Author Student Oksana Askerova

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import Exceptions.AccountBalanceChangeException;
import Exceptions.AccountIsBlockedException;

public class Account implements Comparable<Account> {

    private final String number;
    private long balance;
    private final long BALANCE_LIMIT = 0;
    private boolean isBlocked = false;

    public Account(String number) {
        this.number = number;
    }

    public Account(String number, long balance) {
        this.number = number;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
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
 
    public class MinusCardNumberException extends Exception {
        public MinusCardNumberException(long cardNumber) {
            super("Номер счёта не может быть отрицательным "" + cardNumber + """);
        }
    }


    public synchronized long deposit(long amount) throws AccountIsBlockedException, AccountBalanceChangeException {
        checkIsBlocked();

        if (amount <= 0)
            throw new AccountBalanceChangeException("Отрицательный или нулевой депозит не допускается.");
        return balance += amount;
    }

    public synchronized long withdraw(long amount) throws AccountIsBlockedException, AccountBalanceChangeException {
        checkIsBlocked();

        if (amount <= 0)
            throw new AccountBalanceChangeException("Отрицательный или нулевой вывод средств не допускается.");

        long afterWithdrawalBalance = balance - amount;

        if (afterWithdrawalBalance < BALANCE_LIMIT)
            throw new AccountBalanceChangeException("Account " + number + " закончились средства!");

        balance = afterWithdrawalBalance;

        return balance;
    }

    private void checkIsBlocked() throws AccountIsBlockedException {
        if (isBlocked())
            throw new AccountIsBlockedException("Account " + number + " заблокирован!");
    }

    @Override
    public int compareTo(Account o) {
        return this.getNumber().compareTo(o.getNumber());
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                ", balance=" + balance +
                ", BALANCE_LIMIT=" + BALANCE_LIMIT +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
        

