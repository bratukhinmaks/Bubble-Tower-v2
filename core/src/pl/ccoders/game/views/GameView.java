package pl.ccoders.game.views;

import pl.ccoders.game.activities.GameController;
import pl.ccoders.game.model.GameModel;

public class GameView {
    public void drawGame(GameModel mGame, GameController cGame) {
        drawGameScore(mGame);
        if(cGame.getIsGameRunning()) drawGameSession(mGame);
        else drawMenu(mGame);
    };

    private void drawGameScore(GameModel mGame) {
        mGame.getmSpriteBatch().begin();
        mGame.getmResultTitleFont().draw(mGame.getmSpriteBatch(), mGame.getResult(), 6 * mGame.getUnit(), 35 * mGame.getUnit());
        mGame.getmSpriteBatch().end();
    }

    private void drawGameSession(GameModel mGame) {

    }

    private void drawMenu(GameModel mGame) {

    }
}
