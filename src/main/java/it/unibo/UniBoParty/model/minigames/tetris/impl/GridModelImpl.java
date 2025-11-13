package it.unibo.uniboparty.model.minigames.tetris.impl;
import java.awt.*;
import java.util.*;
import java.util.List;

import it.unibo.uniboparty.model.minigames.tetris.api.GridModel;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;

public final class GridModelImpl implements GridModel {
    private final int rows;
    private final int cols;
    private final boolean[][] grid;
    private final List<ModelListener> listeners = new ArrayList<>();

    GridModelImpl(int rows, int cols) {
        this.rows = rows; this.cols = cols;
        this.grid = new boolean[rows][cols];
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void addListener(ModelListener l) { 
        listeners.add(l); 
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void removeListener(ModelListener l) { 
        listeners.remove(l); 
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void fireChange() {
         for (ModelListener l : listeners) {
            l.onModelChanged();
         }
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public boolean isOccupied(int r, int c) { 
        return grid[r][c]; 
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public boolean canPlace(PieceImpl piece, int topR, int leftC) {
        for (Point rel : piece.getCells()) {
            int r = topR + rel.y, c = leftC + rel.x;
            if (r < 0 || r >= rows || c < 0 || c >= cols) return false;
            if (grid[r][c]) return false;
        }
        return true;
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void place(PieceImpl piece, int topR, int leftC) {
        if (!canPlace(piece, topR, leftC)) throw new IllegalArgumentException("Invalid placement");
        for (Point rel : piece.getCells()) grid[topR + rel.y][leftC + rel.x] = true;
        //clearFullLines();
        //fireChange();
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public int clearFullLines() {
        
        java.util.List<Integer> fullRows = new ArrayList<>();
        java.util.List<Integer> fullCols = new ArrayList<>();

        
        for (int r = 0; r < rows; r++) {
            boolean full = true;
            for (int c = 0; c < cols; c++) if (!grid[r][c]) { full = false; break; }
            if (full) fullRows.add(r);
        }
        
        for (int c = 0; c < cols; c++) {
            boolean full = true;
            for (int r = 0; r < rows; r++) if (!grid[r][c]) { full = false; break; }
            if (full) fullCols.add(c);
        }

        for (int r : fullRows) Arrays.fill(grid[r], false);
        for (int c : fullCols) for (int r = 0; r < rows; r++) grid[r][c] = false;

        return fullRows.size() + fullCols.size();
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void reset() {
        for (int r = 0; r < rows; r++) Arrays.fill(grid[r], false);
        fireChange();
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public int getRows() {
        return rows; 
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public int getCols() {
        return cols; 
    }
}
