package service.test;

import domain.Task;
import domain.Wish;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.WishService;

import static org.junit.Assert.*;

public class WishServiceTest {
    private WishService wishService;

    @Before
    public void setUp() throws Exception {
        wishService = new WishService();
    }

    @Test
    public void getMaxWishId() {
        assertEquals(12,wishService.getMaxWishId());
    }

    @Test
    public void modifyWish() {
        Wish wish = new Wish();
        wish.setWishId(11);
        wish.setWishName("test_modifyWish_wishName");
        wish.setParentId(1);
        wish.setChildId(2);
        wish.setWishDescription("test_modifyWish_wishDescription");
        wish.setDeadline("2024-07-19 14:04");
        wish.setWishTarget("1");
        wish.setWishStatus("undone");
        wish.setWishProgress("50");
        assertEquals(1, wishService.modifyWish(wish));
        assertEquals("test_modifyWish_wishName", wish.getWishName());
        assertEquals("test_modifyWish_wishDescription", wish.getWishDescription());
        assertEquals("2024-07-19 14:04", wish.getDeadline());
        assertEquals("undone", wish.getWishStatus());
        assertEquals("1", wish.getWishTarget());
    }

    @Test
    public void modifyWishStatusAndProgress() {
        Wish wish = new Wish();
        wish.setWishId(11);
        wish.setWishStatus("done");
        assertEquals(1, wishService.modifyWishStatusAndProgress(wish));
        assertEquals("done", wish.getWishStatus());
    }

    @Test
    public void getWishById() {
        Wish wish1 = new Wish();
        wish1.setWishId(1);
        Wish wish2 = new Wish();
        wish2.setWishId(2);
        Wish wish3 = new Wish();
        wish3.setWishId(3);
        wishService.saveWishes();
        Wish retrievedTask = wishService.getWishById(2);
        assertNotNull(retrievedTask);
        assertEquals(2, retrievedTask.getWishId());
    }

}