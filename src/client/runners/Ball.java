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
    private Point2D pos;
    private double startPos;
    private double direction;
    
    private ImageView ballView;
    private final double padding = 2;

    public Ball(Point2D pos) {
        super("");
        this.pos = pos;
        this.ballView = ballView;
        this.ballView.setTranslateX(this.pos.getX());
        this.ballView.setTranslateY(this.pos.getY());
    }
    
    public Point2D getPos() {
        return this.pos;
    }


    
    @Override
    public void update() {
        
        
    }

}