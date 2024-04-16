package service;

import domain.Wish;
import util.JSONController;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WishService {
    private List<Wish> wishes;
    private JSONController json = new JSONController("wish.txt");
    private Wish wish;

    /**
     * Initialize WishService
     */
    public WishService() {
        wishes = json.readArray(Wish.class);
    }

    /**
     * Retrieves the maximum wish ID from the wish list.
     *
     * @return The maximum wish ID, or 0 if the wish list is empty.
     */
    public int getMaxWishId() {
        int maxId = 0;
        if (wishes != null && !wishes.isEmpty()) {
            for (Wish wish : wishes) {
                int wishId = wish.getWishId();
                if (wishId > maxId) {
                    maxId = wishId;
                }
            }
        }
        return maxId;
    }

    /**
     * Modifies an existing wish with the provided wish information.
     *
     * @param wish The modified wish object.
     * @return 1 if the modification is successful, 0 otherwise.
     */
    public int modifyWish(Wish wish) {
        int wishId = wish.getWishId();
        if (wish != null) {
            // 根据任务id查找对应的任务对象
            Wish existingWish = getWishById(wishId);
            existingWish.setWishName(wish.getWishName());
            existingWish.setParentId(wish.getParentId());
            existingWish.setChildId(wish.getChildId());
            existingWish.setWishDescription(wish.getWishDescription());
            existingWish.setDeadline(wish.getDeadline());
            existingWish.setWishStatus(wish.getWishStatus());
            existingWish.setWishTarget(wish.getWishTarget());
            existingWish.setWishProgress(wish.getWishProgress());
            if (saveWishes()) {
                System.out.println("修改成功");
                JOptionPane.showMessageDialog(null, "Success", "Tips", JOptionPane.INFORMATION_MESSAGE);
                return 1;
            } else {
                System.out.println("修改失败");
                JOptionPane.showMessageDialog(null, "Failure", "Tips", JOptionPane.INFORMATION_MESSAGE);
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Modifies the status of an existing wish.
     *
     * @param wish The wish object containing the modified status.
     * @return 1 if the modification is successful, 0 otherwise.
     */
    public int modifyWishStatusAndProgress(Wish wish) {
        int wishId = wish.getWishId();
        if (wish != null) {
            // 根据任务id查找对应的任务对象
            Wish existingWish = getWishById(wishId);
            existingWish.setWishStatus(wish.getWishStatus());
            existingWish.setWishProgress(wish.getWishProgress());
            if (saveWishes()) {
                System.out.println("确认成功");
                JOptionPane.showMessageDialog(null, "Confirmation success", "Tips", JOptionPane.INFORMATION_MESSAGE);
                return 1;
            } else {
                System.out.println("确认失败");
                JOptionPane.showMessageDialog(null, "Confirmation failure", "Tips", JOptionPane.INFORMATION_MESSAGE);
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Saves the wish list to the JSON file.
     *
     * @return true if the wish list is successfully saved, false otherwise.
     */
    public boolean saveWishes() {
        return json.writeArray(wishes);
    }

    /**
     * Deletes a wish with the specified wish ID from the wish list.
     *
     * @param wishId The ID of the wish to delete.
     */
    public void deleteWish(int wishId) {
        // 检查任务列表是否为空
        if (wishes == null) {
//            wishes = new ArrayList<>();
            System.out.println("任务列表为空，没有要删除的函数");
            return;
        }

        // 遍历任务列表，查找指定的wishId
        Iterator<Wish> iterator = wishes.iterator();
        while (iterator.hasNext()) {
            Wish wish = iterator.next();
            if (wish.getWishId() == wishId) {
                // 找到指定的wishId，从任务列表中删除该任务
                iterator.remove();
                json.writeArray(wishes); // 更新任务列表到文件中
                System.out.println("任务已成功删除！");
                JOptionPane.showMessageDialog(null, "任务已成功删除", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        // 没有找到指定的wishId
        System.out.println("没有找到具有wishId的任务！");
        JOptionPane.showMessageDialog(null, "没有找到具有wishId的任务", "提示", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Retrieves a wish from the wish list based on the specified wish ID.
     * If the wish does not exist, a new wish is created and added to the list.
     *
     * @param wishId The ID of the wish to retrieve.
     * @return The wish object with the specified wish ID.
     */
    public Wish getWishById(int wishId) {
        // 检查任务列表是否为空
        if (wishes == null) {
            wishes = new ArrayList<>();
        }
        // 遍历任务列表，查找指定的wishId
        for (Wish wish : wishes) {
            if (wish.getWishId() == wishId) {
                return wish; // 如果找到指定的wishId，直接返回任务对象
            }
        }
        // 如果wishId不存在，则创建新任务并添加到任务列表中
        Wish newWish = new Wish();
        newWish.setWishId(wishId);
        // 设置其他新任务的属性
        wishes.add(newWish);
        json.writeArray(wishes);
        return newWish; // 返回新创建的任务对象
    }

    public double getTotalWishTargetBeforeDeadLine() {
        LocalDateTime now = LocalDateTime.now();
        double totalWishTarget = 0;
        double wishTarget;
        if (wishes != null && !wishes.isEmpty()) {
            for (Wish wish : wishes) {
                if (wish.getWishStatus().equals("undone")) {
                    LocalDateTime dateTime = LocalDateTime.parse(wish.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    if (wish.getWishTarget() == null || wish.getWishTarget().isEmpty() || dateTime.isBefore(now)) {
                        wishTarget = 0;
                    } else {
                        wishTarget = Double.parseDouble(wish.getWishTarget());
                    }
                    totalWishTarget += wishTarget;
                }
            }
        }
        return totalWishTarget;
    }
}
