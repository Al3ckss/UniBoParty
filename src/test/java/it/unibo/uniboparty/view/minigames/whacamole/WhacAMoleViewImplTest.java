package it.unibo.uniboparty.view.minigames.whacamole;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JButton;

import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.view.minigames.whacamole.impl.WhacAMoleViewImpl;

class WhacAMoleViewImplTest {

    @Test
    void testViewCanBeCreated() {
        WhacAMoleViewImpl view = new WhacAMoleViewImpl();
        assertNotNull(view, "The view instance should not be null");
    }

    @Test
    void testGetFinalScoreDoesNotThrowAndIsNonNegative() {
        WhacAMoleViewImpl view = new WhacAMoleViewImpl();

        try {
            int score = view.getFinalScore();
            assertTrue(score >= 0, "Final score should never be negative");
        } catch (Exception e) {
            fail("getFinalScore() should not throw exceptions");
        }
    }

    @Test
    void testStartButtonCanBeClickedWithoutErrors() {
        WhacAMoleViewImpl view = new WhacAMoleViewImpl();

        JButton startButton = findButtonByText(view, "Start Game");
        assertNotNull(startButton, "Start button should be present in the view");

        try {
            startButton.doClick();
        } catch (Exception e) {
            fail("Clicking the Start button should not throw exceptions");
        }
    }

    @Test
    void testStartButtonGetsDisabledAfterClick() {
        WhacAMoleViewImpl view = new WhacAMoleViewImpl();

        JButton startButton = findButtonByText(view, "Start Game");
        assertNotNull(startButton, "Start button should be present in the view");

        startButton.doClick();

        assertFalse(startButton.isEnabled(), 
            "After clicking Start, the button should be disabled");
    }

    private JButton findButtonByText(final Container root, final String text) {
        for (Component c : root.getComponents()) {
            if (c instanceof JButton b) {
                if (text.equals(b.getText())) {
                    return b;
                }
            } else if (c instanceof Container child) {
                JButton result = findButtonByText(child, text);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
