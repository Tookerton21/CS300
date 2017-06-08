package Client.RegisterGUI;

import Client.Login;
import Client.Register;

import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicOptionPaneUI.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by niruiz3964 on 5/23/17.
 */
public class RegisterGUI extends JFrame {
    //private Register reg;

    private JPanel panel1;
    private JTextField usernameText;
    private JTextField lastNameText;
    private JTextField firstNameText;
    private JPanel Header;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JButton regButton;


    public void init(Register reg){
        regButton.addActionListener(reg);
    }

    public JPanel getPanel1(){
        return panel1;
    }

    public String getUsernameText() {
        return usernameText.getText();
    }

    public String getLastNameText() {
        return lastNameText.getText();
    }

    public String getFirstNameText() {
        return firstNameText.getText();
    }

    public String getPasswordText() {
        return String.copyValueOf(passwordText.getPassword());
    }

}
