package com.satyrlabs.colorcrusade.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by mhigh on 6/28/2017.
 */

public class Coin {

    private static final int FLUCTUATION = 200;

    private Vector2 posCoin;
    private Rectangle boundsCoin;
    Texture coin;
    private Random rand;

    public Coin(float y){
        rand = new Random();
        coin = new Texture("coin.png");
        posCoin = new Vector2(rand.nextInt(FLUCTUATION), y);
        boundsCoin = new Rectangle(posCoin.x, posCoin.y, coin.getWidth(), coin.getHeight());
        boundsCoin.setPosition(posCoin.x, posCoin.y);

    }

    public Texture getCoin(){
        return coin;
    }

    public Vector2 getPosCoin(){
        return posCoin;
    }

    public void reposition(float y){
        posCoin.set(rand.nextInt(FLUCTUATION), y);
        boundsCoin.setPosition(posCoin.x, posCoin.y);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(boundsCoin);
    }

    public void dispose(){
        coin.dispose();
    }

}
