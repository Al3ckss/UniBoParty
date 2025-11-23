package unibo.party.minigames.whacamole;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import unibo.party.minigames.whacamole.view.impl.WhacAMoleViewImpl;

/**
 * Simple launcher for the Whac-A-Mole minigame.
 */
public class WhacAMoleApp extends Application {

    @Override
    public void start(Stage stage) {
        WhacAMoleViewImpl root = new WhacAMoleViewImpl();

        Scene scene = new Scene(root, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Whac-A-Mole");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
