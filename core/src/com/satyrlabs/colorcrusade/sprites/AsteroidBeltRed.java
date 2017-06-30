package com.satyrlabs.colorcrusade.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by mhigh on 6/29/2017.
 */
//A full wall of asteroids so the player must change color.
public class AsteroidBeltRed {

    private Texture asteroidRed;
    private Vector2 posAsteroidRed;
    private Rectangle boundsAsteroidRed;

    public AsteroidBeltRed(float y){
        asteroidRed = new Texture("asteroid_belt_red.png");

        posAsteroidRed = new Vector2(0, y + 2000);

        boundsAsteroidRed = new Rectangle(posAsteroidRed.x, posAsteroidRed.y, asteroidRed.getWidth(), asteroidRed.getHeight());
        boundsAsteroidRed.setPosition(posAsteroidRed.x, posAsteroidRed.y);
    }

    public Texture getAsteroidRed(){
        return asteroidRed;
    }

    public Vector2 getPosAsteroidRed(){
        return posAsteroidRed;
    }

    public void reposition(float y){
        posAsteroidRed.set(0, y);
        boundsAsteroidRed.setPosition(posAsteroidRed.x, posAsteroidRed.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsAsteroidRed);
    }

    public void dispose(){
        asteroidRed.dispose();
    }
}
