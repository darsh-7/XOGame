package com.example.xogame;


import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Player player1,player2;
    private boolean freeze = false, gameActive = false,playMusic =true;
    private int counter = 0;
    MediaPlayer backMusic;
    char boxes[] = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8'};
    final int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        playMusic();
       // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                backMusic.stop();
                MainActivity.this.finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()


    }
   //Determine round by the counter value
    public char getTurn() {
        return (counter == 0 | counter == 2 | counter == 4 | counter == 6 | counter == 8) ? 'X' : 'O';
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
        boxes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8'};
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
            status.setText("it is (" + getTurn() + ") turn");
        } else {
            //reset the game
            resetGame();
        }
    }

    // fined a box using his id
    public int finedBox(View box) {

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

    //take action when a box clicked
    public void boxTaped(View box) throws InterruptedException {
        if (!gameActive || freeze) //do nothing when the game not active or in frozen mode
            return;

        playSound("box");
        TextView status = (TextView) findViewById(R.id.status);
        TextView m = (TextView) findViewById(box.getId());
        int boxNum = finedBox(box);//store box number value
        if (getTurn() == 'X') {
            if (boxes[boxNum] == 'X' || boxes[boxNum] == 'O')//check if is any value in this boxes
                return;
            m.setText("X");

            boxes[finedBox(box)] = 'X';//change box value
        } else {
            if (boxes[boxNum] == 'X' || boxes[boxNum] == 'O')//check if is any value in this boxes
                return;
            m.setText("O");
            boxes[finedBox(box)] = 'O';
        }
        if (counter >= 4 && win_or_loss()) {
            status.setText(getTurn() + " win");
            freeze = true;
            playSound("win");
            return;
        } else if (++counter >= 9) {
            status.setText("Draw");
            freeze = true;
            playSound("draw");
            return;
        }

        status.setText("it is (" + getTurn() + ") turn");

    }
    //sound method
    public void playSound(String sound) throws InterruptedException {
        MediaPlayer music;
        if (sound == "box")
            music = MediaPlayer.create(this, R.raw.selectclick);
        else if (sound == "click")
            music = MediaPlayer.create(this, R.raw.clicktone);
        else if (sound == "win")
            music = MediaPlayer.create(this, R.raw.win);
        else if (sound == "draw")
            music = MediaPlayer.create(this, R.raw.draw);
        else {
            music = MediaPlayer.create(this, R.raw.clickerror);
            ((TextView) findViewById(R.id.status)).setText("error with click sound");
            Thread.sleep(2000);
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
    public void stopPlayMusic(View button) {
        if (backMusic.isPlaying()) {
            playMusic =false;
            backMusic.stop();
            button.setBackgroundResource(android.R.drawable.ic_lock_silent_mode);
        } else {
            playMusic =true;
            playMusic();
            button.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off);
        }
    }

    public void playMusic() {
        //first song
        if (!playMusic)
            return;
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
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        backMusic.stop();
    }


}