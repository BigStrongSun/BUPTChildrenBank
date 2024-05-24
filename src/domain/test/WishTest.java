package domain.test;

import domain.Wish;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WishTest {
    private Wish wish;

    @Before
    public void setUp() {
        wish = new Wish();
        wish.setWishId(100);
        wish.setWishName("Birthday Gift");
        wish.setWishDescription("A new bicycle");
        wish.setDeadline("2024-05-24");
        wish.setWishStatus("Pending");
        wish.setWishProgress("150");
        wish.setWishTarget("999");
        wish.setParentId(10);
        wish.setChildId(20);
    }

    @Test
    public void testWishId() {
        assertEquals("Wish ID should be 100", 100, wish.getWishId());
    }

    @Test
    public void testWishName() {
        assertEquals("Wish name should match", "Birthday Gift", wish.getWishName());
    }

    @Test
    public void testWishDescription() {
        assertEquals("Wish description should match", "A new bicycle", wish.getWishDescription());
    }

    @Test
    public void testDeadline() {
        assertEquals("Deadline should match", "2024-05-24", wish.getDeadline());
    }

    @Test
    public void testWishStatus() {
        assertEquals("Wish status should be pending", "Pending", wish.getWishStatus());
    }

    @Test
    public void testWishProgress() {
        assertEquals("Wish progress should be 'Not Started'", "150", wish.getWishProgress());
    }

    @Test
    public void testWishTarget() {
        assertEquals("Wish target should match", "999", wish.getWishTarget());
    }

    @Test
    public void testParentId() {
        assertEquals("Parent ID should match", 10, wish.getParentId());
    }

    @Test
    public void testChildId() {
        assertEquals("Child ID should match", 20, wish.getChildId());
    }
}

