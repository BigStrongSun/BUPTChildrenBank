package pages;
//更换用户名的界面
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;

import service.ChangePasswordService;
import service.ChangeProfileService;
public class ChangeProfilePage {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Change Username");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        placeComponents(panel);

        frame.setVisible(true);
    }
    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JLabel newUserLabel = new JLabel("New Username:");
        newUserLabel.setBounds(10, 80, 80, 25);
        panel.add(newUserLabel);

        JPasswordField newUserText = new JPasswordField(20);
        newUserText.setBounds(100, 80, 165, 25);
        panel.add(newUserText);

        JButton changePasswordButton = new JButton("Change Username");
        changePasswordButton.setBounds(10, 110, 165, 25);
        panel.add(changePasswordButton);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String Password = new String(passwordText.getPassword());
                String newusername = new String(newUserText.getPassword());

                if (ChangePasswordService.validatePassword(username, Password)) {
                    ChangeProfileService.changeUsername(username, Password,newusername);
                    JOptionPane.showMessageDialog(null, "username changed successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });
    }

}
