package Server;

import java.net.Socket;
import java.util.Iterator;

/**
 * Created by niruiz3964 on 6/5/17.
 */
public class ServerOnline extends Thread {
    Server server;
    Socket socket;

    public ServerOnline(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    public void run(){

    }

    protected void stillOnline(){
        Iterator itr = server.onlineUsers.iterator();

        while(itr.hasNext()){

        }
    }
}
