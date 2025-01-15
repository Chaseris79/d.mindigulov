package ru.mindigulov;

import java.io.*;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        boolean argP = false;                                   //Задает префикс имен выходных файлов.
        boolean argO = false;                                   //Задает путь для результатов.
        boolean argA = false;                                   //Задает режим добавления в существующие файлы.
        boolean argS = false;                                   //Краткая статистика, содержит только количество элементов записанных в исходящие файлы.
        boolean argF = false;                                   //Полная статистика для чисе дополнительно содержит минимальное и максимальное значения,
                                                                        // сумма и среднее. Полная статистика для строк, помимо их количества, содержит также
                                                                        // размер самойкороткой строки и самой длинной.
        ArrayList fileList = new ArrayList();                           // список файлов, которые указаны при вводе парамертров запуска программы
        String prefix = null;                                   //Префикс имен выходного файла
        String pathName = "";                                   //Путь для модификатора "-o"
        ArrayList strings = new ArrayList();                    //Хранилище строк
        ArrayList floats = new ArrayList();                     //Хранилище дробных чисел
        ArrayList integers = new ArrayList();                   //Хранилище целых чисел

        String targetIntegers = "integers.txt";                 //Наименование файла с числами по умолчанию"
        String targetFloats = "floats.txt";                     //Наименование файла с дробными числами по умолчанию"
        String targetStrings = "strings.txt";                   //Наименование файла со строками по умолчанию"

        int amountOfStrings = 0;                                //Количество элементов строк
        int amountOfFloats = 0;                                 //Количество элементов дробных чисел
        int amountOfIntegers = 0;                               //Количество элементов чисел

        int maxInt = 0;                                         //Максимальное значение целого числа
        int minInt = 0;                                         //Минимальное значение целого числа
        int sumInt = 0;                                         //Сумма чисел
        float averageInt = 0;                                   //Среднее арефметическое чисел

        float maxFloat = 0;                                     //Максимальное значение дробного числа
        float minFloat = 0;                                     //Минимальное значение дробного числа
        float sumFloat = 0;                                     //Сумма дробных чисел
        float averageFloat = 0;                                 //Среднее арефметическое дробных чисел

        int maxLenght = 0;                                      //Максимальная длина строки
        int minLenght = 0;                                      //Минимальная длина строки


        int argsPos = 0;                                        //позиция указателя в цикле

      for (String s: args) {
         s = s.toLowerCase();
          if (s.equals("-p")) {
              argP = true;
              prefix = args[argsPos+1];
          } else if (s.equals("-o")) {
              argO = true;
              pathName = args[argsPos+1];
              char testChar = pathName.charAt(0);
              if (testChar == '/' || testChar == '\\') {
                    pathName = new String(pathName.substring(1, pathName.length()));
              }
              char testChar2 = pathName.charAt(pathName.length()-1);
              if (testChar2 != '/' || testChar2 != '\\') {
                  pathName = new String(pathName + "/");
              }

          } else if (s.equals("-a")) {
              argA = true;
          } else if (s.equals("-s")) {
              argS = true;
          } else if (s.equals("-f")) {
              argF = true;
          } else if (s.contains(".txt")) {
              fileList.add(s);
          }
         argsPos++;
      }                                //Считывание параметров запуска программы

        try {

            int filesCount = fileList.size();
            for (int i = 0; i < filesCount; i++) {
                BufferedReader reader = new BufferedReader(new FileReader((String) fileList.get(i)));
                while (reader.ready()) {
                    String line = reader.readLine();
                    try {
                        int readInt = Integer.parseInt(line);
                        sumInt = sumInt + readInt;
                        if (integers.isEmpty()) {
                            maxInt = readInt;
                            minInt = readInt;
                        } else {
                            if (readInt > maxInt) {
                                maxInt = readInt;
                            }
                            if (readInt < minInt) {
                                minInt = readInt;
                            }
                        }
                        integers.add(readInt);
                        averageInt = sumInt/integers.size();
                        amountOfIntegers++;

                    } catch (Exception e) {
                        try {
                            Float readFloat = (Float.parseFloat(line));
                            if (floats.isEmpty()) {
                                maxFloat = readFloat;
                                minFloat = readFloat;
                                sumFloat = readFloat;
                            } else {
                                if (readFloat > maxFloat) {
                                    maxFloat = readFloat;
                                }
                                if (readFloat < minFloat) {
                                    minFloat = readFloat;
                                }
                                sumFloat = sumFloat + readFloat;
                            }
                                floats.add(Float.parseFloat(line));
                            averageFloat = sumFloat/floats.size();
                            amountOfFloats++;

                        } catch (Exception ee) {
                                strings.add(line);
                                if ( line.length() >= maxLenght ) {
                                    maxLenght = line.length();
                                } else {
                                    minLenght = line.length();
                                }

                                amountOfStrings++;
                        }
                    }

                }
                reader.close();
            }
            if (argP) {
                targetStrings = new String(prefix + targetStrings);
                targetIntegers = new String(prefix + targetIntegers);
                targetFloats = new String(prefix + targetFloats);
            }

            if (!strings.isEmpty()) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(pathName+targetStrings, argA));
                    for (int i = 0; i < strings.size(); i++) {
                        writer.write((String) strings.get(i) + "\n");
                    }
                    writer.close();

               }
           catch (FileNotFoundException fileNotFoundException) {
               File filePath = new File(pathName);
               filePath.mkdirs();
               BufferedWriter writer = new BufferedWriter(new FileWriter(pathName+targetStrings, argA));
               for (int i = 0; i < strings.size(); i++) {
                   writer.write((String) strings.get(i) + "\n");
               }
               writer.close();
                }
            }
            if (!integers.isEmpty()) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(pathName+targetIntegers, argA));
                    for (int i = 0; i < integers.size(); i++) {
                        writer.write((Integer) integers.get(i) + "\n");
                    }
                    writer.close();

                }
                catch (FileNotFoundException fileNotFoundException) {
                    File filePath = new File(pathName);
                    filePath.mkdirs();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(pathName+targetIntegers, argA));
                    for (int i = 0; i < integers.size(); i++) {
                        writer.write((Integer) integers.get(i) + "\n");
                    }
                    writer.close();
                }
            }
            if (!floats.isEmpty()) {
                try {

                    BufferedWriter writer = new BufferedWriter(new FileWriter(pathName+targetFloats, argA));
                    for (int i = 0; i < floats.size(); i++) {
                        writer.write((Float) floats.get(i) + "\n");
                    }
                    writer.close();

                }
                catch (FileNotFoundException fileNotFoundException) {
                    File filePath = new File(pathName);
                    filePath.mkdirs();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(pathName+targetFloats, argA));
                    for (int i = 0; i < floats.size(); i++) {
                        writer.write((Float) floats.get(i) + "\n");
                    }
                    writer.close();
                }
            }



        } catch (IOException e) {                       //Чтение и запись файлов
            throw new RuntimeException(e);
        }

        amountOfStrings = strings.size();
        amountOfFloats = floats.size();
        amountOfIntegers = integers.size();

        if (argS) {
            System.out.println("Краткая статистика:"+"\n"+ "Количество элементов строк: " + amountOfStrings + "\n" +
                    "Количество элементов дробных чисел: " + amountOfFloats + "\n"+
                    "Количество элементов целых чисел: " + amountOfIntegers);
        }                                        //Вывод в консоль для краткой статистики
        if (argF) {
            System.out.println("Полная статистика:"+ "\n"+ "* * * * * * * * * * * * * *"+"\n" + "Минимальное целое число: " + minInt + "\n" + "Максимальное целое число: "
                    + maxInt + "\n" + "Среднее целое число: " + averageInt + "\n" + "Сумма целых чисел: " + sumInt + "\n" + "Количество элементов (Числа): " + amountOfIntegers+ "\n" + "* * * * * * * * * * * * * *" + "\n" +
                    "Минимальное дробное число: " + minFloat + "\n" + "Максимальное дробное число: " + maxFloat + "\n" + "Среднее дробное число: " + averageFloat + "\n" +
                    "Сумма дробных чисел: "  + sumFloat +  "\n"
                   + "Количество элементов (Дробные): " + amountOfFloats+ "\n" +  "* * * * * * * * * * * * * *" + "\n" + "Размер самой длиной строки: " + maxLenght + "\n" +
                    "Размер самой короткой строки: " + minLenght + "\n" + "Количество строк: " + amountOfStrings);
        }                                        //Вывод в консоль для полной статистики

    }

}