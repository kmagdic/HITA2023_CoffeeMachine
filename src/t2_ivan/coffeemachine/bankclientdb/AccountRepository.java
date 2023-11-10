package t2_ivan.coffeemachine.bankclientdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    Connection conn;
    public AccountRepository(Connection conn) {
        this.conn = conn;
    }

    public void createTable(){
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS account (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "name text NOT NULL, \n" +
                    "balance decimal NOT NULL, \n" +
                    "client_id INT NOT NULL, \n" +
                    "oib text NOT NULL\n)";

            Statement st = conn.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

public void inesrt (Account a) {

    String sqlInsert = "INSERT INTO account (name, balance, client_id) VALUES (?,?,?)";

    try {
        PreparedStatement ps = conn.prepareStatement(sqlInsert);

        ps.setString(1, a.getAccountName());
        ps.setDouble(2, a.getBalance());
        ps.setInt(3, a.getClient().getId());

        ps.executeUpdate();

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

    public List<Client> getListForClient (Client client) {
        String sqlAllRecords = "SELECT * FROM account where client_id + " + client.getId();

        List<Client> resultList = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlAllRecords);

            while (rs.next()){
                Account a = new Account();
                c.setId(rs.getInt("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setAddress(rs.getString("address_name"));
                c.setOib(rs.getString("OIB"));

                resultList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  resultList;
    }

    public void updateClient (Client c){
        String update = "UPDATE client SET " +
                "first_name = ?, " +
                "last_name = ?, " +
                "address_name = ?, " +
                "oib = ?" +
                "where id = ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(update);
            ps.setString(1,c.getFirstName());
            ps.setString(2,c.getLastName());
            ps.setString(3,c.getAddress());
            ps.setString(4,c.getOib());
            ps.setInt(5,c.getId());


            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Client c) {
        String delete = "delete from client where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(delete);
            ps.setInt(1, c.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}
