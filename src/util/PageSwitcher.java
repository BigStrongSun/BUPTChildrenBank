package util;

import pages.TaskPage;

import javax.swing.*;

/**
 * <p>The util.PageSwitcher class provides a utility method for switching between frames in a GUI application.
 * It allows closing one frame and opening another frame, providing a simple mechanism for page navigation.
 *
 * @author Yuxinyue Qian
 */
public class PageSwitcher {
    /**
     * Switches between frames by closing the specified frame and opening another frame.
     *
     * @param frameToClose The frame to be closed.
     * @param frameToOpen  The frame to be opened.
     */
    public static void switchPages(JFrame frameToClose, JFrame frameToOpen) {
        if (frameToClose != null) {
            frameToClose.setVisible(false);
            frameToClose.dispose(); // 关闭要关闭的页面
        }
        if (frameToOpen != null) {
            frameToOpen.setVisible(true); // 显示要打开的页面
        }
        //如果返回的页面需要重新进入初始化函数，需要把这个页面关闭后再重新打开
        if (frameToOpen.getTitle().equals("pages.TaskPage")) {
            frameToOpen.dispose();
            TaskPage taskPage = new TaskPage();
            taskPage.setVisible(true);
        }

        System.out.println("关闭了" + frameToClose.getTitle() + "页面,打开了" + frameToOpen.getTitle() + "页面");
    }
}
