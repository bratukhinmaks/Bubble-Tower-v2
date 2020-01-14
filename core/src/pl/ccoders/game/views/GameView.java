package pl.ccoders.game.views;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import pl.ccoders.game.activities.GameController;
import pl.ccoders.game.model.GameModel;
import pl.ccoders.game.utils.ConstUtils;

public class GameView {
    public void drawGame(GameModel mGame, GameController cGame) {
        drawGameScore(mGame);
        if(cGame.getIsGameRunning()) drawGameSession(mGame);
        else drawMenu(mGame, cGame);
    };

    private void drawGameScore(GameModel mGame) {
        CharSequence chrs;
        if(mGame.getFontHandler().getResult() == null ) chrs = "0";
        else chrs = mGame.getFontHandler().getResult();
        mGame.getCanvasHandler().getmSpriteBatch().begin();
        mGame.getFontHandler().getmResultTitleFont().draw(mGame.getCanvasHandler().getmSpriteBatch(), chrs , 6 * mGame.getUnit(), 35 * mGame.getUnit());
        mGame.getCanvasHandler().getmSpriteBatch().end();
    }

    private void drawGameSession(GameModel mGame) {
        mGame.getCanvasHandler().getmStage().clear();
        mGame.getCanvasHandler().getmShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for (BubbleView bubble : mGame.getBubbleHandler().getmBubbleViewList()) {
            bubble.draw(mGame.getCanvasHandler().getmShapeRenderer());
        }
        for (int i = 0; i < mGame.getNeedleHandler().getNumberOfNeedles() + 1; i++) {
            mGame.getNeedleHandler().getmNeedleView().draw(mGame.getCanvasHandler().getmShapeRenderer(), i * 5 * mGame.getUnit());
        }
        if (mGame.getBonusHandler().getmBonusView() != null) {
            mGame.getBonusHandler().getmBonusView().draw(mGame.getCanvasHandler().getmShapeRenderer());
        }
        if(mGame.getBonusHandler().isCreateObstacle()) {
            mGame.getCanvasHandler().getmShapeRenderer().setColor(1, 1, 1, 1);
            mGame.getCanvasHandler().getmShapeRenderer().rectLine(mGame.getObstacleHandler().getLineLength() - 10 * mGame.getUnit(), 23.5f * mGame.getUnit(), mGame.getObstacleHandler().getLineLength(), 23.5f * mGame.getUnit(), mGame.getUnit() / 6);
        }
        mGame.getCanvasHandler().getmShapeRenderer().end();
    }

    private void drawMenu(GameModel mGame, GameController cGame) {
        mGame.getCanvasHandler().getmSpriteBatch().begin();
        mGame.getFontHandler().getmMenuTitleFont().draw(mGame.getCanvasHandler().getmSpriteBatch(), cGame.getIsMenuShowed() ? ConstUtils.APP_NAME : ConstUtils.GAME_OVER, 2 * mGame.getUnit(), 17 * mGame.getUnit());
        mGame.getFontHandler().getmRestartButton().draw(mGame.getCanvasHandler().getmSpriteBatch(), 1);
        mGame.getCanvasHandler().getmSpriteBatch().end();
        mGame.getCanvasHandler().getmStage().draw();
    }
}
