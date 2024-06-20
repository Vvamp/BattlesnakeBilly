package nl.hu.bep.billy.algorithms.mcts;


public class Cell {
    private CellStatus status;

    public Cell(CellStatus status) {
        this.status = status;
    }

    public Cell(Cell cell) {
        this.status = cell.status;
    }

    public CellStatus getStatus() {
        return status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }
}