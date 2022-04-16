
/**
 * Ghost - Class that represents a ghost following a pacman
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.transform.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.io.*;
import java.security.*;
import javafx.util.Duration;
import java.util.*;

public class Ghost extends Pane {

    private static final int SPEED = 3;
    private int posX;
    private int posY;
    private ImageView ghostView;
    private int moveGhost;
    public Ghost(int posX, int posY, ImageView ghostView) {
        //saving data
        this.posX = posX;
        this.posY = posY;
        this.ghostView = ghostView;
        System.out.println(posX+" "+posY);
        moveGhost=(int)Math.floor(Math.random()*(4-1+1)+1);
        ///setting spawn location and adding to root
        this.ghostView.setTranslateX(this.posX);
        this.ghostView.setTranslateY(this.posY);
        this.getChildren().add(this.ghostView);
    }

    
    public void update() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                switch(moveGhost){
                    case 1:
                        posX--;
                        ghostView.setTranslateX(posX);
                        break;
                    case 2:
                        posX++;
                        ghostView.setTranslateX(posX);
                        break;
                    case 3:
                        posY--;
                        ghostView.setTranslateY(posY);
                        break;
                    case 4:
                        posY++;
                        ghostView.setTranslateY(posY);
                        break;
                }
            }
        });
    }
    public void checkCollision(){
        
    }


}
