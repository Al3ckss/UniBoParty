package it.unibo.UniBoParty.model.minigames.dinosaurGame.impl;

import java.util.Random;

public class ObstacleFactory {

    private static final int[][] OBSTACLE_TYPES = {
        {20, 50},
        {50, 40},
        {35, 70}
    };

    private static final Random random = new Random();

    public static ObstacleImpl create(
            int startX,
            int groundY,
            int minDistance,
            int maxVariation,
            int speed
    ) {
        int[] type = OBSTACLE_TYPES[random.nextInt(OBSTACLE_TYPES.length)];
        int width = type[0];
        int height = type[1];

        int x = startX + minDistance + random.nextInt(maxVariation);

        return new ObstacleImpl(x, groundY, width, height, speed);
    }
}
