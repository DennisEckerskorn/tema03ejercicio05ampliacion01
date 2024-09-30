package com.denniseckerskorn.tema03ejercicio05ampliacion01;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denniseckerskorn.tema03ejercicio05ampliacion01.modelos.GuessNumberGame;

public class MainActivity extends AppCompatActivity {
    private Button[] buttons;
    private GuessNumberGame game;
    private TextView tvGuessMessage;
    private TextView tvRemaingAttempts;
    private TextView tvGameFinishedMessage;
    private TextView tvCurrentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvGuessMessage = findViewById(R.id.tvGuessMessage);
        tvRemaingAttempts = findViewById(R.id.tvRemainingAttempts);
        tvGameFinishedMessage = findViewById(R.id.tvGameFinishedMessage);
        tvCurrentValue = findViewById(R.id.tvCurrentValue);

        //Referencia al gridLayout:
        GridLayout glButtons = findViewById(R.id.glButtons);
        int rowCount = 2;
        int columnCount = 5;

        createButtons(glButtons, rowCount, columnCount);
        startNewGame();

        glButtons.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int gridWidth = glButtons.getWidth();

                int buttonSize = gridWidth / columnCount;

                // Ajustar el tamaño de cada botón para que sea cuadrado
                for (Button button : buttons) {
                    GridLayout.LayoutParams params = (GridLayout.LayoutParams) button.getLayoutParams();
                    params.width = buttonSize;  // Ancho del boton
                    params.height = buttonSize; // Alto del boton igual al ancho para que sea cuadrado
                    button.setLayoutParams(params);
                }

                // Remover el listener después de ajustar el tamaño
                glButtons.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        glButtons.setOnClickListener(v -> resetGame());
    }

    private void createButtons(GridLayout glButtons, int rowCount, int columnCount) {
        glButtons.setRowCount(rowCount);
        glButtons.setColumnCount(columnCount);
        buttons = new Button[rowCount * columnCount];

        for (int i = 0; i < buttons.length; i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(i + 1));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            button.setLayoutParams(params);
            glButtons.addView(button);

            final int guess = i + 1;
            button.setOnClickListener(v -> handleGuess(guess));

            buttons[i] = button;
        }
    }

    private void handleGuess(int guess) {
        GuessNumberGame.GUESS_MESSAGE result = game.makeGuess(guess);

        switch (result) {
            case TOO_LOW:
                tvCurrentValue.setText(String.valueOf(guess));
                tvCurrentValue.setTextColor(ContextCompat.getColor(this, R.color.red));
                tvGuessMessage.setText(getString(R.string.greaterThen));
                break;
            case TOO_HIGH:
                tvCurrentValue.setText(String.valueOf(guess));
                tvCurrentValue.setTextColor(ContextCompat.getColor(this, R.color.red));
                tvGuessMessage.setText(getString(R.string.lessThen));
                break;
            case CORRECT:
                tvCurrentValue.setText(String.valueOf(guess));
                tvCurrentValue.setTextColor(ContextCompat.getColor(this, R.color.blue));
                tvGuessMessage.setText(getString(R.string.winnMessage));
                disableAllButtons();
                tvGameFinishedMessage.setText(R.string.tvStartAgain);
                break;
            case GAME_OVER:
                tvCurrentValue.setText(String.valueOf(guess));
                tvCurrentValue.setTextColor(ContextCompat.getColor(this, R.color.red));
                tvGuessMessage.setText(getString(R.string.gameOver, game.getRandomNumber()));
                disableAllButtons();
                tvGameFinishedMessage.setText(R.string.tvStartAgain);
                break;
        }

        updateRemaingAttempts();
        buttons[guess - 1].setEnabled(false);

        if(game.getAttempts() <= 0) {
            disableAllButtons();
            tvGameFinishedMessage.setText(R.string.tvStartAgain);
        }
    }

    private void startNewGame() {
        game = new GuessNumberGame(this, 1, 10);
        updateRemaingAttempts();
        enableAllButtons();
        tvGuessMessage.setText("");
        tvGameFinishedMessage.setText("");
    }

    private void disableAllButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    private void updateRemaingAttempts() {
        int remaingAttempts = game.getAttempts();
        String remaininAttemptsMessage = getString(R.string.tvRemainingAttempts, remaingAttempts);
        tvRemaingAttempts.setText(remaininAttemptsMessage);
    }

    private void enableAllButtons() {
        for(Button button : buttons) {
            button.setEnabled(true);
        }
    }

    private void resetGame() {
        startNewGame();
    }

}