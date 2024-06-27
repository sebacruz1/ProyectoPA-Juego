package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameLluviaMenu extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    private int higherScore;
    private GameScreen gameScreen; // Referencia a la instancia actual de GameScreen

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font

        // Obtener las instancias Singleton
        GameHandler gameHandler = GameHandler.getInstance();
        ConfiguracionGlobal configuracionGlobal = ConfiguracionGlobal.getInstance();

        gameScreen = new GameScreen(this); // Crear una instancia de GameScreen
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public int getHigherScore() {
        return higherScore;
    }

    public void setHigherScore(int higherScore) {
        this.higherScore = higherScore;
    }

    public GameScreen getGameScreen() {
        return gameScreen; // Método para obtener la instancia actual de GameScreen
    }
}
