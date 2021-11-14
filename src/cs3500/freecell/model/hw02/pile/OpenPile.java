package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.card.ICard;
import java.util.ArrayList;

/**
 * Represents the open pile of the freecell game which can only hold one card at a time.
 */
public class OpenPile extends AbstractPile {

  /**
   * Creates an OpenPile object with no card.
   */
  public OpenPile() throws IllegalArgumentException {
    super(PileType.OPEN);
  }

  /**
   * Creates an OpenPile object with a card inside.
   *
   * @param cards the cards to add inside.
   * @throws IllegalArgumentException if cards is null or cards length is greater than 1.
   */
  public OpenPile(ArrayList<ICard> cards) throws IllegalArgumentException {
    super(cards, 1, PileType.OPEN);
  }

  @Override
  public boolean canAddCard(ICard card) throws IllegalArgumentException {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    }
    // checks if the pile is occupied
    return cards.size() == 0;
  }
}
