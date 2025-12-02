package it.unibo.uniboparty.view.minigames.whacamole.api;

/**
 * View interface for the Whac-A-Mole game.
 * 
 * <p>
 * The View is responsible for showing the game to the player and receiving input (such as hole clicks).
 * At the end of the match, the main board can ask the View to provide the player's final score.
 * </p>
 */
@FunctionalInterface
public interface WhacAMoleView {

    /**
     * Returns the final score of the match.
     * 
     * <p>
     * This method is expected to be called only after the game is finished,
     * in order to assign points to the player in the main game.
     * </p>
     * 
     * @return the final score achieved by the player in the match.
     */
    int getFinalScore();
}
