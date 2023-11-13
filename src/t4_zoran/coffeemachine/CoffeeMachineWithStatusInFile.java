package t4_zoran.coffeemachine;

import java.io.*;
import java.sql.*;

class CoffeeMachineWithStatusInFile extends CoffeeMachine{

    public CoffeeMachineWithStatusInFile(int water, int milk, int coffeeBeans, int cups, float money, Connection conn) {
        super(water, milk, coffeeBeans, cups, money, conn);
    }

    public boolean loadFromDatabase(ResultSet rs) throws SQLException {

        if (rs.next()) {
            setWater(rs.getInt(1));
            setMilk(rs.getInt(2));
            setCoffeeBeans(rs.getInt(3));
            setCups(rs.getInt(4));
            setMoney(rs.getInt(5));
            setAdminUsername(rs.getString(6));
            setAdminPassword(rs.getString(7));
        }
        return true;
    }

    public void saveToFile(String fileName){
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(getWater() + "; " +  getMilk() + "; " + getCoffeeBeans() +
                    "; " + getCups() + "; " + getMoney());
            writer.write("\n");
            writer.write(getAdminUsername() + "; " + getAdminPassword());
            writer.write("\n");

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
