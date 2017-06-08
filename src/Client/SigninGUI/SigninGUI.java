package Client.SigninGUI;

import Client.*;
import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by niruiz3964 on 5/25/17.
 */
public class SigninGUI {

    private JPanel loginWindow;
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel passwordLabel;
    private JLabel usernameLabel;
    //private Client client;


    public void init(Client client){
        //Set Action commands for both the login and register buttons
        loginButton.setActionCommand("Login");
        registerButton.setActionCommand("Register");

        //Have the client class implement the action performed
        loginButton.addActionListener((ActionListener) client);
        registerButton.addActionListener((ActionListener) client);
    }

    public JPanel getLoginWindow() {
        return loginWindow;
    }

    public String getUsername() {
        return usernameText.getText();
    }

    public String getPassword() {
        return String.copyValueOf(passwordText.getPassword());
    }

    public void resetUsername() { usernameText.setText("");}

    public void resetPassword() {passwordText.setText("");}

}
