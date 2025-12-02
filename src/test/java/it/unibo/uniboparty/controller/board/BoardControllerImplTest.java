package it.unibo.uniboparty.controller.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.controller.board.impl.BoardControllerImpl;
import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.model.board.api.BoardModel;
import it.unibo.uniboparty.model.board.impl.BoardModelImpl;
import it.unibo.uniboparty.utilities.MinigameId;

class BoardControllerImplTest {

    private static final int BOARD_SIZE = 24;

    private static final int CELL_INDEX_GAME_1_FIRST = 1;
    private static final int CELL_INDEX_GAME_2_FIRST = 4;
    private static final int CELL_INDEX_GAME_3 = 7;
    private static final int CELL_INDEX_GAME_1_SECOND = 9;
    private static final int CELL_INDEX_GAME_2_SECOND = 12;
    private static final int CELL_INDEX_GAME_4 = 15;
    private static final int CELL_INDEX_GAME_5 = 16;
    private static final int CELL_INDEX_GAME_6 = 19;
    private static final int CELL_INDEX_GAME_7 = 21;
    private static final int CELL_INDEX_GAME_8 = 22;

    private static final String CELL_INDEX_MESSAGE_PREFIX = "Cell at index ";

    private BoardModel model;
    private BoardController controller;

    @BeforeEach
    void setUp() {
        this.model = new BoardModelImpl();
        this.controller = new BoardControllerImpl(this.model);
    }

    @Test
    void testBoardSizeDelegatesToModel() {
        // The controller should delegate the board size to the model.
        assertEquals(this.model.getSize(), this.controller.getBoardSize());
        assertEquals(BOARD_SIZE, this.controller.getBoardSize());
    }

    @Test
    void testGetCellTypeAtReturnsCorrectTypes() {
        // MINIGAME cells
        final int[] minigameIndices = {
            CELL_INDEX_GAME_1_FIRST,
            CELL_INDEX_GAME_2_FIRST,
            CELL_INDEX_GAME_3,
            CELL_INDEX_GAME_1_SECOND,
            CELL_INDEX_GAME_2_SECOND,
            CELL_INDEX_GAME_4,
            CELL_INDEX_GAME_5,
            CELL_INDEX_GAME_6,
            CELL_INDEX_GAME_7,
            CELL_INDEX_GAME_8,
        };
        for (final int index : minigameIndices) {
            assertEquals(
                CellType.MINIGAME,
                this.controller.getCellTypeAt(index),
                CELL_INDEX_MESSAGE_PREFIX + index + " should be MINIGAME"
            );
        }

        // BACK_2 cells
        final int[] backTwoIndices = {3, 10, 17};
        for (final int index : backTwoIndices) {
            assertEquals(
                CellType.BACK_2,
                this.controller.getCellTypeAt(index),
                CELL_INDEX_MESSAGE_PREFIX + index + " should be BACK_2"
            );
        }

        // SWAP cells
        final int[] swapIndices = {5, 13, 20};
        for (final int index : swapIndices) {
            assertEquals(
                CellType.SWAP,
                this.controller.getCellTypeAt(index),
                CELL_INDEX_MESSAGE_PREFIX + index + " should be SWAP"
            );
        }

        // NORMAL cells
        final int[] normalIndices = {0, 2, 6, 8, 11, 14, 18, 23};
        for (final int index : normalIndices) {
            assertEquals(
                CellType.NORMAL,
                this.controller.getCellTypeAt(index),
                CELL_INDEX_MESSAGE_PREFIX + index + " should be NORMAL"
            );
        }
    }

    @Test
    void testGetMinigameAtReturnsCorrectIds() {
        // Check that controller returns the correct minigame id
        // for each MINIGAME cell in the fixed layout.

        assertEquals(MinigameId.GAME_1, this.controller.getMinigameAt(CELL_INDEX_GAME_1_FIRST));
        assertEquals(MinigameId.GAME_2, this.controller.getMinigameAt(CELL_INDEX_GAME_2_FIRST));
        assertEquals(MinigameId.GAME_3, this.controller.getMinigameAt(CELL_INDEX_GAME_3));

        // Repeated minigames
        assertEquals(MinigameId.GAME_1, this.controller.getMinigameAt(CELL_INDEX_GAME_1_SECOND));
        assertEquals(MinigameId.GAME_2, this.controller.getMinigameAt(CELL_INDEX_GAME_2_SECOND));

        assertEquals(MinigameId.GAME_4, this.controller.getMinigameAt(CELL_INDEX_GAME_4));
        assertEquals(MinigameId.GAME_5, this.controller.getMinigameAt(CELL_INDEX_GAME_5));
        assertEquals(MinigameId.GAME_6, this.controller.getMinigameAt(CELL_INDEX_GAME_6));
        assertEquals(MinigameId.GAME_7, this.controller.getMinigameAt(CELL_INDEX_GAME_7));
        assertEquals(MinigameId.GAME_8, this.controller.getMinigameAt(CELL_INDEX_GAME_8));
    }

    @Test
    void testGetMinigameAtReturnsNullOnNonMinigameCells() {
        // All non-MINIGAME cells should not have a minigame id.
        final int[] nonMinigameIndices = {
            0, 2, 3, 5, 6, 8, 10, 11, 13, 14, 17, 18, 20, 23,
        };

        for (final int index : nonMinigameIndices) {
            assertNull(
                this.controller.getMinigameAt(index),
                CELL_INDEX_MESSAGE_PREFIX + index + " should not have a minigame id"
            );
        }
    }

    @Test
    void testOnPlayerLandedReturnsMinigameIdForMinigameCells() {
        // onPlayerLanded should return the same id as getMinigameAt
        // when the player lands on a MINIGAME cell.

        assertEquals(MinigameId.GAME_1, this.controller.onPlayerLanded(CELL_INDEX_GAME_1_FIRST));
        assertEquals(MinigameId.GAME_2, this.controller.onPlayerLanded(CELL_INDEX_GAME_2_FIRST));
        assertEquals(MinigameId.GAME_3, this.controller.onPlayerLanded(CELL_INDEX_GAME_3));

        assertEquals(MinigameId.GAME_1, this.controller.onPlayerLanded(CELL_INDEX_GAME_1_SECOND));
        assertEquals(MinigameId.GAME_2, this.controller.onPlayerLanded(CELL_INDEX_GAME_2_SECOND));

        assertEquals(MinigameId.GAME_4, this.controller.onPlayerLanded(CELL_INDEX_GAME_4));
        assertEquals(MinigameId.GAME_5, this.controller.onPlayerLanded(CELL_INDEX_GAME_5));
        assertEquals(MinigameId.GAME_6, this.controller.onPlayerLanded(CELL_INDEX_GAME_6));
        assertEquals(MinigameId.GAME_7, this.controller.onPlayerLanded(CELL_INDEX_GAME_7));
        assertEquals(MinigameId.GAME_8, this.controller.onPlayerLanded(CELL_INDEX_GAME_8));
    }

    @Test
    void testOnPlayerLandedReturnsNullForNonMinigameCells() {
        // onPlayerLanded should return null when the cell is not a MINIGAME.
        final int[] nonMinigameIndices = {
            0, 2, 3, 5, 6, 8, 10, 11, 13, 14, 17, 18, 20, 23,
        };

        for (final int index : nonMinigameIndices) {
            assertNull(
                this.controller.onPlayerLanded(index),
                "onPlayerLanded should return null for non-MINIGAME cell at index " + index
            );
        }
    }
}
