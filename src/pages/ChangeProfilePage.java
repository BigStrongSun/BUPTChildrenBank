package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import service.ChangePasswordService;
import service.ChangeProfileService;

public class ChangeProfilePage extends JFrame {

    public ChangeProfilePage() {
        super("Change Username"); // Set the title via the superclass constructor
        setSize(400, 300); // Set size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        JPanel panel = new JPanel();
        getContentPane().add(panel); // Add panel to the frame's content pane
        placeComponents(panel); // Place components on the panel
        setVisible(true); // Make the frame visible
    }

    private void placeComponents(JPanel panel) {
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
                String password = new String(passwordText.getPassword());
                String newUsername = new String(newUserText.getPassword());

                if (ChangePasswordService.validatePassword(username, password)) {
                    ChangeProfileService.changeUsername(username, password, newUsername);
                    JOptionPane.showMessageDialog(null, "Username changed successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });
    }

    public static void main(String[] args) {
        new ChangeProfilePage(); // This will create and show the window
    }
}
