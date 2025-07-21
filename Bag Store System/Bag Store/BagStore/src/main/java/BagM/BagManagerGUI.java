package BagM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class BagManagerGUI extends JFrame {
    private BagManager bagManager;
    private JTextArea displayArea;
    private JTextField nameField, searchField;

    public BagManagerGUI() {
        bagManager = new BagManager();
        loadBagsFromFile();

        setTitle("Bag Manager");
        setSize(800, 600); // Increase the size of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // North Panel for input fields
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 2));

        northPanel.add(new JLabel("Bag Name:"));
        nameField = new JTextField();
        northPanel.add(nameField);

        northPanel.add(new JLabel("Search Category:"));
        searchField = new JTextField();
        northPanel.add(searchField);

        add(northPanel, BorderLayout.NORTH);

        // Center Panel for buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));

        JButton addButton = new JButton("Add Bag");
        JButton viewButton = new JButton("View All Bags");
        JButton searchButton = new JButton("Search Bags");

        addButton.addActionListener(new AddButtonListener());
        viewButton.addActionListener(new ViewButtonListener());
        searchButton.addActionListener(new SearchButtonListener());

        centerPanel.add(addButton);
        centerPanel.add(viewButton);
        centerPanel.add(searchButton);

        add(centerPanel, BorderLayout.CENTER);

        // South area to display bags
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Set a larger font for readability
        displayArea.setRows(20); // Set more rows for a larger display area
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String category = searchField.getText();
            if (!name.isEmpty() && !category.isEmpty()) {
                BagClass bag = new BagClass(bagManager.getAllBags().size() + 1, name, category);
                bagManager.addBag(bag);
                saveBagsToFile(); // Save bags to file
                displayArea.append("Added: " + bag + "\n");
                nameField.setText("");
                searchField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Name and Category cannot be empty!");
            }
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BagClass> bags = bagManager.getAllBags();
            displayArea.setText("All Bags:\n");
            for (BagClass bag : bags) {
                displayArea.append(bag + "\n");
            }
        }
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String category = searchField.getText();
            List<BagClass> bags = bagManager.searchBagsByCategory(category);
            displayArea.setText("Search Results:\n");
            for (BagClass bag : bags) {
                displayArea.append(bag + "\n");
            }
        }
    }

    private void loadBagsFromFile() {
        File file = new File("bags.txt");
        if (!file.exists()) {
            System.out.println("File not found, starting with an empty bag list.");
            return;
        }

        bagManager.clearBags(); // Clear existing bags before loading new ones

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String category = parts[2];
                        bagManager.addBag(new BagClass(id, name, category));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid data format: " + line);
                    }
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBagsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bags.txt"))) {
            for (BagClass bag : bagManager.getAllBags()) {
                writer.write(bag.getId() + "," + bag.getName() + "," + bag.getCategory());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BagManagerGUI().setVisible(true));
    }
}
