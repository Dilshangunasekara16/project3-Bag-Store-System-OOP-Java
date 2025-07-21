
package Main;

public abstract class User {
    private int userID;
    private String name;
    private String username;
    private String password;

    public User(int userID, String name, String username, String password) {
        this.userID = userID;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public void login(String username, String password) {
        // Implement login logic
    }

    public void logout() {
        // Implement logout logic
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

