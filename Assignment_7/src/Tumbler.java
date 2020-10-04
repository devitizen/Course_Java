import javafx.scene.paint.Color;

import java.util.Scanner;

/**
 * This is a class to implement the super class of TimsProduct.
 *
 * @author Jonghwan Lee
 */
public class Tumbler extends TimsProduct {

    /** color of tumbler */
    private Color color;

    /**
     * Constructor
     * @param name  name of tumbler
     * @param color color of tumbler
     * @param cost  cost of tumbler
     * @param price price of tumbler
     */
    private Tumbler(String name, Color color, double cost, double price) {
        super(name, cost, price);
        this.color = color;
    }

    /**
     * @return object of Tumbler
     */
    public static Tumbler create() {

        Tumbler[] tumblers = new Tumbler[3];
        tumblers[0] = new Tumbler("Mohawk", Color.ORANGE, 6.99, 29.99);
        tumblers[1] = new Tumbler("Hamilton", Color.YELLOW, 5.99, 32.99);
        tumblers[2] = new Tumbler("Ontario", Color.BLUE, 4.99, 27.99);

        System.out.println("\nWhich tumbler would you like to get?");
        for (int i = 0; i < tumblers.length; i++) {
            System.out.println((i + 1) + ") " + tumblers[i].getName() + " - " + tumblers[i].getColor() + ", $"
                    + tumblers[i].getRetailPrice());
        }
        System.out.print("Please enter the number: ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt() - 1;
        while (index < 0 || index >= tumblers.length) {
            System.out.print("Please re-enter the correct number: ");
            index = sc.nextInt() - 1;
        }

        return tumblers[index];
    }

    /**
     * @return color of tumbler
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return values of instance variables
     */
    @Override
    public String toString() {
        return super.toString() + "\n\tType... Tumbler {color= " + color + "}";
    }
}