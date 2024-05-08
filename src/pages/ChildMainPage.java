package pages;
/**
 * 家长点击某个孩子的按钮后进入此页面。此页面用户身份为家长
 */

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import util.BtnOrange;
import util.GradientBackground;

public class ChildMainPage extends JFrame {
    private JLabel lblCurrentMoney;
    private JLabel lblTotalGoal;

    private JFrame previousPage;
    private JLabel lblChildName;

    private String parentName = "Doe";

    public ChildMainPage(int childId, JFrame previousPage) {
//        this.previousPage = previousPage; // 初始化 previousPage 引用
//
//
//        setTitle("Child Main Page");
//        setSize(1280, 720);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        getContentPane().setBackground(new Color(255, 248, 239));


        this.previousPage = previousPage; // 初始化 previousPage 引用

        setTitle("Child Main Page");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());

        // 设置渐变背景
        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.4f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);
        setLayout(null);



        JButton backButton = new JButton("Back");
        backButton.setFocusable(false); // 移除按钮的焦点框
        backButton.setContentAreaFilled(false); // 不填充内容区域
        backButton.setOpaque(true); // 不透明背景
        backButton.setBounds(0, 0, 80, 30); // 设置按钮位置为 (0, 0)
        add(backButton); // 添加按钮




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

        JLabel lblNameType = new JLabel();
        lblNameType.setBounds(1000, 40, 250, 50);
        Font font = new Font("Arial", Font.BOLD, 18); // 设置字体为加粗，大小为18
        lblNameType.setFont(font);
        add(lblNameType);
        lblNameType.setText("<html>" + parentName + "<br>Type: Parent</html>");


        // 在正上方添加一个小框显示 current money 和 total goal
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 255, 255, 155));
        topPanel.setBounds(550, 40, 200, 70);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        lblCurrentMoney = new JLabel("Current Money: $100");
        lblCurrentMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblCurrentMoney); // 将 lblCurrentMoney 添加到 topPanel 中

        lblTotalGoal = new JLabel("Total Goal: $500");
        lblTotalGoal.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblTotalGoal); // 将 lblTotalGoal 添加到 topPanel 中

        add(topPanel); // 将 topPanel 添加到 ChildMainPage 中


//        lblChildName = new JLabel(childName);
        lblChildName.setText(lblChildName.getText() + "'s Homepage");
        lblChildName.setFont(new Font("Arial", Font.BOLD, 18));
        lblChildName.setBounds(550, 250, 250, 50);
        lblChildName.setForeground(new Color(61,138,82, 255));
        add(lblChildName);

//        JLabel nameLabel = new JLabel(lblChildName.getText() + "'s Homepage");
//        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
//        nameLabel.setForeground(Color.GREEN);
//        nameLabel.setOpaque(true); // 设置标签为不透明
//        nameLabel.setBackground(new Color(255, 255, 255, 255));
////        nameLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置居中对齐
//
//// 设置标签的边距
//        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//// 添加标签到内容面板中
//       nameLabel.add(lblTotalGoal);
//
//

        // 在页面中下方添加一个大框，里面有三个按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 255, 255, 0));
        buttonPanel.setBounds(100, 300, 1080, 300);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));


        JButton btnTaskPool = new BtnOrange("Task Pool");
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

//
//        setComponentZOrder(gradientBackground, 999);

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainPage = new MainPage(); // 创建 MainPage 实例
            mainPage.setVisible(true); // 显示 MainPage

            // 创建 ChildMainPage 实例，并传入 MainPage 的引用
            ChildMainPage childMainPage = new ChildMainPage(1, mainPage);
            childMainPage.setVisible(true); // 显示 ChildMainPage
            mainPage.setVisible(false); // 隐藏 MainPage
        });
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame mainPage = new MainPage(); // 创建 MainPage 实例
//            mainPage.setVisible(true); // 显示 MainPage
//
//            // 创建 ChildMainPage 实例，并传入 MainPage 的引用
//            ChildMainPage childMainPage = new ChildMainPage(1, mainPage);
//            childMainPage.setVisible(true); // 显示 ChildMainPage
//        });
//    }
}
