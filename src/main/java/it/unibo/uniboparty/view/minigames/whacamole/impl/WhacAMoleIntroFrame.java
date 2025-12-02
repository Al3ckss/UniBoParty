package it.unibo.uniboparty.view.minigames.whacamole.impl;

import javax.swing.JFrame;

import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;

public final class WhacAMoleIntroFrame extends AbstractMinigameIntroFrame {

    @Override
    protected String getMinigameTitle() {
        return "Whac-A-Mole";
    }

    @Override
    protected String getRulesText() {
        return
              "How to play:\n"
            + "- Hit the mole when it appears in the grid.\n"
            + "- Avoid bombs: they remove 2 points.\n"
            + "- Each mole is worth 1 point.\n"
            + "- You have 30 seconds to score as much as possible.";
    }

    @Override
    protected JFrame createGameFrame() {
        final JFrame gameFrame = new JFrame("Whac-A-Mole - Game");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setContentPane(new WhacAMoleViewImpl());
        gameFrame.pack();
        return gameFrame;
    }
}
