package Othello.model;

import java.util.Arrays;

/**
 * Board for the Tic Tac Toe game.
 */
public class Board {

    /**
     * Dimension of the board, i.e., if set to 3, the board has 3 rows and 3 columns.
     */
    public static final int DIM = 8;
    private static final String DELIM = "     ";
    private static final String[] NUMBERING =
            {" 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 ",
             "---+---+---+---+---+---+---+---",
             " 8 | 9 |10 | 11| 12| 13| 14| 15",
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
     * The DIM by DIM fields of the Tic Tac Toe board. See NUMBERING for the
     * coding of the fields.
     */
    private Mark[] fields;
    private Game othelloGame;


    // -- Constructors -----------------------------------------------
    /**
     * Creates an empty board.
     */
    //@ ensures (\forall int i; (i >= 0 && i < DIM*DIM); fields[i] == Mark.EMPTY);
    public Board() {
    	 // TODO: implement
        fields = new Mark[DIM*DIM];
        for (int i = 0; i < DIM*DIM; i++) {
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
    	 // TODO: implement
        Board copy = new Board();
        for (int i = 0; i < DIM*DIM; i++) {
            copy.setField(i, fields[i]);
        }
        return copy;
    }

    /**
     * Calculates the index in the linear array of fields from a (row, col)
     * pair.
     * @return the index belonging to the (row,col)-field
     */
    /*@ requires row >= 0 && row < DIM;
    requires col >= 0 && row < DIM;
     @*/
    public int index(int row, int col) {
    	 // TODO: implement
        int [][] indexBoard = new int[][] {
                {0, 1, 2, 3, 4, 5, 6, 7},
                {8, 9, 10, 11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20, 21, 22, 23},
                {24, 25, 26, 27, 28, 29, 30, 31},
                {32, 33, 34, 35, 36, 37, 38, 39},
                {40, 41, 42, 43, 44, 45, 46, 47},
                {48, 49, 50, 51, 52, 53, 54, 55},
                {56, 57, 58, 59, 60, 61, 62, 63}
        };
        return indexBoard [row][col];
    }
    public int row(int index) {
        switch (index) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return 0;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return 1;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                return 2;
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                return 3;
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
                return 4;
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
                return 5;
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
                return 6;
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
                return 7;
            default:
                return 00000000;
        }
    }
    public int col(int index) {
        switch (index) {
            case 0:
            case 8:
            case 16:
            case 24:
            case 32:
            case 40:
            case 48:
            case 56:
                return 0;
            case 1:
            case 9:
            case 17:
            case 25:
            case 33:
            case 41:
            case 49:
            case 57:
                return 1;
            case 2:
            case 10:
            case 18:
            case 26:
            case 34:
            case 42:
            case 50:
            case 58:
                return 2;
            case 3:
            case 11:
            case 19:
            case 27:
            case 35:
            case 43:
            case 51:
            case 59:
                return 3;
            case 4:
            case 12:
            case 20:
            case 28:
            case 36:
            case 44:
            case 52:
            case 60:
                return 4;
            case 5:
            case 13:
            case 21:
            case 29:
            case 37:
            case 45:
            case 53:
            case 61:
                return 5;
            case 6:
            case 14:
            case 22:
            case 30:
            case 38:
            case 46:
            case 54:
            case 62:
                return 6;
            case 7:
            case 15:
            case 23:
            case 31:
            case 39:
            case 47:
            case 55:
            case 63:
                return 7;
            default:
                return 11111111;
        }
    }

    /**
     * Returns true if index is a valid index of a field on the board.
     * @return true if 0 <= index < DIM*DIM
     */
    //@ ensures index >= 0 && index < DIM*DIM ==> \result == true;
    public boolean isField(int index) {
    	 // TODO: implement
        return index >= 0 && index < DIM * DIM;
    }

    /**
     * Returns true of the (row,col) pair refers to a valid field on the board.
     * @return true if 0 <= row < DIM && 0 <= col < DIM
     */
    //@ ensures row >= 0 && row < DIM && col >= 0 && col < DIM ==> \result == true;
    public boolean isField(int row, int col) {
    	 // TODO: implement
        return row >= 0 && row < DIM && col >= 0 && col < DIM;
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
    	 // TODO: implement
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
    	 // TODO: implement
        if (isField(row, col)) {
            return getField(row*DIM+col);
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
    	 // TODO: implement
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
    	 // TODO: implement
        return isField(row, col) && getField(index(row, col)) == Mark.EMPTY;
    }

    /**
     * Tests if the whole board is full.
     * @return true if all fields are occupied
     */
    //@ ensures (\forall int i; (i >= 0 && i < DIM*DIM); fields[i] == Mark.XX || fields[i] == Mark.OO);
    public boolean isFull() {
    	 // TODO: implement
        for (int i = 0; i < DIM*DIM; i++) {
            if (fields[i] == Mark.EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the game is over. The game is over when there is a winner
     * or the whole board is full.
     * @return true if the game is over
     */
    //@ ensures isFull() || hasWinner() ==> \result == true;
    public boolean gameOver() {
    	 // TODO: implement
        return isFull() || hasWinner();
    }


    /**
     * Checks if the mark m has won. A mark wins if it controls at
     * least one row, column or diagonal.
     * @param m the mark of interest
     * @return true if the mark has won
     */

    public boolean isWinner(Mark m) {
        // Count the number of pieces of the given mark on the board
        int count = 0;
        for (int i = 0; i < DIM * DIM; i++) {
            if (fields[i] == m) {
                count++;
            }
        }

        // Check if the count is greater than half the total number of fields
        if (count > (DIM * DIM - count)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Returns true if the game has a winner. This is the case when one of the
     * marks controls at least one row, column or diagonal.
     * @return true if the student has a winner.
     */
    //@ ensures isWinner(Mark.XX) || isWinner(Mark.OO) ==> \result == true;
    public boolean hasWinner() {
    	 // TODO: implement
         return isWinner(Mark.XX) || isWinner(Mark.OO);
    }

    /**
     * Returns a String representation of this board. In addition to the current
     * situation, the String also shows the numbering of the fields.
     *
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
    	 // TODO: implement
        for (int i = 0; i < DIM*DIM; i++) {
            fields[i] = Mark.EMPTY;
        }
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
    	 // TODO: implement, see exercise P-4.6
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
    	 // TODO: implement
        fields[row*DIM+col] = m;
    }
}
