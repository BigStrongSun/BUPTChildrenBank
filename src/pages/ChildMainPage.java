package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.TempService;
import util.BtnOrange;
import util.GradientBackground;
import util.JSONController;
import domain.Account;
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

    double totalBalance = 0.0;

    public ChildMainPage(int childId, JFrame previousPage) {
        this.previousPage = previousPage;

        tempService = new TempService();
        parentId = tempService.getTemp().getParentId();
        childId = tempService.getTemp().getChildId();
        childName = String.valueOf(tempService.getTemp().getChildId());
        parentName = String.valueOf(tempService.getTemp().getParentId());

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

        lblTotalGoal = new JLabel("Total Goal: $500");
        lblTotalGoal.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblTotalGoal);

        add(topPanel);

        lblChildName.setText(lblChildName.getText() + "'s Homepage");
        lblChildName.setFont(new Font("Arial", Font.BOLD, 18));
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
        totalBalance = 0.0;
        for (Account account : accounts) {
            // 打印当前遍历的账户的用户ID和余额
            System.out.println("Account User ID: " + account.getUserId() + ", Account Balance: " + account.getBalance());

            if (account.getUserId() == childId) {
                totalBalance += account.getBalance();
            }
        }
        // 打印计算后的总余额
        System.out.println("Total Balance: " + totalBalance);

        // 更新UI显示
        lblCurrentMoney.setText("Current Money: $" + totalBalance);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainPage = new MainPage();
            mainPage.setVisible(true);

            ChildMainPage childMainPage = new ChildMainPage(1, mainPage);
            childMainPage.setVisible(true);
            mainPage.setVisible(false);
        });
    }
}
