package domain;

import java.util.Date; // 导入Date类，用于处理日期

// 交易类型的枚举，定义可能的交易类型
enum TransactionType {
    TRANSFER, WITHDRAWAL, GIFT_EXCHANGE, BONUS
}

public class Transaction {
    private String transactionId; // 交易ID，唯一标识一笔交易
    private TransactionType type; // 交易类型
    private String senderAccountId; // 发送方账户ID
    private String receiverAccountId; // 接收方账户ID
    private double amount; // 交易金额
    private double fee; // 交易手续费
    private String description; // 交易描述，例如“做家务的奖励”，“买玩具的支出”
    private Date transactionDate; // 交易日期和时间

    /**
     * 交易信息的构造函数，用于创建一个新的交易实例
     * @param transactionId 交易的唯一标识符
     * @param type 交易类型
     * @param senderAccountId 发送方账户ID
     * @param receiverAccountId 接收方账户ID
     * @param amount 交易金额
     * @param fee 交易手续费
     * @param description 交易的具体描述
     * @param transactionDate 交易发生的日期和时间
     */
    public Transaction(String transactionId, TransactionType type, String senderAccountId, String receiverAccountId, double amount, double fee, String description, Date transactionDate) {
        this.transactionId = transactionId;
        this.type = type;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.fee = fee;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    // 下面是一系列getter和setter方法，用于访问和修改交易对象的属性

    public String getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public String getReceiverAccountId() {
        return receiverAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public double getFee() {
        return fee;
    }

    public String getDescription() {
        return description;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setSenderAccountId(String senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public void setReceiverAccountId(String receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
