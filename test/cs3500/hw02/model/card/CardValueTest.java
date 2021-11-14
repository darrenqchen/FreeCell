package cs3500.hw02.model.card;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.card.CardValue;
import org.junit.Test;

/**
 * Tests for the CardValue class methods.
 */
public class CardValueTest {

  @Test
  public void testGetNumValue() {
    assertEquals(1, CardValue.ACE.getValue());
    assertEquals(5, CardValue.FIVE.getValue());
    assertEquals(11, CardValue.JACK.getValue());
    assertEquals(12, CardValue.QUEEN.getValue());
    assertEquals(13, CardValue.KING.getValue());
  }
}
