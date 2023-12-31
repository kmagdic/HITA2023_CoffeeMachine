package t1_sinisa.coffeemachine;


import t1_sinisa.coffeemachine.repositories.TransactionRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachine {

    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private float money;
    private List<CoffeeType> coffeeTypesList = new ArrayList<>();

    private String adminUsername = "admin";
    private String adminPassword = "admin12345";
    private String statusFileName = "coffee_machine_status.txt";
    //private CoffeeType espresso = new CoffeeType("Espresso", 350, 0, 16, 4);
    //private CoffeeType latte = new CoffeeType("Latte", 350, 75, 20, 7);
    //private CoffeeType cappuccino = new CoffeeType("Cappuccino", 200, 100, 12, 6);
    //private Log log = new Log();

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        //coffeeTypesList.add(espresso);
        //coffeeTypesList.add(latte);
        //coffeeTypesList.add(cappuccino);
    }

    public List<CoffeeType> getCoffeeTypes() {
        return coffeeTypesList;
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

    public void buyCoffee(CoffeeType coffeeType, Transaction transaction, TransactionRepository transactionRepository){
        Date date = new Date(System.currentTimeMillis());


        if (hasEnoughResources(coffeeType)) {
            System.out.println("I have enough resources, making you " + coffeeType.getName() + "\n");

            transaction.setFormattedDate(date);
            transaction.setDrinkType(coffeeType.getName());
            transaction.setBuyStatus("bought");
            transactionRepository.addTransaction(transaction);

            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.money += coffeeType.getPrice();
            this.cups -= 1;
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            System.out.println("Cannot make you a coffee, missing: " + missing + "\n");

            transaction.setFormattedDate(date);
            transaction.setDrinkType(coffeeType.getName());
            transaction.setBuyStatus("not bought");
            transactionRepository.addTransaction(transaction);

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

    public boolean changePassword(String password) {
        boolean containsDigit = false;
        boolean containsLowercase = false;
        boolean containsUpperCase = false;
        for (char c: password.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
                break;
            }
        }

        for (char c: password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                containsLowercase = true;
                break;
            }
        }

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUpperCase = true;
                break;
            }
        }

        if (password.length() >= 8 && containsDigit && containsUpperCase && containsLowercase) {
            this.adminPassword = password;
            return true;
        } else {
            return false;
        }
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
                '}';
    }


}