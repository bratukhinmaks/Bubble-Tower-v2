package pl.ccoders.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
  boolean isGameRunning, isMenuShowed;
  Needle needles;
  float numberOfNeedles;
  TextButton restartButton;
  TextButton.TextButtonStyle textButtonStyle;
  BitmapFont font;
  Stage stage;
  Bonus bonus;
  Preferences prefs;
  int highScore = 0, currentScore;
  boolean createBonus, scoreInc, scoreDec, needlesLengthDec, createObstacle;
  Music backgroundMusic, gameOverMusic, positiveBonusMusic, negativeBonusMusic;
  float bonusNeedlesLength;
  int bonusCurrentScore;
  int bubbleSizeForNeedles;
  float lineLength;

  public boolean checkBubblesCollision(Circle bubble1, Circle bubble2) {
    float distance = (float)Math.sqrt(Math.pow(Math.abs(bubble1.posX-bubble2.posX), 2)+Math.pow(Math.abs(bubble1.posY-bubble2.posY), 2));
    if( distance < bubble1.size + bubble2.size ) return true;
    return false;
  }

  public boolean checkCollision(Circle bubble) {
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
    isGameRunning = false;
    isMenuShowed = true;
    gameOverText = new BitmapFont();
    needles = new Needle(unit, 3*unit);
    numberOfNeedles = Gdx.graphics.getHeight()/(needles.height);
    font = new BitmapFont();
    font.getData().setScale(5);
    textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.fontColor = Color.GREEN;
    textButtonStyle.downFontColor = Color.RED;
    textButtonStyle.font = font;
    restartButton = new TextButton("Restart the game", textButtonStyle);
    restartButton.setPosition(5*unit, 5*unit);
    stage = new Stage();
    prefs = Gdx.app.getPreferences("gamePreferences");
    createBonus = true;
    backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("level1.ogg"));
    backgroundMusic.setLooping(true);
    positiveBonusMusic = Gdx.audio.newMusic(Gdx.files.internal("posBonMus.mp3"));
    positiveBonusMusic.setLooping(false);
    negativeBonusMusic = Gdx.audio.newMusic(Gdx.files.internal("negBonMus.wav"));
    negativeBonusMusic.setLooping(false);
    scoreInc = false;
    needlesLengthDec = false;
    scoreDec = false;
    createObstacle = false;
    bonusNeedlesLength = 0;
    bonusCurrentScore = 0;
    bubbleSizeForNeedles = 0;
    lineLength = 0*unit;
  }

  @Override
  public void render () {
    currentScore = bubbles.size()-2+bonusCurrentScore;
    if(scoreDec) {
      if(currentScore>=5) bonusCurrentScore -= 5;
      else bonusCurrentScore -= currentScore;
      scoreDec = false;
    }
    if(scoreInc) {
      currentScore +=5;
      scoreInc = false;
    }
    batch.begin();
    result = isMenuShowed?"":"Your score is "+currentScore+"\nHigh score is " + highScore;
    resultText.setColor(1,1,1,1);
    resultText.getData().setScale(5);
    resultText.draw(batch, result, 6*unit,35*unit);
    batch.end();
    if(isGameRunning) {    //GRA SIĘ ZACZĘłA
      backgroundMusic.play();
      if(bonus!=null && (checkBubblesCollision(currentBubble, bonus) || bonus.posY<0)) {
        if(checkBubblesCollision(currentBubble, bonus)) {
          if(bonus.isPositive) positiveBonusMusic.play();
          else negativeBonusMusic.play();
          switch (bonus.type){
//            case "velSpeedDec":
//              velSpeedDec = true;
//              break;
//            case "velSpeedInc":
//              velSpeedInc = true;
//              break;
            case "scoreInc":
              scoreInc = true;
              break;
            case "scoreDec" :
              scoreDec = true;
              break;
            case "needlesLengthDec":
              needlesLengthDec = true;
              break;
            case "createObstacle":
              createObstacle = true;
              break;
            default:
              break;
          }
        }
        bonus = null;
        createBonus = true;
      }
      if(Math.random() < 0.01 &&   createBonus) {
        bonus = new Bonus(unit);
        createBonus = false;
      }
      isMenuShowed = false;
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
        nextBubble = new Bubble(unit, currentBubble.posX-currentBubble.size, currentBubble.posY, (bubbles.size()<55 ? bubbles.size() : 55)*unit/600, (bubbles.size()<15?bubbles.size():15)/250f);
//        if(velSpeedInc) {
//          nextBubble.velSpeedIncrease += unit/300;
//          velSpeedInc = false;
//        }
//        else if(velSpeedDec) {
//          nextBubble.velSpeedIncrease -= unit/300;
//          velSpeedDec = false;
//        }
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
      if(needles.length <= 6*unit && needles.length >=3/2*unit) needles.length = 3*unit + bubbles.size()*unit/15+bonusNeedlesLength;
      if(needles.length > 6*unit) needles.length = 6*unit;
      if(needles.length <3/2*unit) needles.length = 3/2*unit;
      if(needlesLengthDec) {
        if(needles.length > 3*unit) {
          bonusNeedlesLength = -bubbles.size()/15*unit - unit;
        }
        needlesLengthDec = false;
      }
      for(int i = 0; i< numberOfNeedles+1; i++ ) {
        needles.draw(shape, i*5*unit);
      }
      if(bonus != null) {
        bonus.move();
        bonus.draw(shape);
      }
      if(createObstacle) {
        shape.setColor(1,1,1,1);

        shape.rectLine(lineLength-10*unit,23.5f*unit, lineLength, 23.5f*unit, unit/6);
        lineLength += unit/4;
        if(nextBubble.posY+nextBubble.size > 23.5*unit && nextBubble.posX+nextBubble.size<lineLength && nextBubble.posX+nextBubble.size > lineLength - 5*unit && nextBubble.posX-nextBubble.size<lineLength && nextBubble.posX-nextBubble.size > lineLength - 5*unit) isGameRunning = false;
        if(lineLength >= 30*unit ) {
          createObstacle = false;
          lineLength = 0*unit;
        }

      }
      shape.end();
    }
    else {   //PRZEGRANIE(ALBO MENU - PRZY PIERWSZYM ODPALENIU GRY)
      backgroundMusic.stop();
      if (currentScore > highScore ) {
        prefs.putInteger("highscore", currentScore);
        prefs.flush();
      }
      highScore = prefs.getInteger("highscore");
      batch.begin();
      gameOverText.getData().setScale(10);
      gameOverText.draw(batch, isMenuShowed? "Bubble Tower" : "GAME OVER", 2*unit, 17*unit);
      if(isMenuShowed) restartButton.setText("Play the Game");
      else restartButton.setText("Restart the game");
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
          bonus = null;
          createBonus = true;
          scoreInc = false;
          scoreDec = false;
          createObstacle = false;
          needlesLengthDec = false;
          bonusNeedlesLength = 0;
          bonusCurrentScore = 0;
          bubbleSizeForNeedles = 0;
          lineLength = 0*unit;
        }
      });
    }
  }

  @Override
  public void dispose () {

  }
}
