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

import javafx.scene.media.*;
import javafx.application.Application;

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
   private List<List<Ball>> balls = new ArrayList<>();
   private HUD hud;
   private AnimationTimer timer;
   private Pacman p;
   private boolean isCoop = false;
   private boolean ifCollision = false;
   private static final int GHOST_NUM = 4;
   private static final int GRID_WIDTH = 5;
   private static final int GRID_HEIGHT = 5;

   /* multiplayer constructor */
   public Game(Court court, boolean isCoop, int numPlayers) {
      this.court = court;
      this.displayPlayers(numPlayers);
      this.hud = new HUD(court.stage.getScene());
      this.getChildren().addAll(this.court);
      this.start();
   }

   /* singleplayer constructor */
   public Game(Court court) {
      this.court = court;
      this.displayRunners(runners);
      this.displayEatables(balls);
      this.hud = new HUD(court.stage.getScene());
      this.getChildren().addAll(this.court, this.hud);
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

   public void displayPlayers(int numPlayers) {
      for (int i = 0; i < numPlayers; i++) {
         if (i == 0) {
            this.runners.add(addPlayerControls(new Pacman(new Point2D(40, 40))));
            p = (Pacman) this.runners.get(0);
         }
         this.runners.add(new Pacman(new Point2D(40, 80)));
      }
      this.court.getChildren().addAll(runners);
   }

   public void displayRunners(List<Runner> list) {
      this.runners.add(addPlayerControls(new Pacman(new Point2D(40, 40))));
      p = (Pacman) this.runners.get(0);
      for (int i = 0; i < GHOST_NUM; i++)
         this.runners.add(new Ghost(this.court, i, this.court.randPos(32, 40)));

      court.getChildren().addAll(list);
   }

   public void displayEatables(List<List<Ball>> balls) {
      for (int i = 0; i < GRID_WIDTH; i++) {
         balls.add(new ArrayList<>());
         for (int j = 0; j < GRID_HEIGHT; j++)
            balls.get(i).add(new Ball(this.court.randPos(25, 25)));
         court.getChildren().addAll(balls.get(i));
      }

   }

   // start() method
   public void start() {
      timer = new AnimationTimer() {
         @Override
         public void handle(long now) {
            for (Runner r : runners) {
               if (r instanceof Ghost) {
                  if (!ifCollision) {
                     if (p.checkCollisionWithGhost((Ghost) r)) {
                        ifCollision = true;
                        p.lives--;
                        hud.update(p.score, p.lives);
                        restart(court.stage);

                        // System.out.println("PLEASE WORK");

                        // String path = "video/lose.mp4";
                        // Media media = new Media(new File(path).toURI().toString());
                        // MediaPlayer player = new MediaPlayer(media);
                        // MediaView mediaView = new MediaView(player);
                        // court.getChildren().add(mediaView);
                        // player.play();
                        // player.setOnEndOfMedia(() -> System.exit(0));
                     }
                  }
                  court.handleCollision((Ghost) r);
                  r.update();
               } else if (!court.isCollisionMap(r.getTranslateX(), r.getTranslateY(), r.height, r.width, r.angle))
                  r.update();

            }
            for (List<Ball> ballList : balls) {
               for (Iterator<Ball> iter = ballList.iterator(); iter.hasNext();) {
                  Ball ball = iter.next();
                  if (p.checkCollisionWithBall(ball))
                     ball.setVisible(false);

                  if (!ball.isVisible()) {
                     iter.remove();
                     p.score++;
                     hud.update(p.score, p.lives);
                     System.out.println(p.score);
                  }
               }
            }
            if (!ifCollision) {
               if (p.score == (GRID_HEIGHT * GRID_WIDTH)) {
                  System.out.println("All eaten");
                  ifCollision = true;
                  String path = "video/win.mp4";
                  Media media = new Media(new File(path).toURI().toString());
                  MediaPlayer player = new MediaPlayer(media);
                  MediaView mediaView = new MediaView(player);
                  court.getChildren().add(mediaView);
                  player.play();
                  player.setOnEndOfMedia(() -> System.exit(0));
               }
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

   public void cleanup() {

      Platform.runLater(() -> {
         if (hud.lives - 1 == 0) {
            System.exit(0);
         }
         timer.stop();
         balls.clear();
         runners.clear();
         court.getChildren().clear();
         p.score = 0;
      });
   }

   public void restart(Stage stage) {
      cleanup();
      Game g = new Game(new Court(stage));
      stage.setScene(new Scene(g, 1120, 700));
      stage.show();
      g.hud.lives = p.lives--;
      g.hud.update(0, g.hud.lives);
   }

   void alertLater(AlertType type, String header, String message) {
      Alert a = new Alert(type, message);
      a.setHeaderText(header);
      a.showAndWait();
   }
   // public void startGame(Stage stage) {

   // initializeScene(stage);
   // // // TODO - restart game call method

   // }

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

} // end class Races