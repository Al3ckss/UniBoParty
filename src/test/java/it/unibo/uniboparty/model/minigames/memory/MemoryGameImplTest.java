package it.unibo.uniboparty.model.minigames.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.minigames.memory.api.Card;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameReadOnlyState;
import it.unibo.uniboparty.model.minigames.memory.api.Symbol;
import it.unibo.uniboparty.model.minigames.memory.impl.CardImpl;
import it.unibo.uniboparty.model.minigames.memory.impl.MemoryGameImpl;

class MemoryGameImplTest {

    private MemoryGameImpl game;
    private List<Card> deck;

    @BeforeEach
    void setUp() {
        // Deterministic deck: 4 cards, 2 pairs
        // [STAR, STAR, HEART, HEART]
        this.deck = List.of(
            new CardImpl(0, Symbol.STAR),
            new CardImpl(1, Symbol.STAR),
            new CardImpl(2, Symbol.HEART),
            new CardImpl(3, Symbol.HEART)
        );
        this.game = new MemoryGameImpl(this.deck);
    }

    @Test
    void testInitialStateIsCorrect() {
        final MemoryGameReadOnlyState state = this.game.getGameState();

        assertEquals(0, state.getMatchedPairs(), "No pairs should be matched at the beginning");
        assertEquals(2, state.getTotalPairs(), "Total pairs should be deck size / 2");
        assertFalse(state.isGameOver(), "Game should not be over at the beginning");
        assertFalse(state.isWaitingSecondFlip(), "No card should be selected yet");
        assertEquals(0, state.getMoves(), "Moves should start at 0");

        state.getCards().forEach(card ->
            assertFalse(card.isRevealed(), "All cards should be hidden at the beginning")
        );
    }

    @Test
    void testFirstFlipRevealsCardAndWaitsSecond() {
        final boolean result = this.game.flipCard(0);
        final MemoryGameReadOnlyState state = this.game.getGameState();

        assertTrue(result, "Flipping a valid hidden card should succeed");
        assertTrue(state.getCards().get(0).isRevealed(), "Card at index 0 should now be revealed");
        assertTrue(state.isWaitingSecondFlip(), "Game should now be waiting for the second flip");
        assertEquals(0, state.getMoves(), "Moves should not increment on the first flip");
    }

    @Test
    void testMatchIncrementsMatchedPairsAndKeepsCardsRevealed() {
        this.game.flipCard(0); // first STAR
        final boolean secondFlipResult = this.game.flipCard(1); // second STAR

        final MemoryGameReadOnlyState state = this.game.getGameState();

        assertTrue(secondFlipResult, "Second flip on matching card should succeed");
        assertEquals(1, state.getMatchedPairs(), "One pair should be matched");
        assertFalse(state.isWaitingSecondFlip(), "Turn should be finished after a match");
        assertFalse(this.game.hasMismatchPending(), "No mismatch should be pending after a match");
        assertTrue(state.getCards().get(0).isRevealed(), "First card should stay revealed");
        assertTrue(state.getCards().get(1).isRevealed(), "Second card should stay revealed");
        assertEquals(1, state.getMoves(), "One move should be counted after two flips");
    }

    @Test
    void testMismatchSetsPendingAndRequiresResolve() {
        this.game.flipCard(0); // STAR
        this.game.flipCard(2); // HEART -> mismatch

        final MemoryGameReadOnlyState stateAfterMismatch = this.game.getGameState();
        assertTrue(this.game.hasMismatchPending(), "Mismatch should be pending after two different cards");
        assertTrue(stateAfterMismatch.getCards().get(0).isRevealed(), "First card should be visible");
        assertTrue(stateAfterMismatch.getCards().get(2).isRevealed(), "Second card should be visible");

        final boolean thirdFlipResult = this.game.flipCard(3);
        final MemoryGameReadOnlyState stateAfterThirdTry = this.game.getGameState();

        assertFalse(thirdFlipResult, "Cannot flip new cards while mismatch is pending");
        assertEquals(
            stateAfterMismatch.getMoves(),
            stateAfterThirdTry.getMoves(),
            "Moves should not change while mismatch is pending"
        );

        this.game.resolveMismatch();
        final MemoryGameReadOnlyState stateAfterResolve = this.game.getGameState();

        assertFalse(this.game.hasMismatchPending(), "Mismatch should be cleared after resolveMismatch()");
        assertFalse(stateAfterResolve.getCards().get(0).isRevealed(), "First mismatched card should be hidden again");
        assertFalse(stateAfterResolve.getCards().get(2).isRevealed(), "Second mismatched card should be hidden again");
    }

    @Test
    void testFlipCardWithInvalidIndexDoesNothing() {
        final MemoryGameReadOnlyState before = this.game.getGameState();

        assertFalse(this.game.flipCard(-1), "Flipping with a negative index should fail");
        assertFalse(this.game.flipCard(this.deck.size()), "Flipping with index == size should fail");

        final MemoryGameReadOnlyState after = this.game.getGameState();

        assertEquals(before.getMatchedPairs(), after.getMatchedPairs(), "Matched pairs should not change");
        assertEquals(before.getMoves(), after.getMoves(), "Moves should not change");
        assertEquals(before.isGameOver(), after.isGameOver(), "Game over state should not change");
    }

    @Test
    void testGameEndsWhenAllPairsAreFound() {
        // First pair: STAR
        this.game.flipCard(0);
        this.game.flipCard(1);

        // Second pair: HEART
        this.game.flipCard(2);
        this.game.flipCard(3);

        final MemoryGameReadOnlyState state = this.game.getGameState();

        assertTrue(state.isGameOver(), "Game should be over when all pairs are found");
        assertEquals(state.getTotalPairs(), state.getMatchedPairs(), "All pairs should be matched");
    }
}
