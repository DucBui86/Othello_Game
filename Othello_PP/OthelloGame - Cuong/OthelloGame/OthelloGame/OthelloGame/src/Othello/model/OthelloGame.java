package Othello.model;

import java.util.ArrayList;
import java.util.List;


public class OthelloGame implements Game {
    public static Board board;
    private final AbstractPlayer player1;
    private final AbstractPlayer player2;
    public int turnIndex;
    String[] nameDir = {"top-left", "left", "down-left", "down", "down-right", "right", "up-right", "up"};
    static final int[] dirX = {-1, 0, 1, 1, 1, 0, -1, -1};
    static final int[] dirY = {-1, -1, -1, 0, 1, 1, 1, 0};


    /**
     * Creates new game of Othello with the given players and a new game board
     * @param player1 the first player
     * @param player2 the second player
     */
     /*@requires player1 != null && player2 != null;
     */
    public OthelloGame(AbstractPlayer player1, AbstractPlayer player2 ) {
        board = new Board();
        this.player1 = player1;
        this.player2 = player2;
        this.turnIndex = 0;
    }

    /**
     * Check if the game is over or not.
     * @return true if the game is over, otherwise false.
     */

    @Override
    public boolean isGameover() {
        if (board.gameOver()) {
            return true;
        } else {
            List<Move> playerValidMoves = getValidMoves();
            if (playerValidMoves.size() == 0) {
                changeMark();
                if (playerValidMoves.size() == 0) {
                    return true;
                } else {
                   changeMark();
                   return false;
                }
            }
            return false;
        }
    }

    /**
     * if turnIndex = 0, return player1, otherwise player2.
     * @return the player whose turn it currently
     */
    /*@
       @requires player1 != null && player2 != null;
        ensures \result == player1 || \result == player2;
        ensures (turnIndex == 0) ==> (\result == player1) || (turnIndex != 0) ==> (\result == player2);
    */
    @Override
    public Player getTurn() {
        if (turnIndex == 0) {
            return player1;
        } else {
            return player2;
        }
    }
    public Mark changeMark() {
        if (turnIndex == 0) {
            return Mark.OO;
        } else {
            return Mark.XX;
        }
    }

    @Override
    public Mark getMark() {
        if (turnIndex == 0) {
            return Mark.XX;
        } else {
            return Mark.OO;
        }
    }

    /**
     * if Mark.XX is winner who is player1, else Mark.OO returns player2.
     * @return player1 or player2 is winner.
     */
    /*@
        @ensures (\result == null && !isGameover());
         ensures (\result == player1 && board.isWinner(Mark.XX)) || (\result == player2 && !board.isWinner(Mark.XX));
    */
    @Override
    public Player getWinner() {
        if (isGameover()) {
            if (board.isWinner(Mark.XX)) {
                return player1;
            } else {
                return player2;
            }
        }
        return null;
    }

    /**
     * Returns a list of valid moves for the current player.
     * @return a list of valid moves for the current player.
     */

    @Override
    public List<Move> getValidMoves() {
        List<Move> validMove = new ArrayList<>();
        Mark currentMark ;
        if (turnIndex == 0) {
            currentMark = Mark.XX;
        } else {
            currentMark = Mark.OO;
        }

        for (int i = 0; i < board.DIM * board.DIM; i++) {
            int row = board.row(i);
            int col = board.col(i);
             if (board.getField(row, col) == Mark.EMPTY) {
                if (isValidMove(new OthelloMove(currentMark, row, col))) {
                    validMove.add(new OthelloMove(currentMark, row, col));
                }
            }
        }
        return validMove;
    }

    /**
     * This function is used to check if the move made is valid or not.
     *
     * @param move The move that needs to be checked for its validity.
     *
     * @return true if the move is valid, false otherwise.
     * @ensures if the field is not empty or not a valid field on the board then return false.
     * @ensures for each direction check if the adjacent field is not empty and is occupied by the opponent.
     * @ensures check if the adjacent field in the opposite direction is not empty and is occupied by the current player.
     */
    @Override
    public boolean isValidMove(Move move) {
        OthelloMove othelloMove = (OthelloMove) move;
        int row = othelloMove.getRow();
        int col = othelloMove.getCol();
        Mark currentPlayer = othelloMove.getMark();
        Mark opponent;
        if(currentPlayer.equals(Mark.XX)){
            opponent = Mark.OO;
        } else{
            opponent = Mark.XX;
        }
        // check if there are any opponent's pieces in the 8 directions
        // dirX and dirY are examples of arrays that contain the direction changes in the x and y axis, respectively.
        // The values in the arrays would be the changes in x and y coordinates for each direction
        // (e.g. [-1, -1] for the top-left direction, [0, 1] for the right direction, etc.).
        if (board.isField(row, col) && board.isEmptyField(row, col)) {
            for (int i = 0; i < dirX.length; i++) {
                int x = row + dirX[i];
                int y = col + dirY[i];
                if (board.isField(x, y) && board.getField(x, y).equals(Mark.EMPTY)) {
                    continue;
                }
                if (board.isField(x, y) && board.getField(x, y).equals(opponent)) {
                    // check if there is a current player's piece in the opposite direction
                    while (board.isField(x, y) && board.getField(x, y).equals(opponent)) {
                        x += dirX[i];
                        y += dirY[i];
                        if (board.isField(x, y) && board.getField(x, y).equals(currentPlayer)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Executes a move on the board. If the move is not valid, nothing happens.
     *
     * @param move the move to be executed
     *
     * @ensures if isValidMove(move) is true, the move is executed on the board
     * @ensures if isValidMove(move) is false, the board remains unchanged
     * @ensures the move is made on the board and all the necessary flips are done
     */
    @Override
    public void doMove(Move move) {
        if ((move.getRow() * 8 + move.getCol() == 64)) {
            if (getMark().equals(Mark.XX)) {
                turnIndex = 1;
            } else {
                turnIndex = 0;
            }
        } else {
            OthelloMove othelloMove = (OthelloMove) move;
            this.board.setField(othelloMove.getRow(), othelloMove.getCol(), othelloMove.getMark());
            flip(othelloMove);
            //change index of player after valid move is done
            if (turnIndex == 0) {
                turnIndex = 1;
            } else {
                turnIndex = 0;
            }
        }
    }

    // The flip() is used to flip the opponent's pieces in the Othello game board.
    // The method iterates over the 8 possible directions (up, down, left, right, and the 4 diagonal directions)
    // Example of direction: when i = 0 => dirX[-1] and dirY[-1] the direction is up - left - diagonal.
    private void flip(OthelloMove othelloMove) {
        Mark currentMark = othelloMove.getMark();
        Mark opponentMark = (currentMark == Mark.XX) ? Mark.OO : Mark.XX;
        for (int i = 0; i < board.DIM; i++) {
            int row = othelloMove.getRow() + dirX[i];
            int col = othelloMove.getCol() + dirY[i];
            boolean found = false;

            // Check if there are any opponent's pieces in the current direction by checking the field at the current direction's index, and if it is equal to the opponent's mark.
            // If there are opponent's pieces in that direction, it then checks if there is a current player's piece in the opposite direction by iterating in that direction until it reaches a different mark or an invalid field.
            while (board.isField(row, col) && board.getField(row, col) == opponentMark) {
                row = row + dirX[i];
                col = col + dirY[i];
                found = true;
            }
            // If a current player's piece is found, it starts flipping the opponent's pieces in that direction by setting the field at the current index to the current player's mark.
            if (found && board.isField(row, col) && board.getField(row, col) == currentMark) {
                row = othelloMove.getRow() + dirX[i];
                col = othelloMove.getCol() + dirY[i];
                while (board.getField(row, col) == opponentMark) {
                    board.setField(row, col, currentMark);
                    row += dirX[i];
                    col += dirY[i];
                }
            }
        }
    }

    /**
     * Count the number of pieces flipped when doMove.
     * @param othelloMove a valid move that do doMove.
     * @return return the number of pieces flipped.
     */
    public int countPiecesFlipped(OthelloMove othelloMove) {
        Mark currentMark = othelloMove.getMark();
        Mark opponentMark = (currentMark == Mark.XX) ? Mark.OO : Mark.XX;
        int count = 0;
        for (int i = 0; i < board.DIM; i++) {
            int row = othelloMove.getRow() + dirX[i];
            int col = othelloMove.getCol() + dirY[i];
            boolean found = false;

            while (board.isField(row, col) && board.getField(row, col) == opponentMark) {
                row = row + dirX[i];
                col = col + dirY[i];
                count++;
                found = true;
            }

            if (found && board.isField(row, col) && board.getField(row, col) == currentMark) {
                row = othelloMove.getRow() + dirX[i];
                col = othelloMove.getCol() + dirY[i];
                while (board.getField(row, col) == opponentMark) {
                    count--;
                    row += dirX[i];
                    col += dirY[i];
                }
            }
        }
        return count;
    }


    /**
     * Return a string representation of the Othello game state.
     * @return a string representation of the Othello game state, including the current board
     */
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(board);
        stringBuffer.append("\n");
        stringBuffer.append(getTurn());
        return stringBuffer.toString();
    }
}