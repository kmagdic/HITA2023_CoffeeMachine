package t4_luka.coffeemachine.bankclientdb;
import java.sql.*;
import java.util.*;

public class ClientRepository {
    Connection conn;

    public ClientRepository(Connection conn){
        this.conn = conn;

    }
    public void createTable(){
        try{
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Client (\n" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    first_name VARCHAR(255) NOT NULL,\n" +
                    "    last_name VARCHAR(255) NOT NULL,\n" +
                    "    address_name VARCHAR(255) NOT NULL,\n" +
                    "    oib VARCHAR(255) NOT NULL,\n" +
                    ");";
            Statement st = conn.createStatement();
            st.execute(sqlCreateTable);
        } catch (SQLException e){
            e.getMessage();
        }
    }
    public void insertClient(Client c){
        String sqlInsert = "INSERT INTO Client (first_name, last_name, address_name, OIB) VALUES (?, ?, ?, ?);";
        try{
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getOib());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Client> clientList(){
        String sqlAllRecords = "SELECT * FROM Client;";

        List<Client> resultList = new ArrayList<Client>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlAllRecords);
            while (rs.next()){
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setAddress(rs.getString("address_name"));
                c.setOib(rs.getString("oib"));
                resultList.add(c);
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return resultList;
    }
    public void update(Client c){
        String sqlInsert = "UPDATE Client SET (first_name, last_name, address_name, OIB) VALUES (?, ?, ?, ?);";
        try{
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getOib());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static Client findClient (String oib){
        for (Client c: BankConsoleMain.clientList){
            if (oib.equals(c.getOib())){
                return c;
            }
        }
        return null;
    }
}