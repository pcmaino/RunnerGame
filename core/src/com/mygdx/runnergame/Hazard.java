package com.mygdx.runnergame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.*;

public class Hazard {
    private Rectangle rec;
    private char color; //'r' or 'b'

    public Hazard(int x, int y, int height, int width) {
        rec = new Rectangle();
        rec.width = width;
        rec.height = height;
        rec.x = x;
        rec.y = y;
    }

    public Hazard(int x, int y, int height, int width, char color) {
        rec = new Rectangle();
        rec.width = width;
        rec.height = height;
        rec.x = x;
        rec.y = y;
        this.color = color;
    }

    public Rectangle getRec() {
        return rec;
    }
    public char getColor() {
        return color;
    }



}
