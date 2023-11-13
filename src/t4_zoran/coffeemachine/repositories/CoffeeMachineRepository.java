package t4_zoran.coffeemachine.repositories;

import java.sql.*;

import static t4_zoran.coffeemachine.CoffeeMachineConsole.coffeeMachineName;

public class CoffeeMachineRepository {
    Connection conn;

    public CoffeeMachineRepository(Connection conn) {
        this.conn = conn;
    }

    public void createStatusOfCoffeeMachine() {
        try {
            String sqlCreateAdmin = "CREATE TABLE IF NOT EXISTS admin (\n" +
                    "ID_admin integer PRIMARY KEY auto_increment, \n" +
                    "firstname text NOT NULL, \n" +
                    "lastname text NOT NULL, \n" +
                    "username text NOT NULL, \n" +
                    "email text NOT NULL, \n" +
                    "super_admin boolean NOT NULL\n)";

            String sqlCreateTableCoffeeMachine = "CREATE TABLE IF NOT EXISTS coffeemachine (\n" +
                    "ID_coffeemachine integer PRIMARY KEY auto_increment, \n" +
                    "name text,\n" +
                    "address text,\n" +
                    "admin_ID integer NOT NULL,\n" +
                    "water integer NOT NULL,\n" +
                    "milk integer NOT NULL,\n" +
                    "coffee_beans integer NOT NULL,\n" +
                    "disposable_cups integer NOT NULL,\n" +
                    "money decimal NOT NULL,\n" +
                    "FOREIGN KEY (admin_ID) REFERENCES admin(ID_admin)\n)";

            Statement st = conn.createStatement();
            st.execute(sqlCreateAdmin);
            st.execute(sqlCreateTableCoffeeMachine);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet coffeeMachineStatus() {

        String sqlCoffeeMachineStatus = "select water, milk, coffee_beans, disposable_cups," +
                "money, username, password from coffeemachine\n" +
                "join admin on admin_ID = ID_admin\n" +
                "where name = '" + coffeeMachineName + "'";

        ResultSet rs;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(sqlCoffeeMachineStatus);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}
