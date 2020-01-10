package pl.ccoders.game.views;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class NeedleView {

  public float length, height, unit;

  public NeedleView(float pUnit, float pHeight) {
    unit = pUnit;
    height = pHeight;
  }

  public void draw(ShapeRenderer pShapeRenderer, float y1) {
    pShapeRenderer.setColor(1, 1, 1, 1);
    pShapeRenderer.triangle(0, y1, 0, y1 + height, length, y1 + height / 2);
    pShapeRenderer.triangle(20 * unit, y1, 20 * unit, y1 + height, 20 * unit - length, y1 + height / 2);
  }
}
