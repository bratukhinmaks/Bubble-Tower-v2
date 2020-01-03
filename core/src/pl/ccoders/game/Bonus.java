package pl.ccoders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Shape;

public class Bonus extends Circle {
  float speed;
  boolean isPositive;
  String type;

  Bonus(float unit)
  {
   posX = (float)Math.random()*8*unit + 6*unit;
   posY = Gdx.graphics.getHeight();
   speed = -unit/7;
   size = unit*1/2;
   isPositive = Math.random()>0.5;
   blue = 0;
   red = isPositive ? 0 : 1;
   green = isPositive ? 1 : 0;
   type = isPositive ? (Math.random() > 0.5 ? "scoreInc" : "needlesLengthDec") : (Math.random() > 0.5 ? "scoreDec" : "createObstacle");
  }


  public void move() {
    posY += speed;
  }

}
