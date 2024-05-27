package service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import domain.Temp;
import domain.User;
import org.junit.Before;
import org.junit.Test;
import service.ChangeNameService;
import util.JSONController;
import java.util.List;

/**
 * The ChangeNameServiceTest class contains unit tests for the ChangeNameService class.
 */
public class ChangeNameServiceTest {
    private static JSONController jsonUser;
    private static List<User> userList;
    private static JSONController jsonTemp;
    private static Temp temp;

    /**
     * Sets up the test environment by initializing JSON controllers and reading data from the JSON files.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        jsonUser = new JSONController("user.txt");
        userList = jsonUser.readArray(User.class);
        jsonTemp = new JSONController("temp.txt");
        temp = (Temp) jsonTemp.read(Temp.class);
    }

    /**
     * Tests the changeName method.
     */
    @Test
    public void testChangeName() {
        // Prepare test data
        String newName = "NewUserName";
        User currentUser = ChangeNameService.findCurrentUser();
        
        // Ensure the current user is not null
        assertNotNull(currentUser);
        
        // Execute the method to change the name
        ChangeNameService.changeName(newName);
        
        // Verify that the Temp file content has been updated
        assertEquals(newName, temp.getName());
        
        // Verify that the current user's name in userList has been updated
        assertEquals(newName, currentUser.getName());
    }

    /**
     * Tests the findCurrentId method.
     */
    @Test
    public void testFindCurrentId() {
        // Verify the correctness of findCurrentId method based on the Temp file content
        int expectedId = temp.isParent() ? temp.getParentId() : temp.getChildId();
        int actualId = ChangeNameService.findCurrentId();
        assertEquals(expectedId, actualId);
    }

    /**
     * Tests the findCurrentUser method.
     */
    @Test
    public void testFindCurrentUser() {
        // Find the current user and verify
        User expectedUser = null;
        for (User user : userList) {
            if (user.getUsername().equals(String.valueOf(ChangeNameService.findCurrentId()))) {
                expectedUser = user;
                break;
            }
        }
        User actualUser = ChangeNameService.findCurrentUser();
        assertEquals(expectedUser, actualUser);
    }

    /**
     * Tests the findCurrentName method.
     */
    @Test
    public void testFindCurrentName() {
        // Verify that the findCurrentName method correctly returns the current user name
        String actualName = ChangeNameService.findCurrentName();
        assertEquals("NewUserName", actualName);
    }
}
