package pl.ccoders.game.model;

import com.badlogic.gdx.Gdx;

import pl.ccoders.game.activities.GameController;
import pl.ccoders.game.views.GameView;

public class GameModel {

    private GameController cGame = new GameController();
    private GameView vGame = new GameView();
    private ScoreModel scoreHandler = new ScoreModel();
    private BonusModel bonusHandler = new BonusModel();
    private MusicModel musicHandler = new MusicModel();
    private FontModel fontHandler = new FontModel();
    private NeedleModel needleHandler = new NeedleModel();
    private BubbleModel bubbleHandler = new BubbleModel();
    private CanvasModel canvasHandler = new CanvasModel();
    private ObstacleModel obstacleHandler = new ObstacleModel();

    private int maxUnits = 20;
    private float unit;


    public void initGame() {
        unit = Gdx.graphics.getWidth() / maxUnits;

        cGame.initController();
        fontHandler.initFonts(this);
        musicHandler.initMusic();
        scoreHandler.initScore();
        bonusHandler.initBonus();
        needleHandler.initNeedle(this);
        canvasHandler.initCanvas();
        bubbleHandler.initBubble(this);
        obstacleHandler.initObstacle(this);
    }

    public void updateGame() {
        if(cGame.getIsGameRunning()) updateGameSession();
        else updateGameMenu();
        vGame.drawGame(this, cGame);
    }

    private void updateGameSession() {
        musicHandler.playBackgroundMusic();
        bonusHandler.createBonus(this);
        bubbleHandler.updateBubbles();
        bubbleHandler.touchBubbleHandler(this);
        bonusHandler.handleBonusCatch(this);
        needleHandler.updateNeddles(this);
        bonusHandler.updateBonus();
        obstacleHandler.updateObstacle(this);
        scoreHandler.updateScore(this);
        fontHandler.updateTitles(this);
    }

    private void updateGameMenu() {
        musicHandler.stopBackgroundMusic();
        scoreHandler.updateHighscore();
        if(cGame.isButtonPressed(this)) {
            bubbleHandler.restartBubble(this);
            needleHandler.initOnRestart(this);
            cGame.initOnRestartGame();
            bonusHandler.initBonus();
            obstacleHandler.initObstacle(this);
        }
    }

    public float getUnit() {
        return unit;
    }

    public GameController getcGame() {
        return cGame;
    }

    public BonusModel getBonusHandler() {
        return bonusHandler;
    }

    public FontModel getFontHandler() {
        return fontHandler;
    }

    public NeedleModel getNeedleHandler() {
        return needleHandler;
    }

    public CanvasModel getCanvasHandler() {
        return canvasHandler;
    }

    public BubbleModel getBubbleHandler() {
        return bubbleHandler;
    }

    public ObstacleModel getObstacleHandler() {
        return obstacleHandler;
    }

    public MusicModel getMusicHandler() {
        return musicHandler;
    }
}
