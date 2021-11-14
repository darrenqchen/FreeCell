package cs3500.freecell.model.hw02.pile;

import java.util.List;

/**
 * Represents an interface to include methods that will just be in cascade pile.
 */
public interface ICascadePile<T> extends IPile<T> {

  /**
   * Adds the cards to the cascade pile (We know that it is valid).
   *
   * @param cards the cards to add to the list
   * @throws IllegalArgumentException if the cards are null
   */
  void addCards(List<T> cards) throws IllegalArgumentException;

  /**
   * Gets all the cards after the index (only works with Cascade and index starts at 0).
   *
   * @param index index to start getting cards from.
   * @return list of cards after that index.
   * @throws IllegalArgumentException if the index is invalid or it's not a cascade pile.
   */
  List<T> getCardsAfterIndex(int index) throws IllegalArgumentException;

  /**
   * Checks if the cards after the index are alternating colors and consecutive, descending
   * values in the cascade pile that they are moving from.
   * @param index index to start querying the list for a valid build.
   * @return {@code true} if it is a valid build, {@code false} otherwise.
   * @throws IllegalArgumentException if the index is invalid.
   */
  boolean isValidBuild(int index) throws IllegalArgumentException;

  /**
   * Removes all the cards after the index (only works with Cascade and index starts at 0).
   *
   * @param index index to start removing cards from.
   * @throws IllegalArgumentException if the index is invalid or it's not a cascade pile.
   */
  void removeCardsAfterIndex(int index) throws IllegalArgumentException;
}
