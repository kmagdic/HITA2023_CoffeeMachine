package t4_zoran.coffeemachine;

import java.sql.*;
import java.util.*;

public class CoffeeMachineConsole {

    Scanner sc = new Scanner(System.in);
    String coffeeMachineStatusFileName = "src/t4_zoran/coffeemachine/coffee_machine_status.txt";
    static Connection conn;

    public static void main(String[] args)  {

        conn = makeDBConnection("coffee_machines.db");

        DatabaseManager dbm = new DatabaseManager(conn);

        dbm.createDatabase();

        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.start(conn);
    }

    void start(Connection conn) {
        CoffeeMachineWithStatusInFile machine = new CoffeeMachineWithStatusInFile(400, 540, 120, 9, 550, conn);
        System.out.println("Welcome to Coffee Machine 2.3 version by Zoran");

        boolean loadedSuccessfully  = machine.loadFromFile(coffeeMachineStatusFileName);
        if(!loadedSuccessfully) {
            System.out.println("Coffee machine status file is not found. Using default values.");
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
                    machine.saveToFile(coffeeMachineStatusFileName);
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

    private void adminMenu(CoffeeMachineWithStatusInFile machine) {
        String ch = "";
        while (!ch.equals("exit")) {
            System.out.println();
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
                    System.out.println("Write how many disposable cups you want to add: ");
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
                    System.out.println(machine.getCoffeeBeans() + " g of coffeeBeans");
                    System.out.println(machine.getCups() + " cups");
                    System.out.println("$" + machine.getMoney() + " of money");
                    break;

                case "password":
                    System.out.println("Enter new admin password:");
                    String newPassword;
                    boolean isStrong = false;
                    while (!isStrong) {
                        newPassword = sc.next();
                        if (machine.passwordIsStrong(newPassword)) {
                            machine.saveToFile(coffeeMachineStatusFileName);
                            System.out.println("Password is changed");
                            isStrong = true;
                        }
                        else {
                            System.out.println("Please enter stronger password! " +
                                    "It has to be a least 7 characters and it needs has at least one number.");
                        }
                    }

                case "log":
                    DatabaseManager dbm = new DatabaseManager(conn);
                    List<Transaction> transactionList = dbm.transactionList();
                    for (Transaction t : transactionList) {
                        System.out.print("Date/time: " + t.getTimestamp() + ", coffee type: " +
                                t.getCoffeeType() + ", action: ");
                        if (t.getMissing() == null) {
                            System.out.println("Bought");
                        } else {
                            System.out.println("Not bought, not enough ingredients: " + t.getMissing());
                        }
                    }
                    System.out.println();
                    break;

                case "exit":
                    break;
            }
        }
    }

    public static Connection makeDBConnection(String filename){
        try {
            return DriverManager.getConnection("jdbc:h2:./" + filename);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}