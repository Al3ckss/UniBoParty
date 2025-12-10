package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.dinosaurgame.impl.ControllerImpl;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;
import it.unibo.uniboparty.utilities.MinigameResultCallback;

/**
 * Intro window for the Dinosaur Game minigame.
 */
public final class DinoGameIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Optional callback used to notify the board when the minigame ends.
     */
    private final transient MinigameResultCallback resultCallback;

    /**
     * Creates the intro window without a callback.
     *
     * <p>Useful to run the minigame standalone.</p>
     */
    public DinoGameIntroFrame() {
        this(null);
    }

    /**
     * Creates the intro window with a callback.
     *
     * @param resultCallback callback that receives the result code
     *                       when the game window is closed
     */
    public DinoGameIntroFrame(final MinigameResultCallback resultCallback) {
        super();
        this.resultCallback = resultCallback;
        initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Dinosaur Run";
    }

    @Override
    protected String getRulesText() {
        return
              "How to play:\n"
            + "- Press SPACE to make the dinosaur jump.\n"
            + "- Avoid all obstacles, you lose if you touch one of them.\n"
            + "- Survive for 30 seconds to win.\n"
            + "- The speed increases over time, so be ready.";
    }

    @Override
    protected JFrame createGameFrame() {
        final JFrame gameFrame = new JFrame("Dinosaur Run - Game");
        final ModelImpl model = new ModelImpl();
        final ViewImpl view = new ViewImpl(model);

        final ControllerImpl controller = new ControllerImpl(model, view);

        gameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gameFrame.setContentPane(view);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);

        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                view.unbindModel();
                controller.cleanup();
            }

            @Override
            public void windowClosed(final WindowEvent e) {
                if (resultCallback != null) {
                    final int resultCode = controller.getState();
                    resultCallback.onResult(resultCode);
                }
            }
        });

        return gameFrame;
    }
}
