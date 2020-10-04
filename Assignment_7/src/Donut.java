import java.util.Scanner;

/**
 * This is a class to implement the interface of Consumable.
 * This is also to implement the super class of TimsProduct.
 *
 * @author Jonghwan Lee
 */
public class Donut extends TimsProduct implements Consumable {

    /** description of donut */
    private String description;
    /** calorie of donut */
    private int calorieCount;

    /**
     * Constructor
     * @param name         name of donut
     * @param description  description of donut
     * @param cost         cost of donut
     * @param price        price of donut
     * @param calorieCount calorie of donut
     */
    private Donut(String name, String description, double cost, double price, int calorieCount) {
        super(name, cost, price);
        this.description = description;
        this.calorieCount = calorieCount;
    }

    /**
     * @return object of Donut
     */
    public static Donut create() {

        Donut[] donuts = new Donut[3];
        donuts[0] = new Donut("Sticky", "gooey", 0.25, 0.99, 320);
        donuts[1] = new Donut("Filled", "thick", 0.35, 1.29, 400);
        donuts[2] = new Donut("Yeast", "soft", 0.45, 1.59, 280);

        System.out.println("\nWhich donut would you like to have?");
        for (int i = 0; i < donuts.length; i++) {
            System.out.println((i + 1) + ") " + donuts[i].getName() + " - " + donuts[i].getDescription() + ", "
                    + donuts[i].getCalorieCount() + " cal., $" + donuts[i].getRetailPrice());
        }

        System.out.print("Please enter the number: ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt() - 1;
        while (index < 0 || index >= donuts.length) {
            System.out.print("Please re-enter the correct number: ");
            index = sc.nextInt() - 1;
        }

        return donuts[index];
    }

    /**
     * @return description of donut
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return calorie of donut
     */
    @Override
    public int getCalorieCount() {
        return calorieCount;
    }

    /**
     * @return consumption method of donut
     */
    @Override
    public String getConsumptionMethod() {
        return "Eat it";
    }

    /**
     * @return values of instance variables
     */
    @Override
    public String toString() {
        return super.toString() + "\n\tType... Donut {description= " + description + ", calorieCount= "
                + calorieCount + " cal.}" + "\n\tHow to consume... " + getConsumptionMethod();
    }
}
