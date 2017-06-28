package com.satyrlabs.colorcrusade.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.satyrlabs.colorcrusade.ColorCrusade;
import com.satyrlabs.colorcrusade.sprites.BlockBlue;
import com.satyrlabs.colorcrusade.sprites.BlockRed;
import com.satyrlabs.colorcrusade.sprites.Rocket;

/**
 * Created by mhigh on 6/27/2017.
 */

public class PlayState extends State {
    private static final int BLOCK_SPACING = 125;
    private static final int BLOCK_COUNT = 4;

    private static final int BLOCK_COUNT_BLUE = 4;

    private Rocket rocket;
    private Texture bg;

    private int colorToggle = 1;

    private Array<BlockRed> blocksRed;
    private Array<BlockBlue> blocksBlue;

    public PlayState(GameStateManager gsm){
        super(gsm);
        rocket = new Rocket(50, 50);
        cam.setToOrtho(false, ColorCrusade.WIDTH / 2, ColorCrusade.HEIGHT / 2);
        bg = new Texture("background.png");

        blocksRed = new Array<BlockRed>();
        blocksBlue = new Array<BlockBlue>();

        for (int i = 1; i <= BLOCK_COUNT; i++){
            blocksRed.add(new BlockRed(i * (BLOCK_SPACING + BlockRed.BLOCK_WIDTH)));
        }

        for (int i = 1; i <= BLOCK_COUNT_BLUE; i++){
            blocksBlue.add(new BlockBlue(i * (BLOCK_SPACING + BlockBlue.BLOCK_WIDTH)));
        }


    }

    @Override
    protected void handleInput(){
        if(Gdx.input.justTouched()){
            //TODO change colors here
            if(colorToggle == 1){
                colorToggle = 2;
            } else if(colorToggle == 2){
                colorToggle = 1;
            }
        }

    }

    @Override
    public void update(float dt){
        handleInput();
        rocket.update(dt);
        cam.position.y = rocket.getPosition().y + 50;

        for(int i = 0; i < blocksRed.size; i++){
            BlockRed blockRed = blocksRed.get(i);

            if(cam.position.y - (cam.viewportHeight / 2) > blockRed.getPosBlockRed().y + blockRed.getBlockRed().getWidth()){
                blockRed.reposition(blockRed.getPosBlockRed().y + ((BlockRed.BLOCK_WIDTH + BLOCK_SPACING) * BLOCK_COUNT));
            }

            //Check if each block is touching the player
            if(blockRed.collides(rocket.getBounds()) && colorToggle == 2)
                gsm.set(new PlayState(gsm)); //restart the game
        }

        for(int i = 0; i < blocksBlue.size; i++){
            BlockBlue blockBlue = blocksBlue.get(i);

            if(cam.position.y - (cam.viewportHeight / 2) > blockBlue.getPosBlockBlue().y + blockBlue.getBlockBlue().getWidth()){
                blockBlue.reposition(blockBlue.getPosBlockBlue().y + ((BlockBlue.BLOCK_WIDTH + BLOCK_SPACING) * BLOCK_COUNT));
            }

            if(blockBlue.collides(rocket.getBounds()) && colorToggle == 1)
                gsm.set(new PlayState(gsm)); //restart the game
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, cam.position.y - (cam.viewportHeight / 2));
        sb.draw(rocket.getTexture(), rocket.getPosition().x, rocket.getPosition().y);
        for(BlockRed blockRed : blocksRed){
            sb.draw(blockRed.getBlockRed(), blockRed.getPosBlockRed().x, blockRed.getPosBlockRed().y);
        }
        for(BlockBlue blockBlue : blocksBlue){
            sb.draw(blockBlue.getBlockBlue(), blockBlue.getPosBlockBlue().x, blockBlue.getPosBlockBlue().y);
        }
        sb.end();

    }

    @Override
    public void dispose(){
        bg.dispose();
        rocket.dispose();
        for(BlockRed blockRed : blocksRed){
            blockRed.dispose();
        }
        for(BlockBlue blockBlue : blocksBlue){
            blockBlue.dispose();
        }

    }
}
