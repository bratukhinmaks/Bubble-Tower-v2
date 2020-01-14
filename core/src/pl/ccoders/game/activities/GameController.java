package pl.ccoders.game.activities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.ccoders.game.model.GameModel;
import pl.ccoders.game.views.CircleView;

public class GameController {
    private boolean isGameRunning, isMenuShowed, wasTouched, buttonPressed;

    public void initController() {
        isGameRunning = false;
        isMenuShowed = false;
        wasTouched = false;
        buttonPressed = false;
    }

    public void initOnRestartGame() {
        wasTouched = false;
        isMenuShowed = false;
        isGameRunning = true;
        buttonPressed = false;
    }

    public boolean isTouched() {
        if(Gdx.input.isTouched()) {
            wasTouched = true;
            return true;
        }
        return false;
    }

    public boolean isButtonPressed(GameModel mGame) {
        Gdx.input.setInputProcessor(mGame.getCanvasHandler().getmStage());
        mGame.getCanvasHandler().getmStage().addActor(mGame.getFontHandler().getmRestartButton());
        mGame.getFontHandler().getmRestartButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonPressed = true;
            }
        });
        return buttonPressed;
    }

    public boolean getIsMenuShowed() {
        return isMenuShowed;
    }

    public boolean getIsGameRunning() {
        return isGameRunning;
    }

    public boolean getwasTouched() {
        return wasTouched;
    }

    public void setWasTouched(boolean pWasTouched) {
        wasTouched = pWasTouched;
    }
    public void setGameRunning(boolean pIsGameRunning) {
        isGameRunning = pIsGameRunning;
    }
}
