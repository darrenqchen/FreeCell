package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModel;
import java.io.IOException;

/**
 * FreecellView represented as a text view.
 */
public class FreecellTextView implements FreecellView {

  private final FreecellModel<?> model;
  private final Appendable ap;

  /**
   * Constructs a FreecellView in text form.
   *
   * @param model the model part of the Freecell game.
   * @throws IllegalArgumentException {@code model} is null.
   */
  public FreecellTextView(FreecellModel<?> model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.ap = new StringBuilder();
  }

  /**
   * Constructs a FreecellView in text form with a given Appendable.
   *
   * @param model model part of the Freecell game.
   * @param ap    given Appendable.
   * @throws IllegalArgumentException if any arguments are null.
   */
  public FreecellTextView(FreecellModel<?> model, Appendable ap) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    } else if (ap == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }
    this.model = model;
    this.ap = ap;
  }

  @Override
  public void renderBoard() throws IOException {
    try {
      this.ap.append(this.toString()).append("\n");
    } catch (IOException e) {
      System.out.println("Could not append the board to the Appendable.");
      throw new IOException();
    }
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted.
   * @throws IOException              if transmission of the board to the provided data destination
   *                                  fails.
   * @throws IllegalArgumentException if String is null.
   */
  public void renderMessage(String message) throws IOException, IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null.");
    }
    try {
      this.ap.append(message);
    } catch (IOException e) {
      System.out.println("Could not append the message to the Appendable.");
      throw new IOException();
    }
  }

  @Override
  public String toString() {
    try {
      return this.toStringFoundation()
          + this.toStringOpen() + this.toStringCascade();
    } catch (IllegalStateException e) {
      return "";
    }
  }

  /**
   * Returns the string of the cascade pile.
   *
   * @return cascade pile string.
   */
  private String toStringCascade() {
    StringBuilder cascade = new StringBuilder();
    for (int c = 0; c < this.model.getNumCascadePiles(); c += 1) {
      StringBuilder temp = new StringBuilder();
      temp.append("\nC").append(Integer.toString(c + 1)).append(":");
      for (int i = 0; i < this.model.getNumCardsInCascadePile(c); i += 1) {
        if (i == 0) {
          temp.append(" ");
        } else {
          temp.append(", ");
        }
        temp.append(this.model.getCascadeCardAt(c, i).toString());
      }
      cascade.append(temp);
    }
    return cascade.toString();
  }

  /**
   * Returns the string of the foundation pile.
   *
   * @return foundation pile string.
   */
  private String toStringFoundation() {
    StringBuilder foundation = new StringBuilder();
    for (int f = 0; f < 4; f += 1) {
      StringBuilder temp = new StringBuilder();
      if (f == 0) {
        temp.append("F").append(Integer.toString(f + 1)).append(":");
      } else {
        temp.append("\nF").append(Integer.toString(f + 1)).append(":");
      }
      for (int i = 0; i < this.model.getNumCardsInFoundationPile(f); i += 1) {
        if (i == 0) {
          temp.append(" ");
        } else {
          temp.append(", ");
        }
        temp.append(this.model.getFoundationCardAt(f, i).toString());
      }
      foundation.append(temp);
    }
    return foundation.toString();
  }

  /**
   * Returns the string of the open pile.
   *
   * @return open pile string.
   */
  private String toStringOpen() {
    StringBuilder open = new StringBuilder();
    for (int f = 0; f < this.model.getNumOpenPiles(); f += 1) {
      StringBuilder temp = new StringBuilder();
      temp.append("\nO").append(Integer.toString(f + 1)).append(":");
      if (this.model.getNumCardsInOpenPile(f) != 0) {
        temp.append(" ");
        temp.append(this.model.getOpenCardAt(f).toString());
      }
      open.append(temp);
    }
    return open.toString();
  }
}
