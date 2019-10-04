package com.mygdx.runnergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.*;

import java.util.ArrayList;


public class HazardHandler {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    ArrayList<Rectangle> hazards;

    public HazardHandler(SpriteBatch batch, OrthographicCamera camera) {
        this.batch = batch;
        this.camera = camera;
        this.hazards = new ArrayList<>();
    }

    public void spawnHazards() {


    }
}
