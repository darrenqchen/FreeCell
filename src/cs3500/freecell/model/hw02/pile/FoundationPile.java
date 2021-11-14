package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.card.ICard;
import java.util.ArrayList;

/**
 * Represents the foundation pile in a Freecell game.
 */
public class FoundationPile extends AbstractPile {

  /**
   * Creates an empty foundation pile.
   */
  public FoundationPile() {
    super(PileType.FOUNDATION);
  }

  /**
   * Creates a FoundationPile with cards being less than 14 cards.
   *
   * @param cards the cards to add inside.
   * @throws IllegalArgumentException if cards is null or cards length is greater than 13.
   */
  public FoundationPile(ArrayList<ICard> cards) throws IllegalArgumentException {
    super(cards, 13, PileType.FOUNDATION);
  }

  @Override
  public boolean canAddCard(ICard card) throws IllegalArgumentException {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    } else if (this.sizeOfPile() == 0 && card.toString().charAt(0) == 'A') {
      // Ace is the first card added
      return true;
    } else if (this.sizeOfPile() != 0) {
      ICard lastCard = getCardAt(this.sizeOfPile() - 1);
      return lastCard.getSuite() == card.getSuite()
          // checks the card is one higher
          && lastCard.getValue() == card.getValue() - 1;
    }
    return false;
  }

  /**
   * Cannot remove a card from the foundation pile so it throws an error.
   *
   * @throws IllegalStateException when you try to remove a card.
   */
  public void removeLastCard() throws IllegalStateException {
    if (this.cards.size() == 0) {
      throw new IllegalStateException("The list is already empty");
    }
    throw new IllegalStateException("Cannot remove a card from this pile");
  }

  /**
   * Cannot remove a card from the foundation pile so it throws an error.
   *
   * @throws IllegalStateException when you try to remove a card.
   */
  public void removeCardAt(int index) throws IllegalStateException {
    if (this.cards.size() == 0) {
      throw new IllegalStateException("The list is already empty");
    }
    throw new IllegalStateException("Cannot remove a card from this pile");
  }
}
