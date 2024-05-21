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
        setTitle("Add Saving Account");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

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

    }

    public static void main(String[] args) {
        AddAccountPage addAccountPage = new AddAccountPage();
    }

}
