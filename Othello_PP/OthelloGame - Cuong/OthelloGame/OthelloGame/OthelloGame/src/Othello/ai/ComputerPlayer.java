package Othello.ai;


import Othello.model.*;

public class ComputerPlayer extends AbstractPlayer {
    private String name = "Computer";
    private Strategy strategy;

    public ComputerPlayer(Strategy strategy) {
        super(strategy.getName());
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }

    @Override
    public Move determineMove(Game game) {
        if (game.getValidMoves().size() == 0) {
            Move move = new OthelloMove(game.getMark(), 8, 0);
            return move;
        } else {
            return strategy.determineMove(game);
        }
    }
}
