package pages;

import domain.Wish;
import service.WishService;
import service.TempService;
import util.BtnOrange;
import util.JSONController;
import util.TopPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
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
    private JLabel lblTotalTarget;

    private JButton btnCreate;

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
        revalidate();

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

        lblCurrentMoney = new JLabel("Current Money : ");
        lblCurrentMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        lblCurrentMoney.setForeground(Color.WHITE);
        lblCurrentMoney.setBounds(20, 60, 150, 28);
        wishDifferencePanel.add(lblCurrentMoney);
        lblCurrentMoney.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        lblTotalTarget = new JLabel("Total Target : ");
        lblTotalTarget.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTotalTarget.setForeground(Color.WHITE);
        lblTotalTarget.setBounds(20, 160, 150, 28);
        wishDifferencePanel.add(lblTotalTarget);
        lblTotalTarget.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        btnCreate= new BtnOrange("Create Wish");
        btnCreate.setBounds(1050,20,150,25);
        getContentPane().add(btnCreate);


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