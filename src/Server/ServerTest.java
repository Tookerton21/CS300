package Server;

import Client.Client;
import Server.Server;

import javax.swing.JFrame;
/**
 * Created by niruiz3964 on 5/17/17.
 */
public class ServerTest {
    public static void main(String[] args){
        Server sally = new Server();
        sally.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sally.startRunning();

        //Client client = new Client();
        //client.startClient();

        //sally.Register("Robin", "Wright", "Robright", "6789");
        //sally.loadUsers();
        //sally.displayUsers();
    }
}
