package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.Wish;
import service.TempService;
import service.UpdateAccountService;
import service.UserService;
import util.BtnOrange;
import util.GradientBackground;
import util.JSONController;
import domain.Account;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class ChildMainPage extends JFrame {
    private JLabel lblCurrentMoney;
    private JLabel lblTotalGoal;
    private TempService tempService;
    private JFrame previousPage;
    private JLabel lblChildName;

    private int parentId;
    private int childId;

    private String childName;
    private String parentName;
    private JSONController jsonUser = new JSONController("user.txt");
    UserService userService = new UserService();

    double totalBalance = 0.0;

    String totalTarget;

    public ChildMainPage(int childId, JFrame previousPage) {
        this.previousPage = previousPage;

        tempService = new TempService();
        parentId = tempService.getTemp().getParentId();
        childId = tempService.getTemp().getChildId();

        childName = userService.getChildNameById(childId);
        parentName = userService.getParentNameById(parentId);

        System.out.println("aaaa"+childName);
        System.out.println("aaaa"+parentName);

        setTitle("Child Main Page");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set gradient background
        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.4f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);
        setLayout(null);

        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setBounds(0, 0, 80, 30);
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (previousPage != null) {
                    previousPage.setVisible(true);
                    dispose();
                }
            }
        });
        add(backButton);

        setupUserIcon();
        setLocationRelativeTo(null);
        setResizable(false);

        lblChildName = new JLabel("Child " + childId);
        lblChildName.setFont(new Font("Arial", Font.BOLD, 18));
        lblChildName.setBounds(1000, 20, 250, 50);
        add(lblChildName);

        JLabel lblNameType = new JLabel();
        lblNameType.setBounds(1000, 40, 250, 50);
        Font font = new Font("Arial", Font.BOLD, 18);
        lblNameType.setFont(font);
        add(lblNameType);
        lblNameType.setText("<html>" + parentName + "<br>Type: Parent</html>");

        // Top panel to show current money and total goal
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 255, 255, 155));
        topPanel.setBounds(525, 40, 250, 70);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        lblCurrentMoney = new JLabel("Current Money: $" + totalBalance);
        lblCurrentMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblCurrentMoney);

        lblTotalGoal = new JLabel("Total Target: $" + totalTarget);
        lblTotalGoal.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblTotalGoal);

        add(topPanel);



        lblChildName.setText("<html><b style='font-size:25px;'>" + childName + "</b>'s Homepage</html>");
        lblChildName.setFont(new Font("Arial", Font.BOLD, 22)); // 设置默认大小，HTML会覆盖这里的设置


        lblChildName.setBounds(550, 250, 250, 50);
        lblChildName.setForeground(new Color(61, 138, 82, 255));
        add(lblChildName);

        // Bottom panel with three buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 255, 255, 0));
        buttonPanel.setBounds(100, 300, 1080, 300);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        JButton btnTaskPool = new BtnOrange("Task Pool");
        btnTaskPool.setPreferredSize(new Dimension(200, 100));
        btnTaskPool.setFont(new Font("Arial", Font.PLAIN, 30));
        btnTaskPool.setForeground(new Color(45, 107, 28, 204));
        btnTaskPool.setBackground(new Color(218, 241, 221));
        btnTaskPool.setBorderPainted(false);

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
        btnWishPool.setBorderPainted(false);

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
        btnWallet.setForeground(new Color(62, 90, 206, 204));
        btnWallet.setBackground(new Color(215, 231, 252));
        btnWallet.setBorderPainted(false);

        btnWallet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WalletPage().setVisible(true);
            }
        });
        buttonPanel.add(btnWallet);

        add(buttonPanel);

        setLocationRelativeTo(null);
        setResizable(false);

        this.childId = childId; // 更新 childId 的值
        calculateAndUpdateTotalBalance();
        calculateAndUpdateTotalTarget();
        setVisible(true);
        setLocationRelativeTo(null);

        UpdateAccountService.startScheduledUpdates();
    }

    private void setupUserIcon() {
        JPanel userInfoPanel = new JPanel(new BorderLayout());
        userInfoPanel.setOpaque(false);
        JPanel userDetailPanel = new JPanel(new BorderLayout());
        userDetailPanel.setOpaque(false);

        // 用户头像
        ImageIcon icon = new ImageIcon(new ImageIcon("src/images/头像男.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        JLabel labelIcon = new JLabel(icon);
        labelIcon.setHorizontalAlignment(JLabel.CENTER);

        // 添加鼠标点击事件
        labelIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    new ModifyInformation().setVisible(true);
                    dispose();

            }
        });

        userDetailPanel.add(labelIcon, BorderLayout.CENTER);
        userInfoPanel.add(userDetailPanel, BorderLayout.NORTH);
        add(userInfoPanel);  // 确保你已经正确地将用户面板添加到 JFrame 或其他容器中
        userInfoPanel.setBounds(900, 20, 100, 100);  // 设置合适的位置和大小
    }


    // Read account data from file
    private List<Account> readAccountData() {
        JSONController jsonAccount = new JSONController("account.txt");
        return jsonAccount.readArray(Account.class);
    }

    // Calculate the total balance for the childId
    private void calculateAndUpdateTotalBalance() {
        // 打印 childId 的值
        System.out.println("Child ID: " + childId);

        List<Account> accounts = readAccountData();
        double totalBalanceValue = 0.0;
        for (Account account : accounts) {
            // 打印当前遍历的账户的用户ID和余额
            System.out.println("Account User ID: " + account.getUserId() + ", Account Balance: " + account.getBalance());

            if (account.getUserId() == childId) {
                totalBalanceValue += account.getBalance();
            }
        }
        // 格式化为两位小数
        String formattedTotal = String.format("%.2f", totalBalanceValue);

        // 打印计算后的总余额
        System.out.println("Total Balance: " + formattedTotal);

        // 更新UI显示
        lblCurrentMoney.setText("Current Money: $" + formattedTotal);
    }
    private List<Wish> readWishData() {
        JSONController jsonWish = new JSONController("wish.txt");
        return jsonWish.readArray(Wish.class);
    }
    private void calculateAndUpdateTotalTarget() {
        // 打印 childId 的值
        System.out.println("Child ID: " + childId);

        List<Wish> wishes = readWishData();
        double totalTargetValue = 0.0;
        for (Wish wish : wishes) {
            if (wish.getWishStatus().equals("undone")) {
                // 打印当前遍历的账户的用户ID和余额
                System.out.println("Wish User ID: " + wish.getChildId() + ", Target: " + wish.getWishTarget());

                if (wish.getChildId() == childId) {
                    totalTargetValue += Double.parseDouble(wish.getWishTarget());
                }
            }
        }
        // 格式化为两位小数
        String formattedTotal = String.format("%.2f", totalTargetValue);

        // 打印计算后的总余额
        System.out.println("Total Target: " + formattedTotal);

        // 更新UI显示
        lblTotalGoal.setText("Total Target: $" + formattedTotal);
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame mainPage = new MainPage();
//            mainPage.setVisible(true);
//
//            ChildMainPage childMainPage = new ChildMainPage(1, mainPage);
//            childMainPage.setVisible(true);
//            mainPage.setVisible(false);
//        });
//    }
}
