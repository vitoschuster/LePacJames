package client.runners;

/**
 * Class that represents a ball (eatable)
 * @author L. Krpan V.Schuster
 * @version 18/4/2022
 */
import javafx.application.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;

public class Ball extends Runner {
    private double startPos;
    private double direction;

    private final double padding = 2;

    private static final String IMG_PATH = "img/ball.png";

    public Ball(Point2D pos) {
        super("",new Point2D(1,1));
        this.pos = pos;
    }

    @Override
    public void update() {
        
        
    }

}