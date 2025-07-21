package BagM;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, String> users = new HashMap<>();
    private static final String FILE_NAME = "users.txt";

    public UserManager() {
        loadUsers();
    }

    public boolean validateUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public void addUser(String username, String password) {
        users.put(username, password);
        saveUsers();
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // File does not exist, nothing to load
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    users.put(username, password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

