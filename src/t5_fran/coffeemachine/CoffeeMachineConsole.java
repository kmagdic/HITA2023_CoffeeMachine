package t5_fran.coffeemachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachineConsole {

    Scanner sc = new Scanner(System.in);
    static Connection conn;

    public static void main(String[] args)  {
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
        makeDBConnection("./cmtransactions.h2");
    }

    public static void makeDBConnection(String fileName) {
        try {
            conn = DriverManager.getConnection("jdbc:h2:" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void run() {

        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        System.out.println("Welcome to Coffee Machine 1.0 - version by Fran!");
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
                    System.out.println(machine.getCoffeeBeans() + " g of coffee beans");
                    System.out.println(machine.getCups() + " cups");
                    System.out.println("$" + machine.getMoney() + " of money");
                    break;

                case "password":
                    boolean pass1 = false;
                    boolean pass2 = false;
                    boolean ultimatePass = false;
                    while (ultimatePass != true) {

                        pass1 = false;
                        pass2 = false;
                        System.out.println("Enter new admin password:");
                        String newPass = sc.next();
                        char[] newPassArray = newPass.toCharArray();
                        for (char letter : newPassArray) {
                            if (Character.isDigit(letter)) {
                                pass1 = true;
                            }
                        }
                        if (newPass.length() >= 7) {
                            pass2 = true;
                        }
                        if (pass1 == true && pass2) {
                            machine.setAdminPassword(newPass);
                            ultimatePass = true;
                        } else {
                            System.out.println("Please enter stronger password! It has to be a least 7 characters and it needs to have at least one number.");
                        }

                    }
                    break;

                case "log":
                    List<Transaction> theTransactions = machine.getTransactions();

                    for (Transaction aTransaction : theTransactions) {

                        System.out.println(aTransaction.getTransactionInfo());

                    }

                    break;

                case "exit":
                    break;

            }
        }
    }



}