package cs3500.freecell;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.MultiFreecellModel;
import cs3500.freecell.model.hw02.card.ICard;
import java.io.InputStreamReader;

/**
 * Runs the game if you decided to run this class.
 */
public class Main {

  /**
   * Runs the Freecell game in the Main class.
   *
   * @param args string arguments (can be empty).
   */
  public static void main(String... args) {
    FreecellController<ICard> controller = new SimpleFreecellController(new MultiFreecellModel(),
        new InputStreamReader(System.in), System.out);
    controller.playGame(new MultiFreecellModel().getDeck(), 8, 1, false);
  }
}

