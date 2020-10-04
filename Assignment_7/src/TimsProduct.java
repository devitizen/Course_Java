/**
 * This is a class to implement the interface of Commodity.
 * This includes instance variables and methods for subclasses.
 *
 * @author Jonghwan Lee
 */
public abstract class TimsProduct implements Commodity {

    /** name of product */
    private String name;
    /** cost and price of product */
    private double cost, price;

    /**
     * Constructor
     * @param name  name of product
     * @param cost  cost of product
     * @param price price of product
     */
    public TimsProduct(String name, double cost, double price) {
        this.name = name;
        this.cost = cost;
        this.price = price;
    }

    /**
     * @return name of product
     */
    public String getName() {
        return name;
    }

    /**
     * @return cost of product
     */
    @Override
    public double getProductionCost() {
        return cost;
    }

    /**
     * @return price of product
     */
    @Override
    public double getRetailPrice() {
        return price;
    }

    /**
     * @return values of instance variables
     */
    @Override
    public String toString() {
        return "\nTimsProduct {name= " + name + ", Cost= $" + cost + ", Price= $" + price + "}";
    }
}