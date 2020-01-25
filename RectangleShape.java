import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
* Component that draws a rectangle based on mouse position
*
* Note that this program is written as the hint and is not a working program. It should be
* completed and then should be tested. You can write your own program from the scratch if
* it is easier for you.
*/

public class RectangleShape extends Shape {
    private int xCoord, yCoord;
    private final int HEIGHT = 20, WIDTH = 40;

    /**
     * Constructor to create a rectangle based on the mouse coordinates given from
     * the drawPanel
     */

    public RectangleShape(int xCoord, int yCoord, String colourName) {
        super(colourName);
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * Returns the shape's name
     */

    public String getShapeName() {
        return "Rectangle";
    }

    /**
     * Return the x-coordinate of the mouse position for the rectangle
     */

    public int getX() {
        return xCoord;
    }

    /**
     * Return the y-coordinate of the mouse position for the rectangle
     */

    public int getY() {
        return yCoord;
    }

    /**
     * Draw a rectangle
     */

    public void draw(Graphics2D g2) {
        int upperLeftX = getX();
        int upperLeftY = getY();
        g2.setColor(getColour());
        g2.fillRect(upperLeftX, upperLeftY, WIDTH, HEIGHT);
    }

}
