import java.util.Scanner;

/**
 * This is a class to call TimsProduct to create an array for an order under the name of a customer.
 *
 * @author Jonghwan Lee
 */
public class TimsOrder {

    /**
     * number of products ordered
     */
    private int size;
    /**
     * name of customer
     */
    private String name;
    /**
     * array of products ordered
     */
    private TimsProduct[] timsProducts;

    /**
     * Constructor of TimsOrder
     *
     * @param name         name of customer
     * @param size         number of products ordered
     * @param timsProducts array of products ordered
     */
    private TimsOrder(String name, int size, TimsProduct[] timsProducts) {
        this.name = name;
        this.size = size;
        this.timsProducts = timsProducts;
    }

    /**
     * @return object of TimsOrder
     */
    public static TimsOrder create() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Can I get your name? ");
        String name = sc.next();
        System.out.println("\nHi " + name + ",");

        String choice = "";
        String choices = "";
        String order = "";
        while (true) {
            System.out.print("\n=============================\n" +
                    "1) Donut \n" +
                    "2) Mug \n" +
                    "3) Coffee \n" +
                    "4) Tumbler \n" +
                    "=============================");
            if (choices.equals("")) {
                System.out.println("\nWhat do you like to have? \n");
            } else {
                System.out.println("\nWhat else do you like to have? \n");
            }
            System.out.print("Please enter the number (0 to quit): ");
            choice = sc.next();

            // Only includes a valid order
            if (choice.length() == 1 && "1234".contains(choice)) {
                choices += choice;

                if (!order.equals("")) {
                    order += ",";
                }

                if (choice.equals("1")) {
                    order += " Donut";
                } else if (choice.equals("2")) {
                    order += " Mug";
                } else if (choice.equals("3")) {
                    order += " Coffee";
                } else if (choice.equals("4")) {
                    order += " Tumbler";
                }
            }
            System.out.println("You ordered" + order + ".");

            if (choice.equals("0")) {
                break;
            }
        }

        int size = choices.length();
        TimsProduct[] timsProducts = new TimsProduct[size];
        for (int i = 0; i < size; i++) {
            if (choices.charAt(i) == '1') {
                timsProducts[i] = Donut.create();
            } else if (choices.charAt(i) == '2') {
                timsProducts[i] = Mug.create();
            } else if (choices.charAt(i) == '3') {
                timsProducts[i] = Coffee.create();
            } else if (choices.charAt(i) == '4') {
                timsProducts[i] = Tumbler.create();
            }
        }

        return new TimsOrder(name, size, timsProducts);
    }

    /**
     * @return total amount of price
     */
    public double getAmountDue() {
        double amountDue = 0.0;
        for (TimsProduct t : timsProducts) {
            amountDue += t.getRetailPrice();
        }
        return amountDue;
    }

    /**
     * @return values of instance variables
     */
    @Override
    public String toString() {
        String msg = "";
        for (TimsProduct t : timsProducts) {
            msg += t.toString();
        }
        return "\nOrder for: " + name + msg;
    }
}
