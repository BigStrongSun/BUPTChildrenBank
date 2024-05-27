package service;

import domain.Account;
import domain.User;
import util.JSONController;

import java.util.List;

public class UserService {
    private List<User> users;
    private JSONController jsonUser = new JSONController("user.txt");

    public UserService() {
        users = jsonUser.readArray(User.class);
    }

    public List<User> getUserlist(){
        return users;
    }
    public User getUserById(int userId) {
        for (User user : users) {
            if (user.getUsername().equals(String.valueOf(userId))) {

                System.out.println(user);
                return user;
            }

        }
        return null;
    }

    public String getChildNameById(int userId) {
        // 根据 userId 获取用户对象
        User user = getUserById(userId);
        System.out.println(userId);
        if (user != null) { // 检查用户是否存在
            if (user.getIdentity().equals("child")) { // 如果用户是孩子
                return user.getName(); // 返回用户的名字
            } else { // 如果用户不是孩子
                int childId = user.getChildOrParentId(); // 获取与该用户关联的孩子的 ID
                user = getUserById(childId); // 根据孩子的 ID 获取用户对象
                if (user != null) { // 检查孩子是否存在
                    return user.getName(); // 返回孩子的名字
                } else {
                    System.err.println("Child with ID " + childId + " not found."); // 打印错误信息
                    return null; // 返回 null
                }
            }
        } else {
            System.err.println("User with ID " + userId + " not found."); // 打印错误信息
            return null; // 返回 null
        }
    }


    public String getParentNameById(int userId) {
        User user = getUserById(userId);
        if (user != null) {
            if (user.getIdentity().equals("parent")) {
                return user.getName();
            } else {
                int parentId = user.getChildOrParentId();
                user = getUserById(parentId);
                if (user != null) {
                    return user.getName();
                }
            }
        }
        return null;
    }
}
