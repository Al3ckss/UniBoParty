package it.unibo.uniboparty.view.minigames.mazegame.impl;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.mazegame.impl.MazeControllerImpl;
import it.unibo.uniboparty.controller.minigames.mazegame.api.MazeController;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;

/**
 * Intro window for the Maze Game minigame.
 */
public final class MazeIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates the Tetris intro frame and initializes its UI.
     */
    public MazeIntroFrame() {
        super();
        this.initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Maze Game";
    }

    @Override
    protected String getRulesText() {
        return
              "How to play:\n"
            + "- Navigate through the maze to reach the exit.\n"
            + "- Avoid dead ends and find the shortest path.\n"
            + "- Use arrow keys to move your character.\n"
            + "- Reach the exit in the shortest time possible to win!\n"
            + "- If you reach the maximum time or moves you lose.";
    }

    @Override
    protected JFrame createGameFrame() {
        final MazeController controller = new MazeControllerImpl();
        return controller.getView();
    }

}
