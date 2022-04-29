/**
 * Court - Class used for inserting images to court and checking rgb collision
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
package client;

import client.runners.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.geometry.*;
import java.io.*;
import java.util.concurrent.*;

public class Court extends Pane {
    public Stage stage;
    public Image image;
    public ImageView imageView;
    private PixelReader reader;

    private static final String PATH_BG = "img/map.png";
    private static final String PATH_BG_PROPS = "img/mapBg.png"; // bg for collision

    /**
     * Court constructor.
     * 
     * @param stage Stage
     */
    public Court(Stage stage) {
        this.stage = stage;

        try { // loading images (real and fake bg)
            this.image = new Image(new FileInputStream(PATH_BG_PROPS));
            this.imageView = new ImageView(new Image(new FileInputStream(PATH_BG)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.getChildren().add(this.imageView);
    }

    /**
     * Checks RGB collision for pacman
     * 
     * @param xpos   x position of pacman
     * @param ypos   y position of pacman
     * @param width  width of pacman
     * @param height height of pacman
     * @param deg    angle of pacman
     * @return true if collision, else false
     */
    public boolean isCollisionMap(double xpos, double ypos, double width, double height, double deg) {
        reader = this.image.getPixelReader();
        final int pad = 3;
        int x = (int) xpos;
        int y = (int) ypos;
        int xw = (int) (width + x);
        int yh = (int) (height + y);
        int xw2 = (int) (width/2 + x);
        int yh2 = (int) (height/2 + y);
        int angle = (int) deg;

        switch (angle) { // pacman
            case 0:
                if (reader.getColor(xw + pad, yh).equals(Color.RED)
                        || reader.getColor(xw + pad, y).equals(Color.RED) || reader.getColor(xw + pad, yh2).equals(Color.RED))
                    return true;
                break;
            case 90:
                if (reader.getColor(x, yh + pad).equals(Color.RED)
                        || reader.getColor(xw, yh + pad).equals(Color.RED) || reader.getColor(xw2, yh + pad).equals(Color.RED))
                    return true;
                break;
            case 270:
                if (reader.getColor(x, y - pad).equals(Color.RED)
                        || reader.getColor(xw, y - pad).equals(Color.RED) || reader.getColor(xw2, y - pad).equals(Color.RED))
                    return true;
                break;
            case 180:
                if (reader.getColor(x - pad, y).equals(Color.RED)
                        || reader.getColor(x - pad, yh).equals(Color.RED) || reader.getColor(x - pad, yh2).equals(Color.RED))
                    return true;
                break;
        }

        return false;
    }

    /**
     * Recursive method that finds random number in range of the map size and
     * generates random coordinates until they arent in collision with the map
     * @author V.Schuster - original 200iq 3 line method
     * @param width  width of object
     * @param height height of object
     * @return point2d object where there is no collision with other objects
     */
    public Point2D randPos(double width, double height) {
        int x = ThreadLocalRandom.current().nextInt(50, (int) this.image.getWidth() - 50);
        int y = ThreadLocalRandom.current().nextInt(50, (int) this.image.getHeight() - 50);
        return (this.isCollision(x, y, (int) width, (int) height)) ? randPos(width, height) : new Point2D(x, y);
    }

    /**
     * Returns if object from randPos method touches red color
     * 
     * @param x      x position of object
     * @param y      y position of object
     * @param width  width of object
     * @param height height of object
     * @return true if collision, else false
     */
    public boolean isCollision(int x, int y, int width, int height) {
        reader = this.image.getPixelReader();
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (reader.getColor(i, j).equals(Color.RED))
                    return true;
            }
        }
        return false;
    }

    /**
     * Checks if Ghosts collide with walls and if they collide direction changes
     * 
     * @author L.Krpan - ghost collision expert
     * @param g Ghost object
     */
    public void handleCollision(Ghost g) {
        reader = this.image.getPixelReader();
        int x = (int) g.getTranslateX();
        int y = (int) g.getTranslateY();
        int xw = (int) g.getImage().getWidth();
        int xh = (int) g.getImage().getHeight();
        int speed = Ghost.GHOST_SPEED;

        switch (g.moveGhost) {
            case 1:
                if (reader.getColor(x - speed, y + xh).equals(Color.RED)
                        || reader.getColor(x, y).equals(Color.RED) || (reader.getColor(x - speed, y + xh).equals(Color.GREEN)
                        || reader.getColor(x, y).equals(Color.GREEN))) {
                    g.moveGhost = 2;
                } else if (reader.getColor(x + xw, y + xh).equals(Color.RED)
                        || reader.getColor(x, (y + xh) + speed).equals(Color.RED) || reader.getColor(x + xw, y + xh).equals(Color.GREEN)
                        || reader.getColor(x, (y + xh) + speed).equals(Color.GREEN)) {
                    g.moveGhost = 3;
                }
                break;
            case 2:
                if (reader.getColor(x + xw, y).equals(Color.RED)
                        || reader.getColor(x + xw, (y + xh)).equals(Color.RED) || reader.getColor(x + xw, y).equals(Color.GREEN)
                        || reader.getColor(x + xw, (y + xh)).equals(Color.GREEN)) {
                    g.moveGhost = 1;
                } else if (reader.getColor(x + speed, (y + xh)).equals(Color.RED)
                        || reader.getColor(x + xw, (y + xh) + speed).equals(Color.RED) || (reader.getColor(x + speed, (y + xh)).equals(Color.GREEN)
                        || reader.getColor(x + xw, (y + xh) + speed).equals(Color.GREEN))) {
                    g.moveGhost = 4;
                }
                break;
            case 3:
                if (reader.getColor((x + xw), y).equals(Color.RED)
                        || reader.getColor(x, y - speed).equals(Color.RED) || (reader.getColor((x + xw), y).equals(Color.GREEN)
                        || reader.getColor(x, y - speed).equals(Color.GREEN))) {
                    g.moveGhost = 1;
                } else if (reader.getColor(x - speed, y).equals(Color.RED)
                        || reader.getColor(x, (y + xh)).equals(Color.RED) || reader.getColor(x - speed, y).equals(Color.GREEN)
                        || reader.getColor(x, (y + xh)).equals(Color.GREEN)) {
                    g.moveGhost = 4;
                }
                break;
            case 4:
                if (reader.getColor(x, y).equals(Color.RED)
                        || reader.getColor((x + xw) + speed, y).equals(Color.RED) || reader.getColor(x, y).equals(Color.GREEN)
                        || reader.getColor((x + xw) + speed, y).equals(Color.GREEN)) {
                    g.moveGhost = 3;
                } else if (reader.getColor((x + xw), y - speed).equals(Color.RED)
                        || reader.getColor((x + xw), (y + xh)).equals(Color.RED) || reader.getColor((x + xw), y - speed).equals(Color.GREEN)
                        || reader.getColor((x + xw), (y + xh)).equals(Color.GREEN)) {
                    g.moveGhost = 2;
                }
                break;
        }
       
    }
}
