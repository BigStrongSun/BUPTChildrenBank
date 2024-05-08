package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import util.BtnOrange;
import util.GradientBackground;
import util.*;
import domain.Account;
import util.JSONController;
import java.util.List;

public class TransferPage extends JFrame {
    private JTextField textFieldAccountNumber;
    private JTextField textFieldAmount;
    private JPasswordField passwordField;

    private JFrame previousPage;
    private double Balance = 200;
    private int accountId = 100;
    private int password = 12345678;

    // 从文本文件中读取账户数据
    private List<Account> readAccountData() {
        JSONController jsonAccount = new JSONController("account.txt");
        return jsonAccount.readArray(Account.class);
    }

    // 更新目标账户的余额
    private void updateAccountBalance(int accountId, double newBalance) {
        // 从文本文件中读取账户数据
        List<Account> accounts = readAccountData();

        // 遍历账户列表，找到目标账户并更新其余额
        for (Account account : accounts) {
            if (account.getAccountId() == accountId) {
                account.setBalance(newBalance);
                break;
            }
        }

        // 将更新后的账户数据写入文本文件
        JSONController jsonAccount = new JSONController("account.txt");
        jsonAccount.writeArray(accounts);
    }

    public TransferPage() {
        setTitle("Transfer Page");
        setSize(1280, 720); // 设置页面大小为1280x720
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置渐变背景
        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.4f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);
        setLayout(null);

        //返回键
        JButton backButton = new JButton("Back");
        backButton.setFocusable(false); // 移除按钮的焦点框
        backButton.setContentAreaFilled(false); // 不填充内容区域
        backButton.setOpaque(true); // 不透明背景
        backButton.setBounds(0, 0, 80, 30); // 设置按钮位置为 (0, 0)
        add(backButton); // 添加按钮

        JLabel lblBalance = new JLabel("Your balance: $" + Balance);
        lblBalance.setFont(new Font("Arial", Font.PLAIN, 20));
        lblBalance.setForeground(new Color(45, 107, 28, 204));
        lblBalance.setBounds(900, 80, 250, 30);
        add(lblBalance);

        // 创建标题面板
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 248, 239, 0)); // 设置橙色背景
        titlePanel.setBounds(100, 100, 1000, 50);
        add(titlePanel);

        // 添加标题
        JLabel lblTitle = new JLabel("Transfer Page");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 35)); // 设置字体
        titlePanel.add(lblTitle);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255, 100));
        mainPanel.setBounds(100, 200, 1000, 400);
        mainPanel.setLayout(null); // 使用 null 布局
        add(mainPanel);

        JLabel lblAccountNumber = new JLabel("Account-" + accountId + "            " + ">>>>>>" + "         " + "Account-");
        lblAccountNumber.setFont(new Font("Arial", Font.PLAIN, 24));
        lblAccountNumber.setBounds(250, 30, 2000, 30);
        mainPanel.add(lblAccountNumber);

        textFieldAccountNumber = new JTextField();
        textFieldAccountNumber.setBounds(715, 30, 200, 30);
        textFieldAccountNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        mainPanel.add(textFieldAccountNumber);

        // 添加转账金额标签和文本框
        JLabel lblAmount = new JLabel("Transfer Amount");
        lblAmount.setFont(new Font("Arial", Font.PLAIN, 24));
        lblAmount.setBounds(180, 80, 200, 30);
        mainPanel.add(lblAmount);

        textFieldAmount = new JTextField();
        textFieldAmount.setBounds(380, 80, 200, 30);
        textFieldAmount.setFont(new Font("Arial", Font.PLAIN, 20));
        mainPanel.add(textFieldAmount);

        // 添加密码标签和密码框
        JLabel lblPassword = new JLabel("Password confirm");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 24));
        lblPassword.setBounds(180, 130, 250, 30);
        mainPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(470, 130, 200, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        mainPanel.add(passwordField);

        // 添加转账按钮
        JButton btnTransfer = new BtnOrange("Transfer");
        btnTransfer.setFont(new Font("Arial", Font.PLAIN, 24));
        btnTransfer.setBounds(400, 300, 200, 50);
        mainPanel.add(btnTransfer);

        btnTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的账户号码、金额和密码
                String accountNumber = textFieldAccountNumber.getText();
                String enteredPassword = String.valueOf(passwordField.getPassword());
                String transferAmountString = textFieldAmount.getText();

                // 验证密码是否匹配
                if (!enteredPassword.equals(String.valueOf(password))) {
                    JOptionPane.showMessageDialog(null, "Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // 如果密码不匹配，直接返回，不进行转账操作
                }

                // 转账金额转换为 double 类型
                double transferAmount = 0;
                try {
                    transferAmount = Double.parseDouble(transferAmountString);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid transfer amount!", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // 如果转账金额格式不正确，直接返回，不进行转账操作
                }

                // 从文本文件中读取账户数据
                List<Account> accounts = readAccountData();

                // 根据用户输入的账户号码查找对应的账户信息
                Account selectedAccount = null;
                for (Account account : accounts) {
                    if (String.valueOf(account.getAccountId()).equals(accountNumber)) {
                        selectedAccount = account;
                        break;
                    }
                }

                // 如果找到对应的账户信息
                if (selectedAccount != null) {
                    // 检查转账金额是否大于账户余额
                    if (transferAmount > selectedAccount.getBalance()) {
                        JOptionPane.showMessageDialog(null, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // 构建要显示的信息
                        String message = "The Transfer Account ID is: " + selectedAccount.getAccountId() + "\n" +
                                "Account Type is: " + selectedAccount.getAccountType() + "\n" +
                                "Account Balance is: " + selectedAccount.getBalance() + "\n" +
                                "User ID is: " + selectedAccount.getUserId() + "\n" + "User password is: " + selectedAccount.getPassword() + "\n" + "Are you sure to continue the transfer?";

                        // 弹出确认对话框
                        int option = JOptionPane.showConfirmDialog(null, message, "Confirm Transfer", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {

                            double currentBalance = selectedAccount.getBalance();
                            selectedAccount.setBalance(currentBalance + transferAmount);

// 更新目标账户的余额
                            updateAccountBalance(selectedAccount.getAccountId(), selectedAccount.getBalance());

// 更新页面上的显示余额
                            Balance -= transferAmount;


                            lblBalance.setText("Your balance: $" + Balance);

// 提示转账成功
                            JOptionPane.showMessageDialog(null, "Transfer successful!");

                        } else {
                            // 用户点击了取消按钮，不执行任何操作
                        }
                    }
                } else {
                    // 如果未找到对应的账户信息，弹出提示
                    JOptionPane.showMessageDialog(null, "Account not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TransferPage transferPage = new TransferPage();
            transferPage.setVisible(true);
        });
    }
}
