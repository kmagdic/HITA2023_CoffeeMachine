package t2_ivan.coffeemachine.coffemachine;

import t2_ivan.coffeemachine.coffemachine.repositories.CoffeeRepository;
import t2_ivan.coffeemachine.coffemachine.repositories.LogRepository;

import java.util.Scanner;

public class CoffeeMachineConsole {

    Scanner sc = new Scanner(System.in);
    private static CoffeeRepository coffeeRepository;
    private static LogRepository logRepository;

    public static void main(String[] args)  {
        DatabaseManager db = new DatabaseManager("ivan_coffee");
        coffeeRepository = new CoffeeRepository(db.getConnection());
        logRepository = new LogRepository(db.getConnection());
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    void run() {
        Logger logger = new Logger(logRepository);

        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550, 50, logger, coffeeRepository);
        System.out.println("Welcome to Coffee Machine 3.0 version Ivan");
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
                        adminMenu(machine, logger);
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

        System.out.println("Enter how many grams of sugar you want:");
        int sugar = sc.nextInt();

        if (typeOfCoffeeChoice <= coffeeTypes.length) {
            machine.buyCoffee(coffeeTypes[typeOfCoffeeChoice - 1], sugar);
        } else {
            System.out.println("Wrong enter\n");
        }
    }

    private void adminMenu(CoffeeMachine machine, Logger logger) {
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
                    System.out.println("Write how many grams of sugar you want to add:");
                    int sugar = sc.nextInt();
                    System.out.println("\"Write how many disposable cups you want to add: ");
                    int cup = sc.nextInt();
                    machine.fill(water, milk, coffeeBeans, cup, sugar);
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
                    System.out.println(machine.getSugar() + " sugar");
                    System.out.println("$" + machine.getMoney() + " of money");
                    break;

                case "password":
                    System.out.println("Enter the new admin password: ");
                    String newPassword = sc.next();
                    machine.changeAdminPassword(newPassword);
                    break;

                case "log":
                    logger.printLog();

                case "exit":
                    break;

            }
        }
    }
}