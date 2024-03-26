package util;

import pages.TaskCreateAndModifyPage;
import service.TaskService;
import service.TempService;
import util.PageSwitcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * The util.TaskComponent class extends JPanel and represents a graphical component
 * that displays information about a task. It provides visual representation
 * for task details such as task name, start time, end time, task status, and money.
 * It also allows for interaction by clicking on the component to switch to a
 * task creation and modification page.
 *
 * @author Yuxinyue Qian
 */
public class TaskComponent extends JPanel {
    private TaskService taskService = new TaskService();
    private JFrame mainMenuFrame; // MainMenuFrame的实例
    TempService tempService = new TempService();
    private int parentId;//父母的Id
    private int childId;//孩子的Id

    /**
     * Constructs a util.TaskComponent object with the specified task ID, main menu frame,
     * and flags indicating whether the task is viewed by parents and whether it can be modified.
     *
     * @param taskId        the ID of the task
     * @param mainMenuFrame the main menu frame instance
     * @param isParent      true if the task is viewed by parents, false otherwise
     * @param isModify      true if the task can be modified, false otherwise
     */
    public TaskComponent(int taskId, JFrame mainMenuFrame, boolean isParent, boolean isModify) {
        parentId=tempService.getTemp().getParentId();
        childId=tempService.getTemp().getChildId();
        this.mainMenuFrame = mainMenuFrame;
        String taskName = taskService.getTaskById(taskId).getTaskName();
        taskName = (taskName == null || taskName == "") ? "？？？" : taskName;
        String startTime = taskService.getTaskById(taskId).getStartTime();
        startTime = (startTime == null || startTime == "") ? "？？？" : startTime;
        String endTime = taskService.getTaskById(taskId).getEndTime();
        endTime = (endTime == null || endTime == "") ? "？？？" : endTime;
        String taskStatus = taskService.getTaskById(taskId).getTaskStatus();
        taskStatus = (taskStatus == null || taskStatus == "") ? "undone" : taskStatus;
        String money = taskService.getTaskById(taskId).getMoney();
        money = (money == null || money == "") ? "？？？" : money;

        // 设置主面板的布局为水平排列
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(255, 235, 156)); // 背景色设置为浅黄色
        //        module3.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        //创建模块0_1，包含 startTime 和 EndTime
        JPanel module0_1 = new JPanel();
        module0_1.setLayout(new FlowLayout(FlowLayout.CENTER));
        module0_1.setPreferredSize(new Dimension(400, 20));
        module0_1.add(new JLabel(startTime));
        module0_1.add(new JLabel("-"));
        module0_1.add(new JLabel(endTime));
        module0_1.setOpaque(false);
//        module0_1.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        //创建模块0_2，包含taskName
        JPanel module0_2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        module0_2.setPreferredSize(new Dimension(400, 20));
        module0_2.add(new JLabel(taskName));
        module0_2.setOpaque(false);

        // 创建模块1并添加到主面板，模块1内部垂直排列taskName和time
        JPanel module1 = new JPanel();
        module1.setLayout(new BoxLayout(module1, BoxLayout.Y_AXIS));
        module1.setPreferredSize(new Dimension(400, 50));
        module1.add(module0_2);
        module1.add(module0_1);
        module1.setOpaque(false); // 设置透明背景
//        module1.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        // 创建模块2并添加到主面板，包含status
//        JPanel module2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel module2 = new JPanel();
        module2.setLayout(new BoxLayout(module2, BoxLayout.Y_AXIS));
        module2.setPreferredSize(new Dimension(50, 50));
        JLabel statusLabel = new JLabel(taskStatus);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        module2.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        // 根据taskStatus的值设置前景色
        if (taskStatus.equals("done")) {
            statusLabel.setForeground(new Color(19, 133, 78));
        } else if (taskStatus.equals("undone")) {
            statusLabel.setForeground(Color.RED);
        }

        // 将JLabel添加到JPanel中
        module2.add(statusLabel);
        module2.setOpaque(false); // 设置透明背景

        // 创建模块3并添加到主面板，包含money
        JPanel module3 = new JPanel();
        module3.setLayout(new BoxLayout(module3, BoxLayout.Y_AXIS));
        module3.setPreferredSize(new Dimension(50, 50));
        JLabel moneyLabel = new JLabel(money);
        moneyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        module3.add(moneyLabel);
        module3.setOpaque(false); // 设置透明背景
//        module3.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        // 将各模块添加到主面板
        add(Box.createHorizontalStrut(20));
        add(module1);
        add(Box.createHorizontalStrut(20));
        add(module2);
        add(Box.createHorizontalStrut(50)); // 添加间距
        add(module3);
        add(Box.createHorizontalStrut(50));

        // 添加鼠标点击事件监听器
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // 在点击时执行页面切换操作
                PageSwitcher.switchPages(mainMenuFrame, new TaskCreateAndModifyPage(taskId, isParent, isModify));
            }
        });

    }
}
