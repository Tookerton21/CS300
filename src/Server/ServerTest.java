package Server;

import javax.swing.JFrame;
/**
 * Created by niruiz3964 on 5/17/17.
 * This is used to get the server class up and running. Will load the required classes that are needed to run.
 */
public class ServerTest {
    public static void main(String[] args){
        Server server = new Server();
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server.startRunning();
    }
}
