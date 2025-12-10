package it.unibo.uniboparty.view.minigames.typeracergame.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.typeracergame.impl.ControllerImpl;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.ModelImpl;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;
import it.unibo.uniboparty.utilities.MinigameResultCallback;

/**
 * Intro window for the TyperacerGame minigame.
 */
public final class TyperacerGameIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Optional callback used to notify the board when the minigame ends.
     */
    private final transient MinigameResultCallback resultCallback;

    /**
     * Creates the intro window without a callback.
     */
    public TyperacerGameIntroFrame() {
        this(null);
    }

    /**
     * Creates the intro frame with a callback.
     *
     * @param resultCallback callback that will receive the result code
     *                       when the game window is closed
     */
    public TyperacerGameIntroFrame(final MinigameResultCallback resultCallback) {
        super();
        this.resultCallback = resultCallback;
        initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "TypeRacer";
    }

    @Override
    protected String getRulesText() {
        return
              "How to play:\n"
            + "- Type the displayed sentence as fast as you can.\n"
            + "- Every correct word gives you 1 point.\n"
            + "- Get 10 points in under 20 seconds to win.\n"
            + "- If you make a mistake, the point won't be given. Fix the word and try again.\n";
    }

    @Override
    protected JFrame createGameFrame() {
        final JFrame gameFrame = new JFrame("TypeRacer - Game");
        final ViewImpl view = new ViewImpl();
        final ModelImpl model = new ModelImpl();

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
