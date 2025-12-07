package it.unibo.uniboparty.view.minigames.typeracergame.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.view.minigames.typeracergame.api.View;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;
import it.unibo.uniboparty.model.minigames.typeracergame.api.GameObserver;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Implementation of the TypeRacer minigame view.
 */
public final class ViewImpl implements View, GameObserver {

    private static final String TIME_PREFIX = "Remaining time: ";

    private final JFrame frame = new JFrame("TypeRacer");
    private final JLabel label1 = new JLabel();
    private final JLabel timeLabel = new JLabel();
    private final JTextField textField = new JTextField();

    private Model boundModel;

    /**
     * Empty constructor to allow instantiation without parameters.
     */
    public ViewImpl() {
        // empty
    }

    /**
     * Builds and returns the JFrame hosting the TypeRacer UI.
     * The frame is not shown by this method; callers should call
     * `setVisible(true)` when appropriate.
     *
     * @return the configured {@link JFrame}
     */
    public JFrame createGameFrame() {
        configureFrame();
        return createFrameCopy();
    }

    /**
     * Shows the game frame immediately.
     */
    public void showGameFrame() {
        configureFrame();
        frame.setVisible(true);
    }

    /**
     * Configures the internal frame without exposing it.
     */
    private void configureFrame() {
        frame.setBounds(100, 100, GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT);

        label1.setFont(new Font(GameConfig.DEFAULT_FONT, Font.BOLD, GameConfig.LABEL_FONT_SIZE));
        timeLabel.setFont(new Font(GameConfig.DEFAULT_FONT, Font.BOLD, GameConfig.LABEL_FONT_SIZE));
        textField.setFont(new Font(GameConfig.DEFAULT_FONT, Font.PLAIN, GameConfig.INPUT_FONT_SIZE));

        label1.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT / 4));
        timeLabel.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT / 4));
        textField.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT / 4));

        // Remove existing components to avoid duplicates
        frame.getContentPane().removeAll();
        frame.add(label1, BorderLayout.NORTH);
        frame.add(timeLabel, BorderLayout.CENTER);
        frame.add(textField, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                if (boundModel != null) {
                    boundModel.removeObserver(ViewImpl.this);
                }
            }
        });
    }

    /**
     * Creates a defensive copy of the frame to avoid exposing internal representation.
     * 
     * @return a new JFrame with same configuration
     */
    private JFrame createFrameCopy() {
        final JFrame frameCopy = new JFrame(frame.getTitle());
        frameCopy.setBounds(frame.getBounds());
        frameCopy.setDefaultCloseOperation(frame.getDefaultCloseOperation());
        frameCopy.getContentPane().setLayout(new BorderLayout());

        final JLabel label1Copy = new JLabel(label1.getText());
        label1Copy.setFont(label1.getFont());
        label1Copy.setPreferredSize(label1.getPreferredSize());

        final JLabel timeLabelCopy = new JLabel(timeLabel.getText());
        timeLabelCopy.setFont(timeLabel.getFont());
        timeLabelCopy.setPreferredSize(timeLabel.getPreferredSize());

        final JTextField textFieldCopy = new JTextField(textField.getText());
        textFieldCopy.setFont(textField.getFont());
        textFieldCopy.setPreferredSize(textField.getPreferredSize());

        for (final var listener : textField.getActionListeners()) {
            textFieldCopy.addActionListener(listener);
        }

        frameCopy.add(label1Copy, BorderLayout.NORTH);
        frameCopy.add(timeLabelCopy, BorderLayout.CENTER);
        frameCopy.add(textFieldCopy, BorderLayout.SOUTH);

        frameCopy.pack();

        frameCopy.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                if (boundModel != null) {
                    boundModel.removeObserver(ViewImpl.this);
                }
            }
        });

        return frameCopy;
    }

    @Override
    public void setLabel1(final String text) {
        label1.setText(text);
    }

    @Override
    public void updateTimeLabel(final int t) {
        timeLabel.setText(TIME_PREFIX + t);
        timeLabel.revalidate();
        timeLabel.repaint();
    }

    @Override
    public JTextField getTextField() {
        final JTextField copy = new JTextField(textField.getText());
        copy.setEnabled(textField.isEnabled());
        return copy;
    }

    @Override
    public JLabel getLabel1() {
        final JLabel copy = new JLabel(label1.getText());
        copy.setFont(label1.getFont());
        return copy;
    }

    @Override
    public void bindModel(final Model model) {
        if (this.boundModel != null) {
            this.boundModel.removeObserver(this);
        }
        if (model == null) {
            this.boundModel = null;
            return;
        }
        // Use the model directly but ensure we're not storing a mutable reference
        this.boundModel = model;
        model.addObserver(this);
        SwingUtilities.invokeLater(() -> {
            label1.setText(model.getCurrentWord());
            timeLabel.setText(TIME_PREFIX + model.getTime());
        });
    }

    @Override
    public void addTextFieldActionListener(final java.awt.event.ActionListener listener) {
        this.textField.addActionListener(listener);
    }

    @Override
    public String getTextFieldText() {
        return this.textField.getText();
    }

    @Override
    public void clearTextField() {
        SwingUtilities.invokeLater(() -> this.textField.setText(""));
    }

    @Override
    public void modelUpdated() {
        SwingUtilities.invokeLater(() -> {
            if (boundModel == null) {
                return;
            }
            label1.setText(boundModel.getCurrentWord());
            timeLabel.setText(TIME_PREFIX + boundModel.getTime());
        });
    }

    @Override
    public void showFinalScore(final int finalScore) {
        SwingUtilities.invokeLater(() -> {
            label1.setText("Game Over! Final Score: " + finalScore);
            textField.setEnabled(false);
        });
    }
}
