package util;

import pages.WishPage;
import service.TempService;
import service.WishService;

import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;
import java.awt.*;

public class WishComponent extends JPanel {
    private WishService wishService = new WishService();
    TempService tempService = new TempService();
    private int parentId;//父母的Id
    private int childId;//孩子的Id

    public WishComponent(int wishId) {
        parentId = tempService.getTemp().getParentId();
        childId = tempService.getTemp().getChildId();
        setBackground(new Color(255, 235, 156));
        setSize(700, 100);
        setLayout(null);

        //后期换成从txt读取
        int currentMoney = 1;
        String wishName = wishService.getWishById(wishId).getWishName();
        wishName = (wishName == null || wishName == "") ? "？？？" : wishName;
        String deadLine = wishService.getWishById(wishId).getDeadline();
        deadLine = (deadLine == null || deadLine == "") ? "？？？" : deadLine;
        String wishStatus = wishService.getWishById(wishId).getWishStatus();
        wishStatus = (wishStatus == null || wishStatus == "") ? "undone" : wishStatus;
        String wishTarget = wishService.getWishById(wishId).getWishTarget();
        wishTarget = (wishTarget == null || wishTarget == "") ? "？？？" : wishTarget;
        String wishProgress = wishService.getWishById(wishId).getWishProgress();
        wishProgress = (wishProgress == null || wishProgress == "") ? "？？？" : wishProgress;

        JLabel lblWishName = new JLabel("wishName");
        lblWishName.setBounds(60, 20, 340, 40);
        lblWishName.setFont(new Font("Arial", Font.PLAIN, 20));
//        lblWishName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(lblWishName);

        JProgressBar jProgressBar = new JProgressBar(0, 100);
        jProgressBar.setValue(Integer.parseInt("90"));
        jProgressBar.setForeground(new Color(255, 169, 32));
        jProgressBar.setBorderPainted(false);
        jProgressBar.setBounds(60, 80, 302, 10);
        add(jProgressBar);

//        JLabel lblWishProgress = new JLabel(wishProgress+"%");
        JLabel lblWishProgress = new JLabel("90" + "%");
        lblWishProgress.setForeground(new Color(102, 102, 102));
        lblWishProgress.setBounds(368, 69, 42, 30);
        add(lblWishProgress);

        JLabel lblWishStatus = new JLabel(wishStatus);
        if (wishStatus.equals("done")) {
            lblWishStatus.setForeground(new Color(19, 133, 78));
        } else if (wishStatus.equals("undone")) {
            lblWishStatus.setForeground(Color.RED);
        }
        lblWishStatus.setBounds(440,30,50,20);
        add(lblWishStatus);

        JLabel lblWishTarget = new JLabel("300");
        lblWishTarget.setFont(new Font("Arial", Font.PLAIN, 20));
        lblWishTarget.setBounds(550, 20, 100, 40);
        add(lblWishTarget);

        JLabel lblDeadline = new JLabel("Expired Date: "+"2024-03-13");
        lblDeadline.setBounds(440, 64, 200, 40);
        add(lblDeadline);
//        lblDeadline.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

    }
}
