package t2_ivan.coffeemachine.coffemachine.repositories;

import t2_ivan.coffeemachine.coffemachine.CoffeeType;
import t2_ivan.coffeemachine.coffemachine.LogEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogRepository {

    private final Connection connection;
    public LogRepository(Connection connection) {
        this.connection = connection;
    }

    public void logTransaction(Date date, CoffeeType coffeeType, int sugarAmount, boolean success, int amount) {
        String sqlInsert = "INSERT INTO logs (date, coffee_type, sugar_amount, success, amount) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlInsert);

            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setInt(2, coffeeType.getId());
            statement.setInt(3, sugarAmount);
            statement.setBoolean(4, success);
            statement.setInt(5, amount);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<LogEntry> getAllLogs() {
        String sqlAllLogs = "SELECT * FROM logs";
        List<LogEntry> resultList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlAllLogs)) {

            while (resultSet.next()) {
                Date logDate = resultSet.getDate("date");
                int coffeeTypeId = resultSet.getInt("coffee_type");
                int sugarAmount = resultSet.getInt("sugar_amount");
                boolean success = resultSet.getBoolean("success");
                int amount = resultSet.getInt("amount");

                LogEntry logEntry = new LogEntry(logDate, coffeeTypeId, sugarAmount, success, amount);
                resultList.add(logEntry);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultList;
    }
}
