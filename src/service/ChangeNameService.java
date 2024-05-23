package service;
import java.util.List;
import domain.*;
import util.*;
//本service类包含一个用于更换当前用户名字的方法。还有一个用于找当前用户id,以及一个用于找当前用户的方法（这两个方法别的地方应该也能用）
public class ChangeNameService {
    private static JSONController jsonTemp = new JSONController("temp.txt");
    private static Temp temp = (Temp) jsonTemp.read(Temp.class);
    private static JSONController jsonUser = new JSONController("user.txt");
    private static List<User> userList = jsonUser.readArray(User.class);

    public static void changeName(String newName){//传入新名字
        //将更新内容写入temp.txt
//        temp.setName(newName);
//        jsonTemp.write(temp);
        WriteToTemp.writeToTempFile(temp.getChildId(), newName, temp.isParent(), temp.getParentId());


        //将更新内容写入user.txt
        User currentUser = findCurrentUser();
        currentUser.setName(newName);
        jsonUser.writeArray(userList);

    }

    public static int findCurrentId(){//找到当前用户id
        int currentId;
        if(temp.isParent()){
            currentId = temp.getParentId();
        }else{
            currentId = temp.getChildId();
        }
        return currentId;
    }

    public static User findCurrentUser(){//在所有用户中找到当前用户
        int currentId = findCurrentId();
        String currentUsername = String.valueOf(currentId);
        for(User user: userList){//找到当前user
            if(user.getUsername().equals(currentUsername)){
                return user;
            }
        }
        return null;
    }

    public static String findCurrentName(){//在所有用户中找到当前用户
        return temp.getName();
    }
}
