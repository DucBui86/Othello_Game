package Client;

import Othello.model.Board;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientTUI implements Runnable {
    private final Board board;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Client client = new Client();
        boolean isConnected = false;
        while (!isConnected) {
            try {
                System.out.println("\n Enter port number (from 0 to 65536): ");
                int portServer = Integer.parseInt(in.readLine());
                System.out.println("\n Enter server address: ");
                String serverAddress = in.readLine();
                if (!client.connect(InetAddress.getByName(serverAddress), portServer)) {
                    System.out.println("Error: failed to connect");
                    System.out.println("Let's try again.");
                }
                isConnected = true;
            } catch (UnknownHostException e) {
                System.out.println("Error: server is invalid");
            } catch (NumberFormatException e) {
                System.out.println("Error: port number is invalid");
            }
        }
    }

    public ClientTUI() {
        this.board = new Board();
        new Thread(this).start();
    }
    @Override
    public void run() {}
}
