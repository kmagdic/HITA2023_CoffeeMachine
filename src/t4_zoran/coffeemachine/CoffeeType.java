package t4_zoran.coffeemachine;

public class CoffeeType {

    private int coffeeTypeID;
    private String name;
    private int waterNeeded;
    private int milkNeeded;
    private int coffeeBeansNeeded;
    private int price;


    public CoffeeType(int coffeeTypeID, String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, int price) {
        this.coffeeTypeID = coffeeTypeID;
        this.name = name;
        this.waterNeeded = waterNeeded;
        this.milkNeeded = milkNeeded;
        this.coffeeBeansNeeded = coffeeBeansNeeded;
        this.price = price;
    }

    public CoffeeType() {

    }

    public int getCoffeeTypeID() {
        return coffeeTypeID;
    }

    public void setCoffeeTypeID(int coffeeTypeID) {
        this.coffeeTypeID = coffeeTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMilkNeeded() {
        return milkNeeded;
    }

    public void setMilkNeeded(int milkNeeded) {
        this.milkNeeded = milkNeeded;
    }

    public int getWaterNeeded() {
        return waterNeeded;
    }

    public void setWaterNeeded(int waterNeeded) {
        this.waterNeeded = waterNeeded;
    }

    public int getCoffeeBeansNeeded() {
        return coffeeBeansNeeded;
    }

    public void setCoffeeBeansNeeded(int coffeeBeansNeeded) {
        this.coffeeBeansNeeded = coffeeBeansNeeded;
    }

}
