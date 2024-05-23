package pages;
import domain.Account;
import service.CreateAccountService;
import service.LoginService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import util.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginPageForAll extends JFrame{
    private static JSONController jsonAccount = new JSONController("account.txt");
    private static List<Account> accountList = jsonAccount.readArray(Account.class);
    //孩子界面及功能
    public LoginPageForAll(boolean isParent) {
        if (!isParent) {
            setTitle("Child Login Page");
        } else {
            setTitle("Parent Login Page");
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        // 在这里添加孩子界面的内容
        JPanel panel = new JPanel();
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
                PageSwitcher.switchPages(LoginPage.page,LoginPage.frame);
            }
        });

        JLabel userLabel = new JLabel("UserId:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JLabel userLabel2 = new JLabel("You can change your name after you login");
        userLabel2.setBounds(10, 130, 250, 25);
        panel.add(userLabel2);

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
//                if(username.equals("0")){
//                    showMessageDialog(null, "UserId cannot be 0");
//                    System.exit(0);//就是想退出，还没想到更好的方法。
//                }
                try {
                    // 尝试将字符串转换为int
                    int input = Integer.parseInt(username);
                    System.out.println("Integer value: " + input);
                } catch (NumberFormatException ex) {
                    // 如果转换失败，显示错误提示
                    JOptionPane.showMessageDialog(null, "Error: UserId must be integer");
                    System.exit(1);
                }
                String password = new String(passwordText.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "UserId or password cannot be empty");
                } else if (LoginService.usernameExist(username)) {
                    JOptionPane.showMessageDialog(null, "UserId already exists");
                }else if(username.equals("0")){
                    JOptionPane.showMessageDialog(null, "UserId cannot be 0");
                }else {
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
                    PageSwitcher.switchPages(LoginPage.childFrame,new MainPage());
                }
            }
        });
        setVisible(true);
    }
}
