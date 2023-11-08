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
    public static void createSchema(Connection conn) {

        String createTableSql =
                "CREATE TABLE IF NOT EXISTS Log (\n" +
                        "    ID INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    Date VARCHAR(255) NOT NULL,\n" +
                        "    Message VARCHAR(255) NOT NULL\n);";


        try {

            Statement stmt = conn.createStatement();

            // create a new table if it doesn't exist
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public static void addLog(String date, String message) {
        String sql = "INSERT INTO Log(Date, Message) VALUES(?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setString(2, message);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)  {
        CoffeeMachineConsole console = new CoffeeMachineConsole();

        makeDBConnection("./banksystem.h2");
        createSchema(conn);

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
                    //for (Transaction t: machine.logList)
                    //System.out.println(t.getCroatianDateStr() + " "+  t.getWhatHappened());
                    String query = "SELECT * FROM Log";

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
                    }
                    break;

                case "exit":
                    break;

            }
        }
    }



}