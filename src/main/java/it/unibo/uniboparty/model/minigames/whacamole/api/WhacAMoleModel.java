package it.unibo.uniboparty.model.minigames.whacamole.api;

import it.unibo.uniboparty.model.minigames.whacamole.impl.WhacAMoleGameState;

/**
 * Model interface for the Whac-A-Mole game.
 * Defines only the operations that the Controller can invoke.
 */
public interface WhacAMoleModel {
    
    /**
     * Starts a new game from scratch.
     */
    void startGame();

    /**
     * Updates the internal game state based on the time that has passed.
     * @param elapsedMillis numer of milliseconds passed since last tick.
     */
    void tick(long elapsedMillis);

    /**
     * Attempts to hit the holr at the given index.
     * @param index the hole index clicked by the player.
     * @return true if a mole or bomb was present and the hit was valid, false otherwise.
     */
    boolean hitHole(int index);

    /**
     * Returns an immutable snapshot of the current game state.
     * @return the current state of the game.
     */
    WhacAMoleGameState getGameState();

    /**
     * Indicates whether the current mole is actually a bomb.
     * @return true if the current mole is a bomb, false otherwise.
     */
    boolean isCurrentMoleABomb();
}
