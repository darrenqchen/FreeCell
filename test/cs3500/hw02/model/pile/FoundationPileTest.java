package cs3500.hw02.model.pile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.card.CardSuite;
import cs3500.freecell.model.hw02.card.CardValue;
import cs3500.freecell.model.hw02.card.ICard;
import cs3500.freecell.model.hw02.card.PlayingCard;
import cs3500.freecell.model.hw02.pile.FoundationPile;
import cs3500.freecell.model.hw02.pile.IPile;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the FoundationPileTest methods.
 */
public class FoundationPileTest {

  ICard aceHearts;
  ICard aceClubs;
  ICard twoClubs;
  ICard twoHearts;
  ICard threeClubs;
  ICard fourClubs;
  ICard fiveClubs;
  ICard sixClubs;
  ICard sevenClubs;
  ICard eightClubs;
  ICard nineClubs;
  ICard tenClubs;
  ICard jackClubs;
  ICard queenClubs;
  ICard kingClubs;

  IPile<ICard> mtFoundationPile;
  IPile<ICard> oneFoundationPile;
  IPile<ICard> fullFoundationPile;
  ArrayList<ICard> list;
  ArrayList<ICard> oneList;

  @Before
  public void initData() {
    this.aceHearts = new PlayingCard(CardValue.ACE, CardSuite.HEARTS);
    this.aceClubs = new PlayingCard(CardValue.ACE, CardSuite.CLUBS);
    this.twoClubs = new PlayingCard(CardValue.TWO, CardSuite.CLUBS);
    this.twoHearts = new PlayingCard(CardValue.TWO, CardSuite.HEARTS);
    this.threeClubs = new PlayingCard(CardValue.THREE, CardSuite.CLUBS);
    this.fourClubs = new PlayingCard(CardValue.FOUR, CardSuite.CLUBS);
    this.fiveClubs = new PlayingCard(CardValue.FIVE, CardSuite.CLUBS);
    this.sixClubs = new PlayingCard(CardValue.SIX, CardSuite.CLUBS);
    this.sevenClubs = new PlayingCard(CardValue.SEVEN, CardSuite.CLUBS);
    this.eightClubs = new PlayingCard(CardValue.EIGHT, CardSuite.CLUBS);
    this.nineClubs = new PlayingCard(CardValue.NINE, CardSuite.CLUBS);
    this.tenClubs = new PlayingCard(CardValue.TEN, CardSuite.CLUBS);
    this.jackClubs = new PlayingCard(CardValue.JACK, CardSuite.CLUBS);
    this.queenClubs = new PlayingCard(CardValue.QUEEN, CardSuite.CLUBS);
    this.kingClubs = new PlayingCard(CardValue.KING, CardSuite.CLUBS);
    this.list = new ArrayList<ICard>();
    this.list.addAll(Arrays.asList(this.aceClubs, this.twoClubs, this.threeClubs, this.fourClubs,
        this.fiveClubs, this.sixClubs, this.sevenClubs, this.eightClubs, this.nineClubs,
        this.tenClubs, this.jackClubs, this.queenClubs, this.kingClubs));
    this.mtFoundationPile = new FoundationPile();
    this.oneList = new ArrayList<ICard>();
    this.oneList.add(this.aceClubs);
    this.oneFoundationPile = new FoundationPile(this.oneList);
    this.fullFoundationPile = new FoundationPile(list);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullCards() {
    new FoundationPile(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorCardsMoreThanMax() {
    ArrayList<ICard> fourteenList = new ArrayList<ICard>(this.list);
    fourteenList.add(this.aceClubs);
    new FoundationPile(fourteenList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardNull() {
    this.mtFoundationPile.addCard(null);
    this.fullFoundationPile.addCard(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardInvalid() {
    // adding non aces to empty foundation piles
    this.mtFoundationPile.addCard(this.twoHearts);
    this.mtFoundationPile.addCard(this.queenClubs);
    // adding card to full pile
    this.fullFoundationPile.addCard(this.aceClubs);
    // adding a wrong suite one higher
    this.oneFoundationPile.addCard(this.twoHearts);
    // clubs two higher
    this.oneFoundationPile.addCard(this.threeClubs);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardIfGameNotStartNullCard() {
    this.mtFoundationPile.addCardIfGameNotStart(null, false);
  }

  @Test(expected = IllegalStateException.class)
  public void addCardIfGameNotStartGameStarted() {
    this.mtFoundationPile.addCardIfGameNotStart(this.aceClubs, true);
  }

  @Test
  public void testAddCardIfGameNotStarted() {
    assertEquals(0, this.mtFoundationPile.sizeOfPile());
    this.mtFoundationPile.addCardIfGameNotStart(this.aceClubs, false);
    assertEquals(1, this.mtFoundationPile.sizeOfPile());
  }

  @Test
  public void testAddCard() {
    assertEquals(0, this.mtFoundationPile.sizeOfPile());
    this.mtFoundationPile.addCard(this.aceClubs);
    assertEquals(1, this.mtFoundationPile.sizeOfPile());
  }

  @Test(expected = IllegalStateException.class)
  public void getCardAtEmptyList() {
    this.mtFoundationPile.getCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardAtInvalidIndex() {
    this.fullFoundationPile.getCardAt(14);
  }

  @Test
  public void testGetCardAt() {
    assertEquals(this.aceClubs, this.fullFoundationPile.getCardAt(0));
  }


  @Test(expected = IllegalStateException.class)
  public void getLastCardEmpty() {
    this.mtFoundationPile.getLastCard();
  }

  @Test
  public void testGetLastCard() {
    assertEquals(this.aceClubs, this.oneFoundationPile.getLastCard());
    assertEquals(this.kingClubs, this.fullFoundationPile.getLastCard());
  }

  @Test
  public void testGetPileType() {
    assertEquals(PileType.FOUNDATION, this.fullFoundationPile.getPileType());
  }

  @Test(expected = IllegalStateException.class)
  public void removeCardAtEmpty() {
    this.mtFoundationPile.removeCardAt(0);
  }

  @Test(expected = IllegalStateException.class)
  public void removeCardAtAnyIndex() {
    this.fullFoundationPile.removeCardAt(-1);
    this.fullFoundationPile.removeCardAt(14);
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveCardAt() {
    this.oneFoundationPile.removeCardAt(0);
  }

  @Test(expected = IllegalStateException.class)
  public void removeLastCardEmpty() {
    this.mtFoundationPile.removeLastCard();
  }

  // cannot remove cards from FoundationPile
  @Test(expected = IllegalStateException.class)
  public void removeLastCardFoundation() {
    this.mtFoundationPile.removeLastCard();
    this.fullFoundationPile.removeLastCard();
  }

  @Test
  public void testSizeOfPile() {
    assertEquals(0, this.mtFoundationPile.sizeOfPile());
    assertEquals(1, this.oneFoundationPile.sizeOfPile());
    assertEquals(13, this.fullFoundationPile.sizeOfPile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void canAddCardNull() {
    this.mtFoundationPile.canAddCard(null);
    this.fullFoundationPile.canAddCard(null);
  }

  @Test
  public void testCanAddCard() {
    // adding aces to empty pile
    assertTrue(this.mtFoundationPile.canAddCard(this.aceClubs));
    assertTrue(this.mtFoundationPile.canAddCard(this.aceHearts));
    // adding twoClubs to aceClubs
    assertTrue(this.oneFoundationPile.canAddCard(this.twoClubs));
    // adding non-ace to empty pile
    assertFalse(this.mtFoundationPile.canAddCard(this.twoClubs));
    // adding to a full pile
    assertFalse(this.fullFoundationPile.canAddCard(this.twoClubs));
    // adding a wrong suite one higher
    assertFalse(this.oneFoundationPile.canAddCard(this.twoHearts));
    // clubs two higher
    assertFalse(this.oneFoundationPile.canAddCard(this.threeClubs));
  }

}
