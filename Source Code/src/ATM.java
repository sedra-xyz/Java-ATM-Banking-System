import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
class ATM {
    private List<User> users;
    private List<Transaction> transactions;
    private final String userFile = "users.txt";
    private final String transactionFile = "transactions.txt";
    private User currentUser;
    private Admin admin;

    public ATM() {
        users = new ArrayList<>();
        transactions = new ArrayList<>();
        admin = new Admin();
        loadUsersFromFile();
        loadTransactionsFromFile();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("ATM System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("User ID:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(10, 50, 80, 25);
        panel.add(pinLabel);

        JPasswordField pinText = new JPasswordField();
        pinText.setBounds(100, 50, 165, 25);
        panel.add(pinText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        JButton adminButton = new JButton("Admin ");
        adminButton.setBounds(100, 80, 150, 25);
        panel.add(adminButton);

        JTextArea outputArea = new JTextArea();
        outputArea.setBounds(10, 120, 550, 100);
        outputArea.setEditable(false);
        panel.add(outputArea);

        loginButton.addActionListener(e -> {
            String userID = userText.getText();
            String PIN = new String(pinText.getPassword());
            currentUser = authenticate(userID, PIN);
            if (currentUser != null) {
                outputArea.setText("Login successful!\n");
                manageAccount(outputArea);
            } else {
                outputArea.setText("Invalid credentials or account locked.\n");
            }
        });

        adminButton.addActionListener(e -> adminLogin());
        loginButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String userID = userText.getText();
            String PIN = new String(pinText.getPassword());
            currentUser = authenticate(userID, PIN);
            if (currentUser != null) {
                currentUser.loadAccountsFromFile("accounts.txt"); // Load accounts for the authenticated user
                outputArea.setText("Login successful!\n");
                manageAccount(outputArea);
            } else {
                outputArea.setText("Invalid credentials or account locked.\n");
            }
        }
    });
    }

    private void adminLogin() {
        String id = JOptionPane.showInputDialog("Enter Admin ID:");
        String pin = JOptionPane.showInputDialog("Enter Admin PIN:");
        if (admin.authenticate(id, pin)) {
            String options = "1. Create User\n2. Reset User PIN\n3. Exit";
            String choice = JOptionPane.showInputDialog(options);
            switch (choice) {
                case "1":
                    admin.createUser(users);
                    break;
                case "2":
                    admin.resetUserPIN(users);
                    break;
                case "3":
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid admin credentials.");
        }
    }

    private User authenticate(String userID, String PIN) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                if (user.isLocked()) {
                    return null;
                }
                if (user.authenticate(PIN)) {
                    return user;
                }
            }
        }
        return null;
    }

    private void manageAccount(JTextArea outputArea) {
        StringBuilder options = new StringBuilder();
        options.append("Select an operation:\n")
                .append("1. View Accounts\n")
                .append("2. Deposit\n")
                .append("3. Withdraw\n")
                .append("4. Transfer\n")
                .append("5. View Transactions\n")
                .append("6. Apply Interest\n")
                .append("7. Exit\n");

        String input = JOptionPane.showInputDialog(options.toString());
        int choice = Integer.parseInt(input);
        switch (choice) {
            case 1:
                viewAccounts(outputArea);
                break;
            case 2:
                performDeposit(outputArea);
                break;
            case 3:
                performWithdrawal(outputArea);
                break;
            case 4:
                performTransfer(outputArea);
                break;
            case 5:
                viewTransactions(outputArea);
                break;
            case 6:
                applyInterest(outputArea);
                break;
            case 7:
                JOptionPane.showMessageDialog(null, "Exiting.");
                break;
            default:
                outputArea.setText("Invalid choice.\n");
        }
    }



    private void viewAccounts(JTextArea outputArea) {
        StringBuilder accountsInfo = new StringBuilder("Accounts for " + currentUser.getUserID() + ":\n");
        for (Account account : currentUser.getAccounts()) {
            accountsInfo.append("Account Number: ").append(account.getAccountNumber())
                    .append(", Balance: ").append(account.getBalance()).append("\n");
        }
        outputArea.setText(accountsInfo.toString());
    }

    private void performDeposit(JTextArea outputArea) {
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        if (accountNumber == null) return; 
        String amountString = JOptionPane.showInputDialog("Enter amount to deposit:");
        if (amountString == null) return; 
        try {
            double amount = Double.parseDouble(amountString);

            for (Account account : currentUser.getAccounts()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    account.deposit(amount);
                    transactions.add(new Transaction(UUID.randomUUID().toString(), accountNumber, amount, "Deposit"));
                    updateTransactionFile();
                    outputArea.setText("Deposit successful.\n");
                    return;
                }
            }
            outputArea.setText("Account not found.\n");
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid amount entered.\n");
        }
    }
    private void performWithdrawal(JTextArea outputArea) {
      
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        String amountInput = JOptionPane.showInputDialog("Enter amount to withdraw:");

        try {
            
            double amount = Double.parseDouble(amountInput);
            boolean found = false; 

            
            for (Account account : currentUser.getAccounts()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    
                    account.withdraw(amount);
                    transactions.add(new Transaction(UUID.randomUUID().toString(), accountNumber, amount, "Withdrawal"));
                    updateTransactionFile(); 
                    outputArea.setText("Withdrawal successful from account " + accountNumber + ".\n");

                   
                    outputArea.append("New balance: " + account.getBalance() + "\n");

                    found = true; 
                    break;
                }
            }

           
            if (!found) {
                outputArea.setText("Account not found.\n");
            }

        } catch (InsufficientFundsException e) {
            
            outputArea.setText(e.getMessage() + "\n");
        } catch (NumberFormatException e) {
           
            outputArea.setText("Invalid amount format. Please enter a number.\n");
        } catch (Exception e) {
            
            outputArea.setText("An unexpected error occurred: " + e.getMessage() + "\n");
        }
    }
    
private void performTransfer(JTextArea outputArea) {
    
    String fromAccountNumber = JOptionPane.showInputDialog("Enter your account number:");
    String toAccountNumber = JOptionPane.showInputDialog("Enter recipient account number:");
    String amountInput = JOptionPane.showInputDialog("Enter amount to transfer:");

    try {
      
        double amount = Double.parseDouble(amountInput);
        Account fromAccount = null;
        Account toAccount = null;

       
        for (Account account : currentUser.getAccounts()) {
            if (account.getAccountNumber().equals(fromAccountNumber)) {
                fromAccount = account;
            }
            if (account.getAccountNumber().equals(toAccountNumber)) {
                toAccount = account;
            }
        }

     
        if (fromAccount == null || toAccount == null) {
            outputArea.setText("One or both accounts not found.\n");
            return;
        }

        fromAccount.withdraw(amount);
      
        toAccount.deposit(amount);

       
        transactions.add(new Transaction(UUID.randomUUID().toString(), fromAccountNumber, amount, "Transfer to " + toAccountNumber));
        updateTransactionFile(); 
        
        outputArea.setText("Transfer successful. Amount: " + amount + "\n");

        
        outputArea.append("New balance for " + fromAccountNumber + ": " + fromAccount.getBalance() + "\n");
        outputArea.append("New balance for " + toAccountNumber + ": " + toAccount.getBalance() + "\n");

    } catch (InsufficientFundsException e) {
        
        outputArea.setText(e.getMessage() + "\n");
    } catch (NumberFormatException e) {
   
        outputArea.setText("Invalid amount format. Please enter a number.\n");
    } catch (Exception e) {
       
        outputArea.setText("An unexpected error occurred: " + e.getMessage() + "\n");
    }
}

    private void viewTransactions(JTextArea outputArea) {
        StringBuilder transactionHistory = new StringBuilder("Transaction history:\n");
        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber().equals(currentUser.getAccounts().get(0).getAccountNumber())) {
                transactionHistory.append(transaction).append("\n");
            }
        }
        outputArea.setText(transactionHistory.toString());
    }

    private void applyInterest(JTextArea outputArea) {
        for (Account account : currentUser.getAccounts()) {
            if (account instanceof SavingsAccount) {
                SavingsAccount savingsAccount = (SavingsAccount) account;
                double interest = savingsAccount.calculateInterest();
                savingsAccount.deposit(interest);
                transactions.add(new Transaction(UUID.randomUUID().toString(), savingsAccount.getAccountNumber(), interest, "Interest Applied"));
                updateTransactionFile();
                outputArea.append("Interest of " + interest + " applied to account " + savingsAccount.getAccountNumber() + ".\n");
            }
        }
    }

    private void loadUsersFromFile() {
        try (Scanner fileScanner = new Scanner(new File(userFile))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                if (data.length >= 2) {
                    users.add(new User(data[0], data[1]));
                } else {
                    System.out.println("Invalid user data line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found. Starting with no users.");
        }
    }


private void loadTransactionsFromFile() {
    try (Scanner fileScanner = new Scanner(new File(transactionFile))) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim();
            if (line.isEmpty()) {
                continue; 
            }
            String[] data = line.split(",");
            if (data.length == 4) { 
                try {
                    Transaction transaction = new Transaction(data[0], data[1], Double.parseDouble(data[2]), data[3]);
                    transactions.add(transaction);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount format for transaction: " + line);
                }
            } else {
                System.out.println("Invalid transaction format: " + line);
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Transaction data file not found. Starting with no transactions.");
    }
}

    private void updateTransactionFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(transactionFile, true))) {
            
            if (!transactions.isEmpty()) {
                Transaction lastTransaction = transactions.get(transactions.size() - 1);
                writer.println(lastTransaction.toString());
            }
        } catch (IOException e) {
            System.out.println("Error updating transaction file.");
        }
    }
}