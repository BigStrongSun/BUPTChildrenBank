package pages;
import service.CreateAccountService;
import service.LoginService;
import util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class ParentLoginPage{
    //父母登陆界面及功能,以及注册界面功能
    public static void parentComponents(JPanel panel) {
        panel.setLayout(null);

        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setBounds(0, 0, 80, 30);
        panel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PageSwitcher.switchPages(LoginPage.parentFrame,LoginPage.frame);
            }
        });

        JLabel userLabel = new JLabel("UserId:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        RoundedTextField userText = new RoundedTextField(20);
        //JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        RoundedPasswordTextField passwordText = new RoundedPasswordTextField(20);
        //JPasswordField passwordText = new JPasswordField(20);
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
                String username = null;
                boolean usernameValid = false;
                while (!usernameValid){
                    username = userText.getText();
                    if(username.equals("0")){
                        showMessageDialog(null, "Username cannot be 0");
                        System.exit(0);//就是想退出，还没想到更好的方法。然后我也不知道exit0,1有啥区别
                    }
                    try {
                        // 尝试将字符串转换为int
                        int input = Integer.parseInt(username);
                        System.out.println("Integer value: " + input);
                        usernameValid = true;
                    } catch (NumberFormatException ex) {
                        // 如果转换失败，显示错误提示
                        showMessageDialog(null, "Error: UserId must be integer");
                        System.exit(1);
                    }
                }
                String password = new String(passwordText.getPassword());
                String identity = "parent";

                if (username.isEmpty() || password.isEmpty()) {
                    showMessageDialog(null, "UserId or password cannot be empty");
                } else if (LoginService.usernameExist(username)) {
                    showMessageDialog(null, "UserId already exists");
                } else {
                    LoginService.saveUser(username, password,identity);
                    CreateAccountService.createParentAccount(GenerateRandomId.generateNewAccID()
                            ,password,Integer.parseInt(username));
                    showMessageDialog(null, "Registration successful");
                    //PageSwitcher.switchPages(LoginPage.parentFrame,new MainPage());
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    showMessageDialog(null, "UserId or password cannot be empty");
                } else if (!LoginService.usernameExist(username) ||
                        !LoginService.validatePassword(username, password)
                        || !LoginService.checkIfIsParent(username)) {
                    showMessageDialog(null, "Invalid username or password");
                } else {
                    showMessageDialog(null, "Login successful");
                    LoginService.saveCurrentUser(username,true,LoginService.findName(username));
                    PageSwitcher.switchPages(LoginPage.parentFrame,new MainPage());
                }
            }
        });

    }

}


