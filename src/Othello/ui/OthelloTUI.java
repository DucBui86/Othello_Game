package Othello.ui;


import Othello.model.*;

import java.util.*;

public class OthelloTUI {
    public static void main(String[] args) {
        OthelloTUI object = new OthelloTUI();
        object.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String output = "YES";
        do {
            System.out.print("Input the first player's name: ");
            String name1 = scanner.nextLine();
            System.out.print("Input the second player's name: ");
            String name2 = scanner.nextLine();
            HumanPlayer humanPlayer1 = new HumanPlayer(name1, Mark.XX);
            HumanPlayer humanPlayer2 = new HumanPlayer(name2, Mark.OO);

            OthelloGame othelloGame = new OthelloGame(humanPlayer1, humanPlayer2);

//            if (name2.equals("-N")) {
//                ComputerPlayer computerPlayer = new ComputerPlayer(Mark.OO, new NaiveStrategy());
//                othelloGame = new OthelloGame(humanPlayer1, computerPlayer);
//            }
//            else if (name2.equals("-S")) {
//                ComputerPlayer computerPlayer = new ComputerPlayer(Mark.OO, new SmartStrategy());
//                othelloGame = new OthelloGame(humanPlayer1, computerPlayer);
//            }

            while (!othelloGame.isGameover()) {
                System.out.println(othelloGame.toString());
                AbstractPlayer currentPlayer = (AbstractPlayer) othelloGame.getTurn();
                othelloGame.doMove(currentPlayer.determineMove(othelloGame));
            }
            System.out.println("The winner is: " + othelloGame.getWinner());
            System.out.print("Do you want to play another game? If yes, enter \"YES\"; otherwise, enter \"NO\": ");
            output = scanner.nextLine();
        } while (output.equals("YES"));
    }
}