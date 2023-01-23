package Othello.model;

public class OthelloMove implements Move {
    private static Mark mark;
    private int row;
    private int col;

    public OthelloMove(Mark mark, int row, int col) {
        this.mark = mark;
        this.row = row;
        this.col = col;
    }

    public Mark getMark() {
        return this.mark;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    public String toString() {
        String word = ("Row " + this.row + " - Col " + this.col + " - Mark " + this.mark);
        return word;
    }
}
