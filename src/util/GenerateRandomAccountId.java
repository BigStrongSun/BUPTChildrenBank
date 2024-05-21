package util;

import domain.Account;

import java.util.List;
import java.util.Random;

//用于自动生成一个不重复的AccountId
public class GenerateRandomAccountId {
    private static JSONController jsonAccount = new JSONController("account.txt");
    private static List<Account> accountList = jsonAccount.readArray(Account.class);

    public static int generateNewAccID(){
        Random rand = new Random();

        boolean flag;
        int newAccountId;
                        do {//这里要写一个生成不重复的accountId
            flag = false;
            // 生成一个1到1000之间的随机整数（包括1和1000）
            newAccountId = rand.nextInt(1000) + 1;
            System.out.println("The current random number is " + newAccountId);
            for(Account account: accountList){
                if(account.getAccountId() == newAccountId){
                    flag = true;//重复了
                    break;
                }
            }
        } while (flag);//如果里面的循环完了flag还是false,就没有重复
        return newAccountId;
    }
}
