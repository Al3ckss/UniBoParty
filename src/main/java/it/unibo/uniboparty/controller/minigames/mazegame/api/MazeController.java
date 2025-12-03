package it.unibo.uniboparty.controller.minigames.mazegame.api;

import javax.swing.JFrame;

/**
 * Interface representing the Maze Controller.
 */
public interface MazeController {

    /**
     * Start a new game by resetting the maze and player position.
     */
    void startNewGame();

    /**
     * Gets the game view associated with this controller.
     * 
     * @return the game view
     */
    JFrame getView();
}

