import java.util.Scanner;

/**
 * This is a class to implement the interface of Consumable.
 * This is also to implement the super class of TimsProduct.
 *
 * @author Jonghwan Lee
 */
public class Coffee extends TimsProduct implements Consumable {

    /** description of coffee */
    private String description;
    /** calorie of coffee */
    private int calorieCount;

    /**
     * Constructor
     * @param name         name of coffee
     * @param description  description of coffee
     * @param cost         cost of coffee
     * @param price        price of coffee
     * @param calorieCount calorie of coffee
     */
    private Coffee(String name, String description, double cost, double price, int calorieCount) {
        super(name, cost, price);
        this.description = description;
        this.calorieCount = calorieCount;
    }

    /**
     * @return object of Coffee
     */
    public static Coffee create() {

        Coffee[] coffees = new Coffee[3];
        coffees[0] = new Coffee("Black", "aromatic", 1.25, 2.99, 15);
        coffees[1] = new Coffee("Caffe Latte", "milky", 1.35, 4.29, 120);
        coffees[2] = new Coffee("Cappuccino", "savory", 1.45, 3.59, 130);

        System.out.println("\nWhich coffee would you like to have?");
        for (int i = 0; i < coffees.length; i++) {
            System.out.println((i + 1) + ") " + coffees[i].getName() + " - " + coffees[i].getDescription() + ", "
                    + coffees[i].getCalorieCount() + " cal., $" + coffees[i].getRetailPrice());
        }

        System.out.print("Please enter the number: ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt() - 1;
        while (index < 0 || index >= coffees.length) {
            System.out.print("Please re-enter the correct number: ");
            index = sc.nextInt() - 1;
        }

        return coffees[index];
    }

    /**
     * @return description of coffee
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return calorie of coffee
     */
    @Override
    public int getCalorieCount() {
        return calorieCount;
    }

    /**
     * @return consumption method of coffee
     */
    @Override
    public String getConsumptionMethod() {
        return "Drink it";
    }

    /**
     * @return values of instance variables
     */
    @Override
    public String toString() {
        return super.toString() + "\n\tType... Coffee {description= " + description + ", calorieCount= "
                + calorieCount + " cal.}" + "\n\tHow to consume... " + getConsumptionMethod();
    }
}
