// MainMenuScreen.java
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen extends AbstractScreen implements GameState {
    private Music backgroundMusic;

    public MainMenuScreen(final GameLluviaMenu game) {
        super(game);
        // Cargar música de fondo
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
    }

    @Override
    public void start() {
        // Código para iniciar el menú principal
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override
    public void end() {
        // Código para cerrar el menú principal
        backgroundMusic.stop();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.getData().setScale(2, 2);
        font.draw(batch, "Bienvenido a Recolecta Gotas!!! ", 100, camera.viewportHeight / 2 + 50);
        font.draw(batch, "Toca en cualquier lugar para comenzar!", 100, camera.viewportHeight / 2 - 50);
        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }
}
