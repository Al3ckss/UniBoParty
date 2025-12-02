package it.unibo.uniboparty.model.minigames.whacamole;

/**
 * Immutable snapshot of the current game state.
 * 
 * <p>
 * The View uses this class to update the UI without being able
 * to modify the Model directly.
 * </p>
 */
public final class WhacAMoleGameState {

    private final int score;
    private final long timeLeftMillis;
    private final boolean gameOver;
    private final int currentMoleIndex;
    private final int totalHoles;

    /**
     * Creates a new game state snapshot.
     *
     * @param score current player score
     * @param timeLeftMillis time left before the game ends (in milliseconds)
     * @param gameOver {@code true} if the game is over
     * @param currentMoleIndex index (0..totalHoles-1) of the current mole/bomb,
     * or -1 if none is visible
     * @param totalHoles total number of holes in the grid
     */
    public WhacAMoleGameState(
        final int score,
        final long timeLeftMillis,
        final boolean gameOver,
        final int currentMoleIndex,
        final int totalHoles
    ) {
        this.score = score;
        this.timeLeftMillis = timeLeftMillis;
        this.gameOver = gameOver;
        this.currentMoleIndex = currentMoleIndex;
        this.totalHoles = totalHoles;
    }

    /**
     * @return the score of the player
     */
    public int getScore() {
        return this.score;
    }

    /**
     * @return remaining time in milliseconds
     */
    public long getTimeLeftMillis() {
        return this.timeLeftMillis;
    }

    /**
     * @return {@code true} if the match has ended, {@code false} otherwise
     */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * @return the index of the current mole/bomb, or -1 if nothing is visible
     */
    public int getCurrentMoleIndex() {
        return this.currentMoleIndex;
    }

    /**
     * @return total holes
     */
    public int getTotalHoles() {
        return this.totalHoles;
    }
}
