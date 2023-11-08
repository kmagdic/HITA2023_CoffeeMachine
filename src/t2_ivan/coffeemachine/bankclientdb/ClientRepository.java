package t2_ivan.coffeemachine.bankclientdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientRepository {
    Connection conn;
    public ClientRepository(Connection conn) {
        this.conn = conn;
    }

    public void createTable(){
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS client (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "first_name text NOT NULL, \n" +
                    "last_name text NOT NULL, \n" +
                    "address_name text NOT NULL, \n" +
                    "oib text NOT NULL\n)";

            Statement st = conn.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

}

public void insertClient (Client c){

    String sqlInsert = "INSERT INTO client (first_name, last_name, address_name, OIB) VALUES (?,?,?,?)";

        }
