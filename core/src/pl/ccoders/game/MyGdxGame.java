package pl.ccoders.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.awt.Button;
import java.util.ArrayList;
import pl.ccoders.game.Bubble;
import pl.ccoders.game.Needle;


public class MyGdxGame extends ApplicationAdapter {
  int maxUnits = 20;
  float unit;
  boolean wasTouched;
  float posYSum;
  Bubble currentBubble, nextBubble;
  ArrayList<Bubble> bubbles;
  ShapeRenderer shape;
  SpriteBatch batch;
  String result;
  BitmapFont resultText, gameOverText;
  boolean isGameRunning;
  Needle needles;
  float numberOfNeedles;
  TextButton restartButton;
  TextButton.TextButtonStyle textButtonStyle;
  BitmapFont font;
  Stage stage;

  public boolean checkBubblesCollision(Bubble bubble1, Bubble bubble2) {
    float distance = (float)Math.sqrt(Math.pow(Math.abs(bubble1.posX-bubble2.posX), 2)+Math.pow(Math.abs(bubble1.posY-bubble2.posY), 2));
    if( distance < bubble1.size + bubble2.size ) return true;
    return false;
  }

  public boolean checkCollision(Bubble bubble) {
    if(bubble.posX-bubble.size < needles.length || bubble.posX + bubble.size > (20*unit - needles.length)) {
      return true;
    }
    return false;
  }


  @Override
  public void create () {
    batch = new SpriteBatch();
    unit = Gdx.graphics.getWidth()/maxUnits;
    currentBubble = new Bubble(unit, maxUnits/2*unit,3/2*unit, 0f, 0f);
    currentBubble.size = 3*unit;
    currentBubble.inMotion = false;
    bubbles = new ArrayList<>();
    bubbles.add(currentBubble);
    nextBubble = new Bubble(unit, currentBubble.posX-currentBubble.size, currentBubble.posY, 0f, 0f);
    bubbles.add(nextBubble);
    shape = new ShapeRenderer();
    wasTouched = false;
    posYSum = 0;
    resultText = new BitmapFont();
    isGameRunning = true;
    gameOverText = new BitmapFont();
    needles = new Needle(unit);
    numberOfNeedles = Gdx.graphics.getHeight()/(5*unit);
    font = new BitmapFont();
    font.getData().setScale(5);
    textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.fontColor = Color.GREEN;
    textButtonStyle.downFontColor = Color.WHITE;
    textButtonStyle.font = font;
    restartButton = new TextButton("Restart the game", textButtonStyle);
    restartButton.setPosition(5*unit, 5*unit);
    stage = new Stage();
  }

  @Override
  public void render () {
    batch.begin();
    result = String.valueOf(bubbles.size()-2);
    resultText.setColor(1,1,1,1);
    resultText.getData().setScale(10);
    resultText.draw(batch, result, 17*unit,35*unit);
    batch.end();
    if(isGameRunning) {
      stage.clear();
      if(Gdx.input.isTouched()) {
        wasTouched = true;
        nextBubble.inMotion = false;
        nextBubble.velSpeed = 0f;
        if(nextBubble.size < 4*unit) {
          nextBubble.size += nextBubble.sizeIncSpeed;
        }
        if (checkCollision(currentBubble) || checkCollision(nextBubble)) {
          isGameRunning = false;
        }
        for( Bubble bubble: bubbles) {
          if(!bubble.equals(nextBubble)&&!bubble.equals(currentBubble)) {
            if(checkBubblesCollision(nextBubble, bubble)) {
              isGameRunning = false;
            }
          }
        }
      }
      else if (!Gdx.input.isTouched() && wasTouched) {
        currentBubble = nextBubble;
        nextBubble = new Bubble(unit, currentBubble.posX-currentBubble.size, currentBubble.posY, bubbles.size()*unit/300, bubbles.size()/50);
        bubbles.add(nextBubble);
        if(currentBubble.posY > 20*unit) {
          for(Bubble bubble: bubbles) {
            bubble.posY -= currentBubble.posY-20*unit;
          }
        }
        wasTouched = false;
      }

      shape.begin(ShapeRenderer.ShapeType.Filled);
      nextBubble.move();
      nextBubble.posX = currentBubble.posX - currentBubble.size*(float)Math.cos(nextBubble.vel)-nextBubble.size*(float)Math.cos(nextBubble.vel);
      nextBubble.posY = currentBubble.posY + currentBubble.size*(float)Math.sin(nextBubble.vel)+nextBubble.size*(float)Math.sin(nextBubble.vel);
      for( Bubble bubble: bubbles) {
        bubble.draw(shape);
      }
      if(needles.length < 6*unit) needles.length = 3*unit + bubbles.size()*unit/10;
      for(int i = 0; i< numberOfNeedles+1; i++ ) {
        needles.draw(shape, i*5*unit);
      }
      shape.end();
    }
    else {
      batch.begin();
      gameOverText.getData().setScale(10);
      gameOverText.draw(batch, "GAME OVER", 2*unit, 17*unit);
      restartButton.draw(batch, 1);
      batch.end();
      Gdx.input.setInputProcessor(stage);
      stage.addActor(restartButton);
      stage.draw();
      restartButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          currentBubble = new Bubble(unit, maxUnits/2*unit,3/2*unit, 0f, 0f);
          currentBubble.size = 3*unit;
          currentBubble.inMotion = false;
          nextBubble = new Bubble(unit, currentBubble.posX-currentBubble.size, currentBubble.posY, 0f, 0f);
          bubbles.clear();
          bubbles.add(currentBubble);
          bubbles.add(nextBubble);
          needles.length = 3*unit;
          wasTouched = false;
          posYSum = 0;
          isGameRunning = true;
        }
      });
    }

  }

  @Override
  public void dispose () {

  }
}
