import javax.swing.*;
import java.io.*;
import java.util.List;


class Admin {
    private String adminID = "Admin"; 
    private String adminPIN = "admin12345"; 

    
    public boolean authenticate(String id, String pin) {
        return adminID.equals(id) && adminPIN.equals(pin);
    }

    
    public void createUser(List<User> users) {
        String userID = JOptionPane.showInputDialog("Enter new User ID:");
        String pin = JOptionPane.showInputDialog("Enter new User PIN:");
        User newUser = new User(userID, pin);
        users.add(newUser);
        saveUserToFile(newUser); 
        JOptionPane.showMessageDialog(null, "User created successfully.");
    }

   
    private void saveUserToFile(User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt", true))) {
            writer.println(user.getUserID() + "," + user.getPIN()); 
        } catch (IOException e) {
            System.out.println("Error saving user to file.");
        }
    }

    public void resetUserPIN(List<User> users) {
        String userID = JOptionPane.showInputDialog("Enter User ID to reset PIN:");
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                String newPin = JOptionPane.showInputDialog("Enter new PIN:");
                user.resetPIN(newPin);
                JOptionPane.showMessageDialog(null, "PIN reset successfully.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "User not found.");
    }
}