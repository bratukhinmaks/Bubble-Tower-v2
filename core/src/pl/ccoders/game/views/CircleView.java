package pl.ccoders.game.views;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class CircleView {

  public float posX, posY, size, red, green, blue;

  public void draw(ShapeRenderer pShapeRenderer) {
    pShapeRenderer.setColor(red, green, blue, 1);
    pShapeRenderer.circle(posX, posY, size);
  }

  protected abstract void move();
}