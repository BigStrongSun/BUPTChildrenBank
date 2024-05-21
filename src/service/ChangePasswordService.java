package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import util.JSONController;
import domain.*;
import java.util.List;

public class ChangePasswordService {
    static private JSONController jsonUser = new JSONController("user.txt");
    static private List<User> userList = jsonUser.readArray(User.class);

    public static boolean validatePassword(String username, String password) {
        for(User user: userList){
            if(user.getUsername().equals(username) &&
                    user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    public static void changeUserPassword(String username, String newPassword) {
        for(User user: userList){
            if(user.getUsername().equals(username))   {
                user.setPassword(newPassword);
                break;
            }
        }
        jsonUser.writeArray(userList);

        System.out.println("Password for user " + username + " changed to " + newPassword);
    }
}
