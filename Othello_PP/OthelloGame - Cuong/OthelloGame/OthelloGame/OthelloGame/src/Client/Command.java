package Client;

public interface Command {
    String HELLO = "HELLO";
    String LOGIN = "LOGIN";
    String ALREADYLOGGEDIN = "ALREADYLOGGEDIN";
    String LIST = "LIST";
    String QUEUE = "QUEUE";
    String NEWGAME = "NEWGAME";
    String MOVE = "MOVE";
    String GAMEOVER = "GAMEOVER";
    String QUIT = "QUIT";
    String ERROR = "ERROR";
}
