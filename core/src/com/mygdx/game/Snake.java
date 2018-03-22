package com.mygdx.game;



import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Snake extends Game {
	
	SpriteBatch batch;
	
	
	@Override
	public void create() {	
		batch=new SpriteBatch();
		try {
			this.setScreen(new MainGameScreen(this));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		super.render();
	}

	


	public void pause() {

	}

	@Override
	public void dispose() {
		batch.dispose();

	}
}
