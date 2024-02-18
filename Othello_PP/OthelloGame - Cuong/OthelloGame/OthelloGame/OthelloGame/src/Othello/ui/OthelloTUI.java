package Othello.ui;


import Othello.ai.ComputerPlayer;
import Othello.ai.NaiveStrategy;
import Othello.ai.SmartStrategy;
import Othello.model.*;

import java.util.*;

public class OthelloTUI {

    public static void main(String[] args) {
        OthelloTUI object = new OthelloTUI();
        object.run();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        String word = "YES";
        do {
            System.out.print("Enter the first player's name: ");
            String name1 = sc.nextLine();
            System.out.print("Enter the second player's name: ");
            String name2 = sc.nextLine();

            HumanPlayer humanPlayer1 = new HumanPlayer(name1, Mark.XX);
            HumanPlayer humanPlayer2 = new HumanPlayer(name2, Mark.OO);
            OthelloGame othelloGame = new OthelloGame(humanPlayer1, humanPlayer2);

            if (name1.equals("-N")) {
                ComputerPlayer computerPlayer = new ComputerPlayer(new NaiveStrategy());
                othelloGame = new OthelloGame(computerPlayer, humanPlayer2);
            } else if (name2.equals("-N")) {
                ComputerPlayer computerPlayer = new ComputerPlayer(new NaiveStrategy());
                othelloGame = new OthelloGame(humanPlayer1, computerPlayer);
            }else if (name1.equals("-S")) {
                ComputerPlayer computerPlayer = new ComputerPlayer(new SmartStrategy());
                othelloGame = new OthelloGame(computerPlayer, humanPlayer2);
            } else if (name2.equals("-S")) {
                ComputerPlayer computerPlayer = new ComputerPlayer(new SmartStrategy());
                othelloGame = new OthelloGame(humanPlayer1, computerPlayer);
            }

            if ( name1.equals("-N") && name2.equals("-N")) {
                ComputerPlayer computerPlayer1 = new ComputerPlayer(new NaiveStrategy());
                ComputerPlayer computerPlayer2 = new ComputerPlayer(new NaiveStrategy());
                othelloGame = new OthelloGame(computerPlayer1, computerPlayer2);

            } else if ( name1.equals("-S") && name2.equals("-S")) {
                ComputerPlayer computerPlayer1 = new ComputerPlayer(new SmartStrategy());
                ComputerPlayer computerPlayer2 = new ComputerPlayer(new SmartStrategy());
                othelloGame = new OthelloGame(computerPlayer1, computerPlayer2);

            } else if ( name1.equals("-N") && name2.equals("-S")) {
                ComputerPlayer computerPlayer1 = new ComputerPlayer(new NaiveStrategy());
                ComputerPlayer computerPlayer2 = new ComputerPlayer(new SmartStrategy());
                othelloGame = new OthelloGame(computerPlayer1, computerPlayer2);

            } else if ( name1.equals("-S") && name2.equals("-N")) {
                ComputerPlayer computerPlayer1 = new ComputerPlayer(new SmartStrategy());
                ComputerPlayer computerPlayer2 = new ComputerPlayer(new NaiveStrategy());
                othelloGame = new OthelloGame(computerPlayer1, computerPlayer2);
            }

            while (!othelloGame.isGameover()) {
                System.out.println(othelloGame);
                AbstractPlayer currentPlayer = (AbstractPlayer) othelloGame.getTurn();
                Move currentMove = currentPlayer.determineMove(othelloGame);
                if (othelloGame.isValidMove(currentMove)) {
                    othelloGame.doMove(currentMove);
                }
                System.out.println("ValidMoves: " + othelloGame.getValidMoves());
            }

            System.out.println("Winner is: " + othelloGame.getWinner());
            System.out.print("Do you want to play another game? If yes, type \"YES\". Otherwise, type \"NO\": ");
            word = sc.nextLine();
        } while (word.equals("YES"));
    }
}