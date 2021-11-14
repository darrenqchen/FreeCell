package cs3500.hw02.model.pile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.card.CardSuite;
import cs3500.freecell.model.hw02.card.CardValue;
import cs3500.freecell.model.hw02.card.ICard;
import cs3500.freecell.model.hw02.card.PlayingCard;
import cs3500.freecell.model.hw02.pile.CascadePile;
import cs3500.freecell.model.hw02.pile.ICascadePile;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the CascadePile class methods.
 */
public class CascadePileTest {
  private ICard twoHearts;
  private ICard threeHearts;
  private ICard aceSpades;
  private ICard twoSpades;
  private ICard twoDiamonds;
  private ICard fourClubs;
  private ICascadePile<ICard> mtCascadePile;
  private ICascadePile<ICard> aceCascadePile;
  private ICascadePile<ICard> filledCascadePile;

  @Before
  public void initData() {
    ICard aceHearts = new PlayingCard(CardValue.ACE, CardSuite.HEARTS);
    this.aceSpades = new PlayingCard(CardValue.ACE, CardSuite.SPADES);
    this.twoDiamonds = new PlayingCard(CardValue.TWO, CardSuite.DIAMONDS);
    this.twoHearts = new PlayingCard(CardValue.TWO, CardSuite.HEARTS);
    this.twoSpades = new PlayingCard(CardValue.TWO, CardSuite.SPADES);
    this.threeHearts = new PlayingCard(CardValue.THREE, CardSuite.HEARTS);
    this.fourClubs = new PlayingCard(CardValue.FOUR, CardSuite.CLUBS);
    ArrayList<ICard> list = new ArrayList<ICard>(Arrays.asList(this.fourClubs, this.threeHearts));
    ArrayList<ICard> aceList = new ArrayList<ICard>();
    aceList.add(aceHearts);
    this.mtCascadePile = new CascadePile();
    this.aceCascadePile = new CascadePile(aceList);
    this.filledCascadePile = new CascadePile(list);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardNull() {
    this.mtCascadePile.addCard(null);
    this.filledCascadePile.addCard(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardInvalid() {
    // adding any card to a pile with an ace at the end
    this.aceCascadePile.addCard(this.fourClubs);
    // adding one lower but the same color and same suite
    this.filledCascadePile.addCard(this.twoHearts);
    // adding a same color but different suite and one lower
    this.filledCascadePile.addCard(this.twoDiamonds);
    // adding two lower but the different color
    this.filledCascadePile.addCard(this.aceSpades);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullCards() {
    new CascadePile(null);
  }

  @Test
  public void testAddCard() {
    assertEquals(0, this.mtCascadePile.sizeOfPile());
    this.mtCascadePile.addCard(this.fourClubs);
    assertEquals(1, this.mtCascadePile.sizeOfPile());
    assertEquals(2, this.filledCascadePile.sizeOfPile());
    this.filledCascadePile.addCard(this.twoSpades);
    assertEquals(3, this.filledCascadePile.sizeOfPile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardIfGameNotStartNullCard() {
    this.mtCascadePile.addCardIfGameNotStart(null, false);
  }

  @Test(expected = IllegalStateException.class)
  public void addCardIfGameNotStartGameStarted() {
    this.mtCascadePile.addCardIfGameNotStart(this.aceSpades, true);
  }

  @Test
  public void testAddCardIfGameNotStarted() {
    assertEquals(0, this.mtCascadePile.sizeOfPile());
    this.mtCascadePile.addCardIfGameNotStart(this.aceSpades, false);
    assertEquals(1, this.mtCascadePile.sizeOfPile());
  }

  @Test(expected = IllegalStateException.class)
  public void getCardAtEmpty() {
    this.mtCascadePile.getCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardAtInvalidIndex() {
    this.filledCascadePile.getCardAt(-1);
    this.filledCascadePile.getCardAt(3);
  }

  @Test
  public void testGetCardAt() {
    assertEquals(this.fourClubs, this.filledCascadePile.getCardAt(0));
  }

  @Test(expected = IllegalStateException.class)
  public void getLastCardEmpty() {
    this.mtCascadePile.getLastCard();
  }

  @Test
  public void testGetLastCard() {
    assertEquals(this.threeHearts, this.filledCascadePile.getLastCard());
  }

  @Test
  public void testGetPileType() {
    assertEquals(PileType.CASCADE, this.filledCascadePile.getPileType());
  }

  @Test(expected = IllegalStateException.class)
  public void removeCardAtEmpty() {
    this.mtCascadePile.removeCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeCardAtInvalidIndex() {
    this.filledCascadePile.removeCardAt(-1);
    this.filledCascadePile.removeCardAt(3);
  }

  @Test
  public void testRemoveCardAt() {
    assertEquals(2, this.filledCascadePile.sizeOfPile());
    this.filledCascadePile.removeCardAt(0);
    assertEquals(1, this.filledCascadePile.sizeOfPile());
  }

  @Test(expected = IllegalStateException.class)
  public void removeLastCardEmpty() {
    this.mtCascadePile.removeLastCard();
  }

  @Test
  public void testRemoveLastCard() {
    assertEquals(2, this.filledCascadePile.sizeOfPile());
    this.filledCascadePile.removeLastCard();
    assertEquals(1, this.filledCascadePile.sizeOfPile());
  }

  @Test
  public void testSizeOfPile() {
    assertEquals(0, this.mtCascadePile.sizeOfPile());
    assertEquals(1, this.aceCascadePile.sizeOfPile());
    assertEquals(2, this.filledCascadePile.sizeOfPile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void canAddCardNull() {
    this.mtCascadePile.canAddCard(null);
    this.filledCascadePile.canAddCard(null);
  }

  @Test
  public void testCanAddCard() {
    // adding card to an empty cascade pile
    assertTrue(this.mtCascadePile.canAddCard(this.fourClubs));
    // adding a card one lower and a different color
    assertTrue(this.filledCascadePile.canAddCard(this.twoSpades));
    // adding any card to a pile with an ace at the end
    assertFalse(this.aceCascadePile.canAddCard(this.fourClubs));
    // adding one lower but the same color
    assertFalse(this.filledCascadePile.canAddCard(this.twoHearts));
    // adding a same color but different suite and one lower
    assertFalse(this.filledCascadePile.canAddCard(this.twoDiamonds));
    // adding two lower but the different color
    assertFalse(this.filledCascadePile.canAddCard(this.aceSpades));
  }
}
