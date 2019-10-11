package com.mygdx.runnergame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.*;

import java.awt.Color;
import 	java.util.Random;

public class Hazard {
    private Rectangle rec;
    private char color; //'r' or 'b'

    public Hazard(int x, int y, int height, int width) {
        rec = new Rectangle();
        rec.width = width;
        rec.height = height;
        rec.x = x;
        rec.y = y;
        if( (new Random()).nextInt(2)  == 0)
            this.color = 'r';
        else
            this.color = 'b';
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
