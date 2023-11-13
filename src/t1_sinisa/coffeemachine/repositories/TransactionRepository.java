package t1_sinisa.coffeemachine.repositories;

import t1_sinisa.coffeemachine.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TransactionRepository {

    Connection conn;

    public TransactionRepository(Connection conn) { this.conn = conn; }

    public void createTable () {

        try {
            String createTableSql =
                    "CREATE TABLE IF NOT EXISTS transaction (\n" +
                            "   id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                            "   date_time DATETIME NOT NULL,\n" +
                            "   drink_type VARCHAR(255) NOT NULL,\n" +
                            "   buy_status VARCHAR(255) NOT NULL\n" +
                            ");";
            Statement st = conn.createStatement();
            st.execute(createTableSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTransaction (Transaction transaction) {
        String insertIntoSql = "INSERT INTO transaction (date_time, drink_type, buy_status) VALUES(?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(insertIntoSql);
            ps.setTimestamp(1, transaction.getFormattedDate());
            ps.setString(2, transaction.getDrinkType());
            ps.setString(3, transaction.getBuyStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getListOfTransactions() {

        String sqlPrint = "SELECT * FROM transaction";
        List<Transaction> resultList = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setFormattedDate(rs.getTimestamp("date_time"));
                transaction.setDrinkType(rs.getString("drink_type"));
                transaction.setBuyStatus(rs.getString("buy_status"));

                resultList.add(transaction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

}
