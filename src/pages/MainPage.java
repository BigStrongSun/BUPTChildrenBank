package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import domain.Account;
import domain.Wish;
import service.TempService;
import util.BtnOrange;
import service.WishService;
import util.GradientBackground;
import util.JSONController;

public class MainPage extends JFrame {
    private JLabel lblCurrentMoney;
    private JLabel lblTotalGoal;
    private TempService tempService;
    private boolean isParent;
    private int parentId;
    private int childId;
     double totalBalance;
     String totalTarget;

    private String childName ;

    private String parentName;

    public MainPage() {
        setTitle("Main Page");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);
        setLayout(null);



        tempService = new TempService();
        parentId = tempService.getTemp().getParentId();
        childId = tempService.getTemp().getChildId();
        isParent = tempService.getTemp().isParent();
        childName = String.valueOf(tempService.getTemp().getChildId());
        parentName = String.valueOf(tempService.getTemp().getParentId());



        JLabel lblNameType = new JLabel();
        lblNameType.setBounds(1000, 20, 250, 50);
        Font font = new Font("Arial", Font.BOLD, 18); // 设置字体为加粗，大小为18
        lblNameType.setFont(font);
        add(lblNameType);

        if (isParent) {
            // 如果用户是家长，显示孩子的按钮列表
            displayParentView(parentId);
            lblNameType.setText("<html>" + parentName + "<br>Type: Parent</html>");

        } else {

            displayChildView(childId);
            lblNameType.setText("<html>" + childName + "<br>Type: Child</html>");
        }


        setLocationRelativeTo(null);
        setResizable(false);



    }



//    private void displayParentView(int parentId) {
//        JPanel parentPanel = new JPanel();
//        parentPanel.setBackground(new Color(255, 255, 255, 0));
//        parentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
//        parentPanel.setBounds(100, 300, 1080, 300);
//        add(parentPanel);
//
//        // 定义一组按钮背景颜色
//        Color[] buttonColors = {new Color(215, 231, 252), new Color(240, 200, 200), new Color(225, 250, 200)};
//        // 定义一组按钮文本颜色
//        Color[] textColors = {new Color(62, 90, 206, 204), new Color(191, 17, 48), new Color(45, 114, 25)};
//
//        for (int i = 0; i < 3; i++) {
//            JButton btnChild = new BtnOrange("Child " + (i + 1));
//            btnChild.setPreferredSize(new Dimension(200, 100));
//            btnChild.setFont(new Font("Arial", Font.PLAIN, 30));
//            // 使用循环中的相应颜色
//            btnChild.setForeground(textColors[i % textColors.length]);  // 循环使用文本颜色数组中的颜色
//            btnChild.setBackground(buttonColors[i % buttonColors.length]);  // 循环使用背景颜色数组中的颜色
//            btnChild.setBorderPainted(false); // 移除按钮边框
//            int childId = i + 1;
//            btnChild.setBounds(100 + i * 300, 200, 200, 100);
//            btnChild.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    dispose();
//                    openChildMainPage(childId);
//                }
//            });
//            parentPanel.add(btnChild);
//        }
//    }

    private void displayParentView(int parentId) {
        JPanel parentPanel = new JPanel();
        parentPanel.setBackground(new Color(255, 255, 255, 0));
        parentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        parentPanel.setBounds(100, 300, 1080, 300);
        add(parentPanel);

        // 定义按钮背景颜色和文本颜色
        Color buttonColor = new Color(215, 231, 252);
        Color textColor = new Color(62, 90, 206, 204);


        if (childId != 0) {
            JButton btnChild = new BtnOrange(" " + childId);
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
        }else {
            JLabel noChildLabel = new JLabel("You haven't bound any child.");
            noChildLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            noChildLabel.setForeground(textColor);
            // 设置JLabel的位置（例如：X = 50, Y = 100）
            noChildLabel.setBounds(200, 100, 300, 50);
            parentPanel.add(noChildLabel);
        }
    }


    private void openChildMainPage(int childId) {
        ChildMainPage childMainPage = new ChildMainPage(childId, this); // 将 MainPage 的引用传递给 ChildMainPage
        childMainPage.setVisible(true);
    }





    private void displayChildView(int childId) {
        // 创建 WishService 实例
        WishService wishService = new WishService();



        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 255, 255, 200));
        topPanel.setBounds(525, 60, 250, 70);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // 使用 BoxLayout 排列垂直方向

        lblCurrentMoney = new JLabel("Current Money: $" + totalBalance);
        lblCurrentMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblCurrentMoney);

        lblTotalGoal = new JLabel("Total Target: $" + totalTarget);
        lblTotalGoal.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblTotalGoal);

        add(topPanel);



        add(topPanel);

        // 在页面中下方添加一个大框，里面有三个按钮
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

                new TaskPage().setVisible(true);
                dispose();
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
            // 打印当前遍历的账户的用户ID和余额
            System.out.println("Wish User ID: " + wish.getChildId() + ", Target: " + wish.getWishTarget());

            if (wish.getChildId() == childId) {
                totalTargetValue += Double.parseDouble(wish.getWishTarget());
            }
        }
        // 格式化为两位小数
        String formattedTotal = String.format("%.2f", totalTargetValue);

        // 打印计算后的总余额
        System.out.println("Total Target: " + formattedTotal);

        // 更新UI显示
        lblTotalGoal.setText("Total Target: $" + formattedTotal);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
    }
}

