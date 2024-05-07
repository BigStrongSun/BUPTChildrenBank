package pages;
import service.LoginService;
import pages.ChildMainPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChildLoginPage {
	//孩子界面及功能
    public static void childComponents(JPanel panel) {
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

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(10, 80, 80, 25);
        panel.add(registerButton);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 80, 80, 25);
        panel.add(loginButton);


        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                String identity = "child";

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username or password cannot be empty");
                } else if (LoginService.childisUsernameExist(username)) {
                    JOptionPane.showMessageDialog(null, "Username already exists");
                } else {
                	LoginService.childsaveUser(username, password, identity);
                    JOptionPane.showMessageDialog(null, "Registration successful");
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username or password cannot be empty");
                } else if (!LoginService.childisUsernameExist(username) || !LoginService.childvalidatePassword(username, password)) {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                } else {
                    JOptionPane.showMessageDialog(null, "Login successful");
                }
            }
        });
        
    }
}
