/**
 * Ghost - Class that represents a ghost following a pacman
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
package client.runners;

import javafx.geometry.*;

public class Pacman extends Runner {

    private static final String IMG_PATH = "img/lebronn.gif";
    public static int imgCount = 0;

    /**
     * Pacman constructor.
     * 
     * @param pos point2D object
     */
    public Pacman(Point2D pos) {
        super(IMG_PATH, pos);
    }

    /**
     * update Pacman method.
     */
    @Override
    public void update() {
        this.pos = new Point2D(this.getTranslateX(), this.getTranslateY());
        this.setTranslateX(this.getTranslateX() + xspeed);
        this.setTranslateY(this.getTranslateY() + yspeed);
        this.setRotate(angle);

    }


    /**
     * Checks the collision of pacman and ghost.
     * 
     * @param g Ghost object
     * @return true if collision, else false
     */
    public boolean checkCollisionWithGhost(Ghost g) {
        return this.pos.getX() < g.getTranslateX() + g.width && this.pos.getX() + this.width > g.getTranslateX()
                && this.pos.getY() < g.getTranslateY() + g.height && this.pos.getY() + this.height > g.getTranslateY();
    }

    /**
     * Checks the collision of pacman and ball.
     * 
     * @param b Ball object
     * @return true if collision, else false
     */
    public boolean checkCollisionWithBall(Ball b) {
        return this.pos.getX() < b.getTranslateX() + b.width && this.pos.getX() + this.width > b.getTranslateX()
                && this.pos.getY() < b.getTranslateY() + b.height && this.pos.getY() + this.height > b.getTranslateY();
    }

}