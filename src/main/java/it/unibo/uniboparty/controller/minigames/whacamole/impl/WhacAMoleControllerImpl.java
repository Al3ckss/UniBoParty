package it.unibo.uniboparty.controller.minigames.whacamole.impl;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import it.unibo.uniboparty.controller.minigames.whacamole.api.WhacAMoleController;
import it.unibo.uniboparty.model.minigames.whacamole.WhacAMoleGameState;
import it.unibo.uniboparty.model.minigames.whacamole.impl.WhacAMoleGame;

/**
 * Default Swing implementation of the {@link WhacAMoleController} interface.
 * 
 * <p>
 * This Controller:
 * <ul>
 *     <li>owns a concrete Model instance,</li>
 *     <li>starts and stops an internal Swing {@link Timer} to
 *         periodically update the game logic,</li>
 *     <li>translates user input (hole clicks) into actions on the Model,</li>
 *     <li>exposes read-only game state information to the View.</li>
 * </ul>
 * </p>
 */
public final class WhacAMoleControllerImpl implements WhacAMoleController {

    /** Interval between two logic updates (in milliseconds). */
    private static final int TICK_MILLIS = 100;
    
    private final WhacAMoleGame game;
    private Timer gameLoop;

    /**
     * Creates a new controller with its own game Model.
     */
    public WhacAMoleControllerImpl() {
        this.game = new WhacAMoleGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        this.game.startGame();

        // If a previous loop exists, stop it before starting a new one
        if (this.gameLoop != null) {
            this.gameLoop.stop();
        }

        // Create a periodic task that advances the game logic
        final ActionListener task = e -> {
            updateGameLogic(TICK_MILLIS);
            stopIfGameOver();
        };

        this.gameLoop = new Timer(TICK_MILLIS, task);
        this.gameLoop.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGameLogic(final long elapsedMillis) {
        this.game.tick(elapsedMillis);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onHoleClicked(final int index) {
        this.game.hitHole(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WhacAMoleGameState getState() {
        return this.game.getGameState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCurrentObjectABomb() {
        return this.game.isCurrentMoleABomb();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopIfGameOver() {
        if (this.gameLoop != null && this.game.getGameState().isGameOver()) {
            this.gameLoop.stop();
        }
    }
}
