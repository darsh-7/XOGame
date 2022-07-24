package com.example.xogame;

import java.util.Random;

public class BotSystem {

    int difLevel;
    int player;

    void BotSystem(int difLevel,int player){
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

    public int easyLevel(char[] arrayOfBox) {
        Random rand = new Random();
        int int_random = rand.nextInt(8);

        while (arrayOfBox[int_random]=='1'||arrayOfBox[int_random]=='2'){
            int_random = rand.nextInt(8);
        }
        return  int_random;
    }
}
