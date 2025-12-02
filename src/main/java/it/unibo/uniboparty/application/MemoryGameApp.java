package it.unibo.uniboparty.application;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.controller.minigames.memory.impl.MemoryGameControllerImpl;

/**
 * Simple Swing application that launches the Memory minigame.
 */
public final class MemoryGameApp {

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 700;

    private MemoryGameApp() {
        // utility class: no instances
    }

    /**
     * Launches the application.
     *
     * @param args ignored
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(MemoryGameApp::createAndShowWindow);
    }

    /**
     * Creates the main Swing window and shows the Memory game.
     */
    private static void createAndShowWindow() {
        final JFrame frame = new JFrame("Memory Game");

        // Create the controller (model and view are created inside the controller)
        final MemoryGameControllerImpl controller = MemoryGameControllerImpl.create();

        // Ask the controller for the main panel that contains the view
        final JPanel mainPanel = controller.createMainPanel();

        // Add the panel to the frame
        frame.setContentPane(mainPanel);

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null); // center on screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
