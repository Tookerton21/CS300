package Server;

import java.io.IOException;

/**
 * Created by niruiz3964 on 6/6/17.
 */
public class ServerMessage extends Thread {
    ServerConnection sc;

    public ServerMessage(ServerConnection sc){
        super("SERVERMESSAGE_THREAD");
        this.sc = sc;
    }

    public void run(){
     //   while(true){
            listenMessage();
       // }
    }

    protected void listenMessage(){
        //while(true){
            String textIn = null;
            try {
                textIn = (String)sc.input.readObject();
                //if(textIn != null)

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            sc.sendStringtoAllClients(textIn);
        //}
    }
}
