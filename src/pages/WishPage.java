package pages;

import domain.Task;
import domain.Wish;
import service.WishService;
import service.TempService;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WishPage extends JFrame {
    private WishPage wishPage;

    private List<Wish> wishes;
    private JSONController json = new JSONController("wish.txt");
    WishService wishService = new WishService();
    TempService tempService = new TempService();

    private String childName = "John";
    private int parentId;//父母的Id
    private int childId;//孩子的Id
    private boolean isParent;

    private JLabel lblTitle;
    private JLabel lblChildName;
    private JLabel lblCurrentMoney;
    private JLabel lblCurrentMoneyValue;
    private JLabel lblTotalTarget;
    private JLabel lblTotalTargetValue;

    private JButton btnCreate;
    private WishComponent wishComponent;

    public WishPage() {
        parentId = tempService.getTemp().getParentId();
        childId = tempService.getTemp().getChildId();
        isParent = tempService.getTemp().isParent();
        wishPage = this;
        setTitle("pages.WishPage");
        getContentPane().setBackground(new Color(255, 248, 239));
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel linePanel = new JPanel();
        linePanel.setBackground(new Color(104, 75, 75));
        linePanel.setOpaque(true);
        linePanel.setBounds(500, 20, 1, 640);
        getContentPane().add(linePanel);

        lblChildName = new JLabel(childName + "'s");
        lblChildName.setFont(new Font("Arial", Font.PLAIN, 50));
        lblChildName.setBounds(80, 120, 300, 40);
        lblChildName.setForeground(new Color(0, 0, 0));
//        lblChildName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        getContentPane().add(lblChildName);

        lblTitle = new JLabel("Wish Pool");
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 50));
        lblTitle.setBounds(80, 180, 300, 40);
        lblTitle.setForeground(new Color(0, 0, 0));
//        lblTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        getContentPane().add(lblTitle);

        JPanel wishDifferencePanel = new JPanel();
        wishDifferencePanel.setBackground(new Color(255, 169, 32));
        wishDifferencePanel.setBounds(80, 300, 360, 280);
        wishDifferencePanel.setLayout(null);
        getContentPane().add(wishDifferencePanel);

        //后续读取真实的current money
        lblCurrentMoney = new JLabel("Current Money : ");
        lblCurrentMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        lblCurrentMoney.setForeground(Color.WHITE);
        lblCurrentMoney.setBounds(20, 60, 150, 28);
        wishDifferencePanel.add(lblCurrentMoney);
//        lblCurrentMoney.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        lblCurrentMoneyValue = new JLabel( "$100");
        lblCurrentMoneyValue.setFont(new Font("Arial", Font.PLAIN, 25));
        lblCurrentMoneyValue.setForeground(Color.WHITE);
        lblCurrentMoneyValue.setBounds(180, 60, 80, 28);
        wishDifferencePanel.add(lblCurrentMoneyValue);

        lblTotalTarget = new JLabel("Total Target : ");
        lblTotalTarget.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTotalTarget.setForeground(Color.WHITE);
        lblTotalTarget.setBounds(20, 160, 150, 28);
        wishDifferencePanel.add(lblTotalTarget);
//        lblTotalTarget.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        lblTotalTargetValue = new JLabel("$ "+wishService.getTotalWishTarget());
        lblTotalTargetValue.setFont(new Font("Arial", Font.PLAIN, 25));
        lblTotalTargetValue.setForeground(Color.WHITE);
        lblTotalTargetValue.setBounds(180, 160, 80, 28);
        wishDifferencePanel.add(lblTotalTargetValue);

        btnCreate = new BtnOrange("Create Wish");
        btnCreate.setBounds(1070, 20, 150, 25);
        getContentPane().add(btnCreate);

        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WishCreateAndModifyPage window = new WishCreateAndModifyPage(wishService.getMaxWishId() + 1, true, false);
                window.setVisible(true);
                wishPage.dispose();
            }
        });

        if (isParent) {
            getContentPane().add(btnCreate);
        }

        wishes = json.readArray(Wish.class);
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
                    WishComponent wishComponent = new WishComponent(wish.getWishId(), this, isParent, true);
                    wishComponent.setBounds(550, 50 + x, 670, 100);
                    getContentPane().add(wishComponent);
                    x += 120;
                }
            }
        }


    }
}


class Main1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WishPage frame = new WishPage();
            frame.setVisible(true);
        });
    }
}