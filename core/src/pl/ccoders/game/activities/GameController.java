package pl.ccoders.game.activities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.ccoders.game.model.GameModel;
import pl.ccoders.game.views.CircleView;

public class GameController {
    private boolean isGameRunning, isMenuShowed, wasTouched, isTouched, buttonPressed;

    public void initController() {
        isGameRunning = false;
        isMenuShowed = true;
        wasTouched = false;
        isTouched = false;
        buttonPressed = false;
    }

    public boolean isTouched() {
        if(Gdx.input.isTouched()) wasTouched = true;
        else wasTouched = false;
        return true;
    }

    public boolean isButtonPressed(GameModel mGame) {
        Gdx.input.setInputProcessor(mGame.getmStage());
        mGame.getmStage().addActor(mGame.getmRestartButton());
        mGame.getmRestartButton().addListener(new ClickListener() {
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

    public void setGameRunning(boolean pIsGameRunning) {
        isGameRunning = pIsGameRunning;
    }

    public void setMenuShowed(boolean pMenuShowed) {
        isMenuShowed = pMenuShowed;
    }

    public void setWasTouched(boolean pWasTouched) {
        wasTouched = pWasTouched;
    }
}
