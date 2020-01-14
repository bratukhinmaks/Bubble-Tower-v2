package pl.ccoders.game.model;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

import pl.ccoders.game.views.BubbleView;
import pl.ccoders.game.views.CircleView;

public class BubbleModel {
  private List<BubbleView> mBubbleViewList;

  public List<BubbleView> getmBubbleViewList() {
    return mBubbleViewList;
  }

  public BubbleView getmCurrentBubbleView() {
    return mCurrentBubbleView;
  }

  public BubbleView getmNextBubbleView() {
    return mNextBubbleView;
  }

  private BubbleView mCurrentBubbleView, mNextBubbleView;

  public void initBubble(GameModel mGame) {
    mBubbleViewList = new ArrayList<>();
    mCurrentBubbleView = new BubbleView(mGame.getUnit(), 20 / 2 * mGame.getUnit(), 3 / 2 * mGame.getUnit(), 0f, 0f);
    mCurrentBubbleView.size = 3 * mGame.getUnit();
    mCurrentBubbleView.inMotion = false;
    mNextBubbleView = new BubbleView(mGame.getUnit(), mCurrentBubbleView.posX - mCurrentBubbleView.size, mCurrentBubbleView.posY, 0f, 0f);
    mBubbleViewList.add(mCurrentBubbleView);
    mBubbleViewList.add(mNextBubbleView);
  }

  public void touchBubbleHandler(GameModel mGame) {
    if (mGame.getcGame().isTouched()) {
      mNextBubbleView.inMotion = false;
      mNextBubbleView.velSpeed = 0f;
      if (mNextBubbleView.size < 4 * mGame.getUnit()) {
        mNextBubbleView.size += mNextBubbleView.sizeIncSpeed;
      }
      if (mGame.getNeedleHandler().checkCollision(mCurrentBubbleView, mGame) || mGame.getNeedleHandler().checkCollision(mNextBubbleView, mGame)) {
        mGame.getcGame().setGameRunning(false);
      }
      for (BubbleView bubble : mBubbleViewList) {
        if (!bubble.equals(mNextBubbleView) && !bubble.equals(mCurrentBubbleView)) {
          if (checkBubblesCollision(mNextBubbleView, bubble)) {
            mGame.getcGame().setGameRunning(false);
          }
        }
      }
    } else if (!mGame.getcGame().isTouched() && mGame.getcGame().getwasTouched()) {
      mGame.getcGame().setWasTouched(false);
      mCurrentBubbleView = mNextBubbleView;
      mNextBubbleView = new BubbleView(mGame.getUnit(), mCurrentBubbleView.posX - mCurrentBubbleView.size,
              mCurrentBubbleView.posY, (mBubbleViewList.size() < 60 ? mBubbleViewList.size() : 60) * mGame.getUnit() / 600,
              (mBubbleViewList.size() < 25 ? mBubbleViewList.size() : 25) / 250f);
      mBubbleViewList.add(mNextBubbleView);
      if (mCurrentBubbleView.posY > 20 * mGame.getUnit()) {
        for (BubbleView bubble : mBubbleViewList) {
          bubble.posY -= mCurrentBubbleView.posY - 20 * mGame.getUnit();
        }
      }
    }
  }

  public void updateBubbles() {
    mNextBubbleView.move();
    mNextBubbleView.posX = mCurrentBubbleView.posX - mCurrentBubbleView.size * (float) Math.cos(mNextBubbleView.vel) - mNextBubbleView.size * (float) Math.cos(mNextBubbleView.vel);
    mNextBubbleView.posY = mCurrentBubbleView.posY + mCurrentBubbleView.size * (float) Math.sin(mNextBubbleView.vel) + mNextBubbleView.size * (float) Math.sin(mNextBubbleView.vel);
  }

  public void restartBubble(GameModel mGame) {
    mCurrentBubbleView = new BubbleView(mGame.getUnit(), 20 / 2 * mGame.getUnit(), 3 / 2 *mGame.getUnit(), 0f, 0f);
    mCurrentBubbleView.size = 3 * mGame.getUnit();
    mCurrentBubbleView.inMotion = false;
    mNextBubbleView = new BubbleView(mGame.getUnit(), mCurrentBubbleView.posX - mCurrentBubbleView.size, mCurrentBubbleView.posY, 0f, 0f);
    mBubbleViewList.clear();
    mBubbleViewList.add(mCurrentBubbleView);
    mBubbleViewList.add(mNextBubbleView);
  }

  public boolean checkBubblesCollision(CircleView bubble1, CircleView bubble2) {
    float distance = (float) Math.sqrt(Math.pow(Math.abs(bubble1.posX - bubble2.posX), 2) + Math.pow(Math.abs(bubble1.posY - bubble2.posY), 2));
    return distance < bubble1.size + bubble2.size;
  }
}
