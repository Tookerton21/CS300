package Client;

import Client.RegisterGUI.RegisterGUI;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by niruiz3964 on 5/23/17.
 */
public class Register implements ActionListener{

    private RegisterGUI reg;
    private String userN;
    private String passW;
    JFrame frame;

    public Register(){
        reg = new RegisterGUI();
        reg.init(this);
        frame = new JFrame("RegisterGUI");
        frame.setContentPane(reg.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public String getUserN() {
        return userN;
    }

    public String getPassW() {
        return passW;
    }

    /*
    *  Control the Register button for the RegisterGUI
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Register")) {
            userN = reg.getUsernameText();
            passW = reg.getPasswordText();
            display();


            frame.dispose();
        }
    }

    /*
    Test that the information is being saved correctly
     */

    public void display(){
        System.out.println("Display TEST");
        System.out.println(userN);
        System.out.println(passW);

    }

}
