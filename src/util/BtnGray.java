package util;

import javax.swing.*;
import java.awt.*;

/**
 * The BtnGray class represents a custom gray-colored button component.
 */
public class BtnGray extends JButton {
    private Shape shape;

    /**
     * Constructs a new BtnGray button with the specified button name.
     *
     * @param btnName The name to be displayed on the button.
     */
    public BtnGray(String btnName) {
        super(btnName);

        Dimension size = getPreferredSize();
        size.width = 150;
        size.height = 25;
        setPreferredSize(size);

        this.setFocusable(false);
        this.setBorderPainted(true); // Display border
        this.setContentAreaFilled(false); // Do not fill content area
        this.setOpaque(true); // Opaque background
        this.setBackground(Color.LIGHT_GRAY);
        this.setForeground(Color.white);

        // Use custom UI to override default focus painting
        setUI(new CustomButtonUI());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw rounded background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // Draw text
        String text = getText();
        if (text.startsWith("<html>")) {
            // Use HTML rendering if text starts with <html>
            super.paintComponent(g2);
        } else {
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 2;
            g2.setColor(getForeground());
            g2.drawString(getText(), x, y);
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Do not draw border
    }

    private static class CustomButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        @Override
        protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
            // Do not draw focus frame
        }
    }
}
