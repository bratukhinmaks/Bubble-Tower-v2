package pl.ccoders.game.views;

import com.badlogic.gdx.Gdx;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import pl.ccoders.game.model.BonusModel;
import pl.ccoders.game.model.BubbleModel;
import pl.ccoders.game.model.CanvasModel;
import pl.ccoders.game.model.FontModel;
import pl.ccoders.game.model.GameModel;
import pl.ccoders.game.model.NeedleModel;
import pl.ccoders.game.model.ObstacleModel;

public class BubbleUnitTest {
    private static final double DELTA = 0.00000000001;
    private BubbleModel testBubble;
    private GameModel testGame;
    private CanvasModel testCanvas;
    private FontModel testFonts;
    private NeedleModel testNeedle;
    private ObstacleModel testObstacle;
    private BonusModel testBonus;
    private BubbleView mCurrentBubbleView;

    @Before
    public void setUp() {
        testBubble = new BubbleModel();
        testGame = new GameModel();
        testCanvas = new CanvasModel();
        testFonts = new FontModel();
        testNeedle = new NeedleModel();
        testObstacle = new ObstacleModel();
        testBonus = new BonusModel();

    }

    @Test
    public void isScoreInc() {
        testBonus.initBonus();
        boolean testedScoreInc = testBonus.scoreInc;
        assertFalse(testedScoreInc);
    }

    @Test
    public void isNeedleNull() {
        testBonus.initBonus();
        float needleLength = 0;
        assertEquals(needleLength, testBonus.bonusNeedlesLength, 0.1);
    }

    @Test
    public void isCurrentScoreNull() {
        testBonus.initBonus();
        int score = 0;
        assertEquals(score, testBonus.bonusCurrentScore);
    }


}