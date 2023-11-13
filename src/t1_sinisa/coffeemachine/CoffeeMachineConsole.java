package t1_sinisa.coffeemachine;

import t1_sinisa.coffeemachine.repositories.CoffeeTypeRepository;
import t1_sinisa.coffeemachine.repositories.TransactionRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachineConsole {

    static Connection conn = null;
    Scanner sc = new Scanner(System.in);
    static CoffeeTypeRepository coffeeTypeRepository;
    static TransactionRepository transactionRepository;


    public static void main(String[] args)  {

        conn = makeDBConnection("coffeemachine");
        coffeeTypeRepository = new CoffeeTypeRepository(conn);
        coffeeTypeRepository.createTable();

        transactionRepository = new TransactionRepository(conn);
        transactionRepository.createTable();

        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    void run() {
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        System.out.println("Welcome to Coffee Machine 1.0 version by Trontic");
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
        Transaction transaction = new Transaction();
        System.out.println("Choice: ");
        List<CoffeeType> coffeeTypesList = coffeeTypeRepository.getListOfCoffeeTypes();
        for (int i = 0; i < coffeeTypeRepository.getListOfCoffeeTypes().size(); i++) {
            CoffeeType coffeeType = coffeeTypesList.get(i);
            System.out.println((i + 1) + " - " + coffeeType.getName());
        }
        System.out.println("Enter your choice: ");

        int typeOfCoffeeChoice = sc.nextInt();
        if (typeOfCoffeeChoice <= coffeeTypeRepository.getListOfCoffeeTypes().size()) {
            machine.buyCoffee(coffeeTypesList.get(typeOfCoffeeChoice-1), transaction, transactionRepository);
        } else {
            System.out.println("Wrong enter\n");
        }
    }

    private void adminMenu(CoffeeMachine machine) {
        String ch = "";
        while (!ch.equals("exit")) {
            System.out.println(" ");
            System.out.println("Write action (fill, remaining, take, password, log, coffee_type, exit):");
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
                    System.out.println(machine.getCoffeeBeans() + " g of beans");
                    System.out.println(machine.getCups() + " cups");
                    System.out.println("$" + machine.getMoney() + " of money");
                    break;

                case "password":
                    boolean goodPassword;
                    do {
                        System.out.println("Enter new admin password: ");
                        String password = sc.next();
                        if (machine.changePassword(password)) {
                            System.out.println("Password successfully changed!");
                            goodPassword = true;
                        } else {
                            System.out.println("Please enter stronger password! It has to be at least" +
                                    " 8 characters, at least one upper case letter, one lower case letter" +
                                    " and one number.");
                            goodPassword = false;
                        }
                    } while (!goodPassword);
                    break;

                case "log":
                    List<Transaction> transactionList = transactionRepository.getListOfTransactions();
                    System.out.println("Log: ");
                    for (Transaction t: transactionList) {
                        System.out.println("ID: " + t.getId() +
                                " Date/Time: " + t.getFormattedDate() +
                                " Coffee type: " + t.getDrinkType() +
                                " Buy status: " + t.getBuyStatus());
                    }
                    break;

                case "coffee_type":
                    CoffeeType newCoffeeType = new CoffeeType();
                    coffeeTypeMenu(newCoffeeType);

                    break;

                case "exit":
                    break;

                default:
                    System.out.println("No such option");

            }
        }
    }

    private void coffeeTypeMenu (CoffeeType newCoffeeType) {
        int choice = 0;

        while (choice != 5) {
            System.out.println("Enter your choice: ");
            System.out.println(" 1 - Insert new coffee type");
            System.out.println(" 2 - Get list of all coffee types");
            System.out.println(" 3 - Update coffee types");
            System.out.println(" 4 - Delete coffee type");
            System.out.println(" 5 - Exit this menu");
            choice = sc.nextInt();

            switch (choice) {
                case 1:

                    System.out.println("Add coffee types to the database: ");
                    System.out.print("Enter name: ");
                    newCoffeeType.setName(sc.next());
                    System.out.print("Enter amount of water in ml: ");
                    newCoffeeType.setWaterNeeded(sc.nextInt());
                    System.out.print("Enter amount of milk in ml: ");
                    newCoffeeType.setMilkNeeded(sc.nextInt());
                    System.out.print("Enter amount of coffee beans in g: ");
                    newCoffeeType.setCoffeeBeansNeeded(sc.nextInt());
                    System.out.print("Enter price of this drink: ");
                    newCoffeeType.setPrice(sc.nextInt());

                    coffeeTypeRepository.insertCoffeeType(newCoffeeType);
                    break;

                case 2:

                    List<CoffeeType> coffeeType = coffeeTypeRepository.getListOfCoffeeTypes();
                    System.out.println();

                    for (CoffeeType c : coffeeType) {
                        System.out.println("Id:" + c.getId() + " " +
                                "Name:" + c.getName() + " " +
                                "Water:" + c.getWaterNeeded() + "ml " +
                                "Milk:" + c.getMilkNeeded() + "ml " +
                                "Coffee beans:" + c.getCoffeeBeansNeeded() + "g " +
                                "Price:" + c.getPrice() + "€");
                    }

                    System.out.println();
                    break;

                case 3:
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    CoffeeType c = findCoffeeType(id);

                    if (c == null) {
                        System.out.println("Coffee type doesn't exist");
                    } else {

                        System.out.print("Name: ");
                        c.setName(sc.next());
                        System.out.print("Water: ");
                        c.setWaterNeeded(sc.nextInt());
                        System.out.print("Milk: ");
                        c.setMilkNeeded(sc.nextInt());
                        System.out.print("Coffee beans: ");
                        c.setCoffeeBeansNeeded(sc.nextInt());
                        System.out.print("Price: ");
                        c.setPrice(sc.nextInt());

                        coffeeTypeRepository.updateCoffeeType(c);
                        System.out.println("Updated");
                    }
                    break;

                case 4:
                    System.out.print("ID: ");
                    int id1 = sc.nextInt();
                    CoffeeType c1 = findCoffeeType(id1);

                    if (c1 == null) {
                        System.out.println("Coffee type doesn't exist");
                    } else {
                        coffeeTypeRepository.deleteCoffeeType(c1);
                        System.out.println("Deleted");
                    }
                    break;

                case 5:
                    break;

                default:
                    System.out.println("No such option!");
            }

        }

    }

    public static Connection makeDBConnection (String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CoffeeType findCoffeeType(int id) {
        List<CoffeeType> coffeeTypeList = coffeeTypeRepository.getListOfCoffeeTypes();
        for (CoffeeType c : coffeeTypeList) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }



}