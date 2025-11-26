package it.unibo.uniboparty.model.minigames.whacamole.impl;

/**
 * Rappresenta una fotografia dello stato corrente del gioco.
 * La View lo sua per aggiornare la grafica senza poter modificare direttamente il Model.
 */
public final class WhacAMoleGameState {

    private final int score;
    private final long timeLeftMillis;
    private final boolean gameOver;
    private final int currentMoleIndex;
    private final int totalHoles;

    public WhacAMoleGameState(
        int score,
        long timeLeftMillis,
        boolean gameOver,
        int currentMoleIndex,
        int totalHoles
    ) {
        this.score = score;
        this.timeLeftMillis = timeLeftMillis;
        this.gameOver = gameOver;
        this.currentMoleIndex = currentMoleIndex;
        this.totalHoles = totalHoles;
    }

    public int getScore() {
        return score;
    }

    public long getTimeLeftMillis() {
        return timeLeftMillis;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Ritorna l'indice (0...totalHoles-1) della buca che contiene la talpa in questo momento. Se vale -1
     * significa che al momento nessuna talpa è visibile.
     */
    public int getCurrentMoleIndex() {
        return currentMoleIndex;
    }

    public int getTotalHoles() {
        return totalHoles;
    }
}
