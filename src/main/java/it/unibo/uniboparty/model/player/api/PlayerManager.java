package it.unibo.uniboparty.model.player.api;

/**
 * API for managing multiple players in the board game.
 */
public interface PlayerManager {

    /**
     * @return the number of players in the match
     */
    int getNumberOfPlayers();

    /**
     * @return the index of the current player
     */
    int getCurrentPlayerIndex();

    /**
     * @return the position of the current player
     */
    int getCurrentPlayerPosition();

    /**
     * @param playerIndex the index of the player
     * @return the position of the specified player
     */
    int getPlayerPosition(int playerIndex);

    /**
     * Moves the turn to the next player.
     */
    void nextPlayer();

    /**
     * @param playerIndex the index of the player
     * @param amount the score amount to add
     */
    void addScore(int playerIndex, int amount);

    /**
     * @param playerIndex the index of the player
     * @return the score of the specified player
     */
    int getScore(int playerIndex);

    /**
     * @param diceRoll the dice result
     * @return a {@link TurnResult} describing the turn outcome
     */
    TurnResult playTurn(int diceRoll);
}
