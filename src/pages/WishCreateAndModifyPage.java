package pages;

import domain.Wish;
import service.TempService;
import service.WishService;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WishCreateAndModifyPage extends JFrame {
    private WishCreateAndModifyPage wishCreateAndModifyPage;
    private WishService wishService = new WishService();
    TempService tempService = new TempService();
    private int parentId;//父母的Id
    private int childId;//孩子的Id

    private JTextField textField_wishName;
    private JTextArea textArea_wishDescription;
    private JTextField textField_wishStatus;
    private JTextField textField_wishProgress;
    private JTextField textField_wishTarget;
    private JTextField textField_deadLine;

    private JButton btnCreate;
    private JButton btnModify;
    private JButton btnDelete;
    private JButton btnConfirm;
    private JButton btnBack = new JButton("");

    private JLabel lblChildName;
    private JLabel lblWishName;
    private JLabel lblWishDescription;
    private JLabel lblWishStatus;
    private JLabel lblWishProgress;
    private JLabel lblDeadLine;
    private JLabel lblWishTarget;

    /**
     * Constructs a new pages.WishCreateAndModifyPage object with the given wishId, parent flag, and modification flag.
     *
     * @param wishId   the ID of the wish
     * @param isParent true if the user is a parent, false otherwise
     * @param isModify true if the wish can be modified, false otherwise
     */
    public WishCreateAndModifyPage(int wishId, boolean isParent, boolean isModify) {
        initialize(wishId, isParent, isModify);
    }

    private void initialize(int wishId, boolean isParent, boolean isModify) {
        parentId = tempService.getTemp().getParentId();
        childId = tempService.getTemp().getChildId();
        wishCreateAndModifyPage = this;
////        System.out.println("我进入了WishCreateAndModify页面的初始化函数");
        setTitle("pages.WishCreateAndModifyPage");
        getContentPane().setBackground(new Color(255, 248, 239));
        setBounds(0, 0, 1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        TopPanel topPanel = new TopPanel("Wish Detail", this, new WishPage());
        topPanel.setBackground(new Color(255, 248, 239));
        topPanel.setBounds(0, 0, 1280, 50);
        getContentPane().add(topPanel);

        lblWishName = new JLabel("Wish Name");
        lblWishName.setForeground(new Color(0, 0, 0));
        lblWishName.setFont(new Font("Arial", Font.PLAIN, 20));
        lblWishName.setBounds(136, 120, 300, 28);
        getContentPane().add(lblWishName);

        lblWishDescription = new JLabel("Wish Description");
        lblWishDescription.setForeground(new Color(0, 0, 0));
        lblWishDescription.setFont(new Font("Arial", Font.PLAIN, 20));
        lblWishDescription.setBounds(136, 190, 300, 28);
        getContentPane().add(lblWishDescription);

        lblWishStatus = new JLabel("Wish Status");
        lblWishStatus.setForeground(new Color(0, 0, 0));
        lblWishStatus.setFont(new Font("Arial", Font.PLAIN, 20));
        lblWishStatus.setBounds(136, 300, 300, 28);
        getContentPane().add(lblWishStatus);

        lblWishProgress = new JLabel("Wish Progress");
        lblWishProgress.setForeground(new Color(0, 0, 0));
        lblWishProgress.setFont(new Font("Arial", Font.PLAIN, 20));
        lblWishProgress.setBounds(136, 370, 300, 28);
        getContentPane().add(lblWishProgress);

        lblDeadLine = new JLabel("Dead Line");
        lblDeadLine.setForeground(new Color(0, 0, 0));
        lblDeadLine.setFont(new Font("Arial", Font.PLAIN, 20));
        lblDeadLine.setBounds(136, 440, 300, 28);
        getContentPane().add(lblDeadLine);

        lblWishTarget = new JLabel("Wish Target");
        lblWishTarget.setForeground(new Color(0, 0, 0));
        lblWishTarget.setFont(new Font("Arial", Font.PLAIN, 20));
        lblWishTarget.setBounds(136, 510, 300, 28);
        getContentPane().add(lblWishTarget);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowString = now.format(formatter) + " 23:59";

        if (wishId == wishService.getMaxWishId() + 1) {//说明是点击“Create Wish”按钮进来的，这是不能用getWishById，因为会新建Wish
            textField_wishName = new JTextField();
            textArea_wishDescription = new JTextArea();
            textField_wishStatus = new JTextField("undone");
            textField_wishProgress = new JTextField();
            textField_deadLine = new JTextField(nowString);
            textField_wishTarget = new JTextField("0");
        } else {//说明是点击WishComponent进来的
            textField_wishName = new JTextField(wishService.getWishById(wishId).getWishName());
            textArea_wishDescription = new JTextArea(wishService.getWishById(wishId).getWishDescription());
            textField_wishStatus = new JTextField(wishService.getWishById(wishId).getWishStatus());
            textField_wishProgress = new JTextField(wishService.getWishById(wishId).getWishProgress());
            textField_deadLine = new JTextField(wishService.getWishById(wishId).getDeadline());
            textField_wishTarget = new JTextField(wishService.getWishById(wishId).getWishTarget());
        }
        if (!isParent) {//如果是孩子身份的话，限制不能修改
            textField_wishName.setEditable(false);
            textArea_wishDescription.setEditable(false);
            textField_wishStatus.setEditable(false);
            textField_wishProgress.setEditable(false);
            textField_deadLine.setEditable(false);
            textField_wishTarget.setEditable(false);
        }

        textField_wishName.setBounds(400, 120, 600, 37);
        getContentPane().add(textField_wishName);
        textField_wishName.setColumns(10);

        textArea_wishDescription.setBounds(400, 190, 600, 80);
        getContentPane().add(textArea_wishDescription);
        textArea_wishDescription.setColumns(10);
        textArea_wishDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));


        textField_wishStatus.setBounds(400, 300, 600, 37);
        getContentPane().add(textField_wishStatus);
        textField_wishStatus.setColumns(10);

        textField_wishProgress.setBounds(400, 370, 600, 37);
        getContentPane().add(textField_wishProgress);
        textField_wishProgress.setColumns(10);

        textField_deadLine.setBounds(400, 440, 600, 37);
        getContentPane().add(textField_deadLine);
        textField_deadLine.setColumns(10);
        if (isParent) {
            textField_deadLine.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 处理鼠标点击事件
                    TimePicker timePicker = new TimePicker();
                    timePicker.setVisible(true);

                    timePicker.setTimeSelectionListener(new TimeSelectionListener() {
                        @Override
                        public void onTimeSelected(String timeSelected) {
                            System.out.println("timeSelected:" + timeSelected);
                            textField_deadLine.setText(timeSelected);  // 更新文本框内容
                        }
                    });
                }
            });
        }
        textField_wishTarget.setBounds(400, 510, 600, 37);
        getContentPane().add(textField_wishTarget);
        textField_wishTarget.setColumns(10);

        btnCreate = new BtnOrange("Create Wish");
        btnCreate.setBounds(535, 600, 150, 25);

        btnModify = new BtnOrange("Modify Wish");
        btnModify.setBounds(150, 600, 150, 25);

        btnDelete = new BtnGray("Delete Wish");
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
                int option = JOptionPane.showConfirmDialog(null, "是否确认创建愿望?", "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    if (isValidated()) {
                        Wish wish = new Wish();
                        wish.setParentId(parentId);
                        wish.setChildId(childId);
                        wish.setWishId(wishId);
                        wish.setWishName(String.valueOf(textField_wishName.getText()));
                        wish.setWishDescription(String.valueOf(textArea_wishDescription.getText()));
                        wish.setWishStatus(String.valueOf(textField_wishStatus.getText()));
                        wish.setWishProgress(String.valueOf(textField_wishProgress.getText()));
                        wish.setDeadline(String.valueOf(textField_deadLine.getText()));
                        wish.setWishTarget(String.valueOf(textField_wishTarget.getText()));
                        wishService.modifyWish(wish);
                        PageSwitcher.switchPages(wishCreateAndModifyPage, new WishPage());
                    }
                }
            }
        });

        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int option = JOptionPane.showConfirmDialog(null, "是否确认修改愿望?", "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    if (isValidated()) {
                        Wish wish = new Wish();
                        wish.setParentId(parentId);
                        wish.setChildId(childId);
                        wish.setWishId(wishId);
                        wish.setWishName(String.valueOf(textField_wishName.getText()));
                        wish.setWishDescription(String.valueOf(textArea_wishDescription.getText()));
                        wish.setWishStatus(String.valueOf(textField_wishStatus.getText()));
                        wish.setWishProgress(String.valueOf(textField_wishProgress.getText()));
                        wish.setDeadline(String.valueOf(textField_deadLine.getText()));
                        wish.setWishTarget(String.valueOf(textField_wishTarget.getText()));
                        wishService.modifyWish(wish);
                        PageSwitcher.switchPages(wishCreateAndModifyPage, new WishPage());
                    }
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int option = JOptionPane.showConfirmDialog(null, "是否确认删除愿望?", "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    wishService.deleteWish(wishId);
                    PageSwitcher.switchPages(wishCreateAndModifyPage, new WishPage());
                }

            }
        });

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int option = JOptionPane.showConfirmDialog(null, "是否确认完成愿望?", "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    Wish wish = new Wish();
                    wish.setWishId(wishId);
                    wish.setWishStatus("done");
                    wish.setWishProgress("100");
                    wishService.modifyWishStatusAndProgress(wish);
                    PageSwitcher.switchPages(wishCreateAndModifyPage, new WishPage());
                }
            }
        });
    }

    public boolean isValidated() {
        boolean isValidated = true;
        String type = null;
        LocalDateTime deadLine = LocalDateTime.parse(textField_deadLine.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime now = LocalDateTime.now();

        if (textField_wishName.getText() == null || textField_wishName.getText().isEmpty()) {
            isValidated = false;
            type = "empty";
        }
        if (textField_deadLine.getText() == null || textField_deadLine.getText().isEmpty()) {
            isValidated = false;
            type = "empty";
        }
        if (textField_wishTarget.getText() == null || textField_wishTarget.getText().isEmpty()) {
            isValidated = false;
            type = "empty";
        }
        if (deadLine.isBefore(now)) {
            isValidated = false;
            type = "invalid_date";
        }

        if (!isValidated) {
            if (type.equals("empty")) {
                JOptionPane.showMessageDialog(null, "You should not create empty inputs", "Alert", JOptionPane.WARNING_MESSAGE);
            }
            if (type.equals("invalid_date")) {
                JOptionPane.showMessageDialog(null, "Deadline should not be before now", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        }
        return isValidated;
    }

}

//class Main {
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            WishCreateAndModifyPage frame = new WishCreateAndModifyPage(1, true, true);
//            frame.setVisible(true);
//        });
//    }
//}

