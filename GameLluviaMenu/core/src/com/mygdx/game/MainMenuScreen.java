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

    @Override
    protected void clearScreen() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
    }

    @Override
    protected void updateCamera() {
        camera.update();
    }

    @Override
    protected void drawBackground() {
        // Aquí puedes dibujar cualquier fondo que necesites en el menú principal
    }

    @Override
    protected void drawUI() {
        // Aquí puedes dibujar la interfaz de usuario del menú principal
    }

    @Override
    protected boolean updateGameLogic(float delta) {
        // Implementa cualquier lógica específica del menú principal que necesites actualizar cada frame
        return true; // Devuelve true si todo va bien o false si el juego debe terminar o cambiar de estado
    }

    @Override
    protected void onGameOver() {
        // Manejo del evento de game over desde el menú principal
    }
}
