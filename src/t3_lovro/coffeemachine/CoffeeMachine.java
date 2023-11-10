package t3_lovro.coffeemachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CoffeeMachine {

    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private float money;
    private CoffeeType[] coffeeTypes = new CoffeeType[3];

    private String adminUsername = "admin";
    private String adminPassword = "admin12345";
    private final String statusFileName = "coffeemachine.txt";


    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        coffeeTypes[0] = new CoffeeType("Espresso", 350, 0, 16, 4);
        coffeeTypes[1] = new CoffeeType("Latte", 350, 75, 20, 7);
        coffeeTypes[2] = new CoffeeType("Capuccino", 200, 100, 12, 6);


    }

    public CoffeeType[] getCoffeeTypes() {
        return coffeeTypes;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getCups() {
        return cups;
    }

    public double getMoney() {
        return money;
    }


    public boolean hasEnoughResources(CoffeeType coffeeType) {
        if (water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1) {
            return true;
        } else
            return false;
    }

    public  String buyCoffee(CoffeeType coffeeType) {
        if (hasEnoughResources(coffeeType)) {
            System.out.println("I have enough resources, making you " + coffeeType.getName() + "\n");

            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.money += coffeeType.getPrice();
            this.cups -= 1;

            return "Bought";

        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            System.out.println("Sorry, not enough " + missing + "\n");
            String a = "Not bought, no enough ingredients: " + missing ;

            return a;
        }
    }

    public float takeMoney() {
        float moneyReturn = money;
        money = 0;
        return moneyReturn;
    }

    public String calculateWhichIngredientIsMissing(CoffeeType coffeeType) {
        String ingredientMissing = null;
        if (water < coffeeType.getWaterNeeded()) {
            ingredientMissing = "water";
        } else if (milk < coffeeType.getMilkNeeded()) {
            ingredientMissing = "milk";
        } else if (coffeeBeans < coffeeType.getCoffeeBeansNeeded()) {
            ingredientMissing = "coffee beans";
        } else if (cups < 1) {
            ingredientMissing = "cups";
        }
        return ingredientMissing;
    }

    public void fill(int water, int milk, int coffeeBeans, int cups) {
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffeeBeans;
        this.cups += cups;
    }

    public boolean login(String username, String password) {
        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            return true;
        } else
            return false;
    }

    public boolean loadFromFile(String fileName) {
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

        water = fileScanner.nextInt();
        milk = fileScanner.nextInt();
        coffeeBeans = fileScanner.nextInt();
        cups = fileScanner.nextInt();
        money = Float.parseFloat(fileScanner.next());

        adminUsername = fileScanner.next();
        adminPassword = (fileScanner.next()).trim();

        return true;


    }

    public boolean changePassword(String newPassword) {
        boolean containsDigit = false;
        boolean containsLowercase = false;
        boolean containsUpperCase = false;
        for (char c : newPassword.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
                break;
            }
        }
        for (char c : newPassword.toCharArray()) {
            if (Character.isLowerCase(c)) {
                containsLowercase = true;
                break;
            }
        }
        for (char c : newPassword.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUpperCase = true;
                break;
            }
        }
        if (newPassword.length() >= 8 && containsDigit && containsUpperCase && containsLowercase) {
            this.adminPassword = newPassword;
            return true;
        } else {
            return false;
        }
    }

    public void saveToFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(water + "; " + milk + "; " + coffeeBeans + "; " + cups + "; " + money);
            writer.write("\n");
            writer.write(adminUsername + "; " + adminPassword);
            writer.write("\n");

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean start() {
        return loadFromFile(statusFileName);
    }

    public void stop() {
        saveToFile(statusFileName);
    }

    @Override
    public String toString() {
        return "CoffeeMachine{" +
                "water=" + water +
                ", milk=" + milk +
                ", coffeeBeans=" + coffeeBeans +
                ", cups=" + cups +
                ", money=" + money +
                '}';
    }


}