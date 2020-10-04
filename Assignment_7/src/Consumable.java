/**
 * This is an interface to define abstract methods for consumable commodities.
 *
 * @author Jonghwan Lee
 */
public interface Consumable {
    /**
     * @return calorie of food. Not implemented here
     */
    public abstract int getCalorieCount();

    /**
     * @return consumption method. Not implemented here
     */
    public abstract String getConsumptionMethod();
}