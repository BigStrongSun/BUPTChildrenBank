package service;
import domain.*;
import util.*;
import java.util.*;

public class ChangeChildService {
    static private JSONController jsonUser = new JSONController("user.txt");
    static private List<User> userList = jsonUser.readArray(User.class);
    static private JSONController jsonTemp = new JSONController("temp.txt");
    //static private Temp temp = jsonTemp.read(Temp.class);

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

    }
}
