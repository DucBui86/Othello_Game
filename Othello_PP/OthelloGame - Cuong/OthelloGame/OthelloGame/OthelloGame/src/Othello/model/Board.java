package Othello.model;


/**
 * Board for the Othello game.
 */
public class Board {

    /**
     * Dimension of the board, i.e., if set to 8, the board has 8 rows and 8 columns.
     */
    public static final int DIM = 8;
    private static final String DELIM = "     ";
    private static final String[] NUMBERING =
            {" 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 ",
             "---+---+---+---+---+---+---+---",
             " 8 | 9 | 10| 11| 12| 13| 14| 15",
             "---+---+---+---+---+---+---+---",
             " 16| 17| 18| 19| 20| 21| 22| 23",
             "---+---+---+---+---+---+---+---",
             " 24| 25| 26| 27| 28| 29| 30| 31",
             "---+---+---+---+---+---+---+---",
             " 32| 33| 34| 35| 36| 37| 38| 39",
             "---+---+---+---+---+---+---+---",
             " 40| 41| 42| 43| 44| 45| 46| 47",
             "---+---+---+---+---+---+---+---",
             " 48| 49| 50| 51| 52| 53| 54| 55",
             "---+---+---+---+---+---+---+---",
             " 56| 57| 58| 59| 60| 61| 62| 63"};
    private static final String LINE = NUMBERING[1];

    /**
     * The DIM by DIM fields of the Othello board. See NUMBERING for the
     * coding of the fields.
     */
    public Mark[] fields;

    // -- Constructors -----------------------------------------------
    /**
     * Creates an empty board.
     */
    //@ ensures (\forall int i; (i >= 0 && i < DIM*DIM); fields[i] == Mark.EMPTY) && fields[27] == Mark.OO && fields[28] == Mark.XX && fields[35] == Mark.XX && fields[36] == Mark.OO;
    public Board() {
        fields = new Mark[DIM * DIM];
        for (int i = 0; i < DIM * DIM; i++) {
            fields[i] = Mark.EMPTY;
        }
        fields[27] = Mark.OO;
        fields[28] = Mark.XX;
        fields[35] = Mark.XX;
        fields[36] = Mark.OO;

    }

    /**
     * Creates a deep copy of this field.
     */
    /*@ ensures \result != this;
     ensures (\forall int i; (i >= 0 && i < DIM*DIM); \result.fields[i] == this.fields[i]);
     @*/
    public Board deepCopy() {
        Board copy = new Board();
        for (int i = 0; i < DIM * DIM; i++) {
            copy.setField(i, fields[i]);
        }
        return copy;
    }

    /**
     * Calculates the index in the linear array of fields from a (row, col)
     * @return the index belonging to the (row,col)-field
     */
    /*@ requires row >= 0 && row < DIM;
    requires col >= 0 && row < DIM;
     @*/
    public int index(int row, int col) {
        return row * DIM + col;
    }
    public int row(int index) {
        return index / DIM;
    }
    public int col(int index) {
        return index % DIM;
    }

    /**
     * Returns true if index is a valid index of a field on the board.
     * @return true if 0 <= index < DIM*DIM
     */
    //@ ensures index >= 0 && index < DIM*DIM ==> \result == true;
    public boolean isField(int index) {
        return index >= 0 && index < DIM * DIM;
    }

    /**
     * Returns true of the (row,col) pair refers to a valid field on the board.
     * @return true if 0 <= row < DIM && 0 <= col < DIM
     */
    //@ ensures row >= 0 && row < DIM && col >= 0 && col < DIM ==> \result == true;
    public boolean isField(int row, int col) {
        if ((row < 0) || (col < 0)) {
            return false;
        }
        if ((row >= DIM) || (col >= DIM)) {
            return false;
        }
        return isField(index(row, col));
    }
    
    /**
     * Returns the content of the field i.
     * @param i the number of the field (see NUMBERING)
     * @return the mark on the field
     */
    /*@ requires isField(i);
    ensures \result == Mark.EMPTY || \result == Mark.OO || \result == Mark.XX;
     @*/
    public Mark getField(int i) {
        if (isField(i)) {
            return fields[i];
        }
        return null;
    }

    /**
     * Returns the content of the field referred to by the (row,col) pair.
     * @param row the row of the field
     * @param col the column of the field
     * @return the mark on the field
     */
    /*@ requires isField(row, col);
    ensures \result == Mark.EMPTY || \result == Mark.OO || \result == Mark.XX;
     @*/
    public Mark getField(int row, int col) {
        if (isField(row, col)) {
            return getField(index(row, col));
        }
        return null;
    }

    /**
     * Returns true if the field i is empty.
     * @param i the index of the field (see NUMBERING)
     * @return true if the field is empty
     */
    /*@ requires isField(i);
    ensures getField(i) == Mark.EMPTY ==> \result == true;
     @*/
    public boolean isEmptyField(int i) {
        if (isField(i)) {
            return getField(i) == Mark.EMPTY;
        }
        return false;
    }

    /**
     * Returns true if the field referred to by the (row,col) pair it empty.
     * @param row the row of the field
     * @param col the column of the field
     * @return true if the field is empty
     */
    /*@ requires isField(row, col);
    ensures getField(row, col) == Mark.EMPTY ==> \result == true;
     @*/
    public boolean isEmptyField(int row, int col) {
        return isField(row, col) && getField(index(row, col)) == Mark.EMPTY;
    }

    /**
     * Tests if the whole board is full.
     * @return true if all fields are occupied
     */
    //@ ensures (\forall int i; (i >= 0 && i < DIM*DIM); fields[i] == Mark.XX || fields[i] == Mark.OO);
    public boolean isFull() {
        for (int i = 0; i < DIM * DIM; i++) {
            if (fields[i] == Mark.EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the game is over. The game is over when there is a winner
     * or the whole board is full.
     * or 2 players can not move (implement in isGameOver at OthelloGame)
     * @return true if the game is over
     */
    //@ ensures isFull() ==> \result == true;
    public boolean gameOver() {
        return isFull();
    }

    /**
     * Checks if the mark m has won.
     * the mark m has won if the count of mark is greater than half the total number of fields
     * @param mark the mark of interest
     * @return true if the mark has won
     */
    public boolean isWinner(Mark mark) {
        int count = 0;
        // Count the number of pieces of the given mark on the board
        for (int i = 0; i < DIM * DIM; i++) {
            if (fields[i] == mark) {
                count++;
            }
        }

        // Check if the count is greater than half the total number of fields
        if (count > (DIM * DIM) / 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the game has a winner.
     * @return true if the student has a winner.
     */
    //@ ensures isWinner(Mark.XX) || isWinner(Mark.OO) ==> \result == true;
    public boolean hasWinner() {
         return isWinner(Mark.XX) || isWinner(Mark.OO);
    }

    /**
     * Returns a String representation of this board. In addition to the current
     * situation, the String also shows the numbering of the fields.
     * @return the game situation as String
     */

    public String toString() {
        String s = "";
        for (int i = 0; i < DIM; i++) {
            String row = "";
            for (int j = 0; j < DIM; j++) {
                row += " " + getField(i, j).toString().substring(0, 1).replace("E", " ") + " ";
                if (j < DIM - 1) {
                    row = row + "|";
                }
            }
            s = s + row + DELIM + NUMBERING[i * 2];
            if (i < DIM - 1) {
                s = s + "\n" + LINE + DELIM + NUMBERING[i * 2 + 1] + "\n";
            }
        }
        return s;
    }

    /**
     * Empties all fields of this board (i.e., let them refer to the value
     * Mark.EMPTY).
     */
    //@ ensures (\forall int i; (i >= 0 && i < DIM*DIM); fields[i] == Mark.EMPTY);
    public void reset() {
        for (int i = 0; i < DIM * DIM; i++) {
            fields[i] = Mark.EMPTY;
        }
        fields[27] = Mark.OO;
        fields[28] = Mark.XX;
        fields[35] = Mark.XX;
        fields[36] = Mark.OO;
    }

    /**
     * Sets the content of field i to the mark m.
     * @param i the field number (see NUMBERING)
     * @param m the mark to be placed
     */
    /*@ requires isField(i);
    ensures getField(i) == m;
     @*/
    public void setField(int i, Mark m) {
        if (isField(i)) {
            fields[i] = m;
        }
    }

    /**
     * Sets the content of the field represented by
     * the (row,col) pair to the mark m.
     * @param row the field's row
     * @param col the field's column
     * @param m the mark to be placed
     */
    /*@ requires isField(row, col);
    ensures getField(row, col) == m;
     @*/
    public void setField(int row, int col, Mark m) {
        fields[index(row, col)] = m;
    }
}
