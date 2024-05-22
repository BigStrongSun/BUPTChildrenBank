package service;

import util.GenerateRandomId;
import util.JSONController;
import domain.*;

import static domain.AccountType.CURRENT_ACCOUNT;
import static domain.AccountType.SAVING_ACCOUNT;
import java.util.List;

public class CreateAccountService {
    private static JSONController jsonTemp = new JSONController("temp.txt");
    private static Temp temp = (Temp) jsonTemp.read(Temp.class);
    private static int childId = temp.getChildId();
    private static JSONController jsonAccount = new JSONController("account.txt");
    private static List<Account> accountList = jsonAccount.readArray(Account.class);

    public static void createNewSavingAccount(String password){
        //孩子可以自己添加多个saving account
        int accountId = GenerateRandomId.generateNewAccID();
        Account newAccount = new Account(accountId, SAVING_ACCOUNT, 0, temp.getChildId(), password);
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
