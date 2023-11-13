package t5_fran.coffeemachine;

import java.sql.*;

public class Connector {

    private Connection conn;

    public Connector(String fileName) {

        makeDBConnection(fileName);

    }

    public Connection getConn() {
        return conn;
    }

    private void makeDBConnection(String fileName) {
        try {
            conn = DriverManager.getConnection("jdbc:h2:" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSchema(String createTableSql) {

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}