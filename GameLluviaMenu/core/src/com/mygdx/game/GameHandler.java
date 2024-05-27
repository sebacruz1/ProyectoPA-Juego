package com.mygdx.game;

public class GameHandler {
    private GameState currentState;

    public void setState(GameState newState) {
        if (currentState != null) {
            currentState.end();
        }
        currentState = newState;
        currentState.start();
    }
}
