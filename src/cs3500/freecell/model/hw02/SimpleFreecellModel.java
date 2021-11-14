package cs3500.freecell.model.hw02;

import cs3500.freecell.model.AFreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.card.ICard;
import cs3500.freecell.model.hw02.pile.IPile;

/**
 * FreecellModel represented in the simplest form.
 */
public class SimpleFreecellModel extends AFreecellModel {

  /**
   * Creates a {@code SimpleFreecellModel} with the piles initialized.
   */
  public SimpleFreecellModel() {
    super();
  }

  @Override
  protected void moveHelp(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException {
    if (this.getPile(source).get(pileNumber).sizeOfPile() - 1 != cardIndex) {
      throw new IllegalArgumentException("Card index has to be the last card in the pile.");
    }
    IPile<ICard> sourcePile = this.getPile(source).get(pileNumber);
    IPile<ICard> destPile = this.getPile(destination).get(destPileNumber);
    destPile.addCard(sourcePile.getCardAt(cardIndex));
    sourcePile.removeCardAt(cardIndex);
  }
}
