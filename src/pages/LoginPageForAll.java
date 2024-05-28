package pages;

import domain.Account;
import service.CreateAccountService;
import service.LoginService;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import util.*;

import static service.LoginService.refreshUserList;

public class LoginPageForAll extends JFrame {
    private LoginPageForAll loginPageForAll;
    private static final long serialVersionUID = 1L;
    boolean caught; // check if exception is caught

    public LoginPageForAll(boolean isParent) {
        loginPageForAll = this;
        setTitle(isParent ? "Parent Login Page" : "Child Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setLayout(null);
        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);
        refreshUserList();
        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setBounds(0, 0, 80, 30);
        gradientBackground.add(backButton);

        JLabel titleLabel = new JLabel("Login/Register");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 60));
        titleLabel.setBounds(450, 30, 600, 70);
        gradientBackground.add(titleLabel);
        gradientBackground.setLayout(null);

        backButton.addActionListener(e -> PageSwitcher.switchPages(loginPageForAll, LoginPage.frame));

        JLabel userLabel = new JLabel("UserId:");
        userLabel.setBounds(250, 300, 400, 80);
        userLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        gradientBackground.add(userLabel);

        RoundedTextField userText = new RoundedTextField(20);
        userText.setBounds(450, 300, 400, 80);
        userText.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        gradientBackground.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(250, 400, 400, 80);
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        gradientBackground.add(passwordLabel);

        RoundedPasswordTextField passwordText = new RoundedPasswordTextField(20);
        passwordText.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        passwordText.setBounds(450, 400, 400, 80);
        gradientBackground.add(passwordText);

        JButton registerButton = new BtnOrange("Register");
        registerButton.setBounds(670, 530, 200, 80);
        registerButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gradientBackground.add(registerButton);

        JButton loginButton = new BtnOrange("Login");
        loginButton.setBounds(450, 530, 200, 80);
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gradientBackground.add(loginButton);

        // 注册
        registerButton.addActionListener(e -> {
            String username = userText.getText();
            boolean caught = false;

            try {
                int input = Integer.parseInt(username);
                System.out.println("UserID input value: " + input);
            } catch (NumberFormatException ex) {
                caught = true;
            }

            String password = new String(passwordText.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "UserId or password cannot be empty");
            } else if (LoginService.usernameExist(username)) {
                JOptionPane.showMessageDialog(null, "UserId already exists");
            } else if (username.equals("0")) {
                JOptionPane.showMessageDialog(null, "UserId cannot be 0");
            } else if (caught) {
                JOptionPane.showMessageDialog(null, "Error: UserId must be integer");
            } else {
                String name = username; // 默认名称使用用户名
                if (isParent) {
                    LoginService.saveUser(username, password, "parent", name);
                } else {
                    LoginService.saveUser(username, password, "child", name);
                }

                int newAccountId = GenerateRandomId.generateNewAccID();
                if (!isParent) {
                    CreateAccountService.createNewCurrentAccount(newAccountId, password, Integer.parseInt(username));
                } else {
                    CreateAccountService.createParentAccount(newAccountId, password, Integer.parseInt(username));
                }

                JOptionPane.showMessageDialog(null, "Registration successful");
            }
        });

        // 登录
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "UserID or password cannot be empty");
            } else {
                // 刷新用户列表，确保读取最新数据
                refreshUserList();

                if (!LoginService.usernameExist(username) ||
                        !LoginService.validatePassword(username, password) ||
                        ((LoginService.checkIfIsParent(username) + "").equals((!isParent) + ""))) {
                    JOptionPane.showMessageDialog(null, "Invalid userID or password");
                } else {
                    if (!isParent) {
                        LoginService.saveCurrentUser(username, false, LoginService.findName(username));
                    } else {
                        LoginService.saveCurrentUser(username, true, LoginService.findName(username));
                    }

                    JOptionPane.showMessageDialog(null, "Login successful");
                    PageSwitcher.switchPages(loginPageForAll, new MainPage());
                }
            }
        });

        setVisible(true);
    }
}
