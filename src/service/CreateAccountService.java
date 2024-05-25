package service;

import util.GenerateRandomId;
import util.JSONController;
import domain.*;

import static domain.AccountType.CURRENT_ACCOUNT;
import static domain.AccountType.SAVING_ACCOUNT;

import java.time.LocalDateTime;
import java.util.List;

public class CreateAccountService {
    private static JSONController jsonTemp = new JSONController("temp.txt");
    private static Temp temp = (Temp) jsonTemp.read(Temp.class);
    private static int childId = temp.getChildId();
    private static JSONController jsonAccount = new JSONController("account.txt");
    private static List<Account> accountList = jsonAccount.readArray(Account.class);

    public static void createNewSavingAccount(String password){
        int accountId = GenerateRandomId.generateNewAccID();
        // 假设利息率为1.5%，封闭期为12个月
        double interestRate = 1.5;
        int lockPeriod = 12;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lockEndTime = now.plusMonths(lockPeriod); // 添加12个月的封闭期

        Account newAccount = new Account(accountId, SAVING_ACCOUNT, 0, temp.getChildId(), password,
                interestRate, lockPeriod, now, lockEndTime);
        accountList.add(newAccount);
        jsonAccount.writeArray(accountList);
    }

    public static void createNewCurrentAccount(int accountId, String password, int newChildId){
        //为新注册的孩子账户添加current account
        Account newAccount = new Account(accountId, CURRENT_ACCOUNT, 0, newChildId, password);
        accountList.add(newAccount);
        jsonAccount.writeArray(accountList);
    }

    public static void createParentAccount(int accountId, String password, int newChildId){//唯一区别就是账户里有99999999
        //为新注册的孩子账户添加current account
        Account newAccount = new Account(accountId, CURRENT_ACCOUNT, 99999999.0, newChildId, password);
        accountList.add(newAccount);
        jsonAccount.writeArray(accountList);
    }
}
