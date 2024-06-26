package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import domain.User;
import domain.Account;
import domain.Temp;
import domain.Wish;
import service.UpdateAccountService;
import util.BtnOrange;
import service.WishService;
import service.UserService;
import service.TempService;
import service.ChangeChildService;
import util.GradientBackground;
import util.JSONController;
import util.PageSwitcher;

public class MainPage extends JFrame {
    private JLabel lblCurrentMoney;
    private JLabel lblTotalGoal;
    private TempService tempService;
    private boolean isParent;
    private int parentId;
    private int childId;
    double totalBalance;
    String totalTarget;
    private JSONController jsonUser = new JSONController("user.txt");
    UserService userService = new UserService();
    private String childName;
    private String parentName;
    public MainPage mainPage;

    public MainPage() {
        setTitle("Main Page");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);
        setLayout(null);
        mainPage = this;
        this.tempService = new TempService();
        parentId = tempService.getTemp().getParentId();
        childId = tempService.getTemp().getChildId();
        isParent = tempService.getTemp().isParent();
        parentName = userService.getParentNameById(parentId);
        childName = userService.getChildNameById(childId);

        JButton backButton = new JButton("Logout");
        backButton.setFocusable(false); // 移除按钮的焦点框
        backButton.setContentAreaFilled(false); // 不填充内容区域
        backButton.setOpaque(true); // 不透明背景
        backButton.setBounds(0, 0, 80, 30); // 设置按钮位置为 (0, 0)
        add(backButton); // 添加按钮

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempService.clearTemp(); // 清空temp.txt文件内容
                tempService.deleteTemp();
                mainPage.dispose();
                new LoginPage();
            }
        });

        System.out.println("child name is" + childName);
        System.out.println("aaaa" + parentName);

        JLabel lblNameType = new JLabel();
        lblNameType.setBounds(1000, 40, 250, 50);
        Font font = new Font("Arial", Font.BOLD, 18);
        lblNameType.setFont(font);
        add(lblNameType);

        if (isParent) {
            // 如果用户是家长，显示孩子的按钮列表
            childName = userService.getChildNameById(childId); // 从user.txt中读取孩子的名字
            displayParentView(parentId, childName);
            lblNameType.setText("<html>Name: " + parentName + "<br>Type: Parent</html>");
        } else {
            displayChildView(childId);
            lblNameType.setText("<html>Name: " + childName + "<br>Type: " + getUserIdentityById(childId) + "</html>");
        }

        setupUserIcon(isParent);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

        UpdateAccountService.startScheduledUpdates();
    }

    private void setupUserIcon(boolean isParent) {
        JPanel userInfoPanel = new JPanel(new BorderLayout());
        userInfoPanel.setOpaque(false);
        JPanel userDetailPanel = new JPanel(new BorderLayout());
        userDetailPanel.setOpaque(false);

        // 用户头像
        URL resourceUrl = WalletPage.class.getClassLoader().getResource("images/头像男.png");
        ImageIcon originalIcon = new ImageIcon(resourceUrl);
        Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        JLabel labelIcon = new JLabel(icon);
        labelIcon.setHorizontalAlignment(JLabel.CENTER);

        // 添加鼠标点击事件
        labelIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isParent) {
                    ModifyInformation modifyInformation = new ModifyInformation();
                    modifyInformation.setVisible(true);
                    dispose();
                } else {
                    ChangePasswordOrNamePage changePage = new ChangePasswordOrNamePage(false);
                    changePage.setVisible(true);
                    dispose();
                }
            }
        });

        userDetailPanel.add(labelIcon, BorderLayout.CENTER);
        userInfoPanel.add(userDetailPanel, BorderLayout.NORTH);
        add(userInfoPanel);
        userInfoPanel.setBounds(900, 20, 100, 100);
    }

    private void displayParentView(int parentId, String childName) {
        JPanel parentPanel = new JPanel();
        parentPanel.setBackground(new Color(255, 255, 255, 0));
        parentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        parentPanel.setBounds(100, 300, 1080, 300);
        add(parentPanel);

        // 定义按钮背景颜色和文本颜色
        Color buttonColor = new Color(215, 231, 252);
        Color textColor = new Color(62, 90, 206, 204);

        if (childId != 0) {
            JButton btnChild = new BtnOrange(" " + childName);
            btnChild.setPreferredSize(new Dimension(200, 100));
            btnChild.setFont(new Font("Arial", Font.PLAIN, 30));
            btnChild.setForeground(textColor);
            btnChild.setBackground(buttonColor);
            btnChild.setBorderPainted(false);
            btnChild.setBounds(100, 200, 200, 100);
            btnChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    openChildMainPage(childId);
                }
            });
            parentPanel.add(btnChild);
        } else {
            JButton btnChild = new BtnOrange("<html> Click here<br> to bound a child.</html>");
            btnChild.setPreferredSize(new Dimension(200, 100));
            btnChild.setFont(new Font("Arial", Font.PLAIN, 25));
            btnChild.setForeground(textColor);
            btnChild.setBackground(buttonColor);
            btnChild.setBorderPainted(false);
            btnChild.setBounds(100, 200, 200, 100);
            btnChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    openChangeChildPage(parentId);
                }
            });
            parentPanel.add(btnChild);
        }
    }

    private void openChildMainPage(int childId) {
        ChildMainPage childMainPage = new ChildMainPage(childId, this);
        childMainPage.setVisible(true);
    }

    private void openChangeChildPage(int parentId) {
        ChangeChildPage changeChildPage = new ChangeChildPage();
        changeChildPage.setVisible(true);
    }

    private void displayChildView(int childId) {
        // 创建 WishService 实例
        WishService wishService = new WishService();

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 255, 255, 200));
        topPanel.setBounds(525, 60, 250, 70);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        lblCurrentMoney = new JLabel("Current Money: $" + totalBalance);
        lblCurrentMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblCurrentMoney);

        lblTotalGoal = new JLabel("Total Target: $" + totalTarget);
        lblTotalGoal.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblTotalGoal);

        add(topPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 255, 255, 153));
        buttonPanel.setBounds(100, 300, 1080, 300);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        JButton btnTaskPool = new BtnOrange("Task Pool");
        btnTaskPool.setPreferredSize(new Dimension(200, 100));
        btnTaskPool.setFont(new Font("Arial", Font.PLAIN, 30));
        btnTaskPool.setForeground(new Color(45, 107, 28, 204));
        btnTaskPool.setBackground(new Color(218, 241, 221));
        btnTaskPool.setBorderPainted(false); // 移除按钮边框
        btnTaskPool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageSwitcher.switchPages(mainPage, new TaskPage());
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
        btnWallet.setForeground(new Color(62, 90, 206, 204));
        btnWallet.setBackground(new Color(215, 231, 252));
        btnWallet.setBorderPainted(false); // 移除按钮边框
        btnWallet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WalletPage().setVisible(true);
            }
        });
        buttonPanel.add(btnWallet);

        add(buttonPanel);

        this.childId = childId; // 更新 childId 的值
        calculateAndUpdateTotalBalance();
        calculateAndUpdateTotalTarget();
    }

    private List<Account> readAccountData() {
        JSONController jsonAccount = new JSONController("account.txt");
        return jsonAccount.readArray(Account.class);
    }

    private void calculateAndUpdateTotalBalance() {
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

    private String getUserIdentityById(int userId) {
        List<User> users = userService.getUserlist();
        for (User user : users) {
            if (Integer.parseInt(user.getUsername()) == userId) {
                return user.getIdentity();
            }
        }
        return "Unknown";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
    }
}
