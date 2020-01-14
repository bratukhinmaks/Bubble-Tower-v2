package pl.ccoders.game.model;

public class ObstacleModel {

  public float getLineLength() {
    return lineLength;
  }

  private float lineLength;

  public void initObstacle(GameModel mGame) {
    lineLength = 0 * mGame.getUnit();
  }

  public void updateObstacle(GameModel mGame) {
    if (mGame.getBonusHandler().isCreateObstacle()) {
      lineLength += mGame.getUnit() / 4;
      if (mGame.getBubbleHandler().getmNextBubbleView().posY + mGame.getBubbleHandler().getmNextBubbleView().size > 23.5 * mGame.getUnit() && mGame.getBubbleHandler().getmNextBubbleView().posX + mGame.getBubbleHandler().getmNextBubbleView().size < lineLength && mGame.getBubbleHandler().getmNextBubbleView().posX + mGame.getBubbleHandler().getmNextBubbleView().size > lineLength - 5 * mGame.getUnit() && mGame.getBubbleHandler().getmNextBubbleView().posX - mGame.getBubbleHandler().getmNextBubbleView().size < lineLength && mGame.getBubbleHandler().getmNextBubbleView().posX - mGame.getBubbleHandler().getmNextBubbleView().size > lineLength - 5 * mGame.getUnit())
        mGame.getcGame().setGameRunning(false);
      if (lineLength >= 30 * mGame.getUnit()) {
        mGame.getBonusHandler().setCreateObstacle(false);
        lineLength = 0 * mGame.getUnit();
      }

    }
  }
}
