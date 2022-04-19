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
import java.util.concurrent.*;
import javafx.scene.control.Alert;

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
   private BorderPane root;

   // animation attributes
   private List<Image> images = new ArrayList<>();
   private List<Timeline> timelines = new ArrayList<>();
   private ArrayList<Ghost> ghosts = new ArrayList<>();
   private int counterAnim = 0;

   private static String[] args;

   // grid
   private List<List<Ball>> balls = new ArrayList<>();
   private int gridWidth = 5;
   private int gridHeight = 5;
   private int counterBall = 0;
   private int endBall = 0;

   // hud
   private TextField tfScore = new TextField("0");


   private static final String ICON_IMAGE = "pacman_small"; // file with icon for a racer
   private static final String BG_PROP = "bgProps.png";
   private static final String GHOST_IMAGE = "ghost";
   private static final String BALL = "ball.png";
   private static final int GHOST_NUM = 5;

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
      stage.setOnCloseRequest(evt -> System.exit(0));
      root = new BorderPane();

      doOpen();

      // Get image size from first element in arraylist
      iconWidth = (int) images.get(0).getWidth();
      iconHeight = (int) images.get(0).getHeight();

      // display the
      scene = new Scene(root, 1120, 700);

      // adding pacman icon
      racer = new PacmanRacer(this.scene);
      root.getChildren().add(racer);

      // adding ghosts
      for (int i = 1; i < GHOST_NUM; i++) {
         try {
            ghosts.add(new Ghost((GHOST_NUM - i) * 220 - 20, i * 100,
                  new ImageView(new Image(new FileInputStream(new File(GHOST_IMAGE + i + ".png"))))));
            ghosts.get(i - 1).doOpen(bgProps, new Image(new FileInputStream(new File(GHOST_IMAGE + i + ".png"))));
            root.getChildren().add(ghosts.get(i - 1));
         } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      PixelReader bgReaderForBall = bgProps.getPixelReader();
      try {
         for (int i = 0; i < gridHeight; i++) {
            balls.add(new ArrayList<>());
            for (int j = 0; j < gridWidth; j++) {
               int xBall = i * ThreadLocalRandom.current().nextInt(220, 261) + 50;
               int yBall = j * ThreadLocalRandom.current().nextInt(120, 160) + 50;
               if (!bgReaderForBall.getColor(xBall + 12, yBall + 12).equals(Color.RED)
                     && xBall < bgProps.getWidth() - 30 && yBall < bgProps.getHeight() - 30) {

                  balls.get(balls.size() - 1).add(new Ball(new Point2D(xBall, yBall),
                        new ImageView(new Image(new FileInputStream(new File(BALL)))),
                        new Image(new FileInputStream(new File(BALL)))));

                  endBall++;
                  root.getChildren().add(balls.get(balls.size() - 1).get(balls.get(i).size() - 1));
                  System.out.println("s");
               }
            }
         }

         //displaying the score

      } catch (Exception e) {
         e.printStackTrace();
      }

      createHUD();
      displayScore();
      root.getChildren().addAll(tfScore);
      root.setId("pane");

      scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
      stage.setScene(scene);
      stage.show();

      animationTimerCreate();
      animationTimerStart();

   }

   void createHUD() {
      int padding = 40;
      tfScore.resizeRelocate(scene.getWidth() - padding,0, 40, 25);
      tfScore.setEditable(false);
      tfScore.setFocusTraversable(false);
   }
   void displayScore() {
      Platform.runLater(() -> tfScore.setText(String.valueOf(counterBall))); 
   }

   void animationTimerStart() {
      // Use an animation to update the screen
      timer = new AnimationTimer() {
         public void handle(long now) {
            racer.update();
            for (int i = 0; i < ghosts.size(); i++) {
               ghosts.get(i).update();
            }
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
      private int x = 50; // x position of the racer
      private int y = 50; // x position of the racer
      private int raceROT = 0; // x rotation
      private char collionM = 'R';
      private int xw = 0; //
      private int yh = 0; //
      private static final int SPEED = 4;
      private static final int REFRESH_RATE = 1000 / 60;

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
         pacmanGroup.setTranslateX(x);
         pacmanGroup.setTranslateY(y);
         pacmanGroup.setRotate(raceROT);
         timelines.get(0).play(); // play the animation
         displayScore();
         /*
          * if (racePosX > 800)
          * racePosX = 1;
          * if (racePosY > 500)
          * racePosY = 1;
          */
         // ogranicenje kretanja s obzirom na pixel
         // full collion

         // offset of the picture - the center needs to be the cneter of

      } // end update()

      public void checkCollision() {
         // get pixel reader
         
         PixelReader pixelReader = bgProps.getPixelReader();
         xw = (int) images.get(0).getWidth() + x;
         yh = (int) images.get(0).getHeight() + y;

         // player collision
         // loop
         switch (collionM) {
            case 'd':
               if (pixelReader.getColor(xw, yh).equals(Color.RED)
                     || pixelReader.getColor(xw, y).equals(Color.RED))
                  x -= SPEED;
               break;
            case 's':
               if (pixelReader.getColor(x, yh).equals(Color.RED)
                     || pixelReader.getColor(xw, yh).equals(Color.RED))
                  y -= SPEED;
               break;
            case 'w':
               if (pixelReader.getColor(x, y).equals(Color.RED)
                     || pixelReader.getColor(xw, y).equals(Color.RED))
                  y += SPEED;
               break;
            case 'a':
               if (pixelReader.getColor(x, y).equals(Color.RED)
                     || pixelReader.getColor(x, yh).equals(Color.RED))
                  x += SPEED;
               break;
         }

         // player vs ghost collision
         for (int i = 0; i < ghosts.size(); i++) {
            if (x < (ghosts.get(i).getX() + ghosts.get(i).getW()) && xw > ghosts.get(i).getX()
                  && y < ghosts.get(i).getY() + ghosts.get(i).getH() && yh > ghosts.get(i).getY()) {
                     System.out.println("You lost");
                      //end game
                     System.exit(0);

            }
         }

         // player vs ball collison
         try {

            balls.forEach(ballList -> {
               ballList.forEach(ball -> {
                  if (ball.getX() <= this.xw &&
                        ball.getXW() >= this.x &&
                        ball.getY() <= this.yh &&
                        ball.getYH() >= this.y) {
                     ball.setVisible(false);
                  }
               });
            });

            for (List<Ball> ballList : balls) {
               for (Iterator<Ball> iter = ballList.iterator(); iter.hasNext();) {
                  Ball ball = iter.next();
                  if (!ball.isVisible()) {
                     iter.remove();
                     counterBall++;
                  }
               }
               
            }
            if (counterBall==endBall) {
               Thread.sleep(1000);
               System.out.println("You won");
                //end game
               System.exit(0);
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
         //cant use enhanced foreach for deleting
         //copy to another arraylist and remove all elements
         //subtract the two arrays or use Iterator
      }

      public void move(boolean isMoving, char movement) {

         if (isMoving && !goingForward) {
            timerTaskMover = new TimerTask() {
               @Override
               public void run() {
                  synchronized (timerMover) {
                     if (movement == 'w') {
                        y -= SPEED;
                        raceROT = 270;
                        pacmanGroup.setScaleY(1);
                        collionM = 'w';
                     } else if (movement == 'a') {
                        x -= SPEED;
                        raceROT = 180;
                        collionM = 'a';
                        pacmanGroup.setScaleY(-1);
                     } else if (movement == 's') {
                        y += SPEED;
                        raceROT = 90;
                        pacmanGroup.setScaleY(-1);
                        collionM = 's';
                     } else if (movement == 'd') {
                        x += SPEED;
                        raceROT = 0;
                        pacmanGroup.setScaleY(1);
                        collionM = 'd';
                     }
                  }
               }
            };
            timerMover.scheduleAtFixedRate(timerTaskMover, 0, REFRESH_RATE);
            goingForward = true;
         } else if (!isMoving && goingForward) {
            timerTaskMover.cancel();
            goingForward = false;
         }
      }
   } // end inner class Racer

} // end class Races