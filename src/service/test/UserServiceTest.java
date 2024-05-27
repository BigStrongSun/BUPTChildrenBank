package service.test;

import static org.junit.Assert.*;

import domain.User;
import org.junit.Before;
import org.junit.Test;
import service.UserService;
import util.JSONController;

import java.util.List;

/**
 * The UserServiceTest class contains unit tests for the UserService class.
 */
public class UserServiceTest {
    private UserService userService;
    private static JSONController jsonUser;
    private static List<User> users;

    /**
     * Sets up the test environment by initializing the UserService instance and reading user data from the JSON file.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        // Initialize UserService and read user file
        jsonUser = new JSONController("user.txt");
        users = jsonUser.readArray(User.class);
        userService = new UserService();
    }

    /**
     * Tests the getUserById method.
     */
    @Test
    public void testGetUserById() {
        // Verify retrieving a user by user ID
        User user = userService.getUserById(1989);
        assertNotNull(user);
        assertEquals("1989", user.getUsername());

        // Verify retrieving a non-existent user ID
        User nonExistentUser = userService.getUserById(999);
        assertNull(nonExistentUser);
    }

    /**
     * Tests the getChildNameById method.
     */
    @Test
    public void testGetChildNameById() {
        // Verify retrieving a child's name by child ID
        String childName = userService.getChildNameById(222);
        assertEquals("Kiki Qian", childName);

        // Verify retrieving a child's name by parent ID
        String childNameByParent = userService.getChildNameById(1989);
        assertEquals("Kiki Qian", childNameByParent);

        // Verify retrieving a non-existent user ID
        String nonExistentChildName = userService.getChildNameById(999);
        assertNull(nonExistentChildName);
    }

    /**
     * Tests the getParentNameById method.
     */
    @Test
    public void testGetParentNameById() {
        // Verify retrieving a parent's name by parent ID
        String parentName = userService.getParentNameById(1989);
        assertEquals("lisa", parentName);

        // Verify retrieving a parent's name by child ID
        String parentNameByChild = userService.getParentNameById(222);
        assertEquals("lisa", parentNameByChild);

        // Verify retrieving a non-existent user ID
        String nonExistentParentName = userService.getParentNameById(999);
        assertNull(nonExistentParentName);
    }
}
