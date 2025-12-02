package it.unibo.uniboparty.model.board.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.uniboparty.model.board.CellModel;
import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.model.board.api.BoardModel;
import it.unibo.uniboparty.utilities.MinigameId;

/**
 * Default implementation of the BoardModel.
 * The board is represented as an ordered list of cells.
 */
public final class BoardModelImpl implements BoardModel {

    private final List<CellModel> cells;

    /**
     * Creates the default configuration of the board.
     * The board layout is fixed and shared by all matches.
     */
    public BoardModelImpl() {
        this.cells = new ArrayList<>();

        // 0: Start
        this.cells.add(new CellModel(CellType.NORMAL, null));

        // 1–23
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_1)); // 1
        this.cells.add(new CellModel(CellType.NORMAL, null));                // 2
        this.cells.add(new CellModel(CellType.BACK_2, null));                // 3

        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_2)); // 4
        this.cells.add(new CellModel(CellType.SWAP, null));                  // 5
        this.cells.add(new CellModel(CellType.NORMAL, null));                // 6

        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_3)); // 7
        this.cells.add(new CellModel(CellType.NORMAL, null));                // 8

        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_1)); // 9 (GAME_1 repeated)
        this.cells.add(new CellModel(CellType.BACK_2, null));                // 10

        this.cells.add(new CellModel(CellType.NORMAL, null));                // 11
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_2)); // 12 (GAME_2 repeated)

        this.cells.add(new CellModel(CellType.SWAP, null));                  // 13
        this.cells.add(new CellModel(CellType.NORMAL, null));                // 14

        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_4)); // 15
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_5)); // 16

        this.cells.add(new CellModel(CellType.BACK_2, null));                // 17
        this.cells.add(new CellModel(CellType.NORMAL, null));                // 18

        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_6)); // 19
        this.cells.add(new CellModel(CellType.SWAP, null));                  // 20

        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_7)); // 21
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_8)); // 22

        // 23: Finish
        this.cells.add(new CellModel(CellType.NORMAL, null));                // 23
    }

    @Override
    public int getSize() {
        return this.cells.size();
    }

    @Override
    public CellModel getCellAt(final int index) {
        return this.cells.get(index);
    }

    @Override
    public List<CellModel> getCells() {
        return Collections.unmodifiableList(this.cells);
    }
}
