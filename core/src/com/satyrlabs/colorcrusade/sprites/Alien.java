package com.satyrlabs.colorcrusade.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.satyrlabs.colorcrusade.ColorCrusade;
import com.satyrlabs.colorcrusade.Constants;

import java.util.Random;

/**
 * Created by mhigh on 6/29/2017.
 */

public class Alien {

    private static final int FLUCTUATION = 400;
    private static final int ALIEN_INTRO_HEIGHT = 10000;

    private Texture alien;
    private Vector2 posAlien;
    private Vector2 velocityAlien;
    private int velocity;
    private Rectangle boundsAlien;
    private Animation alienAnimation;
    private Random rand;
    private boolean crashed = false;

    public ParticleEffect alienParticles;

    public Alien(float y){
        alien = new Texture("alienanimation.png");
        alienAnimation = new Animation(new TextureRegion(alien), 3, 0.5f);

        rand = new Random();

        posAlien = new Vector2(rand.nextInt(FLUCTUATION), y + ALIEN_INTRO_HEIGHT);
        velocityAlien = new Vector2(40, 0);
        boundsAlien = new Rectangle(posAlien.x, posAlien.y, alien.getWidth() / 3, alien.getHeight());
        boundsAlien.setPosition(posAlien.x, posAlien.y);

        alienParticles = new ParticleEffect();
        alienParticles.load(Gdx.files.internal("particles/alienbeam.pfx"), Gdx.files.internal("particles"));
    }

    public void update(float dt){
        alienAnimation.update(dt);

        //make the alien move back and forth on the x-axis

        if(!crashed){
            if(posAlien.x < 0){
                posAlien.x = 0;
                velocity = 5;
            }
            if(posAlien.x > (ColorCrusade.WIDTH / 2) - alien.getWidth() / 3){
                posAlien.x = (ColorCrusade.WIDTH / 2) - alien.getWidth() / 3;
                velocity = -5;
            }
            velocityAlien.add(velocity, 0);
            velocityAlien.scl(dt);
            posAlien.add(velocityAlien.x, 0);
            velocityAlien.scl(1 / dt);
        }

        alienParticles.update(dt);
        alienParticles.setPosition(posAlien.x + (alien.getWidth() / 6), posAlien.y);


        boundsAlien.setPosition(posAlien.x, posAlien.y);
    }

    public TextureRegion getAlien(){
        return alienAnimation.getFrame();
    }

    public Vector2 getPosAlien(){
        return posAlien;
    }

    public void reposition(float y){
        posAlien.set(rand.nextInt(FLUCTUATION), y);
        boundsAlien.setPosition(posAlien.x, posAlien.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsAlien);
    }

    public void dispose(){
        alien.dispose();
    }

    public void crashedIntoAlien(){
        //stop the alien from moving after crash
        velocityAlien.x = 0;
        crashed = true;
    }

}
