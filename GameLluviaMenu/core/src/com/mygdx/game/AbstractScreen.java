package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractScreen implements Screen {
    protected final GameLluviaMenu game;
    protected final SpriteBatch batch;
    protected final BitmapFont font;
    protected final OrthographicCamera camera;

    public AbstractScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1920, 1080);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void show() {}

    @Override
    public void dispose() {}

    // Eliminamos la palabra clave 'final' para permitir, si es necesario, la sobrescritura.
    public void render(float delta) {
        clearScreen();
        updateCamera();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        drawBackground();
        drawUI();
        if (!updateGameLogic(delta)) {
            onGameOver();
        }
        batch.end();
    }

    protected abstract void clearScreen();

    protected abstract void updateCamera();

    protected abstract void drawBackground();

    protected abstract void drawUI();

    protected abstract boolean updateGameLogic(float delta);

    protected abstract void onGameOver();
}
