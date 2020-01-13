package pl.ccoders.game.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.List;

import pl.ccoders.game.activities.GameController;
import pl.ccoders.game.utils.ConstUtils;
import pl.ccoders.game.views.BonusView;
import pl.ccoders.game.views.BubbleView;
import pl.ccoders.game.views.CircleView;
import pl.ccoders.game.views.GameView;
import pl.ccoders.game.views.NeedleView;

public class GameModel {

    private List<BubbleView> mBubbleViewList;
    private GameController cGame = new GameController();
    private GameView vGame = new GameView();

    private float numberOfNeedles;


    private int maxUnits = 20;
    private float unit;

    private Preferences mPrefs;

    private int highScore = 0, currentScore;
    private boolean createBonus, scoreInc, scoreDec, needlesLengthDec, createObstacle;
    private float bonusNeedlesLength;
    private int bonusCurrentScore;
    private BubbleView mCurrentBubbleView, mNextBubbleView;
    private BonusView mBonusView;
    private NeedleView mNeedleView;

    private ShapeRenderer mShapeRenderer;
    private SpriteBatch mSpriteBatch;

    private Stage mStage;

    private Music mBackgroundMusic, mPositiveBonusMusic, mNegativeBonusMusic;

    private BitmapFont mResultTitleFont, mMenuTitleFont;
    private TextButton mRestartButton;
    private float lineLength;
    String result;

    public void initGame() {

        mSpriteBatch = new SpriteBatch();
        mShapeRenderer = new ShapeRenderer();
        mStage = new Stage();

        mPrefs = Gdx.app.getPreferences("gamePreferences");

        mNeedleView = new NeedleView(unit, 3 * unit);
        numberOfNeedles = Gdx.graphics.getHeight() / (mNeedleView.height);

        mCurrentBubbleView = new BubbleView(unit, maxUnits / 2 * unit, 3 / 2 * unit, 0f, 0f);
        mCurrentBubbleView.size = 3 * unit;
        mCurrentBubbleView.inMotion = false;

        unit = Gdx.graphics.getWidth() / maxUnits;
        mNextBubbleView = new BubbleView(unit, mCurrentBubbleView.posX - mCurrentBubbleView.size, mCurrentBubbleView.posY, 0f, 0f);
        mBubbleViewList.add(mCurrentBubbleView);
        mBubbleViewList.add(mNextBubbleView);

        createBonus = true;
        scoreInc = false;
        needlesLengthDec = false;
        scoreDec = false;
        createObstacle = false;
        bonusNeedlesLength = 0;
        bonusCurrentScore = 0;
        lineLength = 0 * unit;
        initFonts();
        initMusic();
    }

    private void initMusic() {
        mBackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("level1.ogg"));
        mBackgroundMusic.setLooping(true);
        mBackgroundMusic.setVolume(0.5f);
        mPositiveBonusMusic = Gdx.audio.newMusic(Gdx.files.internal("posBonMus.mp3"));
        mPositiveBonusMusic.setLooping(false);
        mPositiveBonusMusic.setVolume(1f);
        mNegativeBonusMusic = Gdx.audio.newMusic(Gdx.files.internal("negBonMus.wav"));
        mNegativeBonusMusic.setLooping(false);
        mNegativeBonusMusic.setVolume(1f);
    }

    private void initFonts() {
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
        mRestartButton.setPosition(5 * unit, 5 * unit);
    }

    public void updateGame() {
        if(cGame.getIsGameRunning()) updateGameSession();
        else updateGameMenu();
        vGame.drawGame(this, cGame);
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
        if (cGame.isTouched()) {
            mNextBubbleView.inMotion = false;
            mNextBubbleView.velSpeed = 0f;
            if (mNextBubbleView.size < 4 * unit) {
                mNextBubbleView.size += mNextBubbleView.sizeIncSpeed;
            }
            if (checkCollision(mCurrentBubbleView) || checkCollision(mNextBubbleView)) {
                cGame.setGameRunning(false);
            }
            for (BubbleView bubble : mBubbleViewList) {
                if (!bubble.equals(mNextBubbleView) && !bubble.equals(mCurrentBubbleView)) {
                    if (checkBubblesCollision(mNextBubbleView, bubble)) {
                        cGame.setGameRunning(false);
                    }
                }
            }
        } else if (cGame.isTouched() && cGame.getwasTouched()) {
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
        }

        mNextBubbleView.move();
        mNextBubbleView.posX = mCurrentBubbleView.posX - mCurrentBubbleView.size * (float) Math.cos(mNextBubbleView.vel) - mNextBubbleView.size * (float) Math.cos(mNextBubbleView.vel);
        mNextBubbleView.posY = mCurrentBubbleView.posY + mCurrentBubbleView.size * (float) Math.sin(mNextBubbleView.vel) + mNextBubbleView.size * (float) Math.sin(mNextBubbleView.vel);

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

        if (mBonusView != null) {
            mBonusView.move();
        }

        if (createObstacle) {
            lineLength += unit / 4;
            if (mNextBubbleView.posY + mNextBubbleView.size > 23.5 * unit && mNextBubbleView.posX + mNextBubbleView.size < lineLength && mNextBubbleView.posX + mNextBubbleView.size > lineLength - 5 * unit && mNextBubbleView.posX - mNextBubbleView.size < lineLength && mNextBubbleView.posX - mNextBubbleView.size > lineLength - 5 * unit)
                cGame.setGameRunning(false);
            if (lineLength >= 30 * unit) {
                createObstacle = false;
                lineLength = 0 * unit;
            }

        }
        if (cGame.getIsMenuShowed()) {
            mRestartButton.setText(ConstUtils.PLAY_THE_GAME);
        } else {
            mRestartButton.setText(ConstUtils.RESTART_THE_GAME);
        }
    }

    private void updateGameMenu() {
        mBackgroundMusic.stop();
        if (currentScore > highScore) {
            mPrefs.putInteger("highscore", currentScore);
            mPrefs.flush();
        }
        highScore = mPrefs.getInteger("highscore");
    }

    private void updateScore() {
        currentScore = mBubbleViewList.size() - 2 + bonusCurrentScore;
        if (scoreDec) {
            if (currentScore >= 5) bonusCurrentScore -= 5;
            else bonusCurrentScore -= currentScore;
            scoreDec = false;
        }

        if (scoreInc) {
            currentScore += 5;
            scoreInc = false;
        }
        result =  cGame.getIsMenuShowed() ? "" : ConstUtils.YOUR_SCORE_IS + currentScore + ConstUtils.HIGH_SCORE_IS + highScore;
        if(cGame.isButtonPressed(this)) {
            mCurrentBubbleView = new BubbleView(unit, maxUnits / 2 * unit, 3 / 2 * unit, 0f, 0f);
            mCurrentBubbleView.size = 3 * unit;
            mCurrentBubbleView.inMotion = false;
            mNextBubbleView = new BubbleView(unit, mCurrentBubbleView.posX - mCurrentBubbleView.size, mCurrentBubbleView.posY, 0f, 0f);
            mBubbleViewList.clear();
            mBubbleViewList.add(mCurrentBubbleView);
            mBubbleViewList.add(mNextBubbleView);
            mNeedleView.length = 3 * unit;
            cGame.setWasTouched(false);
            cGame.setMenuShowed(false);
            cGame.setGameRunning(true);
            mBonusView = null;
            createBonus = true;
            scoreInc = false;
            scoreDec = false;
            createObstacle = false;
            needlesLengthDec = false;
            bonusNeedlesLength = 0;
            bonusCurrentScore = 0;
            lineLength = 0 * unit;
        }
    }

    public SpriteBatch getmSpriteBatch() {
        return mSpriteBatch;
    }

    public BitmapFont getmResultTitleFont() {
        return mResultTitleFont;
    }

    public  BitmapFont getmMenuTitleFont() {
        return mMenuTitleFont;
    }

    public String getResult() {
        return result;
    }

    public float getUnit() {
        return unit;
    }

    public Stage getmStage() {
        return mStage;
    }

    public ShapeRenderer getmShapeRenderer() {
        return mShapeRenderer;
    }

    public List<BubbleView> getmBubbleViewList() {
        return mBubbleViewList;
    }

    public float getNumberOfNeedles() {
        return numberOfNeedles;
    }

    public float getLineLength() {
        return lineLength;
    }

    public boolean getCreateObstacle() {
        return createObstacle;
    }

    public NeedleView getmNeedleView() {
        return mNeedleView;
    }

    public BonusView getmBonusView() {
        return mBonusView;
    }

    public TextButton getmRestartButton() {
        return mRestartButton;
    }

    private boolean checkBubblesCollision(CircleView bubble1, CircleView bubble2) {
        float distance = (float) Math.sqrt(Math.pow(Math.abs(bubble1.posX - bubble2.posX), 2) + Math.pow(Math.abs(bubble1.posY - bubble2.posY), 2));
        return distance < bubble1.size + bubble2.size;
    }

    private boolean checkCollision(CircleView bubble) {
        return bubble.posX - bubble.size < mNeedleView.length || bubble.posX + bubble.size > (20 * unit - mNeedleView.length);
    }
}
