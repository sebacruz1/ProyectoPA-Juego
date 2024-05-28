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
    private final Texture sushi1;
    private final Texture sushi2;
    private final Texture sushi3;
    private final Texture poop;
    private final Texture heart;
    private final Sound dropSound;
    private final Sound hurtFinal;
    private final Music rainMusic;
    private float speed;
    private int level;

    public Lluvia(Texture sushi1, Texture sushi2, Texture sushi3, Texture poop, Texture heart, Sound drop, Sound hurt, Music rain, float initialSpeed, int level) {
        rainMusic = rain;
        dropSound = drop;
        hurtFinal = hurt;
        this.sushi1 = sushi1;
        this.sushi2 = sushi2;
        this.sushi3 = sushi3;
        this.poop = poop;
        this.heart = heart;
        this.speed = initialSpeed; // Initialize the speed
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
        sushiDrop.x = MathUtils.random(0, 1920 - 64); // Random x position
        sushiDrop.y = 1080;
        sushiDrop.width = 125;
        sushiDrop.height = 125;
        sushiDropsPos.add(sushiDrop);

        // Determine the type of sushi drop: 1 for harmful (poop), 2, 3, or 4 for collectible sushi
        int type;
        int number = MathUtils.random(1, 100);
        if (number <= 35 * level) {
            type = 1; // harmful
        } else if (number == 55) {
            type = 55;
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
                    	hurtFinal.play();
                    	rainMusic.stop();
                        return false; // game over
                      
                    }
                } else if (type == 55) {
                    sumo.agregarVida();

                }
                else { // collectible sushi
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
        int tipoSushi = 1;
        for (int i = 0; i < sushiDropsPos.size; i++) {
            Rectangle sushiDrop = sushiDropsPos.get(i);

            if (sushiDropsType.get(i) == 1) {
                batch.draw(poop, sushiDrop.x, sushiDrop.y);
            } else if (sushiDropsType.get(i) == 55) {
                batch.draw(heart, sushiDrop.x, sushiDrop.y);
            } else {
                batch.draw(texturaSushi(sushiDropsType.get(i)), sushiDrop.x, sushiDrop.y);

            }

        }
    }

    public void destruir() {
        dropSound.dispose();
        hurtFinal.dispose();
        rainMusic.stop();
        sushi1.dispose();
        sushi2.dispose();
        sushi3.dispose();
        poop.dispose();
    }

    public Texture texturaSushi(int tipoSushi) {
        switch (tipoSushi) {
            case 2:
                return sushi2;
            case 3:
                return sushi3;
            default:
                return sushi1;

        }
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
}
