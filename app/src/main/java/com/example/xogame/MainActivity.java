package com.example.xogame;


import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Player player1 = new Player();
    Player player2 = new Player();
    Intent intentIn;
    private boolean freeze = false, gameActive = false, playMusic = true, clickSound = true, darkMode = false;
    private int counter = 0;
    MediaPlayer backMusic;
    static char boxes[] = new char[]{'X', 'O', 'G', 'A', 'M', 'E', '9', '8', '7'};
    final int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    BotSystem bot = new BotSystem(0, 1);


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        ///////
        applySettings();
        playMusic();
    }


    public void applySettings() {
        playSound("click");
        intentIn = getIntent();
        Bundle bundleIn = intentIn.getExtras();


        player1.setName(intentIn.getStringExtra("name1"));
        player2.setName(intentIn.getStringExtra("name2"));

        player1.setColor(intentIn.getStringExtra("color1"));
        player2.setColor(intentIn.getStringExtra("color2"));

        player1.setSymbol(intentIn.getStringExtra("symbol1"));
        player2.setSymbol(intentIn.getStringExtra("symbol2"));


        int botStats = intentIn.getIntExtra("botStats", 0);
        int difficulty = intentIn.getIntExtra("difficulty", 0);
        bot.setBot(difficulty, botStats);


        darkMode = bundleIn.getBoolean("darkMode");
        clickSound = bundleIn.getBoolean("clickSound");
        playMusic = bundleIn.getBoolean("playMusic");


    }

    //goto setting activity
    public void setting(View v) {
        Intent outIntent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(outIntent);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (playMusic)
            backMusic.pause();
    }

    public void onResume() {
        super.onResume();
        if (playMusic)
            backMusic.start();
    }

    //Determine round by the counter value
    public int getTurn() {
        return (counter == 0 | counter == 2 | counter == 4 | counter == 6 | counter == 8) ? 1 : 2;
    }

    //reset the game values
    public void resetGame() {
        ((TextView) findViewById(R.id.play)).setText("Play");
        ((TextView) findViewById(R.id.status)).setText("Tap play to start");
        ((TextView) findViewById(R.id.box0)).setText("");
        ((TextView) findViewById(R.id.box1)).setText("");
        ((TextView) findViewById(R.id.box2)).setText("");
        ((TextView) findViewById(R.id.box3)).setText("");
        ((TextView) findViewById(R.id.box4)).setText("");
        ((TextView) findViewById(R.id.box5)).setText("");
        ((TextView) findViewById(R.id.box6)).setText("");
        ((TextView) findViewById(R.id.box7)).setText("");
        ((TextView) findViewById(R.id.box8)).setText("");
        counter = 0;
        gameActive = freeze = false;
        boxes = new char[]{'X', 'O', 'G', 'A', 'M', 'E', '9', '8', '7'};
        int botStats = intentIn.getIntExtra("botStats", 0);
        int difficulty = intentIn.getIntExtra("difficulty", 0);
        bot.setBot(difficulty, botStats);

    }

    //Determine if any player win or not
    private boolean win_or_loss() {
        for (int poss = 0; poss <= 7; poss++) {
            if (boxes[winPositions[poss][0]] == boxes[winPositions[poss][1]] && boxes[winPositions[poss][0]] == boxes[winPositions[poss][2]])
                return true;
        }
        return false;                 //return false for no winner until now
    }

    //active the game to start play
    public void activeGame(View buttom) throws InterruptedException {
        TextView status = (TextView) findViewById(R.id.status);
        TextView play = (TextView) findViewById(R.id.play);
        playSound("click");
        if (!gameActive) {
            gameActive = true;
            freeze = false;
            play.setText("Clear");

            if (bot.getPlayer() == 1) {
                Player playerBot = (bot.getPlayer() == 1) ? player1 : player2;

                if (bot.getDifLevel() == 0) {
                    int int_random = BotSystem.easyLevel(boxes);
                    Log.d("return", "rondom to select" + int_random + "  ");
                    selectBox(int_random, playerBot.getSymbol(), playerBot.getColor());
                } else if (bot.getDifLevel() == 1) {
                    int int_random = BotSystem.normalMode(boxes);
                    Log.d("return", "normal to select" + int_random + "  ");
                    selectBox(int_random, playerBot.getSymbol(), playerBot.getColor());

                } else if (bot.getDifLevel() == 2) {
                    int int_random = BotSystem.hardMode(boxes);
                    Log.d("return", "hard to select" + int_random + "  ");
                    selectBox(int_random, playerBot.getSymbol(), playerBot.getColor());
                }
            }


            String playerName = ((getTurn() == 1) ? player1.getName() : player2.getName());
            status.setText("it is (" + playerName + ") turn");
        } else {
            //reset the game
            resetGame();
        }

    }

    // fined a box using his id
    public byte finedBox(View box) {

        if (box.getId() == ((TextView) findViewById(R.id.box0)).getId())
            return 0;
        else if (box.getId() == ((TextView) findViewById(R.id.box1)).getId())
            return 1;
        else if (box.getId() == ((TextView) findViewById(R.id.box2)).getId())
            return 2;
        else if (box.getId() == ((TextView) findViewById(R.id.box3)).getId())
            return 3;
        else if (box.getId() == ((TextView) findViewById(R.id.box4)).getId())
            return 4;
        else if (box.getId() == ((TextView) findViewById(R.id.box5)).getId())
            return 5;
        else if (box.getId() == ((TextView) findViewById(R.id.box6)).getId())
            return 6;
        else if (box.getId() == ((TextView) findViewById(R.id.box7)).getId())
            return 7;
        else if (box.getId() == ((TextView) findViewById(R.id.box8)).getId())
            return 8;
        return -1;

    }

    public TextView getBox(int boxNum) {

        switch (boxNum) {
            case 0:
                return (TextView) findViewById(R.id.box0);
            case 1:
                return (TextView) findViewById(R.id.box1);
            case 2:
                return (TextView) findViewById(R.id.box2);
            case 3:
                return (TextView) findViewById(R.id.box3);
            case 4:
                return (TextView) findViewById(R.id.box4);
            case 5:
                return (TextView) findViewById(R.id.box5);
            case 6:
                return (TextView) findViewById(R.id.box6);
            case 7:
                return (TextView) findViewById(R.id.box7);
            case 8:
                return (TextView) findViewById(R.id.box8);
            default:
                return (TextView) findViewById(R.id.status);
        }
    }


    public void boxTaped(View box) {
        if (freeze || !gameActive) //do nothing when the game not active or in frozen mode
            return;

        if (boxes[finedBox(box)] == '1' || boxes[finedBox(box)] == '2')//check if is any value in this boxes
            return;

        //TextView status = (TextView) findViewById(R.id.status);
        TextView play = (TextView) findViewById(R.id.play);

        boolean turn = getTurn() == 1;

        if (turn) {
            selectBox(finedBox(box), player1.getSymbol(), player1.getColor());
        } else {
            selectBox(finedBox(box), player2.getSymbol(), player2.getColor());
        }
        if (freeze || !gameActive) //do nothing when the game not active or in frozen mode
            return;

        Player playerBot = (bot.getPlayer() == 1) ? player1 : player2;
        if (!(bot.getPlayer() == 0)) {

            if (bot.getDifLevel() == 0) {
                int int_random = BotSystem.easyLevel(boxes);
                Log.d("return", "rondom to select" + int_random + "  ");
                selectBox(int_random, playerBot.getSymbol(), playerBot.getColor());
            } else if (bot.getDifLevel() == 1) {
                int int_random = BotSystem.normalMode(boxes);
                Log.d("return", "normal to select" + int_random + "  ");
                selectBox(int_random, playerBot.getSymbol(), playerBot.getColor());

            } else if (bot.getDifLevel() == 2) {
                int int_random = BotSystem.hardMode(boxes);
                Log.d("return", "hard to select" + int_random + "  ");
                selectBox(int_random, playerBot.getSymbol(), playerBot.getColor());
            }

        }


        //play.setText("Clear");
    }


    public void selectBox(int boxNum, String symbol, int color) {


        TextView status = (TextView) findViewById(R.id.status);
        TextView play = (TextView) findViewById(R.id.play);
        String playerName = ((getTurn() == 1) ? player1.getName() : player2.getName());

        TextView m = getBox(boxNum);


        m.setText(symbol);
        //m.setTextColor(R.color.purple_200);
        m.setTextColor(color);

        char playerNumberChar = (getTurn() == 1) ? '1' : '2';
        boxes[boxNum] = playerNumberChar;//change box value


        if (bot.getPlayer() == 0)
            playSound("box");
        else if (getTurn() == bot.getPlayer())
            playSound("bSelect");
        else if (getTurn() == bot.getPlayer())
            playSound("bSelect");
        else
            playSound("box");


        if (counter >= 4 && win_or_loss()) {
            status.setText(playerName + " win");
            freeze = true;
            if (bot.getPlayer() == 0)
                playSound("win");
            else if (getTurn() == 2 && bot.getPlayer() == 2)
                playSound("lose");
            else if (getTurn() == 1 && bot.getPlayer() == 1)
                playSound("lose");
            else
                playSound("win");
            Log.d("return", playerName + "win");
            return;
        } else if (++counter >= 9) {
            status.setText("Draw");
            freeze = true;
            playSound("draw");
            return;
        }
        //play.setText("Clear");
        status.setText("it is (" + playerName + ") turn");

    }


    //sound method
    public void playSound(String sound) {
        if (!clickSound)
            return;
        MediaPlayer music;
        if (sound == "box")
            music = MediaPlayer.create(this, R.raw.selectclick);
        else if (sound == "click")
            music = MediaPlayer.create(this, R.raw.clicktone);
        else if (sound == "win")
            music = MediaPlayer.create(this, R.raw.win);
        else if (sound == "draw")
            music = MediaPlayer.create(this, R.raw.draw);
        else if (sound == "bSelect")
            music = MediaPlayer.create(this, R.raw.botclick);
        else if (sound == "lose")
            music = MediaPlayer.create(this, R.raw.lose);
        else {
            music = MediaPlayer.create(this, R.raw.clickerror);
            /*
            ((TextView) findViewById(R.id.status)).setText("error with click sound");
            Thread.sleep(2000);
             */
        }
        music.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                music.start();
            }
        });
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                music.release();
            }
        });
    }

    //control the music

    public void playMusic() {

        if (!playMusic) {
            return;
        }
        backMusic = MediaPlayer.create(this, R.raw.background2);
        backMusic.setVolume(1.0f, 1.0f);
        backMusic.setLooping(true);
        backMusic.start();
        /*
        backMusic = MediaPlayer.create(this, R.raw.background);
        backMusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                backMusic.start();
            }
        });


         */
    }

    //dark mode option
    public void darkMode(View v) {
        if (((Switch) findViewById(R.id.dark_mode)).isChecked()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        backMusic.stop();
    }


}