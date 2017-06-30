package com.satyrlabs.colorcrusade.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.satyrlabs.colorcrusade.ColorCrusade;
import com.satyrlabs.colorcrusade.sprites.Alien;
import com.satyrlabs.colorcrusade.sprites.AsteroidBeltBlue;
import com.satyrlabs.colorcrusade.sprites.AsteroidBeltRed;
import com.satyrlabs.colorcrusade.sprites.BlockBlue;
import com.satyrlabs.colorcrusade.sprites.BlockRed;
import com.satyrlabs.colorcrusade.sprites.Coin;
import com.satyrlabs.colorcrusade.sprites.Rocket;

import java.util.Random;

/**
 * Created by mhigh on 6/27/2017.
 */

public class PlayState extends State {

    private static final float HUD_MARGIN = 20.0f;
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 480.0f;

    private static final int BLOCK_SPACING_RED = 90;
    private static final int BLOCK_COUNT = 6;

    private static final int BLOCK_SPACING_BLUE = 130;
    private static final int BLOCK_COUNT_BLUE = 6;

    private static final int COIN_COUNT = 10;
    private static final int COIN_SPACING = 60;
    private Random rand;

    private static final int ASTEROID_SPACING_RED = 4000;
    private static final int ASTEROID_SPACING_BLUE = 4000;

    private static final int ALIEN_SPACING = 3500;

    private Rocket rocket;
    private Texture bg;
    private int score;
    private int highScore;

    private int colorToggle = 1;

    BitmapFont font;

    public static final float TIME_DELAY_GAME_OVER = 3;
    private float timeLeftGameOverDelay;


    private Array<BlockRed> blocksRed;
    private Array<BlockBlue> blocksBlue;
    private Array<Coin> coins;
    private AsteroidBeltRed asteroidBeltRed;
    private AsteroidBeltBlue asteroidBeltBlue;
    private Alien alien;

    public PlayState(GameStateManager gsm){

        super(gsm);

        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        highScore = prefs.getInteger("highScore", 0);

        rocket = new Rocket(50, 20);
        cam.setToOrtho(false, ColorCrusade.WIDTH / 2, ColorCrusade.HEIGHT / 2);
        bg = new Texture("background.png");

        blocksRed = new Array<BlockRed>();
        blocksBlue = new Array<BlockBlue>();
        coins = new Array<Coin>();
        asteroidBeltRed = new AsteroidBeltRed(ASTEROID_SPACING_RED);
        asteroidBeltBlue = new AsteroidBeltBlue(ASTEROID_SPACING_BLUE);
        alien = new Alien(ALIEN_SPACING);

        font = new BitmapFont();

        timeLeftGameOverDelay = 3;


        for (int i = 1; i <= BLOCK_COUNT; i++){
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
            if(!rocket.isCrashed()){
                //Change the active color
                if(colorToggle == 1){
                    colorToggle = 2;
                } else if(colorToggle == 2){
                    colorToggle = 1;
                }
            }

        }

    }

    @Override
    public void update(float dt){
        handleInput();
        rocket.update(dt);
        //TODO update the alien once the user hits a certain height
        alien.update(dt);
        cam.position.y = rocket.getPosition().y + 170;

        for(int i = 0; i < blocksRed.size; i++){
            BlockRed blockRed = blocksRed.get(i);
            //If a red block runs off screen, reposition it towards the top of the screen
            if(cam.position.y - (cam.viewportHeight / 2) > blockRed.getPosBlockRed().y + blockRed.getBlockRed().getWidth()){
                blockRed.reposition(blockRed.getPosBlockRed().y + ((BlockRed.BLOCK_WIDTH + BLOCK_SPACING_RED) * BLOCK_COUNT));
            }
            //Check if each red block is touching the player
            if(blockRed.collides(rocket.getBounds()) && colorToggle == 2){
                //start the explosion countdown and stop the rocket from moving
                timeLeftGameOverDelay -= dt;
                rocket.crashRocket();
                if(timeLeftGameOverDelay < 0){
                    gsm.set(new MenuState(gsm));
                }
            }

        }

        for(int i = 0; i < blocksBlue.size; i++){
            BlockBlue blockBlue = blocksBlue.get(i);
            //If a blue block runs off screen, reposition it towards the top of the screen
            if(cam.position.y - (cam.viewportHeight / 2) > blockBlue.getPosBlockBlue().y + blockBlue.getBlockBlue().getWidth()){
                blockBlue.reposition(blockBlue.getPosBlockBlue().y + ((BlockBlue.BLOCK_WIDTH + BLOCK_SPACING_BLUE) * BLOCK_COUNT));
            }
            //Check if each blue block is touching the player
            if(blockBlue.collides(rocket.getBounds()) && colorToggle == 1){
                //start the explosion countdown and stop the rocket from moving
                timeLeftGameOverDelay -= dt;
                rocket.crashRocket();
                if(timeLeftGameOverDelay < 0){
                    gsm.set(new MenuState(gsm));
                }
            }

        }

        //Check for coin collisions
        for(int i = 0; i < coins.size; i++){
            Coin coin = coins.get(i);
            //If coins fall off screen, bounce them to the top
            if(cam.position.y - (cam.viewportHeight / 2) > coin.getPosCoin().y + coin.getCoin().getWidth()){
                coin.reposition(coin.getPosCoin().y + (COIN_SPACING * COIN_COUNT));
            }
            //If the player collides with a coin, bounce it to the top TODO: update the score too
            if(coin.collides(rocket.getBounds())){
                coin.reposition(coin.getPosCoin().y + (COIN_SPACING * COIN_COUNT));
                score += 2;
                //if new highscore, update it
                if(score >= highScore){
                    highScore = score;
                    Preferences prefs = Gdx.app.getPreferences("My Preferences");
                    prefs.putInteger("highScore", highScore);
                    prefs.flush();
                }
            }
        }

        //Logic for red asteroid belt repositioning/collision
        if(cam.position.y  - (cam.viewportHeight / 2) > asteroidBeltRed.getPosAsteroidRed().y + asteroidBeltRed.getAsteroidRed().getHeight()){
            asteroidBeltRed.reposition(asteroidBeltRed.getPosAsteroidRed().y + ASTEROID_SPACING_RED);
        }
        if(asteroidBeltRed.collides(rocket.getBounds()) && colorToggle == 2){
            //start the explosion countdown and stop the rocket from moving
            timeLeftGameOverDelay -= dt;
            rocket.crashRocket();
            if(timeLeftGameOverDelay < 0){
                gsm.set(new MenuState(gsm));
            }
        }

        //Logic for blue asteroid belt repositioning/collision
        if(cam.position.y  - (cam.viewportHeight / 2) > asteroidBeltBlue.getPosAsteroidBlue().y + asteroidBeltBlue.getAsteroidBlue().getHeight()){
            asteroidBeltBlue.reposition(asteroidBeltBlue.getPosAsteroidBlue().y + ASTEROID_SPACING_BLUE);
        }
        if(asteroidBeltBlue.collides(rocket.getBounds()) && colorToggle == 1){
            //start the explosion countdown and stop the rocket from moving
            timeLeftGameOverDelay -= dt;
            rocket.crashRocket();
            if(timeLeftGameOverDelay < 0){
                gsm.set(new MenuState(gsm));
            }
        }

        //Logic for alien
        if(cam.position.y - (cam.viewportHeight / 2) > alien.getPosAlien().y + alien.getAlien().getRegionHeight()){
            alien.reposition(alien.getPosAlien().y + ALIEN_SPACING);
        }
        if(alien.collides(rocket.getBounds())){
            //start the explosion countdown and stop the rocket from moving
            timeLeftGameOverDelay -= dt;
            rocket.crashRocket();
            alien.crashedIntoAlien();
            if(timeLeftGameOverDelay < 0){
                gsm.set(new MenuState(gsm));
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
            sb.setColor(1,0.2f,0,1);
        } else if (colorToggle == 2){
            sb.setColor(0, 0.3f, 0.7f, 1);
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
        sb.draw(asteroidBeltRed.getAsteroidRed(), asteroidBeltRed.getPosAsteroidRed().x, asteroidBeltRed.getPosAsteroidRed().y);
        sb.draw(asteroidBeltBlue.getAsteroidBlue(), asteroidBeltBlue.getPosAsteroidBlue().x, asteroidBeltBlue.getPosAsteroidBlue().y);
        
        sb.draw(alien.getAlien(), alien.getPosAlien().x, alien.getPosAlien().y);

        font.draw(sb, "Score: " + score, 0, rocket.getPosition().y - 10);
        font.draw(sb, "High Score: " + highScore, ColorCrusade.WIDTH / 4 - 10, rocket.getPosition().y - 10);

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
        font.dispose();
        asteroidBeltRed.dispose();
        asteroidBeltBlue.dispose();
        alien.dispose();
    }

    public boolean isGameOver(){
        return true;
    }
}
