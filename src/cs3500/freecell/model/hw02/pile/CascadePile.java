package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.card.ICard;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cascade pile in the game Freecell.
 */
public class CascadePile extends AbstractPile implements ICascadePile<ICard> {

  /**
   * Creates an empty cascade pile.
   */
  public CascadePile() {
    super(PileType.CASCADE);
  }

  /**
   * Creates a nonempty cascade pile.
   *
   * @param cards the cards to add inside.
   * @throws IllegalArgumentException if cards is null or greater than max value
   */
  public CascadePile(ArrayList<ICard> cards) throws IllegalArgumentException {
    super(cards, Integer.MAX_VALUE, PileType.CASCADE);
  }

  @Override
  public boolean canAddCard(ICard card) throws IllegalArgumentException {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    } else if (cards.size() == 0) { // any card can be placed on an empty cascade pile
      return true;
    } else {
      ICard lastCard = this.getLastCard();
      return lastCard.getValue() == card.getValue() + 1
          && lastCard.isDifferentColor(card);
    }
  }

  @Override
  public void addCards(List<ICard> cards) throws IllegalArgumentException {
    if (cards == null) {
      throw new IllegalArgumentException("Cards cannot be null.");
    }
    this.cards.addAll(cards);
  }

  @Override
  public List<ICard> getCardsAfterIndex(int index) throws IllegalArgumentException {
    if (index < 0 || index > this.sizeOfPile() - 1) {
      throw new IllegalArgumentException("Invalid index.");
    }
    List<ICard> list = new ArrayList<>();
    for (int i = index; i < this.sizeOfPile(); i += 1) {
      list.add(this.getCardAt(i));
    }
    return list;
  }

  @Override
  public void removeCardsAfterIndex(int index) throws IllegalArgumentException {
    if (index < 0 || index > this.sizeOfPile() - 1) {
      throw new IllegalArgumentException("Invalid index.");
    }
    for (int i = index; i < this.sizeOfPile(); i += 1) {
      this.removeCardAt(i);
      i -= 1;
    }
  }

  @Override
  public boolean isValidBuild(int index) throws IllegalArgumentException {
    if (index < 0 || index > this.sizeOfPile() - 1) {
      throw new IllegalArgumentException("Invalid index.");
    }
    for (int i = index; i < this.sizeOfPile() - 1; i += 1) {
      ICard first = this.getCardAt(i);
      ICard second = this.getCardAt(i + 1);
      if (!first.isDifferentColor(second) || first.getValue() != second.getValue() + 1) {
        return false;
      }
    }
    return true;
  }
}
