package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.audio.Music;

public class MainMenuScreen implements Screen, GameState {

	final GameLluviaMenu game;
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;
        private Music backgroundMusic;
        
	public MainMenuScreen(final GameLluviaMenu game) {
		this.game = game;
                this.batch = game.getBatch();
                this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
                
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
		font.draw(batch, "Bienvenido a Recolecta Gotas!!! ", 100, camera.viewportHeight/2+50);
		font.draw(batch, "Toca en cualquier lugar para comenzar!", 100, camera.viewportHeight/2-50);

		batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		//PausaScreen pausa = new PausaScreen(game, ());
		//pausa.pause();

		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
            backgroundMusic.dispose();
		// TODO Auto-generated method stub
		
	}

}
