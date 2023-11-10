package t2_ivan.coffeemachine.coffemachine;

import _karlo_dragan.bankclientdb.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeRepository {

    private Connection connection;

    public CoffeeRepository(Connection connection) {
        this.connection = connection;
    }

    public void addCoffee(CoffeeType coffee) {
        String sqlInsert = "INSERT INTO coffees (name, milk, water, coffee_beans, price) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlInsert);

            statement.setString(1, coffee.getName());
            statement.setInt(2, coffee.getMilkNeeded());
            statement.setInt(3, coffee.getWaterNeeded());
            statement.setInt(4, coffee.getCoffeeBeansNeeded());
            statement.setInt(5, coffee.getPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CoffeeType> getCoffees() {
        String sqlAllRecords = "SELECT * FROM coffees";
        List<CoffeeType> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlAllRecords);

            while (resultSet.next()){
                String name = resultSet.getString("name");
                Integer id = resultSet.getInt("coffee_id");
                int milk = resultSet.getInt("milk");
                int water = resultSet.getInt("water");
                int coffeeBeans = resultSet.getInt("coffee_beans");
                int price = resultSet.getInt("price");
                CoffeeType coffee = new CoffeeType(id, name, water, milk, coffeeBeans, price);
                resultList.add(coffee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
