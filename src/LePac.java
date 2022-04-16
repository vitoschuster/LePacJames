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

/**
 * LePacJames - Main class for Pacman Game
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 2203
 */

public class LePac extends Application {
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
   private static final String BG_PROP = "bgProps.png";

   private int iconWidth; // width (in pixels) of the icon
   private int iconHeight; // height (in pixels) or the icon
   private PacmanRacer racer = null; // array of racers
   private Image bgProps = null;

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
         bgProps = new Image(new FileInputStream(new File(BG_PROP)));
         // adding fake bg

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

      // display the
      scene = new Scene(root, 800, 500);
      racer = new PacmanRacer(this.scene);
      root.getChildren().add(racer);
      root.setId("pane");
      scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
      stage.setScene(scene);
      stage.show();

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
   class PacmanRacer extends Pane {
      private Scene scene;
      private int racePosX = 0; // x position of the racer
      private int racePosY = 0; // x position of the racer
      private int raceROT = 0; // x rotation
      private char collionM = 'R';
      private int curX = 0; //
      private int curY = 0; //
      private int speed = 0;

      private ArrayList<ImageView> imageViews = new ArrayList<>(); // arrayList of icon views - used to cycle the
                                                                   // animation
      private Group pacmanGroup;
      private TimerTask timerTaskMover;
      private Timer timerMover = new Timer();
      private boolean goingForward = false;

      // load image and get pixel position

      public PacmanRacer(Scene scene) {

         this.scene = scene;

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
                  (ActionEvent event) -> pacmanGroup.getChildren().setAll(imageViews.get(finalI))));
         }
         counterAnim++;
         this.getChildren().add(pacmanGroup); // adding the background to the root
         checkMovement();
      }

      public void checkMovement() {

         // handle key events
         this.scene.setOnKeyPressed(evt -> {
            KeyCode code = evt.getCode();
            switch (code) {
               case UP:
               case W:
                  this.move(true, 'w');
                  break;
               case LEFT:
               case A:
                  this.move(true, 'a');
                  break;

               case DOWN:
               case S:
                  this.move(true, 's');
                  break;

               case RIGHT:
               case D:
                  this.move(true, 'd');
                  break;
               default:
                  break;
            }
         });

         this.scene.setOnKeyReleased(evt -> {
            KeyCode code = evt.getCode();
            switch (code) {
               case UP:
               case W:
                  this.move(false, 'w');
                  break;
               case LEFT:
               case A:
                  this.move(false, 'a');
                  break;

               case DOWN:
               case S:
                  this.move(false, 's');
                  break;

               case RIGHT:
               case D:
                  this.move(false, 'd');
                  break;
               default:
                  break;
            }
         });
      }

      /**
       * update() method keeps the thread (racer) alive and moving.
       */
      public void update() {
         checkCollision();
         pacmanGroup.setTranslateX(racePosX);
         pacmanGroup.setTranslateY(racePosY);
         pacmanGroup.setRotate(raceROT);

         timelines.get(0).play(); // play the animation
         /*
         if (racePosX > 800)
            racePosX = 1;
         if (racePosY > 500)
            racePosY = 1;
         */
         // ogranicenje kretanja s obzirom na pixel
         // full collion

         // offset of the picture - the center needs to be the cneter of

      } // end update()

      public void checkCollision() {
         if (racePosX >= 0 && racePosX <= 800 && racePosY >= 0 && racePosY <= 500) {
            try {
               // get pixel reader
               PixelReader pixelReader = bgProps.getPixelReader();
               curX = (int) images.get(0).getWidth() + racePosX;
               curY = (int) images.get(0).getHeight() + racePosY;

               // loop

               switch (collionM) {
                  case 'd':
                     if (pixelReader.getColor(curX, curY).equals(Color.RED)
                           || pixelReader.getColor(curX, racePosY).equals(Color.RED)) {
                              racePosX-=speed;
                     } else {
                        speed = 4;
                     }
                     break;
                  case 's':
                     if (pixelReader.getColor(racePosX, curY).equals(Color.RED) || pixelReader.getColor(curX, curY).equals(Color.RED)  ) {
                        racePosY-=speed;
                     } else {
                        speed = 4;
                     }
                     break;
                  case 'w':
                     if (pixelReader.getColor(racePosX, racePosY).equals(Color.RED)
                           || pixelReader.getColor(curX, racePosY).equals(Color.RED)) {
                        racePosY+=speed;
                       
                     } else {
                        speed = 4;
                     }
                     break;
                  case 'a':
                     if (pixelReader.getColor(racePosX, racePosY).equals(Color.RED)
                           || pixelReader.getColor(racePosX, curY).equals(Color.RED)) {
                              racePosX+=speed;
                        
                     } else {
                        speed = 4;
                     }
                     break;

               }
            } catch (IndexOutOfBoundsException e) {
               e.printStackTrace();
            }
         }
      }

      public void move(boolean isMoving, char movement) {

         if (isMoving && !goingForward) {
            timerTaskMover = new TimerTask() {
               @Override
               public void run() {
                  synchronized (timerMover) {
                     if (movement == 'w') {
                        racePosY -= speed;
                        raceROT = 270;
                        pacmanGroup.setScaleY(1);
                        collionM = 'w';
                     } else if (movement == 'a') {
                        racePosX -= speed;
                        raceROT = 180;
                        collionM = 'a';
                        pacmanGroup.setScaleY(-1);
                     } else if (movement == 's') {
                        racePosY += speed;
                        raceROT = 90;
                        pacmanGroup.setScaleY(-1);
                        collionM = 's';
                     } else if (movement == 'd') {
                        racePosX += speed;
                        raceROT = 0;
                        pacmanGroup.setScaleY(1);
                        collionM = 'd';
                     }
                  }
               }
            };
            timerMover.scheduleAtFixedRate(timerTaskMover, 0, 1000 / 60);
            goingForward = true;
         } else if (!isMoving && goingForward) {
            timerTaskMover.cancel();
            goingForward = false;
         }
      }

   } // end inner class Racer

} // end class Races