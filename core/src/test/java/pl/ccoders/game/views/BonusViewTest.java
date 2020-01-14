//package pl.ccoders.game.views;
//
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.Test;
//
//public class BubbleUnitTest {
//    private static final double DELTA = 0.00000000001;
//    private BubbleView bubble;
//    private GameView testGame;
//
//    @Before
//    public void setUp() {
//        bubble = new BubbleView(5,5,5,5,5);
//        testGame = new GameView();
//    }
//
//    @Test
//    public void shouldDrawNewGame() {
//        testGame.drawGame();
//        assertArrayEquals("Adding new currency failed", new String[]{"TEST"},
//                currencyConverter.getCurriencies());
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowsExceptionWhileAddingCurrencyWithNullCode() {
//        currencyConverter.addCurrency(null, 1);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowsExceptionWhileAddingCurrencyWithEmptyCode() {
//        currencyConverter.addCurrency("", 1);
//    }
//
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
//}
//
