package nl.hu.bep.billy.algorithms.mcts;

public class Cell {
    private final CellStatus status;

    public Cell(CellStatus status) {
        this.status = status;
    }

    public CellStatus getStatus() {
        return status;
    }
}
