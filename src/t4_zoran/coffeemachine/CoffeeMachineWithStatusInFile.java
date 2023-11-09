package t4_zoran.coffeemachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

class CoffeeMachineWithStatusInFile extends CoffeeMachine{

    public CoffeeMachineWithStatusInFile(int water, int milk, int coffeeBeans, int cups, float money, Connection conn) {
        super(water, milk, coffeeBeans, cups, money, conn);
    }

    public boolean loadFromFile(String fileName)  {
        FileReader reader;

        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }

        Scanner fileScanner = new Scanner(reader);

        // FILE format:
        // <water_status>; <milk_status>; <coffee_beans_status>; <cups_status>; <money_status>
        // <admin_username>; <admin_password>

        fileScanner.useDelimiter("; |\n"); // delimiter is "; " or "\n" (for the last value)

        setWater(fileScanner.nextInt());
        setMilk(fileScanner.nextInt());
        setCoffeeBeans(fileScanner.nextInt());
        setCups(fileScanner.nextInt());
        setMoney(Float.parseFloat(fileScanner.next()));

        setAdminUsername(fileScanner.next());
        setAdminPassword(fileScanner.next());

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
