package pages;
import service.LoginService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import util.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class ChildLoginPage {
    //孩子界面及功能
    public static void childComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        //JTextField userText = new JTextField(20);
        RoundedTextField userText = new RoundedTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        //JPasswordField passwordText = new JPasswordField(20);
        RoundedPasswordTextField passwordText = new RoundedPasswordTextField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(10, 80, 80, 25);
        panel.add(registerButton);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 80, 80, 25);
        panel.add(loginButton);


        //注册
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                if(username.equals("0")){
                    showMessageDialog(null, "Username cannot be 0");
                    System.exit(0);//就是想退出，还没想到更好的方法。然后我也不知道exit0,1有啥区别
                }
                try {
                    // 尝试将字符串转换为int
                    int input = Integer.parseInt(username);
                    System.out.println("Integer value: " + input);
                } catch (NumberFormatException ex) {
                    // 如果转换失败，显示错误提示
                    JOptionPane.showMessageDialog(null, "Error: Username must be integer");
                    System.exit(1);
                }
                String password = new String(passwordText.getPassword());
                String identity = "child";

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username or password cannot be empty");
                } else if (LoginService.usernameExist(username)) {
                    JOptionPane.showMessageDialog(null, "Username already exists");
                } else {
                    LoginService.saveUser(username, password, identity);
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
                } else if (!LoginService.usernameExist(username) ||
                        !LoginService.validatePassword(username, password)
                        || LoginService.checkIfIsParent(username)) {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                } else {
                    JOptionPane.showMessageDialog(null, "Login successful");
                    LoginService.saveCurrentUser2(username);
                }
            }
        });

    }
}
