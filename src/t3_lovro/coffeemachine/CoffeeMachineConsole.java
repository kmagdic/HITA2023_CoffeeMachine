package t3_lovro.coffeemachine;

import java.util.Scanner;

public class CoffeeMachineConsole {

    Scanner sc = new Scanner(System.in);

    String fileName = "src/t3_lovro/coffeemachine/coffeemachine.txt";
    static String dBPath = "./coffeemachine.h2";
    ComWithDB comWithDB = new ComWithDB(dBPath);

    public static void main(String[] args) {

        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    void run() {
        comWithDB.createSchema();
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        System.out.println("Welcome to Coffee Machine 2.0 version Lovro");
        CoffeeMachine.coffeeType = comWithDB.coffeeType();
        boolean startedSuccessfully = machine.start();
        if (!startedSuccessfully) {
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
                    machine.saveToFile(fileName);
                    machine.stop();
                    System.out.println("Shutting down the machine. Bye!");
                    break;

                default:
                    System.out.println("No such option");
            }
        }
    }

    private void buyAction(CoffeeMachine machine) {
        if (3 <= CoffeeMachine.coffeeType.size()) {
            System.out.println("Choice: ");
            CoffeeMachine.coffeeType = comWithDB.coffeeType();
            for (CoffeeType c : CoffeeMachine.coffeeType) {
                System.out.println(c.getId() + ". " + c.getName() + " " + c.getPrice());
            }
            System.out.println();
            System.out.println("Enter your choice: ");

            int typeOfCoffeeChoice = sc.nextInt();
            if (typeOfCoffeeChoice <= CoffeeMachine.coffeeType.size() - 1) {
                String c = machine.buyCoffee(CoffeeMachine.coffeeType.get(typeOfCoffeeChoice));
                comWithDB.addTransactionToLog(CoffeeMachine.coffeeType.get(typeOfCoffeeChoice), c);
            } else {
                System.out.println("Wrong enter\n");
            }
        } else {
            System.out.println("Choice: ");
            CoffeeType[] coffeeTypes = machine.getCoffeeTypes();
            for (int i = 0; i < machine.getCoffeeTypes().length; i++) {
                System.out.println((i + 1) + " - " + coffeeTypes[i].getName());
            }
            System.out.println("Enter your choice: ");

            int typeOfCoffeeChoice = sc.nextInt();
            if (typeOfCoffeeChoice <= coffeeTypes.length) {
                machine.buyCoffee(coffeeTypes[typeOfCoffeeChoice - 1]);
                String c = machine.buyCoffee(coffeeTypes[typeOfCoffeeChoice - 1]);
                comWithDB.addTransactionToLog((coffeeTypes[typeOfCoffeeChoice - 1]), c);
            } else {
                System.out.println("Wrong enter\n");
            }
        }
    }

    private void adminMenu(CoffeeMachine machine) {
        String ch = "";
        while (!ch.equals("exit")) {
            System.out.println(" ");
            System.out.println("Write action (fill, remaining, take, password, log, addCoffeeType, exit):");
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
                    boolean i = true;
                    while (i) {
                        System.out.println("Enter new admin password: ");
                        Scanner s = new Scanner(System.in);
                        String newPassword = s.next();
                        boolean c = machine.changePassword(newPassword);
                        if (c) {
                            System.out.println("Password is changed ");
                            i = false;
                        } else {
                            System.out.println("Please enter stronger password! It has to be at least 7 characters and it needs to have at least one number ");
                        }
                    }
                    break;
                case "log":
                    System.out.println("Transaction log; ");
                    comWithDB.readingFromDB();
                    break;
                case "addCoffeeType":
                    System.out.println("Write name of new Coffee Type");
                    String name = sc.next();
                    System.out.println("How much milk is needed for new coffee type? ");
                    int newMilk = sc.nextInt();
                    System.out.println("How much water is needed for new coffee type? ");
                    int newWater = sc.nextInt();
                    System.out.println("How much coffee beans is needed for new coffee type? ");
                    int newCoffee = sc.nextInt();
                    System.out.println("What ll be the price for new coffee type ? ");
                    int newPrice = sc.nextInt();
                    CoffeeType newCoffeeType = new CoffeeType(name, newMilk, newWater, newCoffee, newPrice);
                    comWithDB.addCoffeeTypesToDB(newCoffeeType);
                    break;
                case "exit":
                    machine.saveToFile(fileName);
                    break;

            }
        }
    }


}