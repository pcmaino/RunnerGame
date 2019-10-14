package com.mygdx.runnergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.util.ArrayList;
import java.util.Iterator;


public class HazardHandler {
    private SpriteBatch batch;
    private ArrayList<Hazard> hazards;
    private Texture bhazard_tx;
    private Texture rhazard_tx;

    private  int hazardSpeed;
    private float timeSinceLastWave; //keeps track of the time since the last wave was drawn, in seconds
    private float waveSpawnSpeed;

    public HazardHandler(SpriteBatch batch) {
        this.batch = batch;
        this.hazards = new ArrayList<>();

        hazardSpeed = 10;
        timeSinceLastWave = 3;
        waveSpawnSpeed = 3;

        bhazard_tx = new Texture("hazard_b.png");
        rhazard_tx = new Texture("hazard_r.png");

        spawnHazards();
    }


    public ArrayList<Hazard> getHazards() {
        return hazards;
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
        if(timeSinceLastWave >= waveSpawnSpeed) {
            Hazard n1 = new Hazard(Gdx.graphics.getWidth()/2 -bhazard_tx.getWidth()/2,
                    Gdx.graphics.getHeight(),
                    bhazard_tx.getHeight(),
                    bhazard_tx.getWidth()
                    );


            Hazard n2 = new Hazard(5*(Gdx.graphics.getWidth()/6) -bhazard_tx.getWidth()/2,
                    Gdx.graphics.getHeight(),
                    bhazard_tx.getHeight(),
                    bhazard_tx.getWidth()
                    );


            //handler for making sure all 3 hazards arent the same
            Hazard n3 = null;
            if(n2.getColor() == 'r' && n1.getColor() == 'r') {
                n3 = new Hazard((Gdx.graphics.getWidth()/6) -bhazard_tx.getWidth()/2,
                        Gdx.graphics.getHeight(),
                        bhazard_tx.getHeight(),
                        bhazard_tx.getWidth(),
                        'b'
                );
            }
            else if (n2.getColor() == 'b' && n1.getColor() == 'b') {
                n3 = new Hazard((Gdx.graphics.getWidth()/6) -bhazard_tx.getWidth()/2,
                        Gdx.graphics.getHeight(),
                        bhazard_tx.getHeight(),
                        bhazard_tx.getWidth(),
                        'r'
                );
            }
            else {
                n3 = new Hazard((Gdx.graphics.getWidth()/6) -bhazard_tx.getWidth()/2,
                        Gdx.graphics.getHeight(),
                        bhazard_tx.getHeight(),
                        bhazard_tx.getWidth()
                );
            }

            hazards.add(n1);
            hazards.add(n2);
            hazards.add(n3);
            timeSinceLastWave = 0;
        }


    }

    //remember! if any hazard has y <0, delete it.
    public void moveHazards() {
        Iterator i = hazards.iterator();
        while(i.hasNext()) {
            Hazard h = (Hazard) i.next();
            h.getRec().y -= hazardSpeed;

            if(h.getRec().y <= 0) {
                i.remove();
            }
        }

        checkRaiseSpeeds();
    }

    //will raise both the spawn speed and the movement speed
    public void checkRaiseSpeeds() {
        if(RunnerGame.score >= hazardSpeed) {
            System.out.println("SPEEDING UP!");
            hazardSpeed +=6;
        }
        if(RunnerGame.score >= 30 && waveSpawnSpeed != 2) {
            waveSpawnSpeed = 2;
        }

        if(RunnerGame.score >= 60 && waveSpawnSpeed != 1) {
            waveSpawnSpeed = 1;
        }

    }
}
