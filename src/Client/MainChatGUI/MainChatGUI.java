package Client.MainChatGUI;

import Client.*;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.util.Utilities;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by niruiz3964 on 5/24/17.
 */
public class MainChatGUI{
    private static int MAX = 1000;
    private JPanel mainChatWindow;
    private JButton sendButton;
    private JTextArea mainChat;
    private JTextField messageText;
    private JPanel users;
    private JScrollBar scrollBar2;
    private Client client;
    private ArrayList<JButton> list;

    public void init(Client client){
        list = new ArrayList<>();
        users.setLayout(new BoxLayout(users, BoxLayout.Y_AXIS));
        this.client = client;
        sendButton.setActionCommand("Send");
        sendButton.addActionListener((ActionListener) client);
        mainChat.setEditable(false);
    }

    public JPanel getMainChatWindow() {
        return mainChatWindow;
    }

    public String getMessageText() {
        return messageText.getText();
    }

    public void resetMessageText(){
        messageText.setText("");
    }

    public void updateMainWindow(String message){
        mainChat.append(message+"\n");
    }

    public void turnMessageOff(boolean onOff){
        messageText.setEditable(onOff);
    }

    public void setMainChat(JTextArea mainChat) {
        this.mainChat = mainChat;
    }

    public JPanel getUsers(){
        return users;
    }

    public void adduser(String name, String[] onlineList, Client client){
       // DefaultFormBuilder builder;

                        JButton user = new JButton();
                        user.setText(name);
                        user.setActionCommand("user");
                        user.addActionListener(client);
        //Create a smaller border
        user.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        user.setBorderPainted(false);
        user.setContentAreaFilled(false);
        user.setFocusPainted(false);

        list.add(user);
        int size = onlineList.length;

        //Highlight the butons for users that are online
        for(int i=0; i<size; ++i){
            if(name.equals(onlineList[i]))
                user.setBackground(Color.yellow);
                user.setOpaque(true);
        }

        users.add(user);
        users.revalidate();
        users.repaint();
    }

    public void updateOnline(String[] name){
        int size = name.length;
        for(int i=0; i<list.size(); ++i){
            list.get(i).setBackground(Color.white);

            for(int x=0; x<size; ++x){
                if(list.get(i).getText().equals(name[x])){
                    list.get(i).setBackground(Color.yellow);
                }
            }
        }
    }
}
