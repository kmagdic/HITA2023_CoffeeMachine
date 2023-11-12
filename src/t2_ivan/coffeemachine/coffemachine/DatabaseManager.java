package t2_ivan.coffeemachine.coffemachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager(String filename) {
        startConnection(filename);
        createDatabase();
    }

    public Connection getConnection() {
        return connection;
    }

    private void startConnection(String filename) {
        try {
            this.connection = DriverManager.getConnection("jdbc:h2:./" + filename);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDatabase() {
        createCoffeeTypesTable();
        createTransactionLogTable();
    }

    private void createCoffeeTypesTable() throws RuntimeException {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS coffees (\n" +
                    "coffee_id INT PRIMARY KEY AUTO_INCREMENT, \n" +
                    "name NVARCHAR(255) NOT NULL, \n" +
                    "milk INT NOT NULL, \n" +
                    "water INT NOT NULL, \n" +
                    "coffee_beans INT NOT NULL, \n"+
                    "price INT NOT NULL\n)";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTransactionLogTable() throws RuntimeException {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS logs (\n" +
                    "log_id INT PRIMARY KEY AUTO_INCREMENT, \n" +
                    "date DATE NOT NULL, \n" +
                    "coffee_type INT, \n" +
                    "FOREIGN KEY(coffee_type) REFERENCES coffees(coffee_id), \n" +
                    "sugar_amount INT NOT NULL, \n" +
                    "success BOOLEAN NOT NULL, \n" +
                    "amount INT NOT NULL\n)";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
