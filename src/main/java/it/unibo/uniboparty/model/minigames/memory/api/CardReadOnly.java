package it.unibo.uniboparty.model.minigames.memory.api;

/**
 * Read-only view of a Memory card.
 * 
 * <p>
 * This interface is used by the View, so that the UI can read card
 * information (id, symbol, revealed state) without being allowed to
 * modify it. The actual card state can only be changed through the
 * writable {@link Card} interface.
 * </p>
 */
public interface CardReadOnly {

    /**
     * @return the card id
     */
    int getId();

    /**
     * @return the card symbol
     */
    Symbol getSymbol();

    /**
     * @return {@code true} if the card is revealed, {@code false} if it is hidden
     */
    boolean isRevealed();
}
