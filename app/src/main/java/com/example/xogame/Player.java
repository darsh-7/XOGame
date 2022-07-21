package com.example.xogame;

import android.graphics.Color;

public class Player {
    private String name;
    //private char symbol;
    //private int color;
    //private byte priority;

    Player() {
        name = "";
        //symbol = '.';
        //color = Color.BLACK;
        //priority = 0;
    }
    void setPlayer(String name,char symbol,int color,byte priority) {
        this.name = name;
        //this.symbol = symbol;
        //this.color = color;
        //this.priority = priority;
    }



    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

/*
    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public byte getPriority() {
        return priority;
    }

    public char getSymbol() {
        return symbol;
    }
    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }


 */

}
