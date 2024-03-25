package util;

import pages.WishPage;
import service.TempService;
import service.WishService;

import javax.swing.*;
import java.awt.*;

public class WishComponent extends JPanel {
    private WishService wishService = new WishService();
    TempService tempService = new TempService();
    private int parentId;//父母的Id
    private int childId;//孩子的Id

    public WishComponent(int wishId){
        parentId=tempService.getTemp().getParentId();
        childId=tempService.getTemp().getChildId();
        setBackground(new Color(255, 235, 156));
        setSize(700, 120);
        setLayout(null);

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



    }
}

class Main2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WishComponent frame = new WishComponent(1);
            frame.setVisible(true);
        });
    }
}