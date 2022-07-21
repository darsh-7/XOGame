package com.example.xogame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.xogame.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    Intent outIntent;
    String player1Name, player2Name;
    EditText Name1, Name2;
    String p1Name, p2Name;
    Button save;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //////
        /*
        Name1=(EditText) findViewById(R.id.Name1);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outIntent = new Intent(SettingActivity.this,MainActivity.class);
                p1Name= Name1.getText().toString();
                outIntent.putExtra("name",p1Name);
                startActivity(outIntent);
            }
        });

         */
    }

    public void saveSettings(View v) {
        outIntent = new Intent(SettingActivity.this, MainActivity.class);

        Name1 = (EditText) findViewById(R.id.Name1);
        p1Name = Name1.getText().toString();
        outIntent.putExtra("name", p1Name);




        startActivity(outIntent);
    }

}