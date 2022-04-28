/**
 * Runner - Class that represents all moving objects
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
package client.runners;

import javafx.scene.image.*;
import javafx.geometry.*;
import java.io.*;

public abstract class Runner extends ImageView implements Serializable {

    private static final long serialVersionUID = 1L;
    public Point2D pos = new Point2D(0, 0);
    public Image image;
    public double xspeed = 0;
    public double yspeed = 0;
    public double angle = 0;
    public int score = 0;
    public int lives = 2;
    public int height;
    public int width;

    protected Runner() {
    }
    /**
     * Runner constructor.
     * 
     * @param imagePath location of image
     * @param pos point2D object
     */
    protected Runner(String imagePath, Point2D pos) {
        this.setImage(this.loadImage(imagePath));
        this.setTranslateX(pos.getX());
        this.setTranslateY(pos.getY());
        this.height = (int) this.getImage().getHeight();
        this.width = (int) this.getImage().getWidth();
    }
/**
     * Loads images of the runner with FileInputStream
     * 
     * @param path location of image
     */
    public Image loadImage(String path) {

        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

    /**
     * Method that is called in the animation timer
     * Code that needs to be constantly updated goes here
     */
    public abstract void update();


}
