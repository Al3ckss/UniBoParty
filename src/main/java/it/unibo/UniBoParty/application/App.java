package it.unibo.uniboparty.application;

import it.unibo.uniboparty.controller.minigames.dinosaurgame.impl.ControllerImpl;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;
import it.unibo.uniboparty.view.minigames.dinosaurgame.impl.ViewImpl;

/**
 * Main class to run the game.
 */
final class App {

    /**
     * Prevents instantiation of this utility-like class.
     */
    private App() { }

    /**
     * Main method that creates an instance of the Model, View, and Controller.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {
        final ModelImpl model = new ModelImpl();
        final ViewImpl view = new ViewImpl(model);
        new ControllerImpl(model, view);
    }
}
