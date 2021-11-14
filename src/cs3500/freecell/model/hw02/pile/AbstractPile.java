package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.card.ICard;
import java.util.ArrayList;

/**
 * Abstract base class for the implementations of {@code IPile}.
 */
public abstract class AbstractPile implements IPile<ICard> {

  protected final ArrayList<ICard> cards;
  protected final PileType pileType;

  /**
   * Creates an AbstractPile Object and sets the list of cards automatically to an empty ArrayList.
   *
   * @param pileType the PileType of the pile.
   */
  public AbstractPile(PileType pileType) {
    if (pileType == null) {
      throw new IllegalArgumentException("Pile type cannot be null.");
    }
    this.cards = new ArrayList<ICard>();
    this.pileType = pileType;
  }

  /**
   * Creates AbstractPile Object and with a given list of cards.
   *
   * @param cards     list of cards.
   * @param maxLength maximum length that the list can be.
   * @param pileType  pile type of the pile being created.
   */
  public AbstractPile(ArrayList<ICard> cards, int maxLength, PileType pileType) {
    if (cards == null) {
      throw new IllegalArgumentException("Cards cannot be null.");
    } else if (pileType == null) {
      throw new IllegalArgumentException("Pile type cannot be null.");
    } else if (cards.size() > maxLength) {
      throw new IllegalArgumentException("Cards size must be lower.");
    }
    this.cards = cards;
    this.pileType = pileType;
  }

  @Override
  public void addCard(ICard card) throws IllegalArgumentException {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    } else if (!this.canAddCard(card)) {
      throw new IllegalArgumentException("Card cannot be added.");
    } else {
      this.cards.add(card);
    }
  }

  @Override
  public void addCardIfGameNotStart(ICard card, boolean gameStarted)
      throws IllegalArgumentException, IllegalStateException {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    } else if (gameStarted) {
      throw new IllegalStateException("Game has already started.");
    } else {
      this.cards.add(card);
    }
  }

  @Override
  public ICard getCardAt(int index) throws IllegalArgumentException, IllegalStateException {
    if (this.sizeOfPile() == 0) {
      throw new IllegalStateException("The list of cards is empty.");
    } else if (index < 0 || index > this.sizeOfPile() - 1) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    return this.cards.get(index);
  }

  @Override
  public ICard getLastCard() throws IllegalStateException {
    if (this.sizeOfPile() == 0) {
      throw new IllegalStateException("The list of cards is empty.");
    }
    return this.cards.get(this.sizeOfPile() - 1);
  }

  @Override
  public PileType getPileType() {
    return this.pileType;
  }

  @Override
  public void removeCardAt(int index) throws IllegalArgumentException, IllegalStateException {
    if (this.sizeOfPile() == 0) {
      throw new IllegalStateException("The list is already empty");
    } else if (index < 0 || index > this.sizeOfPile() - 1) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    this.cards.remove(index);
  }

  @Override
  public void removeLastCard() throws IllegalStateException {
    if (this.sizeOfPile() == 0) {
      throw new IllegalStateException("The list is already empty");
    }
    this.cards.remove(this.sizeOfPile() - 1);
  }

  @Override
  public int sizeOfPile() {
    return this.cards.size();
  }
}
