package it.unibo.uniboparty.model.minigames.tetris;

import org.junit.jupiter.api.Test;
import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    private static final Color TEST_COLOR = Color.RED;

    // Pezzo a forma di 'L' (4 blocchi) per testare la normalizzazione e rotazione
    private final int[][] L_SHAPE_COORDS = new int[][]{{1, 0}, {2, 0}, {3, 0}, {3, 1}};
    
    // Pezzo complesso per testare la normalizzazione da coordinate negative
    private final int[][] OFFSET_COORDS = new int[][]{{10, -5}, {10, -4}, {11, -5}}; // Min R=10, Min C=-5

    // Pezzo 2x2 (O-Shape)
    private final int[][] O_SHAPE_COORDS = new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};

    // -----------------------------------------------------------------------

    /**
     * test factory method and basic getters.
     */
    @Test
    void testFactoryMethodAndBasicGetters() {
        PieceImpl piece = PieceImpl.of(L_SHAPE_COORDS, "LTest", TEST_COLOR);
        
        assertEquals("LTest", piece.getName());
        assertEquals(TEST_COLOR, piece.getColor());
        assertEquals(4, piece.getCells().size());

        List<Point> cells = piece.getCells();

        assertTrue(cells.contains(new Point(0, 0))); 

        assertTrue(cells.contains(new Point(0, 1)));

        assertTrue(cells.contains(new Point(0, 2)));

        assertTrue(cells.contains(new Point(1, 2)));
    }

    /**
     * test width and height methods.
     */
    @Test
    void testWidthAndHeight() {
        PieceImpl pieceL = PieceImpl.of(L_SHAPE_COORDS, "L", TEST_COLOR);

        assertEquals(2, pieceL.width());
        assertEquals(3, pieceL.height());

        PieceImpl pieceO = PieceImpl.of(O_SHAPE_COORDS, "O", TEST_COLOR);

        assertEquals(2, pieceO.width());
        assertEquals(2, pieceO.height());
    }

    /**
     * test normalization of coordinates with offsets.
     */
    @Test
    void testNormalizationWithOffsetCoords() {
        PieceImpl piece = PieceImpl.of(OFFSET_COORDS, "Offset", TEST_COLOR);
      
        List<Point> cells = piece.getCells();
        assertEquals(3, cells.size());

        assertTrue(cells.contains(new Point(0, 0))); 

        assertTrue(cells.contains(new Point(1, 0)));

        assertTrue(cells.contains(new Point(0, 1)));

        assertEquals(2, piece.width());
        assertEquals(2, piece.height());
    }
}