package it.unibo.uniboparty.controller.minigames.tetris.api;

public interface TetrisController {
    /**
     * Checks whether the game is over.
     * The game is considered over when there are no more valid moves available.
     * In this case, a "Game Over" message with the final score is shown.
     */
    void checkGameOver();

    /**
     * Starts the Tetris game by making the game view visible.
     */
    void startGame();
}
