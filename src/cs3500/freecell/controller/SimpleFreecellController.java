package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.card.ICard;
import cs3500.freecell.view.FreecellTextView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Simple controller part of the Freecell game.
 */
public class SimpleFreecellController implements FreecellController<ICard> {

  private final FreecellModel<ICard> model;
  private final Readable input;
  private final FreecellTextView view;

  /**
   * Creates a SimpleFreecellController object.
   *
   * @param freecellModel model part of the FreecellGame.
   * @param stringReader  Reader that reads the strings.
   * @param out           holds the string to send to the model.
   */
  public SimpleFreecellController(FreecellModel<ICard> freecellModel, Readable stringReader,
      Appendable out) {
    if (freecellModel == null) {
      throw new IllegalArgumentException("FreecellModel cannot be null.");
    } else if (stringReader == null) {
      throw new IllegalArgumentException("Readable cannot be null.");
    } else if (out == null) {
      throw new IllegalArgumentException("Appendable cannot be null.");
    }
    this.model = freecellModel;
    this.input = stringReader;
    this.view = new FreecellTextView(this.model, out);
  }

  /**
   * Returns the corresponding pile based on the letter given.
   *
   * @param s letter for which pile we want to return.
   * @return the corresponding PileType
   */
  private PileType checkPile(String s) {
    if (s == null) {
      throw new IllegalArgumentException("String cannot be null.");
    }
    switch (s) {
      case "c":
        return PileType.CASCADE;
      case "f":
        return PileType.FOUNDATION;
      case "o":
        return PileType.OPEN;
      default:
        throw new IllegalArgumentException("Not a valid pile letter");
    }
  }

  @Override
  public void playGame(List<ICard> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null");
    }
    try {
      this.model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      try {
        this.view.renderMessage("Could not start game.");
        return;
      } catch (IOException f) {
        throw new IllegalStateException("Could not render message.");
      }
    }

    Scanner scanner = new Scanner(this.input);
    String[] inputs;
    while (!this.model.isGameOver()) {
      try {
        this.view.renderBoard();
      } catch (IOException e) {
        throw new IllegalStateException("Could not render board.");
      }
      try {
        inputs = getInput(scanner);
        // moves the card with the given inputs
      } catch (IllegalArgumentException e) { // q was pressed
        try {
          this.view.renderMessage("Game quit prematurely.");
          return;
        } catch (IOException f) {
          throw new IllegalStateException("Could not render message.");
        }
      }
      try {
        this.moveCard(inputs);
      } catch (IllegalStateException e) {
        try {
          this.view.renderMessage("Invalid move. Try again.\n");
        } catch (IOException f) {
          throw new IllegalStateException("Could not render message.");
        }
      }
    }

    // you won the game
    try {
      this.view.renderMessage("Game over.");
      return;
    } catch (IOException e) {
      throw new IllegalStateException("Could not render message.");
    }
  }

  /**
   * Gets the inputs (source pile, card index, and destination pile) to input into a moving card
   * method.
   *
   * @param scanner scanner to take in input from the user.
   * @return inputs to move the card.
   * @throws IllegalArgumentException when the scanner is null or a "q" was inputted.
   */
  private String[] getInput(Scanner scanner) throws IllegalArgumentException {
    if (scanner == null) {
      throw new IllegalArgumentException("scanner cannot be null.");
    }
    String pile1;
    String index;
    String pile2;

    try {
      pile1 = this.getValidPile(scanner, "Enter a valid source pile.\n");
      index = this.getValidNumber(scanner, "Enter a valid card index.\n");
      pile2 = this.getValidPile(scanner, "Enter a valid destination pile.\n");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("q was inputted.");
    }

    return new String[]{
        pile1, index, pile2};
  }

  /**
   * Gets a valid pile (source or destination).
   *
   * @param scanner scanner to take in input from the user.
   * @param message message to display if it didn't get a valid pile.
   * @return a valid pile in the form of a String.
   * @throws IllegalArgumentException scanner or message is null or if "q" was inputted
   * @throws IllegalStateException    if message couldn't be rendered.
   */
  private String getValidPile(Scanner scanner, String message)
      throws IllegalArgumentException, IllegalStateException {
    if (scanner == null) {
      throw new IllegalArgumentException("scanner cannot be null.");
    } else if (message == null) {
      throw new IllegalArgumentException("message cannot be null.");
    }
    StringBuilder pile = new StringBuilder();
    while (scanner.hasNext()) {
      String token = scanner.next().toLowerCase();
      switch (token.substring(0, 1)) {
        case "q":
          throw new IllegalArgumentException("q was inputted.");
        case "c":
        case "f":
        case "o":
          try {
            pile.append(token.charAt(0)).append(Integer.parseInt(token.substring(1)));
            return pile.toString();
          } catch (NumberFormatException e) {
            try {
              this.view.renderMessage(message);
            } catch (IOException f) {
              throw new IllegalStateException("Could not render message.");
            }
          }
          break;
        default:
          try {
            this.view.renderMessage(message);
          } catch (IOException e) {
            throw new IllegalStateException("Could not render message.");
          }
      }
    }
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Ran out of inputs");
    }
    return "";
  }

  /**
   * Gets a valid number to input to the move card method.
   *
   * @param scanner scanner to take in input from the user.
   * @param message message to show if number is invalid.
   * @return a valid number in the form of a String.
   * @throws IllegalArgumentException scanner or message is null or if "q" was inputted.
   * @throws IllegalStateException    if message could not be rendered.
   */
  private String getValidNumber(Scanner scanner, String message)
      throws IllegalArgumentException, IllegalStateException {
    if (scanner == null) {
      throw new IllegalArgumentException("scanner cannot be null.");
    } else if (message == null) {
      throw new IllegalArgumentException("message cannot be null.");
    }
    StringBuilder index = new StringBuilder();
    while (scanner.hasNext()) {
      String token = scanner.next().toLowerCase();
      if (token.equals("q")) {
        throw new IllegalArgumentException("q was inputted.");
      }
      try {
        index.append(Integer.parseInt(token));
        return index.toString();
      } catch (NumberFormatException e) {
        try {
          this.view.renderMessage("Enter a valid number\n");
        } catch (IOException f) {
          throw new IllegalStateException("Could not render message.");
        }
        continue;
      }
    }
    if (!scanner.hasNext()) {
      throw new IllegalStateException("Ran out of inputs");
    }
    return "";
  }

  /**
   * Moves the card with the given inputs.
   *
   * @param inputs inputs to move the card with.
   * @throws IllegalArgumentException if inputs are null.
   * @throws IllegalStateException    if it was an invalid move.
   */
  private void moveCard(String[] inputs) throws IllegalArgumentException, IllegalStateException {
    if (inputs == null) {
      throw new IllegalArgumentException("inputs should not be null.");
    }
    PileType pile1 = this.checkPile(inputs[0].substring(0, 1));
    int pile1Index = Integer.parseInt(inputs[0].substring(1)) - 1;
    int cardIndex = Integer.parseInt(inputs[1]) - 1;
    PileType pile2 = this.checkPile(inputs[2].substring(0, 1));
    int pile2Index = Integer.parseInt(inputs[2].substring(1)) - 1;
    try {
      this.model.move(pile1, pile1Index, cardIndex, pile2, pile2Index);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Invalid move. Try again.");
    }
  }
}
