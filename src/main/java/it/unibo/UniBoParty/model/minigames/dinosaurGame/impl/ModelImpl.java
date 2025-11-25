package it.unibo.UniBoParty.model.minigames.dinosaurGame.impl;

import java.util.ArrayList;

import it.unibo.UniBoParty.model.minigames.dinosaurGame.api.Model;

public class ModelImpl implements Model {

    private int dinoX = GameConfig.INIT_DINO_X;
    private int dinoY = GameConfig.GROUND_Y;
    private int dinoWidth = GameConfig.DINO_WIDTH;
    private int dinoHeight = GameConfig.DINO_HEIGHT;

    private double velY = 0;
    private final double gravity = GameConfig.GRAVITY;
    private int nearestX = 0;

    private boolean isJumping = false;
    private boolean isHoldingJump = false;

    private int difficulty = 0;

    public ArrayList<ObstacleImpl> obstacles = new ArrayList<>();

    private GameState gameState = GameState.RUNNING;

    public ModelImpl() {
        int lastX = 600;
        for (int i = 0; i < GameConfig.NUM_INITIAL_OBSTACLES; i++) {
            ObstacleImpl o = ObstacleFactory.create(
                lastX,
                GameConfig.GROUND_Y,
                GameConfig.INIT_OBSTACLE_MIN_DISTANCE,
                GameConfig.INIT_OBSTACLE_MAX_VARIATION,
                GameConfig.OBSTACLE_INITIAL_SPEED
            );
            obstacles.add(o);
            lastX = o.getObstX();
        }
    }

    @Override
    public void update() {
        if (gameState == GameState.GAME_OVER) return;

        difficulty++;
        nearestX = 0;

        if (isJumping) {
            dinoY += velY;
            velY += isHoldingJump ? gravity * 0.65 : gravity;
        }

        if (dinoY >= GameConfig.GROUND_Y) {
            dinoY = GameConfig.GROUND_Y;
            velY = 0;
            isJumping = false;
        }

        for (ObstacleImpl o : obstacles) {
            if (o.getObstX() > nearestX) nearestX = o.getObstX();
        }

        for (int i = 0; i < obstacles.size(); i++) {
            ObstacleImpl o = obstacles.get(i);
            o.moveObstacle();
            if (o.getObstX() + o.getObstWidth() < 0) {
                obstacles.set(i,
                    ObstacleFactory.create(
                        nearestX,
                        GameConfig.GROUND_Y,
                        GameConfig.INIT_OBSTACLE_MIN_DISTANCE,
                        GameConfig.INIT_OBSTACLE_MAX_VARIATION,
                        GameConfig.OBSTACLE_INITIAL_SPEED + (difficulty / GameConfig.DIFFICULTY_INCREMENT_INTERVAL)
                    )
                );
            }
        }

        for (ObstacleImpl o : obstacles) {
            boolean overlapX = dinoX + dinoWidth > o.getObstX()
                            && dinoX < o.getObstX() + o.getObstWidth();
            boolean overlapY = dinoY > o.getObstY() - o.getObstHeight();

            if (overlapX && overlapY) {
                gameState = GameState.GAME_OVER;
                return;
            }
        }

        if (difficulty % GameConfig.DIFFICULTY_INCREMENT_INTERVAL == 0) {
            for (ObstacleImpl o : obstacles) {
                o.setObstSpeed(o.getObstSpeed() + 1);
            }
            System.out.println("Difficulty Up");
        }
    }

    @Override
    public void jump() {
        if (!isJumping && gameState == GameState.RUNNING) {
            isJumping = true;
            isHoldingJump = true;
            velY = GameConfig.INIT_JUMP_VELOCITY;
        }
    }

    @Override
    public void releaseJump() {
        isHoldingJump = false;
    }

    @Override
    public int getDinoX() {
        return dinoX;
    }

    @Override
    public int getDinoY() {
        return dinoY;
    }

    @Override
    public int getDinoWidth() {
        return dinoWidth;
    }

    @Override
    public int getDinoHeight() {
        return dinoHeight;
    }

    @Override
    public ArrayList<ObstacleImpl> getObstacles() {
        return obstacles;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }
}
