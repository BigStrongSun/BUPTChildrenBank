package service;

import domain.Account;
import domain.Transaction;
import domain.TransactionType;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;


public class WalletService {
    private Map<Integer, Account> accounts = new HashMap<>(); // 用HashMap存储账户信息，键是账户ID（整型），值是Account对象
    private final String accountsFile = "accounts.txt"; // 存储账户数据的文件名
    private final String transactionsFile = "transactions.json"; // 存储交易记录的文件名
    private int transactionIdCounter = 1; // 交易ID计数器，用于生成交易ID

    public WalletService() {
        loadAccounts(); // 构造函数中加载账户信息
    }

    private void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) { // 使用BufferedReader读取账户文件
            String line;
            while ((line = reader.readLine()) != null) { // 逐行读取
                String[] parts = line.split(","); // 分割每行数据
                int accountId = Integer.parseInt(parts[0]); // 解析账户ID
                String password = parts[1]; // 解析密码
                double balance = Double.parseDouble(parts[2]); // 解析账户余额
                accounts.put(accountId, new Account(accountId, balance, password)); // 创建Account对象并加入到Map中
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage()); // 读取文件出错处理
        }
    }

    public boolean transfer(int senderId, int receiverId, String password, double amount) {
        Account sender = accounts.get(senderId); // 获取发送方账户
        Account receiver = accounts.get(receiverId); // 获取接收方账户

        if (sender == null || receiver == null) { // 检查账户是否存在
            System.err.println("Account not found.");
            return false;
        }

        if (!sender.getPassword().equals(password)) { // 验证密码
            System.err.println("Incorrect password.");
            return false;
        }

        double fee = amount * 0.0001; // 计算手续费，0.01%
        double totalAmount = amount + fee; // 总扣款金额

        if (sender.getBalance() >= totalAmount) { // 检查余额是否足够
            sender.setBalance(sender.getBalance() - totalAmount); // 扣除总金额
            receiver.setBalance(receiver.getBalance() + amount); // 接收方增加金额
            recordTransaction(senderId, receiverId, amount, fee); // 记录交易
            return true;
        } else {
            System.err.println("Insufficient funds."); // 余额不足处理
            return false;
        }
    }

    private void recordTransaction(int senderId, int receiverId, double amount, double fee) {
        Transaction transaction = new Transaction(
                transactionIdCounter++, // 生成交易ID
                TransactionType.TRANSFER, // 设置交易类型
                senderId,
                receiverId,
                amount,
                fee,
                "Transfer between accounts", // 交易描述
                new Date() // 设置交易日期
        );
        writeTransactionToFile(transaction); // 将交易信息写入文件
    }

    private void writeTransactionToFile(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionsFile, true))) { // 以追加模式写入交易文件
            String json = JSON.toJSONString(transaction); // 使用 fastjson 将交易对象转换为JSON字符串
            writer.write(json + System.lineSeparator()); // 写入文件
        } catch (IOException e) {
            System.err.println("Failed to record transaction: " + e.getMessage()); // 写入文件出错处理
        }
    }

    // 更新账户信息到文件
    private void updateAccountsFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(accountsFile))) {
            for (Account account : accounts.values()) { // 遍历所有账户
                String line = String.join(",",
                        String.valueOf(account.getAccountId()),
                        account.getPassword(),
                        String.valueOf(account.getBalance())
                );
                writer.write(line + System.lineSeparator()); // 写入新的账户信息
            }
        } catch (IOException e) {
            System.err.println("Failed to update accounts: " + e.getMessage()); // 更新账户信息失败处理
        }
    }
}
