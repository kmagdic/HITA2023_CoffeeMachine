package t1_sinisa.coffeemachine.repositories;

import t1_sinisa.coffeemachine.CoffeeType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeTypeRepository {

    Connection conn;

    public CoffeeTypeRepository(Connection conn) { this.conn = conn; }

    public void createTable () {

        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS coffee_type (\n" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
                    "name TEXT NOT NULL,\n" +
                    "water INTEGER NOT NULL, \n" +
                    "milk INTEGER NOT NULL, \n" +
                    "coffee_beans INTEGER NOT NULL, \n" +
                    "price INTEGER NOT NULL)";

            Statement st = conn.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertCoffeeType (CoffeeType coffeeType) {

        String insertSql = "INSERT INTO coffee_type (name, water, milk, coffee_beans, price) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(insertSql);
            ps.setString(1, coffeeType.getName());
            ps.setInt(2, coffeeType.getWaterNeeded());
            ps.setInt(3, coffeeType.getMilkNeeded());
            ps.setInt(4, coffeeType.getCoffeeBeansNeeded());
            ps.setInt(5, coffeeType.getPrice());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CoffeeType> getListOfCoffeeTypes() {
        String sqlPrint = "SELECT * FROM coffee_type";
        List<CoffeeType> resultList = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                CoffeeType coffeeType = new CoffeeType();
                coffeeType.setId(rs.getInt("id"));
                coffeeType.setName(rs.getString("name"));
                coffeeType.setWaterNeeded(rs.getInt("water"));
                coffeeType.setMilkNeeded(rs.getInt("milk"));
                coffeeType.setCoffeeBeansNeeded(rs.getInt("coffee_beans"));
                coffeeType.setPrice(rs.getInt("price"));

                resultList.add(coffeeType);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public void updateCoffeeType (CoffeeType c) {

        String update = "UPDATE coffee_type SET " +
                "name = ?, " +
                "water = ?, " +
                "milk = ?, " +
                "coffee_beans = ?, " +
                "price = ? " +
                "WHERE id = ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(update);

            ps.setString(1, c.getName());
            ps.setInt(2, c.getWaterNeeded());
            ps.setInt(3, c.getMilkNeeded());
            ps.setInt(4, c.getCoffeeBeansNeeded());
            ps.setInt(5, c.getPrice());
            ps.setInt(6, c.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCoffeeType (CoffeeType c) {
        String delete = "DELETE FROM coffee_type WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(delete);
            ps.setInt(1, c.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
