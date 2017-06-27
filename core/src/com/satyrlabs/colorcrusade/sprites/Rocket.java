package com.satyrlabs.colorcrusade.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.satyrlabs.colorcrusade.Constants;

import static com.satyrlabs.colorcrusade.Constants.*;

/**
 * Created by mhigh on 6/27/2017.
 */

public class Rocket {

    private static final float ACCELERATION = 0.5f;
    private static final float MAX_VELOCITY = 400.0f;
    private Vector2 position;
    private Vector2 velocity;

    private Texture rocket;

    public Rocket(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        rocket = new Texture("bat.png");
    }

    public void update(float dt){
        if(velocity.y < MAX_VELOCITY){
            velocity.add(0, ACCELERATION);
        }
        velocity.scl(dt); //scale the acceleration for each update
        position.add(0, velocity.y);

        velocity.scl(1 / dt);  //reverse the previous scaling

        float acclerometerInput = Gdx.input.getAccelerometerX() / (GRAVITATIONAL_ACCELERATION * ACCELEROMETER_SENSITIVITY);

        position.x -= dt * acclerometerInput * ROCKET_MOVEMENT_SPEED;
    }

    public Vector2 getPosition(){
        return position;
    }

    public Texture getTexture(){
        return rocket;
    }


}
