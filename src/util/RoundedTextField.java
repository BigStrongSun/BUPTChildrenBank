package util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {

    private Shape shape;

    public RoundedTextField(int size) {
        super(size);
        setOpaque(false); // 使组件背景透明
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15); // 设置圆角半径为15
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15); // 设置圆角半径为15
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        shape = new RoundRectangle2D.Float(0, 0, width - 1, height - 1, 15, 15); // 设置圆角半径为15
    }

    @Override
    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }

//    public static void main(String[] args) {
//        // 创建主框架
//        JFrame frame = new JFrame("Rounded Text Field Example");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 200);
//        frame.setLayout(new FlowLayout());
//
//        // 创建圆角文本框
//        RoundedTextField textField = new RoundedTextField(20);
//        textField.setBackground(Color.WHITE);
//        textField.setForeground(Color.BLACK);
//        textField.setPreferredSize(new Dimension(200, 30));
//
//        // 添加文本框到框架
//        frame.add(textField);
//
//        // 显示框架
//        frame.setVisible(true);
//    }
}

