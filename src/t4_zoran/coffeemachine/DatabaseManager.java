package t4_zoran.coffeemachine;

import java.sql.*;

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
}
