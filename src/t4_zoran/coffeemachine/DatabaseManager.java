package t4_zoran.coffeemachine;

import java.sql.*;
import java.util.*;

public class DatabaseManager {

    Connection conn;

    public DatabaseManager(Connection conn) {
        this.conn = conn;
    }

    public void createDatabase() {
        try {
            String sqlCreateTableCoffeeType = "CREATE TABLE IF NOT EXISTS coffee_type (\n" +
                    "ID_coffee_type integer PRIMARY KEY auto_increment, \n" +
                    "name text NOT NULL, \n" +
                    "price decimal NOT NULL\n)";
            String sqlCreateTableIngredient = "CREATE TABLE IF NOT EXISTS ingredient (\n" +
                    "ID_ingredient integer PRIMARY KEY auto_increment, \n" +
                    "name text NOT NULL, \n" +
                    "unit text NOT NULL\n)";
            String sqlCreateTableRecipe = "CREATE TABLE IF NOT EXISTS recipe (\n" +
                    "ID_recipe integer PRIMARY KEY auto_increment, \n" +
                    "coffee_type_ID integer NOT NULL,\n" +
                    "ingredient_ID integer NOT NULL,\n" +
                    "ingredient_amount integer,\n" +
                    "FOREIGN KEY (ingredient_ID) REFERENCES ingredient(ID_ingredient),\n" +
                    "FOREIGN KEY (coffee_type_ID) REFERENCES coffee_type(ID_coffee_type)\n)";
            String sqlCreateTableTransaction = "CREATE TABLE IF NOT EXISTS transaction (\n" +
                    "ID_Transaction integer PRIMARY KEY auto_increment, \n" +
                    "time_stamp datetime NOT NULL, \n" +
                    "coffee_type_ID integer NOT NULL,\n" +
                    "missing_ingredient text,\n" +
                    "FOREIGN KEY (coffee_type_ID) REFERENCES coffee_type(ID_coffee_type)\n)";

            Statement st = conn.createStatement();
            st.execute(sqlCreateTableCoffeeType);
            st.execute(sqlCreateTableIngredient);
            st.execute(sqlCreateTableRecipe);
            st.execute(sqlCreateTableTransaction);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public List<Transaction> transactionList() {
//
//        String sqlAllTransactions = "SELECT ID_Transaction, time_stamp, missing_ingredient," +
//                " c.name as CoffeeName FROM transaction t" +
//                " join coffee_type c on t.coffee_type_ID = c.ID_coffee_type";
//
//        List<Transaction> resultList = new ArrayList<>();
//
//        try {
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery(sqlAllTransactions);
//
//            while (rs.next()){
//                Transaction t = new Transaction();
//                t.setId(rs.getInt("ID_Transaction"));
//                t.setTimestamp(Timestamp.valueOf(rs.getString("time_stamp")));
//                t.setMissing(rs.getString("missing_ingredient"));
//                t.setCoffeeTypeName(rs.getString("CoffeeName"));
//
//                resultList.add(t);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return resultList;
//    }

//    this gives ingredients in a row
//    SELECT group_concat(r.ingredient_amount separator  ', ') AS ingredient_amounts
//    FROM recipe r
//    JOIN coffee_type c ON c.ID_coffee_type = r.coffee_type_ID
//    JOIN ingredient i ON r.ingredient_ID = i.ID_ingredient
//    WHERE r.coffee_type_id= 1
}
