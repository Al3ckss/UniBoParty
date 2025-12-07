package it.unibo.uniboparty.controller.player.impl;

import java.util.List;
import java.util.Objects;

import it.unibo.uniboparty.controller.player.api.GameplayController;
import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.model.player.api.Player;
import it.unibo.uniboparty.model.player.api.PlayerManager;
import it.unibo.uniboparty.model.player.impl.PlayerManagerImpl;
import it.unibo.uniboparty.utilities.MinigameId;
import it.unibo.uniboparty.view.board.api.BoardView;

/**
 * Default implementation of the GameplayController.
 */
public final class GameplayControllerImpl implements GameplayController {

    private final PlayerManager playerManager;

    /**
     * Creates a new GameplayControllerImpl.
     *
     * @param playerNames the names of players coming from the start menu
     * @param boardView the board view to update player positions
     * @param boardController the board controller for board information
     */
    public GameplayControllerImpl(
            final List<String> playerNames,
            final BoardView boardView,
            final BoardController boardController
    ) {
        Objects.requireNonNull(playerNames);
        Objects.requireNonNull(boardView);
        Objects.requireNonNull(boardController);

        final List<Player> players = playerNames.stream()
                .map(Player::new)
                .toList();

        this.playerManager = new PlayerManagerImpl(players, boardView, boardController);
    }

    @Override
    public void onDiceRolled(final int steps) {
        this.playerManager.playTurn(steps);

        //TODO notify UI that the match has finished

    }

    @Override
    public void onMinigameFinished(final int result, final MinigameId id) {
        if (result == 2) {
            return; //minigame is not finished
        }

        if (result == 1) {
            this.playerManager.playTurn(1);
        } else if (result == 0) {
            this.playerManager.playTurn(-1);
        }
    }
}
