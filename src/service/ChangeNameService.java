package service;

import java.util.List;
import domain.*;
import util.*;

import static service.LoginService.refreshUserList;

public class ChangeNameService {

    public static void changeName(String newName) {
        JSONController jsonTemp = new JSONController("temp.txt");
        Temp temp = (Temp) jsonTemp.read(Temp.class);
        JSONController jsonUser = new JSONController("user.txt");
        List<User> userList = jsonUser.readArray(User.class);

        // 将更新内容写入 user.txt
        User currentUser = findCurrentUser(userList, temp);
        if (currentUser != null) {
            currentUser.setName(newName);
            System.out.println("Current user going to change name: " + currentUser.ToStringABC());

            // 替换 userList 中的对应用户
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUsername().equals(currentUser.getUsername())) {
                    userList.set(i, currentUser);
                    break;
                }
            }

            jsonUser.writeArray(userList);
            refreshUserList();
        } else {
            System.err.println("Current user not found, name change failed.");
        }

        // 将更新内容写入 temp.txt
        System.out.println("Before changing name: " + temp.toStringABC());
        temp.setName(newName);
        jsonTemp.write(temp); // 更新 temp 对象到 temp.txt
        System.out.println("Changed name write to temp: " + temp.toStringABC());
    }

    public static int findCurrentId(Temp temp) {
        int currentId;
        if (temp.isParent()) {
            currentId = temp.getParentId();
            System.out.println("Is Parent current ID is " + currentId);
        } else {
            currentId = temp.getChildId();
            System.out.println("Is Child current ID is " + currentId);
        }
        return currentId;
    }

    public static User findCurrentUser(List<User> userList, Temp temp) {
        int currentId = findCurrentId(temp);
        String currentUsername = String.valueOf(currentId);
        for (User user : userList) {
            if (user.getUsername().equals(currentUsername)) {
                return user;
            }
        }
        return null;
    }

    public static String findCurrentName() {
        JSONController jsonTemp = new JSONController("temp.txt");
        Temp temp = (Temp) jsonTemp.read(Temp.class);
        return temp.getName();
    }
}
