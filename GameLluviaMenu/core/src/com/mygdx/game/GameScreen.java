// GameScreen.java
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends AbstractScreen implements GameState {
    private Sumo sumo;
    private Lluvia lluvia;
    private int level;
    private float initialSpeed;

    public GameScreen(final GameLluviaMenu game) {
        super(game);
        this.level = 1; // Initial level
        this.initialSpeed = 500.0f; // Initial speed

        // Load the images for the droplet and the sumo, 64x64 pixels each
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        sumo = new Sumo(new Texture(Gdx.files.internal("sumo.png")), hurtSound);

        // Load the drop sound effect and the rain background "music"
        Texture sushi1 = new Texture(Gdx.files.internal("sushi1.png"));
        Texture sushi2 = new Texture(Gdx.files.internal("sushi2.png"));
        Texture sushi3 = new Texture(Gdx.files.internal("sushi3.png"));
        Texture poop = new Texture(Gdx.files.internal("poop.png"));
        Texture heart = new Texture(Gdx.files.internal("heart.png"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // Initialize Lluvia with initial speed
        lluvia = new Lluvia(sushi1, sushi2, sushi3, poop, heart, dropSound, rainMusic, initialSpeed, level);

        // Creacion del sumo
        sumo.crear();

        // Creacion de la lluvia
        lluvia.crear();
    }

    @Override
    public void start() {
        // Código para iniciar la pantalla de juego
    }

    @Override
    public void end() {
        // Código para terminar la pantalla de juego
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Gotas totales: " + sumo.getPuntos(), 5, 1050);
        font.draw(batch, "Vidas : " + sumo.getVidas(), 670, 1050);
        font.draw(batch, "HighScore : " + game.getHigherScore(), 5, 1000);
        font.draw(batch, "Nivel : " + level, camera.viewportWidth / 2 - 50, 1050);
        font.draw(batch, "Speed : " + (initialSpeed + (level - 1) * 100.0f), 1600, 1050);

        if (!sumo.estaHerido()) {
            sumo.actualizarMovimiento();
            if (!lluvia.actualizarMovimiento(sumo)) {
                if (game.getHigherScore() < sumo.getPuntos()) {
                    game.setHigherScore(sumo.getPuntos());
                }
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }

        sumo.dibujar(batch);
        lluvia.actualizarDibujoSushi(batch);
        batch.end();
        checkLevelProgression();
    }

    private void checkLevelProgression() {
        int points = sumo.getPuntos();
        if (points >= level * 10) {
            level++;
            float newSpeed = initialSpeed + (level - 1) * 100.0f;
            lluvia.setSpeed(newSpeed);
        }
    }
}
