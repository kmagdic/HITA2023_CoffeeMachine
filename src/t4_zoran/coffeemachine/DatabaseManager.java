package t4_zoran.coffeemachine;

import _karlo_dragan.bankclientdb.*;
import java.sql.*;
import java.util.*;

public class DatabaseManager {

    Connection conn;

    public DatabaseManager(Connection conn) {
        this.conn = conn;
    }

    public void createDatabase (){

        try {
            String sqlCreateDatabase = "CREATE TABLE IF NOT EXISTS transaction (\n" +
                    "IDTransaction integer PRIMARY KEY auto_increment, \n" +
                    "time_stamp datetime NOT NULL, \n" +
                    "coffee_type text NOT NULL, \n" +
                    "missing_ingredient text\n)";

//            String sqlCreateDatabase = "drop TABLE transaction";

            Statement st = conn.createStatement();
            st.execute(sqlCreateDatabase);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertTransaction (Transaction t){

        String sqlInsert = "INSERT INTO transaction (time_stamp, coffee_Type, missing_ingredient) VALUES (?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sqlInsert);

            ps.setTimestamp(1, t.getTimestamp());
            ps.setString(2, t.getCoffeeType());
            ps.setString(3, t.getMissing());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> transactionList() {

        String sqlAllTransactions = "SELECT * FROM transaction";

        List<Transaction> resultList = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlAllTransactions);

            while (rs.next()){
                Transaction t = new Transaction();
                t.setId(rs.getInt("IDTransaction"));
                t.setTimestamp(Timestamp.valueOf(rs.getString("time_stamp")));
                t.setCoffeeType(rs.getString("coffee_Type"));
                t.setMissing(rs.getString("missing_ingredient"));

                resultList.add(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
