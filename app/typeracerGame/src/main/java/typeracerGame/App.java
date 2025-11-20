package typeracerGame;

import typeracerGame.controller.ControllerImpl;
import typeracerGame.model.ModelImpl;
import typeracerGame.view.ViewImpl;

public class App {
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl();
        ViewImpl view = new ViewImpl();

        model.setNewWord();
        view.setLabel1(model.getCurrentWord());
        view.updateTimeLabel(model.getTime());

        new ControllerImpl(model, view);
    }
}
