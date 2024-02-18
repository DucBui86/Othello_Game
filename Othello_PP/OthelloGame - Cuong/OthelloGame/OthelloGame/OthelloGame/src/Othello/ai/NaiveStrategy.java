package Othello.ai;

import Othello.model.*;

import java.util.List;
import java.util.Random;


public class NaiveStrategy implements Strategy {
    private String name = "Navie";

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param game the current game
     * @return
     */
    @Override
    public Move determineMove(Game game) {
        if (game.getValidMoves().size() == 0) {
            Move move = new OthelloMove(game.getMark(), 8, 0);
            return move;
        } else {
            List<Move> legalMoves = game.getValidMoves();
            Random random = new Random();
            int index = random.nextInt(legalMoves.size());
            Move randomMove = legalMoves.get(index);
            System.out.println("Naive do move at: " + randomMove);
            return randomMove;
        }
    }
}
