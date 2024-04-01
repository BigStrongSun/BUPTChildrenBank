package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import util.BtnOrange;
public class WelcomePage extends JFrame {
    public WelcomePage() {
        setTitle("BUPT Virtual Bank");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 248, 239));
        setLayout(null); // 使用绝对布局

        JLabel lblTitle = new JLabel("Welcome to BUPT Virtual Bank!");
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 30));
        lblTitle.setBounds(420, 100, 500, 50); // 居中显示
        add(lblTitle);

        JTextArea txtDescription = new JTextArea("This is a virtual bank app designed specifically for children, aimed at helping them learn financial literacy while managing and using their pocket money through virtual accounts. We are committed to fostering children's financial awareness and helping them learn the correct ways of saving and spending.");
        txtDescription.setFont(new Font("Arial", Font.PLAIN, 20));
        txtDescription.setBounds(300, 200, 700, 300);
        txtDescription.setEditable(false); // 不可编辑
        txtDescription.setLineWrap(true); // 自动换行
        txtDescription.setWrapStyleWord(true); // 在单词之间换行
        add(txtDescription);

        JButton btnStart = new BtnOrange("Start Now");
        btnStart.setFont(new Font("Arial", Font.PLAIN, 25));
        btnStart.setBounds(500, 550, 280, 50);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 点击按钮后，打开另一个页面
                dispose(); // 关闭当前页面
                new MainPage().setVisible(true); // 打开任务页面
            }
        });
        add(btnStart);

        setLocationRelativeTo(null); // 居中显示
        setResizable(false); // 禁止调整大小
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomePage welcomePage = new WelcomePage();
            welcomePage.setVisible(true);
        });
    }
}

//class BtnOrange extends JButton {
//    public BtnOrange(String text) {
//        super(text);
//        setBackground(new Color(255, 169, 32)); // 设置按钮的背景色为橙色
//    }
//}
