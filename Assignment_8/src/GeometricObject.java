import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This is a parent class for the Circle and the Rectangle.
 * This includes common variables and methods for them.
 *
 * @author Jonghwan Lee
 */
public abstract class GeometricObject {

    // coordinate of top-left corner of the shape, width of the shape line
    private double x, y, lineWidth;
    // colors of the line and the inside of the shape
    private Color strokeColor, fillColor;

    /**
     * Constructor
     * @param x           coordinate X
     * @param y           cooridnate Y
     * @param lineWidth   width of shape line
     * @param strokeColor color of shape line
     * @param fillColor   color of shape
     */
    public GeometricObject(double x, double y, double lineWidth, Color strokeColor, Color fillColor) {
        this.x = x;
        this.y = y;
        this.lineWidth = lineWidth;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }

    /**
     * Abstract Method to draw shapes. This is overridden by the method of the child classes
     * @param gc GraphicsContext
     */
    public abstract void draw(GraphicsContext gc);

    /**
     * Getters and Setters
     **/
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * toString Method
     **/
    @Override
    public String toString() {
        return "x: " + x + "\ny: " + y + "\nlineWidth: " + lineWidth
                + "\nstrokeColor" + strokeColor + "\nfillColor" + fillColor;
    }

}
