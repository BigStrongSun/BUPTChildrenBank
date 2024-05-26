package domain;

import java.time.LocalDateTime; // 导入LocalDateTime类用于处理时间

public class Account {
    private int accountId;
    private AccountType accountType;
    private double balance;
    private int userId;
    private String password;

    // 新增字段
    private double interestRate; // 利息率
    private int lockPeriod; // 封闭期（以月为单位）
    private LocalDateTime depositTime; // 存入时间
    private LocalDateTime lockEndTime; // 封闭期结束时间
    private boolean inLockPeriod; // 标志账户是否处于封闭期

    private LocalDateTime lastReadTime;//最后一次刷新读取的时间

    // 原有构造器
    public Account(int accountId, AccountType accountType, double balance, String password) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
        this.password = password;
    }

    public Account(int accountId, double balance, String password) {
        this.accountId = accountId;
        this.balance = balance;
        this.password = password;
    }

    public Account() {}

    // 新增构造器：添加了处理储蓄账户的新字段
    public Account(int accountId, AccountType accountType, double balance, int userId, String password,
                   double interestRate, int lockPeriod, LocalDateTime depositTime, LocalDateTime lockEndTime) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
        this.userId = userId;
        this.password = password;

        if (accountType == AccountType.SAVING_ACCOUNT) {
            this.interestRate = interestRate;
            this.lockPeriod = lockPeriod;
            this.depositTime = depositTime;
            this.lockEndTime = lockEndTime;
            this.inLockPeriod = true; // 新创建的储蓄账户处于封闭期
        } else {
            // 对活期账户设置默认值
            this.interestRate = 0.0;
            this.lockPeriod = 0;
            this.depositTime = null;
            this.lockEndTime = null;
            this.inLockPeriod = false;
        }
        this.lastReadTime = LocalDateTime.now();
    }

    // 全字段构造器，支持所有类型的账户
    public Account(int accountId, AccountType accountType, double balance, int userId, String password) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
        this.userId = userId;
        this.password = password;
    }

    // Getter和Setter方法
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(int lockPeriod) {
        this.lockPeriod = lockPeriod;
    }

    public LocalDateTime getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(LocalDateTime depositTime) {
        this.depositTime = depositTime;
    }

    public LocalDateTime getLockEndTime() {
        return lockEndTime;
    }

    public void setLockEndTime(LocalDateTime lockEndTime) {
        this.lockEndTime = lockEndTime;
    }

    public LocalDateTime getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(LocalDateTime lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    public boolean isInLockPeriod() {
        return inLockPeriod;
    }

    public void setInLockPeriod(boolean inLockPeriod) {
        this.inLockPeriod = inLockPeriod;
    }
}
