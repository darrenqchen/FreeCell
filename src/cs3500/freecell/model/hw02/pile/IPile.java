package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.PileType;

/**
 * The interface represents a Pile interface that supports any sort of pile in Freecell.
 * @param <T> card type.
 */
public interface IPile<T> {

  /**
   * Adds the card to the pile.
   *
   * @param card the card being added to the pile.
   * @throws IllegalArgumentException if the card is invalid or null.
   */
  void addCard(T card) throws IllegalArgumentException;

  /**
   * Adds the card to the pile in the beginning before game starts.
   *
   * @param card the card being added to the pile.
   * @param gameStarted if the game has started or not.
   * @throws IllegalArgumentException if the card is invalid or null.
   * @throws IllegalStateException if the game has started.
   */
  void addCardIfGameNotStart(T card, boolean gameStarted)
      throws IllegalArgumentException, IllegalStateException;


  /**
   * Checks if you can add the card to a pile.
   *
   * @param card the card to be potentially added.
   * @return {@code true} if you can add to the pile; {@code false} otherwise.
   * @throws IllegalArgumentException if the card is invalid or null.
   */
  boolean canAddCard(T card) throws IllegalArgumentException;

  /**
   * Gets the card from the given index (index starts at 0).
   *
   * @return the card at the certain index
   * @throws IllegalArgumentException if the index is invalid.
   * @throws IllegalStateException if the card pile is empty.
   */
  T getCardAt(int index) throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets the last card from the pile.
   *
   * @return the last card of the pile.
   * @throws IllegalStateException if the card pile is empty.
   */
  T getLastCard() throws IllegalStateException;

  /**
   * Gets the pile type of the pile.
   *
   * @return pile type of the pile.
   */
  PileType getPileType();

  /**
   * Removes the last card from the pile.
   *
   * @throws IllegalStateException if the card pile is empty.
   */
  void removeLastCard() throws IllegalStateException;

  /**
   * Removes the card at the given index (index starts at 0).
   *
   * @throws IllegalArgumentException if the index is invalid.
   * @throws IllegalStateException if the card pile is empty.
   */
  void removeCardAt(int index) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the size of the pile.
   *
   * @return the size of the pile.
   */
  int sizeOfPile();
}
