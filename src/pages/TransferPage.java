package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import util.BtnOrange;
import util.GradientBackground;

public class TransferPage extends JFrame {
    private JTextField textFieldAccountNumber;
    private JTextField textFieldAmount;
    private JPasswordField passwordField;

    // 模拟的数据
    private int childId = 2021211111;

    public TransferPage() {
        setTitle("Transfer Page");
        setSize(1280, 720); // 设置页面大小为1280x720
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.4f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);
        setLayout(null);


        // 创建一个小的 JPanel 放置标题
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 248, 239,0)); // 设置橙色背景
        titlePanel.setBounds(100, 100, 1000, 50); // 调整位置和大小
        add(titlePanel);

        // 在小的 JPanel 中添加标题
        JLabel lblTitle = new JLabel("Transfer Page");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 35)); // 设置字体
        titlePanel.add(lblTitle);

        // 创建一个大的 JPanel 放置输入框
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255, 100));

        mainPanel.setBounds(100, 200, 1000, 400); // 调整位置和大小
        mainPanel.setLayout(null); // 使用 null 布局


//        JLabel lblAccountNumber =  new JLabel("Account- " + childId + "         " + ">>>>>>"+"            "+ "Account-");
//        lblAccountNumber.setFont(new Font("Arial", Font.PLAIN, 24)); // 设置字体
//        lblAccountNumber.setBounds(180, 30, 2000, 30); // 调整位置和大小
//        mainPanel.add(lblAccountNumber);
//
//        String[] accountOptions = {"2021212222", "2021213333", "2021214444","2021215555"}; // 假设这是您要提供的选项
//
//        JComboBox<String> accountComboBox = new JComboBox<>(accountOptions);
//        accountComboBox.setFont(new Font("Arial", Font.PLAIN, 24)); // 设置字体
//        accountComboBox.setBounds(740, 30, 200, 30); // 调整位置和大小
//        accountComboBox.setBackground(new Color(255,255,255,50)); // 设置背景色为灰
//        mainPanel.add(accountComboBox);

        JLabel lblAccountNumber =  new JLabel("Account- " + childId + "         " + ">>>>>>"+"            "+ "Account-");
        lblAccountNumber.setFont(new Font("Arial", Font.PLAIN, 24)); // 设置字体
        lblAccountNumber.setBounds(180, 30, 2000, 30); // 调整位置和大小
        mainPanel.add(lblAccountNumber);

        textFieldAccountNumber = new JTextField();
        textFieldAccountNumber.setBounds(740, 30, 200, 30); // 调整位置和大小
        mainPanel.add(textFieldAccountNumber);

        JLabel lblAmount = new JLabel("Transfer Amount");
        lblAmount.setFont(new Font("Arial", Font.PLAIN, 24)); // 设置字体
        lblAmount.setBounds(300, 80, 200, 30); // 调整位置和大小
        mainPanel.add(lblAmount);

        textFieldAmount = new JTextField();
        textFieldAmount.setBounds(550, 80, 200, 30); // 调整位置和大小
        mainPanel.add(textFieldAmount);

        JLabel lblPassword = new JLabel("Password confirm");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 24)); // 设置字体
        lblPassword.setBounds(300, 140, 200, 30); // 调整位置和大小
        mainPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(520, 140, 250, 30); // 调整位置和大小
        mainPanel.add(passwordField);

        JButton btnTransfer = new BtnOrange("Transfer");
        btnTransfer.setFont(new Font("Arial", Font.PLAIN, 24)); // 设置字体
        btnTransfer.setBounds(400, 300, 200, 50); // 调整位置和大小
        btnTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在这里添加转账逻辑
                String accountNumber = textFieldAccountNumber.getText();
                String amount = textFieldAmount.getText();
                String password = String.valueOf(passwordField.getPassword());

                // 在这里处理转账逻辑，比如验证密码、检查金额等
                // 然后进行转账操作
                // 如果转账成功，可以添加相应的提示或者跳转到其他页面
                // 如果转账失败，也需要给出相应的提示
            }
        });
        mainPanel.add(btnTransfer); // 将按钮添加到大的 JPanel 中

        add(mainPanel);

//        JButton btnTransfer = new BtnOrange("Transfer");
//        btnTransfer.setFont(new Font("Arial", Font.PLAIN, 24)); // 设置字体
//        btnTransfer.setBounds(550, 500, 200, 50); // 调整位置和大小
//        btnTransfer.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // 在这里添加转账逻辑
//                String accountNumber = textFieldAccountNumber.getText();
//                String amount = textFieldAmount.getText();
//                String password = String.valueOf(passwordField.getPassword());
//
//                // 在这里处理转账逻辑，比如验证密码、检查金额等
//                // 然后进行转账操作
//                // 如果转账成功，可以添加相应的提示或者跳转到其他页面
//                // 如果转账失败，也需要给出相应的提示
//            }
//        });
//        add(btnTransfer);

        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TransferPage transferPage = new TransferPage();
            transferPage.setVisible(true);
        });
    }
}
