
class SavingsAccount extends Account {
    private double interestRate; 

    public SavingsAccount(String accountNumber, String accountHolderName, double interestRate) {
        super(accountNumber, accountHolderName); 
        this.interestRate = interestRate; 
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        // Check for sufficient funds before withdrawal
        if (balance < amount) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal."); 
        }
        balance -= amount;
    }

   
    public double calculateInterest() {
        return balance * interestRate; 
    }
}