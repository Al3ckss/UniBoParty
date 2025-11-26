package it.unibo.uniboparty.application;

import it.unibo.uniboparty.controller.minigames.memory.api.MemoryGameController;
import it.unibo.uniboparty.controller.minigames.memory.impl.MemoryGameControllerImpl;
import it.unibo.uniboparty.view.minigames.memory.api.MemoryGameView;
import it.unibo.uniboparty.view.minigames.memory.impl.MemoryGameViewImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main JavaFX application class for the Memory game.
 * It creates the View and the Controller, and starts the UI.
 */
public class MemoryGameApp extends Application {

    @SuppressWarnings("unused")
    private MemoryGameController controller;

    @Override
    public void start(Stage stage) {
        // Create the View
        final MemoryGameView view = new MemoryGameViewImpl();

        // Create the Controller and connect Model with View
        this.controller = new MemoryGameControllerImpl(view); // collega model↔view

        // Create the main scene with the view
        final Scene scene = new Scene((MemoryGameViewImpl) view, 600, 700);
        stage.setTitle("Memory Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
