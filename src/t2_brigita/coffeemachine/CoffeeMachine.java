package t2_brigita.coffeemachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
public class CoffeeMachine {

    protected int water;
    protected int milk;
    protected int coffeeBeans;
    protected int cups;
    protected float money;
    private ArrayList<CoffeeType> coffeeTypes = new ArrayList<>();
    private ArrayList<String> transactionLog = new ArrayList<>();

    String adminUsername = "admin";
    String adminPassword = "admin12345";
    String statusFileName = "coffee_machine_status.txt";

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        coffeeTypes.add(new CoffeeType("Espresso", 350, 0, 16, 4));
        coffeeTypes.add(new CoffeeType("Latte", 350, 75, 20, 7));
        coffeeTypes.add(new CoffeeType("Cappuccino", 200, 100, 12, 6));
    }


    public ArrayList<CoffeeType> getCoffeeTypes() {
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

    public boolean hasEnoughResources(CoffeeType coffeeType){
        if (water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1) {
            return true;
        } else
            return false;
    }

    public void buyCoffee(CoffeeType coffeeType){
        if (hasEnoughResources(coffeeType)) {
            System.out.println("I have enough resources, making you " + coffeeType.getName() + "\n");

            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.money += coffeeType.getPrice();
            this.cups -= 1;

            logTransaction(coffeeType, "Bought");

        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            System.out.println("Sorry, not enough " + missing + "\n");

            logTransaction(coffeeType, "Not bought, not enough ingredients: " + missing);
        }
    }

    public float takeMoney(){
        float moneyReturn = money;
        money = 0;
        return moneyReturn;
    }

    public String calculateWhichIngredientIsMissing(CoffeeType coffeeType){
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
        }
        return ingredientMissing;
    }

    public void fill(int water, int milk, int coffeeBeans, int cups){
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

    public void changeAdminPassword(String newPassword) {
        Scanner sc = new Scanner(System.in);
        while (!isValidPassword(newPassword)) {
            System.out.println("Please enter a stronger password! It has to be at least 7 characters and must contain " +
                    "at least one number.");
            System.out.println("Enter new admin password:");
            newPassword = sc.next();
        }
        adminPassword = newPassword;
        System.out.println("Password is changed");
    }

    private boolean isValidPassword(String password) {
        int digitCount = 0;
        for (char character : password.toCharArray()) {
            if (Character.isDigit(character)) {
                digitCount++;
            }
        }
        return password.length() >= 7 && digitCount > 0;
    }

    private void logTransaction(CoffeeType coffeeType, String action) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String logEntry = "Date/time: " + dateFormat.format(date) + ", coffee type: " + coffeeType.getName() + ", action: "
                + action;
        transactionLog.add(logEntry);
    }

    public void viewTransactionLog() {
        for (String entry : transactionLog) {
            System.out.println(entry);
        }
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