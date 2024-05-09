package pages;

import util.FrostedGlassPanel;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WalletPage extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout); // 使用CardLayout进行账户信息卡片的管理
    private JTable transactionTable;

    public WalletPage() {
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
        JScrollPane scrollPane = new JScrollPane(transactionTable);
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
        prevButton.addActionListener(e -> cardLayout.previous(cardPanel));
        nextButton.addActionListener(e -> cardLayout.next(cardPanel));

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
        cardPanel.add(createAccountCard("Account-001"), "card1");
        cardPanel.add(createAccountCard("Account-002"), "card2");
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
        String[] columnNames = {"Date", "Description", "Amount"};
        Object[][] data = {
                {"2024-03-15 21:00", "A new backpack", "-$60"},
                {"2024-03-14 21:00", "Doing Dishes", "+$15"},
                {"2024-03-13 21:00", "Sweep the floor", "+$15"},
                {"2024-03-09 21:00", "Transformer", "-$39.99"}
        };
        transactionTable = new JTable(data, columnNames);
        transactionTable.setOpaque(false);
        ((DefaultTableCellRenderer)transactionTable.getDefaultRenderer(Object.class)).setOpaque(false);
    }

    public static void main(String[] args) {
        new WalletPage();
    }
}
