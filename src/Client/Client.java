package Client;

import Client.GUI.PrivateChat;
import Client.MainChatGUI.MainChatGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.lang.management.ThreadInfo;
import java.net.*;
import javax.swing.*;


/**
 * Created by niruiz3964 on 5/17/17.
 */
public class Client extends JFrame implements ActionListener, MouseListener, Runnable {

    private JTextField userText;
    //private JTextArea chatWindow;

    protected String userN;
    protected String passW;

    protected transient ObjectInputStream objInp;
    protected transient ObjectOutputStream objOut;
    protected String message = "";
    protected String serverIP;
    protected transient Socket connection;
    protected MainChatGUI chatWindow;
    protected PrivateChat privateChat;
    private JFrame frameMain;
    private JFrame frameSub;
    protected JFrame curWindow;
    protected String toSend;
    protected String recieved;
    protected int portNum;
    protected boolean tof;
    protected String[] userList;
    protected String[] onlineList;
    protected ClientConnection cc;
    protected ClientChatting cChat;
    //Connect c = new Connect().

    public Client( ) {
        //loadChatWindow();
        userN = null;
        passW = null;
        message = null;
        serverIP = "127.0.0.1";
        portNum = 6666;
        connection = null;
        tof = false;
        cc = null;
    }


    //connect to Server
    protected void connectToServer( ) throws IOException{

//        chatWindow.updateMainWindow("Attempting conection...");
        this.connection = new Socket(serverIP, portNum);
//        chatWindow.updateMainWindow("Connected to Chit Chat");
    }

    protected void setupStreams( ) throws IOException {
        objInp = new ObjectInputStream(connection.getInputStream());
        objOut = new ObjectOutputStream(connection.getOutputStream());
        objOut.flush();
    }

    private void closeCon(){
        chatWindow.updateMainWindow("Thanks for using Chit Chat");
        chatWindow.turnMessageOff(false);

        try{
            objOut.close();
            objInp.close();
            connection.close();
        }catch (IOException ioExceptions){
            ioExceptions.printStackTrace();
        }
    }

    public void whileChatting(){
        do{
            try{
                message = (String) objInp.readObject();
                showMessage(message);
            }catch (ClassNotFoundException classNotFoundException){
                showMessage("\nError, wrong Object type");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(!message.equals("Server - End"));
    }

    //Change or update chatwindow
protected void showMessage(final String mess){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.updateMainWindow(mess);
                    }
                }
        );
    }
    public void run(){
        loadChatWindow();
        System.out.println("Thread: " + Thread.currentThread().getId());
        updateUserList(objInp);
        while(true){
            whileChatting();
        }

    }
    public void startClient() {
        System.out.println("Thread: " + Thread.currentThread().getId());
      // cc = new ClientConnection(connection, this);
        cChat = new ClientChatting(connection, this);
        //cc.start();
        //cChat.start();

// cc.start();
        loadChatWindow();
        System.out.println("Thread: " + Thread.currentThread().getId());
        updateUserList(objInp);
        //cc = new ClientConnection(connection, this);
        //cChat = new ClientChatting(connection, this);
        //cc.start();
        cChat.start();
        while (true) {
            //try {
               // String instr = (String) objInp.readObject();
              //  if (instr.equals("Update"))
                  //  updateUserList(objInp);
                   // cc.updateUserList();
                //else if (instr.equals("Message"))
                    //cChat.whileChatting();

            //} catch (IOException e) {
              //  e.printStackTrace();
            //} catch (ClassNotFoundException e) {
              ///  e.printStackTrace();
            //}
            //whileChatting();
            cChat.whileChatting();
        }
    }

    public void loadChatWindow(){

        //Load the main Chat Window
        chatWindow = new MainChatGUI();
        //Client client = this;
        chatWindow.init(this);

        frameMain = new JFrame("Chit Chat");
        frameMain.setContentPane(chatWindow.getMainChatWindow());
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMain.pack();
        frameMain.setVisible(true);

        //Load the sub chat Window but dont display
        privateChat = new PrivateChat();
        privateChat.init(this);

        frameSub = new JFrame("Chit Chat Private Message");
        frameSub.setContentPane(privateChat.getPrivateWindow());
        frameSub.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frameSub.pack();
        frameSub.setVisible(false);
    }

    protected void publicMessage(String toSend) throws IOException {
        objOut.writeObject("PUBLIC");/////////////////////////////////TESTING
        objOut.writeObject(toSend);
        objOut.flush();
    }

    protected void updateChat() throws ClassNotFoundException {
        try {
            recieved = (String) objInp.readObject();
            chatWindow.updateMainWindow(recieved);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*
    public void updateUserList(String name){
        chatWindow.adduser(name);
    }
*/

    public void updateUserList(ObjectInputStream objInp){
        try {

            userList = (String[]) objInp.readObject();
            onlineList = (String[]) objInp.readObject();
            int size = userList.length;


            for(int i=0; i<size; ++i){
                chatWindow.adduser(userList[i], onlineList , this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
        Action performed for the send button for the main chat
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Send")){
            String toSend = chatWindow.getMessageText();
            chatWindow.resetMessageText();
            try {
                publicMessage(userN+"- "+toSend);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("user")){
            JButton button = (JButton) e.getSource();
            String user = button.getText();
            System.out.println(user);
            frameSub.setVisible(true);
            privateChat.setUserFromLabel(userN);
            privateChat.setUserToLabel(user);
        }
        else if(e.getActionCommand().equals("user")){
            e.paramString();
            System.out.print(e.paramString());
        }
        else if(e.getActionCommand().equals("PrivateChat")){
            String from = privateChat.getUserFromLabel();
            String to = privateChat.getUserToLabel();
            String message = privateChat.getPrivateMessage();

            try {
                objOut.writeObject("PRIVATE");
                objOut.writeObject(to);
                objOut.writeObject(message);
                objOut.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    /*
            Test the contents of the user info
         */
    public void display(){

        System.out.println("Username: "+userN);
        System.out.println("Password: "+passW);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        String recip = (String)e.getSource();
        System.out.println(recip);
        frameSub.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

   /*
    public Client(String host){
        super( "Client Chit Chat!" );
        serverIP = host;
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                        sendMessage(event.getActionCommand());
                        userText.setText("");
                    }
                }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(300, 150);
        setVisible(true);
    }

    public void setUserN(String userN) {
        this.userN = userN;
    }

    public void setPassW(String passW) {
        this.passW = passW;
    }

    //connect to server
    public void startRunning(){
        try{
            connectToServer();
            setupStreams();
            whileChatting();
        }catch (EOFException eofException){
            showMessage("\nClient terminated the connection");
        }catch (IOException ioException){
            ioException.printStackTrace();
        }finally{
            closeCon();
        }

    }

    //connect to Server
    private void connectToServer() throws IOException{
        showMessage("Attempting conection...\n");
        connection = new Socket(InetAddress.getByName(serverIP), 1234);
        showMessage("Connected to: "+connection.getInetAddress().getHostAddress());
    }

    //Set up the streams
    private void setupStreams( ) throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams are not setup");

    }

    private void whileChatting() throws IOException{
        ableToType(true);
        do{
            try{
                message = (String) input.readObject();
                showMessage("\n"+message);
            }catch (ClassNotFoundException classNotFoundException){
                showMessage("\nError, wrong Object type");
            }
        }while(!message.equals("Server - End"));
    }

    //Close the streams and sockets
    private void closeCon(){
        showMessage("\nClosing streams down");
        ableToType(false);
        try{
            output.close();
            input.close();
            connection.close();
        }catch (IOException ioExceptions){
            ioExceptions.printStackTrace();
        }
    }

    //Send message to server
    private void sendMessage(String message){
        try{
            output.writeObject("Client - "+message);
            output.flush();
            showMessage("\nCLIENT - "+message);
        }catch (IOException ioException){
            chatWindow.append("\nError in sending message");
        }
    }

    //Change or update chatwindow
    private void showMessage(final String mess){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(mess);
                    }
                }
        );
    }

    //Give user permission to type into text box
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


}
*/