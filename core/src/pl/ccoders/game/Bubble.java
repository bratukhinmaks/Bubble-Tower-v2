package pl.ccoders.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bubble {
    float size, sizeIncSpeed, posX, posY;
    boolean inMotion, inSizeInc;
    float vel, velSpeed;

    Texture bubble;

    Bubble(float unitW, float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.inMotion = true;
        this.inSizeInc = false;
        this.bubble = new Texture("bubble.png");
        this.sizeIncSpeed = unitW/25;
        this.size = unitW;
        this.vel = 0f;
        this.velSpeed = 1/20f;
    }

    void draw(SpriteBatch batch) {
        batch.draw(bubble, posX, posY, size, size);
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
