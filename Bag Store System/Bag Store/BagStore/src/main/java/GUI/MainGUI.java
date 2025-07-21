package GUI;

import BagM.BagManagerGUI;
import Main.Casheir;
import Main.Manager;
import Main.User;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private User loggedInUser;
    private Map<String, User> users;

    public MainGUI() {
        setTitle("The Little Bag Shop");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load users from file
        users = new HashMap<>();
        loadUsers();

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2));

        mainPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        mainPanel.add(usernameField);

        mainPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        mainPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Debug: Print loaded users
            System.out.println("Loaded Users: " + users);

            if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
                loggedInUser = users.get(username);
                // Debug: Print logged in user
                System.out.println("Logged in User: " + loggedInUser);

                if (loggedInUser instanceof Manager) {
                    showManagerPanel();
                } else if (loggedInUser instanceof Casheir) {
                    showCashierPanel();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }
        });
        mainPanel.add(loginButton);

        add(mainPanel);
        setVisible(true);
    }

    private void showManagerPanel() {
        mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(2, 1));

        JButton manageBagsButton = new JButton("Manage Bags");
        SwingUtilities.invokeLater(() -> new BagManagerGUI().setVisible(true));

        JButton createCashierButton = new JButton("Create Cashier Account");
        createCashierButton.addActionListener(e -> createCashierAccount());

        mainPanel.add(manageBagsButton);
        mainPanel.add(createCashierButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

private void showCashierPanel() {
    mainPanel.removeAll();
    mainPanel.setLayout(new GridLayout(1, 1));

    JButton manageBagsButton = new JButton("Manage Bags");
    manageBagsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> new BagManagerGUI().setVisible(true));
        }
    });

    mainPanel.add(manageBagsButton);

    mainPanel.revalidate();
    mainPanel.repaint();
}


    private void createCashierAccount() {
        JTextField cashierNameField = new JTextField();
        JTextField cashierUsernameField = new JTextField();
        JTextField cashierPasswordField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Cashier Name:"));
        panel.add(cashierNameField);
        panel.add(new JLabel("Username:"));
        panel.add(cashierUsernameField);
        panel.add(new JLabel("Password:"));
        panel.add(cashierPasswordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Create Cashier Account",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = cashierNameField.getText();
            String username = cashierUsernameField.getText();
            String password = cashierPasswordField.getText();

            Casheir cashier = new Casheir(users.size() + 1, name, username, password);
            users.put(username, cashier);
            saveUsers(); // Save new user to file
            JOptionPane.showMessageDialog(null, "New cashier account created for: " + name);
        }
    }

    private void loadUsers() {
        File file = new File("users.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        int id = Integer.parseInt(parts[0]);
                        String role = parts[1];
                        String username = parts[2];
                        String password = parts[3];

                        if ("Manager".equalsIgnoreCase(role)) {
                            users.put(username, new Manager(id, "Manager", username, password));
                        } else if ("Cashier".equalsIgnoreCase(role)) {
                            users.put(username, new Casheir(id, "Cashier", username, password));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Add some default accounts
            users.put("manager", new Manager(1, "Manager", "manager", "pass"));
            users.put("cashier", new Casheir(2, "Cashier", "cashier", "pass"));
            saveUsers();
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User user : users.values()) {
                writer.write(user.getUserID() + "," + (user instanceof Manager ? "Manager" : "Cashier") + "," +
                        user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
