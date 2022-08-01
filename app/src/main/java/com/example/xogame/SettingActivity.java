package com.example.xogame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class SettingActivity extends AppCompatActivity {
    MediaPlayer backMusic;
    boolean playMusic = true, darkmode = false, clickSound = true, settingsActiv = false;
    Intent outIntent;
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
        setToggleButton();
        selectBot();
    }

    public void setToggleButton() {

        ToggleButton myToggle = ((ToggleButton) findViewById(R.id.music));

        if (playMusic) {
            myToggle.setChecked(true);
            myToggle.setTextColor(Color.GREEN);
        } else {
            myToggle.setChecked(false);
            myToggle.setTextColor(Color.RED);
        }

        myToggle = ((ToggleButton) findViewById(R.id.click));
        if (clickSound) {
            myToggle.setChecked(true);
            myToggle.setTextColor(Color.GREEN);
        } else {
            myToggle.setChecked(false);
            myToggle.setTextColor(Color.RED);
        }


        Switch mySwitch = (Switch) findViewById(R.id.darkMode);

        if (isNightMode(this))
            mySwitch.setChecked(true);
        else
            mySwitch.setChecked(false);


        /*
        myToggle = ((ToggleButton) findViewById(R.id.darkMode));
        if (darkmode) {
            myToggle.setChecked(true);
            myToggle.setTextColor(Color.GREEN);
            myToggle.setBackgroundColor(Color.BLACK);
        } else {
            myToggle.setChecked(false);
            myToggle.setTextColor(Color.RED);
        }

         */
    }


    public boolean isNightMode(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }


    public void selectBot() {

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.bot_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        Spinner staticSpinner = (Spinner) findViewById(R.id.bot);
        staticSpinner.setAdapter(staticAdapter);

        staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.difficulty_array,
                        android.R.layout.simple_spinner_item);

        staticSpinner = (Spinner) findViewById(R.id.difficulty);
        staticSpinner.setAdapter(staticAdapter);

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

        String[] items = new String[]{"X", "O", "/", "|", "\\", "-", "+"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        Spinner dynamicSpinner;
        dynamicSpinner = (Spinner) findViewById(R.id.symbol1);
        dynamicSpinner.setAdapter(adapter);

        items = new String[]{"O", "X", "/", "|", "\\", "-", "+"};

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        dynamicSpinner = (Spinner) findViewById(R.id.symbol2);
        dynamicSpinner.setAdapter(adapter);

    }

    public void cancelSetting(View v) throws InterruptedException {
        outIntent = new Intent(SettingActivity.this, MainActivity.class);

        playSound("click");


        EditText tempEditText;

        //name player 1
        tempEditText = (EditText) findViewById(R.id.Name1);
        p1Name = tempEditText.getText().toString();
        if (p1Name.isEmpty() || p1Name.trim().isEmpty())
            outIntent.putExtra("name1", "Player 1");
        else
            outIntent.putExtra("name1", p1Name);

        //name player 2
        tempEditText = (EditText) findViewById(R.id.Name2);
        p2Name = tempEditText.getText().toString();
        if (p2Name.isEmpty() || p2Name.trim().isEmpty())
            outIntent.putExtra("name2", "Player 2");
        else
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

        //bot system

        mySpinner = (Spinner) findViewById(R.id.bot);
        String botStats = mySpinner.getSelectedItem().toString();

        switch (botStats) {
            case "Player1":
                outIntent.putExtra("botStats", 1);
                break;

            case "player2":
                outIntent.putExtra("botStats", 2);
                break;

            default:
                outIntent.putExtra("botStats", 0);
        }
        mySpinner = (Spinner) findViewById(R.id.difficulty);
        botStats = mySpinner.getSelectedItem().toString();
        switch (botStats) {
            case "Normal":
                outIntent.putExtra("difficulty", 1);

                break;
            case "Hard":
                outIntent.putExtra("difficulty", 2);

                break;
            default:
                outIntent.putExtra("difficulty", 0);
        }

        //some data

        outIntent.putExtra("darkMode", darkmode);
        outIntent.putExtra("clickSound", clickSound);
        outIntent.putExtra("playMusic", playMusic);

        startActivity(outIntent);
    }


    public void saveSettings(View v) throws InterruptedException {
        outIntent = new Intent(SettingActivity.this, MainActivity.class);

        playSound("click");

        EditText tempEditText;

        //name player 1
        tempEditText = (EditText) findViewById(R.id.Name1);
        p1Name = tempEditText.getText().toString();
        if (p1Name.isEmpty() || p1Name.trim().isEmpty())
            outIntent.putExtra("name1", "Player 1");
        else
            outIntent.putExtra("name1", p1Name);

        //name player 2
        tempEditText = (EditText) findViewById(R.id.Name2);
        p2Name = tempEditText.getText().toString();
        if (p2Name.isEmpty() || p2Name.trim().isEmpty())
            outIntent.putExtra("name2", "Player 2");
        else
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

        //bot system

        mySpinner = (Spinner) findViewById(R.id.bot);
        String botStats = mySpinner.getSelectedItem().toString();

        switch (botStats) {
            case "Player1":
                outIntent.putExtra("botStats", 1);
                break;

            case "player2":
                outIntent.putExtra("botStats", 2);
                break;

            default:
                outIntent.putExtra("botStats", 0);
        }
        mySpinner = (Spinner) findViewById(R.id.difficulty);
        botStats = mySpinner.getSelectedItem().toString();
        switch (botStats) {
            case "Normal":
                outIntent.putExtra("difficulty", 1);

                break;
            case "Hard":
                outIntent.putExtra("difficulty", 2);

                break;
            default:
                outIntent.putExtra("difficulty", 0);
        }

        //some data
        outIntent.putExtra("darkMode", darkmode);
        outIntent.putExtra("clickSound", clickSound);
        outIntent.putExtra("playMusic", playMusic);
/*
        if (settingsActiv==true) {
        }
        else  {
            outIntent.putExtra("darkMode", true);
            outIntent.putExtra("clickSound", true);
            outIntent.putExtra("playMusic", true);
        }

 */
        startActivity(outIntent);
    }


    //sound method
    public void playSound(String sound) throws InterruptedException {
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
        else {
            music = MediaPlayer.create(this, R.raw.clickerror);
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
    public void stopPlayMusic(View toggle) {

        ToggleButton myToggle = (ToggleButton) toggle;

        if (backMusic.isPlaying()) {
            myToggle.setChecked(false);
            myToggle.setTextColor(Color.RED);
            playMusic = false;
            backMusic.stop();
        } else {
            myToggle.setChecked(true);
            myToggle.setTextColor(Color.GREEN);
            playMusic = true;
            playMusic();
        }
    }


    public void toggleClickSound(View toggle) {

        ToggleButton myToggle = (ToggleButton) toggle;

        if (clickSound) {
            myToggle.setChecked(false);
            myToggle.setTextColor(Color.RED);
            clickSound = false;
        } else {
            myToggle.setChecked(true);
            myToggle.setTextColor(Color.GREEN);
            clickSound = true;
        }
    }


    public void playMusic() {
        //first song
        if (!playMusic)
            return;
        backMusic = MediaPlayer.create(this, R.raw.background);
        backMusic.setVolume(0.6f, 0.6f);
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
        Toast.makeText(this, "Note: Dark Mode in beta version", (int) 50).show();
        if (((Switch) findViewById(R.id.darkMode)).isChecked()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        //backMusic.stop();
    }

/*
        ToggleButton myToggle = ((ToggleButton) findViewById(R.id.darkMode));

        if (myToggle.getText()=="OFF") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //myToggle.setChecked(true);
            myToggle.setTextColor(Color.GREEN);
            myToggle.setBackgroundColor(Color.BLACK);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //myToggle.setChecked(false);
            myToggle.setTextColor(Color.RED);
        }
        myToggle.toggle();



        backMusic.stop();
    }
*/
}