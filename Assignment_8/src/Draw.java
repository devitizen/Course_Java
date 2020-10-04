import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * This is a main class to draw geometric objects for Assignment 8
 *
 * @author Jonghwan Lee
 */
public class Draw extends Application {

    // values captured from the corresponding menu
    private double x, y, width, height, radius, lineWidth;
    private Color strokeColor, fillColor;
    // coordinate of pointer
    private double pntX, pntY;
    // array index of selected object
    private int sIndex;
    // whether or not the Help shown and an exception caught
    private boolean isHelpOn, isError;
    // object array to store the shapes created
    private ArrayList<GeometricObject> goal = new ArrayList<>();
    // instance of Circle
    private Circle c;
    // instance of Rectangle
    private Rectangle r;

    // fx components
    GraphicsContext gc, gc2;
    Label lb60, lb70;
    RadioButton rb11, rb12;
    TextField tf21, tf22, tf31, tf32, tf33, tf41;
    ColorPicker cp42, cp43;

    /**
     * Handler for the mouse move
     * @param me MouseEvent
     */
    private void mouseMovedHandler(MouseEvent me) {
        pointer(me.getX(), me.getY());
    }

    /**
     * Handler for the mouse drag
     * @param me MouseEvent
     */
    private void mouseDraggedHandler(MouseEvent me) {
        if (me.getButton() == MouseButton.PRIMARY) {
            pointer(me.getX(), me.getY());

            double newWidth = Math.abs(me.getX() - pntX);
            double newHeight = Math.abs(me.getY() - pntY);
            double newX = Math.min(me.getX(), pntX);
            double newY = Math.min(me.getY(), pntY);

            drawIt();
            gc.setFill(Color.rgb(135, 206, 235, 0.4));
            gc.fillRect(newX, newY, newWidth, newHeight);
        }
    }

    /**
     * Handler for the mouse press
     * @param me MouseEvent
     */
    private void mousePressedHandler(MouseEvent me) {
        closeHelp();
        drawIt();
        if (me.getButton() == MouseButton.PRIMARY) {
            pntX = me.getX();
            pntY = me.getY();
            tf21.setText(String.valueOf(pntX));
            tf22.setText(String.valueOf(pntY));
        }
    }

    /**
     * Handler for the mouse release
     * @param me MouseEvent
     */
    private void mouseReleasedHandler(MouseEvent me) {
        if (me.getButton() == MouseButton.PRIMARY) {
            // calculate width, height, radius before setting x, y
            double w = Math.abs(me.getX() - pntX);
            double h = Math.abs(me.getY() - pntY);
            double r = Math.min(w, h) / 2;
            tf31.setText(String.valueOf(r));
            tf32.setText(String.valueOf(w));
            tf33.setText(String.valueOf(h));

            // set x, y
            pntX = Math.min(pntX, me.getX());
            pntY = Math.min(pntY, me.getY());
            tf21.setText(String.valueOf(pntX));
            tf22.setText(String.valueOf(pntY));
        }
    }

    /**
     * Handler for the mouse click
     * @param me MouseEvent
     */
    private void mouseClickedHandler(MouseEvent me) {
        if (me.getButton() == MouseButton.SECONDARY) {
            selectedIndex(goal, me.getX(), me.getY());

            // if an object is selected, then its values are located into the menu
            if (sIndex != -1) {
                tf21.setText(String.valueOf(goal.get(sIndex).getX()));
                tf22.setText(String.valueOf(goal.get(sIndex).getY()));
                tf41.setText(String.valueOf(goal.get(sIndex).getLineWidth()));
                cp42.setValue(goal.get(sIndex).getStrokeColor());
                cp43.setValue(goal.get(sIndex).getFillColor());

                if (goal.get(sIndex) instanceof Circle) {
                    rb11.setSelected(true);
                    double tRadius = ((Circle) goal.get(sIndex)).getRadius();
                    tf31.setText(String.valueOf(tRadius));
                    tf32.setText(String.valueOf(tRadius * 2));
                    tf33.setText(String.valueOf(tRadius * 2));
                } else if (goal.get(sIndex) instanceof Rectangle) {
                    rb12.setSelected(true);
                    double tWidth = ((Rectangle) goal.get(sIndex)).getWidth();
                    double tHeight = ((Rectangle) goal.get(sIndex)).getHeight();
                    tf31.setText(String.valueOf(Math.min(tWidth, tHeight) / 2));
                    tf32.setText(String.valueOf(tWidth));
                    tf33.setText(String.valueOf(tHeight));
                }
            } else {
                rb11.setSelected(true);
                tf21.setText("0.0");
                tf22.setText("0.0");
                tf31.setText("0.0");
                tf32.setText("0.0");
                tf33.setText("0.0");
                tf41.setText("1.0");
                cp42.setValue(Color.BLUE);
                cp43.setValue(Color.YELLOW);
            }

            drawIt();
        }
    }

    /**
     * Handler for the Create button
     * @param e ActionEvent
     */
    private void createHandler(ActionEvent e) {
        closeHelp();
        readValue();
        // if no exception is caught, add object to array
        if (!isError) {
            if (rb11.isSelected()) {
                c = new Circle(x, y, lineWidth, strokeColor, fillColor, radius);
                if(radius > 0) {
                    goal.add(c);
                }
            } else if (rb12.isSelected()) {
                r = new Rectangle(x, y, lineWidth, strokeColor, fillColor, width, height);
                if(width > 0 && height > 0) {
                    goal.add(r);
                }
            }

            drawIt();
        }
    }

    /**
     * Handler for the Modify button
     * @param e ActionEvent
     */
    private void modifyHandler(ActionEvent e) {
        closeHelp();
        // if an object is selected
        if (goal.size() > 0 && sIndex > -1) {
            readValue();
            // if no exception is caught
            if (!isError) {
                goal.remove(sIndex);

                if (rb11.isSelected()) {
                    c = new Circle(x, y, lineWidth, strokeColor, fillColor, radius);
                    if(radius > 0) {
                        goal.add(sIndex, c);
                    }
                } else if (rb12.isSelected()) {
                    r = new Rectangle(x, y, lineWidth, strokeColor, fillColor, width, height);
                    if(width > 0 && height > 0) {
                        goal.add(sIndex, r);
                    }
                }

                drawIt();
            }
        }
    }

    /**
     * Handler for the Delete button
     * @param e ActionEvent
     */
    private void deleteHandler(ActionEvent e) {
        closeHelp();
        // if an object is selected
        if (goal.size() > 0 && sIndex > -1) {
            goal.remove(sIndex);
            drawIt();
        }
    }

    /**
     * Handler for the Help button
     * @param e ActionEvent
     */
    private void helpHandler(ActionEvent e) {
        if (isHelpOn) {
            closeHelp();
            drawIt();
        } else {
            openHelp();
        }
    }



    /**
     * Method to clear the canvas and draw shapes with current values
     */
    public void drawIt() {
        // clear canvas
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        // draw shapes according to the array
        for (GeometricObject go : goal) {
            go.draw(gc);
        }
    }

    /**
     * Method to display the content of the Help
     */
    public void openHelp() {
        lb70.setVisible(true);
        lb70.setText("To create," +
                "\n1) Select a shape and set its design on the menu" +
                "\n2) Drag on the canvas for the location and the size," +
                "\n    or type in proper figures " +
                "\n3) Click the Create" +
                "\n" +
                "\nTo modify," +
                "\n1) Select the shape to be modified on the canvas" +
                "\n    by right-clicking it" +
                "\n2) Change the settings in Shape, Design, Location," +
                "\n    and Size as you want, or you can drag on " +
                "\n    the canvas to change the location and size" +
                "\n3) Click the Modify" +
                "\n" +
                "\nTo Delete," +
                "\n1) Select the shape to be deleted on the canvas" +
                "\n    by right-clicking it" +
                "\n2) Click the Delete" +
                "\n" +
                "\nTo exit Help. Click the Help again."
        );
        gc.setFill(Color.rgb(77, 77, 77));
        gc.fillRoundRect(495, 5, 300, 360, 10, 10);
        isHelpOn = true;
    }

    /**
     * Method to close the content of the Help
     */
    public void closeHelp() {
        lb70.setVisible(false);
        drawIt();
        isHelpOn = false;
    }

    /**
     * Method to find out the array index of the selected shape
     * @param g array of geometric objects
     * @param x pointed coordinate X
     * @param y pointed coordinate Y
     */
    public void selectedIndex(ArrayList<GeometricObject> g, double x, double y) {
        sIndex = -1;
        // find the index of object that has the range including the given coordinate
        for (int i = 0; i < g.size(); i++) {
            double gX = g.get(i).getX();
            double gY = g.get(i).getY();

            if (g.get(i) instanceof Circle) {
                double gRadius = ((Circle) g.get(i)).getRadius();
                if (Math.pow(gX + gRadius - x, 2) + Math.pow(gY + gRadius - y, 2) <= Math.pow(gRadius, 2)) {
                    sIndex = i;
                }
            } else if (g.get(i) instanceof Rectangle) {
                double gWidth = ((Rectangle) g.get(i)).getWidth();
                double gHeight = ((Rectangle) g.get(i)).getHeight();
                if ((gX <= x) && (x <= gX + gWidth) && (gY <= y) && (y <= gY + gHeight)) {
                    sIndex = i;
                }
            }
        }
    }

    /**
     * Method to display the pointed coordinate X and Y
     * @param x pointed coordinate X
     * @param y pointed coordinate Y
     */
    public void pointer(double x, double y) {
        lb60.setText("Pointer at (" + x + ", " + y + ")");
    }

    /**
     * Method to capture the values located in the menu
     */
    public void readValue() {
        String msg = "";
        isError = false;
        try {
            x = Double.parseDouble(tf21.getText());
            y = Double.parseDouble(tf22.getText());
        } catch (NumberFormatException e) {
            msg = "Invalid Input!\nThe Location should be numbers!";
        } catch (Exception e) {
            msg = "Caught an exception!";
        }

        try {
            radius = Double.parseDouble(tf31.getText());
            width = Double.parseDouble(tf32.getText());
            height = Double.parseDouble(tf33.getText());
        } catch (NumberFormatException e) {
            msg = "Invalid Input!\nThe Size should be numbers!";
        } catch (Exception e) {
            msg = "Caught an exception!";
        }

        try {
            lineWidth = Double.parseDouble(tf41.getText());
        } catch (NumberFormatException e) {
            msg = "Invalid Input!\nThe Line Width should be numbers!";
        } catch (Exception e) {
            msg = "Caught an exception!";
        }

        if (!msg.equals("")) {
            new Alert(Alert.AlertType.WARNING, msg).showAndWait();
            isError = true;
        }

        strokeColor = cp42.getValue();
        fillColor = cp43.getValue();
    }

    /**
     * Start Method
     * @param stage FX stage to draw on
     * @throws Exception throw exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 750);
        Canvas canvas2 = new Canvas(800, 150);
        Canvas canvas1 = new Canvas(800, 600);
        stage.setScene(scene);
        stage.setTitle("Drawing Shapes in 800x600");
        gc2 = canvas2.getGraphicsContext2D();
        gc = canvas1.getGraphicsContext2D();
        canvas1.relocate(0, 150);

        /* ***** panel ***** */
        // background
        gc2.setFill(Color.LIGHTGRAY);
        gc2.fillRect(0, 0, 800, 150);

        // divider
        gc2.setStroke(Color.BLACK);
        gc2.setLineWidth(0.2);
        gc2.strokeLine(140, 10, 140, 140);
        gc2.strokeLine(340, 10, 340, 140);
        gc2.strokeLine(490, 10, 490, 140);
        gc2.strokeLine(640, 10, 640, 140);

        // menu titles, pointer, help content
        Label lb10 = new Label("Shape >");
        Label lb20 = new Label("Location >");
        Label lb30 = new Label("Size >");
        Label lb40 = new Label("Design >");
        Label lb50 = new Label("");
        lb60 = new Label("");
        lb70 = new Label("");

        lb10.relocate(10, 10);
        lb20.relocate(350, 10);
        lb30.relocate(500, 10);
        lb40.relocate(150, 10);
        lb50.relocate(650, 10);
        lb60.relocate(10, 160);
        lb70.relocate(510, 165);

        lb10.setStyle("-fx-font-weight: bold");
        lb20.setStyle("-fx-font-weight: bold");
        lb30.setStyle("-fx-font-weight: bold");
        lb40.setStyle("-fx-font-weight: bold");
        lb70.setStyle("-fx-text-fill: white");

        // add components for panel
        Group panel = new Group(lb10, lb20, lb30, lb40, lb50, lb60, lb70);


        /* ***** menu ***** */
        // Shape
        rb11 = new RadioButton("Circle");
        rb12 = new RadioButton("Rectangle");
        ToggleGroup rb = new ToggleGroup();
        rb11.setToggleGroup(rb);
        rb12.setToggleGroup(rb);
        rb11.setSelected(true);

        rb11.relocate(10, 40);
        rb12.relocate(10, 70);
        rb11.setSelected(true);

        // Location
        Label lb21 = new Label("Coord X");
        Label lb22 = new Label("Coord Y");
        tf21 = new TextField("0.0");
        tf22 = new TextField("0.0");

        lb21.relocate(350, 40);
        lb22.relocate(350, 70);
        tf21.relocate(410, 40);
        tf22.relocate(410, 70);
        tf21.setPrefWidth(60);
        tf22.setPrefWidth(60);

        // Size
        Label lb31 = new Label("Radius");
        Label lb32 = new Label("Width");
        Label lb33 = new Label("Height");
        tf31 = new TextField("0.0");
        tf32 = new TextField("0.0");
        tf33 = new TextField("0.0");

        lb31.relocate(500, 40);
        lb32.relocate(500, 70);
        lb33.relocate(500, 100);
        tf31.relocate(560, 40);
        tf32.relocate(560, 70);
        tf33.relocate(560, 100);
        tf31.setPrefWidth(60);
        tf32.setPrefWidth(60);
        tf33.setPrefWidth(60);

        // Design
        Label lb41 = new Label("Line Width");
        Label lb42 = new Label("Line Color");
        Label lb43 = new Label("Fill Color");
        tf41 = new TextField("1.0");
        cp42 = new ColorPicker(Color.BLUE);
        cp43 = new ColorPicker(Color.YELLOW);

        lb41.relocate(150, 40);
        lb42.relocate(150, 70);
        lb43.relocate(150, 100);
        tf41.relocate(220, 40);
        cp42.relocate(220, 70);
        cp43.relocate(220, 100);
        tf41.setPrefWidth(100);
        cp42.setPrefWidth(100);
        cp43.setPrefWidth(100);

        // Buttons
        Button bt51 = new Button("Create");
        Button bt52 = new Button("Modify");
        Button bt53 = new Button("Delete");
        Button bt54 = new Button("Help");

        bt51.relocate(650, 10);
        bt52.relocate(725, 10);
        bt53.relocate(650, 80);
        bt54.relocate(725, 80);
        bt51.setPrefWidth(65);
        bt52.setPrefWidth(65);
        bt53.setPrefWidth(65);
        bt54.setPrefWidth(65);
        bt51.setPrefHeight(60);
        bt52.setPrefHeight(60);
        bt53.setPrefHeight(60);
        bt54.setPrefHeight(60);

        bt51.setStyle("-fx-font-weight: bold");
        bt52.setStyle("-fx-font-weight: bold");
        bt53.setStyle("-fx-font-weight: bold");
        bt54.setStyle("-fx-font-weight: bold");

        // add components for menu
        Group menu = new Group(rb11, rb12, lb21, lb22, tf21, tf22, lb31, lb32, lb33, tf31, tf32, tf33,
                lb41, lb42, lb43, tf41, cp42, cp43, bt51, bt52, bt53, bt54);


        /* **** add all components ***** */
        root.getChildren().addAll(canvas1, canvas2, panel, menu);


        /* ***** event handlers ***** */
        // mouse handlers
        canvas1.addEventHandler(MouseEvent.MOUSE_MOVED, this::mouseMovedHandler);
        canvas1.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::mouseDraggedHandler);
        canvas1.addEventHandler(MouseEvent.MOUSE_PRESSED, this::mousePressedHandler);
        canvas1.addEventHandler(MouseEvent.MOUSE_RELEASED, this::mouseReleasedHandler);
        canvas1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::mouseClickedHandler);
        // button handlers
        bt51.setOnAction(this::createHandler);
        bt52.setOnAction(this::modifyHandler);
        bt53.setOnAction(this::deleteHandler);
        bt54.setOnAction(this::helpHandler);

        /* ***** show ***** */
        stage.show();
    }

    /**
     * This is a main method.
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }

}
