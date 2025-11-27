package it.unibo.uniboparty.view.end_screen.impl;

import it.unibo.uniboparty.model.end_screen.api.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.Serial;
import java.net.URL;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * Panel that draws the podium graphics with a background and title.
 */
public class PodiumPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(PodiumPanel.class.getName());

    private static final String BG_IMAGE_PATH = "sudoku_icons/background.jpeg";

    private static final int BASE_Y_OFFSET = 100; // Distanza dal fondo
    private static final int BAR_WIDTH = 100;
    private static final int FIRST_HEIGHT = 160;
    private static final int SECOND_HEIGHT = 110;
    private static final int THIRD_HEIGHT = 80;
    private static final int GAP = 20;
    private static final int PLAYER_TEXT_OFFSET = 25;
    private static final Font PLAYER_FONT = new Font("Arial", Font.BOLD, 16);

    private static final String TITLE_TEXT = "Congratulazioni!";
    private static final int TITLE_Y_POS = 60;
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD | Font.ITALIC, 42);
    private static final Color TITLE_COLOR = new Color(50, 50, 50); // Grigio scuro

    private final List<Player> topPlayers;
    private final transient Image backgroundImage;

    /**
     * @param topPlayers List of top 3 players.
     */
    public PodiumPanel(final List<Player> topPlayers) {
        // FIX SPOTBUGS: Creiamo una NUOVA ArrayList copiando i dati.
        // In questo modo, il pannello è "blindato" da modifiche esterne.
        this.topPlayers = new ArrayList<>(topPlayers);

        final URL imgUrl = getClass().getClassLoader().getResource(BG_IMAGE_PATH);
        if (imgUrl != null) {
            // FIX SPOTBUGS: Copia difensiva dell'immagine tramite ImageIcon
            this.backgroundImage = new ImageIcon(imgUrl).getImage();
        } else {
            this.backgroundImage = null;
            // FIX CHECKSTYLE: Usa Logger invece di System.err
            LOGGER.log(Level.WARNING, "Attenzione: Immagine di sfondo ''" + BG_IMAGE_PATH + "'' non trovata.");
        }
    }

    /**
     * Draws the UI.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;

        // Attiva l'antialiasing per testi e disegni più fluidi
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2);

        drawTitle(g2);

        if (topPlayers.isEmpty()) {
            return;
        }
        drawPodiumBars(g2);
    }

    private void drawBackground(final Graphics2D g2) {
        if (backgroundImage != null) {

            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void drawTitle(final Graphics2D g2) {
        g2.setFont(TITLE_FONT);
        g2.setColor(TITLE_COLOR);

        final FontMetrics metrics = g2.getFontMetrics(TITLE_FONT);
        final int textWidth = metrics.stringWidth(TITLE_TEXT);
        final int x = (getWidth() - textWidth) / 2;

        g2.drawString(TITLE_TEXT, x, TITLE_Y_POS);
    }

    private void drawPodiumBars(final Graphics2D g2) {
        final int baseY = getHeight() - BASE_Y_OFFSET;
        final int firstX = (getWidth() / 2) - (BAR_WIDTH / 2);
        final int secondX = firstX - BAR_WIDTH - GAP;
        final int thirdX = firstX + BAR_WIDTH + GAP;

        g2.setFont(PLAYER_FONT);

        if (!topPlayers.isEmpty()) {
            drawSingleBar(g2, firstX, baseY, FIRST_HEIGHT, Color.YELLOW, "1. " + topPlayers.get(0).getName());
        }
        if (topPlayers.size() >= 2) {
            drawSingleBar(g2, secondX, baseY, SECOND_HEIGHT, Color.LIGHT_GRAY, "2. " + topPlayers.get(1).getName());
        }
        if (topPlayers.size() >= 3) {
            drawSingleBar(g2, thirdX, baseY, THIRD_HEIGHT, Color.ORANGE, "3. " + topPlayers.get(2).getName());
        }
    }

    private void drawSingleBar(final Graphics2D g, final int x, final int baseY,
                               final int height, final Color color, final String text) {
        final int yPos = baseY - height;

        g.setColor(color);
        g.fillRect(x, yPos, BAR_WIDTH, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, yPos, BAR_WIDTH, height);

        final FontMetrics metrics = g.getFontMetrics(PLAYER_FONT);
        final int textWidth = metrics.stringWidth(text);
        final int centeredTextX = x + (BAR_WIDTH - textWidth) / 2;

        g.drawString(text, centeredTextX, yPos - PLAYER_TEXT_OFFSET);
    }
}
