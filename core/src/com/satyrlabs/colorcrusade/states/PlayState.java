package com.satyrlabs.colorcrusade.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.satyrlabs.colorcrusade.ColorCrusade;
import com.satyrlabs.colorcrusade.sprites.Rocket;

/**
 * Created by mhigh on 6/27/2017.
 */

public class PlayState extends State {

    private Rocket rocket;


    public PlayState(GameStateManager gsm){
        super(gsm);
        rocket = new Rocket(50, 100);
        cam.setToOrtho(false, ColorCrusade.WIDTH / 2, ColorCrusade.HEIGHT / 2);
    }

    @Override
    protected void handleInput(){

    }

    @Override
    public void update(float dt){
        handleInput();
        rocket.update(dt);
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(rocket.getTexture(), rocket.getPosition().x, rocket.getPosition().y);
        sb.end();

    }

    @Override
    public void dispose(){

    }
}
