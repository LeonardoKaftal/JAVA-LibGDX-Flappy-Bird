package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Rectangle {
    float x;
    float y;
    int score = 0;
    public static final int WIDTH = 150;
    public static final int HEIGHT = 150;

    public Player(float x, float y) {
        super();
        this.x = x;
        this.y = y;

    }

    public void addGravity() {
        final int yVelocity = 150;
        final int jumpingPower = 4100;

        this.y -= yVelocity * Gdx.graphics.getDeltaTime();
        if (Gdx.input.justTouched()) this.y += jumpingPower * Gdx.graphics.getDeltaTime();
        if (this.y < 0) this.y = 0;
    }

}
