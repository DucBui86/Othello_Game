package Othello.ai;


import Othello.model.Game;
import Othello.model.Move;

public interface Strategy {
    /**
     * Returns the name of the strategy.
     * @return the name of the strategy
     */
    String getName();


    /**
     * Return a next legal move
     * @param game the current game
     * @return a next legal move
     * @ensures \result != null && game.isLegalMove(\result)
     */
    Move determineMove(Game game);

}
