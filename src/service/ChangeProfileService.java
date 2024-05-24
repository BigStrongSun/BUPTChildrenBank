package service;

import domain.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import util.JSONController;
import domain.*;
import java.util.List;

public class ChangeProfileService {
    static private JSONController jsonUser = new JSONController("user.txt");
    static private List<User> userList = jsonUser.readArray(User.class);

    public static void changeUsername(String username, String password, String newusername) {
        for(User user: userList){
            if(user.getUsername().equals(username))   {
                user.setUsername(newusername);
                break;
            }
        }
        jsonUser.writeArray(userList);


        System.out.println("newusername for user " + username + " changed to " + newusername);
    }

    //this method is to change the childOrParentId attribute of the corresponding child or parent
    public static void changeCoPId(String username,String newUsername){//input the old username and the new username of the changing account
        //find if there is someone (child or parent) is associated with them
        for(User user: userList){
            if(user.getChildOrParentId() == Integer.parseInt(username)){
                user.setChildOrParentId(Integer.parseInt(newUsername));
                jsonUser.writeArray(userList);
                break;
            }
        }

    }
}
