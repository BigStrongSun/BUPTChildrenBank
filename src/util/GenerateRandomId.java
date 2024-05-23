package util;

import domain.*;

import java.util.List;
import java.util.Random;


public class GenerateRandomId {
    private static JSONController jsonAccount = new JSONController("account.txt");
    private static List<Account> accountList = jsonAccount.readArray(Account.class);
    private static JSONController jsonUser = new JSONController("user.txt");
    private static List<User> userList = jsonUser.readArray(User.class);

    public static int generateNewAccID(){//用于自动生成一个不重复的AccountId
        Random rand = new Random();

        boolean flag;
        int newAccountId;
                        do {//生成不重复的accountId
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

    public static int generateNewUserID(){//用于自动生成一个不重复的UserId。即parentId或ChildId. 当前也是username
        Random rand = new Random();

        boolean flag;
        int newUserId;
        do {//生成不重复的accountId
            flag = false;
            // 生成一个1到1000之间的随机整数（包括1和1000）
            newUserId = rand.nextInt(1000) + 1;
            System.out.println("The current random number is " + newUserId);
            for(User user: userList){
                //目前的user id叫username,且是String。所以要转换类型
                int currentUserId = Integer.parseInt(user.getUsername());
                if( currentUserId == newUserId){
                    flag = true;//重复了
                    break;
                }
            }
        } while (flag);//如果里面的循环完了flag还是false,就没有重复
        return newUserId;
    }
}
