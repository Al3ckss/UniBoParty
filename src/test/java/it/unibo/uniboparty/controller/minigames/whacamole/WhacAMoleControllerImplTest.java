package it.unibo.uniboparty.controller.minigames.whacamole;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.controller.minigames.whacamole.api.WhacAMoleController;
import it.unibo.uniboparty.controller.minigames.whacamole.impl.WhacAMoleControllerImpl;
import it.unibo.uniboparty.model.minigames.whacamole.WhacAMoleGameState;

class WhacAMoleControllerImplTest {

    private static final int INITIAL_TIME_MILLIS = 30_000;

    private WhacAMoleController controller;

    @BeforeEach
    void setUp() {
        this.controller = new WhacAMoleControllerImpl();
    }

    @Test
    void testStartGameInitializesCorrectly() {
        assertDoesNotThrow(
            this.controller::startGame,
            "startGame should not throw exceptions"
        );

        final WhacAMoleGameState state = this.controller.getState();
        assertFalse(state.isGameOver());
        assertEquals(0, state.getScore());
        assertTrue(state.getTimeLeftMillis() > 0);
    }

    @Test
    void testUpdateGameLogicDoesNotThrow() {
        this.controller.startGame();

        assertDoesNotThrow(
            () -> this.controller.updateGameLogic(100),
            "updateGameLogic should not throw exceptions"
        );

        assertTrue(this.controller.getState().getTimeLeftMillis() <= INITIAL_TIME_MILLIS);
    }

    @Test
    void testHoleClickDoesNotThrow() {
        this.controller.startGame();

        assertDoesNotThrow(
            () -> this.controller.onHoleClicked(0),
            "onHoleClicked should not throw exceptions"
        );
    }

    @Test
    void testStateReturnedIsNotNull() {
        this.controller.startGame();

        final WhacAMoleGameState state = this.controller.getState();
        assertNotNull(state);
    }

    @Test
    void testBombFlagAccessible() {
        this.controller.startGame();

        assertDoesNotThrow(
            this.controller::isCurrentObjectABomb,
            "isCurrentObjectABomb should not throw exceptions"
        );
    }

    @Test
    void testStopIfGameOverDoesNotThrow() {
        this.controller.startGame();

        assertDoesNotThrow(
            this.controller::stopIfGameOver,
            "stopIfGameOver should not throw exceptions"
        );
    }
}
