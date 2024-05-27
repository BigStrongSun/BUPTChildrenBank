package service.test;

import static org.junit.Assert.*;

import domain.Account;
import domain.AccountType;
import domain.Temp;

import org.junit.Before;
import org.junit.Test;
import service.CreateAccountService;
import util.GenerateRandomId;
import util.JSONController;

import java.util.List;

/**
 * The CreateAccountServiceTest class contains unit tests for the CreateAccountService class.
 */
public class CreateAccountServiceTest {
    private static JSONController jsonAccount;
    private static List<Account> accountList;
    private static JSONController jsonTemp;
    Temp temp = (Temp) jsonTemp.read(Temp.class);
    
    /**
     * Sets up the test environment by initializing the JSON controllers and reading the account and temp data from the JSON files.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        // Initialize CreateAccountService and read account and temp files
        jsonAccount = new JSONController("account.txt");
        accountList = jsonAccount.readArray(Account.class);
        jsonTemp = new JSONController("temp.txt");
    }

    /**
     * Tests the createNewSavingAccount method.
     */
    @Test
    public void testCreateNewSavingAccount() {
        String password = "12345";
        CreateAccountService.createNewSavingAccount(password);

        // Verify that a new saving account was created
        Account newAccount = accountList.get(accountList.size() - 1);
        assertNotNull(newAccount);
        assertEquals(AccountType.SAVING_ACCOUNT, newAccount.getAccountType());
        assertEquals(password, newAccount.getPassword());
        assertEquals(1.5, newAccount.getInterestRate(), 0.01);
        assertEquals(12, newAccount.getLockPeriod());
    }

    /**
     * Tests the createNewCurrentAccount method.
     */
    @Test
    public void testCreateNewCurrentAccount() {
        int accountId = GenerateRandomId.generateNewAccID();
        String password = "currentPassword";
        int newChildId = 12345;

        CreateAccountService.createNewCurrentAccount(accountId, password, newChildId);

        // Verify that a new current account was created
        Account newAccount = accountList.get(accountList.size() - 1);
        assertNotNull(newAccount);
        assertEquals(AccountType.CURRENT_ACCOUNT, newAccount.getAccountType());
        assertEquals(password, newAccount.getPassword());
        assertEquals(newChildId, newAccount.getUserId());
    }

    /**
     * Tests the createParentAccount method.
     */
    @Test
    public void testCreateParentAccount() {
        int accountId = GenerateRandomId.generateNewAccID();
        String password = "parentPassword";
        int newChildId = 12345;

        CreateAccountService.createParentAccount(accountId, password, newChildId);

        // Verify that a new parent account was created
        Account newAccount = accountList.get(accountList.size() - 1);
        assertNotNull(newAccount);
        assertEquals(AccountType.CURRENT_ACCOUNT, newAccount.getAccountType());
        assertEquals(password, newAccount.getPassword());
        assertEquals(newChildId, newAccount.getUserId());
        assertEquals(99999999.0, newAccount.getBalance(), 0.01);
    }
}
