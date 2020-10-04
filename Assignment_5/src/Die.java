/**
 * This is a model class to create a die. This randomly creates the number of the die and
 * create a file name for the corresponding image.
 *
 * @author Jonghwan Lee
 */
public class Die {
    /** number of die **/
    private int num;
    /** file name of image **/
    private String imgFile;

    /**
     * Constructor. Set the number of die and the file name of the image
     */
    public Die() {
        num = (int) (Math.random() * 6 + 1);
        imgFile = num + ".jpg";
    }

    /**
     * @return the number of die
     */
    public int getNum() {
        return num;
    }

    /**
     * @return the file name of the image
     */
    public String getImgFile() {
        return imgFile;
    }

    /**
     * @return the number of die
     */
    @Override
    public String toString() {
        return " " + num;
    }
}
