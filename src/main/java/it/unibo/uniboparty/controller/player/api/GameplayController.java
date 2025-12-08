package it.unibo.uniboparty.controller.player.api;

import it.unibo.uniboparty.utilities.MinigameId;

/**
 * API for controlling the gameplay flow.
 */
public interface GameplayController {

    /**
     * Called when the dice have been rolled by the current player.
     *
     * @param steps the number of steps obtained from the dice
     */
    void onDiceRolled(int steps);

    /**
     * Called when a minigame ends.
     *
     * <p>Return codes:
     * <ul>
     *   <li>0 = minigame lost (-1 cell)</li>
     *   <li>1 = minigame won (+1 cell)</li>
     *   <li>2 = minigame still running</li>
     * </ul>
     * </p>
     *
     * @param result the result code of the minigame
     * @param id     the identifier of the minigame
     */
    void onMinigameFinished(int result, MinigameId id);
}
