package com.mygdx.game;

public class GameHandler {
    private static GameHandler instance;
    private GameState currentState;

    // Constructor privado para evitar la instanciación directa
    private GameHandler() {}

    // Método estático para obtener la instancia única de la clase
    public static synchronized GameHandler getInstance() {
        if (instance == null) {
            instance = new GameHandler();
        }
        return instance;
    }

    public void setState(GameState newState) {
        if (currentState != null) {
            currentState.end();
        }
        currentState = newState;
        currentState.start();
    }
}
