package it.unibo.uniboparty.controller.end_screen.impl;

import it.unibo.uniboparty.application.GameLauncher;
import it.unibo.uniboparty.controller.end_screen.api.LeaderboardController;
import it.unibo.uniboparty.model.end_screen.api.LeaderboardModel;
import it.unibo.uniboparty.model.end_screen.impl.LeaderboardModelImpl;
import it.unibo.uniboparty.view.end_screen.api.LeaderboardView;
import it.unibo.uniboparty.view.end_screen.impl.LeaderboardViewImpl;

/**
 * Concretw implementation of the leaderboard.
 */
public class LeaderboardControllerImpl implements LeaderboardController {

    private final LeaderboardModel model;
    private final LeaderboardView view;

    /**
     * Constructor. Instantiates Model and View internally to satisfy SpotBugs/MVC rules.
     */
    public LeaderboardControllerImpl() {
        this.model = new LeaderboardModelImpl();
        this.view = new LeaderboardViewImpl();

        initController();
    }

    private void initController() {
        // 1. Prendi i dati dal model e mostrali nella view
        view.showPodium(model.getTopPlayers());

        // 2. Gestisci il tasto "Indietro"
        view.addBackListener(e -> {
            view.close(); // Chiudi questa finestra
            // Riapri il launcher principale
            GameLauncher.main(new String[]{});
        });
    }
}
