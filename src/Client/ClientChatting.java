package Client;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by niruiz3964 on 6/5/17.
 */
public class ClientChatting extends Thread {

    Socket socket;
    Client client;
    ObjectInputStream input;
    ObjectOutputStream output;
    boolean canRun;
    public ClientChatting (Socket socket, Client client){
        super("ClientChatting");
        this.socket = socket;
        this.client = client;
        this.input = client.objInp;
        this.output = client.objOut;
        canRun = true;
    }

    public void run(){
     //  while(true){
          // whileChatting();
       //}
    }

    public void whileChatting(){
        do{
            try{
                System.out.println(input.available());

                client.message = (String) input.readObject();
                String list[] = (String[]) input.readObject();

                client.showMessage(client.message); // Send the message to be sent
                client.chatWindow.updateOnline(list); //REcieved the new online users, call GUI to update

            }catch (ClassNotFoundException classNotFoundException){
                client.showMessage("\nError, wrong Object type");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException n){
                canRun = false;
                break;
            }
        }while(!client.message.equals("Server - End"));
    }
}
