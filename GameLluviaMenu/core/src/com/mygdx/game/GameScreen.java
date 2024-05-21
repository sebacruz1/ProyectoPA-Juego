package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Sumo sumo;
    private Lluvia lluvia;
    private int level;
    private float initialSpeed;

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
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
        Texture star = new Texture(Gdx.files.internal("star.png"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // Initialize Lluvia with initial speed
        lluvia = new Lluvia(sushi1, sushi2, sushi3, poop, star, dropSound, rainMusic, initialSpeed, level);

        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        // Creacion del sumo
        sumo.crear();

        // Creacion de la lluvia
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        // Limpia la pantalla con color azul obscuro.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Actualizar matrices de la cámara
        camera.update();

        // Actualizar
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibujar textos
        font.draw(batch, "Gotas totales: " + sumo.getPuntos(), 5, 1050);
        font.draw(batch, "Vidas : " + sumo.getVidas(), 670, 1050);
        font.draw(batch, "HighScore : " + game.getHigherScore(), 5, 1000);
        font.draw(batch, "Nivel : " + level, camera.viewportWidth / 2 - 50, 1050);
        font.draw(batch, "Speed : " +  (initialSpeed + (level - 1) * 100.0f), 1600, 1050 );

        if (!sumo.estaHerido()) {
            // Movimiento del sumo desde teclado
            sumo.actualizarMovimiento();

            // Caida de la lluvia
            if (!lluvia.actualizarMovimiento(sumo)) {
                // Actualizar HigherScore
                if (game.getHigherScore() < sumo.getPuntos())
                    game.setHigherScore(sumo.getPuntos());

                // Ir a la ventana de finde juego y destruir la actual
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }

        sumo.dibujar(batch);
        lluvia.actualizarDibujoSushi(batch);

        batch.end();

        // Check level progression
        checkLevelProgression();
    }

    private void checkLevelProgression() {
        int points = sumo.getPuntos();

        // Example of level progression: increase drops per interval every 10 points
        if (points >= level * 10) {
            level++;
            float newSpeed = initialSpeed + (level - 1) * 100.0f; // Increase speed by 50 units per level
            int newDropsPerInterval = level; // Increase number of drops per interval
            lluvia.setSpeed(newSpeed);

        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
        // Continuar con sonido de lluvia
        lluvia.continuar();
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {
        lluvia.pausar();
        game.setScreen(new PausaScreen(game, this));
    }

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        sumo.destruir();
        lluvia.destruir();
    }
}
