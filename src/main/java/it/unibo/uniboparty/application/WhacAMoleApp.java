package it.unibo.uniboparty.application;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.view.minigames.whacamole.api.WhacAMoleView;
import it.unibo.uniboparty.view.minigames.whacamole.impl.WhacAMoleViewImpl;

/**
 * Simple Swing application launcher for the Whac-A-Mole minigame.
 */
public final class WhacAMoleApp {

    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 500;

    private WhacAMoleApp() {

    }

    /**
     * Entry point for the Whac-A-Mole application.
     * 
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(WhacAMoleApp::createAndShowWindow);
    }

    private static void createAndShowWindow() {
        final JFrame frame = new JFrame("Whac-A-Mole");

        final WhacAMoleView view = new WhacAMoleViewImpl();

        frame.setContentPane((WhacAMoleViewImpl) view);

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
