package t4_luka.coffeemachine;

import java.sql.*;
import java.util.Scanner;

public class CoffeeMachineConsole {
    private static Connection conn;

    Scanner sc = new Scanner(System.in);

    private static void makeDBConnection(String fileName) {
        try {
            conn = DriverManager.getConnection("jdbc:h2:" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createCoffeeType(Connection conn) {

        String createTableSql =
                "CREATE TABLE IF NOT EXISTS CoffeeType (\n" +
                        "    ID INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    Name VARCHAR(255) NOT NULL,\n" +
                        "    Water INT NOT NULL,\n" +
                        "    Milk INT NOT NULL,\n" +
                        "    CoffeeBeans INT NOT NULL,\n" +
                        "    Price INT NOT NULL,\n" +
                        "    Cups INT NOT NULL\n" +
                        "    );";


        try {

            Statement stmt = conn.createStatement();

            // create a new table if it doesn't exist
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public static void createTransactionLog(Connection conn) {

        String createTableSql =
                "CREATE TABLE IF NOT EXISTS TransactionLog (\n" +
                        "    ID INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    DateTime VARCHAR(255) NOT NULL,\n" +
                        "    CoffeeTypeID INT NOT NULL,\n" +
                        "    FOREIGN KEY (CoffeeTypeID) REFERENCES CoffeeType(ID),\n" +
                        "    Success VARCHAR(255) NOT NULL\n" +
                        "    );";


        try {

            Statement stmt = conn.createStatement();

            // create a new table if it doesn't exist
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void addTransactionLog(String dateTime, int coffeeTypeID, String success) {
        String sql = "INSERT INTO TransactionLog(DateTime, CoffeeTypeID, Success) VALUES(?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dateTime);
            pstmt.setInt(2, coffeeTypeID);
            pstmt.setString(3, success);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static int returnNumberOfCoffeeTypes(Connection conn){
        String query = "SELECT MAX(ID) FROM CoffeeType";
        int id=0;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Execute the SELECT query
            ResultSet resultSet = pstmt.executeQuery();
            id = resultSet.getInt("ID");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
    public static void insertCoffeeTypes() {
        String sql = "INSERT INTO CoffeeType (Name, Water, Milk, CoffeeBeans, Price, Cups) VALUES(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "Espresso");
                pstmt.setInt(2, 350);
                pstmt.setInt(3, 0);
                pstmt.setInt(4, 16);
                pstmt.setInt(5, 4);
                pstmt.setInt(6, 1);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "Latte");
                pstmt.setInt(2, 350);
                pstmt.setInt(3, 75);
                pstmt.setInt(4, 20);
                pstmt.setInt(5, 7);
                pstmt.setInt(6, 1);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "Capuccino");
                pstmt.setInt(2, 200);
                pstmt.setInt(3, 100);
                pstmt.setInt(4, 12);
                pstmt.setInt(5, 6);
                pstmt.setInt(6, 1);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        public static void printLog(Connection conn){
            String query = "SELECT DateTime, Name, Success FROM TransactionLog JOIN CoffeeType ON CoffeeTypeID=CoffeeType.ID;";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Execute the SELECT query
                ResultSet resultSet = pstmt.executeQuery();

                // Process the result set
                while (resultSet.next()) {
                    // Retrieve data from the result set
                    // Assuming 'id' is a column in your 'Log' table
                    String dateTime = resultSet.getString("DateTime");
                    String name = resultSet.getString("Name");
                    String success = resultSet.getString("Success"); // Assuming 'message' is a column in your 'Log' table

                    // Do something with the retrieved data
                    System.out.println("DateTime: " + dateTime + ", Name: " + name + ", Success: " + success);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    public static void main(String[] args)  {
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        makeDBConnection("./banksystem.h2");
        createCoffeeType(conn);
        createTransactionLog(conn);
        if (returnNumberOfCoffeeTypes(conn)!=3){
            insertCoffeeTypes();
        }

        console.run();
    }

    void run() {
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        System.out.println("Welcome to Coffee Machine 1.0 version by Luka");
        boolean startedSuccessfully = machine.start();

        if(!startedSuccessfully) {
            System.out.println("Coffee machine started but without file. Using default values.");
        }

        String action = "";

        while (!action.equals("exit")) {
            System.out.println("Write action (buy, login, exit): ");
            action = sc.next();
            switch (action) {
                case "buy":
                    buyAction(machine);
                    break;

                case "login":
                    System.out.println("Enter username: ");
                    String username = sc.next();
                    System.out.println("Enter password: ");
                    String password = sc.next();

                    if (machine.login(username, password)) {
                        adminMenu(machine);
                    } else {
                        System.out.println("Wrong username or password\n");
                    }
                    break;

                case "exit":
                    machine.stop();
                    System.out.println("Shutting down the machine. Bye!");
                    break;

                default:
                    System.out.println("No such option");
            }
        }
    }

    private void buyAction(CoffeeMachine machine) {
        System.out.println("Choice: ");
        CoffeeType[] coffeeTypes = machine.getCoffeeTypes();
        for (int i = 0; i < machine.getCoffeeTypes().length; i++) {
            System.out.println((i + 1) + " - " + coffeeTypes[i].getName());
        }
        System.out.println("Enter your choice: ");

        int typeOfCoffeeChoice = sc.nextInt();
        if (typeOfCoffeeChoice <= coffeeTypes.length) {
            machine.buyCoffee(coffeeTypes[typeOfCoffeeChoice - 1]);
        } else {
            System.out.println("Wrong enter\n");
        }
    }

    private void adminMenu(CoffeeMachine machine) {
        String ch = "";
        while (!ch.equals("exit")) {
            System.out.println(" ");
            System.out.println("Write action (fill, remaining, take, password, log, exit):");
            ch = sc.next();

            switch (ch) {
                case "fill":
                    System.out.println("Write how many ml of water you want to add:");
                    int water = sc.nextInt();
                    System.out.println("Write how many ml of milk you want to add:");
                    int milk = sc.nextInt();
                    System.out.println("Write how many grams of coffee beans you want to add:");
                    int coffeeBeans = sc.nextInt();
                    System.out.println("\"Write how many disposable cups you want to add: ");
                    int cup = sc.nextInt();
                    machine.fill(water, milk, coffeeBeans, cup);
                    break;

                case "take":
                    float amount = machine.takeMoney();
                    System.out.println("I gave you $" + amount + "\n");
                    break;

                case "remaining":
                    System.out.println("The coffee machine has:");
                    System.out.println(machine.getWater() + " ml of water");
                    System.out.println(machine.getMilk() + " ml of milk");
                    System.out.println(machine.getCoffeeBeans() + " g of water");
                    System.out.println(machine.getCups() + " cups");
                    System.out.println("$" + machine.getMoney() + " of money");
                    break;

                case "password":
                    System.out.println("Enter new admin password: ");
                    String newPassword = sc.next();
                    while (!(newPassword.length() > 7 && (newPassword.contains("0")
                            || newPassword.contains("1")
                            || newPassword.contains("2")
                            || newPassword.contains("3")
                            || newPassword.contains("4")
                            || newPassword.contains("5")
                            || newPassword.contains("6")
                            || newPassword.contains("7")
                            || newPassword.contains("8")
                            || newPassword.contains("9")))){
                        System.out.println("Please enter stronger password! It has to be a least 7 characters and it needs has at least one number.");
                        newPassword = sc.next();
                    }
                    machine.setAdminPassword(newPassword);
                    machine.saveToFile("coffee_machine_status.txt");
                    System.out.println("Password is changed");
                    break;

                case "log":
                    printLog(conn);
                    //for (Transaction t: machine.logList)
                    //System.out.println(t.getCroatianDateStr() + " "+  t.getWhatHappened());
                    /*String query = "SELECT * FROM Log";

                    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                        // Execute the SELECT query
                        ResultSet resultSet = pstmt.executeQuery();

                        // Process the result set
                        while (resultSet.next()) {
                            // Retrieve data from the result set
                            int id = resultSet.getInt("ID");  // Assuming 'id' is a column in your 'Log' table
                            String date = resultSet.getString("Date");
                            String message = resultSet.getString("Message"); // Assuming 'message' is a column in your 'Log' table

                            // Do something with the retrieved data
                            System.out.println("ID: " + id + "Date: " + date + ", Message: " + message);
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }*/
                    break;

                case "exit":
                    break;

            }
        }
    }
}
