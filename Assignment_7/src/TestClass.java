/**
 * This is a class to test the assignment 7.
 *
 * @author Jonghwan Lee
 */
public class TestClass {

    /**
     * This is a main method.
     * @param args unused
     */
    public static void main(String[] args) {
        TimsOrder t = TimsOrder.create();
        System.out.println(t);
        System.out.printf("Total price: $%.2f\n", t.getAmountDue());
    }
}
