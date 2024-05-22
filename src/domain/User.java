package domain;

public class User {
    private String username;
    private String password;
    private String identity;
    private int childOrParentId;
    private String name;

    public User(String username, String password, String identity, int childOrParentId, String name) {
        this.username = username;
        this.password = password;
        this.identity = identity;
        this.childOrParentId = childOrParentId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
        //if(identity.equals("parent")){isParent = true;}
    }

    public User(String username, String password, String identity) {
        this.username = username;
        this.password = password;
        this.identity = identity;
        //if(identity.equals("parent")){isParent = true;}
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {//for password validation method
        this.username = username; this.password =password;
    }

    public int getChildOrParentId() {
        return childOrParentId;
    }

    public void setChildOrParentId(int childOrParentId) {
        this.childOrParentId = childOrParentId;
    }

    public User(String username, String password, String identity, int childOrParentId) {
        this.username = username;
        this.password = password;
        this.identity = identity;
        this.childOrParentId = childOrParentId;
    }
}
