package Client.GUI;

import Client.Client;
import javax.swing.*;

/**
 * Created by niruiz3964 on 5/28/17.
 */
public class PrivateChat {
    private JPanel privateWindow;
    private JLabel toLabel;
    private JLabel fromLabel;
    private JLabel userToLabel;
    private JLabel userFromLabel;
    private JTextPane MessageHistory;
    private JTextArea privateMessage;
    private JButton privSendButton;

    public void init(Client client){
        privSendButton.setActionCommand("PrivateChat");
        privSendButton.addActionListener(client);
    }

    public String getPrivateMessage() {
        return privateMessage.getText();
    }

    public void setUserToLabel(String userToLabel){
        this.userToLabel.setText(userToLabel);
    }

    public void setUserFromLabel(String userFromLabel){
        this.userFromLabel.setText(userFromLabel);
    }

    public String getUserFromLabel(){
        return userFromLabel.getText();
    }

    public String getUserToLabel(){
        return userToLabel.getText();
    }
    public JPanel getPrivateWindow() {
        return privateWindow;
    }
}
