package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class VerticalMovementStrategy implements MovementStrategy {
    private int velocity;

    public VerticalMovementStrategy(int velocity) {
        this.velocity = velocity;
    }

    @Override
    public void move(Rectangle sumo, float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) sumo.y += velocity * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) sumo.y -= velocity * deltaTime;
        // Asegurar que el sumo no salga de los bordes de la pantalla
        if (sumo.y < 0) sumo.y = 0;
        if (sumo.y > 1080 - 64) sumo.y = 1080 - 64;
    }
}
