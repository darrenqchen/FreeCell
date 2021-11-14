package cs3500.hw02.model.card;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.card.CardSuite;
import org.junit.Test;

/**
 * Tests for the Suite class methods.
 */
public class CardSuiteTest {

  @Test
  public void testGetSymbol() {
    assertEquals("♣", CardSuite.CLUBS.getSymbol());
    assertEquals("♦", CardSuite.DIAMONDS.getSymbol());
    assertEquals("♥", CardSuite.HEARTS.getSymbol());
    assertEquals("♠", CardSuite.SPADES.getSymbol());
  }
}
