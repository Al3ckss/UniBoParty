package typeracerGame.controller;

import javax.swing.Timer;
import javax.swing.SwingUtilities;

import typeracerGame.model.ModelImpl;
import typeracerGame.view.ViewImpl;
import typeracerGame.model.GameConfig;
import typeracerGame.model.GameState;

public class ControllerImpl implements Controller {

    private final ModelImpl model;
    private final ViewImpl view;
    private Timer timer;

    public ControllerImpl(ModelImpl model, ViewImpl view) {
        this.model = model;
        this.view = view;

        model.setState(GameState.RUNNING);

        timer = new Timer(GameConfig.TIMER_DELAY_MS, e ->{
            if (model.getState() == GameState.RUNNING) {
                model.decreaseTime();
                SwingUtilities.invokeLater(() -> view.updateTimeLabel(model.getTime()));

                if (model.getTime() <= 0) {
                    model.gameOver();
                    SwingUtilities.invokeLater(() -> view.setLabel1("Tempo Finito. Punti: " + model.getPoints()));
                    timer.stop();
                }
            }
        });

        setupInputField();
        timer.start();
    }

    private void setupInputField() {
        view.getTextField().addActionListener(e -> {
            if (model.getState() != GameState.RUNNING) return;

            String typed = view.getTextField().getText();
            String current = model.getCurrentWord();

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
