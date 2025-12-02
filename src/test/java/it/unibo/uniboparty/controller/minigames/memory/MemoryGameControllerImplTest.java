package it.unibo.uniboparty.controller.minigames.memory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.controller.minigames.memory.impl.MemoryGameControllerImpl;

class MemoryGameControllerImplTest {

    @Test
    void testControllerCanBeCreatedAndPanelIsNotNull() {
        final MemoryGameControllerImpl controller = MemoryGameControllerImpl.create();
        final JPanel panel = controller.createMainPanel();

        assertNotNull(controller, "Controller should not be null");
        assertNotNull(panel, "Main panel should not be null");
    }

    @Test
    void testOnCardClickedDoesNotThrowForValidCoordinates() {
        final MemoryGameControllerImpl controller = MemoryGameControllerImpl.create();
        final JPanel panel = controller.createMainPanel();
        assertNotNull(panel);

        // Simulate some clicks in the 4x4 grid
        assertDoesNotThrow(() -> controller.onCardClicked(0, 0));
        assertDoesNotThrow(() -> controller.onCardClicked(1, 1));
        assertDoesNotThrow(() -> controller.onCardClicked(2, 3));
    }

    @Test
    void testOnCardClickedWithOutOfRangeCoordinatesIsHandled() {
        final MemoryGameControllerImpl controller = MemoryGameControllerImpl.create();
        controller.createMainPanel();

        // These should not crash the program
        assertDoesNotThrow(() -> controller.onCardClicked(-1, 0));
        assertDoesNotThrow(() -> controller.onCardClicked(10, 10));
    }
}
