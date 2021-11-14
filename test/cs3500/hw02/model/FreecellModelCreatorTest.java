package cs3500.hw02.model;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.MultiFreecellModel;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import org.junit.Test;

/**
 * Tests for the FreecellModelCreatorTest methods.
 */
public class FreecellModelCreatorTest {

  @Test
  public void createSingleMove() {
    assertEquals(SimpleFreecellModel.class,
        FreecellModelCreator.create(GameType.SINGLEMOVE).getClass());
  }

  @Test
  public void createMultiMove() {
    assertEquals(MultiFreecellModel.class,
        FreecellModelCreator.create(GameType.MULTIMOVE).getClass());
  }
}
