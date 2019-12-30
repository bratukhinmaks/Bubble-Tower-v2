package pl.ccoders.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Needle {
  float length;
  float unit;

  Needle(float unit) {
    this.unit = unit;
  }
  void draw(ShapeRenderer shape, float y1) {
    shape.setColor(1,1,1,1);
    shape.triangle(0, y1, 0, y1+5*unit, length, y1+5/2*unit);
    shape.triangle(20*unit, y1, 20*unit, y1+5*unit, 20*unit-length, y1+5/2*unit);
  }
}
