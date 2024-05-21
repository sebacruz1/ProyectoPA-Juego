package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
    private Array<Rectangle> sushiDropsPos;
    private Array<Integer> sushiDropsType;
    private long lastDropTime;
    private Texture sushi1;
    private Texture sushi2;
    private Texture sushi3;
    private Texture poop;
    private Sound dropSound;
    private Music rainMusic;
    private float speed; // Variable to control the speed of the drops
    private int dropsPerInterval; // Variable to control the number of drops per interval
    private int level;

    public Lluvia(Texture sushi1, Texture sushi2, Texture sushi3, Texture poop, Sound ss, Music mm, float initialSpeed, int level) {
        rainMusic = mm;
        dropSound = ss;
        this.sushi1 = sushi1;
        this.sushi2 = sushi2;
        this.sushi3 = sushi3;
        this.poop = poop;
        this.speed = initialSpeed; // Initialize the speed
        this.dropsPerInterval = 5; // Initialize the number of drops per interval
        this.level = level;
    }

    public void crear() {
        sushiDropsPos = new Array<Rectangle>();
        sushiDropsType = new Array<Integer>();
        crearGotaDeSushi();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearGotaDeSushi() {

        Rectangle sushiDrop = new Rectangle();
        sushiDrop.x = MathUtils.random(0, 800 - 64); // Random x position
        sushiDrop.y = 480;
        sushiDrop.width = 64;
        sushiDrop.height = 64;
        sushiDropsPos.add(sushiDrop);

        // Determine the type of sushi drop: 1 for harmful (poop), 2, 3, or 4 for collectible sushi
        int type;
        if (MathUtils.random(1, 100) <= 35 * level) {
            type = 1; // harmful
        } else {
            type = MathUtils.random(2, 4); // collectible sushi
        }
        sushiDropsType.add(type);


        lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Sumo sumo) {
        if (TimeUtils.nanoTime() - lastDropTime > 99500000) {
            crearGotaDeSushi();
        }

        for (int i = 0; i < sushiDropsPos.size; i++) {
            Rectangle sushiDrop = sushiDropsPos.get(i);
            sushiDrop.y -= speed * Gdx.graphics.getDeltaTime(); // Adjust speed here

            if (sushiDrop.y + 64 < 0) {
                sushiDropsPos.removeIndex(i);
                sushiDropsType.removeIndex(i);
            }
            if (sushiDrop.overlaps(sumo.getArea())) {
                int type = sushiDropsType.get(i);
                if (type == 1) { // harmful drop
                    sumo.danar();
                    if (sumo.getVidas() <= 0) {
                        return false; // game over
                    }
                } else { // collectible sushi
                    sumo.sumarPuntos(1);
                    dropSound.play();
                }
                sushiDropsPos.removeIndex(i);
                sushiDropsType.removeIndex(i);
            }
        }
        return true;
    }

    public void actualizarDibujoSushi(SpriteBatch batch) {
        for (int i = 0; i < sushiDropsPos.size; i++) {
            Rectangle sushiDrop = sushiDropsPos.get(i);
            int type = sushiDropsType.get(i);

            switch (type) {
                case 1:
                    batch.draw(poop, sushiDrop.x, sushiDrop.y);
                    break;
                case 2:
                    batch.draw(sushi1, sushiDrop.x, sushiDrop.y);
                    break;
                case 3:
                    batch.draw(sushi2, sushiDrop.x, sushiDrop.y);
                    break;
                case 4:
                    batch.draw(sushi3, sushiDrop.x, sushiDrop.y);
                    break;
            }
        }
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
        sushi1.dispose();
        sushi2.dispose();
        sushi3.dispose();
        poop.dispose();
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }

    // Method to set the speed
    public void setSpeed(float newSpeed) {
        this.speed = newSpeed;
    }

    // Method to set the number of drops per interval
    public void setDropsPerInterval(int newDropsPerInterval) {
        this.dropsPerInterval = newDropsPerInterval;
    }
}
