package service;

import domain.Temp;
import util.JSONController;
import java.util.List;
import domain.User;
import util.WriteToTemp;

import static java.lang.Integer.parseInt;

public class LoginService {
    private static TempService tempService = new TempService();
    private static Temp temp = tempService.getTemp();
    private static JSONController json2 = new JSONController("user.txt");
    static List<User> userList = json2. readArray(User.class);

    public static void saveCurrentUser(String username,boolean isParent, String name){//这里传入的isParent不是temp中的
        int userId = parseInt(username);
        //Temp temp = new Temp(userId,0,isParent,name);//childid为0代表尚未绑定孩子
        TempService tempService = new TempService();
        Temp temp =tempService.getTemp();

        temp.setParent(isParent);
        temp.setName(name);

        int childOrParentId = 0;
        for(User user: userList){
            if(user.getUsername().equals(username)){
                childOrParentId = user.getChildOrParentId();
            }
        }

        if (isParent) {//是家长
            temp.setParentId(userId);
            temp.setChildId(childOrParentId);

        } else {//是孩子
            temp.setChildId(userId);
            temp.setParentId(childOrParentId);
        }

        //json.write(temp);//向temp.txt里写入
        //以下是强制向temp.txt中写。与以上信息无关
        if (isParent) {
            WriteToTemp.writeToTempFile(childOrParentId, name, true, userId);
        } else {
            WriteToTemp.writeToTempFile(userId, name, false, childOrParentId);
        }
    }

    public static void saveUser(String username, String password, String identity) {
        User newUser = new User(username, password, identity, 0, "name");
        userList.add(newUser);
        json2.writeArray(userList);

    }

    public static boolean usernameExist(String username) {
        for(User user: userList){
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    
    public static boolean validatePassword(String username, String password) {
        for(User user: userList){//check if the entered info match any info in the file
            if (user.getUsername().equals(username)) {
                return user.getPassword().equals(password);
            }
        }
        return false;
    }

    public static boolean checkIfIsParent(String username){//check if the username is a parent's username, return true
        for(User user: userList) {
            if (user.getUsername().equals(username)) {//username is unique, so we only need to use the username to identify
                if (user.getIdentity().equals("parent")) {
                    return true;
                }
            }
        }
        return false;

    }

    public static String findName(String userId){//通过userId,找到对应user的name，并返回
        for(User user: userList){
            if(user.getUsername().equals(userId)){
                return user.getName();
            }
        }
        return null;
    }
}