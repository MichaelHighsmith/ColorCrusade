package com.satyrlabs.colorcrusade.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.satyrlabs.colorcrusade.ColorCrusade;
import com.satyrlabs.colorcrusade.sprites.BlockBlue;
import com.satyrlabs.colorcrusade.sprites.BlockRed;
import com.satyrlabs.colorcrusade.sprites.Coin;
import com.satyrlabs.colorcrusade.sprites.Rocket;

import java.util.Random;

/**
 * Created by mhigh on 6/27/2017.
 */

public class PlayState extends State {
    private static final int BLOCK_SPACING_RED = 60;
    private static final int BLOCK_COUNT = 4;

    private static final int BLOCK_SPACING_BLUE = 80;
    private static final int BLOCK_COUNT_BLUE = 4;

    private static final int COIN_COUNT = 10;
    private static final int COIN_SPACING = 50;
    private Random rand;

    private Rocket rocket;
    private Texture bg;

    private int colorToggle = 1;

    private Array<BlockRed> blocksRed;
    private Array<BlockBlue> blocksBlue;
    private Array<Coin> coins;

    public PlayState(GameStateManager gsm){
        super(gsm);
        rocket = new Rocket(50, 20);
        cam.setToOrtho(false, ColorCrusade.WIDTH / 2, ColorCrusade.HEIGHT / 2);
        bg = new Texture("background.png");

        blocksRed = new Array<BlockRed>();
        blocksBlue = new Array<BlockBlue>();
        coins = new Array<Coin>();

        for (int i = 1; i <= BLOCK_COUNT; i++){
            rand = new Random();
            blocksRed.add(new BlockRed(i * (BLOCK_SPACING_RED + BlockRed.BLOCK_WIDTH)));
        }

        //TODO add a random here to space out the spawns
        for (int i = 1; i <= BLOCK_COUNT_BLUE; i++){
            blocksBlue.add(new BlockBlue(i * (BLOCK_SPACING_BLUE + BlockBlue.BLOCK_WIDTH)));
        }

        //coins
        for (int i = 1; i <= COIN_COUNT; i++){
            coins.add(new Coin(i * (COIN_SPACING)));
        }


    }

    @Override
    protected void handleInput(){
        if(Gdx.input.justTouched()){
            //Change the active color
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
        cam.position.y = rocket.getPosition().y + 170;

        for(int i = 0; i < blocksRed.size; i++){
            BlockRed blockRed = blocksRed.get(i);

            //If a red block runs off screen, reposition it towards the top of the screen
            if(cam.position.y - (cam.viewportHeight / 2) > blockRed.getPosBlockRed().y + blockRed.getBlockRed().getWidth()){
                blockRed.reposition(blockRed.getPosBlockRed().y + ((BlockRed.BLOCK_WIDTH + BLOCK_SPACING_RED) * BLOCK_COUNT));
            }

            //Check if each red block is touching the player
            if(blockRed.collides(rocket.getBounds()) && colorToggle == 2)
                gsm.set(new PlayState(gsm)); //restart the game
        }

        for(int i = 0; i < blocksBlue.size; i++){
            BlockBlue blockBlue = blocksBlue.get(i);

            //If a blue block runs off screen, reposition it towards the top of the screen
            if(cam.position.y - (cam.viewportHeight / 2) > blockBlue.getPosBlockBlue().y + blockBlue.getBlockBlue().getWidth()){
                blockBlue.reposition(blockBlue.getPosBlockBlue().y + ((BlockBlue.BLOCK_WIDTH + BLOCK_SPACING_BLUE) * BLOCK_COUNT));
            }

            //Check if each blue block is touching the player
            if(blockBlue.collides(rocket.getBounds()) && colorToggle == 1)
                gsm.set(new PlayState(gsm)); //restart the game
        }

        //Check for coin collisions
        for(int i = 0; i < coins.size; i++){
            Coin coin = coins.get(i);

            //If coins fall off screen, bounce them to the top
            if(cam.position.y - (cam.viewportHeight / 2) > coin.getPosCoin().y + coin.getCoin().getWidth()){
                coin.reposition(coin.getPosCoin().y + (COIN_SPACING * COIN_COUNT));
            }

            //If the player collids with a coin, bounce it to the top TODO: update the score too
            if(coin.collides(rocket.getBounds())){
                coin.reposition(coin.getPosCoin().y + (COIN_SPACING * COIN_COUNT));
            }
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, cam.position.y - (cam.viewportHeight / 2));
        //change the rockets color depending on the color selected
        if(colorToggle == 1){
            sb.setColor(1,0,0,1);
        } else if (colorToggle == 2){
            sb.setColor(0, 0, 1, 1);
        }
        sb.draw(rocket.getTexture(), rocket.getPosition().x, rocket.getPosition().y);
        //set the color back to white
        sb.setColor(1,1,1,1);
        for(BlockRed blockRed : blocksRed){
            sb.draw(blockRed.getBlockRed(), blockRed.getPosBlockRed().x, blockRed.getPosBlockRed().y);
        }
        for(BlockBlue blockBlue : blocksBlue){
            sb.draw(blockBlue.getBlockBlue(), blockBlue.getPosBlockBlue().x, blockBlue.getPosBlockBlue().y);
        }
        for(Coin coin : coins){
            sb.draw(coin.getCoin(), coin.getPosCoin().x, coin.getPosCoin().y);
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
        for(Coin coin : coins){
            coin.dispose();
        }

    }
}
