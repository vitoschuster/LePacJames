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
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.io.*;
import java.security.*;
import javafx.util.Duration;
import java.util.*;
/**
 * LePacJames - Main class for Pacman Game
 * @author V.Schuster
 * @author L.Krpan
 * @version 1203
 */

public class Game2DSTARTER extends Application {
   // Window attributes
   private Stage stage;
   private Scene scene;
   private VBox root;

   // animation attributes
   private ArrayList<Image> images = new ArrayList<>();
   private ArrayList<Timeline> timelines = new ArrayList<>();
   private int counterAnim = 0;

   private static String[] args;

   private static final String ICON_IMAGE = "pacman_small"; // file with icon for a racer

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
   public void start(Stage stage) {
      startGame(stage);
   }

   public void startGame(Stage stage) {
      initializeScene(stage);
      // TODO - restart game call method

   }

   public void cleanup() {
      
   }

   public void restart(Stage stage) {
      // cleanup();
      start(stage);
   }

   /**
    * Trying to open the images for animation
    */
   void doOpen() {
      try {

         // adding pictures to arraylist
         for (int i = 1; i < 7; i++) {
            images.add(new Image(new FileInputStream(new File(ICON_IMAGE + i + ".png"))));
         }

      } catch (Exception e) {
         System.out.println("Exception " + e);
      }
   }

   // start the race
   public void initializeScene(Stage stage) {
      this.stage = stage;
      stage.setTitle("Game2D Starter");
      stage.setOnCloseRequest(
            new EventHandler<WindowEvent>() {
               public void handle(WindowEvent evt) {
                  System.exit(0);
               }
            });
      root = new VBox(8);

      doOpen();

      // Get image size from first element in arraylist
      iconWidth = (int) images.get(0).getWidth();
      iconHeight = (int) images.get(0).getHeight();

      racer = new PacmanRacer();
      root.getChildren().add(racer);
      root.setId("pane");
      // display the window
      scene = new Scene(root, 800, 500);
      scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
      stage.setScene(scene);
      stage.show();

      scene.setOnKeyPressed(racer);

      animationTimerCreate();
      animationTimerStart();
   }

   void animationTimerStart() {
      // Use an animation to update the screen
      timer = new AnimationTimer() {
         public void handle(long now) {
            racer.update();
            // System.out.println("He");
         }
      };
      System.out.println("Starting race...");

   }

   void animationTimerCreate() {
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
      private ArrayList<ImageView> imageViews = new ArrayList<>(); // arrayList of icon views - used to cycle the
                                                                   // animation
      private Group pacmanGroup;

      //load image and get pixel position

      public PacmanRacer() {

         // Draw the icon - add the cycle animation frames
         for (int i = 0; i < images.size(); i++) {
            imageViews.add(new ImageView(images.get(i)));
         }

         for (int i = 4; i >= 0; i--) {
            imageViews.add(new ImageView(images.get(i)));
         }

         // setting the first frame in the timeline (to group)
         pacmanGroup = new Group(imageViews.get(0));

         timelines.add(new Timeline()); // creating timeline
         timelines.get(counterAnim).setCycleCount(javafx.animation.Animation.INDEFINITE); // setting cycle to infinite

         for (int i = 0; i < imageViews.size(); i++) {
            final int finalI = i;
            timelines.get(counterAnim).getKeyFrames().add(new KeyFrame(
                     Duration.millis(50.0 * i),
                    (ActionEvent event) -> {
                        pacmanGroup.getChildren().setAll(imageViews.get(finalI));
                    }));
        }
        counterAnim++;

        this.getChildren().add(pacmanGroup);

      }

      /**
       * update() method keeps the thread (racer) alive and moving.
       */
      public void update() {

         pacmanGroup.setTranslateX(racePosX);
         pacmanGroup.setTranslateY(racePosY);
         pacmanGroup.setRotate(raceROT);

        
         
         timelines.get(0).play(); //play the animation

         if (racePosX > 800)
            racePosX = 0;
         if (racePosY > 500)
            racePosY = 0;

         //ogranicenje kretanja s obzirom na pixel
         //full collion
         //offset of the picture - the center needs to be the cneter of

      } // end update()

      @Override
      public void handle(KeyEvent evt) {
         KeyCode code = evt.getCode();
         switch (code) {
            case UP:
            case W:
               this.goUp();
               break;
            case LEFT:
            case A:
               this.goLeft();
               break;

            case DOWN:
            case S:
               this.goDown();
               break;

            case RIGHT:
            case D:
               this.goRight();
               break;
            default:
               break;
         }

      }

      public void goUp() {
         racePosY -= 10;
         // g
         raceROT = 270;
         this.pacmanGroup.setScaleY(1);
      }

      public void goLeft() {
         racePosX -= 10;
         raceROT = 180;
         this.pacmanGroup.setScaleY(-1);

      }

      public void goDown() {
         racePosY += 10;
         raceROT = 90;
         this.pacmanGroup.setScaleY(-1);

      }

      public void goRight() {
         racePosX += 10;
         raceROT = 0;
         this.pacmanGroup.setScaleY(1);

      }

   } // end inner class Racer

} // end class Races