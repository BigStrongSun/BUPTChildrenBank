package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.TempService;
import util.BtnOrange;
import service.WishService;

public class MainPage extends JFrame {
    private JLabel lblCurrentMoney;
    private JLabel lblTotalGoal;
    private TempService tempService;
    private boolean isParent;
    private int parentId;
    private int childId;

    private String childName = "John";

    private String parentName = "Doe";

    public MainPage() {
        setTitle("Main Page");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 248, 239));
        setLayout(null);

        tempService = new TempService();
        parentId = tempService.getTemp().getParentId();
        childId = tempService.getTemp().getChildId();
        isParent = tempService.getTemp().isParent();

        //左上角添加测试的切换类型按钮
        JButton btnSwitchUser = new JButton("切换用户身份");
        btnSwitchUser.setBounds(0, 0, 200, 30);

        if (isParent) {
            // 如果用户是家长，显示孩子的按钮列表
            displayParentView(parentId);
        } else {
            // 如果用户是孩子，显示当前的金额和总目标，以及任务池、许愿池和钱包的按钮
            displayChildView(childId);
        }
        JLabel lblNameType = new JLabel();
        lblNameType.setBounds(1000, 20, 250, 50);
        Font font = new Font("Arial", Font.BOLD, 18); // 设置字体为加粗，大小为18
        lblNameType.setFont(font);
        add(lblNameType);

// 根据用户类型设置姓名和类型文本
        if (isParent) {
            lblNameType.setText("<html>" + parentName + "<br>Type: Parent</html>");

        } else {
            lblNameType.setText("<html>" + childName + "<br>Type: ChildS</html>");

        }

        btnSwitchUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll(); // 移除当前所有组件
                if (isParent) {
                    isParent = false;
                    displayChildView(childId);
                } else {
                    isParent = true;
                    displayParentView(parentId);
                }
                add(btnSwitchUser); // 添加切换用户身份的按钮
                add(lblNameType); // 添加用户简单信息
                repaint(); // 重新绘制界面
                revalidate(); // 重新验证布局
            }
        });

        add(btnSwitchUser);

        setLocationRelativeTo(null);
        setResizable(false);

        //右上角添加用户简单信息
// 创建 JLabel 用于显示姓名和类型


    }


    private void displayParentView(int parentId) {
        // 创建一个背景色为橙色的 JPanel
        JPanel parentPanel = new JPanel();
        parentPanel.setBackground(new Color(255, 169, 32, 87)); // 橙色背景
        parentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50)); // 设置布局为空，以便手动设置组件位置
        parentPanel.setBounds(100, 300, 1080, 300); // 设置面板大小与 JFrame 相同
        add(parentPanel);

//        JLabel lblTitle = new JLabel("This is parent's view.");
//        lblTitle.setFont(new Font("Arial", Font.PLAIN, 30));
//        lblTitle.setBounds(420, 100, 500, 50); // 设置标签位置
//        parentPanel.add(lblTitle);

        // 示例：假设有两个孩子，显示两个按钮
        for (int i = 0; i < 2; i++) {
            JButton btnChild = new BtnOrange("Child " + (i + 1));
            btnChild.setPreferredSize(new Dimension(200, 100));
            btnChild.setFont(new Font("Arial", Font.PLAIN, 20));
            int childId = i + 1;
            btnChild.setBounds(100 + i * 300, 200, 200, 100); // 设置按钮位置
            btnChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
//                new WishPage(parentId, childId).setVisible(true);
                }
            });
            parentPanel.add(btnChild);
        }
    }


    private void displayChildView(int childId) {
        // 创建 WishService 实例
        WishService wishService = new WishService();

        // 读取当前金额和总目标值
        //之后的函数！！
//        double currentMoney = wishService.getCurrentMoney();
//        double totalGoal = wishService.getTotalWishTargetBeforeDeadLine();

        // 在正上方添加一个小框显示 current money 和 total goal
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.white);
        topPanel.setBounds(550, 20, 200, 70);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // 使用 BoxLayout 排列垂直方向

        lblCurrentMoney = new JLabel("Current Money: $100");//之后要改成函数来调用
        lblCurrentMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblCurrentMoney);

        lblTotalGoal = new JLabel("Total Goal: $500");
        lblTotalGoal.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(lblTotalGoal);

        add(topPanel);



        add(topPanel);

        // 在页面中下方添加一个大框，里面有三个按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 169, 32, 87));
        buttonPanel.setBounds(100, 300, 1080, 300);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        JButton btnTaskPool = new BtnOrange("Task");
        btnTaskPool.setPreferredSize(new Dimension(200, 100));
        btnTaskPool.setFont(new Font("Arial", Font.PLAIN, 20));
        btnTaskPool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TaskPage().setVisible(true);
            }
        });
        buttonPanel.add(btnTaskPool);

        JButton btnWishPool = new BtnOrange("Wish");
        btnWishPool.setPreferredSize(new Dimension(200, 100));
        btnWishPool.setFont(new Font("Arial", Font.PLAIN, 20));
        btnWishPool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WishPage().setVisible(true);
            }
        });
        buttonPanel.add(btnWishPool);

        JButton btnWallet = new BtnOrange("Wallet");
        btnWallet.setPreferredSize(new Dimension(200, 100));
        btnWallet.setFont(new Font("Arial", Font.PLAIN, 20));
        btnWallet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
//                new WalletPage().setVisible(true);
            }
        });
        buttonPanel.add(btnWallet);

        add(buttonPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
    }
}

