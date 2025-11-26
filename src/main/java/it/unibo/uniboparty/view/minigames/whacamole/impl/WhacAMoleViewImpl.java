package it.unibo.uniboparty.view.minigames.whacamole.impl;

import it.unibo.uniboparty.controller.minigames.whacamole.api.WhacAMoleController;
import it.unibo.uniboparty.controller.minigames.whacamole.impl.WhacAMoleControllerImpl;
import it.unibo.uniboparty.model.minigames.whacamole.impl.WhacAMoleGameState;
import it.unibo.uniboparty.view.minigames.whacamole.api.WhacAMoleView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Implementations of the Whac-A-Mole game view.
 *
 * This class builds the GUI, handles user interactions (hole clicks),
 * and periodically refreshes its graphics by pulling read-only state
 * from the Controller.
 */
public class WhacAMoleViewImpl extends BorderPane implements WhacAMoleView {

    private final WhacAMoleController controller;

    private final Label scoreLabel;
    private final Label timerLabel;
    private final Label gameOverLabel;
    private final Button startButton;

    private final Button[] holeButtons;

    private Timeline uiRefreshLoop;
    private boolean gameStarted;

    private final Image moleImage;
    private final Image bombImage;

    /**
     * Creates the GUI for the Whac-A-Mole game.
     */
    public WhacAMoleViewImpl() {
        this.controller = new WhacAMoleControllerImpl();

        this.scoreLabel = new Label("Score: 0");
        this.timerLabel = new Label("Timer: 30s");
        this.startButton = new Button("Start Game");

        this.gameOverLabel = new Label("");
        this.gameOverLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: red;");
        this.gameOverLabel.setVisible(false);

        this.holeButtons = new Button[9];
        this.gameStarted = false;

        this.moleImage = new Image(getClass().getResource("/images/whacamole/mole.png").toExternalForm());
        this.bombImage = new Image(getClass().getResource("/images/whacamole/bomb.png").toExternalForm());

        VBox topBox = buildTopBar();
        GridPane grid = buildGrid();
        VBox bottomBox = buildBottomBar();

        this.setTop(topBox);
        this.setCenter(grid);
        this.setBottom(bottomBox);

        wireActions();
        setupUIRefreshLoop();
        refreshUI();
    }

    /**
     * Builds the top bar (score and timer).
     */
    private VBox buildTopBar() {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(scoreLabel, timerLabel);
        return box;
    }

    /**
     * Builds the 3x3 mole grid.
     */
    private GridPane buildGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        int index = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {

                Button b = new Button("");
                b.setPrefSize(80, 80);
                b.setStyle("-fx-background-color: gray;");

                final int holeIndex = index;
                b.setOnAction(e -> {
                    if (!gameStarted) {
                        return;
                    }

                    controller.onHoleClicked(holeIndex);
                    refreshUI();
                });

                holeButtons[index] = b;
                grid.add(b, c, r);
                index++;
            }
        }

        return grid;
    }

    /**
     * Builds the bottom bar (start button + end message).
     */
    private VBox buildBottomBar() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(startButton, gameOverLabel);
        return box;
    }

    /**
     * Connects the Start button to game initialization logic.
     */
    private void wireActions() {
        startButton.setOnAction(e -> {
            if (gameStarted) {
                return;
            }

            gameStarted = true;
            controller.startGame();
            startButton.setDisable(true);
            gameOverLabel.setVisible(false);
            refreshUI();
        });
    }

    /**
     * Refresh loop: asks the Controller for state every 100ms and redraws UI.
     */
    private void setupUIRefreshLoop() {
        uiRefreshLoop = new Timeline(
            new KeyFrame(Duration.millis(100), e -> refreshUI())
        );
        uiRefreshLoop.setCycleCount(Timeline.INDEFINITE);
        uiRefreshLoop.play();
    }

    /**
     * Redraws the entire GUI according to the current state.
     */
    private void refreshUI() {
        WhacAMoleGameState state = controller.getState();

        if (!gameStarted) {
            scoreLabel.setText("Score: 0");
            timerLabel.setText("Time: 30s");
            gameOverLabel.setVisible(false);

            for (Button b : holeButtons) {
                b.setGraphic(null);
                b.setText("");
                b.setStyle("-fx-background-color: gray;");
                b.setDisable(false);
            }

            return;
        }

        scoreLabel.setText("Score: " + state.getScore());
        long secondsLeft = state.getTimeLeftMillis() / 1000;
        timerLabel.setText("Time: " + secondsLeft + "s");

        int moleIndex = state.getCurrentMoleIndex();
        boolean isBomb = controller.isCurrentObjectABomb();

        for (int i = 0; i < holeButtons.length; i++) {
            Button b = holeButtons[i];

            if (!state.isGameOver() && i == moleIndex) {

                ImageView iv = new ImageView(isBomb ? bombImage : moleImage);
                iv.setFitWidth(60);
                iv.setFitHeight(60);
                iv.setPreserveRatio(true);

                b.setGraphic(iv);

                if (isBomb) {
                    b.setStyle("-fx-background-color: red;");
                } else {
                    b.setStyle("-fx-background-color: burlywood;");
                }

                b.setDisable(false);

            } else {
                b.setGraphic(null);
                b.setText("");
                b.setStyle("-fx-background-color: gray;");
                b.setDisable(state.isGameOver());
            }
        }

        if (state.isGameOver()) {
            if (uiRefreshLoop != null) {
                uiRefreshLoop.stop();
            }

            controller.stopIfGameOver();

            gameOverLabel.setText("TIME'S UP! Final Score: " + state.getScore());
            gameOverLabel.setVisible(true);
        }
    }

    /**
     * Used by the main UniBoParty board to retrieve the final score.
     */
    @Override
    public int getFinalScore() {
        return controller.getState().getScore();
    }
}
