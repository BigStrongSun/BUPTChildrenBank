package service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import domain.Temp;
import util.JSONController;
import java.util.List;
import domain.User;

import static java.lang.Integer.parseInt;

public class LoginService {
    private static JSONController json = new JSONController("temp.txt");
    private static JSONController json2 = new JSONController("user.txt");
    static List<User> userList = json2. readArray(User.class);

    public static void saveCurrentUser(String username){//for parent
        int userId = parseInt(username);
        Temp temp = new Temp(userId,0,true);//childid为0代表尚未绑定孩子
        for(User user: userList){
            if(user.getUsername().equals(username)){
                temp.setChildId(user.getChildOrParentId());
                System.out.println(user.getUsername());
                System.out.println(user.getChildOrParentId());
                System.out.println(temp.getChildId());
            }
        }
        json.write(temp);//向temp.txt里写入
    }
    public static void saveCurrentUser2(String username){//for child
        int userId = parseInt(username);

        Temp temp = new Temp(0,userId,false);//parentid为0代表尚未绑定家长
        for(User user: userList){
            if(user.getUsername().equals(username)){
                temp.setParentId(user.getChildOrParentId());
            }
        }

        json.write(temp);//向temp.txt里写入
    }
    public static void saveUser(String username, String password, String identity) {
        User newUser = new User(username, password, identity, 0);
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

    public static boolean checkIfIsParent(String username){//if is parent, return true
        for(User user: userList) {
            if (user.getUsername().equals(username)) {//username is unique, so we only need to use the username to identify
                if (user.getIdentity().equals("parent")) {
                    return true;
                }
            }
        }
        return false;

    }
}