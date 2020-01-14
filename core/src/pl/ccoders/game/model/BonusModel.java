package pl.ccoders.game.model;

import pl.ccoders.game.utils.ConstUtils;
import pl.ccoders.game.views.BonusView;

public class BonusModel {
  private boolean createBonus;
  public boolean scoreInc;
  public boolean scoreDec;
  private boolean needlesLengthDec;
  private boolean createObstacle;
  public float bonusNeedlesLength;
  public int bonusCurrentScore;

  private BonusView mBonusView;

  public void initBonus() {
    createBonus = true;
    scoreInc = false;
    needlesLengthDec = false;
    scoreDec = false;
    createObstacle = false;
    bonusNeedlesLength = 0;
    bonusCurrentScore = 0;
    mBonusView = null;
  }

  public void createBonus(GameModel mGame) {
    if (Math.random() < 0.01 && createBonus) {
      mBonusView = new BonusView(mGame.getUnit());
      createBonus = false;
    }
  }

  public void handleBonusCatch(GameModel mGame) {
    if (mBonusView != null && (mGame.getBubbleHandler().checkBubblesCollision(mGame.getBubbleHandler().getmCurrentBubbleView(), mBonusView) || mBonusView.posY < 0)) {
      if (mGame.getBubbleHandler().checkBubblesCollision(mGame.getBubbleHandler().getmCurrentBubbleView(), mBonusView)) {
        if (mBonusView.isPositive) {
          mGame.getMusicHandler().playPositiveBonusMusic();
        } else {
          mGame.getMusicHandler().playNegativeBonusMusic();
        }
        switch (mBonusView.type) {
          case ConstUtils.SCORE_INC:
            scoreInc = true;
            break;
          case ConstUtils.SCORE_DEC:
            scoreDec = true;
            break;
          case ConstUtils.NEEDLES_LENGTH_DEC:
            needlesLengthDec = true;
            break;
          case ConstUtils.CREATE_OBSTACLE:
            createObstacle = true;
            break;
          default:
            break;
        }
      }
      mBonusView = null;
      createBonus = true;
    }
  }

  public void updateBonus() {
    if (mBonusView != null) {
      mBonusView.move();
    }

  }

  public BonusView getmBonusView() {
    return mBonusView;
  }

  public boolean isScoreDec() {
    return scoreDec;
  }

  public boolean isNeedlesLengthDec() {
    return needlesLengthDec;
  }

  public boolean isCreateObstacle() {
    return createObstacle;
  }

  public float getBonusNeedlesLength() {
    return bonusNeedlesLength;
  }

  public int getBonusCurrentScore() {
    return bonusCurrentScore;
  }

  public void setNeedlesLengthDec(boolean needlesLengthDec) {
    this.needlesLengthDec = needlesLengthDec;
  }

  public void setCreateObstacle(boolean createObstacle) {
    this.createObstacle = createObstacle;
  }
}
