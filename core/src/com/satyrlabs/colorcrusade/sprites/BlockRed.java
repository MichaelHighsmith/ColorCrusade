package com.satyrlabs.colorcrusade.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by mhigh on 6/27/2017.
 */

public class BlockRed {

    public static final int BLOCK_WIDTH = 52;  //maybe change this based upon block size

    private static final int FLUCTUATION = 130;
    private static final int BLOCK_GAP = 100;

    private Texture blockRed;
    private Vector2 posBlockRed;
    private Rectangle boundsBlockRed;
    private Random rand;

    public BlockRed(float y){
        blockRed = new Texture("block.png");
        rand = new Random();

        posBlockRed = new Vector2(rand.nextInt(FLUCTUATION) + BLOCK_GAP, y);

        boundsBlockRed = new Rectangle(posBlockRed.x, posBlockRed.y, blockRed.getWidth(), blockRed.getHeight());
        boundsBlockRed.setPosition(posBlockRed.x, posBlockRed.y);
    }

    public Texture getBlockRed(){
        return blockRed;
    }

    public Vector2 getPosBlockRed(){
        return posBlockRed;
    }

    public void reposition(float y){
        posBlockRed.set(rand.nextInt(FLUCTUATION) + BLOCK_GAP, y);
        boundsBlockRed.setPosition(posBlockRed.x, posBlockRed.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsBlockRed);
        //TODO adjust this or statement to take into account whether the color variable is set to 1 or 2 (blue of red) for collision)
    }

    public void dispose(){
        blockRed.dispose();
    }
}
