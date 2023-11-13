package t5_fran.coffeemachine;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String dateTime;
    private String coffeeType;
    private String action;
    private Connector dbConnector;

    public Transaction(String typeOfCoffee, String theAction, Connector theConnector) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
        dateTime = dtf.format(LocalDateTime.now());
        coffeeType = typeOfCoffee;
        action = theAction;
        dbConnector = theConnector;

        addTransaction(dateTime, coffeeType, action);

    }

    public void addTransaction(String dateTime, String coffeeType, String action) {
        String sql = "INSERT INTO transaction_log(dateTime, coffeeType, action) VALUES(?, ?, ?)";

        try (PreparedStatement pstmt = dbConnector.getConn().prepareStatement(sql)) {
            pstmt.setString(1, dateTime);
            pstmt.setString(2, coffeeType);
            pstmt.setString(3, action);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getTransactionInfo() {

        String transactionInfo = "Date/time: " + dateTime + ", coffee type: " + coffeeType + ", action: " + action;
        return transactionInfo;

    }

}