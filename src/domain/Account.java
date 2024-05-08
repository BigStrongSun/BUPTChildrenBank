package domain;

public class Account {
    private int accountId;

    private AccountType accountType; // 使用枚举类型
    private double balance;
    private int userId;
    private String password;

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
    public Account(){}

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return password;}

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
}
