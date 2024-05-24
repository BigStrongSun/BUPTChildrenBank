package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.Temp;
import service.ChangePasswordService;
import service.ChangeProfileService;
import service.LoginService;
import service.TempService;
import util.WriteToTemp;

public class ChangeProfilePage extends JFrame {

    TempService tempService = new TempService();
    Temp temp = tempService.getTemp();
    String username;
    public ChangeProfilePage() {

        super("Change Username"); // Set the title via the superclass constructor

        if (temp.isParent()) {
            username = String.valueOf(temp.getParentId());
        } else {
            username = String.valueOf(temp.getChildId());
        }
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

        JLabel userLabel2 = new JLabel(username);
        userLabel2.setBounds(100, 20, 165, 25);
        panel.add(userLabel2);

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

                String password = new String(passwordText.getPassword());
                String newUsername = new String(newUserText.getPassword());

                if (ChangePasswordService.validatePassword(username, password)
                    &&!LoginService.usernameExist(newUsername)) {
                    ChangeProfileService.changeUsername(username, password, newUsername);
                    if (temp.isParent()) {//是家长，则新username应该赋给家长
                        WriteToTemp.writeToTempFile(temp.getChildId(),temp.getName(), temp.isParent(), Integer.parseInt(newUsername));
                    } else {//是孩子，则新username应该赋给孩子
                        WriteToTemp.writeToTempFile(Integer.parseInt(newUsername),temp.getName(), temp.isParent(), temp.getParentId());
                    }
                    ChangeProfileService.changeCoPId(username,newUsername);
                    JOptionPane.showMessageDialog(null, "Username changed successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid password or the username not availale");
                }
            }
        });
    }

    public static void main(String[] args) {
        new ChangeProfilePage(); // This will create and show the window
    }
}
