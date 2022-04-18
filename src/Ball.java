
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

public class Ball extends Pane {
    private Point2D pos;
    private ImageView ballView;

    public Ball(Point2D pos, ImageView ballView) {
        this.pos = pos;
        this.ballView = ballView;
        this.ballView.setTranslateX(this.pos.getX());
        this.ballView.setTranslateY(this.pos.getY());
        this.getChildren().add(ballView);
    }
    
    public double getX() {
        return this.pos.getX();
    }

    public double getXW() {
        return this.getX() + this.getW();
    }

    public double getYH() {
        return this.getY() + this.getH();
    }

    public double getY() {
        return this.pos.getY();
    }
    public double getW() {
        return this.ballView.getFitWidth();
    }

    public double getH() {
        return  this.ballView.getFitHeight();
    }

}