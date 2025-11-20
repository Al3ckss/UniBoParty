package dinosaurgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ModelImplTest {

    private ModelImpl model;
    private final int GROUND_Y = 350; // valore costante dal modello

    @BeforeEach
    void setUp() {
        model = new ModelImpl();
    }

    @Test
    void testInitialDinoPosition() {
        assertEquals(50, model.getDinoX(), "La coordinata X iniziale del dinosauro dovrebbe essere 50");
        assertEquals(GROUND_Y, model.getDinoY(), "La coordinata Y iniziale del dinosauro dovrebbe essere 350");
        assertEquals(40, model.getDinoWidth(), "La larghezza del dinosauro dovrebbe essere 40");
        assertEquals(50, model.getDinoHeight(), "L'altezza del dinosauro dovrebbe essere 50");
    }

    @Test
    void testJumpAndLanding() {
        int yBefore = model.getDinoY();
        model.jump();
        model.update(); // applica il salto
        assertTrue(model.getDinoY() < yBefore, "Il dinosauro dovrebbe saltare (Y diminuita)");

        model.releaseJump();

        // Applica update finché non ritorna a terra
        while (model.getDinoY() < GROUND_Y) {
            model.update();
        }

        assertEquals(GROUND_Y, model.getDinoY(), "Il dinosauro dovrebbe tornare a terra dopo il salto");
    }

    @Test
    void testObstaclesAreCreated() {
        ArrayList<ObstacleImpl> obstacles = model.getObstacles();
        assertNotNull(obstacles, "La lista di ostacoli non deve essere nulla");
        assertTrue(obstacles.size() >= 3, "Devono esserci almeno 3 ostacoli iniziali");
    }

    @Test
    void testObstacleMovement() {
        ArrayList<ObstacleImpl> obstacles = model.getObstacles();
        ObstacleImpl o = obstacles.get(0);
        int xBefore = o.getObstX();
        o.moveObstacle();
        assertTrue(o.getObstX() < xBefore, "L'ostacolo dovrebbe muoversi verso sinistra");
    }

    @Test
    void testDifficultyIncrease() {
        ArrayList<ObstacleImpl> obstacles = model.getObstacles();
        int speedBefore = obstacles.get(0).getObstSpeed();

        // Aggiorna abbastanza volte per triggerare l'incremento difficoltà
        for (int i = 0; i < 500; i++) {
            model.update();
        }

        int speedAfter = obstacles.get(0).getObstSpeed();
        assertTrue(speedAfter >= speedBefore, "La velocità dell'ostacolo dovrebbe aumentare con la difficoltà");
    }
}
