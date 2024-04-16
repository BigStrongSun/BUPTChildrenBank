package service;

import domain.Account;
import domain.Task;
import util.JSONController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountService {
    private List<Account> accounts;
    private JSONController json = new JSONController("account.txt");

    public AccountService() {
        accounts = json.readArray(Account.class);
    }

    public int getMaxAccountId() {
        int maxId = 0;
        if (accounts != null && !accounts.isEmpty()) {
            for (Account account : accounts) {
                int accountId = account.getAccountId();
                if (accountId > maxId) {
                    maxId = accountId;
                }
            }
        }
        return maxId;
    }

    public boolean saveAccounts() {
        return json.writeArray(accounts);
    }

    public void deleteAccount(int accountId) {
        // 检查任务列表是否为空
        if (accounts == null) {
//            tasks = new ArrayList<>();
            System.out.println("no such an account");
            return;
        }

        // 遍历任务列表，查找指定的taskId
        Iterator<Account> iterator = accounts.iterator();
        while (iterator.hasNext()) {
            Account account = iterator.next();
            if (account.getAccountId() == accountId) {
                // 找到指定的taskId，从任务列表中删除该任务
                iterator.remove();
                json.writeArray(accounts); // 更新任务列表到文件中
                System.out.println("Account已成功删除！");
                JOptionPane.showMessageDialog(null, "Account已成功删除", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        // 没有找到指定的taskId
        System.out.println("没有找到具有accountId的任务！");
        JOptionPane.showMessageDialog(null, "没有找到具有accountId的任务", "提示", JOptionPane.WARNING_MESSAGE);
    }

    public Account getAccountById(int accountId) {
        // 检查任务列表是否为空
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        // 遍历任务列表，查找指定的taskId
        for (Account account : accounts) {
            if (account.getAccountId() == accountId) {
                return account;
            }
        }
        // 如果accountId不存在，则创建新account并添加到account列表中
        Account newAccount = new Account();
        newAccount.setAccountId(accountId);
        // 设置其他新任务的属性
        accounts.add(newAccount);
        json.writeArray(accounts);
        return newAccount;
    }

    public int addBalance(int accountId, double income) {
        double newBalance = 0;
        for (Account account : accounts) {
            if (account.getAccountId() == accountId) {
                double currentBalance = account.getBalance();
                newBalance = currentBalance + income;
                if(newBalance < 0){
                   break;
                }
                account.setBalance(newBalance);
                json.writeArray(accounts); // 更新account列表到文件中
                JOptionPane.showMessageDialog(null, "已更新current account的余额为：$" + newBalance, "提示", JOptionPane.INFORMATION_MESSAGE);
                return 1;
            }
        }
        JOptionPane.showMessageDialog(null, "current account的余额不足，请从saving account中将钱转入再使用", "提示", JOptionPane.WARNING_MESSAGE);
        return 0;
    }

}
