package cs3500.hw02.view;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.MultiFreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw02.card.ICard;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import cs3500.hw02.model.AFreecellModelTest;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the FreecellTextView class.
 */
public abstract class AFreecellTextViewTest {

  FreecellModel<ICard> model;
  FreecellTextView view;

  @Before
  public void initData() {
    this.model = makeModel();
    this.view = new FreecellTextView(this.model);
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

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullModel() {
    new FreecellTextView(null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullAppendable() {
    new FreecellTextView(makeModel(), null);
  }

  @Test
  public void toStringNoPileEmpty() {
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
    assertEquals("F1: A♠\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♥\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣", this.view.toString());
    System.out.println(this.view.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void renderBoardIOException() {
    Appendable log = new StringBuilder();
    FreecellView view = new MockFreecellTextView(log);
    try {
      view.renderBoard();
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void testRenderBoardNoPileEmpty() {
    Appendable log = new StringBuilder();
    this.view = new FreecellTextView(this.model, log);
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
    try {
      this.view.renderBoard();
    } catch (IOException e) {
      //
    }
    assertEquals("F1: A♠\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♥\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n", log.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void renderMessageNullMessage() {
    try {
      this.view.renderMessage(null);
    } catch (IOException e) {
      //
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void renderMessageIOException() {
    Appendable log = new StringBuilder();
    FreecellView view = new MockFreecellTextView(log);
    try {
      view.renderMessage("Should throw exception.");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void renderMessageNotEmpty() {
    Appendable log = new StringBuilder();
    this.view = new FreecellTextView(this.model, log);
    try {
      this.view.renderMessage("Testing renderMessage() out.");
    } catch (IOException e) {
      //
    }
    assertEquals("Testing renderMessage() out.", log.toString());
  }

  @Test
  public void renderBoardAndMessage() {
    Appendable log = new StringBuilder();
    this.view = new FreecellTextView(this.model, log);
    this.model.startGame(this.model.getDeck(), 8, 4, false);
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
    try {
      this.view.renderBoard();
    } catch (IOException e) {
      //
    }
    try {
      this.view.renderMessage("Testing renderMessage() out.");
    } catch (IOException e) {
      //
    }
    assertEquals("F1: A♠\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♥\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Testing renderMessage() out.", log.toString());
  }

}