package service.test;

import static org.junit.Assert.*;

import domain.Temp;
import domain.User;
import org.junit.Before;
import org.junit.Test;
import service.LoginService;
import service.TempService;
import util.JSONController;

import java.util.List;

/**
 * The LoginServiceTest class contains unit tests for the LoginService class.
 */
public class LoginServiceTest {
    private static JSONController jsonUser;
    private static List<User> userList;
    private static TempService tempService = new TempService();

    /**
     * Sets up the test environment by initializing the JSON controller and reading the user data from the JSON file.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        // Initialize LoginService and read user file
        jsonUser = new JSONController("user.txt");
        userList = jsonUser.readArray(User.class);
    }

    /**
     * Tests the saveCurrentUser method for saving a parent user.
     */
    @Test
    public void testSaveCurrentUserAsParent() {
        // Simulate saving the current user as a parent
        LoginService.saveCurrentUser("333", true, "ParentUser");

        // Verify that the Temp file content is correctly updated
        Temp temp = new TempService().getTemp();
        assertTrue(temp.isParent());
        assertEquals("ParentUser", temp.getName());
        assertEquals(0, temp.getChildId());
        assertEquals(333, temp.getParentId());
    }

    /**
     * Tests the saveCurrentUser method for saving a child user.
     */
    @Test
    public void testSaveCurrentUserAsChild() {
        // Simulate saving the current user as a child
        LoginService.saveCurrentUser("444", false, "ChildUser");

        // Verify that the Temp file content is correctly updated
        Temp temp = new TempService().getTemp();
        assertFalse(temp.isParent());
        assertEquals("ChildUser", temp.getName());
        assertEquals(0, temp.getParentId());
        assertEquals(444, temp.getChildId());
    }

    /**
     * Tests the saveUser method.
     */
    @Test
    public void testSaveUser() {
        // Simulate saving a new user
        String username = "newuser";
        String password = "newpassword";
        String identity = "child";
        String name = "New User";
        LoginService.saveUser(username, password, identity, name);

        // Verify that the new user is correctly saved
        User newUser = getUserByUsername(username);
        assertNotNull(newUser);
        assertEquals(username, newUser.getUsername());
        assertEquals(password, newUser.getPassword());
        assertEquals(identity, newUser.getIdentity());
        assertEquals(name, newUser.getName());
    }

    /**
     * Tests the usernameExist method.
     */
    @Test
    public void testUsernameExist() {
        // Verify an existing username
        assertTrue(LoginService.usernameExist("1989"));
        // Verify a non-existing username
        assertFalse(LoginService.usernameExist("nonexistent"));
    }

    /**
     * Tests the validatePassword method.
     */
    @Test
    public void testValidatePassword() {
        // Verify correct username and password
        assertTrue(LoginService.validatePassword("1989", "123"));
        // Verify incorrect username
        assertFalse(LoginService.validatePassword("wronguser", "123"));
        // Verify incorrect password
        assertFalse(LoginService.validatePassword("1989", "wrongpassword"));
    }

    /**
     * Tests the checkIfIsParent method.
     */
    @Test
    public void testCheckIfIsParent() {
        // Verify a parent username
        assertTrue(LoginService.checkIfIsParent("222"));
        // Verify a non-parent username
        assertFalse(LoginService.checkIfIsParent("1989"));
    }

    /**
     * Tests the findName method.
     */
    @Test
    public void testFindName() {
        // Verify finding a name by userId
        assertEquals("Kiki Qian", LoginService.findName("1989"));
        assertEquals("lisa", LoginService.findName("222"));
        assertNull(LoginService.findName("nonexistent"));
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
