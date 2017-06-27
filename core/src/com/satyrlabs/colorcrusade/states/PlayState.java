package com.satyrlabs.colorcrusade.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.satyrlabs.colorcrusade.ColorCrusade;

/**
 * Created by mhigh on 6/27/2017.
 */

public class PlayState extends State {

    private Texture rocket;

    public PlayState(GameStateManager gsm){
        super(gsm);
        rocket = new Texture("bat.png");
        cam.setToOrtho(false, ColorCrusade.WIDTH / 2, ColorCrusade.HEIGHT / 2);
    }

    @Override
    protected void handleInput(){

    }

    @Override
    public void update(float dt){

    }

    @Override
    public void render(SpriteBatch sb){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(rocket, 50, 50);
        sb.end();

    }

    @Override
    public void dispose(){

    }
}
