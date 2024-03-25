package util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * <p>The util.BtnGray class represents a custom gray-colored button component.
 *
 * @author Yuxinyue Qian
 */
public class BtnGray extends JButton {
    private Shape shape;

    /**
     * Constructs a new util.BtnGray button with the specified button name.
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
        this.setBorderPainted(true); // 不显示边框
        this.setContentAreaFilled(false); // 不填充内容区域
        this.setOpaque(true); // 不透明背景
        this.setBackground(Color.LIGHT_GRAY);
        this.setForeground(Color.white);
    }
}

