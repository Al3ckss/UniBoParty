package it.unibo.uniboparty.model.minigames.mazegame.impl;

import java.util.Random;

import it.unibo.uniboparty.model.minigames.mazegame.api.MazeGenerator;
import it.unibo.uniboparty.utilities.CellType;

/**
 * Implementation of the MazeGenerator interface.
 */
public class MazeGeneratorImpl implements MazeGenerator {
    private final Random random = new Random();
    static private final CellType w = CellType.WALL;
    static private final CellType e = CellType.EMPTY;
    static private final CellType s = CellType.START;
    static private final CellType x = CellType.EXIT;

    /**
     * {@inheritDoc}
     */
    @Override
    public CellType[][] generate() {

        final int index = random.nextInt(3); 
        return switch (index) {
            case 0 -> buildMaze1();
            case 1 -> buildMaze2();
            default -> buildMaze3();
        };

    }

    private CellType[][] buildMaze1() {
        return new CellType[][] {
                {w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w},
                {w, s, e, e, w, e, e, e, e, w, e, e, e, e, w, e, e, e, e, w},
                {w, w, e, w, w, e, w, w, e, w, e, w, w, e, w, w, w, w, e, w},
                {w, e, e, e, e, e, w, e, e, w, e, e, e, w, e, e, w, e, e, w},
                {w, e, w, w, w, e, w, w, e, w, w, w, w, e, w, w, w, w, e, w},
                {w, e, e, e, w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, w, e, w, w, w, w, w, w, w, w, w, w, w, w, w, w, e, w},
                {w, e, e, e, e, e, e, e, e, w, e, e, e, e, e, e, e, w, e, w},
                {w, e, w, w, w, w, w, e, w, w, w, w, w, w, w, e, w, w, e, w},
                {w, e, e, e, e, e, w, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, w, w, e, w, w, w, w, w, w, w, w, w, w, w, w, w, e, w},
                {w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, e, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, e, w},
                {w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, w, w, w, w, e, w, w, w, w, w, w, w, w, w, w, e, w, w},
                {w, e, e, e, e, e, e, w, e, e, e, e, e, e, w, e, e, e, e, w},
                {w, e, w, w, w, w, e, w, w, w, w, w, w, e, w, w, w, w, e, w},
                {w, e, e, e, e, e, w, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, w, w, e, w, w, w, w, w, w, w, w, w, w, w, w, w, x, w},
                {w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w},
        };
        
    }

    private CellType[][] buildMaze2() {
        return new CellType[][] {
                {w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w},
                {w, s, e, w, e, e, e, e, w, e, e, w, e, e, e, e, w, e, e, w},
                {w, w, e, w, e, w, w, e, w, w, e, w, w, w, e, w, w, e, w, w},
                {w, e, e, e, e, e, w, e, e, e, e, e, e, e, w, e, e, e, e, w},
                {w, e, w, w, w, w, w, w, w, w, w, w, e, w, w, w, w, w, w, w},
                {w, e, e, e, e, e, e, e, e, e, e, w, e, e, e, e, e, e, e, w},
                {w, w, w, w, w, w, w, w, w, w, e, w, w, w, w, w, w, w, e, w},
                {w, e, e, e, e, e, e, w, e, e, e, e, e, e, e, w, e, e, e, w},
                {w, e, w, w, w, w, e, w, w, w, w, w, w, w, e, w, w, w, e, w},
                {w, e, e, e, e, e, w, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, w, w, e, w, w, w, w, w, w, w, w, w, w, w, w, w, e, w},
                {w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, e, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, e, w},
                {w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, w, e, w, w, w, w, w, e, w, w, w, e, w, w, w, w, w, w},
                {w, e, e, e, e, w, e, e, e, e, e, e, e, w, e, e, e, e, e, w},
                {w, e, w, w, w, w, e, w, w, w, w, w, w, e, w, w, w, w, e, w},
                {w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, w, w, e, e, e, e, e, w, w, w, w, w, w, w, w, w, x, w},
                {w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w},
        };

    }

    private CellType[][] buildMaze3() {

        return new CellType[][] {
                {w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w},
                {w, s, e, w, e, w, e, e, e, w, e, e, w, e, w, e, e, e, e, w},
                {w, e, w, w, e, w, w, w, e, w, w, w, w, e, w, w, w, e, w, w},
                {w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, e, w, w, w, w},
                {w, e, e, e, e, e, e, e, e, w, e, e, e, e, e, e, w, e, e, w},
                {w, e, w, w, w, w, w, e, w, w, w, w, w, w, w, e, w, w, w, w},
                {w, e, e, e, e, e, w, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, w, w, e, w, w, w, w, w, w, w, w, w, w, w, w, w, e, w},
                {w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, e, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, e, w},
                {w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, e, w, w, w, e, w, w, w, w, w, w, w, w, e, w, w, w, w},
                {w, e, e, e, e, w, e, e, e, e, e, e, e, w, e, e, e, e, e, w},
                {w, e, w, w, w, w, e, w, w, w, w, w, w, e, w, w, w, w, e, w},
                {w, e, e, e, e, e, w, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, w, w, w, e, w, w, w, w, w, w, w, w, w, w, w, w, w, e, w},
                {w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w},
                {w, e, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, x, w},
                {w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w},
        };

    }

}
