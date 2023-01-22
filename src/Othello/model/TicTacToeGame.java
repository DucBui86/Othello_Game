package Othello.model;

import java.util.ArrayList;
import java.util.List;


public class TicTacToeGame implements Game {
    private  Board board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;
    private int turnIndex;

    public TicTacToeGame(AbstractPlayer player1, AbstractPlayer player2 ) {
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
    public List<Move> getValidMoves() { //consider all empty fields of the board
        List<Move> moves = new ArrayList<>();
        Mark currentMark;
        if (turnIndex == 0) {
            currentMark = Mark.XX;
        } else {
            currentMark = Mark.OO;
        }

        for (int i = 0; i < board.DIM; i++) {
            for (int j = 0; j < board.DIM; j++) {
                Move currentMove = new TicTacToeMove(currentMark, i, j);
                if (board.isEmptyField(i, j)) {
                    moves.add(currentMove);
                }
            }
        }
        return moves;
    }

    @Override
    public boolean isValidMove(Move move) {
        if (!(move instanceof TicTacToeMove)) {
            return false;
        }
        TicTacToeMove ticTacToeMove = (TicTacToeMove) move;

        if (board.isField(ticTacToeMove.getRow(), ticTacToeMove.getCol())
                && board.isEmptyField(ticTacToeMove.getRow(), ticTacToeMove.getCol())) {
            return true;
        }
        return false;
    }

    @Override
    public void doMove(Move move) {
        TicTacToeMove ticTacToeMove = (TicTacToeMove) move;
        this.board.setField(ticTacToeMove.getRow(), ticTacToeMove.getCol(), ticTacToeMove.getMark());

        //change index of player after valid move is done
        if (turnIndex == 0) {
            turnIndex = 1;
        } else {
            turnIndex = 0;
        }
    }

    @Override
    public Board getboard() {
        return this.board;
    }

    @Override
    public void changeBoard(Board board) {

    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(board);
        stringBuffer.append("\n");
        stringBuffer.append(getTurn());
        return stringBuffer.toString();
    }

    /**
     * Finds a winning move for the current player, or returns null if no such move exists.
     *
     * @param game the current state of the game
     * @return a winning move for the current player, or null if no such move exists
     */
    public static Move findWinningMove(TicTacToeGame game) {
        // Check rows
        for (int row = 0; row < game.getboard().DIM; row++) {
            int count = 0;
            Move winningMove = null;
            for (int col = 0; col < game.getboard().DIM; col++) {
                if (game.getboard().getField(row, col) == game.getTurn().getMark()) {
                    count++;
                } else if (game.isValidMove(new TicTacToeMove(game.getTurn().getMark(), row, col))) {
                    winningMove = new TicTacToeMove(game.getTurn().getMark(), row, col);
                }
            }
            if (count == 2 && winningMove != null) {
                return winningMove;
            }
        }

        // Check columns
        for (int col = 0; col < game.getboard().DIM; col++) {
            int count = 0;
            Move winningMove = null;
            for (int row = 0; row < game.getboard().DIM; row++) {
                if (game.getboard().getField(row, col) == game.getTurn().getMark()) {
                    count++;
                } else if (game.isValidMove(new TicTacToeMove(game.getTurn().getMark(), row, col))) {
                    winningMove = new TicTacToeMove(game.getTurn().getMark(), row, col);
                }
            }
            if (count == 2 && winningMove != null) {
                return winningMove;
            }
        }
        // Check diagonal
        int count = 0;
        Move winningMove = null;
        for (int i = 0; i < game.getboard().DIM; i++) {
            if (game.getboard().getField(i, i) == game.getTurn().getMark()) {
                count++;
            } else if (game.isValidMove(new TicTacToeMove(game.getTurn().getMark(), i, i))) {
                winningMove = new TicTacToeMove(game.getTurn().getMark(), i, i);
            }
        }
        if (count == 2 && winningMove != null) {
            return winningMove;
        }

        // Check other diagonal
        count = 0;
        winningMove = null;
        for (int i = 0; i < game.getboard().DIM; i++) {
            if (game.getboard().getField(i, i) == game.getTurn().getMark()) {
                count++;
            } else if (game.isValidMove(new TicTacToeMove(game.getTurn().getMark(), i, i))) {
                winningMove = new TicTacToeMove(game.getTurn().getMark(), i, i);
            }
        }
        if (count == 2 && winningMove != null) {
            return winningMove;
        }
        return null;
    }
}