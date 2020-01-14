package pl.ccoders.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class CanvasModel {
  private ShapeRenderer mShapeRenderer;
  private SpriteBatch mSpriteBatch;
  private Stage mStage;

  public ShapeRenderer getmShapeRenderer() {
    return mShapeRenderer;
  }

  public SpriteBatch getmSpriteBatch() {
    return mSpriteBatch;
  }

  public Stage getmStage() {
    return mStage;
  }

  public void initCanvas() {
    mSpriteBatch = new SpriteBatch();
    mShapeRenderer = new ShapeRenderer();
    mStage = new Stage();
  }
}
