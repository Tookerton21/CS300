package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * Created by niruiz3964 on 6/5/17.
 */
public class ClientConnection extends Thread {
    Socket socket;
    Client client;
    boolean canRun;
    ObjectOutputStream output;
    ObjectInputStream input;

    public ClientConnection(Socket socket, Client client){
        this.socket = socket;
        this.client = client;
        this.canRun = true;

    }

    //public void run(){
        //try {
          //  this.output = new ObjectOutputStream(socket.getOutputStream());
          //  this.input = new ObjectInputStream(socket.getInputStream());
        //} catch (IOException e) {
          //  System.out.println("Error in setting up Streams for thread");
           // e.printStackTrace();
       // }

      //  while(true){
      //      updateUserList();
      //  }
    //}
    public void updateUserList(){
        try {

            client.userList = (String[]) client.objInp.readObject();
            client.onlineList = (String[]) client.objInp.readObject();
            int size = client.userList.length;


            for(int i=0; i<size; ++i){
                client.chatWindow.adduser(client.userList[i], client.onlineList , client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
