package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import java.awt.Dimension;
import javax.swing.JFrame;

import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;
import it.unibo.uniboparty.view.minigames.dinosaurgame.api.View;

/**
 * Implementation of the game view for the Dinosaur Game.
 */
public class ViewImpl implements View {

    private final GamePanelImpl panel1;
    private boolean showGameOver;

    /**
     * Creates the view and initializes the main window.
     *
     * @param model the game model used by the panel
     */
    public ViewImpl(final ModelImpl model) {
        final JFrame frame = new JFrame("Dino Game");
        this.panel1 = new GamePanelImpl(model);

        frame.setMinimumSize(new Dimension(600, 500));
        frame.add(this.panel1);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel1.setFocusable(true);
        this.panel1.requestFocusInWindow();
    }

    @Override
    public void repaint() {
        this.panel1.repaint();
    }

    @Override
    public GamePanelImpl getPanel() {
        return this.panel1;
    }

    /**
     * Displays the game-over overlay on the panel.
     */
    public void showGameOverOverlay() {
        this.showGameOver = true;
        this.panel1.repaint();
    }

    /**
     * @return whether the game-over overlay should be shown.
     */
    public boolean isGameOver() {
        return this.showGameOver;
    }
}
