package it.unibo.uniboparty.view.board.impl;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.uniboparty.model.dice.api.DiceModel;
import it.unibo.uniboparty.model.dice.impl.DiceModelImpl;
import it.unibo.uniboparty.model.player.api.Player;
import it.unibo.uniboparty.model.player.api.PlayerManager;
import it.unibo.uniboparty.model.player.api.TurnResult;
import it.unibo.uniboparty.model.player.impl.PlayerManagerImpl;

/**
 * Main game window that shows:
 * <ul>
 *   <li>the board,</li>
 *   <li>a button to roll the dice,</li>
 *   <li>the result of the last dice roll.</li>
 * </ul>
 *
 * <p>
 * This class connects the board view, the dice model and
 * the {@link PlayerManager}. It delegates:
 * <ul>
 *   <li>movement rules to {@link PlayerManager},</li>
 *   <li>board rendering and minigame launching to {@link BoardViewImpl},</li>
 *   <li>dice generation to {@link DiceModel}.</li>
 * </ul>
 * </p>
 */
public final class MainBoardFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final BoardViewImpl boardView;
    private final DiceModel diceModel;
    private final PlayerManager playerManager;

    private final JLabel diceResultLabel;
    private final JButton rollButton;

    /**
     * Creates the main board window using the given list of player names.
     *
     * <p>
     * For each name a {@link Player} instance is created.
     * The {@link PlayerManagerImpl} is responsible for tracking
     * player positions and applying the rules of the board.
     * </p>
     *
     * @param playerNames names of the players participating in the match
     */
    public MainBoardFrame(final List<String> playerNames) {
        super("UniBoParty - Board");

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.boardView = new BoardViewImpl();

        this.diceModel = new DiceModelImpl();

        final List<Player> players = new ArrayList<>();
        for (final String name : playerNames) {
            players.add(new Player(name));
        }

        this.playerManager = new PlayerManagerImpl(
            players,
            this.boardView,
            this.boardView.getController()
        );

        this.add(this.boardView, BorderLayout.CENTER);

        final JPanel bottomPanel = new JPanel();
        this.diceResultLabel = new JLabel("Roll the dice to start");
        this.rollButton = new JButton("Roll dice");

        bottomPanel.add(this.diceResultLabel);
        bottomPanel.add(this.rollButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.rollButton.addActionListener(e -> handleRoll());

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Handles a dice roll:
     * <ol>
     *   <li>rolls the dice via the {@link DiceModel},</li>
     *   <li>asks the {@link PlayerManager} to play the turn,</li>
     *   <li>checks if the game has ended.</li>
     * </ol>
     *
     * <p>
     * If the player lands on a MINIGAME cell,
     * {@link BoardViewImpl#setPlayerPosition(int)} will automatically
     * launch the intro frame of the corresponding minigame.
     * </p>
     */
    private void handleRoll() {
        this.diceModel.roll();
        final int diceRoll = this.diceModel.getTotal();
        this.diceResultLabel.setText("You rolled: " + diceRoll);

        final TurnResult result = this.playerManager.playTurn(diceRoll);

        if (result.gameEnded()) {
            JOptionPane.showMessageDialog(
                this,
                "Game over! A player has reached the final cell."
            );
            this.rollButton.setEnabled(false);
        }
    }
}
