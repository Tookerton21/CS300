package Client;

import javax.swing.JFrame;

/**
 * Created by niruiz3964 on 5/17/17.
 */
public class ClientTest {
    public static void main(String[] args) throws InterruptedException {
       // Login login = new Login();
        //Client client = login;
        //client.display();
        //niko = new Client("127.0.0.1");

        Login client = new Login();
//        client.startL();
        client.startClient();
    }
}
