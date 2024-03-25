package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>The util.TopPanel class represents a custom JPanel component that serves as the top panel in a GUI application.
 * It includes a back button and a task label, allowing users to navigate back and display the current task name.
 *
 * @author Yuxinyue Qian
 */
public class TopPanel extends JPanel {
    private JFrame frameToClose;
    private JFrame frameToOpen;

    /**
     * Constructs a new util.TopPanel with the specified task name, frame to close, and frame to open.
     *
     * @param name         The name of the current task.
     * @param frameToClose The frame to be closed when the back button is clicked.
     * @param frameToOpen  The frame to be opened when the back button is clicked.
     */
    public TopPanel(String name, JFrame frameToClose, JFrame frameToOpen) {
        this.frameToClose = frameToClose;
        this.frameToOpen = frameToOpen;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 50)); // 设置面板的首选大小

        // 创建返回按钮
        JButton backButton = new JButton("Back");
        backButton.setFocusable(false); // 移除按钮的焦点框
        backButton.setBorder(BorderFactory.createEmptyBorder(-5, 15, -5, 15)); // 设置边距
//        backButton.setBorderPainted(true); // 不显示边框
        backButton.setContentAreaFilled(false); // 不填充内容区域
        backButton.setOpaque(true); // 不透明背景

        // 添加返回按钮的点击事件监听器
        backButton.addActionListener(new BackButtonListener());

        // 创建任务标签
        JLabel taskLabel = new JLabel(name);
        taskLabel.setForeground(new Color(0, 0, 0));
        taskLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        taskLabel.setHorizontalAlignment(SwingConstants.CENTER);
        taskLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100)); // 设置边距

        // 添加返回按钮和任务标签到顶部面板
        add(backButton, BorderLayout.WEST);
        add(taskLabel, BorderLayout.CENTER);

    }

    /**
     * ActionListener implementation for the back button.
     * Handles the action event when the back button is clicked.
     */
    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("返回按钮被点击了");

            PageSwitcher.switchPages(frameToClose, frameToOpen);
        }
    }

}