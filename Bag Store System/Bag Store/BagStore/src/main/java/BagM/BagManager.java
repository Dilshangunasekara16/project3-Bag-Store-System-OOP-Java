package BagM;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BagManager {
    private List<BagClass> bags = new ArrayList<>();
    private static final String FILE_NAME = "bags.txt";

    public BagManager() {
        loadBags();
    }

    public void addBag(BagClass bag) {
        bags.add(bag);
        saveBags();
    }

    public List<BagClass> getAllBags() {
        return bags;
    }

    public List<BagClass> searchBagsByCategory(String category) {
        return bags.stream()
                   .filter(bag -> bag.getCategory().equalsIgnoreCase(category))
                   .collect(Collectors.toList());
    }

    private void saveBags() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (BagClass bag : bags) {
                writer.write(bag.getId() + "," + bag.getName() + "," + bag.getCategory());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBags() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // File does not exist, nothing to load
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String category = parts[2];
                    bags.add(new BagClass(id, name, category));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        // Method to clear all bags
    public void clearBags() {
        bags.clear();
    }
}

