/**
 * This is a model class to create five dice. This also includes some logic
 * to calculate a score for a dice and produce some feedback to the user.
 *
 * @author Jonghwan Lee
 */
public class Dice {
    /** array variable for five dice **/
    private Die[] dieArray;
    /** sum of dice, score for pattern of dice **/
    private int diceSum, patternScore;

    /**
     * Constructor. Create 5 dice by making the class Die an array
     */
    public Dice() {
        dieArray = new Die[5];
        for (int i = 0; i < 5; i++) {
            dieArray[i] = new Die();
        }
    }

    /**
     * Change a die in the set of dice for the case of rerolling a die
     *
     * @param i index of the array
     */
    public void setDie(int i) {
        Die d = new Die();
        dieArray[i] = d;
    }

    /**
     * Sum up the numbers of dice
     */
    public void setDiceSum() {
        diceSum = 0;
        for (Die die : dieArray) {
            diceSum += die.getNum();
        }
    }

    /**
     * @return sum of the numbers of dice
     */
    public int getDiceSum() {
        return diceSum;
    }

    /**
     * Calculate a score for the patterns
     */
    public void setPatternScore() {
        // Sum of dice
        setDiceSum();
        // Number of the same dice
        int numSameDice = 0;
        for (int i = 0; i < 5; i++) {
            int count = 0;
            for (int j = 0; j < 5; j++) {
                if (dieArray[i].getNum() == dieArray[j].getNum()) {
                    count++;
                }
            }
            if (numSameDice < count) {
                numSameDice = count;
            }
        }
        // Prime number
        boolean isPrime = true;
        for (int i = 2; i < diceSum; i++) {
            if (diceSum % i == 0) {
                isPrime = false;
            }
        }
        // Pattern score
        patternScore = 0;
        if (numSameDice == 5) {
            patternScore = 100;
        } else if (isPrime) {
            patternScore = 50;
        } else if (numSameDice >= 3) {
            patternScore = 30;
        } else if (numSameDice == 1) {
            patternScore = 25;
        }
    }

    /**
     * @return score for the pattern
     */
    public int getPatternScore() {
        return patternScore;
    }

    /**
     * @param i index of the array of dice
     * @return file name of the image
     */
    public String getImgFile(int i) {
        return dieArray[i].getImgFile();
    }

    /**
     * @param fName user's first name
     * @return whether the name input is valid
     */
    public boolean isValidName(String fName) {
        int countInvalid = 0;
        for (int i = 0; i < fName.length(); i++) {
            char k = fName.toLowerCase().charAt(i);
            if (k < 'a' || k > 'z') {
                countInvalid++;
            }
        }
        return countInvalid == 0;
    }

    /**
     * @param score score for the pattern
     * @param sum   score for the sum
     * @return message of reason why the pattern score is calculated
     */
    public String patternMsg(int score, int sum) {
        String msg = "";
        if (score == 100) {
            msg = "all the same value (100 points)";
        } else if (score == 50) {
            msg = "sum of " + sum + " is a prime number (50 points)";
        } else if (score == 30) {
            msg = "3 same value (30 points)";
        } else if (score == 25) {
            msg = "all different values (25 points)";
        }
        return msg;
    }

    /**
     * @return instance variables
     */
    @Override
    public String toString() {
        String msg = "";
        for (int i = 0; i < dieArray.length; i++) {
            msg += dieArray[i];
            if (i == dieArray.length - 1) {
                msg += ".";
            } else {
                msg += ",";
            }
        }
        return msg;
    }
}
