package pl.ccoders.game.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import pl.ccoders.game.utils.ConstUtils;

public class FontModel {
  private BitmapFont mResultTitleFont, mMenuTitleFont;

  public String getResult() {
    return result;
  }

  public String result;

  public BitmapFont getmResultTitleFont() {
    return mResultTitleFont;
  }

  public BitmapFont getmMenuTitleFont() {
    return mMenuTitleFont;
  }

  public TextButton getmRestartButton() {
    return mRestartButton;
  }

  private TextButton mRestartButton;

  public void initFonts(GameModel mGame) {
    mResultTitleFont = new BitmapFont();
    mResultTitleFont.setColor(1, 1, 1, 1);
    mResultTitleFont.getData().setScale(5);
    mMenuTitleFont = new BitmapFont();
    mMenuTitleFont.getData().setScale(10);
    BitmapFont restartButtonFont = new BitmapFont();
    restartButtonFont.getData().setScale(5);
    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.fontColor = Color.GREEN;
    textButtonStyle.downFontColor = Color.RED;
    textButtonStyle.font = restartButtonFont;
    mRestartButton = new TextButton(ConstUtils.RESTART_THE_GAME, textButtonStyle);
    mRestartButton.setPosition(5 * mGame.getUnit(), 5 * mGame.getUnit());
  }

  public void updateTitles(GameModel mGame) {
    if (mGame.getcGame().getIsMenuShowed()) {
      mGame.getFontHandler().getmRestartButton().setText(ConstUtils.PLAY_THE_GAME);
    } else {
      mGame.getFontHandler().getmRestartButton().setText(ConstUtils.RESTART_THE_GAME);
    }
  }
}
