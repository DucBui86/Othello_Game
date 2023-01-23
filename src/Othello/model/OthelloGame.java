package Othello.model;

import java.util.ArrayList;
import java.util.List;


public class OthelloGame implements Game {
    private  Board board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;
    private int turnIndex;
    int[] dirX = {-1, -1, -1, 0, 1, 1, 1, 0};
    int[] dirY = {-1, 0, 1, 1, 1, 0, -1, -1};

    public OthelloGame(AbstractPlayer player1, AbstractPlayer player2 ) {
        board = new Board();
        this.player1 = player1;
        this.player2 = player2;
        this.turnIndex = 0;
    }

    @Override
    public boolean isGameover() {
        return board.gameOver();
    }

    @Override
    public Player getTurn() {
        if (turnIndex == 0) {
            return player1;
        } else {
            return player2;
        }
    }

    @Override
    public Player getWinner() {
        if (board.hasWinner()) {
            if (board.isWinner(Mark.XX)) {
                return player1;
            } else {
                return player2;
            }
        }
        return null;
    }

    @Override
    public List<Move> getValidMoves() {
        List<Move> validMove = new ArrayList<>();
        Mark currentMark = getTurn().getMark();
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

    @Override
    public boolean isValidMove(Move move) {
        OthelloMove othelloMove = (OthelloMove) move;
        int moveIndex = othelloMove.getRow() * board.DIM + othelloMove.getCol();
        Mark currentPlayer = othelloMove.getMark();
        Mark opponent = (currentPlayer == Mark.XX) ? Mark.OO : Mark.XX;

        if (!board.isField(moveIndex) || !board.isEmptyField(moveIndex)) {
            return false;
        }
        // check if there are any opponent's pieces in the 8 directions
        for (int i = 0; i < board.DIM; i++) {
            int x = board.row(moveIndex) + dirX[i];
            int y = board.col(moveIndex) + dirY[i];
            int index = board.index(x,y);

            if (board.isField(index) && board.getField(index) == opponent) {
                // check if there is a current player's piece in the opposite direction
                while (board.isField(index) && board.getField(index) == opponent) {
                    x += dirX[i];
                    y += dirY[i];
                    index = board.index(x,y);
                }
                if (board.isField(index) && board.getField(index) == currentPlayer) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void doMove(Move move) {
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
    private void flip(OthelloMove move) {
        Mark currentMark = move.getMark();
        Mark opponentMark = (currentMark == Mark.XX) ? Mark.OO : Mark.XX;
        for (int i = 0; i < board.DIM; i++) {
            int row = move.getRow() + dirX[i];
            int col = move.getCol() + dirY[i];
            int index = board.index(row, col);

            boolean found = false;

            while (board.isField(index) && board.getField(index) == opponentMark) {
                row += dirX[i];
                col += dirY[i];
                index = board.index(row, col);
                found = true;
            }

            if (found && board.isField(index) && board.getField(index) == currentMark) {
                row = move.getRow() + dirX[i];
                col = move.getCol() + dirY[i];
                index = board.index(row, col);
                while (board.getField(index) == opponentMark) {
                    board.setField(row, col, currentMark);
                    row += dirX[i];
                    col += dirY[i];
                    index = board.index(row, col);
                }
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(board);
        stringBuffer.append("\n");
        stringBuffer.append(getTurn());
        return stringBuffer.toString();
    }
}