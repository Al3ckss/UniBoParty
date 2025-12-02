package it.unibo.uniboparty.application;

import it.unibo.uniboparty.controller.minigames.typeracergame.impl.ControllerImpl;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.ModelImpl;
import it.unibo.uniboparty.view.minigames.typeracergame.impl.ViewImpl;

/**
 * Main entry point for the TypeRacer minigame demo.
 */
public final class BoardApp {

    private BoardApp() {
        // utility class
    }

    /**
     * Main method to start the game.
     *
     * @param args command line arguments (unused)
     */
    public static void main(final String[] args) {
        final ModelImpl model = new ModelImpl();
        final ViewImpl view = new ViewImpl();
        view.bindModel(model);

        model.setNewWord();

        new ControllerImpl(model, view);

        /*DINOSAUR GAME
        public static void main(String[] args) {
        ModelImpl model = new ModelImpl();
        ViewImpl view = new ViewImpl(model);
        new ControllerImpl(model, view);
        }
    */
    }
}
