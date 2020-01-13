package pl.ccoders.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import pl.ccoders.game.model.GameModel;
import pl.ccoders.game.utils.ConstUtils;
import pl.ccoders.game.views.BonusView;
import pl.ccoders.game.views.BubbleView;
import pl.ccoders.game.views.CircleView;
import pl.ccoders.game.views.GameView;
import pl.ccoders.game.views.NeedleView;

public class MyGdxGame extends ApplicationAdapter {
  GameModel mGame = new GameModel();


  private boolean wasTouched;

  @Override
  public void create() {
    mGame.initGame();

    wasTouched = false;
  }

  @Override
  public void render() {
    mGame.updateGame();

    if (isGameRunning) {//GRA SIĘ ZACZĘłA
      updateGameSession();
    } else {   //PRZEGRANIE(ALBO MENU - PRZY PIERWSZYM ODPALENIU GRY)
      updateGameMenu();
    }
  }

  private void updateGameMenu() {
    mBackgroundMusic.stop();
    if (currentScore > highScore) {
      mPrefs.putInteger("highscore", currentScore);
      mPrefs.flush();
    }
    highScore = mPrefs.getInteger("highscore");
    mSpriteBatch.begin();

    mMenuTitleFont.draw(mSpriteBatch, isMenuShowed ? ConstUtils.APP_NAME : ConstUtils.GAME_OVER, 2 * unit, 17 * unit);

    if (isMenuShowed) {
      mRestartButton.setText(ConstUtils.PLAY_THE_GAME);
    } else {
      mRestartButton.setText(ConstUtils.RESTART_THE_GAME);
    }

    mRestartButton.draw(mSpriteBatch, 1);
    mSpriteBatch.end();
    Gdx.input.setInputProcessor(mStage);
    mStage.addActor(mRestartButton);
    mStage.draw();
    mRestartButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        mCurrentBubbleView = new BubbleView(unit, maxUnits / 2 * unit, 3 / 2 * unit, 0f, 0f);
        mCurrentBubbleView.size = 3 * unit;
        mCurrentBubbleView.inMotion = false;
        mNextBubbleView = new BubbleView(unit, mCurrentBubbleView.posX - mCurrentBubbleView.size, mCurrentBubbleView.posY, 0f, 0f);
        mBubbleViewList.clear();
        mBubbleViewList.add(mCurrentBubbleView);
        mBubbleViewList.add(mNextBubbleView);
        mNeedleView.length = 3 * unit;
        wasTouched = false;
        posYSum = 0;
        isGameRunning = true;
        mBonusView = null;
        createBonus = true;
        scoreInc = false;
        scoreDec = false;
        createObstacle = false;
        needlesLengthDec = false;
        bonusNeedlesLength = 0;
        bonusCurrentScore = 0;
        bubbleSizeForNeedles = 0;
        lineLength = 0 * unit;
      }
    });
  }

  private void updateGameSession() {
    mBackgroundMusic.play();
    if (mBonusView != null && (checkBubblesCollision(mCurrentBubbleView, mBonusView) || mBonusView.posY < 0)) {
      if (checkBubblesCollision(mCurrentBubbleView, mBonusView)) {
        if (mBonusView.isPositive) {
          mPositiveBonusMusic.play();
        } else {
          mNegativeBonusMusic.play();
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
    if (Math.random() < 0.01 && createBonus) {
      mBonusView = new BonusView(unit);
      createBonus = false;
    }
    isMenuShowed = false;
    mStage.clear();
    if (Gdx.input.isTouched()) {
      wasTouched = true;
      mNextBubbleView.inMotion = false;
      mNextBubbleView.velSpeed = 0f;
      if (mNextBubbleView.size < 4 * unit) {
        mNextBubbleView.size += mNextBubbleView.sizeIncSpeed;
      }
      if (checkCollision(mCurrentBubbleView) || checkCollision(mNextBubbleView)) {
        isGameRunning = false;
      }
      for (BubbleView bubble : mBubbleViewList) {
        if (!bubble.equals(mNextBubbleView) && !bubble.equals(mCurrentBubbleView)) {
          if (checkBubblesCollision(mNextBubbleView, bubble)) {
            isGameRunning = false;
          }
        }
      }
    } else if (!Gdx.input.isTouched() && wasTouched) {
      mCurrentBubbleView = mNextBubbleView;
      mNextBubbleView = new BubbleView(unit, mCurrentBubbleView.posX - mCurrentBubbleView.size,
              mCurrentBubbleView.posY, (mBubbleViewList.size() < 60 ? mBubbleViewList.size() : 60) * unit / 600,
              (mBubbleViewList.size() < 25 ? mBubbleViewList.size() : 25) / 250f);
      mBubbleViewList.add(mNextBubbleView);
      if (mCurrentBubbleView.posY > 20 * unit) {
        for (BubbleView bubble : mBubbleViewList) {
          bubble.posY -= mCurrentBubbleView.posY - 20 * unit;
        }
      }
      wasTouched = false;
    }
    mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    mNextBubbleView.move();
    mNextBubbleView.posX = mCurrentBubbleView.posX - mCurrentBubbleView.size * (float) Math.cos(mNextBubbleView.vel) - mNextBubbleView.size * (float) Math.cos(mNextBubbleView.vel);
    mNextBubbleView.posY = mCurrentBubbleView.posY + mCurrentBubbleView.size * (float) Math.sin(mNextBubbleView.vel) + mNextBubbleView.size * (float) Math.sin(mNextBubbleView.vel);
    for (BubbleView bubble : mBubbleViewList) {
      bubble.draw(mShapeRenderer);
    }
    if (mNeedleView.length <= 6 * unit && mNeedleView.length >= 3 / 2 * unit)
      mNeedleView.length = 3 * unit + mBubbleViewList.size() * unit / 15 + bonusNeedlesLength;
    if (mNeedleView.length > 6 * unit) mNeedleView.length = 6 * unit;
    if (mNeedleView.length < 3 / 2 * unit) mNeedleView.length = 3 / 2 * unit;
    if (needlesLengthDec) {
      if (mNeedleView.length > 3 * unit) {
        bonusNeedlesLength = -mBubbleViewList.size() / 15 * unit - unit;
      }
      needlesLengthDec = false;
    }
    for (int i = 0; i < numberOfNeedles + 1; i++) {
      mNeedleView.draw(mShapeRenderer, i * 5 * unit);
    }
    if (mBonusView != null) {
      mBonusView.move();
      mBonusView.draw(mShapeRenderer);
    }
    if (createObstacle) {
      mShapeRenderer.setColor(1, 1, 1, 1);

      mShapeRenderer.rectLine(lineLength - 10 * unit, 23.5f * unit, lineLength, 23.5f * unit, unit / 6);
      lineLength += unit / 4;
      if (mNextBubbleView.posY + mNextBubbleView.size > 23.5 * unit && mNextBubbleView.posX + mNextBubbleView.size < lineLength && mNextBubbleView.posX + mNextBubbleView.size > lineLength - 5 * unit && mNextBubbleView.posX - mNextBubbleView.size < lineLength && mNextBubbleView.posX - mNextBubbleView.size > lineLength - 5 * unit)
        isGameRunning = false;
      if (lineLength >= 30 * unit) {
        createObstacle = false;
        lineLength = 0 * unit;
      }

    }
    mShapeRenderer.end();
  }

  private boolean checkBubblesCollision(CircleView bubble1, CircleView bubble2) {
    float distance = (float) Math.sqrt(Math.pow(Math.abs(bubble1.posX - bubble2.posX), 2) + Math.pow(Math.abs(bubble1.posY - bubble2.posY), 2));
    return distance < bubble1.size + bubble2.size;
  }

  private boolean checkCollision(CircleView bubble) {
    return bubble.posX - bubble.size < mNeedleView.length || bubble.posX + bubble.size > (20 * unit - mNeedleView.length);
  }
}
