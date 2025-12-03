package it.unibo.uniboparty.view.minigames.tetris.impl;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.tetris.impl.TetrisControllerImpl;
import it.unibo.uniboparty.controller.minigames.tetris.api.TetrisController;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;

/**
 * Intro window for the Tetris minigame.
 */
public final class TetrisIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates the Tetris intro frame and initializes its UI.
     */
    public TetrisIntroFrame() {
        super();
        this.initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Tetris";
    }

    @Override
    protected String getRulesText() {
        return
              "How to play:\n"
            + "- Click and drag the piece into the grid.\n"
            + "- The bigger the piece is, the bigger are the points gained.\n"
            + "- Each row (vertical/orizzontally) add more points to the score.\n"
            + "- Reach 100 points to win! If you finish available moves, you lose.";
    }

    @Override
    protected JFrame createGameFrame() {
        final TetrisController controller = new TetrisControllerImpl();
        return controller.getView();
    }
}
