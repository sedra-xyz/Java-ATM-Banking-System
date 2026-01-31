
class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolderName, double overdraftLimit) {
        super(accountNumber, accountHolderName);
        this.overdraftLimit = overdraftLimit; 
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
       
        if (balance + overdraftLimit < amount) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal."); 
        }
        balance -= amount; 
    }
}