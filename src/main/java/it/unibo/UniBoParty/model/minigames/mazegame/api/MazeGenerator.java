package it.unibo.uniboparty.model.minigames.mazegame.api;

import it.unibo.uniboparty.utilities.CellType;

/**
 * Interface for generating mazes.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface MazeGenerator {
    /**
     * Generates a maze.
     * 
     * @return a maze as a 2D array of CellType
     */
    CellType[][] generate();

}
