package cs3500.freecell.model;

import cs3500.freecell.model.hw02.card.CardSuite;
import cs3500.freecell.model.hw02.card.CardValue;
import cs3500.freecell.model.hw02.card.ICard;
import cs3500.freecell.model.hw02.card.PlayingCard;
import cs3500.freecell.model.hw02.pile.CascadePile;
import cs3500.freecell.model.hw02.pile.FoundationPile;
import cs3500.freecell.model.hw02.pile.ICascadePile;
import cs3500.freecell.model.hw02.pile.IPile;
import cs3500.freecell.model.hw02.pile.OpenPile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Creates an abstract class to represent the FreecellModel class given one of the FreecellModels.
 * Is meant to simplify the code in each of the subclasses.
 */
public abstract class AFreecellModel implements FreecellModel<ICard> {

  protected final ArrayList<ICascadePile<ICard>> cascadePiles;
  protected final ArrayList<IPile<ICard>> foundationPiles;
  protected final ArrayList<IPile<ICard>> openPiles;
  protected final List<ICard> deck;
  protected boolean gameStarted;

  /**
   * Creates an AFreecellModel object given a model (composition).
   */
  protected AFreecellModel() {
    this.cascadePiles = new ArrayList<ICascadePile<ICard>>();
    this.foundationPiles = new ArrayList<IPile<ICard>>();
    this.openPiles = new ArrayList<IPile<ICard>>();
    this.deck = new ArrayList<ICard>();
    this.gameStarted = false;
  }

  /**
   * Gets the pile based on the PileType inputted.
   *
   * @param pileType pile you want to return.
   * @return the corresponding pile.
   */
  protected List<IPile<ICard>> getPile(PileType pileType) {
    if (pileType == PileType.CASCADE) {
      return new ArrayList<IPile<ICard>>(this.cascadePiles);
    } else if (pileType == PileType.FOUNDATION) {
      return this.foundationPiles;
    } else {
      return this.openPiles;
    }
  }

  /**
   * Checks if the deck is valid.
   *
   * @param deck deck of cards.
   * @return {@code true} if the deck is valid, {@code false} otherwise.
   * @throws IllegalArgumentException if the deck is null.
   */
  protected boolean checkDeck(List<ICard> deck) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null.");
    }
    if (deck.size() != 52) { // checks the size of the deck
      return false;
    }
    // checks for duplicates
    for (int i = 0; i < deck.size(); i++) {
      for (int j = i + 1; j < deck.size(); j++) {
        if (deck.get(i).equals(deck.get(j))) {
          return false;
        }
      }
    }
    // checks to see if there is correct cards inside
    List<String> toStringDeck = new ArrayList<String>();
    for (CardValue value : CardValue.values()) {
      for (CardSuite suite : CardSuite.values()) {
        toStringDeck.add(new PlayingCard(value, suite).toString());
      }
    }
    for (ICard card : deck) {
      if (!toStringDeck.contains(card.toString())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Always returns the same list of new cards in the order as when you first open a pack of playing
   * cards.
   *
   * @return the ordered list of cards
   */
  public List<ICard> getDeck() {
    List<ICard> deckTemp = new ArrayList<ICard>();
    for (CardValue value : CardValue.values()) {
      for (CardSuite suite : CardSuite.values()) {
        deckTemp.add(new PlayingCard(value, suite));
      }
    }
    // so the last card is an Ace
    Collections.reverse(deckTemp);
    return deckTemp;
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    } else if (source == null || destination == null) {
      throw new IllegalArgumentException("PileType cannot be null.");
    } else if (pileNumber < 0 || pileNumber > this.getPile(source).size() - 1) {
      throw new IllegalArgumentException("Source pile number is invalid.");
    } else if (destPileNumber < 0 || destPileNumber > this.getPile(destination).size() - 1) {
      throw new IllegalArgumentException("Destination pile number is invalid.");
    }

    this.moveHelp(source, pileNumber, cardIndex, destination, destPileNumber);
  }


  /**
   * Helps move the cards in the given model assuming that the initial if else statements passed in
   * move().
   *
   * @param source         the type of the source pile see {@link PileType}.
   * @param pileNumber     the pile number of the given type, starting at 0.
   * @param cardIndex      the index of the card to be moved from the source pile, starting at 0.
   * @param destination    the type of the destination pile.
   * @param destPileNumber the pile number of the given type, starting at 0.
   * @throws IllegalArgumentException if the card isn't the last card to move.
   */
  protected abstract void moveHelp(PileType source, int pileNumber, int cardIndex,
      PileType destination,
      int destPileNumber) throws IllegalArgumentException;

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    this.gameStarted = false;
    if (deck == null) {
      throw new IllegalArgumentException("Deck should not be null");
    } else if (!this.checkDeck(deck)) { // checks if the deck is valid
      throw new IllegalArgumentException("Deck is invalid.");
    } else if (numCascadePiles < 4 || numCascadePiles > 52) { // cascadePiles < 4 or > 52
      throw new IllegalArgumentException("Invalid number of cascade piles.");
    } else if (numOpenPiles < 1) { // open piles < 1
      throw new IllegalArgumentException("Invalid number of open piles.");
    }
    // changes the deck to the new deck if valid
    this.deck.clear();
    this.deck.addAll(deck);

    // shuffles the deck if the shuffle is true
    if (shuffle) {
      Collections.shuffle(this.deck);
    }

    // creates the necessary cascade piles
    this.cascadePiles.clear();
    for (int i = 0; i < numCascadePiles; i += 1) {
      this.cascadePiles.add(new CascadePile());
    }

    // adds the cards in a roundrobin fashion
    for (int i = 0; i < this.deck.size(); i += 1) {
      this.cascadePiles.get(i % this.cascadePiles.size())
          // can't do this.getNumCascadePiles because startGame is false
          .addCardIfGameNotStart(this.deck.get(i), this.gameStarted);
    }

    // creates the necessary open
    this.openPiles.clear();
    for (int i = 0; i < numOpenPiles; i += 1) {
      this.openPiles.add(new OpenPile());
    }

    // clears the foundation piles
    this.foundationPiles.clear();
    this.foundationPiles.addAll(Arrays.asList(new FoundationPile(),
        new FoundationPile(), new FoundationPile(), new FoundationPile()));

    this.gameStarted = true;
  }

  @Override
  public boolean isGameOver() {
    // checks the size of the foundation piles
    for (IPile<ICard> cards : this.foundationPiles) {
      if (cards.sizeOfPile() != 13) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    } else if (index < 0 || index > this.foundationPiles.size()) {
      throw new IllegalArgumentException("Invalid index.");
    }
    return this.foundationPiles.get(index).sizeOfPile();
  }

  @Override
  public int getNumCascadePiles() {
    if (!gameStarted) {
      return -1;
    }
    return this.cascadePiles.size();
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    } else if (index < 0 || index > this.cascadePiles.size() - 1) {
      throw new IllegalArgumentException("Invalid index.");
    }
    return this.cascadePiles.get(index).sizeOfPile();
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    } else if (index < 0 || index > this.openPiles.size() - 1) {
      throw new IllegalArgumentException("Invalid index.");
    }
    return this.openPiles.get(index).sizeOfPile();
  }

  @Override
  public int getNumOpenPiles() {
    if (!gameStarted) {
      return -1;
    }
    return this.openPiles.size();
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    } else if (pileIndex < 0 || pileIndex > this.foundationPiles.size() - 1) {
      throw new IllegalArgumentException("Invalid pileIndex.");
    }
    IPile<ICard> pile = this.foundationPiles.get(pileIndex);
    if (cardIndex < 0 || cardIndex > pile.sizeOfPile() - 1) {
      throw new IllegalArgumentException("Invalid cardIndex.");
    }
    return pile.getCardAt(cardIndex);
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    } else if (pileIndex < 0 || pileIndex > this.cascadePiles.size() - 1) {
      throw new IllegalArgumentException("Invalid pileIndex.");
    }
    IPile<ICard> pile = this.cascadePiles.get(pileIndex);
    if (cardIndex < 0 || cardIndex > pile.sizeOfPile() - 1) {
      throw new IllegalArgumentException("Invalid cardIndex.");
    }
    return pile.getCardAt(cardIndex);
  }

  @Override
  public ICard getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    } else if (pileIndex < 0 || pileIndex > this.openPiles.size() - 1) {
      throw new IllegalArgumentException("Invalid pileIndex.");
    }
    IPile<ICard> pile = this.openPiles.get(pileIndex);
    if (pile.sizeOfPile() == 0) {
      return null;
    }
    return pile.getCardAt(0);
  }

  /**
   * Returns the maximum number of cards you can remove from the pile.
   *
   * @return max number of cards removable from the pile
   */
  protected int maxNumMovableCards() {
    return (this.getNumEmptyOpenPiles() + 1)
        * (int) Math.pow(2, this.getNumEmptyCascadePiles());
  }

  /**
   * Gets the number of empty open piles in the game.
   *
   * @return number of empty open piles.
   */
  private int getNumEmptyOpenPiles() {
    int emptyOpenPiles = 0;
    for (IPile<ICard> pile : this.openPiles) {
      if (pile.sizeOfPile() == 0) {
        emptyOpenPiles += 1;
      }
    }
    return emptyOpenPiles;
  }

  /**
   * Gets the number of empty cascade piles in the game.
   *
   * @return number of empty cascade piles.
   */
  private int getNumEmptyCascadePiles() {
    int emptyCascadePiles = 0;
    for (IPile<ICard> pile : this.cascadePiles) {
      if (pile.sizeOfPile() == 0) {
        emptyCascadePiles += 1;
      }
    }
    return emptyCascadePiles;
  }
}
