package cs3500.hw02.model.card;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.card.CardColor;
import org.junit.Test;

/**
 * Tests for the Color class methods.
 */
public class CardColorTest {

  @Test
  public void testGetColor() {
    assertEquals("Red", CardColor.RED.getColor());
    assertEquals("Black", CardColor.BLACK.getColor());
  }
}
