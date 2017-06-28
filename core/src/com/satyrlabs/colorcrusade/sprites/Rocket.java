package com.satyrlabs.colorcrusade.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.satyrlabs.colorcrusade.Constants;

import static com.satyrlabs.colorcrusade.Constants.*;

/**
 * Created by mhigh on 6/27/2017.
 */

public class Rocket {

    private static final float ACCELERATION = 0.5f;
    private static final float MAX_VELOCITY = 300.0f;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Animation rocketAnimation;
    Texture texture;


    public Rocket(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        texture = new Texture("rocketanimation.png");
        rocketAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());

    }

    public void update(float dt){
        rocketAnimation.update(dt);

        if(velocity.y < MAX_VELOCITY){
            velocity.add(0, ACCELERATION);
        }
        velocity.scl(dt); //scale the acceleration for each update
        position.add(0, velocity.y);

        velocity.scl(1 / dt);  //reverse the previous scaling

        float accelerometerInput = Gdx.input.getAccelerometerX() / (GRAVITATIONAL_ACCELERATION * ACCELEROMETER_SENSITIVITY);

        position.x -= dt * accelerometerInput * ROCKET_MOVEMENT_SPEED;

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


}
