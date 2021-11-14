package cs3500.hw02.model;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.AFreecellModel;
import cs3500.freecell.model.MultiFreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the MultiFreecellModel class methods that aren't in the AFreecellModel.
 */
public class MultiFreecellModelTest {

  private AFreecellModel model;

  @Before
  public void initData() {
    this.model = new MultiFreecellModel();
  }

  @Test
  public void twoCardsFromC2COneOpenNoCascade() {
    FreecellView view = new FreecellTextView(this.model);
    this.model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(7, this.model.getNumCardsInCascadePile(0));
    this.model.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 2, 6, PileType.OPEN, 1);
    this.model.move(PileType.CASCADE, 3, 6, PileType.OPEN, 2);
    this.model.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);
    this.model.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 0);
    assertEquals(8, this.model.getNumCardsInCascadePile(0));
  }

  @Test
  public void twoCardsFromC2CNoOpenOneCascade() {
    model.startGame(model.getDeck(), 8, 1, false);
    assertEquals(7, this.model.getNumCardsInCascadePile(0));
    model.move(PileType.CASCADE, 2, 6, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 4, 4, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 0, 4, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 4, 3, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 4, 2, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 0, 2, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 4, 1, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 0, 1, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 4, 0, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 7);
    model.move(PileType.CASCADE, 7, 5, PileType.CASCADE, 1);
    assertEquals(8, this.model.getNumCardsInCascadePile(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoCardsFromC2CNoOpenNoCascade() {
    model.startGame(model.getDeck(), 8, 1, false);
    model.move(PileType.CASCADE, 2, 6, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 4);
    model.move(PileType.CASCADE, 4, 5, PileType.CASCADE, 0);
  }

}
