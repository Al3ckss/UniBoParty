package it.unibo.uniboparty.view.minigames.dinosaurgame.api;

import javax.swing.JPanel;

/**
 * Interface defining the main operations of the game view.
 */
public interface View {

    /**
     * Repaints the updated game view.
     */
    void repaint();

    /**
     * Returns the main panel where the dinosaur and obstacles are drawn.
     *
     * @return the main JPanel of the view
     */
    JPanel getPanel();
}
