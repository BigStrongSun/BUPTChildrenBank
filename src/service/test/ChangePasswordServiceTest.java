package service.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import domain.User;
import org.junit.Before;
import org.junit.Test;
import service.ChangePasswordService;
import util.JSONController;

import java.util.List;

/**
 * The ChangePasswordServiceTest class contains unit tests for the ChangePasswordService class.
 */
public class ChangePasswordServiceTest {
    private ChangePasswordService changePasswordService;
    private static JSONController jsonUser;
    private static List<User> userList;

    /**
     * Sets up the test environment by initializing the ChangePasswordService instance
     * and reading user data from the JSON file.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        // Initialize ChangePasswordService and read user file
        changePasswordService = new ChangePasswordService();
        jsonUser = new JSONController("user.txt");
        userList = jsonUser.readArray(User.class);
    }

    /**
     * Tests the validatePassword method.
     */
    @Test
    public void testValidatePassword() {
        // Validate correct username and password
        assertTrue(ChangePasswordService.validatePassword("100", "123"));
        
        // Validate incorrect username
        assertFalse(ChangePasswordService.validatePassword("wronguser", "oldpassword"));
        
        // Validate incorrect password
        assertFalse(ChangePasswordService.validatePassword("testuser", "wrongpassword"));
    }

    /**
     * Tests the changeUserPassword method.
     */
    @Test
    public void testChangeUserPassword() {
        // Change user password
        ChangePasswordService.changeUserPassword("211", "122");

        // Validate new password
        assertTrue(ChangePasswordService.validatePassword("211", "122"));

        // Validate old password is no longer valid
        assertFalse(ChangePasswordService.validatePassword("211", "123"));
    }
}
