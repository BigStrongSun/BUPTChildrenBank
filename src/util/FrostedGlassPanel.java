package util;
import javax.swing.*;
import java.awt.*;

public class FrostedGlassPanel extends JPanel {
    private final int radius;

    public FrostedGlassPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 设置透明度较高的半透明背景
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 调整为需要的透明度
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();
    }
}
