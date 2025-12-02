package it.unibo.uniboparty.view.board;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.view.board.impl.BoardViewImpl;
import it.unibo.uniboparty.controller.board.api.BoardController;

class BoardViewImplTest {

    private static final int BOARD_SIZE = 24;
    private static final int LAST_CELL_INDEX = BOARD_SIZE - 1;

    @Test
    void testViewCreatesController() {
        // The view should create a valid controller pointing to the board model.
        final BoardViewImpl view = new BoardViewImpl();
        final BoardController controller = view.getController();

        assertNotNull(controller, "The controller should not be null");
        assertEquals(BOARD_SIZE, controller.getBoardSize(), "The board size should match the fixed layout");
    }

    @Test
    void testSetPlayerPositionWorksForValidIndices() {
        // Calling setPlayerPosition on valid indices should not throw exceptions.
        final BoardViewImpl view = new BoardViewImpl();

        assertDoesNotThrow(() -> view.setPlayerPosition(0), "Position 0 should be valid");
        assertDoesNotThrow(() -> view.setPlayerPosition(LAST_CELL_INDEX), "Last cell index should be valid");
    }

    @Test
    void testSetPlayerPositionThrowsForInvalidIndices() {
        // setPlayerPosition must throw IllegalArgumentException for out-of-bounds indices.
        final BoardViewImpl view = new BoardViewImpl();

        assertThrows(IllegalArgumentException.class, () -> view.setPlayerPosition(-1));
        assertThrows(IllegalArgumentException.class, () -> view.setPlayerPosition(100));
        assertThrows(IllegalArgumentException.class, () -> view.setPlayerPosition(BOARD_SIZE)); // out of bound
    }
}
