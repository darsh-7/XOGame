package com.example.xogame;

import android.util.Log;

import java.util.Random;

public class BotSystem {

    final static int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    int difLevel;
    int playerr;
    static char board[][] = new char[3][3];
    int row, col;

    BotSystem() {
    }

    BotSystem(int difLevel, int player) {
        this.difLevel = difLevel;
        this.playerr = player;
    }


    public void setBot(int difLevel, int player) {
        this.difLevel = difLevel;
        this.playerr = player;
    }

    public int getPlayer() {
        return playerr;
    }

    public int getDifLevel() {
        return difLevel;
    }

    static public char[][] twoD(char[] boxes) {
        char[][] boxes2 = new char[3][3];


        int count = 0;

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                if (count == boxes.length) break;

                boxes2[i][j] = boxes[count];

                //System.out.printf("a[%d][%d]= %d\n",i,j,boxes2[i][j]);

                count++;

            }

        }
        return boxes2;
    }


    static public int easyLevel(char[] arrayOfBox) {
        Random rand = new Random();
        int int_random = rand.nextInt(8);

        while (arrayOfBox[int_random] == '1' || arrayOfBox[int_random] == '2') {
            int_random = rand.nextInt(9);
        }
        Log.d("return", "rondom" + int_random + "  ");

        return int_random;
    }


    public static int normalMode(char[] boxes) {
        Random rand = new Random();
        if (rand.nextInt(8) < 4)
            return easyLevel(boxes);
        else
            return hardMode(boxes);
    }


    public static int hardMode(char[] boxes) {
        int location;
        for (int poss = 0; poss <= 7; poss++) {
            location = ((boxes[winPositions[poss][0]] == boxes[winPositions[poss][1]]) ? winPositions[poss][2] : -1);
            if (location != -1)
                if (!(boxes[location] == '1' || boxes[location] == '2')) {
                    Log.d("return", "1 hard mode val " + location + "  ");
                    return location;
                }

            location = ((boxes[winPositions[poss][1]] == boxes[winPositions[poss][2]]) ? winPositions[poss][0] : -1);
            if (location != -1)
                if (!(boxes[location] == '1' || boxes[location] == '2')) {
                    Log.d("return", "1 hard mode val " + location + "  ");
                    return location;
                }
            location = ((boxes[winPositions[poss][0]] == boxes[winPositions[poss][2]]) ? winPositions[poss][1] : -1);
            if (location != -1)
                if (!(boxes[location] == '1' || boxes[location] == '2')) {
                    Log.d("return", "1 hard mode val " + location + "  ");
                    return location;
                }
        }
        //make chance to win
        for (int poss = 0; poss <= 7; poss++) {
            int countAction = 0;
            int boxNum=-1;
            if ((boxes[winPositions[poss][0]] == '1' || boxes[winPositions[poss][0]] == '2') )
                countAction += 2;
            else{
                countAction++;
                boxNum = winPositions[poss][0];
            }
            if ((boxes[winPositions[poss][1]] == '1' || boxes[winPositions[poss][1]] == '2') )
                countAction += 2;
            else{
                countAction++;
                boxNum = winPositions[poss][1];
            }
            if ((boxes[winPositions[poss][2]] == '1' || boxes[winPositions[poss][2]] == '2') )
                countAction += 2;
            else{
                countAction++;
                boxNum = winPositions[poss][2];
            }

            if (countAction == 4) {
                Log.d("return", "find poss to win = " + boxNum);
                return boxNum;
            }
        }
        Log.d("skip", "go to easyLevel");
        return easyLevel(boxes);
    }
}