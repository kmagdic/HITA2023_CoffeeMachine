package t3_lovro.coffeemachine;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComWithDB {
    Connection conn;

    public ComWithDB(String dBPath) {
        this.conn = conn;
        conn = makeDBConnection(dBPath);
    }
    public static Connection makeDBConnection(String dBPath) {
        try {
            return DriverManager.getConnection("jdbc:h2:" + dBPath);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSchema() {
        String createTableSql =
                """
                          CREATE TABLE IF NOT EXISTS coffee_types (
                              id_coffee_type INT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(50)NOT NULL,
                              milk INT NOT NULL,
                              water INT NOT NULL,
                              coffee_beans INT NOT NULL,
                              price INT NOT NULL
                          );
                                                 
                          CREATE TABLE IF NOT EXISTS machine_log (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              date_time VARCHAR(100) NOT NULL,
                              coffee_type_id INT NOT NULL,
                              action VARCHAR(255) NOT NULL,
                              CONSTRAINT coffee_type_fk FOREIGN KEY (coffee_type_id) REFERENCES coffee_types(id_coffee_type)
                          );
                                
                        """;
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<CoffeeType> coffeeType() {

        String sqlAllRecords = "SELECT id_coffee_type, name, milk, water, coffee_beans, price FROM coffee_types";

        List<CoffeeType> resultList = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlAllRecords);

            while (rs.next()) {
                CoffeeType c = new CoffeeType();
                c.setName(rs.getString("name"));
                c.setMilkNeeded(rs.getInt("milk"));
                c.setWaterNeeded(rs.getInt("water"));
                c.setCoffeeBeansNeeded(rs.getInt("coffee_beans"));
                c.setPrice(rs.getInt("price"));
                c.setId(rs.getInt("id_coffee_type"));
                resultList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public void addCoffeeTypesToDB(CoffeeType coffeeType) {
        String sql = "INSERT INTO COFFEE_TYPES(name, milk, water, coffee_beans, price) VALUES(?, ?, ?, ? ,? )";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, coffeeType.getName());
            pstmt.setInt(2, coffeeType.getMilkNeeded());
            pstmt.setInt(3, coffeeType.getWaterNeeded());
            pstmt.setInt(4, coffeeType.getCoffeeBeansNeeded());
            pstmt.setInt(5, coffeeType.getPrice());
            pstmt.executeUpdate();
            System.out.println("CoffeeType added! ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Transaction
    public void addTransactionToLog(CoffeeType coffeeType, String action) {
        String sql = "INSERT INTO machine_log(date_time, coffee_type_id, action) VALUES(?, ?, ?)";
        java.util.Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, simpleDateFormat.format(date));
            pstmt.setInt(2, coffeeType.getId());
            pstmt.setString(3, action);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readingFromDB() {
        Statement statement;
        try {
            statement = conn.createStatement();
            String sqlUpit = "SELECT date_time, name, action FROM machine_log JOIN coffee_types ON  coffee_type_id = id_coffee_type";
            ResultSet rezultat = statement.executeQuery(sqlUpit);
            while (rezultat.next()) {
                String dateTime = rezultat.getString("date_time");
                String coffeeType = rezultat.getString("name");
                String action = rezultat.getString("action");
                System.out.println(dateTime + " " + coffeeType + " " + action);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
