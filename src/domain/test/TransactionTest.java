package domain.test;

import static org.junit.Assert.assertEquals;

import domain.Transaction;
import domain.TransactionType;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

public class TransactionTest {
    private Transaction transaction;
    private int transactionId = 100;
    private TransactionType type = TransactionType.BONUS;
    private int senderAccountId = 200;
    private int receiverAccountId = 300;
    private double amount = 1500.50;
    private double fee = 15.50;
    private String description = "做家务的奖励";
    private Date transactionDate = new Date(); // 使用当前日期和时间

    @Before
    public void setUp() {
        transaction = new Transaction(transactionId, type, senderAccountId, receiverAccountId, amount, fee, description, transactionDate);
    }

    @Test
    public void testTransactionId() {
        assertEquals("Transaction ID should match", transactionId, transaction.getTransactionId());
    }

    @Test
    public void testType() {
        assertEquals("Transaction type should match", type, transaction.getType());
    }

    @Test
    public void testSenderAccountId() {
        assertEquals("Sender account ID should match", senderAccountId, transaction.getSenderAccountId());
    }

    @Test
    public void testReceiverAccountId() {
        assertEquals("Receiver account ID should match", receiverAccountId, transaction.getReceiverAccountId());
    }

    @Test
    public void testAmount() {
        assertEquals("Transaction amount should match", amount, transaction.getAmount(), 0.0);
    }

    @Test
    public void testFee() {
        assertEquals("Transaction fee should match", fee, transaction.getFee(), 0.0);
    }

    @Test
    public void testDescription() {
        assertEquals("Description should match", description, transaction.getDescription());
    }

    @Test
    public void testTransactionDate() {
        assertEquals("Transaction date should match", transactionDate, transaction.getTransactionDate());
    }
}

