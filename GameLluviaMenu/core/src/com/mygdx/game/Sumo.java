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
    private int velx = 600;
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

    public void setVidas(int vidas) {
        if (vidas >= 0) {
            this.vidas = vidas;
        }
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        if (puntos >= 0) {
            this.puntos = puntos;
        }
    }

    public Rectangle getArea() {
        return sumo;
    }

    public boolean estaHerido() {
        return herido;
    }

    public void sumarPuntos(int pp) {
        this.puntos += pp;
    }

    public void agregarVida() {
        this.vidas++;
    }


    public void crear() {
        sumo = new Rectangle();
        sumo.x = 1920 / 2 - 64 / 2;
        sumo.y = 20;
        sumo.width = 128;
        sumo.height = 128;
    }

    public void danar() {
        if (vidas > 0) {
            vidas--;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
        }
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

    public void actualizarMovimiento(GameLluviaMenu game) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) sumo.x -= velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) sumo.x += velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
        
            PausaScreen pausaScreen = new PausaScreen(game);
            game.setScreen(pausaScreen);
        }
        // Asegurar que el sumo no salga de los bordes de la pantalla
        if (sumo.x < 0) sumo.x = 0;
        if (sumo.x > 1920 - 64) sumo.x = 1920 - 64;
    }

    public void destruir() {
        sumoImage.dispose();
        sonidoHerido.dispose();
    }
}
