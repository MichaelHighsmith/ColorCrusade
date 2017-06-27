package com.satyrlabs.colorcrusade.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.satyrlabs.colorcrusade.ColorCrusade;
import com.satyrlabs.colorcrusade.sprites.Block;
import com.satyrlabs.colorcrusade.sprites.Rocket;

/**
 * Created by mhigh on 6/27/2017.
 */

public class PlayState extends State {
    private static final int BLOCK_SPACING = 125;
    private static final int BLOCK_COUNT = 4;

    private Rocket rocket;
    private Texture bg;

    private Array<Block> blocks;

    public PlayState(GameStateManager gsm){
        super(gsm);
        rocket = new Rocket(50, 50);
        cam.setToOrtho(false, ColorCrusade.WIDTH / 2, ColorCrusade.HEIGHT / 2);
        bg = new Texture("background.png");

        blocks = new Array<Block>();

        for (int i = 1; i <= BLOCK_COUNT; i++){
            blocks.add(new Block(i * (BLOCK_SPACING + Block.BLOCK_WIDTH)));
        }
    }

    @Override
    protected void handleInput(){
        if(Gdx.input.justTouched()){
            //TODO change colors here
        }

    }

    @Override
    public void update(float dt){
        handleInput();
        rocket.update(dt);
        cam.position.y = rocket.getPosition().y + 50;

        for(Block block : blocks){
            if(cam.position.y - (cam.viewportHeight / 2) > block.getPosBlock1().y + block.getBlock1().getWidth()){
                block.reposition(block.getPosBlock1().y + ((Block.BLOCK_WIDTH + BLOCK_SPACING) * BLOCK_COUNT));
            }
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(rocket.getTexture(), rocket.getPosition().x, rocket.getPosition().y);
        for(Block block : blocks){
            sb.draw(block.getBlock1(), block.getPosBlock1().x, block.getPosBlock1().y);
            sb.draw(block.getBlock2(), block.getPosBlock2().x, block.getPosBlock2().y);
        }
        sb.end();

    }

    @Override
    public void dispose(){

    }
}
