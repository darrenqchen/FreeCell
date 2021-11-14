package cs3500.freecell.model.hw02.card;

/**
 * Represents a standard 52 deck of playing cards with values from Ace to King, four suits (Clubs,
 * Diamonds, Hearts, Spades), and the color (Red or Black).
 */
public class PlayingCard implements ICard {

  private final CardValue value;
  private final CardSuite suite;
  private final CardColor color;

  /**
   * Constructs a PlayingCard with a value and suite.
   *
   * @param value the value of the card.
   * @param suite the suite of the card.
   * @throws IllegalArgumentException when any of the arguments are null.
   */
  public PlayingCard(CardValue value, CardSuite suite)
      throws IllegalArgumentException {
    if (value == null) {
      throw new IllegalArgumentException("CardValue should not be null");
    } else if (suite == null) {
      throw new IllegalArgumentException("CardSuite should not be null");
    }
    this.value = value;
    this.suite = suite;
    if (this.suite == CardSuite.CLUBS || this.suite == CardSuite.SPADES) {
      this.color = CardColor.BLACK;
    } else { // Suite is either Diamonds or Hearts
      this.color = CardColor.RED;
    }
  }

  @Override
  public boolean isDifferentColor(ICard card) {
    return this.getColor() != card.getColor();
  }

  @Override
  public CardColor getColor() {
    return this.color;
  }

  @Override
  public CardSuite getSuite() {
    return this.suite;
  }

  @Override
  public int getValue() {
    return this.value.getValue();
  }

  /**
   * Returns the cards value and suite in the format "3♦" or "A♥". If the number is 1, 11, 12, or
   * 13, it will return A, J, Q, K respectively for the value and numbers otherwise as strings.
   *
   * @return the card's value and suite.
   */
  public String toString() {
    String symbol = this.suite.getSymbol();
    switch (this.value.getValue()) {
      case 1:
        return "A" + symbol;
      case 11:
        return "J" + symbol;
      case 12:
        return "Q" + symbol;
      case 13:
        return "K" + symbol;
      default:
        return String.valueOf(this.value.getValue()) + symbol;
    }
  }


}
