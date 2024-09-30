package com.denniseckerskorn.tema03ejercicio05ampliacion01.modelos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import com.denniseckerskorn.tema03ejercicio05ampliacion01.R;

import java.util.Random;

public class GuessNumberGame {
    public enum GUESS_MESSAGE {
        TOO_LOW,
        TOO_HIGH,
        CORRECT,
        GAME_OVER
    }

    private int startNumber;
    private int endNumber;
    private int randomNumber;
    private static final int MAX_ATTEMPTS = 3;
    private int attempts;
    private Random rnd;
    private Resources resources;

    public GuessNumberGame(Context context, int startNumber, int endNumber) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.attempts = MAX_ATTEMPTS;
        this.randomNumber = generateRandomNumber();
        this.resources = context.getResources();
    }

    private int generateRandomNumber() {
        rnd = new Random();
        return rnd.nextInt(endNumber - startNumber + 1) + startNumber;
    }

    @SuppressLint({"StringFormatInvalid"})
    public GUESS_MESSAGE makeGuess(int guess) {
        if (attempts <= 0) {
            return GUESS_MESSAGE.GAME_OVER;
        }

        if (guess < randomNumber) {
            attempts--;
            return GUESS_MESSAGE.TOO_LOW;
        } else if (guess > randomNumber) {
            attempts--;
            return GUESS_MESSAGE.TOO_HIGH;
        } else {
            return GUESS_MESSAGE.CORRECT;
        }
    }

    public int getAttempts() {
        return attempts;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

}
