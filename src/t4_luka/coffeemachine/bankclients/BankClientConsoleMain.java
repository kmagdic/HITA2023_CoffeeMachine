package t4_luka.coffeemachine.bankclients;

import java.sql.*;

public class BankClientConsoleMain {


    private static Connection conn;

    public static void main(String[] args) {
        makeDBConnection("./banksystem.h2");
        createSchema(conn);
        addUser("Dragan", "Sacer", "dragan@agileway.com", "dragec", "test123");

    }


    private static void makeDBConnection(String fileName) {
        try {
            conn = DriverManager.getConnection("jdbc:h2:" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void createSchema(Connection conn) {

        String createTableSql =
                "CREATE TABLE IF NOT EXISTS app_user (\n" +
                        "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    first_name VARCHAR(255) NOT NULL,\n" +
                        "    last_name VARCHAR(255) NOT NULL,\n" +
                        "    email VARCHAR(255) NOT NULL,\n" +
                        "    username VARCHAR(255) NOT NULL,\n" +
                        "    password VARCHAR(255) NOT NULL\n" +
                        ");";


        try {

            Statement stmt = conn.createStatement();

            // create a new table if it doesn't exist
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void addUser(String firstName, String lastName, String email,String username, String password) {
        String sql = "INSERT INTO app_user(first_name, last_name, email, username, password) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("bla");
    }

}
