package it.unibo.uniboparty.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.board.api.BoardModel;
import it.unibo.uniboparty.model.board.impl.BoardModelImpl;
import it.unibo.uniboparty.utilities.MinigameId;

class BoardModelImplTest {

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

    private static final String CELL_MESSAGE_PREFIX = "Cell ";
    private static final String SHOULD_NOT_HAVE_MINIGAME_SUFFIX = " should not have a minigame";

    private BoardModel model;

    @BeforeEach
    void setUp() {
        this.model = new BoardModelImpl();
    }

    @Test
    void testBoardSizeIsCorrect() {
        // The board layout is fixed and should contain 24 cells.
        assertEquals(BOARD_SIZE, this.model.getSize());
    }

    @Test
    void testCellsAreNotNull() {
        // Every position in the board should contain a valid CellModel instance.
        for (int i = 0; i < this.model.getSize(); i++) {
            assertNotNull(
                this.model.getCellAt(i),
                CELL_MESSAGE_PREFIX + i + " should not be null"
            );
        }
    }

    @Test
    void testMinigameCellsHaveCorrectTypeAndId() {
        // Check a subset of minigame cells to verify both type and MinigameId.

        // index 1: first GAME_1
        assertEquals(CellType.MINIGAME, this.model.getCellAt(CELL_INDEX_GAME_1_FIRST).getType());
        assertEquals(MinigameId.GAME_1, this.model.getCellAt(CELL_INDEX_GAME_1_FIRST).getMinigameId());

        // index 4: GAME_2
        assertEquals(CellType.MINIGAME, this.model.getCellAt(CELL_INDEX_GAME_2_FIRST).getType());
        assertEquals(MinigameId.GAME_2, this.model.getCellAt(CELL_INDEX_GAME_2_FIRST).getMinigameId());

        // index 7: GAME_3
        assertEquals(CellType.MINIGAME, this.model.getCellAt(CELL_INDEX_GAME_3).getType());
        assertEquals(MinigameId.GAME_3, this.model.getCellAt(CELL_INDEX_GAME_3).getMinigameId());

        // index 9: GAME_1 repeated
        assertEquals(CellType.MINIGAME, this.model.getCellAt(CELL_INDEX_GAME_1_SECOND).getType());
        assertEquals(MinigameId.GAME_1, this.model.getCellAt(CELL_INDEX_GAME_1_SECOND).getMinigameId());

        // index 12: GAME_2 repeated
        assertEquals(CellType.MINIGAME, this.model.getCellAt(CELL_INDEX_GAME_2_SECOND).getType());
        assertEquals(MinigameId.GAME_2, this.model.getCellAt(CELL_INDEX_GAME_2_SECOND).getMinigameId());

        // index 15: GAME_4
        assertEquals(CellType.MINIGAME, this.model.getCellAt(CELL_INDEX_GAME_4).getType());
        assertEquals(MinigameId.GAME_4, this.model.getCellAt(CELL_INDEX_GAME_4).getMinigameId());

        // index 16: GAME_5
        assertEquals(CellType.MINIGAME, this.model.getCellAt(CELL_INDEX_GAME_5).getType());
        assertEquals(MinigameId.GAME_5, this.model.getCellAt(CELL_INDEX_GAME_5).getMinigameId());

        // index 19: GAME_6
        assertEquals(CellType.MINIGAME, this.model.getCellAt(CELL_INDEX_GAME_6).getType());
        assertEquals(MinigameId.GAME_6, this.model.getCellAt(CELL_INDEX_GAME_6).getMinigameId());

        // index 21: GAME_7
        assertEquals(CellType.MINIGAME, this.model.getCellAt(CELL_INDEX_GAME_7).getType());
        assertEquals(MinigameId.GAME_7, this.model.getCellAt(CELL_INDEX_GAME_7).getMinigameId());

        // index 22: GAME_8
        assertEquals(CellType.MINIGAME, this.model.getCellAt(CELL_INDEX_GAME_8).getType());
        assertEquals(MinigameId.GAME_8, this.model.getCellAt(CELL_INDEX_GAME_8).getMinigameId());
    }

    @Test
    void testSpecialCellsHaveCorrectTypeAndNoMinigame() {
        // BACK_2 cells: they should be of type BACK_2 and not have a minigame id.
        final int[] backTwoIndices = {3, 10, 17};
        for (final int index : backTwoIndices) {
            final CellModel cell = this.model.getCellAt(index);
            assertEquals(
                CellType.BACK_2,
                cell.getType(),
                CELL_MESSAGE_PREFIX + index + " should be BACK_2"
            );
            assertNull(
                cell.getMinigameId(),
                "BACK_2 cell at index " + index + SHOULD_NOT_HAVE_MINIGAME_SUFFIX
            );
        }

        // SWAP cells: they should be of type SWAP and not have a minigame id.
        final int[] swapIndices = {5, 13, 20};
        for (final int index : swapIndices) {
            final CellModel cell = this.model.getCellAt(index);
            assertEquals(
                CellType.SWAP,
                cell.getType(),
                CELL_MESSAGE_PREFIX + index + " should be SWAP"
            );
            assertNull(
                cell.getMinigameId(),
                "SWAP cell at index " + index + SHOULD_NOT_HAVE_MINIGAME_SUFFIX
            );
        }
    }

    @Test
    void testNormalCellsHaveNoMinigame() {
        // Indices that should be NORMAL in the fixed layout.
        final int[] normalIndices = {0, 2, 6, 8, 11, 14, 18, 23};

        for (final int index : normalIndices) {
            final CellModel cell = this.model.getCellAt(index);
            assertEquals(
                CellType.NORMAL,
                cell.getType(),
                CELL_MESSAGE_PREFIX + index + " should be NORMAL"
            );
            assertNull(
                cell.getMinigameId(),
                "NORMAL cell at index " + index + SHOULD_NOT_HAVE_MINIGAME_SUFFIX
            );
        }
    }
}
