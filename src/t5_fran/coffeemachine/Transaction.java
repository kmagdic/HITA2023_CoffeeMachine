package t5_fran.coffeemachine;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private static Connection conn;

    private String dateTime;
    private String coffeeType;
    private String action;

    public Transaction(String typeOfCoffee, String theAction) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
        dateTime = dtf.format(LocalDateTime.now());
        coffeeType = typeOfCoffee;
        action = theAction;

        makeDBConnection("./cmtransactions.h2");
        createSchema(conn);
        addTransaction(dateTime, coffeeType, action);

    }

    public String getTransactionInfo() {

        String transactionInfo = "Date/time: " + dateTime + ", coffee type: " + coffeeType + ", action: " + action;
        return transactionInfo;

    }

    private void makeDBConnection(String fileName) {
        try {
            conn = DriverManager.getConnection("jdbc:h2:" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createSchema(Connection conn) {

        String createTableSql =
                "CREATE TABLE IF NOT EXISTS transaction_log (\n" +
                        "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    dateTime VARCHAR(255) NOT NULL,\n" +
                        "    coffeeType VARCHAR(255) NOT NULL,\n" +
                        "    action VARCHAR(255) NOT NULL\n" +
                        ");";

        try {

            Statement stmt = conn.createStatement();

            stmt.execute(createTableSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void addTransaction(String dateTime, String coffeeType, String action) {
        String sql = "INSERT INTO transaction_log(dateTime, coffeeType, action) VALUES(?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dateTime);
            pstmt.setString(2, coffeeType);
            pstmt.setString(3, action);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}