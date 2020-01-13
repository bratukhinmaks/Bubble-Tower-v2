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
        vGame.drawGame(this, cGame);
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
    }

    public SpriteBatch getmSpriteBatch() {
        return mSpriteBatch;
    }

    public BitmapFont getmResultTitleFont() {
        return mResultTitleFont;
    }

    public String getResult() {
        return result;
    }

    public float getUnit() {
        return unit;
    }
}
