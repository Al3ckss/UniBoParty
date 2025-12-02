package it.unibo.uniboparty.model.minigames.whacamole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.minigames.whacamole.api.WhacAMoleModel;
import it.unibo.uniboparty.model.minigames.whacamole.impl.WhacAMoleGame;

class WhacAMoleGameTest {

    private static final long GAME_DURATION = 30_000L;
    private static final long EXTRA_TICK = 5_000L;

    private WhacAMoleModel model;

    @BeforeEach
    void setUp() {
        this.model = new WhacAMoleGame();
    }

    @Test
    void testStartGameInitializesState() {
        this.model.startGame();
        final WhacAMoleGameState state = this.model.getGameState();

        assertEquals(0, state.getScore());
        assertFalse(state.isGameOver());
        assertEquals(GAME_DURATION, state.getTimeLeftMillis());
        assertEquals(-1, state.getCurrentMoleIndex());
        assertTrue(state.getTotalHoles() > 0);
    }

    @Test
    void testGameEndsWhenTimeElapses() {
        this.model.startGame();

        this.model.tick(GAME_DURATION + 1_000L);

        final WhacAMoleGameState state = this.model.getGameState();
        assertTrue(state.isGameOver());
        assertEquals(0L, state.getTimeLeftMillis());
    }

    @Test
    void testTickDoesNothingAfterGameOver() {
        this.model.startGame();

        this.model.tick(GAME_DURATION + 1_000L);
        final WhacAMoleGameState stateAfterEnd = this.model.getGameState();

        this.model.tick(EXTRA_TICK);
        final WhacAMoleGameState stateAfterExtraTick = this.model.getGameState();

        assertEquals(stateAfterEnd.getScore(), stateAfterExtraTick.getScore());
        assertEquals(stateAfterEnd.isGameOver(), stateAfterExtraTick.isGameOver());
        assertEquals(stateAfterEnd.getTimeLeftMillis(), stateAfterExtraTick.getTimeLeftMillis());
        assertEquals(stateAfterEnd.getCurrentMoleIndex(), stateAfterExtraTick.getCurrentMoleIndex());
    }

    @Test
    void testSpawnAndHitVisibleObjectUpdatesScoreAndRemovesMole() {
        this.model.startGame();

        this.model.tick(1_000L);

        final WhacAMoleGameState state = this.model.getGameState();
        final int currentIndex = state.getCurrentMoleIndex();

        assertTrue(currentIndex >= 0 && currentIndex < state.getTotalHoles());

        final int oldScore = state.getScore();
        final boolean wasBomb = this.model.isCurrentObjectABomb();

        final boolean hitResult = this.model.hitHole(currentIndex);
        final WhacAMoleGameState stateAfterHit = this.model.getGameState();

        assertTrue(hitResult);
        assertEquals(-1, stateAfterHit.getCurrentMoleIndex());

        if (wasBomb) {
            assertTrue(stateAfterHit.getScore() <= oldScore);
            assertTrue(stateAfterHit.getScore() >= 0);
        } else {
            assertEquals(oldScore + 1, stateAfterHit.getScore());
        }
    }

    @Test
    void testHitHoleOnWrongIndexDoesNothing() {
        this.model.startGame();
        this.model.tick(1_000L);

        final WhacAMoleGameState state = this.model.getGameState();
        final int currentIndex = state.getCurrentMoleIndex();

        assertTrue(currentIndex >= 0 && currentIndex < state.getTotalHoles());

        final int wrongIndex = (currentIndex + 1) % state.getTotalHoles();
        final int oldScore = state.getScore();

        final boolean hitResult = this.model.hitHole(wrongIndex);
        final WhacAMoleGameState stateAfterHit = this.model.getGameState();

        assertFalse(hitResult);
        assertEquals(oldScore, stateAfterHit.getScore());
        assertEquals(currentIndex, stateAfterHit.getCurrentMoleIndex());
    }

    @Test
    void testIsCurrentObjectABombIsFalseWhenNoMoleVisible() {
        this.model.startGame();
        final WhacAMoleGameState state = this.model.getGameState();

        assertEquals(-1, state.getCurrentMoleIndex());
        assertFalse(this.model.isCurrentObjectABomb(),
            "isCurrentObjectABomb() should be false when no object is visible");
    }
}
