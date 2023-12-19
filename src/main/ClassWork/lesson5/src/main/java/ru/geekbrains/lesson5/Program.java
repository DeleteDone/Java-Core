package ru.geekbrains.lesson5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SimpleTimeZone;

public class Program {

    /**
     * 1.  Создать 2 текстовых файла, примерно по 50-100 символов в каждом(особого значения не имеет);
     * 2.  Написать метод, «склеивающий» эти файлы, то есть вначале идет текст из первого файла, потом текст из второго.
     * 3.* Написать метод, который проверяет, присутствует ли указанное пользователем слово в файле (работаем только с латиницей).
     * 4.* Написать метод, проверяющий, есть ли указанное слово в папке
     */

    private static final Random random = new Random();

    private static final int CHAR_BOUND_L = 65;
    private static final int CHAR_BOUND_H = 90;

    private static final String TO_SEARCH = "GeekBrains";


    public static void main(String[] args) {
        System.out.println(generateSymbols(23));

        writeFileContents("sample01.txt", 30, TO_SEARCH);
        writeFileContents("sample02.txt", 50, TO_SEARCH);

        concatenate("sample01.txt", "sample02.txt", "sampleOut.txt");


        long i = 0;

        while ( (i = searchInFile("sampleOut.txt", i, TO_SEARCH)) > 0){
            System.out.printf("Файл содержит искомое слово, смещение: %d\n", i);
        }
        System.out.println("Завершение поиска");

        String[] fileNames = new String[10];
        for (int j = 0; j < fileNames.length; j++){
            fileNames[j] = "file_" + j + ".txt";
            writeFileContents(fileNames[j], 30, TO_SEARCH);
            System.out.printf("Файл %s создан.\n", fileNames[j]);
        }

        List<String> result = searchMatch(new File("."), TO_SEARCH);
        for (String s: result) {
            System.out.printf("Файл %s содержит искомое слово '%s'\n", s, TO_SEARCH);
        }

    }

    /**
     * Метод генерации некоторой последовательности символов
     *
     * @param length кол-во символов
     * @return последовательность символов
     */
    static String generateSymbols(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append((char) random.nextInt(CHAR_BOUND_L, CHAR_BOUND_H + 1));
        }
        return stringBuilder.toString();
    }

    static void writeFileContents(String fileName, int length) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.write(generateSymbols(length).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeFileContents(String fileName, int length, String searchWord) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.write(generateSymbols(length).getBytes());
            if (random.nextInt(3) == 0) {
                fileOutputStream.write(searchWord.getBytes());
                fileOutputStream.write(generateSymbols(random.nextInt(15)).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void concatenate(String fileIn1, String fileIn2, String fileOut) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileOut)) {
            int c;
            try (FileInputStream fileInputStream = new FileInputStream(fileIn1)) {
                while ((c = fileInputStream.read()) != -1) {
                    fileOutputStream.write(c);
                }
            }
            try (FileInputStream fileInputStream = new FileInputStream(fileIn2)) {
                while ((c = fileInputStream.read()) != -1) {
                    fileOutputStream.write(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static long searchInFile(String fileName, String searchWord){
        return searchInFile(fileName, 0, searchWord);
    }

    static long searchInFile(String fileName, long offset, String searchWord) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            fileInputStream.skipNBytes(offset);
            byte[] searchData = searchWord.getBytes();
            int c;
            int i = 0;
            while ((c = fileInputStream.read()) != -1) {
                if (c == searchData[i]) {
                    i++;
                } else {
                    i = 0;
                    if (c == searchData[i])
                        i++;
                }
                if(i == searchData.length)
                {
                    return offset;
                }
                offset++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    static List<String> searchMatch(File file, String searchWord){
        List<String> list = new ArrayList<String>();
        try
        {
            File[] files = file.listFiles();
            if (files == null)
                return list;
            for (int i = 0; i < files.length; i++){
                if (files[i].isFile()){
                     if (searchInFile(files[i].getCanonicalPath(), searchWord) > -1){
                         list.add(files[i].getCanonicalPath());
                     }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }


}
