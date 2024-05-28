package service;
import domain.*;
import util.*;
import java.util.*;

public class ChangeChildService {
    static private JSONController jsonUser = new JSONController("user.txt");
    static private List<User> userList = jsonUser.readArray(User.class);
    static private JSONController jsonTemp = new JSONController("temp.txt");
    //static private Temp temp = jsonTemp.read(Temp.class);

    //写一个Clear Association Method，从家长端解绑孩子
    public static void clearAssociation(int parentId, int childId){//传入当前家长以及所绑定的孩子的id
        for(User user: userList){//给孩子解绑（即设置childOrParentId为0
            if(user.getUsername().equals(String.valueOf(childId))){
                user.setChildOrParentId(0);
                break;
            }
        }
        for(User user: userList){//给家长解绑
            if(user.getUsername().equals(String.valueOf(parentId))){
                user.setChildOrParentId(0);
                break;
            }
        }
        jsonUser.writeArray(userList);//更新信息到user.txt文件
    }

    public static void changeChild(int currentUserId, int toChangeId) {//输入当前用户id,和想要绑定的用户Id

        for(User user: userList){//给要绑定的用户加上当前用户的id
            if(user.getUsername().equals(String.valueOf(toChangeId))){
                user.setChildOrParentId(currentUserId);
                break;

            }
        }
        for(User user: userList){//给前用户加上要绑定的用户的id
            if(user.getUsername().equals(String.valueOf(currentUserId))){
                user.setChildOrParentId(toChangeId);
            }
        }
        jsonUser.writeArray(userList);//更新信息到user.txt文件
        System.out.println("child updated" );
    }
}
