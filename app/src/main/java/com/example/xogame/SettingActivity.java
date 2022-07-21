package com.example.xogame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingActivity extends AppCompatActivity {
    MediaPlayer backMusic;
    boolean playMusic=true;
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

        playMusic();
        selectColor();


    }

    public void selectColor(){

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
    public void selectSymbol(){
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.color2);

        String[] items = new String[] { "Chai Latte", "Green Tea", "Black Tea" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);


    }

    public void saveSettings(View v) {
        outIntent = new Intent(SettingActivity.this, MainActivity.class);

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

        startActivity(outIntent);
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
    public  void onPause() {
        super.onPause();
        backMusic.pause();
    }
    public  void onResume() {
        super.onResume();
        backMusic.start();
    }

}