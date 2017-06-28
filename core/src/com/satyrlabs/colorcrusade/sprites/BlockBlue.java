package com.satyrlabs.colorcrusade.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by mhigh on 6/27/2017.
 */

public class BlockBlue {

    public static final int BLOCK_WIDTH = 52;

    private static final int FLUCTUATION = 200;

    private Texture blockBlue;
    private Vector2 posBlockBlue;
    private Rectangle boundsBlockBlue;
    private Random rand;

    public BlockBlue(float y){
        blockBlue = new Texture("block_blue.png");
        rand = new Random();

        posBlockBlue = new Vector2(rand.nextInt(FLUCTUATION), y);

        boundsBlockBlue = new Rectangle(posBlockBlue.x, posBlockBlue.y, blockBlue.getWidth(), blockBlue.getHeight());
        boundsBlockBlue.setPosition(posBlockBlue.x, posBlockBlue.y);
    }

    public Texture getBlockBlue(){
        return blockBlue;
    }

    public Vector2 getPosBlockBlue(){
        return posBlockBlue;
    }

    public void reposition(float y){
        posBlockBlue.set(rand.nextInt(FLUCTUATION), y);
        boundsBlockBlue.setPosition(posBlockBlue.x, posBlockBlue.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsBlockBlue);
    }

    public void dispose(){
        blockBlue.dispose();
    }
}
