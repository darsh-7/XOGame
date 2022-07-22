package com.example.xogame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    MediaPlayer backMusic;
    boolean playMusic = true;
    Intent outIntent;
    EditText tempEditText;
    String p1Name, p2Name;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //////
        lodePage();
    }

    //lode page Spinner and music
    public void lodePage() {
        playMusic();
        selectColor();
        selectSymbol();
    }

    public void selectColor() {

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.color_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        Spinner staticSpinner = (Spinner) findViewById(R.id.color1);
        staticSpinner.setAdapter(staticAdapter);

        staticSpinner = (Spinner) findViewById(R.id.color2);
        staticSpinner.setAdapter(staticAdapter);
    }

    public void selectSymbol() {

        String[] items = new String[]{"X", "O", "/", "|", "\"", "-"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        Spinner dynamicSpinner;
        dynamicSpinner = (Spinner) findViewById(R.id.symbol1);
        dynamicSpinner.setAdapter(adapter);
        dynamicSpinner = (Spinner) findViewById(R.id.symbol2);
        dynamicSpinner.setAdapter(adapter);

    }

    public void saveSettings(View v) throws InterruptedException {
        if (true){
        Toast.makeText(this,"asd",(int)50).show();
        }
        outIntent = new Intent(SettingActivity.this, MainActivity.class);

        playSound("click");

        //name player 1
        tempEditText = (EditText) findViewById(R.id.Name1);
        p1Name = tempEditText.getText().toString();
        outIntent.putExtra("name1", p1Name);

        //name player 2
        tempEditText = (EditText) findViewById(R.id.Name2);
        p2Name = tempEditText.getText().toString();
        outIntent.putExtra("name2", p2Name);

        //color 1
        Spinner mySpinner = (Spinner) findViewById(R.id.color1);
        String color = mySpinner.getSelectedItem().toString();
        outIntent.putExtra("color1", color);

        //color 2
        mySpinner = (Spinner) findViewById(R.id.color2);
        color = mySpinner.getSelectedItem().toString();
        outIntent.putExtra("color2", color);

        //symbol 1
        mySpinner = (Spinner) findViewById(R.id.symbol1);
        String symbol = mySpinner.getSelectedItem().toString();
        outIntent.putExtra("symbol1", symbol);

        //symbol 2
        mySpinner = (Spinner) findViewById(R.id.symbol2);
        symbol = mySpinner.getSelectedItem().toString();
        outIntent.putExtra("symbol2", symbol);


        startActivity(outIntent);
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
            playMusic = false;
            backMusic.stop();
        } else {
            playMusic = true;
            playMusic();
        }
    }


    public void playMusic() {
        //first song
        if (!playMusic)
            return;
        backMusic = MediaPlayer.create(this, R.raw.background);
        backMusic.setVolume(1.0f, 1.0f);
        backMusic.setLooping(true);
        backMusic.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        backMusic.pause();
    }

    public void onResume() {
        super.onResume();
        backMusic.start();
    }
    public void darkMode(View v) {
        if (((Switch) findViewById(R.id.dark_mode)).isChecked()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        backMusic.stop();
    }

}