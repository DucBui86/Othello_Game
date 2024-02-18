package Othello.ui;



import Othello.model.*;


import java.awt.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class HumanPlayer extends AbstractPlayer {
    public Mark mark;
    /**
     * Creates a new Player object.
     *
     * @param name name of user.
     */
    public HumanPlayer(String name, Mark mark) {
        super(name);
        this.mark = mark;
    }
    public HumanPlayer(String name) {
        super(name);
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    @Override
    public Move determineMove(Game game) {
        List<Move> legalMoves = game.getValidMoves();
        Random random = new Random();
        int i = random.nextInt(legalMoves.size());
        Move randomMove = legalMoves.get(i);
        int hint = randomMove.getRow() * 8 + randomMove.getCol();

        if (game.getValidMoves().size() == 0) {
            Move move = new OthelloMove(game.getMark(), 8, 0);
            return move;
        } else {
            while (true) {
                Board board = new Board();
                System.out.println("Enter a valid move (index) from 0 - 63:......!!! Do you want to use hint??? Press \"88\" ");
                Scanner sc = new Scanner(System.in);
                int index = -1;
                try {
                    index = sc.nextInt();
                    if (index == 88) {
                        System.out.println("Hint Hint: " + hint);
                    } else {
                        //convert index to row and col
                        int row = board.row(index);
                        int col = board.col(index);
                        Move move = new OthelloMove(this.mark, row, col);
                        if (game.isValidMove(move))
                            return move;
                        else
                            System.out.println("Move is not valid!!! Please enter again!!!\n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Wrong syntax!!! Enter a valid move again: ");
                }
            }
        }
    }
}
