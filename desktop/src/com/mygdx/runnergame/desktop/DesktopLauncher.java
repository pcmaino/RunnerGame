package com.mygdx.runnergame.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.runnergame.RunnerGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Runner";
		config.width = Gdx.graphics.getWidth();
		config.height = Gdx.graphics.getHeight();

		new LwjglApplication(new RunnerGame(), config);
	}
}
