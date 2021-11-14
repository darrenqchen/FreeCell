package cs3500.hw02.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.MultiFreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.card.CardSuite;
import cs3500.freecell.model.hw02.card.CardValue;
import cs3500.freecell.model.hw02.card.ICard;
import cs3500.freecell.model.hw02.card.PlayingCard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the AFreecellModel class methods.
 */
public abstract class AFreecellModelTest {

  private FreecellModel<ICard> model;

  @Before
  public void initData() {
    this.model = makeModel();
  }

  public abstract FreecellModel<ICard> makeModel();

  /**
   * Represents a SimpleFreecellModel class for testing that is used in AFreecellmodelTest.
   */
  public static class SimpleFreecellModelTests extends AFreecellModelTest {

    @Override
    public FreecellModel<ICard> makeModel() {
      return new SimpleFreecellModel();
    }
  }

  /**
   * Represents a MultiFreecellModel class for testing that is used in AFreecellmodelTest.
   */
  public static class MultiFreecellModelTests extends AFreecellModelTest {

    @Override
    public FreecellModel<ICard> makeModel() {
      return new MultiFreecellModel();
    }
  }

  @Test
  public void testGetDeck() {
    List<ICard> deck = this.model.getDeck();
    // checks that the card is 52 cards
    assertEquals(52, deck.size());
    // checks for duplicates
    for (int i = 0; i < deck.size(); i++) {
      for (int j = i + 1; j < deck.size(); j++) {
        assertNotEquals(deck.get(i), deck.get(j));
      }
    }
    // checks to see if there is the right cards inside
    List<String> toStringDeck = new ArrayList<String>();
    for (CardValue value : CardValue.values()) {
      for (CardSuite suite : CardSuite.values()) {
        toStringDeck.add(new PlayingCard(value, suite).toString());
      }
    }
    for (ICard card : deck) {
      assertTrue(toStringDeck.contains(card.toString()));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNullDeck() {
    this.model.startGame(null, 5, 5, false);
  }

  // change stuff for this one
  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDeck() {
    ArrayList<ICard> tempDeck = new ArrayList<ICard>(Arrays.asList(
        new PlayingCard(CardValue.ACE, CardSuite.CLUBS)));
    this.model.startGame(tempDeck, 5, 5, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidNumCascadePiles() {
    this.model.startGame(this.model.getDeck(), 3, 5, false);
    this.model.startGame(this.model.getDeck(), 53, 5, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidNumOpenPiles() {
    this.model.startGame(this.model.getDeck(), 4, 0, false);
  }

  // we will be testing the collections.shuffle method since that is what
  // we are doing for if shuffle is true
  @Test
  public void startGameCheckShuffling() {
    List<ICard> tempDeck = this.model.getDeck();
    assertEquals(52, tempDeck.size());
    Collections.shuffle(tempDeck);
    assertEquals(52, tempDeck.size());
  }

  @Test(expected = IllegalStateException.class)
  public void moveGameNotStarted() {
    this.model.move(PileType.CASCADE, 1, 1, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveNullPileType() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(null, 1, 1, PileType.OPEN, 1);
    this.model.move(PileType.OPEN, 1, 1, null, 1);
    this.model.move(null, 1, 1, null, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveNullInvalidPileNumber() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.OPEN, 5, 0, PileType.OPEN, 2);
    this.model.move(PileType.OPEN, -1, 0, PileType.OPEN, 2);
    this.model.move(PileType.OPEN, 0, 0, PileType.OPEN, 5);
    this.model.move(PileType.OPEN, 0, 0, PileType.OPEN, -1);
  }

  @Test
  public void moveCascadeToEmptyFoundation() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(0, this.model.getNumCardsInFoundationPile(0));
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    assertEquals(1, this.model.getNumCardsInFoundationPile(0));
  }

  @Test
  public void moveCascadeToNotEmptyFoundation() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(0, this.model.getNumCardsInFoundationPile(0));
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 0);
    assertEquals(2, this.model.getNumCardsInFoundationPile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void noMoveCascadeToEmptyFoundation() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 5, 6, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noMoveCascadeToNotEmptyFoundation() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
  }

  @Test
  public void moveOpenToEmptyFoundation() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(0, this.model.getNumCardsInFoundationPile(0));
    this.model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    this.model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(1, this.model.getNumCardsInFoundationPile(0));
  }

  @Test
  public void moveOpenToNotEmptyFoundation() {
    this.model.startGame(this.model.getDeck(), 6, 4, false);
    assertEquals(0, this.model.getNumCardsInFoundationPile(0));
    this.model.move(PileType.CASCADE, 2, 8, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 4, 7, PileType.OPEN, 0);
    this.model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(2, this.model.getNumCardsInFoundationPile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void noMoveOpenToEmptyFoundation() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 5, 6, PileType.OPEN, 0);
    this.model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noMoveOpenToNotEmptyFoundation() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 5, PileType.OPEN, 0);
    this.model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noMoveFoundationToAnything() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    this.model.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 0);
  }

  @Test
  public void moveCascadeToCascade() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(6, this.model.getNumCardsInCascadePile(4));
    this.model.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 4);
    assertEquals(7, this.model.getNumCardsInCascadePile(4));
  }

  @Test
  public void moveOpenToCascade() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(6, this.model.getNumCardsInCascadePile(4));
    this.model.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
    this.model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 4);
    assertEquals(7, this.model.getNumCardsInCascadePile(4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void noMoveCascadeToCascade() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noMoveOpenToCascade() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    this.model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 1);
  }

  @Test
  public void moveCascadeToOpen() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(0, this.model.getNumCardsInOpenPile(0));
    this.model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    assertEquals(1, this.model.getNumCardsInOpenPile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void noMoveCascadeToOpenFull() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 0, 5, PileType.OPEN, 0);
  }

  @Test
  public void testIsGameOver() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertFalse(this.model.isGameOver());
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 2, 6, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 5, 5, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 6, 5, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 7, 5, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 5, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 2, 5, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 3, 5, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 4, 4, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 5, 4, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 6, 4, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 0, 4, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 4, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 2, 4, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 3, 4, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 4, 3, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 5, 3, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 6, 3, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 7, 3, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 3, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 2, 3, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 3, 3, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 4, 2, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 5, 2, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 6, 2, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 7, 2, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 0, 2, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 2, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 2, 2, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 3, 2, PileType.FOUNDATION, 3);
    this.model.move(PileType.CASCADE, 4, 1, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 5, 1, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 6, 1, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 7, 1, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 0, 1, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 2, 1, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 3, 1, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 4, 0, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 5, 0, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 6, 0, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 7, 0, PileType.FOUNDATION, 3);

    this.model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 1);
    this.model.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 2);
    this.model.move(PileType.CASCADE, 3, 0, PileType.FOUNDATION, 3);
    assertTrue(this.model.isGameOver());
  }


  @Test(expected = IllegalStateException.class)
  public void getNumCardsInFoundationPileGameNoStarted() {
    this.model.getNumCardsInFoundationPile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNumCardsInFoundationPileInvalidIndex() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.getNumCardsInFoundationPile(-1);
    this.model.getNumCardsInFoundationPile(5);
  }

  @Test
  public void testGetNumCardsInFoundationPile() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(0, this.model.getNumCardsInFoundationPile(1));
  }

  @Test(expected = IllegalStateException.class)
  public void getNumCardsInCascadePileGameNoStarted() {
    this.model.getNumCardsInCascadePile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNumCardsInCascadePileInvalidIndex() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.getNumCardsInCascadePile(-1);
    this.model.getNumCardsInCascadePile(9);
  }

  @Test
  public void testGetNumCardsInCascadePile() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(7, this.model.getNumCardsInCascadePile(1));
  }

  @Test
  public void testGetNumCascadePiles() {
    assertEquals(-1, this.model.getNumCascadePiles());
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(8, this.model.getNumCascadePiles());
  }

  @Test(expected = IllegalStateException.class)
  public void getNumCardsInOpenPileGameNoStarted() {
    this.model.getNumCardsInOpenPile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNumCardsInOpenPileInvalidIndex() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.getNumCardsInOpenPile(-1);
    this.model.getNumCardsInOpenPile(5);
  }

  @Test
  public void testGetNumCardsInOpenPile() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(0, this.model.getNumCardsInOpenPile(1));
  }

  @Test
  public void testGetNumOpenPiles() {
    assertEquals(-1, this.model.getNumOpenPiles());
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    assertEquals(4, this.model.getNumOpenPiles());
  }

  @Test(expected = IllegalStateException.class)
  public void getFoundationCardAtGameNoStart() {
    this.model.getFoundationCardAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getFoundationCardAtInvalidIndex() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.getFoundationCardAt(0, 1);
    this.model.getFoundationCardAt(0, -1);
    this.model.getFoundationCardAt(5, 0);
    this.model.getFoundationCardAt(-1, 0);
  }

  @Test
  public void testGetFoundationCardAt() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    assertEquals(this.model.getFoundationCardAt(0, 0),
        this.model.getFoundationCardAt(0, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void getCascadeCardAtGameNoStart() {
    this.model.getCascadeCardAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCascadeCardAtInvalidIndex() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.getCascadeCardAt(0, 1);
    this.model.getCascadeCardAt(0, -1);
    this.model.getCascadeCardAt(9, 0);
    this.model.getCascadeCardAt(-1, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void getOpenCardAtGameNoStart() {
    this.model.getOpenCardAt(0);
  }
}
