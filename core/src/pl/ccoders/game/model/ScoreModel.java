package pl.ccoders.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import pl.ccoders.game.utils.ConstUtils;

public class ScoreModel {
  private Preferences mPrefs;
  private int highScore = 0, currentScore;

  public void initScore() {
    mPrefs = Gdx.app.getPreferences("gamePreferences");
  }

  public void updateHighscore() {
    if (currentScore > highScore) {
      mPrefs.putInteger("highscore", currentScore);
      mPrefs.flush();
    }
    highScore = mPrefs.getInteger("highscore");
  }

  public void updateScore(GameModel mGame) {
    currentScore = mGame.getBubbleHandler().getmBubbleViewList().size() + 400 + mGame.getBonusHandler().getBonusCurrentScore();
    if (mGame.getBonusHandler().isScoreDec()) {
      if (currentScore >= 5) mGame.getBonusHandler().bonusCurrentScore -= 5;
      else mGame.getBonusHandler().bonusCurrentScore -= currentScore;
      mGame.getBonusHandler().scoreDec = false;
    }
    if (mGame.getBonusHandler().scoreInc) {
      currentScore += 5;
      mGame.getBonusHandler().scoreInc = false;
    }
    mGame.getFontHandler().result =  mGame.getcGame().getIsMenuShowed() ? "" : ConstUtils.YOUR_SCORE_IS + currentScore + ConstUtils.HIGH_SCORE_IS + highScore;
  }
}
