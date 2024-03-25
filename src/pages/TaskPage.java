package pages;

import domain.Task;
import service.TaskService;
import service.TempService;
import util.BtnOrange;
import util.JSONController;
import util.TaskComponent;
import util.TopPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The pages.TaskPage class represents the main frame of the GUI application.
 * It displays the main menu with task components, a top panel, and a south panel.
 *
 * @author Yuxinyue Qian
 */
public class TaskPage extends JFrame {
    private TaskPage taskPage;

    private List<Task> tasks;
    private JSONController json = new JSONController("task.txt");
    TaskService taskService = new TaskService();
    TempService tempService = new TempService();
    private int parentId;//父母的Id
    private int childId;//孩子的Id
    private boolean isParent;

    public TaskPage() {
        parentId = tempService.getTemp().getParentId();
        childId = tempService.getTemp().getChildId();
        isParent = tempService.getTemp().isParent();
        taskPage = this;
//        System.out.println("我进入了TaskPage的初始化函数");
        setTitle("pages.TaskPage");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // 创建顶部面板
        TopPanel topPanel = new TopPanel("Task", this, this);
        topPanel.setBackground(new Color(255, 248, 239));

        //创建底部面板
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southPanel.setPreferredSize(new Dimension(800, 77));
        southPanel.setBackground(new Color(255, 248, 239));

        // 创建"Create task"按钮
        JButton createTaskButton = new BtnOrange("Create task");
        createTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TaskCreateAndModifyPage window = new TaskCreateAndModifyPage(taskService.getMaxTaskId() + 1, true, false);
                window.setVisible(true);
//                JFrame taskPage = (JFrame) SwingUtilities.getWindowAncestor(SouthPanel.this);
                taskPage.dispose();
            }
        });

        if (isParent) {
            southPanel.add(createTaskButton);
        }

        // 创建中央面板
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 248, 239));

        //添加内容项(读文件，将文件中的日期在选中日期的项数提取出来，循环）
        tasks = json.readArray(Task.class);
        if (tasks != null) {
            Collections.sort(tasks, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    return o2.getStartTime().compareTo(o1.getStartTime());
                }
            });
            for (Task task : tasks) {
                if (task.getParentId() == parentId && task.getChildId() == childId) {
                    TaskComponent taskComponent = new TaskComponent(task.getTaskId(), this, isParent, true);
                    contentPanel.add(taskComponent);
                }
            }
        }

        // 将顶部面板、左侧面板和内容面板添加到主面板
        mainPanel.add(topPanel, BorderLayout.NORTH);
//        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        if (isParent) {
            mainPanel.add(southPanel, BorderLayout.SOUTH);
        }
    }
}