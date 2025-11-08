package dinosaurgame;

import dinosaurgame.controller.ControllerImpl;
import dinosaurgame.model.ModelImpl;
import dinosaurgame.view.ViewImpl;

public class App {
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl();
        ViewImpl view = new ViewImpl(model);
        new ControllerImpl(model, view);
    }
}
