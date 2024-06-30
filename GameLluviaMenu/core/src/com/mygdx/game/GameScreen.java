package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends AbstractScreen implements GameState {
    private Sumo sumo;
    private Lluvia lluvia;
    private int level;
    private float initialSpeed;
    private Texture backgroundImage;

    public GameScreen(final GameLluviaMenu game) {
        super(game);
        this.level = 1; // Initial level
        this.initialSpeed = 500.0f; // Initial speed

        // Load the images for the droplet and the sumo, 64x64 pixels each
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        sumo = new Sumo.SumoBuilder(new Texture(Gdx.files.internal("sumo.png")), hurtSound)
                .vidas(3)
                .puntos(0)
                .velx(600)
                .tiempoHeridoMax(50)
                .movementStrategy(new HorizontalMovementStrategy(600)) // Asignar estrategia de movimiento
                .build();

        // Load the drop sound effect and the rain background "music"
        Texture sushi1 = new Texture(Gdx.files.internal("sushi1.png"));
        Texture sushi2 = new Texture(Gdx.files.internal("sushi2.png"));
        Texture sushi3 = new Texture(Gdx.files.internal("sushi3.png"));
        Texture poop = new Texture(Gdx.files.internal("poop.png"));
        Texture heart = new Texture(Gdx.files.internal("heart.png"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        Sound hurtFinal = Gdx.audio.newSound(Gdx.files.internal("hurtFinal.mp3"));

        // Initialize Lluvia with initial speed
        lluvia = new Lluvia(sushi1, sushi2, sushi3, poop, heart, dropSound, hurtFinal, rainMusic, initialSpeed, level);

        // Creacion del sumo
        sumo.crear();

        // Creacion de la lluvia
        lluvia.crear();

        // Inicialización de los recursos gráficos
        backgroundImage = new Texture(Gdx.files.internal("background.png"));
        
        camera.setToOrtho(false, 1920, 1080); // Configura esto al tamaño deseado de tu ventana
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

    @Override
    public void dispose() {
        backgroundImage.dispose();
    }

    protected void clearScreen() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1); // Limpia la pantalla con un color, opcional si el fondo cubre todo
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    protected void updateCamera() {
        camera.update();
    }

    protected void drawBackground() {
        batch.draw(backgroundImage, 0, 0, 1920, 1080); // Ajusta la imagen al tamaño de la ventana
    }

    protected void drawUI() {
        font.draw(batch, "Gotas totales: " + sumo.getPuntos(), 5, 1050); // Cerca del borde superior de la pantalla
        font.draw(batch, "Vidas : " + sumo.getVidas(), 500, 1050); // Cerca del borde superior, más hacia el centro
        font.draw(batch, "Puntuación Maxima : " + game.getHigherScore(), 5, 1020); // Un poco más abajo
        font.draw(batch, "Nivel : " + level, Gdx.graphics.getWidth() / 2 - 50, 1050); // Centrado horizontalmente en la parte superior
        font.draw(batch, "Speed : " + (initialSpeed + (level - 1) * 100.0f), 1400, 920); // Ajustado a la derecha
    }

    protected boolean updateGameLogic(float delta) {
        if (!sumo.estaHerido()) {
            sumo.actualizarMovimiento(delta); // Pasar delta time para actualizar el movimiento
            if (!lluvia.actualizarMovimiento(sumo)) {
                if (game.getHigherScore() < sumo.getPuntos()) {
                    game.setHigherScore(sumo.getPuntos());
                }
                return false;
            }
        }

        sumo.dibujar(batch);
        lluvia.actualizarDibujoSushi(batch);
        checkLevelProgression();
        return true;
    }

    protected void onGameOver() {
        game.setScreen(new GameOverScreen(game));
        dispose();
    }

    private void checkLevelProgression() {
        int points = sumo.getPuntos();
        if (points >= level * 10) {
            level++;
            float newSpeed = initialSpeed += 100.0f;
            lluvia.setSpeed(newSpeed);
        }
    }
}
