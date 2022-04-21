package client.runners;

import javafx.geometry.*;



public class Pacman extends Runner {

    private static final String IMG_PATH = "img/lepac.gif";


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

    public boolean checkCollisionWithGhost(Ghost g) {
        return this.pos.getX() < g.pos.getX() + g.width && this.pos.getX() + this.width > g.pos.getX()
            && this.pos.getY() < g.pos.getY() + g.height && this.pos.getY() + this.height > g.pos.getY();
    }   
    
    public boolean checkCollisionWithBall(Ball ball) {
        return ball.getBoundsInParent().intersects(this.getBoundsInParent());
    }

} // end inner class Racer