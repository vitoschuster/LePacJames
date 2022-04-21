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

import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.io.*;
import java.security.*;
import javafx.util.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * LePacJames - Main class for Pacman Game
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 2203
 */

public class Game extends StackPane {
   // Window attributes

   private Court court;
   private List<Runner> runners = new ArrayList<>();
   private AnimationTimer timer;
   private Pacman p;
   private static final int GHOST_NUM = 5;

   public Game(Court court) {
      this.court = court;
      this.displayRunners(runners);
      this.getChildren().add(court);
      this.start();
   }

   /**
    * event handling
    * 
    */

   public Pacman addPlayerControls(Pacman pacman) {
      this.court.stage.addEventHandler(KeyEvent.KEY_PRESSED, evt -> {
         switch (evt.getCode()) {
            case W:
               pacman.xspeed = 0;
               pacman.yspeed = -3;
               pacman.angle = 270;
               pacman.setScaleY(1);
               break;

            case A:
               pacman.yspeed = 0;
               pacman.xspeed = -3;
               pacman.angle = 180;
               pacman.setScaleY(-1);
               break;

            case S:
               pacman.yspeed = 3;
               pacman.xspeed = 0;
               pacman.angle = 90;
               pacman.setScaleY(-1);
               break;

            case D:
               pacman.xspeed = 3;
               pacman.yspeed = 0;
               pacman.angle = 0;
               pacman.setScaleY(1);
               break;
         }
      });
      return pacman;
   }

   public void displayRunners(List<Runner> list) {
      this.runners.add(addPlayerControls(new Pacman(new Point2D(40, 40))));
      p = (Pacman)this.runners.get(0);
      for (int i = 0; i < 4; i++)
         this.runners.add(new Ghost(this.court, i, randPos()));
         
      court.getChildren().addAll(list);
   }

   public Point2D randPos() {
      return new Point2D(ThreadLocalRandom.current().nextDouble(100, this.court.image.getWidth() - 200),
            ThreadLocalRandom.current().nextDouble(100, this.court.image.getHeight() - 200));
   }

   // start() method
   public void start() {
      timer = new AnimationTimer() {
         @Override
         public void handle(long now) {
            for (Runner r : runners) {
               if (r instanceof Ghost) {
                  if (p.checkCollisionWithGhost((Ghost) r))
                     System.out.println("PLEASE WORK");
                  court.handleCollision((Ghost) r);
                  r.update();
               } else if (!court.isCollisionMap(r.getTranslateX(), r.getTranslateY(), r.height, r.width, r.angle)) 
                  r.update();

            }

            
         
            
         }
      };
      timer.start();
      // TimerTask task = new TimerTask() {
      // public void run() {
      // timer.start();
      // }
      // };
      // Timer startTimer = new Timer();
      // long delay = 1000L;
      // startTimer.schedule(task, delay);
   }
   // public void startGame(Stage stage) {

   // initializeScene(stage);
   // // // TODO - restart game call method

   // }

   // public void cleanup() {
   // // timer.stop();
   // // balls.clear();
   // // ghosts.clear();
   // // timelines.clear();
   // // images.clear();
   // // score = 0;
   // // endBall = 0;
   // // k = 0;
   // // counterAnim = 0;
   // }

   // public void restart(Stage stage) {
   // // cleanup();
   // // start(stage);
   // }

   void alertLater(AlertType type, String header, String message) {
      Alert a = new Alert(type, message);
      a.setHeaderText(header);
      a.showAndWait();
   }

   // start the race
   public void initializeScene(Stage stage) {

      // createGrid();
      createHUD();
      displayScore();

      // css
      // root.setId("pane");
      // tfScore.setId("score");
      // tfLives.setId("lives");
      // scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

      animationTimerCreate();
      animationTimerStart();

   }

   /*
    * void createGrid() {
    * // adding grid and checking (bad code this needs to change TODO);
    * PixelReader bgReaderForBall = bgProps.getPixelReader();
    * try {
    * for (int i = 0; i < gridHeight; i++) {
    * balls.add(new ArrayList<>());
    * for (int j = 0; j < gridWidth; j++) {
    * int xBall = i * ThreadLocalRandom.current().nextInt(220, 261) + 50;
    * int yBall = j * ThreadLocalRandom.current().nextInt(120, 160) + 50;
    * if (!bgReaderForBall.getColor(xBall + 12, yBall + 12).equals(Color.RED)
    * && xBall < bgProps.getWidth() - 30 && yBall < bgProps.getHeight() - 30) {
    * 
    * balls.get(balls.size() - 1).add(new Ball(new Point2D(xBall, yBall),
    * new ImageView(new Image(new FileInputStream(new File(BALL)))),
    * new Image(new FileInputStream(new File(BALL)))));
    * 
    * endBall++;
    * root.getChildren().add(balls.get(balls.size() - 1).get(balls.get(i).size() -
    * 1));
    * System.out.println("s");
    * }
    * }
    * }
    * // displaying the score
    * 
    * } catch (Exception e) {
    * e.printStackTrace();
    * }
    * }
    */
   void createHUD() {
      // int padding = 80;
      // tfScore.resizeRelocate(scene.getWidth() - padding, 0, 80, 25);
      // tfScore.setEditable(false);
      // tfScore.setFocusTraversable(false);

      // tfLives.resizeRelocate(scene.getWidth() - padding - 100, 0, 100, 25);
      // tfLives.setEditable(false);
      // tfLives.setFocusTraversable(false);

      // root.getChildren().addAll(tfLives, tfScore);
   }

   void displayScore() {
      // Platform.runLater(() -> tfScore.setText("Score: " + score));
   }

   void animationTimerStart() {
      // // Use an animation to update the screen
      /*
       * timer = new AnimationTimer() {
       * public void handle(long now) {
       * 
       * }
       * };
       */
      // for (int i = 0; i < ghosts.size(); i++) {
      // ghosts.get(i).update();
      // }
      // */
      // // System.out.println("He");
      // }
      // };
      // System.out.println("Starting race...");

   }

   void animationTimerCreate() {
      // // TimerTask to delay start of race for 2 seconds
      // TimerTask task = new TimerTask() {
      // public void run() {
      // timer.start();
      // }
      // };
      // Timer startTimer = new Timer();
      // long delay = 1000L;
      // startTimer.schedule(task, delay);
   }

} // end class Races