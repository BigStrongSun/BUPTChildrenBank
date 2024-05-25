package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import service.UpdateAccountService;
import util.BtnOrange;
import util.GradientBackground;
import util.*;
import domain.Account;
import util.JSONController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class TransferPage extends JFrame {
    private JTextField textFieldToAccountNumber;
    private JTextField textFieldFromAccountNumber;
    private JTextField textFieldAmount;
    private JPasswordField passwordField;

    private JFrame previousPage;
//    private int accountId = 100;

    private int userId ;
    private String password ;

    double totalBalance = 0.0;

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

    private void readUserIdFromTemp() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("temp.txt"));
            for (String line : lines) {
                if (line.contains("childId")) {
                    String childIdStr = line.substring(line.indexOf("childId") + 9, line.indexOf(",", line.indexOf("childId")));
                    int childId = Integer.parseInt(childIdStr);
                    if (line.contains("\"isParent\":false")) {
                        userId = childId;
                    } else if (line.contains("\"isParent\":true")) {
                        String parentIdStr = line.substring(line.indexOf("parentId") + 10, line.indexOf("}", line.indexOf("parentId")));
                        int parentId = Integer.parseInt(parentIdStr);
                        userId = parentId;
                    }
                    break;
                }
            }  System.out.println("Read userId: " + userId); // 在这里打印 userId
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public TransferPage() {

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

        //返回键
        JButton backButton = new JButton("Back");
        backButton.setFocusable(false); // 移除按钮的焦点框
        backButton.setContentAreaFilled(false); // 不填充内容区域
        backButton.setOpaque(true); // 不透明背景
        backButton.setBounds(0, 0, 80, 30); // 设置按钮位置为 (0, 0)
        add(backButton); // 添加按钮

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
        mainPanel.setLayout(null); // 使用 null 布局
        add(mainPanel);


        JLabel lblAccountNumber1 = new JLabel( "Account-");
        lblAccountNumber1.setFont(new Font("Arial", Font.PLAIN, 24));
//        lblAccountNumber1.setBounds(180, 30, 200, 30);
        lblAccountNumber1.setBounds(180, 30, 200, 30);
        mainPanel.add(lblAccountNumber1);

        JTextField textFieldFromAccountNumber = new JTextField();
        textFieldFromAccountNumber.setFont(new Font("Arial", Font.PLAIN, 24));
        textFieldFromAccountNumber.setBounds(280, 30, 200, 30);
        mainPanel.add(textFieldFromAccountNumber);

        JLabel lblAccountNumber2 = new JLabel( ">>>>");
        lblAccountNumber2.setFont(new Font("Arial", Font.PLAIN, 24));
        lblAccountNumber2.setBounds(500, 30, 100, 30);
        mainPanel.add(lblAccountNumber2);

        JLabel lblAccountNumber3 = new JLabel( "Account-");
        lblAccountNumber3.setFont(new Font("Arial", Font.PLAIN, 24));
        lblAccountNumber3.setBounds(580, 30, 100, 30);
        mainPanel.add(lblAccountNumber3);

        textFieldToAccountNumber = new JTextField();
        textFieldToAccountNumber.setBounds(680, 30, 200, 30);
        textFieldToAccountNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        mainPanel.add(textFieldToAccountNumber);


        // 添加转账金额标签和文本框
        JLabel lblAmount = new JLabel("Transfer Amount");
        lblAmount.setFont(new Font("Arial", Font.PLAIN, 24));
        lblAmount.setBounds(230, 105, 200, 30);
        mainPanel.add(lblAmount);

        textFieldAmount = new JTextField();
        textFieldAmount.setBounds(480, 105, 200, 30);
        textFieldAmount.setFont(new Font("Arial", Font.PLAIN, 20));
        mainPanel.add(textFieldAmount);

        // 添加密码标签和密码框
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


                // 验证密码是否匹配


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


                boolean isFromAccountValid = false;
                double balance = 0.0;
                for (Account account : accounts) {
                    if (account.getUserId() == userId && textFieldFromAccountNumber.getText().equals(String.valueOf(account.getAccountId()))) {
                        balance = account.getBalance();
                        password = account.getPassword();

                        System.out.println("User ID: " + account.getUserId());
                        System.out.println("Account ID: " + account.getAccountId());
                        System.out.println("Text Field Value: " + textFieldFromAccountNumber.getText());
                        System.out.println("password: " + account.getPassword());

                        isFromAccountValid = true;
                        break; // 如果找到了匹配的账户，就可以退出循环了
                    }
                }

                if (!enteredPassword.equals(String.valueOf(password))) {
                    JOptionPane.showMessageDialog(null, "Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // 如果密码不匹配，直接返回，不进行转账操作
                }

                if (!isFromAccountValid) {
                    JOptionPane.showMessageDialog(null, "Invalid source account!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isFromAccountValid) {
                    JOptionPane.showMessageDialog(null, "Invalid source account!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if ( fromAccountId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a source account!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (textFieldFromAccountNumber.getText().equals( textFieldToAccountNumber.getText())) {
                    JOptionPane.showMessageDialog(null, "You cannot transfer to the same account!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }



// 计算指定用户所有账户的余额总和

                for (Account account : accounts) {
                    if (account.getUserId() == userId) {
                        totalBalance += account.getBalance();
                    }
                }

                // 根据用户输入的账户号码查找对应的账户信息
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



                // 如果找到对应的账户信息
                if (selectedToAccount != null) {
                    // 检查转账金额是否大于账户余额
                    if (transferAmount > selectedFromAccount.getBalance()) {
                        JOptionPane.showMessageDialog(null, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // 构建要显示的信息
                        String message = "The Transfer Account ID is: " + selectedToAccount.getAccountId() + "\n" +
                                "Account Type is: " + selectedToAccount.getAccountType() + "\n" +
                                " From Account Balance is: " + balance+ "\n"+
                                " To Account Balance is: " + selectedToAccount.getBalance() + "\n" +
                                "User ID is: " + selectedToAccount.getUserId() + "\n" + "User password is: " + selectedToAccount.getPassword() + "\n" + "Are you sure to continue the transfer?";

                        // 弹出确认对话框
                        int option = JOptionPane.showConfirmDialog(null, message, "Confirm Transfer", JOptionPane.OK_CANCEL_OPTION);// 确保确认转账成功后执行以下操作
                        if (option == JOptionPane.OK_OPTION) {
                            // 更新转账方账户余额
                            double currentFromBalance = selectedFromAccount.getBalance();
                            selectedFromAccount.setBalance(currentFromBalance - transferAmount);
                            // 更新收款方账户余额
                            double currentToBalance = selectedToAccount.getBalance();
                            selectedToAccount.setBalance(currentToBalance + transferAmount);

                            // 将更新后的数据写回到文件中
                            updateAccountBalance(selectedFromAccount.getAccountId(), selectedFromAccount.getBalance()); // 更新转账方账户余额
                            updateAccountBalance(selectedToAccount.getAccountId(), selectedToAccount.getBalance()); // 更新收款方账户余额

                            // 更新页面上的总余额显示
                            calculateAndUpdateTotalBalance();

                            // 更新已有的总余额标签
                            lblBalance.setText("Your total balance: $" + totalBalance);

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


    private void calculateAndUpdateTotalBalance() {
        // 从文本文件中读取账户数据
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
            TransferPage transferPage = new TransferPage();
            transferPage.setVisible(true);
        });
    }
}
