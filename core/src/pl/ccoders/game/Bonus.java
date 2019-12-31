package pl.ccoders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Shape;

public class Bonus {
  boolean isPositive;
  float speed;
  float posX, posY, unit;
  String type;
  String[] typesOfBonuses = new String[2];

  Bonus(float unit, boolean isPositive, ShapeRenderer shape) {
    this.isPositive = isPositive;
    this.speed = unit/2;
    this.unit = unit;
    this.posX = (float)(Math.random()*8+6)*unit;
    this.posY = Gdx.graphics.getHeight();
    typesOfBonuses[0] = isPositive? "needlesDec" : "needlesInc";
    typesOfBonuses[1] = isPositive? "oneMoreLive" : "scoreDec";
    type = typesOfBonuses[Math.random()>0.5 ? 0 : 1];
  }

  void draw(ShapeRenderer shape) {
    shape.setColor(isPositive? Color.GREEN : Color.RED);
    shape.circle(posX, posY, unit);
  }

  void checkTakeOfBonus(Bubble bubble, boolean collisionEvent) {
    if( collisionEvent ) {

    }
  }
}
