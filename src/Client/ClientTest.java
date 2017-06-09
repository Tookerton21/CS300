package Client;

/**
 * Created by niruiz3964 on 5/17/17.
 * This will get the chat application for the user side up and running. It will load the
 * appropriate classes for the chat application.
 */
public class ClientTest {
    public static void main(String[] args) {
        Login client = new Login();
        client.startClient();
    }
}
