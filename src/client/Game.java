package client;
import client.runners.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.*;
import javafx.scene.transform.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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

/**
 * LePacJames - Main class for Pacman Game
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 2203
 */


public class Game extends StackPane {
   // Window attributes
   private Stage stage;
   private Scene scene;
   private BorderPane root;
   private Court court;
   private static String[] args;
   
   // animation attributes
   
   private int counterAnim = 0;

   // grid
   
   


   // hud
   private TextField tfScore = new TextField("Score: 0");
   private TextField tfLives = new TextField("Lives: 4" );
   private int k = 0;
   

   private static final String PATH_IMG = "../img/";
   private static final String ICON_IMAGE = PATH_IMG + "pacman_small"; // file with icon for a racer
   private static final String BG_PROP = PATH_IMG + "bgProps.png";
   private static final String GHOST_IMAGE = PATH_IMG + "ghost";
   private static final String BALL = PATH_IMG + "ghostball.png";
   private static final int GHOST_NUM = 5;
   private int ghostSpeed = 1;

   private int iconWidth; // width (in pixels) of the icon
   private int iconHeight; // height (in pixels) or the icon
   private Pacman racer = null; // array of racers
   private Image bgProps = null;

   private AnimationTimer timer; // timer to control animation

   public Game(Court court) {
      this.court = court;
      start(stage);
   }

   /**
    * event handling
    * 
    */



   // start() method
   public void start(Stage stage) {
      startGame(stage);
   }

   public void startGame(Stage stage) {
      initializeScene(stage);
      // TODO - restart game call method

   }

   public void cleanup() {
      // timer.stop();
      // balls.clear();
      // ghosts.clear();
      // timelines.clear();
      // images.clear();
      // score = 0;
      // endBall = 0;
      // k = 0;
      // counterAnim = 0;
   }

   public void restart(Stage stage) {
      cleanup();
      start(stage);
   }


   /**
    * Trying to open the images for animation
    */
   // void doOpenImages() {
   //    try {
   //       // adding pictures to arraylist
   //       for (int i = 1; i < 7; i++) {
   //          images.add(new Image(new FileInputStream(new File(ICON_IMAGE + i + ".png"))));
   //       }
        
   //       // adding fake bg

   //    } catch (Exception e) {
   //       System.out.println("Exception " + e);
   //    }
   // }


   void alertLater(AlertType type, String header, String message) {
      Alert a = new Alert(type, message);
      a.setHeaderText(header);
      a.showAndWait();
   }


   // start the race
   public void initializeScene(Stage stage) {
      this.stage = stage;
      stage.setTitle("LePac James");
      stage.setOnCloseRequest(evt -> System.exit(0));
      root = new BorderPane();

      doOpenImages();

      // Get image size from first element in arraylist
      iconWidth = (int) images.get(0).getWidth();
      iconHeight = (int) images.get(0).getHeight();

      // display the
      scene = new Scene(root, 1120, 700);

      // adding pacman icon
      racer = new Pacman(this.scene);
      root.getChildren().add(racer);

      // adding ghosts
      for (int i = 1; i < GHOST_NUM; i++) {
         try {
            ghosts.add(new Ghost((GHOST_NUM - i) * 220 - 20, i * 100,
                  new ImageView(new Image(new FileInputStream(new File(GHOST_IMAGE + i + ".png"))))));
            ghosts.get(i - 1).doOpen(bgProps, new Image(new FileInputStream(new File(GHOST_IMAGE + i + ".png"))));
            root.getChildren().add(ghosts.get(i - 1));
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         }
      }

      //change ghost speed every level
      ghostSpeed++;
      tfLives.setText("Lives: " + (5 - ghostSpeed));
      ghosts.forEach(ghost -> ghost.setSpeed(ghostSpeed, ghostSpeed));
      if (ghostSpeed > 4) {
         alertLater(AlertType.ERROR, "Game Over", "You lost");
         System.exit(0);
      }

      

      

      createGrid();
      createHUD();
      displayScore();

      //css
      // root.setId("pane");
      // tfScore.setId("score");
      // tfLives.setId("lives");
      // scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
      stage.setScene(scene);
      stage.show();

      animationTimerCreate();
      animationTimerStart();

   }

   void createGrid() {
      //adding grid and checking (bad code this needs to change TODO);
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
   }
   

   void createHUD() {
      int padding = 80;
      tfScore.resizeRelocate(scene.getWidth() - padding, 0, 80, 25);
      tfScore.setEditable(false);
      tfScore.setFocusTraversable(false);

      tfLives.resizeRelocate(scene.getWidth() - padding - 100, 0, 100, 25);
      tfLives.setEditable(false);
      tfLives.setFocusTraversable(false);

      root.getChildren().addAll(tfLives, tfScore);
   }
   
   
   void displayScore() {
      Platform.runLater(() -> tfScore.setText("Score: " + score));
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

  

} // end class Races