package it.unibo.uniboparty.controller.minigames.whacamole.impl;

import it.unibo.uniboparty.controller.minigames.whacamole.api.WhacAMoleController;
import it.unibo.uniboparty.model.minigames.whacamole.WhacAMoleGame;
import it.unibo.uniboparty.model.minigames.whacamole.impl.WhacAMoleGameState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Default implementation of the WhacAMoleController interface.
 * This controller:
 * - owns a concrete Model instance,
 * - starts and stops an internal timer to periodically update the game logic,
 * - translates user input from the View into actions on the Model,
 * - exposes read-only game state information to the View.
 */
public class WhacAMoleControllerImpl implements WhacAMoleController {

    private static final long TICK_MILLIS = 100;
    
    private final WhacAMoleGame game;
    private Timeline gameLoop;

    /**
     * Creates a new controller with a default game model.
     */
    public WhacAMoleControllerImpl() {
        this.game = new WhacAMoleGame();
    }

    @Override
    public void startGame() {
        game.startGame();

        // Timeline that periodically advances the game logic
        gameLoop = new Timeline(
            new KeyFrame(Duration.millis(TICK_MILLIS), e -> { 
                updateGameLogic(TICK_MILLIS);
            })
        );
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    public void stopGameLoopIfOver() {
        if(game.getGameState().isGameOver() && gameLoop != null) {
            gameLoop.stop();
        }
    }

    @Override
    public void updateGameLogic(long elapsedMillis) {
        game.tick(elapsedMillis);
    }

    @Override
    public void onHoleClicked(int index) {
        game.hitHole(index);
    }

    @Override
    public WhacAMoleGameState getState() {
        return game.getGameState();
    }

    @Override
    public boolean isCurrentObjectABomb() {
        return game.isCurrentMoleABomb();
    }

    @Override
    public void stopIfGameOver() {
        if (gameLoop != null && game.getGameState().isGameOver()) {
            gameLoop.stop();
        }
    }
}
