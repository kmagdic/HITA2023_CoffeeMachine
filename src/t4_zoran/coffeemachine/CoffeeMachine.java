package t4_zoran.coffeemachine;

import java.sql.*;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private float money;

    private final Connection conn;

    private final CoffeeType[] coffeeTypes = new CoffeeType[3];

    private String adminUsername = "admin";
    private String adminPassword = "admin123";

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money, Connection conn) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        this.conn = conn;

        coffeeTypes[0] = new CoffeeType("Espresso", 350, 0, 16, 4);
        coffeeTypes[1] = new CoffeeType("Latte",350, 75, 20, 7);
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

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public void setCoffeeBeans(int coffeeBeans) {
        this.coffeeBeans = coffeeBeans;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public boolean hasEnoughResources(CoffeeType coffeeType){
        return water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups > 0;
    }

    public void buyCoffee(CoffeeType coffeeType){
        String missing = calculateWhichIngredientIsMissing(coffeeType);
        boolean success;

        if (hasEnoughResources(coffeeType)) {
            System.out.println("I have enough resources, making you " + coffeeType.getName() + "\n");

            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.money += coffeeType.getPrice();
            this.cups -= 1;
            success = true;
        } else {
            System.out.println("Sorry, not enough " + missing + "\n");
            success = false;
        }

        Transaction transaction = new Transaction(coffeeType.getName(), success, missing);

        DatabaseManager dbm = new DatabaseManager(conn);
        dbm.insertTransaction(transaction);
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
        }        else if (cups < 1) {
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
        return adminUsername.equals(username) && adminPassword.equals(password.trim());
    }

    public boolean passwordIsStrong(String newPassword){

        boolean containsDigit = false;

        for (char c : newPassword.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
                break;
            }
        }

        if (newPassword.length() > 6 && containsDigit) {
            setAdminPassword(newPassword);
            return true;
        } else
            return false;
    }
}
