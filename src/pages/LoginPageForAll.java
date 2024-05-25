package pages;
import domain.Account;
import service.CreateAccountService;
import service.LoginService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import pages.LoginPage;
import util.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginPageForAll extends JFrame{
    private LoginPageForAll loginPageForAll;
private static final long serialVersionUID = 1L;
		//    private static JSONController jsonAccount = new JSONController("account.txt");
//    private static List<Account> accountList = jsonAccount.readArray(Account.class);
        boolean caught;//check if exception is caught

    //孩子界面及功能
    public LoginPageForAll(boolean isParent) {

    	loginPageForAll = this;
        if (!isParent) {
            setTitle("Child Login Page");
        } else {
            setTitle("Parent Login Page");
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
       

        // 在这里添加孩子界面的内容
        GradientPanel panel = new GradientPanel(new Color(175,238,238), new Color(144,238,144));
        
        add(panel);
        panel.setLayout(null);
        //返回按钮
        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setBounds(0, 0, 80, 30);
        panel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PageSwitcher.switchPages(loginPageForAll,LoginPage.frame);
            }
        });

        JLabel userLabel = new JLabel("UserId:");
        userLabel.setBounds(20, 50, 80, 25);
        panel.add(userLabel);

        JLabel userLabel2 = new JLabel("You can change your name after you login");
        userLabel2.setBounds(20, 160, 250, 25);
        panel.add(userLabel2);

        //JTextField userText = new JTextField(20);
        RoundedTextField userText = new RoundedTextField(20);
        userText.setBounds(100, 50, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 80, 80, 25);
        panel.add(passwordLabel);

        //JPasswordField passwordText = new JPasswordField(20);
        RoundedPasswordTextField passwordText = new RoundedPasswordTextField(20);
        passwordText.setPreferredSize(new Dimension(165, 25));
        passwordText.setBounds(100, 82, 165, 25);
        panel.add(passwordText);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 120, 100, 25);
        panel.add(registerButton);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 120, 100, 25);
        panel.add(loginButton);


        //注册
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();


                try {
                    // 尝试将字符串转换为int
                    int input = Integer.parseInt(username);
                    //System.out.println("Integer value: " + input);
                } catch (NumberFormatException ex) {
                    // 如果转换失败，显示错误提示
                    //JOptionPane.showMessageDialog(null, "Error: UserId must be integer");
                    caught = true;
                }

                String password = new String(passwordText.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "UserId or password cannot be empty");
                } else if (LoginService.usernameExist(username)) {
                    JOptionPane.showMessageDialog(null, "UserId already exists");
                }else if(username.equals("0")){
                    JOptionPane.showMessageDialog(null, "UserId cannot be 0");
                } else if (caught) {
                    JOptionPane.showMessageDialog(null, "Error: UserId must be integer");
                } else {
                    if (isParent) {
                        LoginService.saveUser(username, password, "parent");
                    } else {
                        LoginService.saveUser(username, password, "child");
                    }
                    int newAccountId = GenerateRandomId.generateNewAccID();;
                    if (!isParent) {//是孩子
                        //给新账号自动生成一个Current Account,该账户密码与账号的密码相同
                        CreateAccountService.createNewCurrentAccount(newAccountId,password,Integer.parseInt(username));
                    } else {//是家长
                        CreateAccountService.createParentAccount(GenerateRandomId.generateNewAccID()
                                ,password,Integer.parseInt(username));
                    }
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
                        || ((LoginService.checkIfIsParent(username)+"").equals((!isParent)+""))) {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                } else {
                    JOptionPane.showMessageDialog(null, "Login successful");
                    if (!isParent) {
                        LoginService.saveCurrentUser(username,false,LoginService.findName(username));
                    } else {
                        LoginService.saveCurrentUser(username,true,LoginService.findName(username));
                    }
                    PageSwitcher.switchPages(loginPageForAll ,new MainPage());
                }
            }
        });
        setVisible(true);
    }
}
