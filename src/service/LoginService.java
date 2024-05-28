package service;

import domain.Temp;
import util.JSONController;
import java.util.List;
import domain.User;

import static java.lang.Integer.parseInt;

public class LoginService {
    private static JSONController json2 = new JSONController("user.txt");
    private static JSONController json = new JSONController("temp.txt");
    static List<User> userList = json2.readArray(User.class);

    public static void saveCurrentUser(String username, boolean isParent, String name) {
        int userId = parseInt(username);
        TempService tempService = new TempService();
        Temp temp = tempService.getTemp();

        temp.setParent(isParent);
        temp.setName(name);
        System.out.println("temp when logging in: " + temp.toStringABC());

        int childOrParentId = 0;
        userList = json2. readArray(User.class);
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                System.out.println("ChildOrParentId: " + user.getChildOrParentId());
                childOrParentId = user.getChildOrParentId();
            }
        }

        if (isParent) { // 是家长
            temp.setParentId(userId);
            temp.setChildId(childOrParentId);
        } else { // 是孩子
            temp.setChildId(userId);
            temp.setParentId(childOrParentId);
        }
        tempService.setTemp(temp);
        System.out.println("write to temp is"+temp.toStringABC());
    }

    public static void saveUser(String username, String password, String identity) {
        User newUser = new User(username, password, identity, 0, "name");
        userList.add(newUser);
        json2.writeArray(userList);
    }

    public static boolean usernameExist(String username) {
        System.out.println(username);
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validatePassword(String username, String password) {
        for (User user : userList) { // 检查输入的信息是否匹配文件中的信息
            if (user.getUsername().equals(username)) {
                return user.getPassword().equals(password);
            }
        }
        return false;
    }

    public static boolean checkIfIsParent(String username) { // 检查用户名是否是家长用户名
        for (User user : userList) {
            if (user.getUsername().equals(username)) { // 用户名是唯一的，所以只需要使用用户名来识别
                if (user.getIdentity().equals("parent")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String findName(String userId) { // 通过userId找到对应用户的名字并返回
        for (User user : userList) {
            if (user.getUsername().equals(userId)) {
                return user.getName();
            }
        }
        return null;
    }
}
