package it.unibo.uniboparty.utilities;

import javax.swing.JFrame;

/**
 * Base frame used as an intro screen for any minigame.
 *
 * <p>The actual UI is initialized by {@link #initIntroFrame()},
 * which must be called by subclasses at the end of their constructor.</p>
 */
public abstract class AbstractMinigameIntroFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Protected constructor with no side effects.
     * Subclasses must call {@link #initIntroFrame()} explicitly.
     */
    protected AbstractMinigameIntroFrame() {
        super();
    }

    /**
     * Initializes the intro window for the minigame.
     *
     * <p>This method builds a {@link MinigameIntroPanel} and configures
     * the frame (title, size, position and default close operation).</p>
     *
     * <p>It must be called once by the subclass constructor.</p>
     */
    protected final void initIntroFrame() {
        final MinigameIntroPanel panel = new MinigameIntroPanel(
            this.getMinigameTitle(),
            this.getRulesText(),
            () -> {
                final JFrame gameFrame = this.createGameFrame();
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);
                this.dispose();
            }
        );

        this.setTitle(this.getMinigameTitle());
        this.setContentPane(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * @return the short title of the minigame (used in UI and window title)
     */
    protected abstract String getMinigameTitle();

    /**
     * @return the textual description of the rules, shown when clicking "How to play"
     */
    protected abstract String getRulesText();

    /**
     * @return the JFrame to display when the user presses "Play"
     */
    protected abstract JFrame createGameFrame();
}
