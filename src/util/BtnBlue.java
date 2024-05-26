package util;

import javax.swing.*;
import java.awt.*;


public class BtnBlue extends JButton {

    public BtnBlue(String btnName){
        super(btnName);

        Dimension size = getPreferredSize();
        size.width=150;
        size.height=25;
        setPreferredSize(size);

        this.setFocusable(false);
        this.setBorderPainted(false); // 不显示边框
        this.setContentAreaFilled(true); // 不填充内容区域
        this.setOpaque(true); // 不透明背景
        this.setBackground(new Color(215, 231, 252));
        this.setForeground(new Color(62,90,206, 204));
    }
}
