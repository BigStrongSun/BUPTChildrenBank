package service;

import util.JSONController;
import domain.*;

import static domain.AccountType.SAVING_ACCOUNT;
import java.util.List;

public class CreateAccountService {
    private static JSONController jsonTemp = new JSONController("temp.txt");
    private static Temp temp = (Temp) jsonTemp.read(Temp.class);
    private static int childId = temp.getChildId();
    private static JSONController jsonAccount = new JSONController("account.txt");
    private static List<Account> accountList = jsonAccount.readArray(Account.class);
    //孩子创建新账户
    public static void createNewAccount(int accountId,String password){
        Account newAccount = new Account(accountId, SAVING_ACCOUNT, 0, temp.getChildId(), password);
        accountList.add(newAccount);
        jsonAccount.writeArray(accountList);
    }
}
