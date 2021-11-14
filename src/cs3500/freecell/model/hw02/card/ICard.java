package cs3500.freecell.model.hw02.card;

/**
 * This interface represents a Card interface that supports any sort of playing cards being used.
 */
public interface ICard {

  /**
   * Checks if the cards are different colors.
   *
   * @param card the card you're comparing.
   * @return {@code true} if the card's are different colors, {@code false} otherwise.
   */
  public boolean isDifferentColor(ICard card);

  /**
   * Returns the CardColor enum.
   *
   * @return color of card.
   */
  public CardColor getColor();

  /**
   * Returns the card value of the card.
   *
   * @return int value of card
   */
  public int getValue();

  /**
   * Returns the symbol of the card.
   *
   * @return symbol of card
   */
  public CardSuite getSuite();

  /**
   * Returns the cards value and suite.
   *
   * @return value and suite of the card
   */
  public String toString();

}
