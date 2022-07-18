package com.example.xogame;

public class Player {
    private String name;
    private char symbol;
    private String color;
    private byte priority;


    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

}
