package pl.ccoders.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Needle {
  float length, height, unit;

  Needle(float unit, float height) {
    this.unit = unit;
    this.height = height;
  }
  void draw(ShapeRenderer shape, float y1) {
    shape.setColor(1,1,1,1);
    shape.triangle(0, y1, 0, y1+height, length, y1+height/2);
    shape.triangle(20*unit, y1, 20*unit, y1+height, 20*unit-length, y1+height/2);
  }
}
