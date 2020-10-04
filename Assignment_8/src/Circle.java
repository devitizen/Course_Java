import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This is a child class of the GeometricObject.
 * This is to draw circles.
 *
 * @author Jonghwan Lee
 */
public class Circle extends GeometricObject {

    // radius of circle
    private double radius;

    /**
     * Constructor
     * @param x           coordinate X
     * @param y           cooridnate Y
     * @param lineWidth   width of circle line
     * @param strokeColor color of circle line
     * @param fillColor   color of circle
     * @param radius      radius of circle
     */
    public Circle(double x, double y, double lineWidth, Color strokeColor, Color fillColor, double radius) {
        super(x, y, lineWidth, strokeColor, fillColor);
        this.radius = radius;
    }

    /**
     * Method to draw circles. This overrides the abstract method of the GeometricObject
     * @param gc GraphicsContext
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setFill(getFillColor());
        gc.setStroke(getStrokeColor());
        gc.fillOval(getX(), getY(), getRadius() * 2, getRadius() * 2);
        if(getLineWidth() > 0) {
            gc.strokeOval(getX(), getY(), getRadius() * 2, getRadius() * 2);
        }
    }

    /**
     * Getters and Setters
     **/
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * toString method
     **/
    @Override
    public String toString() {
        return super.toString() + "\nradius: " + radius;
    }

}
