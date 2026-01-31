import javax.swing.*;


public class ATMManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATM atm = new ATM(); 
            atm.createAndShowGUI(); 
        });
    }
}