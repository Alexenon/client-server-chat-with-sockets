package chat.client.ui;

import javax.swing.*;

/**
 * Component used to force the user to enter his username
 * */
public class UsernameInputDialog {
    public static String getUsername(JFrame parentFrame) {
        String username = JOptionPane.showInputDialog(parentFrame, "Enter your username:");
        if (username == null || username.isEmpty()) {
            System.exit(0);
        }
        return username;
    }

}