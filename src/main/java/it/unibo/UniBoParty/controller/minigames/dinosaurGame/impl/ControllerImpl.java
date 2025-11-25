package it.unibo.UniBoParty.controller.minigames.dinosaurGame.impl;

import javax.swing.Timer;

import it.unibo.UniBoParty.controller.minigames.dinosaurGame.api.Controller;
import it.unibo.UniBoParty.model.minigames.dinosaurGame.impl.GameConfig;
import it.unibo.UniBoParty.model.minigames.dinosaurGame.impl.GameState;
import it.unibo.UniBoParty.model.minigames.dinosaurGame.impl.ModelImpl;
import it.unibo.UniBoParty.view.minigames.dinosaurGame.impl.ViewImpl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ControllerImpl implements Controller {

    private final ModelImpl model;
    private final ViewImpl view;
    private Timer timer;

    public ControllerImpl(ModelImpl model, ViewImpl view) {
        this.model = model;
        this.view = view;

        setupTimer();
        setupKeyListener();
    }

    private void setupTimer() {
        timer = new Timer(GameConfig.TIMER_DELAY_MS, e -> {
            if (model.getGameState() == GameState.RUNNING) {
                model.update();
                view.repaint();

                if (model.getGameState() == GameState.GAME_OVER) {
                    timer.stop();
                    System.out.print("GAME OVER");
                }
            }
        });
        timer.start();
    }

    private void setupKeyListener() {
        view.getPanel().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && model.getGameState() == GameState.RUNNING) {
                    model.jump();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && model.getGameState() == GameState.RUNNING) {
                    model.releaseJump();
                }
            }
        });

        view.getPanel().setFocusable(true);
        view.getPanel().requestFocusInWindow();
    }

    @Override
    public void start() {
        timer.start();
    }

    @Override
    public void stop() {
        timer.stop();
    }
}
