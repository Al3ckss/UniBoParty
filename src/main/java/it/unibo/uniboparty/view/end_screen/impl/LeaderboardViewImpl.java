package it.unibo.uniboparty.view.end_screen.impl;

import it.unibo.uniboparty.model.end_screen.api.Player;
import it.unibo.uniboparty.view.end_screen.api.LeaderboardView;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

/**
 * Concrete implementation of the View.
 */
public class LeaderboardViewImpl implements LeaderboardView {

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 50;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    private final JFrame frame;
    private final JButton backButton;

    /**
     * Comment.
     */
    public LeaderboardViewImpl() {
        frame = new JFrame("Classifica - Podio");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        backButton = new JButton("Torna al Menu");
        backButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        // Pannello pulsante in basso
        final JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * Shows the podium.
     *
     * @param players The list of top players.
     */
    @Override
    public void showPodium(final List<Player> players) {
        final PodiumPanel podium = new PodiumPanel(players);
        frame.add(podium, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Registers a back listener.
     *
     * @param listener The action to perform.
     */
    @Override
    public void addBackListener(final java.awt.event.ActionListener listener) {
        backButton.addActionListener(listener);
    }

    /**
     * Closes the frame.
     */
    @Override
    public void close() {
        frame.dispose();
    }
}
