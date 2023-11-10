package t2_ivan.coffeemachine.bankclientdb.repositories;

import t2_ivan.coffeemachine.bankclientdb.Account;
import t2_ivan.coffeemachine.bankclientdb.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepository {
    Connection conn;
    public AccountRepository(Connection conn) {
        this.conn = conn;
    }

    public void createTable(){
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS account (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "account_name  text  NOT NULL,\n " +
                    "balance decimal NOT NULL," +
                    "client_id INT)";

            Statement st = conn.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

public void insert (Account a) {

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

    public List<Account> getListAccountsForClient (Client client) {
        String sqlPrint = "SELECT * FROM account where client_id = " + client.getId();

        List<Account> resultList = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()){
                Account a = new Account();
                a.setId(rs.getInt("id"));
                a.setAccountName(rs.getString("name"));
                a.setBalance(rs.getDouble("balance"));
                a.setClient(client);

                resultList.add(a);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  resultList;
    }

    public Map<Integer, Account> getListAsMapAccountsForClient(Client client) {
        String sqlPrint = "SELECT * FROM account where client_id = " + client.getId();
        Map<Integer, Account> resultMap = new HashMap<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                Account a = new Account();
                a.setId(rs.getInt("id"));
                a.setAccountName(rs.getString("name"));
                a.setBalance(rs.getDouble("balance"));
                a.setClient(client);

                resultMap.put(a.getId(), a);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    public void updateAccount (Account a){
        String update = "update account set " +
                "name = ?, " +
                "balance = ?, " +
                "client_id = ? " +
                "where id = ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(update);
            // ps.setInt(1,c.getId());
            ps.setString(1, a.getAccountName());
            ps.setDouble(2, a.getBalance());
            ps.setInt(3, a.getClient().getId());
            ps.setInt(4, a.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Account a) {
        String delete = "delete from account where id = " + a.getId();
        try {
            PreparedStatement ps = conn.prepareStatement(delete);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}
