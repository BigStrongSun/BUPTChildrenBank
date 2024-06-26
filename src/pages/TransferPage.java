package pages;

import domain.Account;
import domain.Temp;
import domain.Transaction;
import domain.TransactionType;
import service.UpdateAccountService;
import util.BtnOrange;
import util.GradientBackground;
import util.JSONController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static service.TransactionIdGenerateService.addTransaction;
import static service.TransactionIdGenerateService.generateTransactionId;

public class TransferPage extends JFrame {
    private JTextField textFieldToAccountNumber;
    private JTextField textFieldFromAccountNumber;
    private JTextField textFieldAmount;
    private JPasswordField passwordField;

    private TransferPage transferPage;
    private JFrame previousPage;

    private int userId;
    private String password;

    private int selectedAccountId;

    String accountType;

    double totalBalance = 0.0;

    // 从文本文件中读取账户数据
    private List<Account> readAccountData() {
        JSONController jsonAccount = new JSONController("account.txt");
        return jsonAccount.readArray(Account.class);
    }

    // 更新目标账户的余额
    private void updateAccountBalance(int accountId, double newBalance) {
        List<Account> accounts = readAccountData();
        for (Account account : accounts) {
            if (account.getAccountId() == accountId) {
                account.setBalance(newBalance);
                break;
            }
        }
        JSONController jsonAccount = new JSONController("account.txt");
        jsonAccount.writeArray(accounts);
    }

    // 从 temp.txt 读取 userId
    private void readUserIdFromTemp() {
        JSONController jsonTemp = new JSONController("temp.txt");
        Temp temp = (Temp) jsonTemp.read(Temp.class);
        if (temp != null) {
            userId = temp.isParent() ? temp.getParentId() : temp.getChildId();
        }
        System.out.println("Read userId: " + userId);
    }

    public TransferPage(int selectedAccountId) {
        this.selectedAccountId = selectedAccountId; // 初始化 selectedAccountId

        transferPage = this;
        UpdateAccountService.startScheduledUpdates();

        readUserIdFromTemp();
        calculateAndUpdateTotalBalance();

        setTitle("Transfer Page");
        setSize(1280, 720); // 设置页面大小为1280x720
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置渐变背景
        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.4f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);
        setLayout(null);

        // 返回键
        JButton backButton = new JButton("Back");
        backButton.setFocusable(false); // 移除按钮的焦点框
        backButton.setContentAreaFilled(false); // 不填充内容区域
        backButton.setOpaque(true); // 不透明背景
        backButton.setBounds(0, 0, 80, 30); // 设置按钮位置为 (0, 0)
        add(backButton); // 添加按钮

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WalletPage window = new WalletPage();
                window.setVisible(true);
                transferPage.dispose();
            }
        });

        JLabel lblBalance = new JLabel("Your total balance: $" + totalBalance);
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
        mainPanel.setLayout(null);
        add(mainPanel);

        // 转出账户
        JLabel lblAccountNumber1 = new JLabel("Account-");
        lblAccountNumber1.setFont(new Font("Arial", Font.PLAIN, 24));
        lblAccountNumber1.setBounds(180, 30, 200, 30);
        mainPanel.add(lblAccountNumber1);

        JTextField textFieldFromAccountNumber = new JTextField(String.valueOf(selectedAccountId)); // 默认填入 selectedAccountId
        textFieldFromAccountNumber.setFont(new Font("Arial", Font.PLAIN, 24));
        textFieldFromAccountNumber.setBounds(280, 30, 200, 30);
        textFieldFromAccountNumber.setEditable(false); // 不允许编辑
        mainPanel.add(textFieldFromAccountNumber);

        JLabel lblAccountNumber2 = new JLabel(">>>>");
        lblAccountNumber2.setFont(new Font("Arial", Font.PLAIN, 24));
        lblAccountNumber2.setBounds(500, 30, 100, 30);
        mainPanel.add(lblAccountNumber2);

        // 转入的账户
        JLabel lblAccountNumber3 = new JLabel("Account-");
        lblAccountNumber3.setFont(new Font("Arial", Font.PLAIN, 24));
        lblAccountNumber3.setBounds(580, 30, 100, 30);
        mainPanel.add(lblAccountNumber3);

        textFieldToAccountNumber = new JTextField();
        textFieldToAccountNumber.setBounds(680, 30, 200, 30);
        textFieldToAccountNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        mainPanel.add(textFieldToAccountNumber);

        // 转账金额
        JLabel lblAmount = new JLabel("Transfer Amount");
        lblAmount.setFont(new Font("Arial", Font.PLAIN, 24));
        lblAmount.setBounds(230, 105, 200, 30);
        mainPanel.add(lblAmount);

        textFieldAmount = new JTextField();
        textFieldAmount.setBounds(480, 105, 200, 30);
        textFieldAmount.setFont(new Font("Arial", Font.PLAIN, 20));
        mainPanel.add(textFieldAmount);

        // 密码框
        JLabel lblPassword = new JLabel("Password confirm");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 24));
        lblPassword.setBounds(230, 180, 250, 30);
        mainPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(490, 180, 200, 30);
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
                String fromAccountId = textFieldFromAccountNumber.getText();
                String toAccountId = textFieldToAccountNumber.getText();
                String enteredPassword = String.valueOf(passwordField.getPassword());
                String transferAmountString = textFieldAmount.getText();

                // 转账金额转换为 double 类型
                double transferAmount = 0;
                try {
                    transferAmount = Double.parseDouble(transferAmountString);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid transfer amount!", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // 检查转账金额输入的是不是数字
                }

                // 读 accout.txt
                List<Account> accounts = readAccountData();

                boolean isFromAccountValid = false;
                double balance = 0.0;
                for (Account account : accounts) {
                    if (account.getAccountId() == selectedAccountId) {
                        balance = account.getBalance();
                        password = account.getPassword();
                        System.out.println(password);
                        accountType = String.valueOf(account.getAccountType());

                        System.out.println("User ID: " + account.getUserId());
                        System.out.println("Account ID: " + account.getAccountId());
                        System.out.println("Text Field Value: " + textFieldFromAccountNumber.getText());
                        System.out.println("password: " + account.getPassword());
                        System.out.println("accountType: " + account.getAccountType());

                        if ("SAVING_ACCOUNT".equals(accountType)) {
                            // 判断是不是还在封闭期内
                            String lockEndTimeStr = String.valueOf(account.getLockEndTime());
                            LocalDateTime lockEndTime = LocalDateTime.parse(lockEndTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                            LocalDateTime now = LocalDateTime.now();
                            System.out.println("Current Time: " + now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                            System.out.println("Lock End Time: " + lockEndTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

                            Duration duration = Duration.between(now, lockEndTime);
                            Period period = Period.between(now.toLocalDate(), lockEndTime.toLocalDate());

                            System.out.println("Time until lock period ends: " + duration.getSeconds() + " seconds");
                            System.out.println("Period until lock period ends: " + period.getYears() + " years " + period.getMonths() + " months " + period.getDays() + " days");

                            if (!duration.isNegative()) {
                                System.out.println("The lock period has not ended yet.");
                                JOptionPane.showMessageDialog(null, "The Account is still in lock period!", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            } else {
                                System.out.println("The lock period has ended.");
                            }
                        }

                        isFromAccountValid = true;
                        break; // If the account is found, exit the loop
                    }
                }

                if (!enteredPassword.equals(password)) {
                    JOptionPane.showMessageDialog(null, "Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // 密码
                }

                if (!isFromAccountValid) {
                    JOptionPane.showMessageDialog(null, "Invalid source account!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (fromAccountId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a source account!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (textFieldFromAccountNumber.getText().equals(textFieldToAccountNumber.getText())) {
                    JOptionPane.showMessageDialog(null, "You cannot transfer to the same account!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 计算现在这个用户的所有账户的总金额
                for (Account account : accounts) {
                    if (account.getUserId() == userId) {
                        totalBalance += account.getBalance();
                    }
                }

                // 查找对应的账户信息
                Account selectedFromAccount = null;
                Account selectedToAccount = null;
                for (Account account : accounts) {
                    if (String.valueOf(account.getAccountId()).equals(fromAccountId)) {
                        selectedFromAccount = account;
                    }
                    if (String.valueOf(account.getAccountId()).equals(toAccountId)) {
                        selectedToAccount = account;
                    }
                    if (selectedFromAccount != null && selectedToAccount != null) {
                        break;
                    }
                }

                // 找到对应账户后
                if (selectedToAccount != null) {
                    // 对比输入金额和实际余额
                    if (transferAmount > selectedFromAccount.getBalance()) {
                        JOptionPane.showMessageDialog(null, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // 显示一些判断信息
                        String message = "The Transfer Account ID is: " + selectedToAccount.getAccountId() + "\n" +
                                "Account Type is: " + selectedToAccount.getAccountType() + "\n" +
                                " From Account Balance is: " + balance + "\n" +
                                " To Account Balance is: " + selectedToAccount.getBalance() + "\n" +
                                "User ID is: " + selectedToAccount.getUserId() + "\n" +
                                "User password is: " + selectedToAccount.getPassword() + "\n" +
                                "Are you sure to continue the transfer?";

                        // 弹出确认对话框
                        int option = JOptionPane.showConfirmDialog(null, message, "Confirm Transfer", JOptionPane.OK_CANCEL_OPTION);

                        // 确保确认转账成功后执行以下操作
                        if (option == JOptionPane.OK_OPTION) {
                            double currentFromBalance = selectedFromAccount.getBalance();
                            selectedFromAccount.setBalance(currentFromBalance - transferAmount);
                            double currentToBalance = selectedToAccount.getBalance();
                            selectedToAccount.setBalance(currentToBalance + transferAmount);

                            // 成功转账，更新两个账户金额
                            updateAccountBalance(selectedFromAccount.getAccountId(), selectedFromAccount.getBalance());
                            updateAccountBalance(selectedToAccount.getAccountId(), selectedToAccount.getBalance());
                            calculateAndUpdateTotalBalance();

                            Transaction transaction = new Transaction(
                                    generateTransactionId(),
                                    TransactionType.TRANSFER,
                                    selectedFromAccount.getAccountId(),
                                    selectedToAccount.getAccountId(),
                                    transferAmount,
                                    0.0,
                                    "Transfer from " + selectedFromAccount.getAccountId() + " to " + selectedToAccount.getAccountId(),
                                    new Date()
                            );

                            // 添加新交易到交易记录
                            addTransaction(transaction);

                            lblBalance.setText("Your total balance: $" + totalBalance);
                            JOptionPane.showMessageDialog(null, "Transfer successful!");
                        } else {
                            // 用户点击了取消按钮，不执行任何操作
                        }
                    }
                } else {
                    // 如果找不到对应的账户信息，弹窗
                    JOptionPane.showMessageDialog(null, "Account not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void calculateAndUpdateTotalBalance() {
        // 从 account.txt 读数据，更新总金额
        List<Account> accounts = readAccountData();

        // 计算总余额
        totalBalance = 0.0;
        for (Account account : accounts) {
            if (account.getUserId() == userId) {
                totalBalance += account.getBalance();
                System.out.println(userId);
                System.out.println(account.getBalance());
                System.out.println(totalBalance);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TransferPage transferPage = new TransferPage(0); // 修改这里以适应构造函数
            transferPage.setVisible(true);
            transferPage.setLocationRelativeTo(null);
        });
    }
}
