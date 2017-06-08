package Server;

import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * Created by niruiz3964 on 6/6/17.
 */
public class ServerUpdateList extends Thread {

    Socket socket;
    ServerConnection serverConnection;
    Server server;
    ObjectOutputStream output;

    public ServerUpdateList(Socket socket, Server server, ServerConnection serverConnection){
        super("SERVERUPDATELIST_THREAD");
        this.socket = socket;
        this.serverConnection = serverConnection;
        this.server = server;
    }

    public void start(){
       // while(true){
            //server.addUsers(output);
        //}
    }

    public void updateList(){
        while(true) {
            server.addUsers(output);

        }
    }


}
