/**
 * This is an interface to define abstract methods for commodities.
 *
 * @author Jonghwan Lee
 */
public interface Commodity {
    /**
     * @return cost of product. Not implemented here
     */
    public abstract double getProductionCost();

    /**
     * @return price of product. Not implemented here
     */
    public abstract double getRetailPrice();
}