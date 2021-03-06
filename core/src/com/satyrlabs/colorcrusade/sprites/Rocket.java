package com.satyrlabs.colorcrusade.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.satyrlabs.colorcrusade.ColorCrusade;
import com.satyrlabs.colorcrusade.Constants;

import static com.satyrlabs.colorcrusade.Constants.*;

/**
 * Created by mhigh on 6/27/2017.
 */

public class Rocket {

    private static final float ACCELERATION = 0.5f;
    private static final float MAX_VELOCITY = 250.0f;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Animation rocketAnimation;
    Texture texture;
    private boolean crashed = false;

    public ParticleEffect explosionParticles;


    public Rocket(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        texture = new Texture("rocketanimation.png");
        rocketAnimation = new Animation(new TextureRegion(texture), 4, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 4, texture.getHeight());

        explosionParticles = new ParticleEffect();

        explosionParticles.load(Gdx.files.internal("particles/explosion.pfx"), Gdx.files.internal("particles"));

    }

    public void update(float dt){
        rocketAnimation.update(dt);

        if(velocity.y < MAX_VELOCITY){
            velocity.add(0, ACCELERATION);
        }
        velocity.scl(dt); //scale the acceleration for each update
        position.add(0, velocity.y);

        velocity.scl(1 / dt);  //reverse the previous scaling

        //As long as the rocket hasn't crashed, it can move on the x-axis from tilts
        if(!crashed){
            float accelerometerInput = Gdx.input.getAccelerometerX() / (GRAVITATIONAL_ACCELERATION * ACCELEROMETER_SENSITIVITY);

            position.x -= dt * accelerometerInput * ROCKET_MOVEMENT_SPEED;

            //Stop the rocket from going off screen
            if(position.x < 0 - (texture.getWidth() / 4)){
                position.x = ColorCrusade.WIDTH / 2;
            }
            if(position.x > ColorCrusade.WIDTH /2){
                position.x = 0 - (texture.getWidth() / 4);
            }
        }

        explosionParticles.update(dt);
        explosionParticles.setPosition(position.x + texture.getWidth() / 6, position.y + texture.getHeight() / 2);

        bounds.setPosition(position.x, position.y);
    }

    public Vector2 getPosition(){
        return position;
    }

    public TextureRegion getTexture(){
        return rocketAnimation.getFrame();
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        texture.dispose();
    }

    public void crashRocket(){
        //stop movement and cancel the accelerometer
        velocity.x = 0;
        velocity.y = 0;
        crashed = true;
    }

    public boolean isCrashed(){
        return crashed;
    }


}
