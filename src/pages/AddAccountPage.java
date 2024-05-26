package pages;

import service.AccountService;
import service.TempService;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAccountPage extends JFrame {
    private AddAccountPage addAccountPage;
    private AccountService accountService;
    private int userId;

    public AddAccountPage() {
        super("Add Account");
        addAccountPage = this;
        accountService = new AccountService();
        TempService tempService = new TempService();
        userId = tempService.getTemp().getChildId();

        setSize(300, 300);
        setResizable(false);

        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);

        // 标签和输入框
        JLabel label1 = new JLabel("Please set a password");
        JLabel label2 = new JLabel("to create your saving account");
        JLabel label3 = new JLabel("Initial deposit amount:");
        RoundedPasswordTextField passwordField = new RoundedPasswordTextField(20); // 设置密码
        JTextField depositField = new RoundedTextField(20); // 输入存款金额

        gradientBackground.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 周围留白
        gradientBackground.setLayout(new GridLayout(6, 1, 10, 10)); // 使用GridLayout

        gradientBackground.add(label1);
        gradientBackground.add(label2);
        gradientBackground.add(passwordField);
        gradientBackground.add(label3);
        gradientBackground.add(depositField);

        JButton button = new JButton("Create new account");
        gradientBackground.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordField.getPassword());
                double initialDeposit = Double.parseDouble(depositField.getText());

                int newAccountId = accountService.createNewSavingAccount(password, initialDeposit, userId);
                if (newAccountId != -1) {
                    JOptionPane.showMessageDialog(null, "New account created with initial deposit of $" + initialDeposit);
                    WalletPage.walletPage.dispose();
                    PageSwitcher.switchPages(addAccountPage, new WalletPage());
                }
            }
        });

        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        AddAccountPage addAccountPage = new AddAccountPage();
    }
}
