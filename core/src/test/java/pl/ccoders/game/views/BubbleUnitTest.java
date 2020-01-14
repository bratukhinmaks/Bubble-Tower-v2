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
    public void isScoreDec() {
        testBonus.initBonus();
        boolean testedScoreDec = testBonus.scoreDec;
        assertFalse(testedScoreDec);
    }


    @Test
    public void isFontWrightColor() {

    }

//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowsExceptionWhileAddingCurrencyWithNegativeRate() {
//        currencyConverter.addCurrency("TEST", -1);
//    }
//
//    @Test
//    public void shouldConvertsFromOneCurrencyToAnother() {
//        currencyConverter.addCurrency("TEST1", 1);
//        currencyConverter.addCurrency("TEST2", 2);
//        double result = currencyConverter.convert(10, "TEST1", "TEST2");
//        assertEquals("Converting currencies failed", 20, result, DELTA);
//    }
//
//    @Test
//    public void shouldReturnsTheSameAmount() {
//        currencyConverter.addCurrency("TEST", 2);
//        double result = currencyConverter.convert(10, "TEST", "TEST");
//        assertEquals("Converting currencies failed", 10, result, DELTA);
//    }
//
//    @Test
//    public void shouldReturnsEmptyCurrenciesArray() {
//        assertArrayEquals("Returning empty currencies failed",
//                new String[]{}, currencyConverter.getCurriencies());
//    }
}

