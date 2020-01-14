package pl.ccoders.game.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import pl.ccoders.game.views.CircleView;
import pl.ccoders.game.views.NeedleView;

public class NeedleModel {
  private float numberOfNeedles;
  private NeedleView mNeedleView;

  public float getNumberOfNeedles() {
    return numberOfNeedles;
  }

  public NeedleView getmNeedleView() {
    return mNeedleView;
  }



  public void initNeedle(GameModel mGame) {
    mNeedleView = new NeedleView(mGame.getUnit(), 3 * mGame.getUnit());
    numberOfNeedles = Gdx.graphics.getHeight() / (mNeedleView.height);
  }

  public void updateNeddles(GameModel mGame) {
    if (mNeedleView.length <= 6 * mGame.getUnit() && mNeedleView.length >= 3 / 2 * mGame.getUnit())
      mNeedleView.length = 3 * mGame.getUnit() + mGame.getBubbleHandler().getmBubbleViewList().size() * mGame.getUnit() / 15 + mGame.getBonusHandler().getBonusNeedlesLength();
    if (mNeedleView.length > 6 * mGame.getUnit()) mNeedleView.length = 6 * mGame.getUnit();
    if (mNeedleView.length < 3 / 2 * mGame.getUnit()) mNeedleView.length = 3 / 2 * mGame.getUnit();
    if (mGame.getBonusHandler().isNeedlesLengthDec()) {
      if (mNeedleView.length > 3 * mGame.getUnit()) mGame.getBonusHandler().bonusNeedlesLength = -mGame.getBubbleHandler().getmBubbleViewList().size() / 15 * mGame.getUnit() - mGame.getUnit();

      mGame.getBonusHandler().setNeedlesLengthDec(false);
    }
  }

  public void initOnRestart(GameModel mGame) {
    mNeedleView.length = 3 * mGame.getUnit();
  }

  public boolean checkCollision(CircleView bubble, GameModel mGame) {
    return bubble.posX - bubble.size < mNeedleView.length || bubble.posX + bubble.size > (20 * mGame.getUnit() - mNeedleView.length);
  }
}
