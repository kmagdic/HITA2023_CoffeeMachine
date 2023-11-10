package t2_ivan.coffeemachine.coffemachine;

import java.sql.*;
import java.util.Date;

public class LogRepository {

    private final Connection connection;
    public LogRepository(Connection connection) {
        this.connection = connection;
    }

    public void logTransaction(Date date, CoffeeType coffeeType, boolean success, int amount) {
        String sqlInsert = "INSERT INTO logs (date, coffee_type, success, amount) VALUES (?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlInsert);

            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setInt(2, coffeeType.getId());
            statement.setBoolean(3, success);
            statement.setInt(4, amount);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
