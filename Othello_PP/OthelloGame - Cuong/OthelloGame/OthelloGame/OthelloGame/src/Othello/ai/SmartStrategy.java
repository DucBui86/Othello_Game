package Othello.ai;


import Othello.model.*;
import java.util.List;


public class SmartStrategy implements Strategy {
    private String name = "Stu-art";

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Move determineMove(Game game) {
        if (game.getValidMoves().size() == 0) {
            Move move = new OthelloMove(game.getMark(), 8, 0);
            return move;
        } else {
            OthelloGame othelloGame = (OthelloGame) game;
            List<Move> legalMoves = game.getValidMoves();
            System.out.println(legalMoves);
            Move move1 = null;
            int maxFlips = 0;
            for (Move validMove : legalMoves) {
                OthelloMove othelloMove = (OthelloMove) validMove;
                int flip = othelloGame.countPiecesFlipped(othelloMove);
                if (flip >= maxFlips) {
                    move1 = validMove;
                    maxFlips = flip;
                }
            }
            System.out.println("Stu-art do move at: " + move1);
            return move1;
        }
    }
}