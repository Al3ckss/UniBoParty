package it.unibo.uniboparty.model.minigames.whacamole.impl;

import java.util.Random;

import it.unibo.uniboparty.model.minigames.whacamole.WhacAMoleGameState;
import it.unibo.uniboparty.model.minigames.whacamole.api.WhacAMoleModel;

/**
 * Core game logic for the Whac-A-Mole game.
 *
 * <p>
 * This class is responsible for:
 * <ul>
 *   <li>managing the score and the remaining time,</li>
 *   <li>deciding when and where a mole or a bomb appears,</li>
 *   <li>applying the effects of a hit on a hole.</li>
 * </ul>
 * It does not know anything about the GUI.
 * </p>
 */
public class WhacAMoleGame implements WhacAMoleModel {
    
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final int TOTAL_HOLES = ROWS * COLS;

    private static final long GAME_DURATION_MILLIS = 30_000;
    private static final long MOLE_LIFETIME_MILLIS = 800;
    private static final long MIN_TIME_BETWEEN_MOLES = 200;

    private static final double BOMB_PROBABILITY = 0.15;
    private static final int BOMB_PENALTY = 2;

    private int score;
    private long timeLeftMillis;
    private boolean gameOver;

    /**
     * Index (0..TOTAL_HOLES-1) of the currently visible mole/bomb,
     * or -1 if none is visible.
     */
    private int currentMoleIndex;

    /**
     * {@code true} if the currently visible object is a bomb,
     * {@code false} if it is a mole.
     */
    private boolean currentIsBomb;

    /**
     * How long the current mole/bomb has been visible (in milliseconds).
     */
    private long moleVisibleMillis;

    /**
     * How long there has been no mole visible (in milliseconds).
     * Used to decide when to spawn a new one.
     */
    private long timeSinceNoMole;

    private final Random random;

    /**
     * Creates a new Whac-A-Mole game instance.
     * The game starts in "game over" state and must be started via {@link #startGame()}.
     */
    public WhacAMoleGame() {
        this.random = new Random();
        this.gameOver = true;       // wait for startGame() call
        this.currentMoleIndex = -1; // no visible object at start
        this.currentIsBomb = false;
    }

    /**
     * Starts a new match from scratch.
     * Resets score, time and internal state.
     */
    @Override
    public void startGame() {
        this.score = 0;
        this.timeLeftMillis = GAME_DURATION_MILLIS;
        this.gameOver = false;

        this.currentMoleIndex = -1;
        this.currentIsBomb = false;
        this.moleVisibleMillis = 0;
        this.timeSinceNoMole = 0;
    }

    /**
     * Updates the game state according to the time elapsed.
     *
     * <p>
     * It is expected that this method is called periodically (for example every 100ms)
     * by the Controller.
     * </p>
     *
     * @param elapsedMillis number of milliseconds passed since last tick
     */
    @Override
    public void tick(final long elapsedMillis) {
        if (this.gameOver) {
            // if the match is already over, ignore further updates
            return;
        }

        // 1. Update global game timer
        this.timeLeftMillis -= elapsedMillis;
        if (this.timeLeftMillis <= 0) {
            this.timeLeftMillis = 0;
            endGame();
            return;
        }

        // 2. Handle the current mole/bomb visibility
        if (this.currentMoleIndex != -1) {
            // there is a visible object: update how long it has been visible
            this.moleVisibleMillis += elapsedMillis;

            // if it has been visible for too long, hide it
            if (this.moleVisibleMillis >= MOLE_LIFETIME_MILLIS) {
                removeMole();
            }

        } else {
            // no mole visible: update time since last mole
            this.timeSinceNoMole += elapsedMillis;

            // after some time, spawn a new mole/bomb at random
            if (this.timeSinceNoMole >= MIN_TIME_BETWEEN_MOLES) {
                spawnNewMoleRandom();
            }
        }
    }

    /**
     * Tries to hit the hole at the given index.
     *
     * @param index the hole index clicked by the player
     * @return {@code true} if a mole or bomb was present and the hit was valid,
     *         {@code false} otherwise
     */
    @Override
    public boolean hitHole(final int index) {
        if (this.gameOver) {
            // ignore clicks if the game is over
            return false;
        }

        if (index == this.currentMoleIndex && this.currentMoleIndex != -1) {

            // The player hit the current visible object
            if (this.currentIsBomb) {
                // bomb penalty: decrease score, but do not go below zero
                this.score -= BOMB_PENALTY;
                if (this.score < 0) {
                    this.score = 0;
                }
            } else {
                // mole hit: +1 point
                this.score++;
            }
            removeMole();
            return true;

        } else {
            // clicked on an empty or wrong hole
            return false;
        }
    }

    /**
     * Returns an immutable snapshot of the current game state.
     *
     * @return a {@link WhacAMoleGameState} with score, time left and mole position
     */
    @Override
    public WhacAMoleGameState getGameState() {
        return new WhacAMoleGameState(
            this.score,
            this.timeLeftMillis,
            this.gameOver,
            this.currentMoleIndex,
            TOTAL_HOLES
        );
    }

    /**
     * Ends the current game and clears any visible object.
     */
    private void endGame() {
        this.gameOver = true;
        this.currentMoleIndex = -1;
        this.currentIsBomb = false;
    }

    /**
     * Spawns a new mole or bomb at a random hole.
     */
    private void spawnNewMoleRandom() {
        this.currentMoleIndex = this.random.nextInt(TOTAL_HOLES);

        final double r = this.random.nextDouble();
        this.currentIsBomb = (r < BOMB_PROBABILITY);
        this.moleVisibleMillis = 0;
        this.timeSinceNoMole = 0;
    }

    /**
     * Removes the currently visible mole/bomb.
     */
    private void removeMole() {
        this.currentMoleIndex = -1;
        this.moleVisibleMillis = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCurrentMoleABomb() {
        return this.currentIsBomb;
    }
}
