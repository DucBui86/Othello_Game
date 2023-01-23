package Othello.ui;



import Othello.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HumanPlayer extends AbstractPlayer {
    private Mark mark;
    /**
     * Creates a new Player object.
     *
     * @param name
     */
    public HumanPlayer(String name, Mark mark) {
        super(name);
        this.mark = mark;
    }
    public Mark getMark() {
        return this.mark;
    }

    @Override
    public Move determineMove(Game game) {
        while(true) {
            Board board = new Board();
            System.out.print("Enter a valid move (index): ");
            Scanner sc = new Scanner(System.in);
            int index = sc.nextInt();
            //convert index to row and col
            int row = board.row(index);
            int col = board.col(index);
            Move move = new OthelloMove(this.mark, row, col);
            if (game.isValidMove(move))
                return move;
            else
                System.out.println("Move is not valid!!! Please enter again!!!\n");
        }
    }
}
