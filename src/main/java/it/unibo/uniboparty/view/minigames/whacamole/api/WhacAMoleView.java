package it.unibo.uniboparty.view.minigames.whacamole.api;

/**
 * View interface for the Whac-A-Mole game.
 */
public interface WhacAMoleView {
    
    /**
     * Returns the final score of the match.
     * <p>
     * It is expected to be called after the is finished, in order to assign points to the player in the main game.
     * @return the final score achieved by the player in the match.
     */
    int getFinalScore();
}
