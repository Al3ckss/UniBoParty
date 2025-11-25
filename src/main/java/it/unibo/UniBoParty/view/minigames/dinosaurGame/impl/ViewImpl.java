package it.unibo.UniBoParty.view.minigames.dinosaurGame.impl;

import java.awt.Dimension;
import javax.swing.JFrame;

import it.unibo.UniBoParty.model.minigames.dinosaurGame.impl.ModelImpl;
import it.unibo.UniBoParty.view.minigames.dinosaurGame.api.View;

public class ViewImpl implements View {

    private final GamePanelImpl panel1;
    private boolean showGameOver = false;

    public ViewImpl(ModelImpl model) {
        JFrame frame = new JFrame("Dino Game");
        panel1 = new GamePanelImpl(model);
        frame.setMinimumSize(new Dimension(600, 500));
        frame.add(panel1);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel1.setFocusable(true);
        panel1.requestFocusInWindow();
    }

    @Override
    public void repaint() {
        panel1.repaint();
    }

    @Override
    public GamePanelImpl getPanel() {
        return panel1;
    }

    public void showGameOverOverlay() {
        showGameOver = true;
        panel1.repaint();
    }

    public boolean isGameOver() {
        return showGameOver;
    }
}
