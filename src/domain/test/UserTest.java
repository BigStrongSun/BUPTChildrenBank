package domain.test;

import static org.junit.Assert.assertEquals;

import domain.User;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User("johnDoe", "password123", "parent", 100, "John Doe");
    }

    @Test
    public void testUsername() {
        assertEquals("Username should match", "johnDoe", user.getUsername());
    }

    @Test
    public void testPassword() {
        assertEquals("Password should match", "password123", user.getPassword());
    }

    @Test
    public void testIdentity() {
        assertEquals("Identity should match", "parent", user.getIdentity());
    }

    @Test
    public void testChildOrParentId() {
        assertEquals("Child or Parent ID should match", 100, user.getChildOrParentId());
    }

    @Test
    public void testName() {
        assertEquals("Name should match", "John Doe", user.getName());
    }
}
