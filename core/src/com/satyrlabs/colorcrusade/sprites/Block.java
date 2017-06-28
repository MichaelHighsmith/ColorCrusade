package com.satyrlabs.colorcrusade.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by mhigh on 6/27/2017.
 */

public class Block {
    public static final int BLOCK_WIDTH = 52;  //maybe change this based upon block size

    private static final int FLUCTUATION = 130;
    private static final int BLOCK_GAP = 100;

    private Texture block1, block2;
    private Vector2 posBlock1, posBlock2;
    private Rectangle boundsBlock1;
    private Rectangle boundsBlock2;
    private Random rand;

    public Block(float y){
        block1 = new Texture("block.png");
        block2 = new Texture("block_blue.png");
        rand = new Random();

        posBlock1 = new Vector2(rand.nextInt(FLUCTUATION) + BLOCK_GAP, y);
        posBlock2 = new Vector2(posBlock1.x - BLOCK_GAP - block2.getWidth(), y);

        boundsBlock1 = new Rectangle(posBlock1.x, posBlock1.y, block1.getWidth(), block1.getHeight());
        boundsBlock2 = new Rectangle(posBlock2.x, posBlock2.y, block2.getWidth(), block2.getHeight());
        boundsBlock1.setPosition(posBlock1.x, posBlock1.y);
        boundsBlock2.setPosition(posBlock2.x, posBlock2.y);
    }

    public Texture getBlock1(){
        return block1;
    }

    public Texture getBlock2(){
        return block2;
    }

    public Vector2 getPosBlock1(){
        return posBlock1;
    }

    public Vector2 getPosBlock2(){
        return posBlock2;
    }

    public void reposition(float y){
        posBlock1.set(rand.nextInt(FLUCTUATION) + BLOCK_GAP, y);
        posBlock2.set(posBlock1.x - BLOCK_GAP - block2.getWidth(), y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsBlock1) || player.overlaps(boundsBlock2);
        //TODO adjust this or statement to take into account whether the color variable is set to 1 or 2 (blue of red) for collision)
    }

    public void dispose(){
        block1.dispose();
        block2.dispose();
    }
}
