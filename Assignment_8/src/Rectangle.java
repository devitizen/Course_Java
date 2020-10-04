import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This is a child class of the GeometricObject.
 * This is to draw rectangles.
 *
 * @author Jonghwan Lee
 */
public class Rectangle extends GeometricObject {

    // width and height of rectangle
    private double width, height;

    /**
     * Constructor
     * @param x           coordinate X
     * @param y           cooridnate Y
     * @param lineWidth   width of rectangle line
     * @param strokeColor color of rectangle line
     * @param fillColor   color of rectangle
     * @param width       width of rectangle
     * @param height      height of rectangle
     */
    public Rectangle(double x, double y, double lineWidth, Color strokeColor, Color fillColor,
                     double width, double height) {
        super(x, y, lineWidth, strokeColor, fillColor);
        this.width = width;
        this.height = height;
    }

    /**
     * Method to draw rectangles. This overrides the abstract method of the GeometricObject
     * @param gc GraphicsContext
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setFill(getFillColor());
        gc.setStroke(getStrokeColor());
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
        if(getLineWidth() > 0) {
            gc.strokeRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    /**
     * Getters and Setters
     **/
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * toString method
     **/
    @Override
    public String toString() {
        return super.toString() + "\nwidth: " + width + "\nheight: " + height;
    }

}
