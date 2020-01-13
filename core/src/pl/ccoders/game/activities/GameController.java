package pl.ccoders.game.activities;

public class GameController {
    private boolean isGameRunning, isMenuShowed;

    public void initController() {
        isGameRunning = false;
        isMenuShowed = true;
    }

    public boolean getIsMenuShowed() {
        return isMenuShowed;
    }

    public boolean getIsGameRunning() {
        return isGameRunning;
    }
}
