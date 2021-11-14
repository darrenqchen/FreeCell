package cs3500.freecell.model;

import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw02.card.ICard;

/**
 * Represents a class that creates the needed FreecellModel.
 */
public class FreecellModelCreator {

  /**
   * Represents the different game types of Freecell. Right now we just have a SINGLEMOVE
   * (SimpleFreecellModel) and MULTIMOVE (MultiFreecellModel)
   */
  public static enum GameType {
    SINGLEMOVE, MULTIMOVE;
  }

  /**
   * Creates a FreecellModel based on the GameType inputted.
   *
   * @param gameType GameType you want to create.
   * @return a IFreecellModelExtra class.
   * @throws IllegalArgumentException if an invalid GameType in inputted
   */
  public static FreecellModel<ICard> create(GameType gameType) throws IllegalArgumentException {
    switch (gameType) {
      case SINGLEMOVE:
        return new SimpleFreecellModel();
      case MULTIMOVE:
        return new MultiFreecellModel();
      default:
        throw new IllegalArgumentException("Invalid GameType inputted");
    }
  }
}
