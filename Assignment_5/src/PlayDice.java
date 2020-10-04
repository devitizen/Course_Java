import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This is a view class to play a dice game. The users can type in their first name and
 * choose "Yes" or "No" to get a good set of dice to score as much as possible.
 *
 * @author Jonghwan Lee
 */
public class PlayDice extends Application {
    /** score for the turn, score for the play, number of the turn, number of the phase
     * number of not rerolling dice, number of rerolling dice **/
    int turnScore, totalScore, turnNum, phaseNum, noReroll, rollCount;
    /** code to indicate which process is asked **/
    char sCode;
    /** instance variable from the class Dice **/
    Dice dice = new Dice();

    // TODO: Instance Variables for View Components and Model
    Label msg1, msg2, msg3, msg4, msg5;
    TextField name;
    Button enter, start, btnY, btnN;
    GraphicsContext gc;

    // TODO: Private Event Handlers and Helper Methods
    /**
     * Handler method for the button Start
     *
     * @param e
     */
    private void startHandler(ActionEvent e) {
        clear();
        startTurn();
        startPhase();
        scoreComp(dice.getPatternScore(), dice.getDiceSum());
        enter.setVisible(false);
        name.setVisible(false);
        btnY.setVisible(true);
        btnN.setVisible(true);
    }

    /**
     * Handler method for the button Enter
     *
     * @param e
     */
    private void enterHandler(ActionEvent e) {
        /* Valid name */
        if (dice.isValidName(name.getText())) {
            msg5.setText("Thank you,  " + name.getText() + "! \nYou start off with 0 points, let’s play!");
            name.setVisible(false);
            enter.setVisible(false);
            start.setVisible(true);
            /* Invalid name */
        } else {
            msg5.setText("I'm sorry, " + name.getText() + "!\nThe name is only allowed to contain letters without space."
                    + "\nPlease re-enter your name.");
        }
    }

    /**
     * Handler method for the button Yes
     *
     * @param e
     */
    private void yesHandler(ActionEvent e) {
        /* Replaying game? */
        if (sCode == 'r') {
            clear();
            msg5.setText("Welcome to my dice game!  Please enter your first name.");
            enter.setVisible(true);
            name.setVisible(true);
            startImg();
            /* New turn? */
        } else if (sCode == 'n') {
            startTurn();
            startPhase();
            scoreComp(dice.getPatternScore(), dice.getDiceSum());
            msg3.setText("");
            msg5.setText("");
            /* Not rerolling a die */
        } else if (rollCount == 0) {
            endTurn();
            /* Rerolling dice */
        } else if (rollCount < 5) {
            dice.setDie(rollCount - 1);
            reroll();
            /* Rerolling the last die */
        } else if (rollCount == 5) {
            dice.setDie(rollCount - 1);
            startPhase();
        }
    }

    /**
     * Handler method for the button No
     *
     * @param e
     */
    private void noHandler(ActionEvent e) {
        /* Replaying game? */
        if (sCode == 'r') {
            clear();
            startImg();
            msg5.setText("Have a good day! Bye!");
            /* New turn? */
        } else if (sCode == 'n') {
            clear();
            msg5.setText("Would you like to play a new game? or Select \"No\" to quit the game.");
            btnY.setVisible(true);
            btnN.setVisible(true);
            startImg();
            sCode = 'r';
            /* Prime number score */
        } else if (sCode == 'p') {
            scoreComp(0, dice.getDiceSum());
            /* Sum score */
        } else if (sCode == 's') {
            if (rollCount == 0) {
                reroll();
            } else if (rollCount < 5) {
                reroll();
                noReroll++;
            } else if (rollCount == 5) {
                if (noReroll == 4) {
                    rollCount = 0;
                    noReroll = 0;
                    msg3.setText("You didn’t reroll any dice or score any points, please try again.");
                    reroll();
                } else {
                    startPhase();
                }
            }
        }
    }

    /**
     * Method to initialize variables and components
     */
    public void clear() {
        turnNum = 0;
        phaseNum = 0;
        totalScore = 0;
        msg1.setText("");
        msg2.setText("");
        msg3.setText("");
        msg4.setText("");
        msg5.setText("");
        enter.setVisible(false);
        name.setVisible(false);
        start.setVisible(false);
        btnY.setVisible(false);
        btnN.setVisible(false);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 600, 600);
    }

    /**
     * Method for a new turn to increase the turn number and update the status
     */
    public void startTurn() {
        turnNum++;
        status();
        sCode = 'x';        // set default code
        dice = new Dice();
    }

    /**
     * Method for ending a turn to display some messages
     */
    public void endTurn() {
        totalScore += turnScore;
        status();
        sCode = 'n';        // set code for new turn
        msg4.setText("Your score is now " + totalScore + " points. Taking points ended your turn." +
                "\nEnd of turn " + turnNum + ".");
        phaseNum = 0;
        if (turnNum < 10) {
            msg5.setText("Would you like to go to the next turn?");
        } else {
            msg5.setText("This is the last turn. " +
                    "\nWould you like to play a new game? or Select \"No\" to quit the game.");
            sCode = 'r';    // set code for replaying game
        }
    }

    /**
     * Method for a new phase to increase the phase number and display some messages
     */
    public void startPhase() {
        phaseNum++;
        status();
        rollCount = 0;
        noReroll = 0;
        if (phaseNum == 1) {
            msg2.setText("You rolled 5 dice. Their value are " + dice);
        } else {
            msg2.setText("Now, you rerolled some dice and the new values are " + dice + "\n");
            msg3.setText("");
            msg4.setText("");
        }
        diceImg(dice);
        dice.setDiceSum();
        dice.setPatternScore();
        scoreComp(dice.getPatternScore(), dice.getDiceSum());
    }

    /**
     * Method for comparing the pattern score with the sum score and showing some messages
     *
     * @param patternScore score of the pattern
     * @param diceSum      score of the sum
     */
    public void scoreComp(int patternScore, int diceSum) {
        if (phaseNum < 3) {
            if (patternScore > 0) {
                msg4.setText("Would you like to score the pattern points for \n"
                        + dice.patternMsg(patternScore, diceSum) + "?");
                turnScore = patternScore;
                sCode = 'p';    // set code for prime num score
            } else {
                msg4.setText("Would you like to score the sum of the dice (" + diceSum + " points)? ");
                turnScore = diceSum;
                sCode = 's';    // set code for sum score
            }
        } else {
            if (dice.getPatternScore() > dice.getDiceSum()) {
                msg3.setText("You are out of reroll tries." + "\nYou score the pattern points for "
                        + dice.patternMsg(dice.getPatternScore(), dice.getDiceSum()) + ".");
                turnScore = patternScore;
                sCode = 'n';
            } else {
                msg3.setText("You are out of reroll tries. " + "\nYou score the sum of the dice ("
                        + dice.getDiceSum() + " points.)");
                turnScore = diceSum;
                sCode = 'n';
            }
            endTurn();
        }
    }

    /**
     * Method to ask the user to reroll a die
     */
    public void reroll() {
        rollCount++;
        msg4.setText("Would you like to reroll die " + rollCount + "?");
    }

    /**
     * Method to set the status bar
     */
    public void status() {
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 20, 600, 40);
        msg1.setText("Turn: " + turnNum + "\t>>" + "\tPhase: " + phaseNum
                + "\t\t\t\t\t\t\tTotal Score: " + totalScore);
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRoundRect(20, 230, 560, 200, 20, 20);
    }

    /**
     * Method to set the path of the dice images
     *
     * @param dice Dice object
     */
    public void diceImg(Dice dice) {
        if (sCode != 'r') {
            for (int i = 0; i < 5; i++) {
                String imgPath = dice.getImgFile(i);
                Image img = new Image(imgPath);
                gc.drawImage(img, 80 + 80 * i, 150);
            }
        }
    }

    /**
     * Method to set the path of the starting image
     */
    public void startImg() {
        Image img = new Image("Playing Dice_ss.jpg");
        gc.drawImage(img, 0, 0);
    }

    /**
     * Start method
     *
     * @param stage FX stage to draw on
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(600, 550);                   // Set canvas Size in Pixels
        stage.setTitle("My Dice Game! - Programming in Java, Winter 2020");  // Set window title
        stage.setScene(scene);
        gc = canvas.getGraphicsContext2D();

        // YOUR CODE STARTS HERE
        // 1. Create the model
        // 2. Create the GUI components
        msg1 = new Label();
        msg2 = new Label();
        msg3 = new Label();
        msg4 = new Label();
        msg5 = new Label("Welcome to my dice game!  Please enter your first name.");
        name = new TextField("Your Name");
        enter = new Button("Enter");
        start = new Button("Start!");
        btnY = new Button("Yes");
        btnN = new Button("No");
        start.setVisible(false);
        btnY.setVisible(false);
        btnN.setVisible(false);
        startImg();

        // 3. Add components to the root
        root.getChildren().addAll(canvas, msg1, msg2, msg3, msg4, msg5, name, enter, start, btnY, btnN);

        // 4. Configure the components (colors, fonts, size, location)
        root.setStyle("-fx-font-size: 15px");
        msg1.relocate(50, 30);
        msg1.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
        msg2.relocate(50, 90);
        msg3.relocate(50, 240);
        msg4.relocate(50, 300);
        msg5.relocate(50, 360);
        name.relocate(150, 440);
        name.setPrefWidth(160);
        enter.relocate(320, 440);
        enter.setPrefWidth(80);
        start.relocate(260, 440);
        start.setPrefWidth(80);
        btnY.relocate(200, 460);
        btnY.setPrefWidth(80);
        btnN.relocate(320, 460);
        btnN.setPrefWidth(80);

        // 5. Add Event Handlers and do final setup
        enter.setOnAction(this::enterHandler);
        start.setOnAction(this::startHandler);
        btnY.setOnAction(this::yesHandler);
        btnN.setOnAction(this::noHandler);

        // YOUR CODE STOPS HERE
        stage.show();
    }

    /**
     * The actual main method that launches the app.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
