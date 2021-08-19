package com.company;

public class Point {
    public int x;
    public int y;

    Point(String i, String j){
        x = Integer.parseInt(i);
        y = Integer.parseInt(j);
    }

    void outputCoordinate() {
        System.out.print("(" + x + "," + y + ")");
    }
}
