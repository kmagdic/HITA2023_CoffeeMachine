package t4_zoran.coffeemachine.repositories;

import java.sql.*;
import java.util.*;
import t4_zoran.coffeemachine.*;

public class TransactionRepository {
    Connection conn;

    public TransactionRepository(Connection conn) {
        this.conn = conn;
    }

    public void insertTransaction (Transaction t){

        String sqlInsert = "INSERT INTO transaction (time_stamp, missing_ingredient, coffee_type_ID) VALUES (?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert);

            ps.setTimestamp(1, t.getTimestamp());
            ps.setString(2, t.getMissing());
            ps.setInt(3, t.getCoffeeTypeID());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> transactionList() {

        String sqlAllTransactions = "SELECT ID_Transaction, time_stamp, missing_ingredient," +
                " c.name as CoffeeName FROM transaction t" +
                " join coffee_type c on t.coffee_type_ID = c.ID_coffee_type";

        List<Transaction> resultList = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlAllTransactions);

            while (rs.next()){
                Transaction t = new Transaction();
                t.setId(rs.getInt("ID_Transaction"));
                t.setTimestamp(Timestamp.valueOf(rs.getString("time_stamp")));
                t.setMissing(rs.getString("missing_ingredient"));
                t.setCoffeeTypeName(rs.getString("CoffeeName"));

                resultList.add(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
