import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ChildChangePassword {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Change Password");
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

        JLabel passwordLabel = new JLabel("Old Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(10, 80, 80, 25);
        panel.add(newPasswordLabel);

        JPasswordField newPasswordText = new JPasswordField(20);
        newPasswordText.setBounds(100, 80, 165, 25);
        panel.add(newPasswordText);

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(10, 110, 165, 25);
        panel.add(changePasswordButton);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String oldPassword = new String(passwordText.getPassword());
                String newPassword = new String(newPasswordText.getPassword());

                if (validatePassword(username, oldPassword)) {
                    changeUserPassword(username, newPassword);
                    JOptionPane.showMessageDialog(null, "Password changed successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid old password");
                }
            }
        });
    }

    private static boolean validatePassword(String username, String password) {
    	File file = new File("child.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] user = line.split(":");
                if (user[0].equals(username)) {
                    return user[1].equals(password);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private static void changeUserPassword(String username, String newPassword) {
    	File file = new File("child.txt");
        BufferedReader reader = null;
        File tempFile = new File("temp0.txt");
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(tempFile));
            String line = reader.readLine();
            while (line != null) {
                String[] user = line.split(":");
                if (user[0].equals(username)) {
                    writer.write(username + ":" + newPassword);
                } else {
                    writer.write(line);
                }
                writer.newLine();
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file.delete();
        tempFile.renameTo(file);
    
        System.out.println("Password for user " + username + " changed to " + newPassword);
    }
}
