package typeracerGame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa la logica del ModelImpl del gioco TypeRacer.
 * Controlla stato iniziale, gestione delle parole, punteggio e timer.
 */
class ModelImplTest {

    private ModelImpl model;

    @BeforeEach
    void setUp() {
        model = new ModelImpl();
    }

    /**
     * Verifica lo stato iniziale del Model:
     * punteggio a zero, stato READY, tempo iniziale e parola nulla.
     */
    @Test
    void testInitialState() {
        assertEquals(GameState.READY, model.getState());
        assertEquals(0, model.getPoints());
        assertEquals(GameConfig.INITIAL_TIME_SECONDS, model.getTime());
        assertNull(model.getCurrentWord());
    }

    /**
     * Controlla che la generazione di una nuova parola restituisca
     * sempre una parola valida presente nella lista.
     */
    @Test
    void testSetNewWord() {
        model.setNewWord();
        assertNotNull(model.getCurrentWord());
        assertTrue(WordList.WORDS.contains(model.getCurrentWord()));
    }

    /**
     * Verifica che l'incremento del punteggio funzioni correttamente.
     */
    @Test
    void testIncrementPoints() {
        model.incrementPoints();
        assertEquals(1, model.getPoints());
    }

    /**
     * Controlla che il metodo decreaseTime decrementi il tempo di un secondo.
     */
    @Test
    void testDecreaseTime() {
        int initialTime = model.getTime();
        model.decreaseTime();
        assertEquals(initialTime - 1, model.getTime());
    }

    /**
     * Verifica che il passaggio del tempo fino a zero imposti
     * correttamente lo stato GAME_OVER.
     */
    @Test
    void testGameOverState() {
        model.decreaseTime(); // stato non zero, non cambia
        model.setState(GameState.RUNNING);
        for(int i = 0; i < GameConfig.INITIAL_TIME_SECONDS; i++) {
            model.decreaseTime();
        }
        assertEquals(GameState.GAME_OVER, model.getState());
    }

    /**
     * Verifica che il metodo gameOver imposti direttamente lo stato GAME_OVER.
     */
    @Test
    void testGameOverMethod() {
        model.gameOver();
        assertEquals(GameState.GAME_OVER, model.getState());
    }
}
