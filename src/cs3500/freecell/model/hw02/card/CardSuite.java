package cs3500.freecell.model.hw02.card;

/**
 * Represents each Suite: Clubs(♣), Diamonds(♦), Hearts(♥), Spades(♠).
 */
public enum CardSuite {
  // the 4 suites of the Suite enum
  CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥"), SPADES("♠");

  private final String symbol;

  /**
   * Constructs a CardSuite with the symbol as the String value.
   *
   * @param symbol the String value.
   */
  CardSuite(String symbol) {
    if (symbol == null) {
      throw new IllegalArgumentException("Symbol cannot be null.");
    }
    this.symbol = symbol;
  }

  /**
   * Returns the symbol of the CardSuite in String format.
   *
   * @return the symbol.
   */
  public String getSymbol() {
    return this.symbol;
  }
}
