package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PausaScreen extends ScreenAdapter {
    private final GameLluviaMenu game;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final BitmapFont font;

    public PausaScreen(GameLluviaMenu game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1920, 1080);
        this.batch = game.getBatch();
        this.font = game.getFont();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 1.0f, 0.5f);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Juego en Pausa", 100, 150);
        font.draw(batch, "Toca en cualquier lado para continuar!!!", 100, 100);
        batch.end();

        if (Gdx.input.isTouched()) {
            // Volver a la instancia existente de GameScreen
            game.setScreen(game.getGameScreen());
        }
    }

    @Override
    public void pause() {
        // Aquí podrías manejar acciones adicionales si es necesario
    }

    @Override
    public void resume() {
        // Aquí podrías manejar acciones adicionales si es necesario
    }

    @Override
    public void hide() {
        // Liberar recursos si es necesario
    }

    @Override
    public void dispose() {
        // No llamar a dispose aquí ya que este screen puede ser reutilizado
    }
}
