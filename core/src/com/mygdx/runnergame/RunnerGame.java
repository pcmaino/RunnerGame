package com.mygdx.runnergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Random;

public class RunnerGame extends Game {


	private SpriteBatch batch;			//draws our sprites
	private OrthographicCamera camera;		//camera object

	private Vector3 touchPos;
	private Texture roundboi_tx;		//texture files
    private Texture roundboir_tx;
    private Texture roundboib_tx;
	private Texture background_tx;
	private Texture bhazard_tx;
	private Texture rhazard_tx;
	private Sound alert;

	private Rectangle roundboi_rc; //rectangle object for roundboi
	private laneStates roundboi_lane;
	private char roundboi_color;

	private HazardHandler hh;

	public static float score;
	private float switchTimer;
	boolean isGameOver;
	boolean isGameStart;
	public static float finalScore;

	BitmapFont font1;




	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//TEXTURES
		roundboi_tx = new Texture("roundboi.png");
		roundboir_tx = new Texture("roundboi_r.png");
        roundboib_tx   = new Texture("roundboi_b.png");
		background_tx = new Texture("runner_screen.png");
		bhazard_tx = new Texture("hazard_b.png");
		rhazard_tx = new Texture("hazard_r.png");
		alert = Gdx.audio.newSound(Gdx.files.internal("sound.mp3"));

		//TOUCH HOLDER
		touchPos = new Vector3();

		//ROUNDBOI VARS, move into own class if you have time
		roundboi_rc = new Rectangle();
		roundboi_rc.x = Gdx.graphics.getWidth()/2 -128/2; //accounts for roundboi's size
		roundboi_rc.y = Gdx.graphics.getHeight()/5;
		roundboi_rc.width = roundboi_tx.getWidth();
		roundboi_rc.height = roundboi_tx.getHeight();
		roundboi_lane = laneStates.CENTER;
		roundboi_color = 'b'; //can be b or r

		//HAZARD HANDLER
		hh = new HazardHandler(batch);

		//SCORE
		score = 0;
		finalScore = -1;
		isGameOver = false;
		isGameStart = true;

		//SWITCH TEXT TIMER
        switchTimer = 0;

		//FONTS
		font1 = new BitmapFont(false);
		font1.setColor(Color.BLACK);
		font1.getData().setScale(3);
	}

	//LOOK INTO IMPLEMENTING GAME SCREENS NEXT TIME
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_ALPHA_BITS);
		camera.update();
		batch.enableBlending();
		batch.setProjectionMatrix(camera.combined);		//aligns the spritebatch with the camera's matrix


		if(isGameOver) {
			batch.begin();
			batch.draw(background_tx, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font1.setColor(Color.BLACK);
			font1.draw(batch, ">GAME OVER", 20, 200, 150f, Align.left,true);
			font1.draw(batch, ">Score: "+ Math.round(finalScore) +"\n>Touch to reset.", 20, 800, 800f, Align.left,true);
			batch.end();
			if(Gdx.input.justTouched()) {
				System.out.println("RESTARTING");
                create();
			}
			return;
		}

		if(isGameStart) {
			batch.begin();
			batch.draw(background_tx, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font1.setColor(Color.BLACK);
			font1.draw(batch, ">SwapRun", 20, 200, 250f, Align.left,true);
			font1.draw(batch, ">Your color will swap. \n>Avoid opposite color.\n>Tap left and right.", 20, 1000, 300f, Align.left,true);
			font1.draw(batch, ">Tap to start.", 20, 300, 300f, Align.left,true);
			batch.end();
			if(Gdx.input.justTouched()) {
				System.out.println("RESTARTING");
				isGameStart = false;
			}
			return;
		}

		batch.begin();
		{//DRAW SECTION
			batch.draw(background_tx, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			hh.drawHazards();

			if (roundboi_color == 'b') {
				batch.draw(roundboib_tx, roundboi_rc.x, roundboi_rc.y);
				font1.setColor(Color.RED);
			}
			else {
				batch.draw(roundboir_tx, roundboi_rc.x, roundboi_rc.y);
				font1.setColor(Color.BLUE);

			}

			if(switchTimer > 0) {
				font1.draw(batch, "SWITCH", Gdx.graphics.getWidth()-200, 50, 180f,Align.center, true);
				switchTimer -= Gdx.graphics.getDeltaTime();
			}

			font1.setColor(Color.BLACK);
			font1.draw(batch, ">score: " + Math.round(score), 20, Gdx.graphics.getHeight()-30, 150f, Align.left,true);
		}//DRAW SECTION
		batch.end();

	    //INPUT/MOVEMENT UPDATERS
		inputHandler();
		hh.moveHazards();
		hh.spawnHazards();
		switchHandler();
		collideDetector();



		//TIMER/SCORE CONTROL
		score += Gdx.graphics.getDeltaTime();
		if(switchTimer <0) {
			switchTimer = 0;
			if (roundboi_color == 'r')
				roundboi_color = 'b';
			else
				roundboi_color = 'r';
			alert.play(.5f);
		}
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

					roundboi_rc.x = Gdx.graphics.getWidth()/6 -128/2;;
					System.out.println(roundboi_rc.x);
					roundboi_lane = laneStates.LEFT;
				}
				else if(touchPos.x > Gdx.graphics.getWidth()/2) {
					System.out.println("Right Press");

					roundboi_rc.x = 5*(Gdx.graphics.getWidth()/6) -128/2;
					System.out.println(roundboi_rc.x);
					roundboi_lane = laneStates.RIGHT;
				}
			}
			else if( (roundboi_lane == laneStates.LEFT && touchPos.x > Gdx.graphics.getWidth()/2) ||
					(roundboi_lane == laneStates.RIGHT && touchPos.x < Gdx.graphics.getWidth()/2) ) {
				System.out.println("Side Press");
				roundboi_rc.x = Gdx.graphics.getWidth()/2 -128/2;
				System.out.println(roundboi_rc.x);
				roundboi_lane = laneStates.CENTER;
			}
		}

		return roundboi_lane;
	}

    // 25% chance of switching, if switch happens this function will add 1 sec to the switch timer
    // and switch the color, which causes the "SWITCH" text to appear
	private void switchHandler() {
		if(switchTimer >0)
			return;
        if( (int) score %2 + (new Random()).nextInt(5) == 0) {
			switchTimer = 1;
			alert.play();
        }
    }

    private void collideDetector() {
		for(Hazard h:hh.getHazards())
			if(roundboi_color == 'b' && roundboi_rc.overlaps(h.getRec()) &&h.getColor() == 'r'
					|| roundboi_color == 'r' && roundboi_rc.overlaps(h.getRec()) &&h.getColor() == 'b') {

				isGameOver = true;
				finalScore = score;
				System.out.println("GAME OVER: Final score is " + finalScore);
			}

	}


	
	@Override
	public void dispose () {
		batch.dispose();
		roundboi_tx.dispose();
		roundboir_tx.dispose();
		roundboib_tx.dispose();
		background_tx.dispose();
		bhazard_tx.dispose();
		rhazard_tx.dispose();
		font1.dispose();
		alert.dispose();
	}


}
