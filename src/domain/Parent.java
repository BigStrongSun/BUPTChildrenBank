package domain;

public class Parent extends User{
    User child;
    public Parent(String username, String password, String identity) {
        super(username, password, identity);
    }

}
