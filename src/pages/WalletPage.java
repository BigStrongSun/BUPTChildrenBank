package pages;

import domain.*;
import service.CreateAccountService;
import service.TempService;
import service.UpdateAccountService;

import util.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class WalletPage extends JFrame {
    private CardLayout cardLayout = new CardLayout(); // 用于切换账户信息卡片的布局
    private JPanel cardPanel = new JPanel(cardLayout); // 使用CardLayout进行账户信息卡片的管理
    private JTable transactionTable; // 显示交易记录的表格
    private JSONController jsonAccount = new JSONController("account.txt"); // 处理账户数据的JSON控制器
    private JSONController jsonTemp = new JSONController("temp.txt"); // 处理临时数据的JSON控制器
    private JSONController jsonTrans = new JSONController("transaction.txt"); // 处理交易数据的JSON控制器
    List<Account> accounts = jsonAccount.readArray(Account.class); // 从文件中读取账户数据
    private int userId; // 当前用户的ID
    private static int currentAccountId; // 当前活期账户的ID
    private int selectedAccountId; // 当前选中账户的ID
    private ArrayList<String> allAccountsId = new ArrayList<>(); // 存储所有账户ID的列表
    int n = 0; // 用于控制账户切换的索引
    JScrollPane scrollPane = new JScrollPane(transactionTable); // 包含交易表格的滚动面板

    private JButton btnCreateAccount; // 创建账户按钮
    private JButton btnTransferTapTo; // 转账按钮
    public static WalletPage walletPage; // WalletPage实例

    private BalancePanel balancePanel; // 显示余额的面板

    public WalletPage() {
        walletPage = this;
        UpdateAccountService.startScheduledUpdates(); // 开始定期更新账户
        Temp temp = (Temp) jsonTemp.read(Temp.class); // 读取当前用户的临时数据
        userId = temp.getChildId(); // 设置当前用户ID为临时数据中的子ID

        // 遍历账户列表，找到属于当前用户的账户并添加到allAccountsId中
        for (Account account : accounts) {
            if (account.getUserId() == userId) {
                allAccountsId.add(String.valueOf(account.getAccountId()));
            }
        }
        System.out.println(allAccountsId);

        // 如果当前用户有账户，设置currentAccountId为唯一的活期账户的ID，selectedAccountId为第一个账户的ID
        for (Account account : accounts) {
            if (account.getUserId() == userId && account.getAccountType() == AccountType.CURRENT_ACCOUNT) {
                currentAccountId = account.getAccountId();
                break;
            }
        }
        if (allAccountsId.size() != 0) {
            selectedAccountId = Integer.parseInt(allAccountsId.get(0));
        }

        setTitle("My Wallet");
        setSize(1200, 800); // 根据设计调整窗体大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 设置背景和边距
        JPanel backgroundPanel = new JPanel(new BorderLayout(10, 10));
        backgroundPanel.setBackground(new Color(255, 204, 153)); // 浅橙色背景
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 周围留白

        // 返回按钮
        JButton backButton = new JButton("Back");
        backButton.setFocusable(false); // 移除按钮的焦点框
        backButton.setContentAreaFilled(false); // 不填充内容区域
        backButton.setOpaque(true); // 不透明背景
        backButton.setBounds(0, 0, 80, 30); // 设置按钮位置

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PageSwitcher.switchPages(walletPage, new MainPage()); // 切换到主页面
            }
        });

        // 添加顶部标题
        JLabel walletTitle = new JLabel("My Wallet", SwingConstants.CENTER);
        walletTitle.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel panelBox = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 5);
        panelBox.setLayout(flowLayout);
        panelBox.setBackground(new Color(255, 204, 153));
        panelBox.add(backButton);
        panelBox.add(Box.createHorizontalStrut(480)); // 创建水平间隔
        panelBox.add(walletTitle);
        backgroundPanel.add(panelBox, BorderLayout.NORTH);

        // 左侧用户信息区域与账户卡片
        JPanel userInfoPanel = createUserInfoPanel();
        backgroundPanel.add(userInfoPanel, BorderLayout.WEST);

        // 创建并添加交易记录表
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        createTransactionTable();
        scrollPane.setViewportView(transactionTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        JPanel balanceContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        balanceContainer.setOpaque(false);
        accounts = jsonAccount.readArray(Account.class); // 重新读取账户数据
        double currentBalance = 0;
        for (Account account : accounts) {
            if (account.getAccountId() == selectedAccountId) {
                currentBalance += account.getBalance();
                System.out.println(account.getBalance());
            }
        }
        balancePanel = new BalancePanel("Balance", Double.toString(currentBalance), Color.RED); // 创建余额显示面板
        balanceContainer.add(balancePanel);
        rightPanel.add(balanceContainer, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        backgroundPanel.add(rightPanel, BorderLayout.CENTER);

        // 添加背景面板到JFrame
        add(backgroundPanel);

        setVisible(true);
    }

    private JPanel createUserInfoPanel() {
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BorderLayout()); // 使用边界布局管理器
        userInfoPanel.setOpaque(false);

        // 用户信息部分
        JPanel userDetailPanel = new JPanel(new BorderLayout());
        userDetailPanel.setOpaque(false);

        // 用户头像
        ImageIcon icon = new ImageIcon(new ImageIcon("src/images/头像男.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        JLabel labelIcon = new JLabel(icon);
        labelIcon.setHorizontalAlignment(JLabel.CENTER);

        // 用户名标签
        TempService tempService = new TempService();
        Temp temp = tempService.getTemp();
        JLabel userNameLabel = new JLabel(temp.getName(), SwingConstants.CENTER);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        userDetailPanel.add(labelIcon, BorderLayout.CENTER);
        userDetailPanel.add(userNameLabel, BorderLayout.SOUTH);

        // 账户切换部分
        JPanel accountSwitchPanel = new JPanel(new BorderLayout());
        accountSwitchPanel.setPreferredSize(new Dimension(400, 110)); // 控制高度
        accountSwitchPanel.setOpaque(false);

        // 控制卡片切换的按钮
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        prevButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));

        // 上一个账户按钮的监听器
        prevButton.addActionListener(e -> {
            cardLayout.previous(cardPanel); // 切换到上一个账户卡片
            if (n > 0) {
                n--;
                selectedAccountId = Integer.parseInt(allAccountsId.get(n));
            } else {
                n = allAccountsId.size() - 1;
                selectedAccountId = Integer.parseInt(allAccountsId.get(n));
            }
            System.out.println("selected account id is " + selectedAccountId);
            refreshTransactionTable(); // 刷新交易记录表
            refreshBalancePanel(); // 刷新余额显示
        });

        // 下一个账户按钮的监听器
        nextButton.addActionListener(e -> {
            cardLayout.next(cardPanel); // 切换到下一个账户卡片
            if (n < (allAccountsId.size() - 1)) {
                n++;
                selectedAccountId = Integer.parseInt(allAccountsId.get(n));
            } else {
                n = 0;
                selectedAccountId = Integer.parseInt(allAccountsId.get(n));
            }
            System.out.println("selected account id is " + selectedAccountId);
            refreshTransactionTable(); // 刷新交易记录表
            refreshBalancePanel(); // 刷新余额显示
        });

        createAccountCards(); // 创建账户卡片

        accountSwitchPanel.add(prevButton, BorderLayout.WEST);
        accountSwitchPanel.add(cardPanel, BorderLayout.CENTER);
        accountSwitchPanel.add(nextButton, BorderLayout.EAST);

        // 使用 Box 容器精确控制位置和填充
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(userDetailPanel);
        verticalBox.add(Box.createVerticalStrut(10)); // 控制两个面板之间的距离
        verticalBox.add(accountSwitchPanel);

        userInfoPanel.add(verticalBox, BorderLayout.NORTH);

        // 创建账户按钮和转账按钮
        btnCreateAccount = new BtnOrange("Create Account");
        btnTransferTapTo = new BtnOrange("Transfer");

        JPanel buttonPanel = new JPanel(); // 默认FlowLayout
        buttonPanel.add(btnCreateAccount);
        buttonPanel.add(btnTransferTapTo);

        // 创建账户按钮的监听器
        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to create a saving account? Note: The interest is 1.5% and the closure period is 7 days?", "Create Account", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    new AddAccountPage();
                }
            }
        });

        // 转账按钮的监听器
        btnTransferTapTo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PageSwitcher.switchPages(walletPage,new TransferPage());
            }
        });

        buttonPanel.setBackground(new Color(255, 204, 153)); // 浅橙色背景
        userInfoPanel.add(buttonPanel, BorderLayout.SOUTH);
        return userInfoPanel;
    }

    private void createAccountCards() {
        // 示例：添加两个账户信息卡片
        cardPanel.setOpaque(false);
        Temp temp = (Temp) jsonTemp.read(Temp.class);
        userId = temp.getChildId();

        List<String> constraintName = new ArrayList<>();
        int i = 0;
        for (Account account : accounts) {
            if (account.getUserId() == userId) {
                constraintName.add("card" + i);
                cardPanel.add(createAccountCard(allAccountsId.get(i)), constraintName.get(i));
                System.out.println(constraintName.get(i));
                System.out.println("i =" + i);
                i++;
            }
        }
    /*
        // 添加新建账号卡片
        JPanel addAccountPanel = new JPanel(new BorderLayout());
        JButton addButton = new JButton("+");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to create a saving account? Note: The interest is 1.5% and the closure period is 7 days?", "Create Account", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    new AddAccountPage();
                }
            }
        });

        addAccountPanel.add(addButton, BorderLayout.CENTER);
        cardPanel.add(addAccountPanel, "AddNewAccount"); */
    }

    private JPanel createAccountCard(String accountId) {
        FrostedGlassPanel panel = new FrostedGlassPanel(40); // 创建圆角半径为40的面板
        panel.setPreferredSize(new Dimension(300, 100)); // 设置卡片的大小

        // 银行卡图标，保持图标比例
        ImageIcon cardIcon = new ImageIcon(new ImageIcon("src/images/银行卡logo.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)); // 调整图标大小为80x80
        JLabel cardLabel = new JLabel(cardIcon);

        // 账户ID标签
        JLabel idLabel = new JLabel("Account ID: " + accountId);
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // 账户类型标签
        String accountType = "";
        for (Account account : accounts) {
            if (String.valueOf(account.getAccountId()).equals(accountId)) {
                accountType = account.getAccountType().toString();
                break;
            }
        }
        JLabel typeLabel = new JLabel(accountType);
        typeLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 10)); // 设置字体为斜体加粗

        // 设置布局
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间的间距

        // 添加图标
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2; // 跨两行
        gbc.anchor = GridBagConstraints.WEST; // 左对齐
        panel.add(cardLabel, gbc);

        // 添加账户ID标签
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST; // 左对齐，靠上
        panel.add(idLabel, gbc);

        // 添加账户类型标签
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST; // 左对齐
        panel.add(typeLabel, gbc);

        return panel;
    }



    private void createTransactionTable() {
        String[] columnNames = {"Date", "Description", "Amount", "Type"}; // 定义表格列名
        List<Transaction> transactions = jsonTrans.readArray(Transaction.class); // 从文件中读取交易数据
        Object[][] data = new Object[transactions.size()][4]; // 创建表格数据数组
        int i = 0;
        for (Transaction transaction : transactions) {
            int receiverAccountId = transaction.getReceiverAccountId();
            int senderAccountId = transaction.getSenderAccountId();
            if (receiverAccountId == selectedAccountId ||
                    senderAccountId == selectedAccountId) {
                data[i][0] = transaction.getTransactionDate();
                data[i][1] = transaction.getDescription();
                String symbol = "+ ";
                if(transaction.getType() != null){
                    if(transaction.getType().equals(TransactionType.BONUS)){
                        if (receiverAccountId == selectedAccountId) symbol = "+ ";
                        if (senderAccountId == selectedAccountId) symbol = "- ";
                    } else if(transaction.getType().equals(TransactionType.GIFT_EXCHANGE)){
                        if (receiverAccountId == selectedAccountId) symbol = "- ";
                        if (senderAccountId == selectedAccountId) symbol = "+ ";
                    } else if(transaction.getType().equals(TransactionType.WITHDRAWAL)){
                        symbol = "- ";
                    } else if (transaction.getType().equals(TransactionType.TRANSFER)) {
                        if (receiverAccountId == selectedAccountId) symbol = "+ ";
                        if (senderAccountId == selectedAccountId) symbol = "- ";
                    }
                }

                data[i][2] = symbol + transaction.getAmount();
                data[i][3] = transaction.getType();
                i++;
            }
        }

        transactionTable = new JTable(data, columnNames);
        transactionTable.setOpaque(false);
        ((DefaultTableCellRenderer) transactionTable.getDefaultRenderer(Object.class)).setOpaque(false);
    }

    private void refreshTransactionTable() {
        // 用于点击切换按钮后，变换表格内容
        // Remove old table from the scrollPane
        scrollPane.setViewportView(null);

        // Recreate the transactionTable
        createTransactionTable();

        // Add new table to scrollPane
        scrollPane.setViewportView(transactionTable);

        // Revalidate and repaint to update the UI
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void refreshBalancePanel() {
        // 刷新余额显示
        accounts = jsonAccount.readArray(Account.class);
        double currentBalance = 0;
        for (Account account : accounts) {
            if (account.getAccountId() == selectedAccountId) {
                currentBalance += account.getBalance();
                System.out.println(account.getBalance());
            }
        }
        balancePanel.updateBalance(Double.toString(currentBalance));
        balancePanel.revalidate();
        balancePanel.repaint();
    }

    public static void main(String[] args) {
        new WalletPage();
    }
}
