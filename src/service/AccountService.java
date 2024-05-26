package service;

import domain.Account;
import domain.AccountType;
import util.GenerateRandomId;
import util.JSONController;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountService {
    private List<Account> accounts;
    private JSONController json = new JSONController("account.txt");
    private static final double FIXED_INTEREST_RATE = 0.10; // 固定利率 10%
    private static final int LOCK_PERIOD_DAYS = 7; // 固定封闭期 7天

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

    public List<Account> getAccounts() {
        return accounts;
    }

    public boolean saveAccounts() {
        return json.writeArray(accounts);
    }

    public void deleteAccount(int accountId) {
        if (accounts == null) {
            System.out.println("no such an account");
            return;
        }

        Iterator<Account> iterator = accounts.iterator();
        while (iterator.hasNext()) {
            Account account = iterator.next();
            if (account.getAccountId() == accountId) {
                iterator.remove();
                json.writeArray(accounts);
                System.out.println("Account已成功删除！");
                JOptionPane.showMessageDialog(null, "Account successfully deleted", "Tips", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        System.out.println("没有找到具有accountId的任务！");
        JOptionPane.showMessageDialog(null, "No tasks found with such accountId", "Tips", JOptionPane.WARNING_MESSAGE);
    }

    public Account getAccountById(int accountId) {
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        for (Account account : accounts) {
            if (account.getAccountId() == accountId) {
                return account;
            }
        }
        Account newAccount = new Account();
        newAccount.setAccountId(accountId);
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
                if (newBalance < 0) {
                    break;
                }
                account.setBalance(newBalance);
                json.writeArray(accounts);
                JOptionPane.showMessageDialog(null, "Balance in current account is updated to：$" + newBalance, "Tips", JOptionPane.INFORMATION_MESSAGE);
                return 1;
            }
        }
        JOptionPane.showMessageDialog(null, "Insufficient balance in current account, please transfer the money form saving account", "Tips", JOptionPane.WARNING_MESSAGE);
        return 0;
    }

    public Account getCurrentAccountByUserId(int userId) {
        for (Account account : accounts) {
            if (account.getUserId() == userId && account.getAccountType().equals(AccountType.CURRENT_ACCOUNT)) {
                return account;
            }
        }
        return null;
    }

    public int createNewSavingAccount(String password, double initialDeposit, int userId) {
        Account currentAccount = getCurrentAccountByUserId(userId);
        if (currentAccount == null || currentAccount.getBalance() < initialDeposit) {
            JOptionPane.showMessageDialog(null, "Insufficient balance in current account to create a saving account", "Tips", JOptionPane.WARNING_MESSAGE);
            return -1; // 余额不足，返回-1表示创建失败
        }

        // 扣除当前账户余额
        currentAccount.setBalance(currentAccount.getBalance() - initialDeposit);

        // 创建新的储蓄账户
        int newAccountId = new GenerateRandomId().generateNewAccID();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lockEndTime = now.plusDays(LOCK_PERIOD_DAYS);
        System.out.println("the balence added is"+initialDeposit);
        Account newAccount = new Account(newAccountId, AccountType.SAVING_ACCOUNT, initialDeposit, userId, password,
                FIXED_INTEREST_RATE, LOCK_PERIOD_DAYS, now, lockEndTime);
        accounts.add(newAccount);

        try {
            json.writeArray(accounts);  // 确保在添加到列表后立即保存更新
            // 确认创建成功，并显示新账户的余额
            JOptionPane.showMessageDialog(null, "New saving account created with balance: $" + newAccount.getBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);

            // 输出新账户的所有信息到控制台
            System.out.println("New Saving Account Created:");
            System.out.println("Account ID: " + newAccount.getAccountId());
            System.out.println("User ID: " + newAccount.getUserId());
            System.out.println("Account Type: " + newAccount.getAccountType());
            System.out.println("Balance: " + newAccount.getBalance());
            System.out.println("Password: " + newAccount.getPassword());
            System.out.println("Interest Rate: " + newAccount.getInterestRate());
            System.out.println("Lock Period (days): " + newAccount.getLockPeriod());
            System.out.println("Deposit Time: " + newAccount.getDepositTime());
            System.out.println("Lock End Time: " + newAccount.getLockEndTime());
        } catch (Exception e) {
            System.err.println("Failed to write the new account to file.");
            e.printStackTrace();
        }

        return newAccountId;
    }



    // 检查并更新封闭期结束后的账户余额
    public void updateAccountsForInterest() {
        LocalDateTime now = LocalDateTime.now();
        for (Account account : accounts) {
            if (account.getAccountType() == AccountType.SAVING_ACCOUNT && now.isAfter(account.getLockEndTime())) {
                double interest = account.getBalance() * FIXED_INTEREST_RATE;
                account.setBalance(account.getBalance() + interest);
                account.setLockEndTime(now.plusDays(LOCK_PERIOD_DAYS)); // 重新设置封闭期
                account.setInLockPeriod(false); // 封闭期结束，允许转账
            }
        }
        saveAccounts();
    }
}
