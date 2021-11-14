package cs3500.hw02.model.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.hw02.card.CardColor;
import cs3500.freecell.model.hw02.card.CardSuite;
import cs3500.freecell.model.hw02.card.CardValue;
import cs3500.freecell.model.hw02.card.ICard;
import cs3500.freecell.model.hw02.card.PlayingCard;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the PlayingCard class methods.
 */
public class PlayingCardTest {

  private ICard aceClubs;
  private ICard jackDiamonds;
  private ICard queenHearts;
  private ICard kingSpades;
  private ICard twoSpades;
  private ICard nineSpades;
  private ICard tenSpades;

  @Before
  public void initData() {
    this.aceClubs = new PlayingCard(CardValue.ACE, CardSuite.CLUBS);
    this.jackDiamonds = new PlayingCard(CardValue.JACK, CardSuite.DIAMONDS);
    this.queenHearts = new PlayingCard(CardValue.QUEEN, CardSuite.HEARTS);
    this.kingSpades = new PlayingCard(CardValue.KING, CardSuite.SPADES);
    this.twoSpades = new PlayingCard(CardValue.TWO, CardSuite.SPADES);
    this.nineSpades = new PlayingCard(CardValue.NINE, CardSuite.SPADES);
    this.tenSpades = new PlayingCard(CardValue.TEN, CardSuite.SPADES);
  }

  // tests for the constructor
  @Test(expected = IllegalArgumentException.class)
  public void constructorNullCardValue() {
    new PlayingCard(null, CardSuite.CLUBS);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullSuite() {
    new PlayingCard(CardValue.ACE, null);
  }

  @Test
  public void testIsDifferentColorBlackBlack() {
    assertFalse(this.aceClubs.isDifferentColor(this.aceClubs));
    assertFalse(this.aceClubs.isDifferentColor(this.kingSpades));
    assertFalse(this.kingSpades.isDifferentColor(this.aceClubs));
    assertFalse(this.kingSpades.isDifferentColor(this.kingSpades));
  }

  @Test
  public void testIsDifferentColorBlackRed() {
    assertTrue(this.aceClubs.isDifferentColor(this.jackDiamonds));
    assertTrue(this.aceClubs.isDifferentColor(this.queenHearts));
    assertTrue(this.kingSpades.isDifferentColor(this.jackDiamonds));
    assertTrue(this.kingSpades.isDifferentColor(this.queenHearts));
  }

  @Test
  public void testIsDifferentColorRedRed() {
    assertFalse(this.jackDiamonds.isDifferentColor(this.jackDiamonds));
    assertFalse(this.jackDiamonds.isDifferentColor(this.queenHearts));
    assertFalse(this.queenHearts.isDifferentColor(this.jackDiamonds));
    assertFalse(this.queenHearts.isDifferentColor(this.queenHearts));
  }

  @Test
  public void testGetColor() {
    assertEquals(CardColor.BLACK, this.aceClubs.getColor());
    assertEquals(CardColor.RED, this.jackDiamonds.getColor());
    assertEquals(CardColor.RED, this.queenHearts.getColor());
    assertEquals(CardColor.BLACK, this.kingSpades.getColor());
  }

  @Test
  public void testGetValue() {
    assertEquals(1, this.aceClubs.getValue());
    assertEquals(2, this.twoSpades.getValue());
    assertEquals(9, this.nineSpades.getValue());
    assertEquals(10, this.tenSpades.getValue());
    assertEquals(11, this.jackDiamonds.getValue());
    assertEquals(12, this.queenHearts.getValue());
    assertEquals(13, this.kingSpades.getValue());
  }

  @Test
  public void testGetSuite() {
    assertEquals(CardSuite.CLUBS, this.aceClubs.getSuite());
    assertEquals(CardSuite.SPADES, this.twoSpades.getSuite());
    assertEquals(CardSuite.DIAMONDS, this.jackDiamonds.getSuite());
    assertEquals(CardSuite.HEARTS, this.queenHearts.getSuite());
  }

  @Test
  public void testToString() {
    assertEquals("A♣", aceClubs.toString());
    assertEquals("J♦", jackDiamonds.toString());
    assertEquals("Q♥", queenHearts.toString());
    assertEquals("K♠", kingSpades.toString());
    assertEquals("2♠", twoSpades.toString());
  }
}
