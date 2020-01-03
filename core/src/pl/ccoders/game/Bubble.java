package pl.ccoders.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.lang.Math;

public class Bubble extends Circle{
  float sizeIncSpeed;
  boolean inMotion, inSizeInc;
  float vel, velSpeed;
  float sizeIncSpeedIncrease, velSpeedIncrease;


  Bubble(float unit, float posX, float posY, float sizeIncSpeedIncrease, float velSpeedIncrease) {
    this.posX = posX;
    this.posY = posY;
    this.inMotion = true;
    this.inSizeInc = false;
    this.sizeIncSpeedIncrease = sizeIncSpeedIncrease;
    this.velSpeedIncrease = velSpeedIncrease;
    this.sizeIncSpeed = unit/30+sizeIncSpeedIncrease;
    this.size = unit/2;
    this.vel = 0f;
    this.velSpeed = 1/10f+velSpeedIncrease;
    this.red = (float)Math.random();
    this.blue = (float)Math.random();
    this.green = (float)Math.random();
    if(this.red+this.green+this.blue <0.5) {
      this.red = (float)Math.random();
      this.blue = (float)Math.random();
      this.green = (float)Math.random();
    }
  }

  void move() {
    if(inMotion) {
      if(vel < 0) {
        velSpeed = 1/15f+velSpeedIncrease;
      }
      else if(vel > 3.1415) {
        velSpeed = -1/15f-velSpeedIncrease;
      }
    }
    vel +=velSpeed;
  }
}
