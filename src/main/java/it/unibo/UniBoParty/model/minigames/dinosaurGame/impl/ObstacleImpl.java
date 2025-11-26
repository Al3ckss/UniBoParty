package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

import it.unibo.uniboparty.model.minigames.dinosaurgame.api.Obstacle;

/**
 * Implementation of obstacles.
 */
public class ObstacleImpl implements Obstacle {

    private int obstX;
    private final int obstY;
    private final int obstWidth;
    private final int obstHeight;
    private int obstSpeed;

    /**
     * Implementation of obstacle class.
     * 
     * @param obstX      the x-coordinate of the obstacle's position
     * @param obstY      the y-coordinate of the obstacle's position (ground level)
     * @param obstWidth  the width of the obstacle
     * @param obstHeight the height of the obstacle
     * @param obstSpeed  the speed at which the obstacle moves
     */
    public ObstacleImpl(final int obstX, final int obstY, final int obstWidth, final int obstHeight, final int obstSpeed) {
        this.obstX = obstX;
        this.obstY = obstY;
        this.obstWidth = obstWidth;
        this.obstHeight = obstHeight;
        this.obstSpeed = obstSpeed;
    }

    @Override
    public final int moveObstacle() {
        obstX -= obstSpeed;
        return obstX;
    }

    @Override
    public final int getObstX() {
        return obstX;
    }

    @Override
    public final void setObstX(final int obstX) {
        this.obstX = obstX;
    }

    @Override
    public final int getObstY() {
        return obstY;
    }

    @Override
    public final int getObstWidth() {
        return obstWidth;
    }

    @Override
    public final int getObstHeight() {
        return obstHeight;
    }

    @Override
    public final int getObstSpeed() {
        return obstSpeed;
    }

    @Override
    public final void setObstSpeed(final int obstSpeed) {
        this.obstSpeed = obstSpeed;
    }
}
