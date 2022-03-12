import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.io.*;
import java.security.Key;
import java.util.*;

/**
 * PackmanGEOStarter with JavaFX and Thread
 */

public class Game2DSTARTER extends Application {
   // Window attributes
   private Stage stage;
   private Scene scene;
   private VBox root;

   private static String[] args;

   private static final String ICON_IMAGE = "pixil-frame-0.png"; // file with icon for a racer

   private int iconWidth; // width (in pixels) of the icon
   private int iconHeight; // height (in pixels) or the icon
   private PacmanRacer racer = null; // array of racers
   private Image carImage = null;

   private AnimationTimer timer; // timer to control animation

   // main program
   public static void main(String[] _args) {
      args = _args;
      launch(args);
   }

   // start() method, called via launch
   public void start(Stage _stage) {
      // stage seteup
      stage = _stage;
      stage.setTitle("Game2D Starter");
      stage.setOnCloseRequest(
            new EventHandler<WindowEvent>() {
               public void handle(WindowEvent evt) {
                  System.exit(0);
               }
            });

      // lukaJeGej
      // vitovitovito
      // root pane
      root = new VBox();

      // create an array of Racers (Panes) and start
      initializeScene();
      scene.setOnKeyPressed(racer);

   }

   // start the race
   public void initializeScene() {

      // Make an icon image to find its size
      try {
         carImage = new Image(new FileInputStream(ICON_IMAGE));
      } catch (Exception e) {
         System.out.println("Exception: " + e);
         System.exit(1);
      }

      // Get image size
      iconWidth = (int) carImage.getWidth();
      iconHeight = (int) carImage.getHeight();

      racer = new PacmanRacer();
      root.getChildren().add(racer);
      root.setId("pane");
      // display the window
      scene = new Scene(root, 800, 500);
      scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
      stage.setScene(scene);
      stage.show();

      System.out.println("Starting race...");

      // Use an animation to update the screen
      timer = new AnimationTimer() {
         public void handle(long now) {
            racer.update();
            // System.out.println("He");
         }
      };

      // TimerTask to delay start of race for 2 seconds
      TimerTask task = new TimerTask() {
         public void run() {
            timer.start();
         }
      };
      Timer startTimer = new Timer();
      long delay = 1000L;
      startTimer.schedule(task, delay);
   }

   /**
    * Racer creates the race lane (Pane) and the ability to
    * keep itself going (Runnable)
    */
   class PacmanRacer extends Pane implements EventHandler<KeyEvent> {
      private int racePosX = 0; // x position of the racer
      private int racePosY = 0; // x position of the racer
      private int raceROT = 0; // x position of the racer
      private ImageView aPicView; // a view of the icon ... used to display and move the image

      public PacmanRacer() {
         // Draw the icon for the racer
         aPicView = new ImageView(carImage);
         this.getChildren().add(aPicView);
      }

      /**
       * update() method keeps the thread (racer) alive and moving.
       */
      public void update() {

         aPicView.setTranslateX(racePosX);
         aPicView.setTranslateY(racePosY);
         aPicView.setRotate(raceROT);

         if (racePosX > 800)
            racePosX = 0;
         if (racePosY > 500)
            racePosY = 0;

      } // end update()

      @Override
      public void handle(KeyEvent evt) {
         KeyCode code = evt.getCode();
         switch (code) {
            case UP:
            case W:
               racePosY -= 10;
               raceROT = 270;

               break;

            case RIGHT:
            case D:
               racePosX += 10;
               raceROT = 0;
               break;

            case DOWN:
            case S:
               racePosY += 10;
               raceROT = 90;
               break;

            case LEFT:
            case A:
               racePosX -= 10;
               raceROT = 180;
               break;
            default:
               break;
         }

      }

   } // end inner class Racer

} // end class Races