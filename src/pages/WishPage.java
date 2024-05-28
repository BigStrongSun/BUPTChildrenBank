package pages;

import domain.*;
import service.UserService;
import service.WishService;
import service.TempService;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The WishPage class represents a JFrame that displays a list of wishes.
 * It allows users to view the current wishes, create new wishes, and see the progress and details of existing wishes.
 * The page shows the child's name, current balance, saving balance, and total target amount for all wishes.
 * Parents can create new wishes for their children.
 */
public class WishPage extends JFrame {
    private WishPage wishPage;
    private List<Wish> wishes;
    private List<Account> accounts;
    private List<User> users;
    private JSONController jsonWish = new JSONController("wish.txt");
    private JSONController jsonAccount = new JSONController("account.txt");
    private JSONController jsonUser = new JSONController("user.txt");
    WishService wishService = new WishService();
    TempService tempService = new TempService();
    UserService userService = new UserService();

    private String childName = "John";
    private int parentId; // Parent's ID
    private int childId; // Child's ID
    private boolean isParent;

    private JLabel lblTitle;
    private JLabel lblChildName;
    private JLabel lblCurrentMoney;
    private JLabel lblCurrentMoneyValue;

    private JLabel lblSavingMoney;
    private JLabel lblSavingMoneyValue;
    private JLabel lblTotalTarget;
    private JLabel lblTotalTargetValue;

    private JButton btnCreate;
    private WishComponent wishComponent;

    /**
     * Constructs a new WishPage object that initializes the page layout and components.
     * It retrieves the parent and child IDs, sets up the user interface, and loads the wish data.
     */
    public WishPage() {
        if(tempService.getTemp()!=null){
            parentId = tempService.getTemp().getParentId();
            childId = tempService.getTemp().getChildId();
            isParent = tempService.getTemp().isParent();
        }
        wishPage = this;
        childName = userService.getChildNameById(childId);
        setTitle("Wish Page");
        getContentPane().setBackground(new Color(255, 248, 239));
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JButton backButton = new JButton("Back");
        backButton.setFocusable(false); // Remove focus border
        backButton.setContentAreaFilled(false); // Do not fill content area
        backButton.setOpaque(true); // Set background opaque
        backButton.setBounds(0, 0, 80, 30);
        getContentPane().add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainPage window = new MainPage();
                window.setVisible(true);
                wishPage.dispose();
            }
        });

        JPanel linePanel = new JPanel();
        linePanel.setBackground(new Color(104, 75, 75));
        linePanel.setOpaque(true);
        linePanel.setBounds(500, 20, 1, 640);
        getContentPane().add(linePanel);

        lblChildName = new JLabel(childName + "'s");
        lblChildName.setFont(new Font("Arial", Font.PLAIN, 50));
        lblChildName.setBounds(80, 120, 300, 40);
        lblChildName.setForeground(new Color(0, 0, 0));
        getContentPane().add(lblChildName);

        lblTitle = new JLabel("Wish Pool");
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 50));
        lblTitle.setBounds(80, 180, 300, 40);
        lblTitle.setForeground(new Color(0, 0, 0));
        getContentPane().add(lblTitle);

        JPanel wishDifferencePanel = new JPanel();
        wishDifferencePanel.setBackground(new Color(255, 169, 32));
        wishDifferencePanel.setBounds(80, 300, 360, 280);
        wishDifferencePanel.setLayout(null);
        getContentPane().add(wishDifferencePanel);

        lblCurrentMoney = new JLabel("Current Balance : ");
        lblCurrentMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        lblCurrentMoney.setForeground(Color.WHITE);
        lblCurrentMoney.setBounds(20, 60, 200, 28);
        wishDifferencePanel.add(lblCurrentMoney);

        accounts = jsonAccount.readArray(Account.class);
        double currentBalance = 0;
        for (Account account : accounts) {
            if (account.getUserId() == childId && account.getAccountType().equals(AccountType.CURRENT_ACCOUNT)) {
                currentBalance += account.getBalance();
            }
        }
        lblCurrentMoneyValue = new JLabel("$ " + currentBalance);
        lblCurrentMoneyValue.setFont(new Font("Arial", Font.PLAIN, 25));
        lblCurrentMoneyValue.setForeground(Color.WHITE);
        lblCurrentMoneyValue.setBounds(180, 60, 150, 28);
        wishDifferencePanel.add(lblCurrentMoneyValue);

        lblSavingMoney = new JLabel("Saving Balance : ");
        lblSavingMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        lblSavingMoney.setForeground(Color.WHITE);
        lblSavingMoney.setBounds(20, 120, 200, 28);
        wishDifferencePanel.add(lblSavingMoney);

        double savingBalance = 0;
        for (Account account : accounts) {
            if (account.getUserId() == childId && account.getAccountType().equals(AccountType.SAVING_ACCOUNT)) {
                savingBalance += account.getBalance();
            }
        }
        lblSavingMoneyValue = new JLabel("$ " + savingBalance);
        lblSavingMoneyValue.setFont(new Font("Arial", Font.PLAIN, 25));
        lblSavingMoneyValue.setForeground(Color.WHITE);
        lblSavingMoneyValue.setBounds(180, 120, 150, 28);
        wishDifferencePanel.add(lblSavingMoneyValue);

        lblTotalTarget = new JLabel("Total Target : ");
        lblTotalTarget.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTotalTarget.setForeground(Color.WHITE);
        lblTotalTarget.setBounds(20, 180, 200, 28);
        wishDifferencePanel.add(lblTotalTarget);

        lblTotalTargetValue = new JLabel("$ " + wishService.getTotalWishTargetBeforeDeadLine());
        lblTotalTargetValue.setFont(new Font("Arial", Font.PLAIN, 25));
        lblTotalTargetValue.setForeground(Color.WHITE);
        lblTotalTargetValue.setBounds(180, 180, 150, 28);
        wishDifferencePanel.add(lblTotalTargetValue);

        btnCreate = new BtnOrange("Create Wish");
        btnCreate.setBounds(1070, 20, 150, 25);
        getContentPane().add(btnCreate);

        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(parentId == 0){
                    JOptionPane.showMessageDialog(null, "Please bind child before set a wish", "Tips", JOptionPane.WARNING_MESSAGE);
                } else if(childId == 0){
                    JOptionPane.showMessageDialog(null, "Please bind parent before set a wish", "Tips", JOptionPane.WARNING_MESSAGE);
                }else{
                    WishCreateAndModifyPage window = new WishCreateAndModifyPage(wishService.getMaxWishId() + 1, true, false);
                    window.setVisible(true);
                    wishPage.dispose();
                }
            }
        });

        if (isParent) {
            getContentPane().add(btnCreate);
        }

        // Create right scroll panel
        JPanel rightContentPanel = new JPanel();
        rightContentPanel.setLayout(null);
        rightContentPanel.setBackground(new Color(255, 248, 239));

        LocalDateTime now = LocalDateTime.now();
        wishes = jsonWish.readArray(Wish.class);
        int x = 0;
        if (wishes != null) {
            Collections.sort(wishes, new Comparator<Wish>() {
                @Override
                public int compare(Wish o1, Wish o2) {
                    return o2.getDeadline().compareTo(o1.getDeadline());
                }
            });
            for (Wish wish : wishes) {
                if (wish.getParentId() == parentId && wish.getChildId() == childId) {
                    LocalDateTime dateTime = LocalDateTime.parse(wish.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    if (dateTime.isAfter(now)) {
                        WishComponent wishComponent = new WishComponent(wish.getWishId(), this, isParent, true);
                        wishComponent.setBounds(0, 20 + x, 670, 100);
                        rightContentPanel.add(wishComponent);
                        x += 120;
                    }
                }
            }
        }
        rightContentPanel.setPreferredSize(new Dimension(670, x + 40));

        JScrollPane scrollPane = new JScrollPane(rightContentPanel);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI()); // Apply custom scroll bar UI
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(550, 20, 700, 660);
        getContentPane().add(scrollPane);
    }
}
