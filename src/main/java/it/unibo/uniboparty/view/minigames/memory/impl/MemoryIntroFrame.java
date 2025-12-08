package it.unibo.uniboparty.view.minigames.memory.impl;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.memory.impl.MemoryGameControllerImpl;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;

/**
 * Intro window for the Memory minigame.
 *
 * <p>
 * Shows the title, the rules and a button to start the actual game.
 * </p>
 */
public final class MemoryIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    @Override
    protected String getMinigameTitle() {
        return "Memory";
    }

    @Override
    protected String getRulesText() {
        return "How to play:\n"
            + "- Click a card to reveal its symbol.\n"
            + "- Then click a second card: if they match, the pair stays revealed.\n"
            + "- If they do not match, they will be hidden again after a short delay.\n"
            + "- Try to find all pairs using as few moves as possible.";
    }

    @Override
    protected JFrame createGameFrame() {
        final MemoryGameControllerImpl controller = MemoryGameControllerImpl.create();

        final JFrame gameFrame = new JFrame("Memory - Game");
        gameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gameFrame.setContentPane(controller.createMainPanel());
        gameFrame.pack();
        return gameFrame;
    }
}
