package pl.ccoders.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.lang.Math;

public class Bubble {
    float size, sizeIncSpeed, posX, posY;
    boolean inMotion, inSizeInc;
    float vel, velSpeed;
    float red,green,blue;


    Bubble(float unit, float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.inMotion = true;
        this.inSizeInc = false;
        this.sizeIncSpeed = unit/50;
        this.size = unit/2;
        this.vel = 0f;
        this.velSpeed = 1/20f;
        this.red = (float)Math.random();
        this.blue = (float)Math.random();
        this.green = (float)Math.random();
    }

    void draw(ShapeRenderer shape) {
        shape.setColor(red, green, blue, 1);
        shape.circle(posX, posY, size);
    }

    void move() {
        if(inMotion) {
            if(vel < 0) {
                velSpeed = 1/20f;
            }
            else if(vel > 3.1415) {
                velSpeed = -1/20f;
            }
        }
        vel +=velSpeed;
    }
}
