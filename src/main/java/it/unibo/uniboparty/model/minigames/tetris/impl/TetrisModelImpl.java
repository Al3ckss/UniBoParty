package it.unibo.uniboparty.model.minigames.tetris.impl;

import it.unibo.uniboparty.model.minigames.tetris.api.TetrisModel;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import it.unibo.uniboparty.model.minigames.tetris.api.GridModel;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;

/**
 * Implementation of the TetrisModel interface.
 */
public final class TetrisModelImpl implements TetrisModel {
    private final GridModel grid;
    private final Random rng = new Random();
    private final List<PieceImpl> rack;
    private PieceImpl selected;
    private int score;

    /**
     * Creates a new TetrisModelImpl instance with the specified grid dimensions.
     * 
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public TetrisModelImpl(final int rows, final int cols) {
        this.grid = new GridModelImpl(rows, cols);
        this.score = 0;
        this.rack = new ArrayList<>();
        this.selected = null;
        newRack();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void newRack() {
        rack.clear();
        Stream.generate(this::randomPiece)
              .limit(3)
              .forEach(rack::add);
        notifyAllListeners();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void consumePiece(final PieceImpl p) {
        rack.remove(p);
        if (rack.isEmpty()) {
            newRack();
        }
        notifyAllListeners();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void award(final int cellsPlaced, final int linesCleared) {
        score += cellsPlaced + (linesCleared * 10);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public boolean hasAnyMove() {
       return rack.stream().anyMatch(p -> 
            IntStream.range(0, grid.getRows()).anyMatch(r -> 
                IntStream.range(0, grid.getCols()).anyMatch(c -> 
                    grid.canPlace(p, r, c)
                )
            )
        );
    }

    /**
     * {@InheritDoc}.
     */
    @Override

    public PieceImpl randomPiece() {
        final List<PieceImpl> bag = StandardPieces.ALL;
        return bag.get(rng.nextInt(bag.size()));
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    // Delegate listeners to grid to keep it simple
    public void addListener(final ModelListener l) {
        grid.addListener(l);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void notifyAllListeners() {
        grid.fireChange(); 
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public boolean checkPlacement(final PieceImpl p, final int r, final int c) {
        return this.grid.canPlace(p, r, c);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void askPlacement(final PieceImpl p, final int r, final int c) {
        this.grid.place(p, r, c);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public int askClearFullLines() {
        return this.grid.clearFullLines();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public GridModel getGrid() {
        return this.grid.copy();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public PieceImpl[] getRack() {
        return rack.stream().toArray(PieceImpl[]::new);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void selectPiece(final PieceImpl p) {
        selected = p;
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public PieceImpl getSelected() {
        return selected;
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void tryPlaceAt(final int r, final int c) {
        Optional.ofNullable(this.selected)
            .ifPresent(piece -> {
                if (this.grid.canPlace(piece, r, c)) {
                    final int cellsPlaced = piece.getCells().size();
                    this.grid.place(piece, r, c);
                    final int linesCleared = this.grid.clearFullLines();
                    award(cellsPlaced, linesCleared);
                    consumePiece(piece);
                }
                this.selected = null;
            });
    }

}
