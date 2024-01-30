package com.mp.program5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mp.program5.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    int[] cellIds = {R.id.cellView0, R.id.cellView1, R.id.cellView2, R.id.cellView3, R.id.cellView4, R.id.cellView5, R.id.cellView6, R.id.cellView7, R.id.cellView8};
    boolean isXTurn;
    boolean gameActive = true;
    int counter = 0;

    //e is for empty, will be filled with either x or o
    //in handlePlayerTap. x are squares and o is a circle
    //in the UI.
    char[] gameState = {'e', 'e', 'e',
                        'e', 'e', 'e',
                        'e', 'e', 'e'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isXTurn = true;
        setCellListeners();
    }

    private void setCellListeners(){
        for(int cellId: cellIds){
            TicTacToeCellView cell = findViewById(cellId);
            cell.setOnClickListener(v -> handlePlayerTap((TicTacToeCellView) v));
        }
    }

    private void handlePlayerTap(TicTacToeCellView img){
        //The tag number represents the position on the board.
        int tappedImg = Integer.parseInt(img.getTag().toString());

        if(!gameActive){
            resetGame();
        }

        if(gameState[tappedImg] == 'e'){
            counter++;

            if(counter == 9){
                gameActive = false;
            }

            if(isXTurn){
                img.setToX();
                gameState[tappedImg] = 'x';
                isXTurn = false;
            }else{
                img.setToO();
                gameState[tappedImg] = 'o';
                isXTurn = true;
            }
        }

        if(checkWinner() == 'x'){
            binding.winnerText.setText("Square wins! Starting with square, tap a cell to start at for a new game.");
            gameActive = false;
        }
        if(checkWinner() == 'o'){
            binding.winnerText.setText("Circle wins! Starting with square, tap a cell to start at for a new game");
            gameActive = false;
        }
    }

    private void resetGame(){
        gameActive = true;
        Arrays.fill(gameState, 'e');
        counter = 0;
        isXTurn = true;
        binding.winnerText.setText("");

        //Reset drawing to be blank
        for(int cellId: cellIds){
            TicTacToeCellView cell = findViewById(cellId);
            cell.reset();
        }
    }

    private char checkWinner(){
        //All check methods return the character of the one that won, x or o. e if neither wins.
        if(checkColumns() == 'x' || checkRows() == 'x' || checkDiagonals() == 'x'){
            return 'x';
        }else if(checkColumns() == 'o' || checkRows() == 'o' || checkDiagonals() == 'o'){
            return 'o';
        }
        return 'e';
    }

    private char checkDiagonals(){
        if(gameState[0] == 'x' && gameState[4] == 'x' && gameState[8] == 'x'){
            return 'x';
        }

        if(gameState[0] == 'o' && gameState[4] == 'o' && gameState[8] == 'o'){
            return 'o';
        }

        if(gameState[2] == 'x' && gameState[4] == 'x' && gameState[6] == 'x'){
            return 'x';
        }
        if(gameState[2] == 'o' && gameState[4] == 'o' && gameState[6] == 'o'){
            return 'o';
        }
        return 'e';
    }

    private char checkRows(){
        for(int row = 0; row < 3; row++){
            boolean rowFilledWithX = true;
            boolean rowFilledWithO = true;

            for(int column = 0; column < 3; column++){
                char cellValue = gameState[row * 3 + column];

                if(cellValue != 'x'){
                    rowFilledWithX = false;
                }
                if(cellValue != 'o'){
                    rowFilledWithO = false;
                }
            }

            if(rowFilledWithX){
                return 'x';
            }else if(rowFilledWithO){
                return 'o';
            }
        }
        return 'e';
    }

    private char checkColumns(){
        for (int column = 0; column < 3; column++) {
            boolean columnIsFilledWithX = true;
            boolean columnIsFilledWithO = true;

            for (int row = 0; row < 3; row++) {
                char cellValue = gameState[row * 3 + column];

                if (cellValue != 'x') {
                    columnIsFilledWithX = false;
                }
                if (cellValue != 'o') {
                    columnIsFilledWithO = false;
                }
            }


            if (columnIsFilledWithX) {
                return 'x';
            } else if (columnIsFilledWithO) {
                return 'o';
            }
        }
        return 'e';
    }
}