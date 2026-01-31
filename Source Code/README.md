# ATM Management System

## Project Overview
A Java-based ATM Management System that simulates real-world banking operations with a graphical user interface. The system supports multiple user types, account management, and transaction processing.

## Features
- **User Authentication**: Secure PIN-based login system
- **Admin Panel**: Special admin interface with user management capabilities
- **Multiple Account Types**: Savings and Checking accounts with different features
- **Banking Operations**: Deposit, withdrawal, transfer, and balance inquiry
- **Transaction History**: Complete record of all transactions
- **Interest Calculation**: Automatic interest for savings accounts
- **File-based Storage**: Persistent data storage using text files

## File Structure
ATM-System/
├── Account.java # Abstract base account class
├── Admin.java # Admin functionality
├── ATM.java # Main ATM interface
├── ATMManagementSystem.java # Application entry point
├── CheckingAccount.java # Checking account implementation
├── InsufficientFundsException.java # Custom exception
├── SavingsAccount.java # Savings account implementation
├── Transaction.java # Transaction record class
├── User.java # User management class
├── admin.txt # Admin credentials
├── users.txt # User credentials
├── accounts.txt # Account information
└── transactions.txt # Transaction history

## Prerequisites
- Java Development Kit (JDK) 8 or higher

## Installation & Usage
1. Clone the repository:

git clone https://github.com/yourusername/atm-management-system.git 

2. Navigate to the project directory:
cd atm-management-system

3. Compile all Java files:
javac *.java

4. Run the application:
java ATMManagementSystem

Default Credentials
Admin: ID: Admin, PIN: admin12345

Sample Users: Check users.txt for pre-loaded users

Data Files Format
users.txt: username,pin

accounts.txt: accountNumber,accountHolderName

transactions.txt: transactionID,accountNumber,amount,type

How It Works
Launch the application

Login as user or admin

Users can perform banking operations

Admins can create users and reset PINs

All data is saved to text files for persistence

Error Handling
Insufficient funds validation

Invalid input detection

File I/O error handling

Authentication failure management

Future Enhancements
Database integration

Enhanced GUI

Multi-currency support

Mobile interface

Biometric authentication

License
This project is for educational purposes.