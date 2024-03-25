package util;

import javax.swing.*;
import java.awt.*;

/**
 * <p>The util.BtnOrange class represents a custom orange-colored button component.
 *
 * @author Yuxinyue Qian
 */
public class BtnOrange extends JButton {

    /**
     * Constructs a new util.BtnOrange button with the specified button name.
     *
     * @param btnName The name to be displayed on the button.
     */
    public BtnOrange(String btnName){
        super(btnName);

        Dimension size = getPreferredSize();
        size.width=150;
        size.height=25;
        setPreferredSize(size);

        this.setFocusable(false);
        this.setBorderPainted(true); // 不显示边框
        this.setContentAreaFilled(false); // 不填充内容区域
        this.setOpaque(true); // 不透明背景
        this.setBackground(new Color(225, 129, 9));
        this.setForeground(Color.white);
    }
}
