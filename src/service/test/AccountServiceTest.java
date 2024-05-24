package service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import domain.Account;
import domain.AccountType;
import org.junit.Before;
import org.junit.Test;
import service.AccountService;
import util.JSONController;
import java.util.List;

public class AccountServiceTest {
    private AccountService accountService;
    List<Account> accounts;
    JSONController jsonController = new JSONController("account.txt");
    @Before
    public void setUp() throws Exception {
        accountService = new AccountService();
        accounts = jsonController.readArray(Account.class);
    }

    @Test
    public void getMaxAccountId() {
        assertEquals(887, accountService.getMaxAccountId());
    }

    @Test
    public void deleteAccountTest() {
        Account account1 = new Account();
        account1.setAccountId(1111);
        Account account2 = new Account();
        account2.setAccountId(2222);

        // 添加账户到服务的账户列表中
        accountService.getAccounts().add(account1);
        accountService.getAccounts().add(account2);

        accountService.saveAccounts();
        accountService.deleteAccount(2222);

        // 断言，检查是否删除了正确的账户
        assertEquals(1111, accountService.getMaxAccountId());
    }

    @Test
    public void getAccountById() {
        Account account1 = new Account();
        account1.setAccountId(1111);
        Account account2 = new Account();
        account2.setAccountId(2222);
        Account account3 = new Account();
        account3.setAccountId(3333);
        // 添加账户到服务的账户列表中
        accountService.getAccounts().add(account1);
        accountService.getAccounts().add(account2);
        accountService.getAccounts().add(account3);
        accountService.saveAccounts();
        Account retrievedAccount = accountService.getAccountById(2222);
        assertNotNull(retrievedAccount);
        assertEquals(2222, retrievedAccount.getAccountId());
    }

    @Test
    public void getBalance() {
        accountService.deleteAccount(1111);
        Account account1 = new Account();
        account1.setAccountId(1111);
        account1.setBalance(0);
        // 添加账户到服务的账户列表中
        accountService.getAccounts().add(account1);
        accountService.saveAccounts();
        accountService.addBalance(1111,100);
        assertEquals(100,accountService.getAccountById(1111).getBalance(),0.0);
    }
    @Test
    public void getCurrentAccountByUserId() {
        Account account1 = new Account();
        account1.setAccountId(6666);
        account1.setUserId(6666);
        account1.setAccountType(AccountType.CURRENT_ACCOUNT);
        Account account2 = new Account();
        account2.setAccountId(7777);
        account2.setUserId(7777);
        account2.setAccountType(AccountType.CURRENT_ACCOUNT);
        Account account3 = new Account();
        account3.setAccountId(8888);
        account3.setUserId(8888);
        account3.setAccountType(AccountType.CURRENT_ACCOUNT);

        // 添加账户到服务的账户列表中
        accountService.getAccounts().add(account1);
        accountService.getAccounts().add(account2);
        accountService.getAccounts().add(account3);
        accountService.saveAccounts();
        assertEquals(8888,accountService.getCurrentAccountByUserId(8888).getAccountId(),0.0);
    }

}
