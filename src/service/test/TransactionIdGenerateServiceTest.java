package service.test;

import static org.junit.Assert.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Transaction;
import domain.TransactionType;
import org.junit.Before;
import org.junit.Test;
import service.TransactionIdGenerateService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The TransactionIdGenerateServiceTest class contains unit tests for the TransactionIdGenerateService class.
 */
public class TransactionIdGenerateServiceTest {
    private static final String TRANSACTION_FILE = "transaction.txt";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Sets up the test environment by resetting the test transaction data.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        // Reset test transaction data
        resetTestTransactions();
    }

    /**
     * Resets the test transaction data.
     *
     * @throws IOException if an error occurs while writing the test data
     */
    private void resetTestTransactions() throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1, TransactionType.BONUS, 1001, 1002, 100.0, 1.0, "Test transaction 1", new Date()));
        transactions.add(new Transaction(2, TransactionType.WITHDRAWAL, 1003, 1004, 200.0, 2.0, "Test transaction 2", new Date()));
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(TRANSACTION_FILE), transactions);
    }

    /**
     * Tests the getLastTransactionId method.
     */
    @Test
    public void testGetLastTransactionId() {
        // Verify the last transaction ID
        int lastId = TransactionIdGenerateService.getLastTransactionId();
        assertEquals(2, lastId);
    }

    /**
     * Tests the generateTransactionId method.
     */
    @Test
    public void testGenerateTransactionId() {
        // Verify the generated new transaction ID
        int newId = TransactionIdGenerateService.generateTransactionId();
        assertEquals(3, newId);
    }

    /**
     * Tests the addTransaction method.
     *
     * @throws IOException if an error occurs while reading the transactions
     */
    @Test
    public void testAddTransaction() throws IOException {
        // Add a new transaction
        Transaction newTransaction = new Transaction(3, TransactionType.TRANSFER, 1005, 1006, 300.0, 3.0, "Test transaction 3", new Date());
        TransactionIdGenerateService.addTransaction(newTransaction);

        // Verify that the transaction is correctly added
        List<Transaction> transactions = readTransactions();
        assertEquals(3, transactions.size());
        Transaction addedTransaction = transactions.get(transactions.size() - 1);
        assertEquals(3, addedTransaction.getTransactionId());
        assertEquals(TransactionType.TRANSFER, addedTransaction.getType());
        assertEquals(1005, addedTransaction.getSenderAccountId());
        assertEquals(1006, addedTransaction.getReceiverAccountId());
        assertEquals(300.0, addedTransaction.getAmount(), 0.01);
        assertEquals(3.0, addedTransaction.getFee(), 0.01);
        assertEquals("Test transaction 3", addedTransaction.getDescription());
        assertNotNull(addedTransaction.getTransactionDate());
    }

    /**
     * Helper method: Reads the list of transactions from the transaction file.
     *
     * @return the list of transactions
     * @throws IOException if an error occurs while reading the file
     */
    private List<Transaction> readTransactions() throws IOException {
        File file = new File(TRANSACTION_FILE);
        List<Transaction> transactions = new ArrayList<>();
        if (file.exists() && file.length() > 0) {
            transactions = objectMapper.readValue(file, new TypeReference<List<Transaction>>(){});
        }
        return transactions;
    }
}
