package service;

import java.util.List;
import domain.*;
import util.*;

public class ChangeNameService {
    private static JSONController jsonTemp = new JSONController("temp.txt");
    private static Temp temp = (Temp) jsonTemp.read(Temp.class);
    private static JSONController jsonUser = new JSONController("user.txt");
    private static List<User> userList = jsonUser.readArray(User.class);

    public static void changeName(String newName) {
        // 将更新内容写入 temp.txt
        WriteToTemp.writeToTempFile(temp.getParentId(),temp.getChildId(), temp.isParent(), newName);

        // 将更新内容写入 user.txt
        User currentUser = findCurrentUser();
        if (currentUser != null) {
            currentUser.setName(newName);
            jsonUser.writeArray(userList);
        } else {
            System.err.println("Current user not found, name change failed.");
        }
    }

    public static int findCurrentId() {
        int currentId;
        if (temp.isParent()) {
            currentId = temp.getParentId();
        } else {
            currentId = temp.getChildId();
        }
        return currentId;
    }

    public static User findCurrentUser() {
        int currentId = findCurrentId();
        String currentUsername = String.valueOf(currentId);
        for (User user : userList) {
            if (user.getUsername().equals(currentUsername)) {
                return user;
            }
        }
        return null;
    }

    public static String findCurrentName() {
        return temp.getName();
    }
}