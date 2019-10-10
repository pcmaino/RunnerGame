package com.mygdx.runnergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;


public class HazardHandler {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    ArrayList<Hazard> hazards;
    Texture bhazard_tx;
    Texture rhazard_tx;

    int hazardSpeed;
    float timeSinceLastWave; //keeps track of the time since the last wave was drawn, in seconds
    float waveSpawnSpeed;

    public HazardHandler(SpriteBatch batch, OrthographicCamera camera) {
        this.batch = batch;
        this.camera = camera;
        this.hazards = new ArrayList<>();

        hazardSpeed = 5;
        timeSinceLastWave = 0;
        waveSpawnSpeed = 3;

        bhazard_tx = new Texture("hazard_b.png");
        rhazard_tx = new Texture("hazard_r.png");

        spawnHazards();
    }



    public void drawHazards() {
        for(Hazard h:hazards) {
            if(h.getColor() == 'r') {
                batch.draw(rhazard_tx, h.getRec().x, h.getRec().y);
            }
            if(h.getColor() == 'b') {
                batch.draw(bhazard_tx, h.getRec().x, h.getRec().y);
            }
        }

        timeSinceLastWave += Gdx.graphics.getDeltaTime(); //returns the time since the last frame was drawn in seconds
    }

    public void spawnHazards() {


    }

    //remember! if any hazard has y <0, delete it.
    public void moveHazards() {

    }

    //will raise both the spawn speed and the movement speed
    public void raiseSpeeds() {

    }
}
