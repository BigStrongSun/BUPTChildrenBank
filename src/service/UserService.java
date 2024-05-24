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
                return user;
            }
        }
        return null;
    }

    public String getChildNameById(int userId) {
        User user = getUserById(userId);
        if (user != null) {
            if (user.getIdentity().equals("child")) {
                return user.getName();
            } else {
                int childId = user.getChildOrParentId();
                user = getUserById(childId);
                return user.getName();
            }
        }
        return null;
    }
}
