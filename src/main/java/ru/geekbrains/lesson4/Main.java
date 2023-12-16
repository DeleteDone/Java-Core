package ru.geekbrains.lesson4;


public class Main {
    public static void main(String[] args) {
        String[][] array = {{"1", "2", "3", "4"}, {"5", "6", "7", "8"}, {"9", "10", "11", "12"}, {"13", "14", "15", "16"}};
        String[][] symbol = {{"1", "2", "3", "4"}, {"5", "√", "7", "8"}, {"9", "10", "11", "12"}, {"13", "14", "15", "16"}};
        String[][] size = {{"1", "2", "3", "4", "5"}, {"5", "√", "7", "8"}, {"9", "10", "11", "12"}, {"13", "14", "15", "16"}};
        try {
            int result = processArray(array);
//            int result1 = processArray(symbol);
//            int result2 = processArray(size);
            System.out.println("Сумма элементов массива: " + result);
//            System.out.println("Сумма элементов массива: " + result1);
//            System.out.println("Сумма элементов массива: " + result2);
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        } catch (MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    public static int processArray(String[][] array) throws MyArraySizeException, MyArrayDataException {
        if (array.length != 4 || array[0].length != 4) {
            throw new MyArraySizeException("Неверный размер массива");
        }

        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("Неверные данные в ячейке", i, j);
                }
            }
        }

        return sum;
    }
}

class MyArraySizeException extends RuntimeException {
    private String message;

    public MyArraySizeException(String message){
        this.message = message;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println(message);
    }
}

class MyArrayDataException extends NumberFormatException {
    private int row;
    private int column;
    private String message;

    public MyArrayDataException(String message, int row, int column) {
        this.message = message;
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println(message + "[" + row + "," + column + "]");
    }
}