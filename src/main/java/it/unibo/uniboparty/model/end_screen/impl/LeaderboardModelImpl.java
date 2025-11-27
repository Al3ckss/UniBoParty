package it.unibo.uniboparty.model.end_screen.impl;

import it.unibo.uniboparty.model.end_screen.api.LeaderboardModel;
import it.unibo.uniboparty.model.end_screen.api.Player;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Concrete implementation of the Leaderboard Model.
 */
public final class LeaderboardModelImpl implements LeaderboardModel {

    @Override
    public List<Player> getTopPlayers() {
        // Qui potresti caricare da file in futuro.
        // Per ora usiamo dati mock.
        final int playerOne = 150;
        final int playerTwo = 120;
        final int playerThree = 200;
        final int playerFour = 80;
        final int playerFive = 180;
        final List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(new Player("Mario", playerOne));
        allPlayers.add(new Player("Carlo", playerTwo));
        allPlayers.add(new Player("Gaia", playerThree));
        allPlayers.add(new Player("Rachele", playerFour));
        allPlayers.add(new Player("Anna", playerFive));

        // Ordina per punteggio decrescente
        allPlayers.sort(Comparator.comparingInt(Player::getScore).reversed());

        // Ritorna solo i primi 3
        return allPlayers.subList(0, Math.min(allPlayers.size(), 3));
    }
}
