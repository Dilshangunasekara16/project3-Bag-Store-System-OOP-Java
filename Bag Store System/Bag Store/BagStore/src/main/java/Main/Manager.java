
package Main;

public class Manager extends User {

    public Manager(int userID, String name, String username, String password) {
        super(userID, name, username, password);
    }

    public Casheir createCashierAccount(int userID, String name, String username, String password) {
        // Implement account creation logic
        return new Casheir(userID, name, username, password);
    }
}

