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
        mGame.getmSpriteBatch().begin();
        mGame.getmResultTitleFont().draw(mGame.getmSpriteBatch(), mGame.getResult(), 6 * mGame.getUnit(), 35 * mGame.getUnit());
        mGame.getmSpriteBatch().end();
    }

    private void drawGameSession(GameModel mGame) {
        mGame.getmStage().clear();
        mGame.getmShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for (BubbleView bubble : mGame.getmBubbleViewList()) {
            bubble.draw(mGame.getmShapeRenderer());
        }
        for (int i = 0; i < mGame.getNumberOfNeedles() + 1; i++) {
            mGame.getmNeedleView().draw(mGame.getmShapeRenderer(), i * 5 * mGame.getUnit());
        }

        if (mGame.getmBonusView() != null) {
            mGame.getmBonusView().draw(mGame.getmShapeRenderer());
        }

        if(mGame.getCreateObstacle()) {
            mGame.getmShapeRenderer().setColor(1, 1, 1, 1);
            mGame.getmShapeRenderer().rectLine(mGame.getLineLength() - 10 * mGame.getUnit(), 23.5f * mGame.getUnit(), mGame.getLineLength(), 23.5f * mGame.getUnit(), mGame.getUnit() / 6);
        }

        mGame.getmShapeRenderer().end();
    }

    private void drawMenu(GameModel mGame, GameController cGame) {
        mGame.getmSpriteBatch().begin();
        mGame.getmMenuTitleFont().draw(mGame.getmSpriteBatch(), cGame.getIsMenuShowed() ? ConstUtils.APP_NAME : ConstUtils.GAME_OVER, 2 * mGame.getUnit(), 17 * mGame.getUnit());
        mGame.getmRestartButton().draw(mGame.getmSpriteBatch(), 1);
        mGame.getmSpriteBatch().end();

        mGame.getmStage().draw();
    }
}
