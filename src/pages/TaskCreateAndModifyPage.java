package pages;

import domain.Task;
import service.TaskService;
import service.TempService;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represents a JFrame for creating and modifying tasks.
 *
 * @author Yuxinyue Qian
 *
 */
public class TaskCreateAndModifyPage extends JFrame {
    private TaskCreateAndModifyPage taskCreateAndModifyPage;
    private TaskService taskService = new TaskService();
    TempService tempService = new TempService();
    private int parentId;//父母的Id
    private int childId;//孩子的Id

    private JTextField textField_taskName;
    private JTextArea textArea_taskDescription;
    private JTextField textField_startTime;
    private JTextField textField_endTime;
    private JTextField textField_money;

    private JButton btnCreate;
    private JButton btnModify;
    private JButton btnDelete;
    private JButton btnConfirm;
    private JButton btnBack = new JButton("");

    private JLabel lblChildName;
    private JLabel lblTaskName;
    private JLabel lblTaskDescription;
    private JLabel lblStartTime;
    private JLabel lblEndTime;
    private JLabel lblMoney;

    /**
     Constructs a new pages.TaskCreateAndModifyPage object with the given taskId, parent flag, and modification flag.
     @param taskId the ID of the task
     @param isParent true if the user is a parent, false otherwise
     @param isModify true if the task can be modified, false otherwise
     */
    public TaskCreateAndModifyPage(int taskId, boolean isParent, boolean isModify) {
        initialize(taskId, isParent, isModify);
    }

    private void initialize(int taskId, boolean isParent, boolean isModify) {
        parentId=tempService.getTemp().getParentId();
        childId=tempService.getTemp().getChildId();
        taskCreateAndModifyPage =this;
//        System.out.println("我进入了TaskCreateAndModify页面的初始化函数");
        setTitle("pages.TaskCreateAndModifyPage");
        getContentPane().setBackground(new Color(255, 248, 239));
        setBounds(0, 0, 1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        TopPanel topPanel = new TopPanel("Task Detail", this, new TaskPage());
        topPanel.setBackground(new Color(255, 248, 239));
        topPanel.setBounds(0, 0, 1280, 50);
        getContentPane().add(topPanel);

        lblTaskName = new JLabel("Task Name");
        lblTaskName.setForeground(new Color(0, 0, 0));
        lblTaskName.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTaskName.setBounds(136, 120, 300, 28);
        getContentPane().add(lblTaskName);

        lblTaskDescription = new JLabel("Task Description");
        lblTaskDescription.setForeground(new Color(0, 0, 0));
        lblTaskDescription.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTaskDescription.setBounds(136, 190, 300, 28);
        getContentPane().add(lblTaskDescription);

        lblStartTime = new JLabel("Time Start");
        lblStartTime.setForeground(new Color(0, 0, 0));
        lblStartTime.setFont(new Font("Arial", Font.PLAIN, 20));
        lblStartTime.setBounds(136, 300, 300, 28);
        getContentPane().add(lblStartTime);

        lblEndTime = new JLabel("Time End");
        lblEndTime.setForeground(new Color(0, 0, 0));
        lblEndTime.setFont(new Font("Arial", Font.PLAIN, 20));
        lblEndTime.setBounds(136, 370, 300, 28);
        getContentPane().add(lblEndTime);

        lblMoney = new JLabel("Money");
        lblMoney.setForeground(new Color(0, 0, 0));
        lblMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        lblMoney.setBounds(136, 440, 300, 28);
        getContentPane().add(lblMoney);

        if (taskId == taskService.getMaxTaskId() + 1) {//说明是点击“Create Task”按钮进来的，这是不能用getTaskById，因为会新建Task
            textField_taskName = new JTextField();
            textArea_taskDescription = new JTextArea();
            textField_startTime = new JTextField();
            textField_endTime = new JTextField();
            textField_money = new JTextField();
        } else {//说明是点击TaskComponent进来的
            textField_taskName = new JTextField(taskService.getTaskById(taskId).getTaskName());
            textArea_taskDescription = new JTextArea(taskService.getTaskById(taskId).getTaskDescription());
            textField_startTime = new JTextField(taskService.getTaskById(taskId).getStartTime());
            textField_endTime = new JTextField(taskService.getTaskById(taskId).getEndTime());
            textField_money = new JTextField(taskService.getTaskById(taskId).getMoney());
        }
        if (!isParent) {//如果是孩子身份的话，限制不能修改
            textField_taskName.setEditable(false);
            textArea_taskDescription.setEditable(false);
            textField_startTime.setEditable(false);
            textField_endTime.setEditable(false);
            textField_money.setEditable(false);
        }
        textField_taskName.setBounds(400, 120, 600, 37);
        getContentPane().add(textField_taskName);
        textField_taskName.setColumns(10);

        textArea_taskDescription.setBounds(400, 190, 600, 80);
        getContentPane().add(textArea_taskDescription);
        textArea_taskDescription.setColumns(10);
        textArea_taskDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));


        textField_startTime.setBounds(400, 300, 600, 37);
        getContentPane().add(textField_startTime);
        textField_startTime.setColumns(10);
        if (isParent) {
            textField_startTime.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 处理鼠标点击事件
                    TimePicker timePicker = new TimePicker();
                    timePicker.setVisible(true);

                    timePicker.setTimeSelectionListener(new TimeSelectionListener() {
                        @Override
                        public void onTimeSelected(String timeSelected) {
                            System.out.println("timeSelected:" + timeSelected);
                            textField_startTime.setText(timeSelected);  // 更新文本框内容
                        }
                    });
                }
            });
        }

        textField_endTime.setBounds(400, 370, 600, 37);
        getContentPane().add(textField_endTime);
        textField_endTime.setColumns(10);
        if (isParent) {
            textField_endTime.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 处理鼠标点击事件
                    TimePicker timePicker = new TimePicker();
                    timePicker.setVisible(true);

                    timePicker.setTimeSelectionListener(new TimeSelectionListener() {
                        @Override
                        public void onTimeSelected(String timeSelected) {
                            System.out.println("timeSelected:" + timeSelected);
                            textField_endTime.setText(timeSelected);  // 更新文本框内容
                        }
                    });
                }
            });
        }

        textField_money.setBounds(400, 440, 600, 37);

        getContentPane().add(textField_money);
        textField_money.setColumns(10);

        btnCreate = new BtnOrange("Create Task");
        btnCreate.setBounds(535, 600, 150, 25);

        btnModify = new BtnOrange("Modify Task");
        btnModify.setBounds(150, 600, 150, 25);

        btnDelete = new BtnGray("Delete Task");
        btnDelete.setBounds(500, 600, 150, 25);

        btnConfirm = new BtnOrange("Confirm finished");
        btnConfirm.setBounds(850, 600, 150, 25);

        if (!isModify && isParent) {
            getContentPane().add(btnCreate);
        }
        if (isModify && isParent) {
            getContentPane().add(btnModify);
            getContentPane().add(btnDelete);
            getContentPane().add(btnConfirm);
        }

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int option = JOptionPane.showConfirmDialog(null, "是否确认创建任务?", "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    if(isValidated()){
                        Task task = new Task();
                        task.setParentId(parentId);
                        task.setChildId(childId);
                        task.setTaskId(taskId);
                        task.setTaskName(String.valueOf(textField_taskName.getText()));
                        task.setTaskDescription(String.valueOf(textArea_taskDescription.getText()));
                        task.setStartTime(String.valueOf(textField_startTime.getText()));
                        task.setEndTime(String.valueOf(textField_endTime.getText()));
                        task.setMoney(String.valueOf(textField_money.getText()));
                        taskService.modifyTask(task);
                        PageSwitcher.switchPages(taskCreateAndModifyPage,new TaskPage());
                    }
                }
            }
        });

        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int option = JOptionPane.showConfirmDialog(null, "是否确认修改任务?", "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    if(isValidated()){
                        Task task = new Task();
                        task.setParentId(parentId);
                        task.setChildId(childId);
                        task.setTaskId(taskId);
                        task.setTaskName(String.valueOf(textField_taskName.getText()));
                        task.setTaskDescription(String.valueOf(textArea_taskDescription.getText()));
                        task.setStartTime(String.valueOf(textField_startTime.getText()));
                        task.setEndTime(String.valueOf(textField_endTime.getText()));
                        task.setMoney(String.valueOf(textField_money.getText()));
                        task.setTaskStatus(taskService.getTaskById(taskId).getTaskStatus());
                        taskService.modifyTask(task);
                        PageSwitcher.switchPages(taskCreateAndModifyPage,new TaskPage());
                    }
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int option = JOptionPane.showConfirmDialog(null, "是否确认删除任务?", "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    taskService.deleteTask(taskId);
                    PageSwitcher.switchPages(taskCreateAndModifyPage,new TaskPage());
                }

            }
        });

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int option = JOptionPane.showConfirmDialog(null, "是否确认完成任务?", "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    Task task = new Task();
                    task.setTaskId(taskId);
                    task.setTaskStatus("done");
                    taskService.modifyTaskStatus(task);
                    PageSwitcher.switchPages(taskCreateAndModifyPage,new TaskPage());
                }
            }
        });
    }
    public boolean isValidated() {
        boolean isValidated = true;
        String type=null;
        LocalDateTime endTime = LocalDateTime.parse(textField_endTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime startTime = LocalDateTime.parse(textField_startTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if(textField_taskName.getText()==null|| textField_taskName.getText().isEmpty()){
            isValidated = false;
            type="empty";
        }
        if(textField_startTime.getText()==null|| textField_startTime.getText().isEmpty()){
            isValidated = false;
            type="empty";
        }
        if(textField_endTime.getText()==null|| textField_endTime.getText().isEmpty()){
            isValidated = false;
            type="empty";
        }        
        if(textField_money.getText()==null|| textField_money.getText().isEmpty()){
            isValidated = false;
            type="empty";
        }
        if(startTime.isAfter(endTime)){
            isValidated=false;
            type="invalid_date";
        }

        if(!isValidated){
            if(type.equals("empty")){
                JOptionPane.showMessageDialog(null, "You should not create empty inputs", "Alert",JOptionPane.WARNING_MESSAGE);
            }
            if(type.equals("invalid_date")){
                JOptionPane.showMessageDialog(null, "start date ate should not be after end date", "Alert",JOptionPane.WARNING_MESSAGE);
            }
        }
        return isValidated;
    }
}
