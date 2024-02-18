import Othello.ai.ComputerPlayer;
import Othello.ai.NaiveStrategy;
import Othello.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;
    private OthelloGame othelloGame;


    @BeforeEach
    public void setUp() {
        board = new Board();
        this.othelloGame = new OthelloGame(player1, player2);

    }

    @Test
    public void testIndex() {
        int index = 0;
        for (int i = 0; i < Board.DIM; i++) {
            for (int j = 0; j < Board.DIM; j++) {
                assertEquals(index, board.index(i, j));
                index += 1;
            }
        }
    }

    @Test
    public void testIsFieldIndex() {
        assertFalse(board.isField(-1));
        assertTrue(board.isField(0));
        assertTrue(board.isField(Board.DIM * Board.DIM - 1));
        assertFalse(board.isField(Board.DIM * Board.DIM));
    }

    @Test
    public void testIsFieldRowCol() {
        assertFalse(board.isField(8, 0));
        assertFalse(board.isField(0, 8));
        assertTrue(board.isField(0, 0));
        assertTrue(board.isField(7, 7));
        assertFalse(board.isField(-1, 0));
        assertFalse(board.isField(0, -1));
    }

    @Test
    public void testSetAndGetFieldIndex() {
        board.setField(0, Mark.XX);
        assertEquals(Mark.XX, board.getField(0));
        assertEquals(Mark.EMPTY, board.getField(1));
    }

    @Test
    public void testSetAndGetFieldRowCol() {
        board.setField(0, 0, Mark.XX);
        assertEquals(Mark.XX, board.getField(0, 0));
        assertEquals(Mark.EMPTY, board.getField(0, 1));
        assertEquals(Mark.EMPTY, board.getField(1, 0));
        assertEquals(Mark.EMPTY, board.getField(1, 1));
    }

    @Test
    public void testSetup() {
        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            if (i == 27 || i == 28 || i == 35 || i == 36) {
                assertNotEquals(Mark.EMPTY, board.getField(i));
            } else {
                assertEquals(Mark.EMPTY, board.getField(i));
            }
        }
    }

    @Test
    public void testReset() {
        board.setField(0, Mark.XX);
        board.setField(Board.DIM * Board.DIM - 1, Mark.OO);
        board.reset();
        assertEquals(Mark.EMPTY, board.getField(0));
        assertEquals(Mark.EMPTY, board.getField(Board.DIM * Board.DIM - 1));
    }

    @Test
    public void testDeepCopy() {
        board.setField(0, Mark.XX);
        board.setField(Board.DIM * Board.DIM - 1, Mark.OO);
        Board deepCopyBoard = board.deepCopy();

        // First test if all the fields are the same
        for (int i = 0; i < Board.DIM * Board.DIM; i++) {
            assertEquals(board.getField(i), deepCopyBoard.getField(i));
        }

        // Check if a field in the deepcopied board the original remains the same
        deepCopyBoard.setField(0, Mark.OO);

        assertEquals(Mark.XX, board.getField(0));
        assertEquals(Mark.OO, deepCopyBoard.getField(0));
    }
    @Test
    public void testIsEmptyFieldIndex() {
        board.setField(0, Mark.XX);
        assertFalse(board.isEmptyField(0));
        assertTrue(board.isEmptyField(1));
    }

    @Test
    public void testIsEmptyFieldRowCol() {
        board.setField(0, 0, Mark.XX);
        assertFalse(board.isEmptyField(0, 0));
        assertTrue(board.isEmptyField(0, 1));
        assertTrue(board.isEmptyField(1, 0));
    }

    @Test
    public void testIsFull() {
        for (int i = 0; i < Board.DIM * Board.DIM - 1; i++) {
            board.setField(i, Mark.XX);
        }
        assertFalse(board.isFull());

        board.setField(Board.DIM * Board.DIM - 1, Mark.XX);
        assertTrue(board.isFull());
    }

    @Test
    public void testGameOver() {
        // Setup test board with custom board
        // Check if game over with full mark on the board.
        for (int i = 0; i < board.DIM * Board.DIM; i++) {
            board.setField(i, Mark.XX);
        }
        assertTrue(board.gameOver());
    }
    @Test
    public void testRandomGame() {
        // create 2 computers to play with random moves
        othelloGame = new OthelloGame(new ComputerPlayer( new NaiveStrategy()), new ComputerPlayer( new NaiveStrategy()));

        while (!othelloGame.isGameover()) {
            AbstractPlayer abstractPlayer = (AbstractPlayer) othelloGame.getTurn();

            Move currentMove = abstractPlayer.determineMove(othelloGame);
            othelloGame.isValidMove(currentMove);
            // check if the currentMove is valid move
            assertTrue(othelloGame.isValidMove(currentMove));

            othelloGame.doMove(currentMove);
        }
        // Check if game over when 2 computers play
        assertTrue(othelloGame.isGameover());
    }

    @Test
    public void testGetMark() {
        othelloGame.turnIndex = 0;
        assertEquals(othelloGame.getMark(), Mark.XX);
    }
    @Test
    public void testChangeMark() {
        othelloGame.turnIndex = 0;
        assertEquals(othelloGame.changeMark(), Mark.OO);
    }
    @Test
    public void testGetWinner() {
        for (int i = 0; i < board.DIM * Board.DIM; i++) {
            board.setField(i, Mark.XX);
        }
            assertEquals(othelloGame.getWinner(),player1);
    }
    @Test
    public void testIsValidMove() {
        Move move = new OthelloMove(Mark.XX, 2, 3);
        assertTrue(othelloGame.isValidMove(move));
    }
}
