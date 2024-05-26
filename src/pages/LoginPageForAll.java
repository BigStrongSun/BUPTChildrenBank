package pages;
import domain.Account;
import service.CreateAccountService;
import service.LoginService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setLayout(null);
		Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
		float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
		GradientBackground gradientBackground = new GradientBackground(colors, fractions);
		setContentPane(gradientBackground);
        

        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setBounds(0, 0, 80, 30);
        gradientBackground.add(backButton);

        JLabel titleLabel = new JLabel("Login/Register");
        titleLabel.setFont(new Font("Times New Roman",Font.BOLD,60));
        titleLabel.setBounds(450,30,600,70);
        gradientBackground.add(titleLabel);
        gradientBackground.setLayout(null);

        // 在这里添加孩子界面的内容
        


        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PageSwitcher.switchPages(loginPageForAll,LoginPage.frame);
            }
        });

        JLabel userLabel = new JLabel("UserId:");
        userLabel.setBounds(250, 300, 400, 80);
        userLabel.setFont(new Font("Times New Roman",Font.PLAIN,40));
        gradientBackground.add(userLabel);

        //JLabel userLabel2 = new JLabel("You can change your name after you login");
        //userLabel2.setBounds(20, 160, 250, 25);
        //panel.add(userLabel2);

        //JTextField userText = new JTextField(20);
        RoundedTextField userText = new RoundedTextField(20);
        userText.setBounds(450, 300, 400, 80);
        userText.setFont(new Font("Times New Roman",Font.PLAIN,40));
        gradientBackground.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(250, 400, 400, 80);
        passwordLabel.setFont(new Font("Times New Roman",Font.PLAIN,40));
        gradientBackground.add(passwordLabel);

        //JPasswordField passwordText = new JPasswordField(20);
        RoundedPasswordTextField passwordText = new RoundedPasswordTextField(20);
      
        passwordText.setFont(new Font("Times New Roman",Font.PLAIN,40));
        passwordText.setBounds(450, 400, 400, 80);
        gradientBackground.add(passwordText);

        JButton registerButton = new BtnOrange("Register");
        registerButton.setBounds(670, 530, 200, 80);
        registerButton.setFont(new Font("Times New Roman",Font.BOLD,20));
        gradientBackground.add(registerButton);
      
        JButton loginButton = new BtnOrange("Login");
        loginButton.setBounds(450, 530, 200, 80);
        loginButton.setFont(new Font("Times New Roman",Font.BOLD,20));
        gradientBackground.add(loginButton);


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
