package service.test;

import static org.junit.Assert.*;

import domain.User;
import org.junit.Before;
import org.junit.Test;
import service.ChangeProfileService;
import service.UserService;
import util.JSONController;

import java.util.List;

/**
 * The ChangeProfileServiceTest class contains unit tests for the ChangeProfileService class.
 */
public class ChangeProfileServiceTest {
    private static JSONController jsonUser;
    private static List<User> userList;

    /**
     * Sets up the test environment by initializing the JSON controller and reading the user data from the JSON file.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        // Initialize ChangeProfileService and read user file
        jsonUser = new JSONController("user.txt");
        userList = jsonUser.readArray(User.class);
    }

    /**
     * Tests the changeUsername method.
     */
    @Test
    public void testChangeUsername() {
        // Change username
        ChangeProfileService.changeUsername("100", "123", "101");

        // Verify that the new username is correct
        User updatedUser = getUserByUsername("101");
        assertNotNull(updatedUser);
        assertEquals("101", updatedUser.getUsername());

        // Verify that the old username no longer exists
        User oldUser = getUserByUsername("100");
        assertNull(oldUser);
    }

    /**
     * Tests the changeCoPId method.
     */
    @Test
    public void testChangeCoPId() {
        UserService us = new UserService();
        // Change the associated childOrParentId
        ChangeProfileService.changeCoPId("100", "101");

        // Verify that the childOrParentId is correct
        User updatedChildUser = us.getUserById(101);
        assertNotNull(updatedChildUser);
        assertEquals(0, updatedChildUser.getChildOrParentId());
    }

    /**
     * Helper method: Retrieves a user by username.
     *
     * @param username the username of the user to retrieve
     * @return the user with the specified username, or null if not found
     */
    private User getUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
