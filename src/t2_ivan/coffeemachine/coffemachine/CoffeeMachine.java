package t2_ivan.coffeemachine.coffemachine;

import t2_ivan.coffeemachine.coffemachine.repositories.CoffeeRepository;

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
    private final CoffeeType[] coffeeTypes = new CoffeeType[3];

    private String adminUsername = "admin";
    private String adminPassword = "admin12345";
    private final String statusFileName = "coffee_machine_status.txt";
    private final Logger logger;
    private final CoffeeRepository coffeeRepository;
    private int sugar;

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money, int sugar, Logger logger, CoffeeRepository coffeeRepository) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        this.logger = logger;
        this.coffeeRepository = coffeeRepository;
        this.sugar = sugar;

        if (coffeeRepository.getCoffees().size() != 3) {
            coffeeRepository.addCoffee(new CoffeeType(null, "Espresso", 350, 0,16,4));
            coffeeRepository.addCoffee(new CoffeeType(null, "Latte",350, 75,20,7));
            coffeeRepository.addCoffee(new CoffeeType(null, "Cappuccino",200, 100,12,6));
        }

        coffeeRepository.getCoffees().toArray(coffeeTypes);
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

    private boolean hasEnoughResources(CoffeeType coffeeType, int sugarAmount){
        if (water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1 && hasEnoughSugar(sugarAmount)) {
            return true;
        } else
            return false;
    }

    public int getSugar() {
        return sugar;
    }

    public boolean hasEnoughSugar(int sugarAmount) {
        return sugar >= sugarAmount;
    }

    public void buyCoffee(CoffeeType coffeeType, int sugarAmount){
        if (hasEnoughResources(coffeeType, sugarAmount)) {
            System.out.println("I have enough resources, making you " + coffeeType.getName() + "\n");
            logger.log(coffeeType, sugarAmount, true);

            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.money += coffeeType.getPrice();
            this.cups -= 1;

            if (sugarAmount > 0) {
                System.out.println("Adding " + sugarAmount + " grams of sugar to your coffee.\n");
                this.sugar -= sugarAmount;
            }
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType, sugarAmount);
            System.out.println("Sorry, not enough " + missing + "\n");
            logger.log(coffeeType, sugarAmount,false);
        }
    }

    public float takeMoney(){
        float moneyReturn = money;
        money = 0;
        return moneyReturn;
    }

    public String calculateWhichIngredientIsMissing(CoffeeType coffeeType, int sugarAmount){
        String ingredientMissing = null;
        if (water < coffeeType.getWaterNeeded()) {
            ingredientMissing = "water";
        }
        else if (milk < coffeeType.getMilkNeeded()) {
            ingredientMissing = "milk" ;
        }
        else if (coffeeBeans < coffeeType.getCoffeeBeansNeeded()) {
            ingredientMissing = "coffee beans" ;
        }
        else if (cups < 1) {
            ingredientMissing = "cups" ;
        } else if (sugar < sugarAmount) {
            ingredientMissing = "sugar";
        }
        return ingredientMissing;
    }

    public void changeAdminPassword(String newPassword) {
        if (isStrongPassword(newPassword)) {
            adminPassword = newPassword;
            System.out.println("Admin password is changed\n");
        } else {
            System.out.println("Please enter a stronger password! It must be at least 7 characters and contain at least one number.\n");
        }
    }

    private boolean isStrongPassword(String password) {
        if (password.length() < 7) {
            return false;
        }

        boolean containsDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
                break;
            }
        }

        return containsDigit;
    }

    public void fill(int water, int milk, int coffeeBeans, int cups, int sugar){
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffeeBeans;
        this.cups += cups;
        this.sugar += sugar;
    }

    public boolean login(String username, String password) {
        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            return true;
        } else
            return false;
    }


    public boolean loadFromFile(String fileName)  {
        FileReader reader = null;

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

    public void saveToFile(String fileName){
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(water + "; " +  milk + "; " + coffeeBeans + "; " + cups + "; " + money);
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
                ", sugar=" + sugar +
                '}';
    }


}