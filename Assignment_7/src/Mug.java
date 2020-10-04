import javafx.scene.paint.Color;

import java.util.Scanner;

/**
 * This is a class to implement the super class of TimsProduct.
 *
 * @author Jonghwan Lee
 */
public class Mug extends TimsProduct {

    /** color of mug */
    private Color color;

    /**
     * Constructor
     * @param name  name of mug
     * @param color color of mug
     * @param cost  cost of mug
     * @param price price of mug
     */
    private Mug(String name, Color color, double cost, double price) {
        super(name, cost, price);
        this.color = color;
    }

    /**
     * @return object of Mug
     */
    public static Mug create() {

        Mug[] mugs = new Mug[3];
        mugs[0] = new Mug("Official Tim's Mug", Color.WHITE, 1.99, 19.99);
        mugs[1] = new Mug("Maple", Color.RED, 1.99, 22.99);
        mugs[2] = new Mug("Beaver", Color.BLUE, 0.99, 15.99);

        System.out.println("\nWhich mug would you like to get?");
        for (int i = 0; i < mugs.length; i++) {
            System.out.println((i + 1) + ") " + mugs[i].getName() + " - " + mugs[i].getColor() + ", $"
                    + mugs[i].getRetailPrice());
        }

        System.out.print("Please enter the number: ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt() - 1;
        while (index < 0 || index >= mugs.length) {
            System.out.print("Please re-enter the correct number: ");
            index = sc.nextInt() - 1;
        }

        return mugs[index];
    }

    /**
     * @return color of mug
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return values of instance variables
     */
    @Override
    public String toString() {
        return super.toString() + "\n\tType... Mug {color= " + color + "}";
    }
}