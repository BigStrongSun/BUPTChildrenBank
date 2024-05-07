package pages;


import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import util.BtnOrange;

public class ChildMainPage extends JFrame {
    private JLabel lblCurrentMoney;
    private JLabel lblTotalGoal;

    private JFrame previousPage;
    private JLabel lblChildName;

    public ChildMainPage(int childId, JFrame previousPage) {
        this.previousPage = previousPage; // 初始化 previousPage 引用

        setTitle("Child Main Page");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 248, 239));
        setLayout(null);



        JButton backButton = new JButton("Back");
        backButton.setFocusable(false); // 移除按钮的焦点框
        backButton.setContentAreaFilled(false); // 不填充内容区域
        backButton.setOpaque(true); // 不透明背景
        backButton.setBounds(0, 0, 80, 30);
        getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (previousPage != null) {
                    previousPage.setVisible(true); // 返回到上一个页面
                    dispose(); // 关闭当前页面
                }
            }
        });
        add(backButton);

        setLocationRelativeTo(null);
        setResizable(false);

        String childName = "Child " + childId;

        lblChildName = new JLabel(childName);
        lblChildName.setFont(new Font("Arial", Font.BOLD, 18));
        lblChildName.setBounds(1000, 20, 250, 50);
        add(lblChildName);

        // 在正上方添加一个小框显示 current money 和 total goal
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.white);
        topPanel.setBounds(550, 20, 200, 70);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        lblCurrentMoney = new JLabel("Current Money: $100");
        lblCurrentMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblCurrentMoney);

        lblTotalGoal = new JLabel("Total Goal: $500");
        lblTotalGoal.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblTotalGoal);

        add(topPanel);

        // 在页面中下方添加一个大框，里面有三个按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 255, 255, 153));
        buttonPanel.setBounds(100, 300, 1080, 300);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        JButton btnTaskPool = new BtnOrange("Task Poll");
        btnTaskPool.setPreferredSize(new Dimension(200, 100));
        btnTaskPool.setFont(new Font("Arial", Font.PLAIN, 30));
        btnTaskPool.setForeground(new Color(45, 107, 28, 204));
        btnTaskPool.setBackground(new Color(218, 241, 221));
        btnTaskPool.setBorderPainted(false); // 移除按钮边框

// 设置按钮背景颜色为CAF0CE

        btnTaskPool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TaskPage().setVisible(true);
            }
        });
        buttonPanel.add(btnTaskPool);

        JButton btnWishPool = new BtnOrange("<html>Wishing<br><center>Well</center></html>");
        btnWishPool.setPreferredSize(new Dimension(200, 100));
        btnWishPool.setFont(new Font("Arial", Font.PLAIN, 30));
        btnWishPool.setForeground(new Color(191, 17, 107, 178));
        btnWishPool.setBackground(new Color(252, 219, 236));
        btnWishPool.setBorderPainted(false); // 移除按钮边框

        btnWishPool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WishPage().setVisible(true);
            }
        });
        buttonPanel.add(btnWishPool);

        JButton btnWallet = new BtnOrange("Wallet");
        btnWallet.setPreferredSize(new Dimension(200, 100));
        btnWallet.setFont(new Font("Arial", Font.PLAIN, 30));
        btnWallet.setForeground(new Color(62,90,206, 204));
        btnWallet.setBackground(new Color(215, 231, 252));
        btnWallet.setBorderPainted(false); // 移除按钮边框

        btnWallet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
//                new WalletPage().setVisible(true);
            }
        });
        buttonPanel.add(btnWallet);

        add(buttonPanel);

        setLocationRelativeTo(null);
        setResizable(false);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainPage = new MainPage(); // 创建 MainPage 实例
            mainPage.setVisible(true); // 显示 MainPage

            // 创建 ChildMainPage 实例，并传入 MainPage 的引用
            ChildMainPage childMainPage = new ChildMainPage(1, mainPage);
            childMainPage.setVisible(true); // 显示 ChildMainPage
        });
    }
}
