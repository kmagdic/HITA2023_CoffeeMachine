package t1_sinisa.coffeemachine;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;

public class DBConnection {

    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void makeDBConnection(String fileName) {
        try {
            conn = DriverManager.getConnection("jdbc:h2:" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSchema(Connection conn) {

        String createTableSql =
                "CREATE TABLE IF NOT EXISTS log_buying (\n" +
                        "   id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   date_time VARCHAR(255) NOT NULL,\n" +
                        "   drink_type VARCHAR(255) NOT NULL,\n" +
                        "   buy_status VARCHAR(255) NOT NULL,\n" +
                        "   explanation VARCHAR(255) NOT NULL\n" +
                        ");";

        try {
            Statement stmt = conn.createStatement();

            stmt.execute(createTableSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addLog (Log log) {
        String insertIntoSql = "INSERT INTO log_buying(date_time, drink_type, buy_status, explanation) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertIntoSql)) {
            pstmt.setString(1, log.getFormattedDate());
            pstmt.setString(2, log.getDrinkType());
            pstmt.setString(3, log.getBuyStatus());
            pstmt.setString(4, log.getExplanation());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readLog () {
        String selectFromSql = "SELECT date_time, drink_type, buy_status, explanation FROM log_buying";

        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectFromSql);

            while (resultSet.next()) {

                System.out.println("Date/time: " + resultSet.getString("date_time")
                        + ", coffee type: " + resultSet.getString("drink_type")
                        + ", action: " + resultSet.getString("buy_status")
                        + ", " + resultSet.getString("explanation"));

            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
