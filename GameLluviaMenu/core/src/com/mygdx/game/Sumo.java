package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Sumo {
    private Rectangle sumo;
    private Texture sumoImage;
    private Sound sonidoHerido;
    private int vidas = 3;
    private int puntos = 0;
    private int velx = 400;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;


    public Sumo(Texture tex, Sound ss) {
        sumoImage = tex;
        sonidoHerido = ss;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public Rectangle getArea() {
        return sumo;
    }

    public void sumarPuntos(int pp) {
        puntos += pp;
    }


    public void crear() {
        sumo = new Rectangle();
        sumo.x = 800 / 2 - 64 / 2;
        sumo.y = 20;
        sumo.width = 64;
        sumo.height = 64;
    }

    public void dañar() {
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
    }

    public void dibujar(SpriteBatch batch) {
        if (!herido)
            batch.draw(sumoImage, sumo.x, sumo.y);
        else {

            batch.draw(sumoImage, sumo.x, sumo.y + MathUtils.random(-5, 5));
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }


    public void actualizarMovimiento() {
        // movimiento desde mouse/touch
		   /*if(Gdx.input.isTouched()) {
			      Vector3 touchPos = new Vector3();
			      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			      camera.unproject(touchPos);
			      sumo.x = touchPos.x - 64 / 2;
			}*/
        //movimiento desde teclado
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) sumo.x -= velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) sumo.x += velx * Gdx.graphics.getDeltaTime();
        // que no se salga de los bordes izq y der
        if (sumo.x < 0) sumo.x = 0;
        if (sumo.x > 800 - 64) sumo.x = 800 - 64;
    }


    public void destruir() {
        sumoImage.dispose();
    }

    public boolean estaHerido() {
        return herido;
    }

}
