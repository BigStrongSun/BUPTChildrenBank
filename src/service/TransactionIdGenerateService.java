package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import domain.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionIdGenerateService {

    private static final String TRANSACTION_FILE = "transaction.txt";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static int getLastTransactionId() {
        int lastId = 0;
        List<Transaction> transactions = readTransactions();
        if (!transactions.isEmpty()) {
            Transaction lastTransaction = transactions.get(transactions.size() - 1);
            lastId = lastTransaction.getTransactionId();
        }
        return lastId;
    }

    public static int generateTransactionId() {
        return getLastTransactionId() + 1;
    }

    private static List<Transaction> readTransactions() {
        File file = new File(TRANSACTION_FILE);
        List<Transaction> transactions = new ArrayList<>();
        if (file.exists() && file.length() > 0) {
            try {
                transactions = objectMapper.readValue(file, new TypeReference<List<Transaction>>(){});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }

    public static void addTransaction(Transaction newTransaction) {
        List<Transaction> transactions = readTransactions();
        transactions.add(newTransaction); // 添加新交易到列表
        try {
            // 覆盖模式写入整个更新后的列表
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(TRANSACTION_FILE), transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
