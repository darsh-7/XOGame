package com.example.xogame;

import android.graphics.Color;
import android.graphics.ComposePathEffect;

public class Player {
    private String name;
    private String symbol;
    private int color;

    Player() {
        name = "";
        symbol = ".";
        color = Color.BLACK;
    }

    void setPlayer(String name, char symbol, int color, byte priority) {
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


    public void setColor(String color) {
        if (color == null) {
            this.color = Color.BLACK;
            return;
        }
        switch (color) {
            case "black":
                this.color = Color.BLACK;
                break;
            case "blue":
                this.color = Color.BLUE;
                break;
            case "red":
                this.color = Color.RED;
                break;
            case "yellow":
                this.color = Color.YELLOW;
                break;
        }
    }

    public int getColor() {
        return color;
    }


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public String getSymbol() {
        return symbol;
    }


}
