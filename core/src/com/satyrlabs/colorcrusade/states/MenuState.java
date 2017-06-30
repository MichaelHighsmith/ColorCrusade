package com.satyrlabs.colorcrusade.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.satyrlabs.colorcrusade.ColorCrusade;

/**
 * Created by mhigh on 6/27/2017.
 */

public class MenuState extends State {

    private Texture background;
    private Texture playBtn;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, ColorCrusade.WIDTH, ColorCrusade.HEIGHT);
        background = new Texture("backgroundmenu.png");
        playBtn = new Texture("playbtn.png");


    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            //pop menu state and push new playstate on click
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.end();
    }

    @Override
    public void dispose(){
        background.dispose();
        playBtn.dispose();
    }
}
