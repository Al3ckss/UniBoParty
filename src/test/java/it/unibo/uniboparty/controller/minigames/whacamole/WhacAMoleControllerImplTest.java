package it.unibo.uniboparty.controller.minigames.whacamole;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.controller.minigames.whacamole.api.WhacAMoleController;
import it.unibo.uniboparty.controller.minigames.whacamole.impl.WhacAMoleControllerImpl;
import it.unibo.uniboparty.model.minigames.whacamole.WhacAMoleGameState;

class WhacAMoleControllerImplTest {

    private WhacAMoleController controller;

    @BeforeEach
    void setUp() {
        controller = new WhacAMoleControllerImpl();
    }

    @Test
    void testStartGameInitializesCorrectly() {
        try {
            controller.startGame();
        } catch (Exception e) {
            fail("startGame should not throw exceptions");
        }

        WhacAMoleGameState state = controller.getState();
        assertFalse(state.isGameOver());
        assertEquals(0, state.getScore());
        assertTrue(state.getTimeLeftMillis() > 0);
    }

    @Test
    void testUpdateGameLogicDoesNotThrow() {
        controller.startGame();

        try {
            controller.updateGameLogic(100);
        } catch (Exception e) {
            fail("updateGameLogic should not throw exceptions");
        }

        assertTrue(controller.getState().getTimeLeftMillis() <= 30000);
    }

    @Test
    void testHoleClickDoesNotThrow() {
        controller.startGame();

        try {
            controller.onHoleClicked(0);
        } catch (Exception e) {
            fail("onHoleClicked should not throw exceptions");
        }
    }

    @Test
    void testStateReturnedIsNotNull() {
        controller.startGame();

        WhacAMoleGameState state = controller.getState();
        assertNotNull(state);
    }

    @Test
    void testBombFlagAccessible() {
        controller.startGame();

        try {
            controller.isCurrentObjectABomb();
        } catch (Exception e) {
            fail("isCurrentObjectABomb should not throw exceptions");
        }
    }

    @Test
    void testStopIfGameOverDoesNotThrow() {
        controller.startGame();

        try {
            controller.stopIfGameOver();
        } catch (Exception e) {
            fail("stopIfGameOver should not throw exceptions");
        }
    }
}
