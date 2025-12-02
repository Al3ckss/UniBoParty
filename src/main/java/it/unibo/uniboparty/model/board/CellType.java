package it.unibo.uniboparty.model.board;

/**
 * Types of cells that can appear on the main board.
 * <ul>
 *   <li>{@link #NORMAL} – a regular cell with no special effect.</li>
 *   <li>{@link #MINIGAME} – a cell that triggers a minigame.</li>
 *   <li>{@link #BACK_2} – a cell that moves the player back by two positions.</li>
 *   <li>{@link #SWAP} – a cell that swaps the player position with a random opponent.</li>
 * </ul>
 */
public enum CellType {
    /**
     * A normal cell with no special effect.
     */
    NORMAL,

    /**
     * A cell that starts a minigame.
     */
    MINIGAME,

    /**
     * A cell that moves the player back by two positions.
     */
    BACK_2,

    /**
     * A cell that swaps the player position with a random opponent.
     */
    SWAP
}
