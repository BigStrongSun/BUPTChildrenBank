package pages;
//这个页面到时候合到钱包里吧

import service.CreateAccountService;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAccountPage extends JFrame{
    private AddAccountPage addAccountPage;
    public AddAccountPage(){

        //设置密码，即可创建新账户。accountId自动随机在1-1000分配
        super("Add Account");
        addAccountPage=this;
        setSize(300, 250);
        setResizable(false);

        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);

//        gradientBackground.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 周围留白
        //RoundedTextField textField = new RoundedTextField(20);//输入新account id
        JLabel label1 = new JLabel("Please set a passward");
        JLabel label2 = new JLabel("to create your saving account");
        RoundedPasswordTextField passwordField = new RoundedPasswordTextField(20);//设置密码
        gradientBackground.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20)); // 周围留白
        add(label1);
        add(label2);
        add(passwordField);
        JButton button = new JButton("Create new account");
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int newAccountId = Integer.parseInt(String.valueOf(textField.getText()));

                CreateAccountService.createNewSavingAccount(String.valueOf(passwordField.getPassword()));
                JOptionPane.showMessageDialog(null, "New account created");
                PageSwitcher.switchPages(addAccountPage,new WalletPage());
            }
        });
        setVisible(true);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        AddAccountPage addAccountPage = new AddAccountPage();
    }

}
