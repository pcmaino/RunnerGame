package com.mygdx.runnergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RunnerGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture roundboi_tx;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		roundboi_tx = new Texture("roundboi.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(roundboi_tx, 0, 0, 50, 80,0,0 , 50, 80,true, false);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		roundboi_tx.dispose();
	}
}
