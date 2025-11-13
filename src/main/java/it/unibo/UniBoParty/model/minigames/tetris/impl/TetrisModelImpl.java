package it.unibo.uniboparty.model.minigames.tetris.impl;

import it.unibo.uniboparty.model.minigames.tetris.api.TetrisModel;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
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
    public int score;

    /**
     * Creates a new TetrisModelImpl instance with the specified grid dimensions.
     * 
     * @param rows
     * @param cols
     */
    public TetrisModelImpl(int rows, int cols) {
        this.grid = new GridModelImpl(rows, cols);
        this.score = 0;
        this.rack = new ArrayList<>();
        this.selected = null;
        newRack();
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void newRack() {
        rack.clear();
        while (rack.size() < 3)
            rack.add(randomPiece());
        notifyAllListeners();
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void consumePiece(PieceImpl p) {
        rack.remove(p);
        if (rack.isEmpty())
            newRack();
        notifyAllListeners();
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void award(int cellsPlaced, int linesCleared) {
        score += cellsPlaced + (linesCleared * 10);
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public boolean hasAnyMove() {
        for (PieceImpl p : rack) {
            for (int r = 0; r < grid.getRows(); r++)
                for (int c = 0; c < grid.getCols(); c++)
                    if (grid.canPlace(p, r, c))
                        return true;
        }
        return false;
    }

    /**
     * {@InheritDoc}
     */
    @Override

    public PieceImpl randomPiece() {
        java.util.List<PieceImpl> bag = StandardPieces.ALL;
        return bag.get(rng.nextInt(bag.size()));
    }

    /**
     * {@InheritDoc}
     */
    @Override
    // Delegate listeners to grid to keep it simple
    public void addListener(ModelListener l) {
        grid.addListener(l);
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void notifyAllListeners() {
        grid.fireChange(); /* no-op but ensures consistency */
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public boolean checkPlacement(PieceImpl p, int r, int c) {
        return this.grid.canPlace(p, r, c);
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void askPlacement(PieceImpl p, int r, int c) {
        this.grid.place(p, r, c);
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public int askClearFullLines() {
        return this.grid.clearFullLines();
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public GridModel getGrid() {
        return this.grid;
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public PieceImpl[] getRack() {
        return Collections.unmodifiableList(rack).toArray(new PieceImpl[0]);
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void selectPiece(PieceImpl p) {
        selected = p;
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public PieceImpl getSelected() {
        return selected;
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void tryPlaceAt(int r, int c) {
        if (this.selected == null)
            return;
        if (this.grid.canPlace(this.selected, r, c)) {
            int cellsPlaced = this.selected.getCells().size();
            this.grid.place(this.selected, r, c);
            int linesCleared = this.grid.clearFullLines();
            award(cellsPlaced, linesCleared);
            consumePiece(this.selected);
            this.selected = null;
        } else {
            this.selected = null;
        }
    }

   
}
