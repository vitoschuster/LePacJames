package client.runners;

/**
 * Class that represents a ball (eatable)
 * @author L. Krpan V.Schuster
 * @version 18/4/2022
 */
import javafx.geometry.Point2D;
public class Ball extends Runner {
    private static final String IMG_PATH = "img/ball.png";

    /**
     * Ball constructor
     * 
     * @param pos point2D OBJECT
     */
    public Ball(Point2D pos) {
        super(IMG_PATH, pos);
        this.pos = pos;
    }

    @Override 
    public void update() {
        //This method is empty because the balls do not yet have to be updated (in later versions this might be of use);
    }
}