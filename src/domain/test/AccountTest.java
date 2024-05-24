package domain.test;

import static org.junit.Assert.assertEquals;

import domain.Account;
import domain.AccountType;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {
    private Account account;
    private int accountId = 101010;
    private AccountType accountType = AccountType.SAVING_ACCOUNT; // 假设有一个枚举 AccountType
    private double balance = 1000.0;
    private int userId = 501;
    private String password = "securePassword123";

    @Before
    public void setUp() {
        account = new Account(accountId, accountType, balance, userId, password);
    }

    @Test
    public void testAccountId() {
        assertEquals("Account ID should match", accountId, account.getAccountId());
    }

    @Test
    public void testAccountType() {
        assertEquals("Account type should match", accountType, account.getAccountType());
    }

    @Test
    public void testBalance() {
        assertEquals("Balance should match", balance, account.getBalance(), 0.0);
    }

    @Test
    public void testUserId() {
        assertEquals("User ID should match", userId, account.getUserId());
    }

    @Test
    public void testPassword() {
        assertEquals("Password should match", password, account.getPassword());
    }
}
