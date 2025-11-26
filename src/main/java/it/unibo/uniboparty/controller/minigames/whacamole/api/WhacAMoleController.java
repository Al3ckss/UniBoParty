package it.unibo.uniboparty.controller.minigames.whacamole.api;

import it.unibo.uniboparty.model.minigames.whacamole.impl.WhacAMoleGameState;

/**
 * Controller interface for the Whac-A-Mole game.
 * 
 * The controller sits between the View and the Model:
 * - it receives user input from the View and translates it into actions on the Model,
 * - it updates the game logic (Model) based on time progression,
 * - it exposes read-only game state information to the View.
 */
public interface WhacAMoleController {
    
    /**
     * Starts a new game.
     * Implementations may also create and start internal timers to periodically advance the game logic.
     */
    void startGame();

    /**
     * Advances the game logic by the given amount of time.
     * @param elapsedMillis number of milliseconds passed since last tick.
     */
    void updateGameLogic(long elapsedMillis);

    /**
     * Called when the player clicks on a specific hole.
     * @param index the index of the hole clicked by the player.
     */
    void onHoleClicked(int index);

    /**
     * Returns the current game state snapshot for the View.
     * @return the current {@link WhacAMoleGameState}.
     */
    WhacAMoleGameState getState();

    /**
     * Indicates whether the current mole is a bomb.
     * @return true if the current mole is a bomb, false otherwise.
     */
    boolean isCurrentObjectABomb();

    /**
     * Stops any internal game loop or timer once the game is over.
     */
    void stopIfGameOver();
}
