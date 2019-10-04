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
	private boolean isMovingRight;		//controls steady movement for roundboi
	private boolean isMovingLeft;
	private int roundboiSpeed = Gdx.graphics.getWidth()/18;

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

		isMovingRight = false;
		isMovingLeft = false;

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
		batch.draw(roundboi_tx, roundboi_rc.x, roundboi_rc.y);

		batch.end();

		//input handling
		if(Gdx.input.isTouched() && !isMovingLeft  && !isMovingRight) {

			//move roundboi along the 3 tracks, not allowing them to fall off
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);		//syncs vector to cameras matrix
			if(touchPos.x > Gdx.graphics.getWidth()/2) {
				if(roundboi_rc.x != (Gdx.graphics.getWidth()* (5/6)) -64/2)  {
					isMovingRight = true;
				}
			}
			else if(touchPos.x < Gdx.graphics.getWidth()/2) {
				if(roundboi_rc.x != (Gdx.graphics.getWidth()* (1/6)) -64/2)  {
					isMovingLeft = true;
				}
			}
			//roundboi_rc.x = touchPos.x - 64 / 2;
		}

		//incrementer of roundboi's movement
		if(isMovingLeft) {
			roundboi_rc.x -= roundboiSpeed;


		}
		else if(isMovingRight) {

		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		roundboi_tx.dispose();
	}


}
