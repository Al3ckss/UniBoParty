package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

public final class GameConfig {

    static public final int GROUND_Y = 350;
    static public final int DINO_WIDTH = 40;
    static public final int DINO_HEIGHT = 50;
    static public final int INIT_DINO_X = 50;

    static public final int PANEL_WIDTH = 600;
    static public final int PANEL_HEIGHT = 400;
    static public final double GRAVITY = 0.7;
    static public final int INIT_JUMP_VELOCITY = -15;

    static public final int NUM_INITIAL_OBSTACLES = 3;
    static public final int INIT_OBSTACLE_MIN_DISTANCE = 400;
    static public final int INIT_OBSTACLE_MAX_VARIATION = 300;
    static public final int OBSTACLE_INITIAL_SPEED = 5;

    static public final int TIMER_DELAY_MS = 12;

    static public final int DIFFICULTY_INCREMENT_INTERVAL = 500;
    public static final double JUMP_GRAVITY = 0.65;

    private GameConfig() { }
}
