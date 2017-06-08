package Server;

import Client.MainChatGUI.MainChatGUI;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by niruiz3964 on 5/17/17.
 */
public class Server extends JFrame{
    private JTextField userText;
    private JTextArea chatWindow;
   private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    private String fileName = "users.text";
    protected LinkedList<String[] > linkedList;
    protected ArrayList<ServerConnection> connections;
    protected LinkedList<String> onlineUsers;
    private BufferedReader buffInput;
    private PrintWriter pWrite;
    private String register;
    private String login;
    private MainChatGUI mainChatGUI;

    public Server(){


        super("Chit Chat Messenger");
        userText = new JTextField();
        userText.setEditable(false); //Dont allow to message to anyone without connection
        userText.addActionListener(
                        new ActionListener(){
                         public void actionPerformed(ActionEvent event){
                            // sendMessage(event.getActionCommand());
                             userText.setText("");
                         }
                        }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(300,150);
        setVisible(true);

        register = "register";
        login = "login";
        linkedList = new LinkedList<String[]>();
        connections = new ArrayList<ServerConnection>();
        onlineUsers = new LinkedList<>();
    }

    //Set up and run the server
    public void startRunning(){
        //try{

        try {
            server = new ServerSocket(6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadUsers();

            while(true){

                try{
//                    server = new ServerSocket(6666);
                    Socket socket = server.accept();
                   // ObjectOutputStream output;
                    //ObjectInputStream input;
                    //connection = socket;
                    //setupStreams(socket, output, input);
                    //String username = (String)input.readObject();
                    //String password = (String)input.readObject();
                    //logInfo(username, password);
                    ServerConnection sc = new ServerConnection(socket, this);
                    connections.add(sc);
                    sc.start();
                    //Connect and have conversation
                  //  waitForConnection();

                   // loginReg();
                   // addUsers();
                    //whileChatting();
              //  }catch(EOFException eofException){
                //    showMessage("\n Server ended the connection");
                //} catch (ClassNotFoundException e) {
                 //   e.printStackTrace();
                //} //catch (ClassNotFoundException e) {
                    //e.printStackTrace();
                //} finally{
                  //  closeCon();
                //}
            }catch (IOException ioException){
                    ioException.printStackTrace();
                }

        }//catch (IOException ioException){
           // ioException.printStackTrace();
      //  }
    }


    //Wait for connection, then display connection information
    /*private void waitForConnection() throws IOException{
        showMessage("Waiting for someone to connect...");
        connection = server.accept();
        new Thread(new WorkerRunnable(connection, "Multhread server")).start();
        //logInfo();

            //connection.close();
    }*/

    //get stream to send and recieve data
    private void setupStreams(Socket socket) throws IOException{
        //buffInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //pWrite = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
        //pWrite.flush();

        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();
        input = new ObjectInputStream(socket.getInputStream());
        showMessage("\n Streams are now setup! \n");
    }

    //During the chat conversation

   /* private void whileChatting() throws IOException{
        String message = "You are now connected! ";
        sendMessage(message);
        ableToType(true);
        do{
            try{
                message = (String) input.readObject();
                showMessage("\n"+message);
            }catch (ClassNotFoundException classNotFoundException){
                showMessage("Invalide user info");
            }
        }while(!message.equals("CLIENT - END"));
    }*/


    //Close Streams and sockets after done chatting
    private void closeCon(){
        showMessage("\n closing connections..");
        ableToType(false);
        try{
              pWrite.close();
              buffInput.close();
//            output.close();
  //          input.close();
            connection.close(); //closes the socket

        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    //send message to client
   /* private void sendMessage(String message){
        try{
            output.writeObject("Server - "+message );
            output.flush();
            showMessage("\nServer - "+message);
        }catch(IOException ioException){
            chatWindow.append("\n ERROR, Cant send message");
        }
    }*/

        //Updates chatWindow
    private void showMessage(final String text){
        //create a thread to update the GUI
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(text);
                    }
                }
        );
    }

    //Let user to type
    private void ableToType(final boolean tof){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        userText.setEditable(tof);
                    }
                }
        );
    }

    //Add a new user to the list
    public boolean register(String username, String password, ObjectOutputStream output) throws IOException {

        String user[] = {username, password};

        Iterator<String[]> itr = linkedList.iterator();
        while(itr.hasNext()){
            String data[] = itr.next();

            //User with the same username was found return false
            if(data[0].equals(username)) {
                output.writeObject(false);
                output.flush();
                return false;
            }
        }

        //No Match was found and is truely a new user
        linkedList.add(user);
        saveUsers(user);
        output.writeObject(true);
        output.flush();
        return true;
    }

    protected void loadUsers(){
        String userN;
        String passW;

        try {
            String fileName = "users.text";
            File inFile = new File(fileName);
            Scanner scanner = new Scanner(inFile);
           // scanner.useDelimiter("&");

            while(scanner.hasNextLine()) {
                userN = scanner.next();
                passW = scanner.next();
                String user[] = {userN, passW};
                linkedList.add(user);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveUsers(String user[]){
        File outFile = new File(fileName);
        try {
            FileWriter fWriter = new FileWriter(outFile, true);
            PrintWriter printWriter = new PrintWriter(fWriter);
            printWriter.print("\n"+user[0] + " " + user[1]);
            printWriter.close();
            fWriter.close();
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        Open a connection with the client to take the username and password
        to test against the users in the database of registered users. If their login info
        is correct then return true and if not found return false;
     */
    protected void addUsers(ObjectOutputStream output){
        try {
            //Read the user interface from the user
            int size = linkedList.size();
            String userList[] = new String[size];
            int pos = 0;

            //Add the users on the current userList to Clients userInterface
            Iterator<String[]> itr = linkedList.iterator();
            while(itr.hasNext()){
                String user[] = itr.next();
                userList[pos] = user[0];
                ++pos;
            }

            size = onlineUsers.size();
            String onlineU[] = new String[size];
            pos =0;

            Iterator<String> it = onlineUsers.iterator();
            while(it.hasNext()){
                onlineU[pos] = it.next();
                ++pos;
            }

            //Send updated gui back to Clien
           // output.writeObject((int) size);
           // output.writeObject("Update");
            output.writeObject(userList);
            output.writeObject(onlineU);
            output.flush();
           // Thread.sleep(4);

        } catch (IOException e) {
            e.printStackTrace();
      //  } catch (InterruptedException e) {
      //      e.printStackTrace();
        }
    }
    private void loginReg() throws ClassNotFoundException {
        try {
            //Get information with the client
            String logReg = (String) input.readObject();
            String username = (String) input.readObject();
            String password = (String) input.readObject();//buffInput.readLine();

            if(logReg.equals(register)){
                boolean res = register(username, password, output);
                output.writeObject(res);
                output.flush();
/*
                if(res == true) {
                    output.writeObject("true");
                    output.flush();
                }
                else{
                    pWrite.println("false");
                    pWrite.flush();
                }*/

            }

            if(logReg.equals(login)){
                //logInfo(username, password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected boolean logInfo(String username, String password, ObjectOutputStream output) throws IOException {

        boolean res = verifyUser(username, password);
        output.writeObject(res);
        output.flush();
        return res;
        /*
        if(res == true) {
            output.writeObject("true");
            output.flush();
            return true;
        }

        else{
            pWrite.println("false");
            pWrite.flush();
            return false;
        }
*/
    }

    private boolean verifyUser(String userN, String passW){
        Iterator<String[]> itr = linkedList.iterator();

        while(itr.hasNext()){
            String user[] = itr.next();

            if(user[0].equals(userN) && user[1].equals(passW)) {
                String users[] = getOnlineUsers();

                //Ensure that the user trying to login isnt corrently logged in.
                //If they are then return false
                for(int i=0; i<users.length; ++i){
                    if(users[i].equals(userN))
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    //Remove the user socket from the connection ArrayList
    //When they sign out or exit
    protected void removeConnections(ServerConnection toRem){
       for(int i=0; i<connections.size(); ++i){
           if(connections.get(i).equals(toRem))
               connections.remove(i);
       }
    }

    //Remove the user from the online list from the Linked list
    //When they sign out or exit
    protected void removeOnline(String user){
        Iterator itr = onlineUsers.iterator();
        while(itr.hasNext()){
            if(itr.next().equals(user))
                itr.remove();
        }
    }
    public void displayUsers(){
        Iterator<String[]> itr = linkedList.iterator();
        while(itr.hasNext()){
          //  for(int i=0; i<4; ++i)
                System.out.println(Arrays.toString(itr.next()));
        }
    }

    public String[] getOnlineUsers(){
        int size = onlineUsers.size();
        String onlineU[] = new String[size];
        int pos =0;

        Iterator<String> it = onlineUsers.iterator();
        while(it.hasNext()){
            onlineU[pos] = it.next();
            ++pos;
        }
        return onlineU;
    }

  //  public class WorkerRunnable implements Runnable{
    //    protected Socket client;
      //  protected String serverT;

        //public WorkerRunnable(Socket client, String serverT){
          //  this.client = client;
            //this.serverT = serverT;
        //}

        //public void run(){
          //  try{
            //    setupStreams(connection);
              //  loginReg();
               // addUsers(connection);
               // whileChatting();
          //  } catch (ClassNotFoundException e) {
                //e.printStackTrace();
            //} catch (IOException e) {
               // e.printStackTrace();
            //}
        //}

   // }

}