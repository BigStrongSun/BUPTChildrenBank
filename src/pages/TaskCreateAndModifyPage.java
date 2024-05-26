package pages;

import domain.*;
import service.AccountService;
import service.TaskService;
import service.TempService;
import service.WalletService;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class represents a JFrame for creating and modifying tasks.
 *
 * @author Yuxinyue Qian
 */
public class TaskCreateAndModifyPage extends JFrame {
    private JSONController jsonAccount = new JSONController("account.txt");
    private JSONController jsonTransation = new JSONController("transaction.txt");
    private TaskCreateAndModifyPage taskCreateAndModifyPage;
    private TaskService taskService = new TaskService();
    TempService tempService = new TempService();
    AccountService accountService = new AccountService();
    private int parentId;//父母的Id
    private int childId;//孩子的Id
    private List<Account> accounts;
    private WalletService walletService = new WalletService();
    private List<Transaction> transactions;
    private JSONController jsonTrans = new JSONController("transaction.txt");

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

    Object[] options = {"Yes", "No"};
    Object[] optionsConfirm = {"confirm"};

    /**
     * Constructs a new pages.TaskCreateAndModifyPage object with the given taskId, parent flag, and modification flag.
     *
     * @param taskId   the ID of the task
     * @param isParent true if the user is a parent, false otherwise
     * @param isModify true if the task can be modified, false otherwise
     */
    public TaskCreateAndModifyPage(int taskId, boolean isParent, boolean isModify) {
        initialize(taskId, isParent, isModify);
    }

    private void initialize(int taskId, boolean isParent, boolean isModify) {
        parentId = tempService.getTemp().getParentId();
        childId = tempService.getTemp().getChildId();
        taskCreateAndModifyPage = this;
        transactions = jsonTrans.readArray(Transaction.class);
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

        lblMoney = new JLabel("Money($)");
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
            textField_money = new JTextField(String.valueOf(taskService.getTaskById(taskId).getMoney()));
            String value = textField_money.getText();
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
                int option = JOptionPane.showOptionDialog(null, "Do you confirm to create a task?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (option == JOptionPane.YES_OPTION) {
                    if (isValidated()) {
                        Task task = new Task();
                        task.setParentId(parentId);
                        task.setChildId(childId);
                        task.setTaskId(taskId);
                        task.setTaskName(String.valueOf(textField_taskName.getText()));
                        task.setTaskDescription(String.valueOf(textArea_taskDescription.getText()));
                        task.setStartTime(String.valueOf(textField_startTime.getText()));
                        task.setEndTime(String.valueOf(textField_endTime.getText()));
                        task.setMoney(Double.parseDouble(textField_money.getText()));
                        taskService.modifyTask(task);
                        PageSwitcher.switchPages(taskCreateAndModifyPage, new TaskPage());
                    }
                }
            }
        });

        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int option = JOptionPane.showOptionDialog(null, "Do you confirm to modify a task?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (option == JOptionPane.YES_OPTION) {
                    if (isValidated()) {
                        Task task = new Task();
                        task.setParentId(parentId);
                        task.setChildId(childId);
                        task.setTaskId(taskId);
                        task.setTaskName(String.valueOf(textField_taskName.getText()));
                        task.setTaskDescription(String.valueOf(textArea_taskDescription.getText()));
                        task.setStartTime(String.valueOf(textField_startTime.getText()));
                        task.setEndTime(String.valueOf(textField_endTime.getText()));
                        task.setMoney(Double.parseDouble(textField_money.getText()));
                        task.setTaskStatus(taskService.getTaskById(taskId).getTaskStatus());
                        taskService.modifyTask(task);
                        PageSwitcher.switchPages(taskCreateAndModifyPage, new TaskPage());
                    }
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int option = JOptionPane.showOptionDialog(null, "Do you confirm to delete a task?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (option == JOptionPane.YES_OPTION) {
                    taskService.deleteTask(taskId);
                    PageSwitcher.switchPages(taskCreateAndModifyPage, new TaskPage());
                }

            }
        });

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int option = JOptionPane.showOptionDialog(null, "Do you want to confirm a task finished?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (option == JOptionPane.YES_OPTION) {
                    Task task = new Task();
                    task.setTaskId(taskId);
                    task.setTaskStatus("done");

                    accounts = jsonAccount.readArray(Account.class);
                    for (Account account : accounts) {
                        if (account.getUserId() == childId && account.getAccountType().equals(AccountType.CURRENT_ACCOUNT)) {
                            int actionresult = accountService.addBalance(account.getAccountId(), Double.parseDouble(textField_money.getText()));
                            if (actionresult == 1) {
                                taskService.modifyTaskStatus(task);
                                Transaction transaction = new Transaction();
                                transaction.setType(TransactionType.BONUS);
                                transaction.setAmount(Double.parseDouble(textField_money.getText()));
                                transaction.setFee(0);
                                transaction.setSenderAccountId(accountService.getCurrentAccountByUserId(parentId).getAccountId());
                                transaction.setReceiverAccountId(account.getAccountId());
                                transaction.setDescription(textField_taskName.getText());
                                walletService.createTrans(transaction);
                                PageSwitcher.switchPages(taskCreateAndModifyPage, new TaskPage());
                                return;
                            } else {
                                System.out.println("Something wrong");
                                return;
                            }
                        }
                    }
                    System.out.println("似乎是没有绑定孩子");
                }
            }
        });
    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric2(String str) {
        return str != null && NUMBER_PATTERN.matcher(str).matches();
    }

    public boolean isValidated() {
        boolean isValidated = true;
        String type = null;
        if (textField_taskName.getText() == null || textField_taskName.getText().isEmpty()) {
            isValidated = false;
            type = "empty";
        }
        if (textField_startTime.getText() == null || textField_startTime.getText().isEmpty()) {
            isValidated = false;
            type = "empty";
        }
        if (textField_endTime.getText() == null || textField_endTime.getText().isEmpty()) {
            isValidated = false;
            type = "empty";
        }
        if (textField_money.getText() == null || textField_money.getText().isEmpty()) {
            isValidated = false;
            type = "empty";
        }
        if (!isNumeric2(textField_money.getText())) {
            isValidated = false;
            type = "notNumber";
        }
        if (!textField_startTime.getText().isEmpty() && !textField_endTime.getText().isEmpty()) {
            LocalDateTime endTime = LocalDateTime.parse(textField_endTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime startTime = LocalDateTime.parse(textField_startTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            if (startTime.isAfter(endTime)) {
                isValidated = false;
                type = "invalid_date";
            }
        }


        if (!isValidated) {
            if (type.equals("empty")) {
                JOptionPane.showMessageDialog(null, "You should not create empty inputs", "Alert", JOptionPane.WARNING_MESSAGE);
            }
            if (type.equals("invalid_date")) {
                JOptionPane.showMessageDialog(null, "Start date should not be after end date", "Alert", JOptionPane.WARNING_MESSAGE);
            }
            if (type.equals("notNumber")) {
                JOptionPane.showMessageDialog(null, "Money should be in Number type", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        }
        return isValidated;
    }
}
