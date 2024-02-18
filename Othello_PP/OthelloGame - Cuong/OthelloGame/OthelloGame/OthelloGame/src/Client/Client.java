package Client;


import Othello.ai.ComputerPlayer;
import Othello.ai.NaiveStrategy;
import Othello.ai.SmartStrategy;
import Othello.model.*;
import Othello.ui.HumanPlayer;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client class
 */

public class Client implements Runnable {
    private static final String DESCRIPTION = "MONO";
    private ClientStatus status;
    private Socket socket;
    public BufferedReader input;
    public PrintWriter output;
    public String username;
    OthelloGame othelloGame;
    AbstractPlayer player;
    private boolean connected = false;


    /**
     * Client class representing a client connection
     * @throws IOException if there is an issue with socket creation
     */
    //@ensures socket == null;
    //@ensures status == ClientStatus.START;
    public Client() throws IOException {
           this.socket = null;
           this.status = ClientStatus.START;
    }

    /**
     * Set status for client.
     * @param clientStatus
     */
    public void setStatus(ClientStatus clientStatus) {
        this.status = clientStatus;
    }

    /**
     * Return true if connect to server with IP address and port, otherwise return false.
     * Start thread, read and write message via socket.
     * @param address IP address
     * @param port port number
     * @return return true if connection is successful, otherwise return false.
     */
    //@requires address != null && 0 <= port && port <= 65536;
    //@ensures \result == true <==> socket.getInetAddress().equals(address) && socket.getLocalPort() == port;
    public boolean connect(InetAddress address, int port) {
        try {
            this.socket = new Socket(address, port);
            System.out.println("[CLIENT] connect to " + address);

            this.output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread thread = new Thread(this);
            thread.start();
            return true;
        } catch (IOException e) {
            System.out.println("Could not be connected!!!");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: port number is invalid");
            return false;
        }
    }

    /**
     * Close the socket.
     * change the status connection to false.
     */
    //@ensures connected == false && socket.isClosed() == true;
    public void close() {
        try {
            socket.close();
            this.connected = false;
        } catch (IOException e) {
            System.out.println("Error IO");
        }
    }

    /**
     * Run method of thread. Send command to HELLO to server and change the status of client.
     * If socket is connected. Read the message from the server and handle the message.
     */
    /*@
        requires connected == true && input != null;
        ensures status == ClientStatus.HELLO_WAITING;
    */
    @Override
    public void run() {
        sendCommand(Command.HELLO, DESCRIPTION);
        setStatus(ClientStatus.HELLO_WAITING);
        this.connected = true;
        while (connected) {
            try {
                String message = input.readLine();
                if (message == null) {
                    continue;
                }
                handleCommand(message);
            } catch (IOException e) {
                System.out.println("Error IO");
                this.close();
            }
        }
    }

    /**
     * Send username to server.
     * @param username username is input and send to server.
     * @return return true when username was sent to server, otherwise return false and close socket.
     */
    /*@
        requires username != null;
        requires output != null;
        ensures (\result == true) ==> (username != null && output != null);
        ensures (\result == false) ==> (\exists Exception e; true);
    */
    public boolean sendUsername(String username) {
        try {
            output.println(username);
            output.flush();
            return true;
        } catch (Exception e) {
            System.out.println("Error Ex");
            return false;
        }
    }

    /**
     * Send message to server
     * @param message message is sent to server.
     */
    //@requires message != null;
    public synchronized void sendMessage(String message) {
        output.println(message);
    }

    /**
     * Send command to server
     * @param command command include: HELLO, ALREADYLOGGEDIN, LIST, QUEUE, NEWGAME, MOVE, GAMEOVER, QUIT, ERROR.
     * @param arguments arguments are sent by server
     */
    public synchronized void sendCommand(String command, String... arguments) {
        String message = command;
        for (String agr : arguments) {
            message += "~" + agr;
        }
        System.out.println("Send command: " + message);
        this.sendMessage(message);
    }
    /**
     * If client at hello awaiting status, enter your username and send username to server.
     * Change status to login a waiting after handle to ready for login.
     */
    private synchronized void handleHello() {
        if (status.equals(ClientStatus.HELLO_WAITING)) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Welcome to Othello game, Please enter your username: ");
            username = sc.nextLine();
            if (username.equals("")) {
                System.out.println("Error message!!! Please enter your username: ");
                username = sc.nextLine();
            }
            sendUsername(username);
            this.getUsernameAndLogin();
        }
    }

    /**
     * Receive username and change status of login state to login awaiting.
     */
    //@ensures status == ClientStatus.LOGIN_WAITING;
    private synchronized void getUsernameAndLogin() {
        sendCommand(Command.LOGIN, username);
        setStatus(ClientStatus.LOGIN_WAITING);
    }

    /**
     * Handle already logged in.
     * Notice error and try login again.
     */
    private synchronized void handleAlreadyLoggedIn() {
        System.out.println("ERROR username, try again!!!");
        this.getUsernameAndLogin();
    }

    /**
     * Handle loggin. Loggin to server.
     * Change status to queue and ready for practice requirement.
     */
    /*@
        requires status == ClientStatus.LOGIN_WAITING;
        ensures status == ClientStatus.QUEUE;
    */
    private synchronized void handleLogin() {
        if (status.equals(ClientStatus.LOGIN_WAITING)) {
            setStatus(ClientStatus.QUEUE);
            this.UserRequired();
        }
    }

    /**
     * Start new game with human player or AI.
     * @param arg receive command "new game" from server to start new game.
     */
    /*@
        requires status == ClientStatus.NEWGAME_WAITING;
        requires System.in != null;
    */
    private synchronized void startNewGame(String[] arg) {
        if (status.equals(ClientStatus.NEWGAME_WAITING)) {
            System.out.println("Do you want to use Smart AI or Native AI to play? Type \"-S\" if you want Smart AI and \"-N\" if you want Native AI, otherwise \"NO\" ");
            Scanner sc = new Scanner(System.in);
            String AI = sc.nextLine().toUpperCase();

            if (AI.equals("-S")) {
                player = new ComputerPlayer(new SmartStrategy());
            } else if (AI.equals("-N")) {
                player = new ComputerPlayer(new NaiveStrategy());
            } else {
                player = new HumanPlayer(username, null);
            }
            boolean firstPlayer = arg[1].equals(username);
            if (firstPlayer) {
                System.out.println("Start game with " + arg[1]);
                player.setMark(Mark.XX);
                AbstractPlayer Player2 = new HumanPlayer(arg[2], Mark.OO);
                this.othelloGame = new OthelloGame(player, Player2);

            } else {
                System.out.println("Start game with " + arg[2]);
                AbstractPlayer Player1 = new HumanPlayer(arg[1], Mark.XX);
                player.setMark(Mark.OO);
                this.othelloGame = new OthelloGame(Player1, player);
            }
            System.out.println(othelloGame);

            if (othelloGame.getTurn() == player) {
                makeMove();
                setStatus(ClientStatus.MOVE_WAITING);
            } else {
                System.out.println("Waiting the opponent player do move!!!");
                setStatus(ClientStatus.MOVE_WAITING);
            }
        }
    }

    /**
     * makeMove and send this index move to server
     * change the client status
     */
    /*@
        requires status == ClientStatus.MOVE_WAITING;
        post othelloGame.getValidMoves().size() == 0 ==> status == ClientStatus.MOVE_WAITING;
    */
    private synchronized void makeMove() {
        if (othelloGame.getValidMoves().size() == 0) {
            sendCommand(Command.MOVE, Integer.toString(64));
        } else {
            Move move = player.determineMove(othelloGame);
            sendCommand(Command.MOVE, Integer.toString(move.getRow() * 8 + move.getCol()));
        }
            setStatus(ClientStatus.MOVE_WAITING);
    }

    /**
     * Handle move, receive index move from server and doMove.
     * Checks if it is player's turn -> makeMove, otherwise wait the opponent player do move.
     * @param arg receive command from server.
     */
    private synchronized void handleMove(String[] arg) {
        if (status.equals(ClientStatus.MOVE_WAITING)) {
            int index = Integer.parseInt(arg[1]);
            int row = index / 8;
            int col = index % 8;
            othelloGame.doMove(new OthelloMove(othelloGame.getMark(),row,col));
            System.out.println(othelloGame);
            if (othelloGame.isGameover()) {
                System.out.println("GameOver!!!");
            }
            if (othelloGame.getTurn() == player) {
               makeMove();
            } else {
               System.out.println("Waiting the opponent player do move");
               setStatus(ClientStatus.MOVE_WAITING);
            }
        }
    }

    /**
     * Handle game over and notice who is winner and lost or no winner.
     * @param arg receive command from server.
     */
    private synchronized void handleGameOver(String[] arg) {
        if (arg[1].equals("DRAW")) {
            System.out.println("DRAW. No winner!!!");
        } else {
            String playerGame = "Player: " + arg[2] + " WinWin.";
            switch (arg[1]) {
                case GameOver.VICTORY:
                    if (arg[2].equals(username)) {
                        System.out.println("WinWin!!! " + arg[2]);
                    } else {
                        System.out.println("Lost!!! \n" + playerGame);
                    }
                    break;
                case GameOver.DISCONNECT:
                    System.out.println("DISCONNECT: " + arg[2]);
                    break;
                default:
                    System.out.println("ERROR ERROR");
                    break;
            }
            setStatus(ClientStatus.NEWGAME_WAITING);
            this.UserRequired();
        }
    }

    /**
     * HandleList prints active players on server.
     * @param arg receive list online players from server.
     */
    private synchronized void handleList(String[] arg) {
        System.out.println("Active players list: ");
        for (int i = 1; i < arg.length; i++) {
            System.out.println(arg[i]);
        }
        this.UserRequired();
    }

    /**
     * Quit game and closes the server
     */
    private void handleQuit() {
        System.out.println("Quit game!!!");
        this.close();
    }

    /**
     * HandleError notice error that server sent to client.
     * If error "MOVE", player makeMove again.
     * @param arg receive command "ERROR" from server
     */
    private synchronized void handleError(String[] arg) {
        System.out.println("Error: " + arg[1]);
        if (input.equals(arg[0])) {
            makeMove();
        }
    }

    /**
     * HandleCommand solves the command being received from the server.
     * @param command
     */
    private void handleCommand(String command) {
        String[] sp = command.split("~");
        switch (sp[0]) {
            case Command.HELLO:
                handleHello();
                break;
            case Command.LOGIN:
                handleLogin();
                break;
            case Command.NEWGAME:
                startNewGame(sp);
                break;
            case Command.MOVE:
                handleMove(sp);
                break;
            case Command.GAMEOVER:
                handleGameOver(sp);
                break;
            case Command.LIST:
                handleList(sp);
                break;
            case Command.QUIT:
                handleQuit();
                break;
            case Command.ALREADYLOGGEDIN:
                handleAlreadyLoggedIn();
                break;
            case Command.ERROR:
                handleError(sp);
                break;
            default:
                System.out.println("ERROR SYNTAX");
                break;
        }
    }

    /**
     * Receive the command from server and handle the requirements
     */
    private void UserRequired() {
        this.printMenu();
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().toUpperCase();

        boolean isCommandValid = false;
        while (!isCommandValid) {
            switch (input) {
                case Command.QUEUE:
                    sendCommand(Command.QUEUE);
                    setStatus(ClientStatus.NEWGAME_WAITING);
                    isCommandValid = true;
                    break;
                case Command.LIST:
                    sendCommand(Command.LIST);
                    isCommandValid = true;
                    break;
                case Command.QUIT:
                    sendCommand(Command.QUIT);
                    isCommandValid = true;
                    this.close();
                    break;
                default:
                    System.out.println("ERROR SYNTAX!!!");
                    sendCommand(Command.ERROR);
                    this.printMenu();
                    input = sc.nextLine().toUpperCase();
                    break;
            }
        }
    }

    /**
     * Print Menu.
     */
    private void printMenu() {
        System.out.println(String.format("%nCommands:%n"));
        System.out.println(String.format("- %s .................. Queue for a new game%n", Command.QUEUE));
        System.out.println(String.format("- %s ................... List all the active clients%n", Command.LIST));
        System.out.println(String.format("- %s ................... End connection with the other party %n", Command.QUIT));
        System.out.println("Enter your requirement: ");
    }
}
