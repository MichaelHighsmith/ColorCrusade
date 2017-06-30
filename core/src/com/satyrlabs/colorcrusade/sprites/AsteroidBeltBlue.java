package com.satyrlabs.colorcrusade.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by mhigh on 6/29/2017.
 */

public class AsteroidBeltBlue {

    private Texture asteroidBlue;
    private Vector2 posAsteroidBlue;
    private Rectangle boundsAsteroidBlue;

    public AsteroidBeltBlue(float y){
        asteroidBlue = new Texture("asteroid_belt_blue.png");

        posAsteroidBlue = new Vector2(0, y);

        boundsAsteroidBlue = new Rectangle(posAsteroidBlue.x, posAsteroidBlue.y, asteroidBlue.getWidth(), asteroidBlue.getHeight());
        boundsAsteroidBlue.setPosition(posAsteroidBlue.x, posAsteroidBlue.y);
    }

    public Texture getAsteroidBlue(){
        return asteroidBlue;
    }

    public Vector2 getPosAsteroidBlue(){
        return posAsteroidBlue;
    }

    public void reposition(float y){
        posAsteroidBlue.set(0, y);
        boundsAsteroidBlue.setPosition(posAsteroidBlue.x, posAsteroidBlue.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsAsteroidBlue);
    }

    public void dispose(){
        asteroidBlue.dispose();
    }

}
