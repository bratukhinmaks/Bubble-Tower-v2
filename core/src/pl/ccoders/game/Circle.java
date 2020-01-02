package pl.ccoders.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Circle {
  float posX, posY, size, red, green, blue;

  void draw(ShapeRenderer shape) {
    shape.setColor(red, green, blue, 1);
    shape.circle(posX, posY, size);
  }

  abstract void move();
}