
abstract class Account {
    protected String accountNumber; 
    protected double balance; 
    protected String accountHolderName; 

   
    public Account(String accountNumber, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = 0.0;
    }

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount) throws InsufficientFundsException;

    
    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }
}