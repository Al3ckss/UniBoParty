package typeracerGame.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import typeracerGame.model.ModelImpl;
import typeracerGame.view.ViewImpl;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa la logica del ControllerImpl del gioco TypeRacer.
 * Verifica l'inizializzazione dello stato, il comportamento del gioco
 * durante la partita e il corretto passaggio a GAME_OVER.
 */
class ControllerImplTest {

    private ModelImpl model;
    private ViewImpl view;

    @BeforeEach
    void setUp() {
        model = new ModelImpl();
        view = new ViewImpl();
        model.setNewWord();
        new ControllerImpl(model, view);
    }

    /**
     * Verifica che il Controller inizializzi correttamente lo stato del gioco
     * e che il punteggio parta da zero.
     */
    @Test
    void testControllerStartsGame() {
        assertEquals(0, model.getPoints());
        assertEquals(model.getState(), typeracerGame.model.GameState.RUNNING);
    }

    /**
     * Simula il decremento del timer fino a zero e verifica che lo stato
     * cambi correttamente in GAME_OVER.
     */
    @Test
    void testGameOverAfterTimeExpires() {
        model.setState(typeracerGame.model.GameState.RUNNING);
        while (model.getTime() > 0) {
            model.decreaseTime();
        }
        assertEquals(typeracerGame.model.GameState.GAME_OVER, model.getState());
    }
}
