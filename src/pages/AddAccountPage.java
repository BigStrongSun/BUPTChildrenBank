package pages;
//这个页面到时候合到钱包里吧

import service.CreateAccountService;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAccountPage extends JFrame{
    public AddAccountPage(){
        //设置密码，即可创建新账户。accountId自动随机在1-1000分配
        super("Add Account");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);

        //RoundedTextField textField = new RoundedTextField(20);//输入新account id
        RoundedPasswordTextField passwordField = new RoundedPasswordTextField(20);//设置密码

        //add(textField);
        add(passwordField);
        JButton button = new JButton("Create new account");
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int newAccountId = Integer.parseInt(String.valueOf(textField.getText()));

                CreateAccountService.createNewSavingAccount(String.valueOf(passwordField.getPassword()));
                JOptionPane.showMessageDialog(null, "New account created");
            }
        });
        setVisible(true);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        AddAccountPage addAccountPage = new AddAccountPage();
    }

}
