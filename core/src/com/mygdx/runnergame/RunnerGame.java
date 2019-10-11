package com.mygdx.runnergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.*;

import java.util.ArrayList;

public class RunnerGame extends ApplicationAdapter {


	SpriteBatch batch;			//draws our sprites
	private OrthographicCamera camera;		//camera object

	Vector3 touchPos;
	Texture roundboi_tx;		//texture files
	Texture background_tx;
	Texture bhazard_tx;
	Texture rhazard_tx;

	private Rectangle roundboi_rc; //rectangle object for roundboi
	private laneStates roundboi_lane;

	private HazardHandler hh;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		roundboi_tx = new Texture("roundboi.png");
		background_tx = new Texture("runner_screen.png");
		bhazard_tx = new Texture("hazard_b.png");
		rhazard_tx = new Texture("hazard_r.png");

		touchPos = new Vector3();

		roundboi_rc = new Rectangle();
		roundboi_rc.x = Gdx.graphics.getWidth()/2 -64/2; //accounts for roundboi's size
		roundboi_rc.y = Gdx.graphics.getHeight()/5;
		roundboi_rc.width = roundboi_tx.getWidth();
		roundboi_rc.height = roundboi_tx.getHeight();

		roundboi_lane = laneStates.CENTER;

		hh = new HazardHandler(batch, camera);
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.1f, .8f);		//sets screen to a color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		//clears screen
		camera.update();
		batch.setProjectionMatrix(camera.combined);		//aligns the spritebatch with the camera's matrix

		batch.begin();
		batch.draw(background_tx, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hh.drawHazards();
		batch.draw(roundboi_tx, roundboi_rc.x, roundboi_rc.y);
		batch.end();

		inputHandler();
		hh.moveHazards();
		hh.spawnHazards();
	}

	laneStates inputHandler() {
		//input handling
		if(Gdx.input.justTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);		//syncs vector to cameras matrix

			//Checks for seconds
			if(roundboi_lane == laneStates.CENTER) {
				if(touchPos.x < Gdx.graphics.getWidth()/2) {
					System.out.println("Left Press");

					roundboi_rc.x = Gdx.graphics.getWidth()/6 -64/2;;
					System.out.println(roundboi_rc.x);
					roundboi_lane = laneStates.LEFT;
				}
				else if(touchPos.x > Gdx.graphics.getWidth()/2) {
					System.out.println("Right Press");

					roundboi_rc.x = 5*(Gdx.graphics.getWidth()/6) -64/2;
					System.out.println(roundboi_rc.x);
					roundboi_lane = laneStates.RIGHT;
				}
			}
			else if( (roundboi_lane == laneStates.LEFT && touchPos.x > Gdx.graphics.getWidth()/2) ||
					(roundboi_lane == laneStates.RIGHT && touchPos.x < Gdx.graphics.getWidth()/2) ) {
				System.out.println("Side Press");
				roundboi_rc.x = Gdx.graphics.getWidth()/2 -64/2;
				System.out.println(roundboi_rc.x);
				roundboi_lane = laneStates.CENTER;
			}
		}

		return roundboi_lane;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		roundboi_tx.dispose();
		//add the rest of the disposes
	}


}
