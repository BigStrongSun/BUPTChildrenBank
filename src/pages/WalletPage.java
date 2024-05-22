package pages;

import domain.Account;
import domain.Temp;
import domain.Transaction;
import domain.TransactionType;
import util.FrostedGlassPanel;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;

import util.JSONController;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class WalletPage extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout); // 使用CardLayout进行账户信息卡片的管理
    private JTable transactionTable;
    private JSONController jsonAccount = new JSONController("account.txt");
    private JSONController jsonTemp = new JSONController("temp.txt");
    private JSONController jsonTrans = new JSONController("transaction.txt");
    List<Account> accounts = jsonAccount.readArray(Account.class);
    private int userId;//当前用户的id
    private static int currentAccountId;
    private ArrayList<String> allAccountsId = new ArrayList<>();
    int n = 0;
    JScrollPane scrollPane = new JScrollPane(transactionTable);

    public WalletPage() {
        Temp temp = (Temp) jsonTemp.read(Temp.class);
        if (temp.isParent()) {
            userId = temp.getParentId();
        } else {
            userId = temp.getChildId();
        }

        for (Account account : accounts) {
            if (account.getUserId() == userId) {
                allAccountsId.add(String.valueOf(account.getAccountId()));
            }
        }
        System.out.println(allAccountsId);


        currentAccountId = Integer.parseInt(allAccountsId.get(0));
        setTitle("My Wallet");
        setSize(1200, 800); // 根据设计调整窗体大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 设置背景和边距
        JPanel backgroundPanel = new JPanel(new BorderLayout(10, 10));
        backgroundPanel.setBackground(new Color(255, 204, 153)); // 浅橙色背景
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 周围留白

        // 添加顶部标题
        JLabel walletTitle = new JLabel("My Wallet", SwingConstants.CENTER);
        walletTitle.setFont(new Font("Arial", Font.BOLD, 24));
        backgroundPanel.add(walletTitle, BorderLayout.NORTH);

        // 左侧用户信息区域与账户卡片
        JPanel userInfoPanel = createUserInfoPanel();
        backgroundPanel.add(userInfoPanel, BorderLayout.WEST);

        // 创建并添加交易记录表
        createTransactionTable();
        scrollPane.setViewportView(transactionTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

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
        JLabel userNameLabel = new JLabel("John Doe", SwingConstants.CENTER);
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

        prevButton.addActionListener(e -> {
            cardLayout.previous(cardPanel);
            if (n > 0) {
                n--;
                currentAccountId = Integer.parseInt(allAccountsId.get(n));
            } else {
                n = allAccountsId.size() - 1;
                currentAccountId = Integer.parseInt(allAccountsId.get(n));
            }
            System.out.println("current account id is " + currentAccountId);
            refreshTransactionTable();
        });
        nextButton.addActionListener(e -> {
            cardLayout.next(cardPanel);
            if (n < (allAccountsId.size() - 1)) {
                n++;
                currentAccountId = Integer.parseInt(allAccountsId.get(n));
            } else {
                n = 0;
                currentAccountId = Integer.parseInt(allAccountsId.get(n));
            }
            refreshTransactionTable();
        });

        createAccountCards();

        accountSwitchPanel.add(prevButton, BorderLayout.WEST);
        accountSwitchPanel.add(cardPanel, BorderLayout.CENTER);
        accountSwitchPanel.add(nextButton, BorderLayout.EAST);

        // 使用 Box 容器精确控制位置和填充
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(userDetailPanel);
        verticalBox.add(Box.createVerticalStrut(10)); // 控制两个面板之间的距离
        verticalBox.add(accountSwitchPanel);

        userInfoPanel.add(verticalBox, BorderLayout.NORTH);

        return userInfoPanel;
    }


    private void createAccountCards() {
        // 示例：添加两个账户信息卡片
        cardPanel.setOpaque(false);
        Temp temp = (Temp) jsonTemp.read(Temp.class);
        if (temp.isParent()) {
            userId = temp.getParentId();
        } else {
            userId = temp.getChildId();
        }
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
    }

    private JPanel createAccountCard(String accountId) {
        FrostedGlassPanel panel = new FrostedGlassPanel(40); // 创建圆角半径为20的面板
        panel.setPreferredSize(new Dimension(180, 100)); // 设置卡片的大小

        // 银行卡图标，保持图标比例
        ImageIcon cardIcon = new ImageIcon(new ImageIcon("src/images/银行卡logo.png").getImage().getScaledInstance(-1, 100, Image.SCALE_SMOOTH)); // 调整图标高度为80，保持原始比例
        JLabel cardLabel = new JLabel(cardIcon);
        cardLabel.setHorizontalAlignment(JLabel.CENTER);

        // 账户ID标签
        JLabel idLabel = new JLabel(accountId, SwingConstants.CENTER);
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));

        panel.add(cardLabel, BorderLayout.CENTER);
        panel.add(idLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void createTransactionTable() {

        String[] columnNames = {"Date", "Description", "Amount", "Type"};
        List<Transaction> transactions = jsonTrans.readArray(Transaction.class);
        Object[][] data = new Object[transactions.size()][4];
        int i = 0;
        for (Transaction transaction : transactions) {
            int receiverAccountId = transaction.getReceiverAccountId();
            int senderAccountId = transaction.getSenderAccountId();
            if (receiverAccountId == currentAccountId ||
                    senderAccountId == currentAccountId) {
                data[i][0] = transaction.getTransactionDate();
                data[i][1] = transaction.getDescription();
                String symbol = "+ ";
                if(transaction.getType().equals(TransactionType.BONUS)){
                    if (receiverAccountId == currentAccountId) symbol = "+ ";
                    if (senderAccountId == currentAccountId) symbol = "- ";
                }else if(transaction.getType().equals(TransactionType.GIFT_EXCHANGE)){
                    if (receiverAccountId == currentAccountId) symbol = "- ";
                    if (senderAccountId == currentAccountId) symbol = "+ ";
                }else if(transaction.getType().equals(TransactionType.WITHDRAWAL)){
                    symbol = "- ";
                } else if (transaction.getType().equals(TransactionType.TRANSFER)) {
                    if (receiverAccountId == currentAccountId) symbol = "+ ";
                    if (senderAccountId == currentAccountId) symbol = "- ";
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

    private void refreshTransactionTable() {//用于点击切换按钮后，变换表格内容
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

    public static void main(String[] args) {
        new WalletPage();
    }
}
