package cs3500.freecell.model.hw02.card;

/**
 * Represents the colors the cards can be.
 */
public enum CardColor {
  BLACK("Black"), RED("Red");

  private final String color;

  /**
   * Constructs a CardColor enum with String color.
   *
   * @param color the color.
   */
  CardColor(String color) {
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null.");
    }
    this.color = color;
  }

  /**
   * Returns the color in String format.
   *
   * @return the color.
   */
  public String getColor() {
    return this.color;
  }
}
