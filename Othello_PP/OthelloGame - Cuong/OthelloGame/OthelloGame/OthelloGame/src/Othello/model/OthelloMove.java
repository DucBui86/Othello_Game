package Othello.model;

public class OthelloMove implements Move {
    private static Mark mark;
    private int row;
    private int col;

    /**
     * Creates an instance of an Othello move, which consists of a mark, row and column.
     * @param mark the mark of the current player
     * @param row the row of the field where the move should be made
     * @param col the column of the field where the move should be made
     */
    /*@
        requires mark == Mark.XX || mark == Mark.OO;
        requires 0 <= row && row <= 7;
        requires 0 <= col && row <= 7;
        ensures this.mark == mark;
        ensures this.row == row;
        ensures this.col == col;
    */
    public OthelloMove(Mark mark, int row, int col)  {
        this.mark = mark;
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the mark of the current player for this move.
     * @return the mark
     */
    //@ensures \result == this.mark;
    public Mark getMark() {
        return this.mark;
    }

    /**
     * Returns the row of the field where the move should be made.
     * @return the row
     */
    //@ensures \result == this.row;
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the field where the move should be made.
     * @return the column
     */
    //@ensures \result == this.col;
    @Override
    public int getCol() {
        return col;
    }

    /**
     * Return string.
     * @return return string contain row, col, mark.
     */
    public String toString() {
        String word = ("Row " + this.row + " - Col " + this.col + " - Mark " + this.mark);
        return word;
    }
}
