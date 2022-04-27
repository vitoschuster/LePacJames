/**
 * Ghost - Class that represents a ghost following a pacman
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
package client.runners;

import java.io.*;

import javafx.application.Platform;
import javafx.geometry.*;

public class Pacman extends Runner implements Serializable {

    private static final String IMG_PATH = "img/lepac.gif";
    private static final long serialVersionUID = 1L;
    private double lastX;
    private double lastY;


    public Pacman(Point2D pos) {
        super(IMG_PATH, pos);
    }

    @Override
    public void update() {
        this.pos = new Point2D(this.getTranslateX(), this.getTranslateY());
        this.setTranslateX(this.getTranslateX() + xspeed);
        this.setTranslateY(this.getTranslateY() + yspeed);
        this.setRotate(angle);

    }

    @Override
    public boolean isMoving(double lastX, double lastY) {
        return this.getTranslateX() != lastX && this.getTranslateY() != lastY;
    }


    public boolean checkCollisionWithGhost(Ghost g) {
        return this.pos.getX() < g.getTranslateX() + g.width && this.pos.getX() + this.width > g.getTranslateX()
                && this.pos.getY() < g.getTranslateY() + g.height && this.pos.getY() + this.height > g.getTranslateY();
    }

    public boolean checkCollisionWithBall(Ball b) {
        return this.pos.getX() < b.getTranslateX() + b.width && this.pos.getX() + this.width >b.getTranslateX()
                && this.pos.getY() < b.getTranslateY() + b.height && this.pos.getY() + this.height > b.getTranslateY();
    }


}