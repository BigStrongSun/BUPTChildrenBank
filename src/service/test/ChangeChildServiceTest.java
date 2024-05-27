package service.test;

import static org.junit.Assert.assertEquals;

import domain.User;

import org.junit.Test;
import service.ChangeChildService;
import util.JSONController;
import java.util.List;

/**
 * The ChangeChildServiceTest class contains unit tests for the ChangeChildService class.
 */
public class ChangeChildServiceTest {
    private ChangeChildService changeChildService;
    static JSONController jsonUser = new JSONController("user.txt");
    static List<User> userList = jsonUser.readArray(User.class);

    /**
     * Tests the clearAssociation method with valid IDs.
     */
    @Test
    public void testClearAssociationValidIds() {
        // Prepare test data
        int parentId = 100;
        int childId = 211;
        // Execute the method
        ChangeChildService.clearAssociation(parentId, childId);
        // Assert that the parent and child are unbound
        User parent = getUserById(parentId);
        User child = getUserById(childId);
        assertEquals(0, parent.getChildOrParentId());
        assertEquals(0, child.getChildOrParentId());
    }

    /**
     * Tests the changeChild method with valid IDs.
     */
    @Test
    public void testChangeChildValidIds() {
        // Prepare test data
        int currentUserId = 222;
        int toChangeId = 1989;
        // Execute the method
        ChangeChildService.changeChild(currentUserId, toChangeId);
        // Assert that the current user and the user to be bound are bound
        User currentUser = getUserById(currentUserId);
        User toChangeUser = getUserById(toChangeId);
        assertEquals(toChangeId, currentUser.getChildOrParentId());
        assertEquals(currentUserId, toChangeUser.getChildOrParentId());
    }

    /**
     * Helper method: Retrieves a user by ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user with the specified ID, or null if not found
     */
    private User getUserById(int userId) {
        for (User user : userList) {
            if (Integer.parseInt(user.getUsername()) == userId) {
                return user;
            }
        }
        return null;
    }
}
