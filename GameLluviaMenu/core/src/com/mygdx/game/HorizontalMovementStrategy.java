package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class HorizontalMovementStrategy implements MovementStrategy {
    private int velocity;

    public HorizontalMovementStrategy(int velocity) {
        this.velocity = velocity;
    }

    @Override
    public void move(Rectangle sumo, float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) sumo.x -= velocity * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) sumo.x += velocity * deltaTime;
        // Asegurar que el sumo no salga de los bordes de la pantalla
        if (sumo.x < 0) sumo.x = 0;
        if (sumo.x > 1920 - 64) sumo.x = 1920 - 64;
    }
}

