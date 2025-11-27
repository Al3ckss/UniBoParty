package it.unibo.uniboparty.model.minigames.whacamole.api;

import it.unibo.uniboparty.model.minigames.whacamole.WhacAMoleGameState;

/**
 * Model interface for the Whac-A-Mole game.
 * 
 * <p>
 * It defines the operations that the Controller can call to
 * start a match, update the logic and react to user hits.
 * </p>
 */
public interface WhacAMoleModel {
    
    /**
     * Starts a new game from scratch.
     * All scores, timers and internal flags are reset.
     */
    void startGame();

    /**
     * Updates the internal game state based on the time that has passed.
     * 
     * @param elapsedMillis numer of milliseconds passed since last tick.
     */
    void tick(long elapsedMillis);

    /**
     * Attempts to hit the hole at the given index.
     * 
     * @param index the hole index clicked by the player.
     * @return {@code true} if a mole or bomb was present and the hit was valid, {@code false} otherwise.
     */
    boolean hitHole(int index);

    /**
     * Returns an immutable snapshot of the current game state.
     * The View uses this to update the UI without modifying the Model.
     * 
     * @return the current state of the game.
     */
    WhacAMoleGameState getGameState();

    /**
     * Indicates whether the current visible object is a bomb.
     * 
     * @return {@code true} if the current visible object is a bomb, {@code false} if it is a mole or if nothing is visible
     */
    boolean isCurrentMoleABomb();
}
