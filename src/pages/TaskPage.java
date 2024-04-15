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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
        mainPanel.setBackground(new Color(255, 248, 239));
        add(mainPanel);

        // 创建顶部面板
        TopPanel topPanel = new TopPanel("Task", this, new MainPage());
        topPanel.setBackground(new Color(255, 248, 239));

        //创建底部面板
        JPanel southPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 30);
        flowLayout.setAlignment(FlowLayout.CENTER);
        southPanel.setLayout(flowLayout);
        southPanel.setPreferredSize(new Dimension(800, 100));
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
//        contentPanel.setOpaque(false);
        contentPanel.setBackground(new Color(255, 248, 239));
// 创建 GridBagLayout
        GridBagLayout gridBagLayout = new GridBagLayout();
        contentPanel.setLayout(gridBagLayout);

// 创建 GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0; // 列索引为 0
        constraints.gridy = GridBagConstraints.RELATIVE; // 逐行排列
        constraints.anchor = GridBagConstraints.NORTH; // 组件顶部对齐
        constraints.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        constraints.insets = new Insets(10, 0, 0, 0); // 设置间隔，顶部留出 10 像素的空白


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
                    contentPanel.add(taskComponent, constraints);
                }
            }
        }

        //左右两边的空白填充项
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(200, 1000));
        rightPanel.setOpaque(false);

        //左右两边的空白填充项
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200, 1000));
        leftPanel.setOpaque(false);

        // 将顶部面板、左侧面板和内容面板添加到主面板
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        // 创建 JScrollPane 并将 contentPanel 作为参数传递
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // 禁用水平滚动条
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
//        mainPanel.add(leftPanel,BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        if (isParent) {
            mainPanel.add(southPanel, BorderLayout.SOUTH);
        }
        // 添加组件完成布局的监听器
//        mainPanel.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                // 获取滚动面板的高度
//                int scrollPaneHeight = scrollPane.getViewport().getHeight();
//                System.out.println("滚动面板的高度：" + scrollPaneHeight);
//
//                // 获取主面板的高度
//                int mainPanelHeight = contentPanel.getHeight();
//                System.out.println("主面板的高度：" + mainPanelHeight);
//            }
//        });
    }
}