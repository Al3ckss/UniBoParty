package it.unibo.uniboparty.controller.minigames.memory.impl;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import it.unibo.uniboparty.controller.minigames.memory.api.MemoryGameController;
import it.unibo.uniboparty.model.minigames.memory.api.Card;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryDeckFactory;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameModel;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameReadOnlyState;
import it.unibo.uniboparty.model.minigames.memory.impl.MemoryDeckFactoryImpl;
import it.unibo.uniboparty.model.minigames.memory.impl.MemoryGameImpl;
import it.unibo.uniboparty.view.minigames.memory.api.MemoryGameView;
import it.unibo.uniboparty.view.minigames.memory.impl.MemoryGameViewImpl;

/**
 * Implementation of the Memory game controller.
 *
 * <p>
 * This class sits between model and view:
 * it receives user input from the view, updates the model, and then
 * asks the view to redraw itself using a read-only game state.
 * </p>
 */
public final class MemoryGameControllerImpl implements MemoryGameController {

    private static final int ROWS = 4;
    private static final int COLS = 4;

    /**
     * Delay (in milliseconds) before hiding mismatching cards.
     */
    private static final int MISMATCH_DELAY_MILLIS = 1_000;

    /**
     * Interval (in milliseconds) between two time updates.
     */
    private static final int TIME_TICK_MILLIS = 1_000;

    /**
     * Result codes for the game:
     * 2 = in progress, 1 = won, 0 = lost.
     */
    private static final int RESULT_LOST = 0;
    private static final int RESULT_WON = 1;
    private static final int RESULT_IN_PROGRESS = 2;

    /**
     * The game model that contains the core logic.
     */
    private final MemoryGameModel game;

    /**
     * The view that shows the game to the user.
     */
    private final MemoryGameView view;

    /**
     * Seconds passed since the game started.
     */
    private int secondsPassed;

    /**
     * Encoded game result: 2 = in progress, 1 = won, 0 = lost.
     */
    private int resultCode;

    /**
     * Swing timer that updates the time and the info panel every second.
     */
    private final Timer timer;

    /**
     * Private constructor: the controller creates both model and view by itself.
     *
     * <p>
     * This keeps the internal fields encapsulated and avoids exposing
     * mutable collaborators to the outside.
     * </p>
     */
    private MemoryGameControllerImpl() {
        // Create a shuffled deck (8 pairs for a 4x4 grid)
        final int numberOfPairs = ROWS * COLS / 2;
        final MemoryDeckFactory factory = new MemoryDeckFactoryImpl();
        final List<Card> deck = factory.createShuffledDeck(numberOfPairs);

        // Create the model
        this.game = new MemoryGameImpl(deck);

        // Create the view and connect it to this controller
        this.view = new MemoryGameViewImpl();
        this.view.setController(this);

        // First render of the UI
        final MemoryGameReadOnlyState initialState = this.game.getGameState();
        this.view.render(initialState);

        // Initialize timer and info panel (time + moves)
        this.secondsPassed = 0;
        this.resultCode = RESULT_IN_PROGRESS;
        this.view.updateInfoPanel(this.secondsPassed, initialState.getMoves());

        // Swing timer that fires every second to update the info panel
        this.timer = new Timer(TIME_TICK_MILLIS, e -> {
            this.secondsPassed++;
            final MemoryGameReadOnlyState current = this.game.getGameState();
            this.view.updateInfoPanel(this.secondsPassed, current.getMoves());
        });
        this.timer.start();
    }

    /**
     * Factory method used by the application to create a new controller instance.
     *
     * @return a new {@link MemoryGameControllerImpl}
     */
    public static MemoryGameControllerImpl create() {
        return new MemoryGameControllerImpl();
    }

    /**
     * Creates a Swing panel that contains the game view.
     *
     * <p>
     * The internal {@link MemoryGameView} is not returned directly:
     * instead, it is wrapped inside a simple container panel.
     * This keeps the view field encapsulated inside the controller
     * and avoids exposing internal mutable state.
     * </p>
     *
     * @return a new {@link JPanel} that can be used as the main content of a frame
     */
    public JPanel createMainPanel() {
        final JPanel container = new JPanel(new BorderLayout());
        container.add((JPanel) this.view, BorderLayout.CENTER);
        return container;
    }

    /**
     * Returns the current result code of the game.
     *
     * <p>
     * 2 = game in progress,
     * 1 = game won,
     * 0 = game lost.
     * </p>
     *
     * @return the result code
     */
    public int getResultCode() {
        return this.resultCode;
    }

    /**
     * Called by the view when the user clicks on a card in the grid.
     *
     * @param r row of the clicked card
     * @param c column of the clicked card
     */
    @Override
    public void onCardClicked(final int r, final int c) {
        // Convert 2D position (row, col) into 1D index in the deck
        final int index = r * COLS + c;

        // Try to flip the card in the model.
        // If flipCard() returns false, the move is ignored.
        final boolean accepted = this.game.flipCard(index);

        // State immediately after the flip attempt
        final MemoryGameReadOnlyState stateAfterFlip = this.game.getGameState();

        // Update the view to show what happened
        this.view.render(stateAfterFlip);
        this.view.updateInfoPanel(this.secondsPassed, stateAfterFlip.getMoves());

        // If the move was not accepted, nothing more to do
        if (!accepted) {
            return;
        }

        // Decide if the turn is complete.
        // waitingSecondFlip == true  -> only the first card has been flipped
        // waitingSecondFlip == false -> the turn is complete (2 cards flipped)
        final boolean closedTurn = !stateAfterFlip.isWaitingSecondFlip();

        // Case A: the turn is NOT closed yet (only the first card flipped)
        if (!closedTurn) {
            return;
        }

        // Case B: the turn is closed (2 cards flipped)
        // Check if there is a mismatch to handle or if the game is over
        if (this.game.hasMismatchPending()) {
            handleMismatch();
        } else if (stateAfterFlip.isGameOver()) {
            endGame();
        }
    }

    /**
     * Handles the case when the player revealed two different cards (mismatch).
     *
     * <p>
     * It waits for a short delay, hides the cards in the model,
     * and then updates the view.
     * </p>
     */
    private void handleMismatch() {
        // Disable all buttons while we wait to hide the cards
        this.view.setAllButtonsDisabled(true);

        // Single-shot Swing timer that waits before hiding the mismatch
        final Timer mismatchTimer = new Timer(MISMATCH_DELAY_MILLIS, e -> {
            // Ask the model to hide the mismatching cards and end the turn
            this.game.resolveMismatch();

            final MemoryGameReadOnlyState afterHide = this.game.getGameState();
            this.view.render(afterHide);
            this.view.updateInfoPanel(this.secondsPassed, afterHide.getMoves());

            // If the game ended after this move, stop everything
            if (afterHide.isGameOver()) {
                endGame();
            } else {
                // Otherwise, re-enable the buttons to continue playing
                this.view.setAllButtonsDisabled(false);
            }

            ((Timer) e.getSource()).stop();
        });
        mismatchTimer.setRepeats(false);
        mismatchTimer.start();
    }

    /**
     * Called when the game is over.
     * Stops the timer and disables all buttons in the view.
     */
    private void endGame() {
        this.timer.stop();
        this.view.setAllButtonsDisabled(true);

        final MemoryGameReadOnlyState finalState = this.game.getGameState();
        if (finalState.getMatchedPairs() == finalState.getTotalPairs()) {
            this.resultCode = RESULT_WON;
        } else {
            this.resultCode = RESULT_LOST;
        }
    }
}
