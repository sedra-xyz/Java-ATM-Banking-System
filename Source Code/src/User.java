import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class User {
    private String userID; 
    private String pin; 
    private boolean locked; 
    private List<Account> accounts; 

    public User(String userID, String pin) {
        this.userID = userID;
        this.pin = pin;
        this.locked = false; 
        this.accounts = new ArrayList<>(); 
    }

    public String getUserID() {
        return userID; 
    }

    public String getPIN() {
        return pin; 
    }

    public boolean isLocked() {
        return locked; 
    }

    public void resetPIN(String newPin) {
        this.pin = newPin;
    }

    public boolean authenticate(String pin) {
        return this.pin.equals(pin);
    }

    public List<Account> getAccounts() {
        return accounts; 
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void loadAccountsFromFile(String filePath) {
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                String accountNumber = data[0];
                String accountHolderName = this.userID; 
              
                Account account = new CheckingAccount(accountNumber, accountHolderName, 1000.0);
                accounts.add(account);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Account data file not found. No accounts loaded.");
        }
    }
}