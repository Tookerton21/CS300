package Client;

import Client.SigninGUI.SigninGUI;
import Client.SigninGUI.Error;
import java.awt.event.*;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.*;
import java.net.Socket;

/**
 * Created by niruiz3964 on 5/22/17.
 */
public class Login extends Client implements ActionListener, MouseListener {
    private JFrame loginWindow;
    private JFrame errorWindow;
    private SigninGUI login;
    private Error error;
    public boolean wakeUP;


    public Login(){
        //Load the login in window
        super( );
      //  loadLogin();
        startL();
    }

    public void startL() {
        loadLogin();

        while(wakeUP == false){
            try {
                Thread.currentThread().sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //loadChatWindow();
        //startClient();
        //super.run();
        System.out.println("exiting StartL");
    }


   private void loadLogin(){
       login = new SigninGUI();
       login.init(this);
       loginWindow = new JFrame("Login Chit Chat");
       loginWindow.setSize(300,200);
       loginWindow.setContentPane(login.getLoginWindow());
       loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       loginWindow.pack();
       loginWindow.setVisible(true);
   }

    private void loadError(){
        error = new Error();
        error.init();
        errorWindow = new JFrame("Invalid Login");
        errorWindow.setContentPane(error.getInvalidLogin());
        errorWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        errorWindow.pack();
        errorWindow.setVisible(false);
    }

    private void viewError(){
        errorWindow.setVisible(true);
    }

    private void closeLogin(){
        loginWindow.setVisible(false);
    }

    private boolean verify(String userN, String passW) throws IOException, ClassNotFoundException {

        //Send the test userN and passW
        objOut.writeObject("LOGIN");
        objOut.writeObject(userN);
        objOut.writeObject(passW);
        objOut.flush();

        //Create Buffered reader for reading Response from the server
        boolean tof = (boolean) objInp.readObject();

        return tof;
    }

    public boolean addUser(String username, String password) throws IOException, ClassNotFoundException {
        objOut.writeObject("REGISTER");
        objOut.writeObject(username);
        objOut.writeObject(password);
        objOut.flush();

        //Read back the result from adding the user

           boolean tof = (boolean)objInp.readObject();

        return tof;
        /*try {
            String tof = (String) objInp.readObject();
            if(tof.equals("true")) {
                System.out.println("User added");
                return true;
            }
*/

    }


    /*
        Action Perfomed for the login page to handle the submit and login buttons
     */
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getActionCommand().equals("Register")){

                System.out.println("Register button clicked");
                userN = login.getUsername();
                passW = login.getPassword();

                connectToServer();
                setupStreams();
                boolean res = false;
                try {
                    res = addUser(userN, passW);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                if(res == true){
                    closeLogin();
                    wakeUP = true;
                }
                else{
                    loadError();
                    viewError();
                    login.resetUsername();
                    login.resetPassword();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

         if(e.getActionCommand().equals("Login")) {
            //System.out.println("Login Button Clicked");
            try {
                userN = login.getUsername();
                passW = login.getPassword();

                connectToServer();
                setupStreams();
                boolean res = false;
                try {
                    res = verify(userN, passW);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                if(res == true) {
                    closeLogin();
                    wakeUP = true;
                }
                if(res == false){
                    //Load the Error Message Window
                    loadError();
                    viewError();


                    login.resetUsername();
                    login.resetPassword();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        else if(e.getActionCommand().equals("Send"))
            super.actionPerformed(e);

        else if(e.getActionCommand().equals("user"))
            super.actionPerformed(e);

        else if(e.getActionCommand().equals("PrivateChat"))
             super.actionPerformed(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }
}
