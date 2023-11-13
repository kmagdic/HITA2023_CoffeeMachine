package t4_zoran.coffeemachine.repositories;

import java.sql.*;
import java.util.*;
import t4_zoran.coffeemachine.*;

public class CoffeeTypeRepository {
    Connection conn;

    public CoffeeTypeRepository(Connection conn) {
        this.conn = conn;
    }

    public List<CoffeeType> getListOfRecipes() {

        List<CoffeeType> resultList = new ArrayList<>();

        String sqlAllRecipes = "SELECT \n" +
                "    c.ID_coffee_type,\n" +
                "    c.name AS coffee_type_name,\n" +
                "    MAX(CASE WHEN water.name = 'water' THEN water_recipe.ingredient_amount ELSE 0 END) AS amount_of_water,\n" +
                "    MAX(CASE WHEN milk.name = 'milk' THEN milk_recipe.ingredient_amount ELSE 0 END) AS amount_of_milk,\n" +
                "    MAX(CASE WHEN beans.name = 'coffee beans' THEN beans_recipe.ingredient_amount ELSE 0 END) AS amount_of_coffee_beans,\n" +
                "    MAX(c.price) AS price\n" +
                "FROM coffee_type c\n" +
                "LEFT JOIN recipe water_recipe ON c.ID_coffee_type = water_recipe.coffee_type_ID\n" +
                "LEFT JOIN ingredient water ON water_recipe.ingredient_ID = water.ID_ingredient\n" +
                "LEFT JOIN recipe milk_recipe ON c.ID_coffee_type = milk_recipe.coffee_type_ID\n" +
                "LEFT JOIN ingredient milk ON milk_recipe.ingredient_ID = milk.ID_ingredient\n" +
                "LEFT JOIN recipe beans_recipe ON c.ID_coffee_type = beans_recipe.coffee_type_ID\n" +
                "LEFT JOIN ingredient beans ON beans_recipe.ingredient_ID = beans.ID_ingredient\n" +
                "GROUP BY c.ID_coffee_type, c.name";

        try {
            Statement st = conn.createStatement();

            ResultSet numberOfRows = st.executeQuery("SELECT * FROM coffee_type");

            int rowCount = 0;
            while (numberOfRows.next()) {
                rowCount++;
            }

            if (rowCount >= 3){

                ResultSet rs = st.executeQuery(sqlAllRecipes);

                while (rs.next()){
                    CoffeeType c = new CoffeeType();
                    c.setCoffeeTypeID(rs.getInt("ID_coffee_type"));
                    c.setName(rs.getString("coffee_type_name"));
                    c.setWaterNeeded(rs.getInt("amount_of_water"));
                    c.setMilkNeeded(rs.getInt("amount_of_milk"));
                    c.setCoffeeBeansNeeded(rs.getInt("amount_of_coffee_beans"));
                    c.setPrice(rs.getInt("price"));

                    resultList.add(c);
                }
            } else {
                resultList.add(new CoffeeType(1, "Espresso", 350, 0, 16, 4));
                resultList.add(new CoffeeType(2, "Latte", 350, 75, 20, 7));
                resultList.add(new CoffeeType(3, "Capuccino", 200, 100, 12, 6));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
