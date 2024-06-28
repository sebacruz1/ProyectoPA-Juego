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
    private int vidas;
    private int puntos;
    private int velx;
    private boolean herido;
    private int tiempoHeridoMax;
    private int tiempoHerido;

    // Constructor privado para que solo el Builder pueda crear instancias de Sumo
    private Sumo(SumoBuilder builder) {
        this.sumoImage = builder.sumoImage;
        this.sonidoHerido = builder.sonidoHerido;
        this.vidas = builder.vidas;
        this.puntos = builder.puntos;
        this.velx = builder.velx;
        this.herido = builder.herido;
        this.tiempoHeridoMax = builder.tiempoHeridoMax;
        this.tiempoHerido = builder.tiempoHerido;
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

    // Clase Builder estática
    public static class SumoBuilder {
        private Texture sumoImage;
        private Sound sonidoHerido;
        private int vidas = 3; // valor por defecto
        private int puntos = 0; // valor por defecto
        private int velx = 600; // valor por defecto
        private boolean herido = false; // valor por defecto
        private int tiempoHeridoMax = 50; // valor por defecto
        private int tiempoHerido = 0; // valor por defecto

        public SumoBuilder(Texture sumoImage, Sound sonidoHerido) {
            this.sumoImage = sumoImage;
            this.sonidoHerido = sonidoHerido;
        }

        public SumoBuilder vidas(int vidas) {
            this.vidas = vidas;
            return this;
        }

        public SumoBuilder puntos(int puntos) {
            this.puntos = puntos;
            return this;
        }

        public SumoBuilder velx(int velx) {
            this.velx = velx;
            return this;
        }

        public SumoBuilder herido(boolean herido) {
            this.herido = herido;
            return this;
        }

        public SumoBuilder tiempoHeridoMax(int tiempoHeridoMax) {
            this.tiempoHeridoMax = tiempoHeridoMax;
            return this;
        }

        public SumoBuilder tiempoHerido(int tiempoHerido) {
            this.tiempoHerido = tiempoHerido;
            return this;
        }

        public Sumo build() {
            return new Sumo(this);
        }
    }
}
