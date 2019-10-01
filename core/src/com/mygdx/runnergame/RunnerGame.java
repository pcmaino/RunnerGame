package com.mygdx.runnergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class RunnerGame extends ApplicationAdapter {
	int screen_width = 480;
	int screen_height = 800;
	SpriteBatch batch;			//draws our sprites
	private OrthographicCamera camera;		//camera object
	Texture roundboi_tx;		//texture files
	Texture background_tx;
	Texture bhazard_tx;
	Texture rhazard_tx;
	private Rectangle roundboi_rc;
	private ArrayList<Rectangle> hazard_rc = new ArrayList<>();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		roundboi_tx = new Texture("roundboi.png");
		background_tx = new Texture("runner_screen.png");
		bhazard_tx = new Texture("hazard_b.png");
		rhazard_tx = new Texture("hazard_r.png");

		roundboi_rc = new Rectangle();
		roundboi_rc.x = 800 / 2 - 64 / 2;
		roundboi_rc.y = 20;
		roundboi_rc.width = roundboi_tx.getWidth();
		roundboi_rc.height = roundboi_tx.getHeight();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.1f, .8f);		//sets screen to a color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		//clears screen
		camera.update();
		batch.setProjectionMatrix(camera.combined);		//aligns the spritebatch with the camera's matrix

		batch.begin();
		//batch.draw(background_tx, 0, 0);
		batch.draw(roundboi_tx, roundboi_rc.x, roundboi_rc.y);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		roundboi_tx.dispose();
	}
}
