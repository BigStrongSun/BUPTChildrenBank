package util;
import javax.swing.*;
import java.awt.*;

public class BalancePanel extends JPanel {
    private JLabel balanceLabel;
    private JLabel amountLabel;

    public BalancePanel(String balanceText, String amount, Color amountColor) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setOpaque(false);  // 设置背景透明

        // 图标
        ImageIcon coinIcon = new ImageIcon(new ImageIcon("src/images/icon.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)); // 替换为实际的图标路径
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
}
