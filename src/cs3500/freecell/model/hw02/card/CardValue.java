package cs3500.freecell.model.hw02.card;

/**
 * Represents each card value from Ace to King, with ints 1-13 respectively. When being shown in
 * toString() for PlayingCard, if the value is 1, 11, 12, or 13, it will be shown as A, J, Q, K
 * respectively while the numbers are just shown as numbers.
 */
public enum CardValue {
  ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
  SIX(6), SEVEN(7), EIGHT(8), NINE(9),
  TEN(10), JACK(11), QUEEN(12), KING(13);

  private final int number;

  /**
   * Constructs a CardValue enum with the number as the value.
   *
   * @param value the number value of the card.
   */
  CardValue(int value) {
    this.number = value;
  }

  /**
   * Returns the numeric value of the CardValue.
   *
   * @return the numeric value of the card.
   */
  public int getValue() {
    return this.number;
  }


}
