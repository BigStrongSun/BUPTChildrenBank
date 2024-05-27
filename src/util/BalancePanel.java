package util;

import pages.WalletPage;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BalancePanel extends JPanel {
    private JLabel balanceLabel;
    private JLabel amountLabel;

    public BalancePanel(String balanceText, String amount, Color amountColor) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setOpaque(false);  // 设置背景透明

        // 图标
        URL resourceUrl = WalletPage.class.getClassLoader().getResource("images/icon.png");
        ImageIcon originalIcon = new ImageIcon(resourceUrl);
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon coinIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(coinIcon);

        // 文本标签
        balanceLabel = new JLabel(balanceText);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // 金额标签
        amountLabel = new JLabel(amount);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        amountLabel.setForeground(amountColor);  // 设置金额颜色

        // 添加组件到面板
        add(iconLabel);
        add(balanceLabel);
        add(amountLabel);
    }

    // 允许动态设置金额
    public void setAmount(String amount) {
        amountLabel.setText(amount);
    }

    // 允许动态更改金额颜色
    public void setAmountColor(Color color) {
        amountLabel.setForeground(color);
    }

    // 更新余额的方法
    public void updateBalance(String amount) {
        setAmount(amount);
    }
}
