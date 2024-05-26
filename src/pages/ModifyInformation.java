package pages;


import util.BtnOrange;
import util.GradientBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ModifyInformation extends JFrame {
    public ModifyInformation() {
        setTitle("Modify Information");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set gradient background
        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.4f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);
        setLayout(new GridBagLayout());  // More flexible layout manager

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = GridBagConstraints.REMAINDER; // Each component in its own row
        constraints.fill = GridBagConstraints.HORIZONTAL;     // Stretch component horizontally
        constraints.insets = new Insets(10, 50, 10, 50);      // Top, left, bottom, right padding


//        JButton backButton = new JButton("Back");
//        backButton.setFocusable(false);
//        backButton.setContentAreaFilled(false);
//        backButton.setOpaque(true);
//        backButton.setBounds(0, 0, 80, 30);
//        add(backButton); // 直接添加到 JFrame
//        backButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // 假设 MainPage 是另一个 JFrame 类
//                setVisible(false); // 隐藏当前窗口
//                dispose();
//                new MainPage().setVisible(true); // 显示主页面
//            }
//        });

        // Button to open Change Child Page
        JButton btnChangeChild = new BtnOrange("Change Child");
        btnChangeChild.setPreferredSize(new Dimension(300, 50));
        btnChangeChild.addActionListener(e -> {
            dispose();
            ChangeChildPage changeChildPage = new ChangeChildPage();
            changeChildPage.setVisible(true);
        });

        // Button to open Change Name Page
        JButton btnChangeName = new BtnOrange("Change Name");
        btnChangeName.setPreferredSize(new Dimension(300, 50));
        btnChangeName.addActionListener(e -> {
            dispose();
            ChangeNamePage changeNamePage = new ChangeNamePage();
            changeNamePage.setVisible(true);
        });

        // Button to open Change Password Page
        JButton btnChangePassword = new BtnOrange("Change Password");
        btnChangePassword.setPreferredSize(new Dimension(300, 50));
        btnChangePassword.addActionListener(e -> {
            dispose();
            ChangePasswordOrNamePage changePasswordOrNamePage = new ChangePasswordOrNamePage();
            changePasswordOrNamePage.setVisible(true);
        });

        // Adding buttons to JFrame with constraints
        add(btnChangeChild, constraints);
        add(btnChangeName, constraints);
        add(btnChangePassword, constraints);

        // Centralizing the JFrame
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ModifyInformation frame = new ModifyInformation();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
