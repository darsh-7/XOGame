package com.example.xogame;

import android.util.Log;

import java.util.Random;

public class BotSystem {

    int difLevel;
    int player;

    BotSystem(int difLevel,int player){
        this.difLevel=difLevel;
        this.player=player;
    }


    public void setBot(int difLevel,int player){
        this.difLevel=difLevel;
        this.player=player;
    }

    public int getPlayer()
    {return player;}

    public int getDifLevel()
    {return difLevel;}

    static public int easyLevel(char[] arrayOfBox) {
        Random rand = new Random();
        int int_random = rand.nextInt(8);

        while (arrayOfBox[int_random] == '1'||arrayOfBox[int_random] == '2'){
            int_random = rand.nextInt(9);
        }
        Log.d("return","rondom"+int_random+"  ");

        return  int_random;
    }
}
