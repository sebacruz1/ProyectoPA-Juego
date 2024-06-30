package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public interface MovementStrategy {
    void move(Rectangle sumo, float deltaTime);
}
