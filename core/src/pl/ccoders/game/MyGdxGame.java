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

  @Override
  public void create() {
    mGame.initGame();
  }

  @Override
  public void render() {
    mGame.updateGame();
  }
}
