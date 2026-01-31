
class Transaction {
    private String transactionID; 
    private String accountNumber;
    private double amount; 
    private String type; 

    public Transaction(String transactionID, String accountNumber, double amount, String type) {
        this.transactionID = transactionID; 
        this.accountNumber = accountNumber; 
        this.amount = amount; 
        this.type = type; 
    }

    @Override
    public String toString() {
        return transactionID + "," + accountNumber + "," + amount + "," + type; 
    }

    public String getAccountNumber() {
        return accountNumber; 
    }
}