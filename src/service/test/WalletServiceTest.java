package service.test;

import domain.Account;
import domain.Transaction;
import org.junit.Before;
import org.junit.Test;
import service.AccountService;
import service.WalletService;
import util.JSONController;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class WalletServiceTest {
    private WalletService walletService;
    private  AccountService accountService;
    List<Account> accounts;
    List<Transaction> transactions;
    JSONController jsonAccount = new JSONController("account.txt");
    JSONController jsonTrans = new JSONController("transaction.txt");

    @Before
    public void setUp() throws Exception {
        accountService = new AccountService();
        walletService = new WalletService();
        accounts = jsonAccount.readArray(Account.class);
        transactions = jsonTrans.readArray(Transaction.class);
    }

    @Test
    public void getMaxTransId() {
        assertEquals(105, walletService.getMaxTransId());
    }

    @Test
    public void createTrans(){
        Transaction transaction = new Transaction();
        transaction.setAmount(200);
        walletService.createTrans(transaction);
        assertEquals(200,walletService.getTransactionbyId(107).getAmount(),0.0);
    }
}
