package Client.LoginGUI;

import Client.Login;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JPasswordField;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.Box;



/**
 * Created by niruiz3964 on 5/22/17.
 */
public class LoginGUI extends JFrame{
    //private JPanel curWindow;
    private JPanel contentPane;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JPanel panel;
    private JPanel panel_2;
    private JPanel panel_3;
    private Component verticalGlue;
    public Login presenter;
    private JLabel lblStatus;

    public LoginGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(5, 1, 0, 0));

        verticalGlue = Box.createVerticalGlue();
        contentPane.add(verticalGlue);

        panel = new JPanel();
        contentPane.add(panel);

        lblUsername = new JLabel("Username:");
        panel.add(lblUsername);

        usernameField = new JTextField();
        panel.add(usernameField);
        usernameField.setColumns(10);

        panel_2 = new JPanel();
        contentPane.add(panel_2);

        lblPassword = new JLabel("Password:");
        panel_2.add(lblPassword);

        passwordField = new JPasswordField();
        panel_2.add(passwordField);
        passwordField.setColumns(10);

        lblStatus = new JLabel("");
        contentPane.add(lblStatus);

        panel_3 = new JPanel();
        contentPane.add(panel_3);

        this.btnLogin = new JButton("Login");
        panel_3.add(btnLogin);

        btnRegister = new JButton("Register");
        panel_3.add(btnRegister);

    }

    public void initialize() {
        btnLogin.addActionListener(presenter);
        //passwordField.addKeyListener(presenter);
        btnRegister.addActionListener(presenter);

        this.setTitle("ChatApp");

        btnLogin.setName("loginButton");
        passwordField.setName("passwordField");
    }


    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return String.copyValueOf(passwordField.getPassword());
    }

    public void setErrorStatus(String m) {
        lblStatus.setText(m);
    }


    /*
    private JPanel panel1;
    private JPasswordField password;
    private JTextField userName;
    private JButton registerButton;
    private JButton loginButton;

    private void init(){
        panel1= new JPanel();
        password = new JPasswordField();
        userName = new JTextField();
        registerButton = new JButton();
        loginButton = new JButton();

    }

    public void loginView(){
        init();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel1);
        panel1.setLayout(new GridLayout(5, 1, 0, 0));

        panel1.add(password);

    }*/

}
