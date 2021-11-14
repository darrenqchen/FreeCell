package cs3500.freecell.model;

import cs3500.freecell.model.hw02.card.ICard;
import cs3500.freecell.model.hw02.pile.ICascadePile;
import cs3500.freecell.model.hw02.pile.IPile;
import java.util.List;

/**
 * Represents a Freecell game that allows you to move multiple cards at once unlike the
 * SimpleFreecellModel that can only move one at a time.
 */
public class MultiFreecellModel extends AFreecellModel {

  /**
   * Creates a {@code MultiFreecellModel} with the piles initialized.
   */
  public MultiFreecellModel() {
    super();
  }

  @Override
  protected void moveHelp(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException {
    // if it's a single move
    if (this.getPile(source).get(pileNumber).sizeOfPile() - 1 == cardIndex) {
      IPile<ICard> sourcePile = this.getPile(source).get(pileNumber);
      IPile<ICard> destPile = this.getPile(destination).get(destPileNumber);
      destPile.addCard(sourcePile.getCardAt(cardIndex));
      sourcePile.removeCardAt(cardIndex);
      return;
    } else if (source == PileType.CASCADE && destination == PileType.CASCADE) {
      ICascadePile<ICard> sourceCascadePile = this.cascadePiles.get(pileNumber);
      ICascadePile<ICard> destCascadePile = this.cascadePiles.get(destPileNumber);
      List<ICard> cards = sourceCascadePile.getCardsAfterIndex(cardIndex);
      if (sourceCascadePile.isValidBuild(cardIndex) && destCascadePile.canAddCard(cards.get(0))
          && maxNumMovableCards() >= cards.size()) {
        destCascadePile.addCards(cards);
        sourceCascadePile.removeCardsAfterIndex(cardIndex);
        return;
      }
    }
    throw new IllegalArgumentException("Could not move.");
  }
}




