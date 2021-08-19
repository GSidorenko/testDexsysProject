package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static Point[] arrPoint;
    static String[] groupNumbers = {"1","2","3"};
    static ArrayList<Point> listx1y = new ArrayList<>();
    static ArrayList<Point> listx2y = new ArrayList<>();
    static ArrayList<Point> listx3y = new ArrayList<>();
    static ArrayList<Point> listfreepoints = new ArrayList<>();

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        while (true) {

            System.out.println("Введите команду:");
            var commandString = in.nextLine();
            if (commandString.isEmpty()){
                System.out.println("Введена неверная команда");
            }
            String[] arrayСommandInputParameters = commandString.split(" ");
            String command = arrayСommandInputParameters[0];
            String[] numericalValues = Arrays.copyOfRange(arrayСommandInputParameters, 1, arrayСommandInputParameters.length);

            switch (command) {
                case "add":
                    if(checkArrayNumericalValues(numericalValues)){
                    if (((numericalValues.length) % 2 == 0)) {
                        int sizeArr = numericalValues.length/2;
                        arrPoint = new Point[sizeArr];

                        for (int i = 0; i < sizeArr; i++){
                            Point dot = new Point(numericalValues[2*i], numericalValues[2*i+1]);
                            arrPoint[i] = dot;
                            if (dot.x <= dot.y)
                            {listx1y.add(dot);}
                            if ((Math.pow(dot.x,2)) <= dot.y)
                            {listx2y.add(dot);}
                            if ((Math.pow(dot.x,3)) <= dot.y)
                            {listx3y.add(dot);}
                            if (!(listx1y.contains(dot) || listx2y.contains(dot) || listx3y.contains(dot)))
                            {listfreepoints.add(dot);}
                        }
                    } else System.out.println("Введено нечетное число координат.\n Нет возможности разбить на точки");
                    } else System.out.println("Введены некоректные данные, нет возможности преобразовать в int");
                    break;
                case "print":
                    if (arrayСommandInputParameters.length == 1) {
                        outputGroup(groupNumbers);
                        System.out.println("Количество точек, не вошедших ни в одну группу: " + listfreepoints.size());
                    }
                    else if (checkArrayNumericalValues(numericalValues)){
                            if (numericalValues.length > 3 )  {
                            System.out.println("Введено больше 3х номеров групп");
                            }
                            else if (!checkNumericalValuesEqualGN(numericalValues)){
                            System.out.println("Введены неправильные номера групп");
                            }
                            else if (checkNumericalValuesEqualGN(numericalValues)) {
                            outputGroup(numericalValues);
                            }}
                        else System.out.println("Введены некоректные данные, нет возможности преобразовать в int");
                    break;
                case "remove":
                    if (arrayСommandInputParameters.length == 1) {
                        System.out.println("Не указаны номера групп для удаления");
                    }
                    else if (numericalValues.length > 3 )  {
                        System.out.println("Введено больше параметров чем имеется групп");
                    }
                    else if ((!checkNumericalValuesEqualGN(numericalValues)) | (!checkArrayNumericalValues(numericalValues))){
                        System.out.println("Введены неправильные номера групп");
                    }
                    if (checkNumericalValuesEqualGN(numericalValues)) {
                        removePointsInGroups(numericalValues);
                        for (int i = 0; i< numericalValues.length; i++)
                        {
                            chooseGroup(numericalValues[i]).clear();
                        }
                    }
                    break;
                case "clear":{
                    listx1y.clear();
                    listx2y.clear();
                    listx3y.clear();
                    listfreepoints.clear();
                }
                break;
                case "help": {
                    System.out.print(
                            "Краткая справка по командам в программе:\n" +
                                    "add <point> - команда добавления в память программы точкек, координаты передаются парами чисел через пробел\n" +
                                    "              прим. add 1 1 -2 -3 //добавляет 2 точки: (1,1) и (-2,-3)\n" +
                                    "print - команда вывода в консоль построчно трех групп точек (y=x, y=x^2, y=x^3)\n" +
                                    "        а также последней строкой указывается количество точек, не вошедших ни в одну группу\n" +
                                    "print <group_num> - команда вывода точек, входящих в группу(ы) переданную(ые) параметром <group_num>\n" +
                                    "              прим. print 1 2 //выводит точки относящиеся к 1й и 2й группам\n" +
                                    "remove <group_num> - команда удаления из памяти всех точек, входящих в группу(ы) <group_num>\n" +
                                    "clear - команда очистки памяти\n" +
                                    "help - команда выводы справки по имеющимся командам");
                }
                break;
                default:
                    System.out.println("Введена неверная команда");
            }
        }

    }

    // Метод проверяющий, возможность перевода строк координат или номеров групп в значения типа Int
    public static boolean checkArrayNumericalValues(String[] strings){
        boolean result = false;
        int buffer;
        for (int i = 0; i < strings.length; i++)
        {
            try {
                buffer = Integer.parseInt(strings[i]);
                result = true;
            }
            catch (NumberFormatException e)
            {
                result = false;
                break;
            }
        }
        return result;
    }
    
    // Метод проверяющий, содержатся ли номера групп для команд print и remove в массиве groupNumbers
    public static boolean checkNumericalValuesEqualGN(String[] numbers){
        boolean result = false;
        for (int i = 0; i < numbers.length; i++)
        {
            if(!Arrays.asList(groupNumbers).contains(numbers[i])){
                result = false;
                break;
            }
            else result = true;
        }
        return result;
    }

    // Метод выводящий в консоль точки принадлежащие конкретной группе
    public static void outputPointInGroup(ArrayList<Point> points) {
        if (points.size()!=0){
            for (Point p : points) {
                p.outputCoordinate();
            }
            System.out.println("");
        }
        else {
            System.out.println("Группа точек пустая");
        }
    }

    // Метод выводящий в консоль группу по номеру/рам
    public static void outputGroup(String[] groups) {
        for (String str : groups) {
            switch (str) {
                case "1": outputPointInGroup(listx1y);
                    break;
                case "2": outputPointInGroup(listx2y);
                    break;
                case "3": outputPointInGroup(listx3y);
                    break;
            }
        }
    }

    // Метод возвращающий конкретную группу по номеру
    public static ArrayList<Point> chooseGroup(String groupNumber) {
        ArrayList<Point> group = new ArrayList<>();
        switch (groupNumber) {
            case "1": group = listx1y;
                break;
            case "2": group = listx2y;
                break;
            case "3": group = listx3y;
                break;
        }
        return group;
    }


    // Метод удаляющий конкретную группу(ы) по номеру
    public static void removePointsInGroups(String[] groups)
    {
        ArrayList<Point> buf;
        for (int i = 0; i<groups.length; i++)
        {
            switch (groups[i]) {
                case "1": {
                    buf = listx1y;
                    for(int j = 0;j < buf.size(); j++){
                        Point dot = buf.get(j);
                        if((listx2y.contains(dot)) |((listx3y.contains(dot)))){
                            listx2y.remove(dot);
                            listx3y.remove(dot);}
                    }
                }
                break;
                case "2": {
                    buf = listx2y;
                    for(int j = 0;j < buf.size(); j++){
                        Point dot = buf.get(j);
                        if((listx1y.contains(dot)) |((listx3y.contains(dot)))){
                            listx1y.remove(dot);
                            listx3y.remove(dot);}
                    }
                }
                break;
                case "3": {
                    buf = listx3y;
                    for(int j = 0;j < buf.size(); j++){
                        Point dot = buf.get(j);
                        if((listx1y.contains(dot)) |((listx2y.contains(dot)))){
                            listx1y.remove(dot);
                            listx2y.remove(dot);}
                    }
                }
                break;
            }
        }
    }

}
