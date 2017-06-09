package Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * Created by niruiz3964 on 6/5/17.
 */
public class ServerConnection extends Thread {
    private Socket socket;
    protected Server server;
    protected ObjectInputStream input;
    protected ObjectOutputStream output;
    private String username;
    private String password;
    protected ServerUpdateList updateList;
    protected ServerMessage sm;
    boolean shouldRun;

    public ServerConnection(Socket socket, Server server){
        super("ServerConnectionThread");
        shouldRun = true;
        this.socket = socket;
        this.server = server;
        this.username = null;
        this.password = null;

    }

    public void sendMessageToClient(String text){
        try {
            output.writeObject(text);
            output.writeObject(server.getOnlineUsers());
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStringtoAllClients(String text){
        for(int index = 0; index<server.connections.size(); ++index){
            ServerConnection sc = server.connections.get(index);
            sc.sendMessageToClient(text);
        }
    }

    protected void sendPrivMessage(String toSend, String message){
        //Look for the user
        boolean online = false;
        for(int index = 0; index<server.connections.size(); ++index){

            ServerConnection sc = server.connections.get(index);

            if(sc.username.equals(toSend)){
                sc.sendMessageToClient("\n\t....PRIVATE MESSAGE....\n"+"From: "+ username+": "+
                        message+"\n\t....END OF MESSAGE.....");
                online = true;
            }
        }
        if(online == false)
            sendMessageToClient("Not Online");
    }

    public void run(){
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());

            //Validate the username and password for the client that wishes to connect
            //To the chat application
            String toDo = (String)input.readObject();
            String username = (String)input.readObject();
            String password = (String)input.readObject();

            boolean res = false;

            //Save the user to the user database
            if(toDo.equals("REGISTER"))
                res = server.register(username,password, output);

            //Log The user into the Server
            else if(toDo.equals("LOGIN"))
                res = server.logInfo(username,password, output);

            if(res == true){
                this.username = username;
                this.password = password;
                server.onlineUsers.add(username);
                server.addUsers(output);
                sendStringtoAllClients(this.username+" Has Entered the chat....");
            }

            //Get the current users that are online

            while(shouldRun){

                String type = (String) input.readObject();
                if(type.equals("PUBLIC")) {
                    String textIn = (String) input.readObject();
                    sendStringtoAllClients(textIn);
                }
                else if(type.equals("PRIVATE")){
                    String toSend = (String) input.readObject();
                    String message = (String) input.readObject();
                    sendPrivMessage(toSend, message);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally{

            server.removeOnline(username);
            server.removeConnections(this);
            sendStringtoAllClients(username+" Has left the room");
            try {
                output.close();
                input.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


}