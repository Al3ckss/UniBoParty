package it.unibo.uniboparty.model.player.api;

/**
 * Represents a player in the game.
 */
public final class Player {

    private final String name;

    /**
     * @param name the player's name
     */
    public Player(final String name) {
        this.name = name;
    }

    /**
     * @return the player's name
     */
    public String getName() {
        return this.name;
    }
}
