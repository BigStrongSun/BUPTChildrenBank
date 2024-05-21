package pages;
//这个页面到时候合到钱包里吧

import domain.User;
import service.ChangeChildService;
import service.CreateAccountService;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAccountPage extends JFrame{
    public AddAccountPage(){
        setTitle("Modify Child");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        RoundedTextField textField = new RoundedTextField(20);//输入新account id
        RoundedPasswordTextField passwordField = new RoundedPasswordTextField(20);//设置密码

        add(textField);
        add(passwordField);
        JButton button = new JButton("Create new account");
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newAccountId = Integer.parseInt(String.valueOf(textField.getText()));
                CreateAccountService.createNewAccount(newAccountId, String.valueOf(passwordField.getPassword()));
                JOptionPane.showMessageDialog(null, "New account with id "+ newAccountId + " created");
            }
        });
        setVisible(true);

    }

    public static void main(String[] args) {
        AddAccountPage addAccountPage = new AddAccountPage();
    }

}
