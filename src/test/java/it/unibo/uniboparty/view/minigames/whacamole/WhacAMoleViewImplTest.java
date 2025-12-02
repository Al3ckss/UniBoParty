package it.unibo.uniboparty.view.minigames.whacamole;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JButton;

import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.view.minigames.whacamole.impl.WhacAMoleViewImpl;

class WhacAMoleViewImplTest {

    @Test
    void testViewCanBeCreated() {
        final WhacAMoleViewImpl view = new WhacAMoleViewImpl();
        assertNotNull(view, "The view instance should not be null");
    }

    @Test
    void testGetFinalScoreDoesNotThrowAndIsNonNegative() {
        final WhacAMoleViewImpl view = new WhacAMoleViewImpl();

        // If getFinalScore() throws, the test fails automaticamente
        final int score = view.getFinalScore();
        assertTrue(score >= 0, "Final score should never be negative");
    }

    @Test
    void testStartButtonCanBeClickedWithoutErrors() {
        final WhacAMoleViewImpl view = new WhacAMoleViewImpl();

        final JButton startButton = findButtonByText(view, "Start Game");
        assertNotNull(startButton, "Start button should be present in the view");

        // Se doClick() lancia un'eccezione, il test fallisce automaticamente
        startButton.doClick();
    }

    @Test
    void testStartButtonGetsDisabledAfterClick() {
        final WhacAMoleViewImpl view = new WhacAMoleViewImpl();

        final JButton startButton = findButtonByText(view, "Start Game");
        assertNotNull(startButton, "Start button should be present in the view");

        startButton.doClick();

        assertFalse(
            startButton.isEnabled(),
            "After clicking Start, the button should be disabled"
        );
    }

    private JButton findButtonByText(final Container root, final String text) {
        for (final Component component : root.getComponents()) {
            if (component instanceof JButton) {
                final JButton button = (JButton) component;
                if (text.equals(button.getText())) {
                    return button;
                }
            } else if (component instanceof Container) {
                final Container child = (Container) component;
                final JButton result = findButtonByText(child, text);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
