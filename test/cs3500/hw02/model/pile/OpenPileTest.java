package cs3500.hw02.model.pile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.card.CardSuite;
import cs3500.freecell.model.hw02.card.CardValue;
import cs3500.freecell.model.hw02.card.ICard;
import cs3500.freecell.model.hw02.card.PlayingCard;
import cs3500.freecell.model.hw02.pile.IPile;
import cs3500.freecell.model.hw02.pile.OpenPile;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the OpenPile class methods.
 */
public class OpenPileTest {
  ICard aceClubs;
  IPile<ICard> mtOpenPile;
  IPile<ICard> filledOpenPile;

  @Before
  public void initData() {
    this.aceClubs = new PlayingCard(CardValue.ACE, CardSuite.CLUBS);
    this.mtOpenPile = new OpenPile();
    ArrayList<ICard> list = new ArrayList<ICard>();
    list.add(aceClubs);
    this.filledOpenPile = new OpenPile(list);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullCards() {
    new OpenPile(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorCardsMoreThanMax() {
    ArrayList<ICard> list = new ArrayList<ICard>();
    list.add(aceClubs);
    list.add(aceClubs);
    new OpenPile(list);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardNull() {
    this.mtOpenPile.addCard(null);
    this.filledOpenPile.addCard(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardInvalid() {
    this.filledOpenPile.addCard(this.aceClubs);
  }

  @Test
  public void testAddCard() {
    assertEquals(0, this.mtOpenPile.sizeOfPile());
    this.mtOpenPile.addCard(this.aceClubs);
    assertEquals(1, this.mtOpenPile.sizeOfPile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardIfGameNotStartNullCard() {
    this.mtOpenPile.addCardIfGameNotStart(null, false);
  }

  @Test(expected = IllegalStateException.class)
  public void addCardIfGameNotStartGameStarted() {
    this.mtOpenPile.addCardIfGameNotStart(this.aceClubs, true);
  }

  @Test
  public void testAddCardIfGameNotStarted() {
    assertEquals(0, this.mtOpenPile.sizeOfPile());
    this.mtOpenPile.addCardIfGameNotStart(this.aceClubs, false);
    assertEquals(1, this.mtOpenPile.sizeOfPile());
  }

  @Test(expected = IllegalStateException.class)
  public void getCardAtEmptyList() {
    this.mtOpenPile.getCardAt(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardAtInvalidIndex() {
    this.filledOpenPile.getCardAt(2);
  }

  @Test
  public void testGetCardAt() {
    assertEquals(this.aceClubs, this.filledOpenPile.getCardAt(0));
  }

  @Test(expected = IllegalStateException.class)
  public void getLastCardEmpty() {
    this.mtOpenPile.getLastCard();
  }

  @Test
  public void testGetLastCard() {
    assertEquals(this.aceClubs, this.filledOpenPile.getLastCard());
  }

  @Test
  public void testGetPileType() {
    assertEquals(PileType.OPEN, this.filledOpenPile.getPileType());
    assertEquals(PileType.OPEN, this.mtOpenPile.getPileType());
  }

  @Test(expected = IllegalStateException.class)
  public void removeLastCardEmpty() {
    this.mtOpenPile.removeLastCard();
  }

  @Test
  public void testRemoveLastCard() {
    assertEquals(1, this.filledOpenPile.sizeOfPile());
    this.filledOpenPile.removeLastCard();
    assertEquals(0, this.filledOpenPile.sizeOfPile());
  }

  @Test(expected = IllegalStateException.class)
  public void removeCardAtEmpty() {
    this.mtOpenPile.removeCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeCardAtInvalidIndex() {
    this.filledOpenPile.removeCardAt(-1);
    this.filledOpenPile.removeCardAt(1);
  }

  @Test
  public void testRemoveCardAt() {
    assertEquals(1, this.filledOpenPile.sizeOfPile());
    this.filledOpenPile.removeCardAt(0);
    assertEquals(0, this.filledOpenPile.sizeOfPile());
  }

  @Test
  public void testSizeOfPile() {
    assertEquals(0, this.mtOpenPile.sizeOfPile());
    assertEquals(1, this.filledOpenPile.sizeOfPile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void canAddCardNull() {
    this.mtOpenPile.canAddCard(null);
    this.filledOpenPile.canAddCard(null);
  }

  @Test
  public void testCanAddCard() {
    assertTrue(this.mtOpenPile.canAddCard(this.aceClubs));
    assertFalse(this.filledOpenPile.canAddCard(this.aceClubs));
  }
}
