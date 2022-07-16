package com.example.xogame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean freeze = false, gameActive = false;
    private int counter = 0;

    char boxes[] = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8'};
    final int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game);
    }
    //Determine round by the counter value
    public char getTurn() {
        return (counter == 0 | counter == 2 | counter == 4 | counter == 6 | counter == 8) ? 'X' : 'O';
    }
    //reset the game values
    public boolean resetGame() {
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
        return true;
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
    public void activeGame(View buttom) {
        TextView status = (TextView) findViewById(R.id.status);
        TextView play = (TextView) findViewById(R.id.play);

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
    public void darkMode(View v){}//dark mode option

    //take action when a box clicked
    public void boxTaped(View box) {
        if (!gameActive || freeze) //do nothing when the game not active or in frozen mode
            return;
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
            if (counter >= 9)
                status.setText("Draw");

            return;
        }
        counter++;
        status.setText("it is (" + getTurn() + ") turn");

    }

}
