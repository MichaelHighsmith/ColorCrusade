package com.satyrlabs.colorcrusade;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.satyrlabs.colorcrusade.states.GameStateManager;
import com.satyrlabs.colorcrusade.states.MenuState;

public class ColorCrusade extends ApplicationAdapter {

	public static final int WIDTH = 440;
	public static final int HEIGHT = 800;

	public static final String TITLE = "ColorCrusade";
	private GameStateManager gsm;

	private SpriteBatch batch;

	AdHandler handler;
	boolean toggle;

	public ColorCrusade(AdHandler handler){
		this.handler = handler;
	}

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		if(Gdx.input.justTouched()){
			handler.showAds(toggle);
			toggle = !toggle;
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Tells us the difference between rener times
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
