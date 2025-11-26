package it.unibo.uniboparty.controller.minigames.typeracergame.impl;

import javax.swing.Timer;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.controller.minigames.typeracergame.api.Controller;
import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameState;
import it.unibo.uniboparty.view.minigames.typeracergame.api.View;

/**
 * Implementation of Typeracer's Controller.
 * 
 * <p>
 * This class handles the connection between the Model and the View.
 * It manages the game loop via a Timer and updates the UI as the game progresses.
 * It also handles user input.
 */
public class ControllerImpl implements Controller {

    /**
     * Reference to the game model.
     * Used to update game state and check game conditions.
     */
    private final Model model;

    /**
     * Reference to the game view.
     * Used to update the UI (labels, input field) according to the game state.
     */
    private final View view;

    /**
     * Timer used to run the game loop.
     */
    private Timer t;

    /**
     * Creates a Typeracer controller that connects a model and a view.
     *
     * @param model the game model to control
     * @param view the game view to update
     */
    public ControllerImpl(final Model model, final View view) {
        this.model = model;
        this.view = view;

        model.setState(GameState.RUNNING);

        t = new Timer(GameConfig.TIMER_DELAY_MS, e -> {
            if (model.getState() == GameState.RUNNING) {
                model.decreaseTime();
                SwingUtilities.invokeLater(() -> view.updateTimeLabel(model.getTime()));

                if (model.getTime() <= 0) {
                    model.gameOver();
                    SwingUtilities.invokeLater(() -> view.setLabel1("Tempo Finito. Punti: " + model.getPoints()));
                    t.stop();
                }
            }
        });

        setupInputField();
        t.start();
    }

    /**
     * Sets up the action listener on the text field to handle typing input.
     * 
     * <p>
     * When the user types a word and presses Enter, it checks if the word matches
     * the current word in the model. If correct, points are incremented and a new
     * word is set.
     */
    private void setupInputField() {
        view.getTextField().addActionListener(e -> {
            if (model.getState() != GameState.RUNNING) {
                return;
            }

            final String typed = view.getTextField().getText();
            final String current = model.getCurrentWord();

            if (typed.equals(current)) {
                model.incrementPoints();
                model.setNewWord();
                SwingUtilities.invokeLater(() -> {
                    view.setLabel1(model.getCurrentWord());
                    view.getTextField().setText("");
                });
            }
        });
    }
}
