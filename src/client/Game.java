package client;

import client.runners.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.io.*;
import java.util.*;
import javafx.scene.media.*;

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
   public List<Runner> runners = new ArrayList<>();
   public List<List<Ball>> balls = new ArrayList<>();
   public List<Ghost> ghosts = new ArrayList<>();
   public HUD hud;
   private AnimationTimer timer;
   public Pacman p;
   public Pacman p2;
   public boolean isMoving = false;
   public boolean isCoop = false;
   private boolean ifCollision = false;
   private int id;

   private static final int GHOST_NUM = 4;
   private static final int GRID_WIDTH = 5;
   private static final int GRID_HEIGHT = 5;

   /* multiplayer constructor */
   public Game(Court court, Integer id, boolean isCoop, int numPlayers) {
      this.isCoop = isCoop;
      this.court = court;
      this.id = id;
      this.displayPlayers(numPlayers);
      this.displayGhost();
      this.displayEatables(balls);
      this.hud = new HUD(court.stage.getScene(), isCoop);
      this.getChildren().addAll(this.court, this.hud);
      this.start();
   }

   /* singleplayer constructor */
   public Game(Court court) {
      this.court = court;
      this.displayRunners(runners);
      this.displayEatables(balls);
      this.hud = new HUD(court.stage.getScene(), isCoop);
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

   public Pacman addPlayer2Controls(Pacman pacman) {
      this.court.stage.addEventHandler(KeyEvent.KEY_PRESSED, evt -> {
         switch (evt.getCode()) {
            case UP:
               pacman.xspeed = 0;
               pacman.yspeed = -3;
               pacman.angle = 270;
               pacman.setScaleY(1);
               break;

            case LEFT:
               pacman.yspeed = 0;
               pacman.xspeed = -3;
               pacman.angle = 180;
               pacman.setScaleY(-1);
               break;

            case DOWN:
               pacman.yspeed = 3;
               pacman.xspeed = 0;
               pacman.angle = 90;
               pacman.setScaleY(-1);
               break;

            case RIGHT:
               pacman.xspeed = 3;
               pacman.yspeed = 0;
               pacman.angle = 0;
               pacman.setScaleY(1);
               break;
         }
      });
      return pacman;
   }
   


   /* singleplayer */
   public void displayRunners(List<Runner> list) {
      this.runners.add(addPlayerControls(new Pacman(new Point2D(40, 40))));
      this.runners.add(addPlayer2Controls(new Pacman(new Point2D(40,60))));
      p = (Pacman) this.runners.get(0);
      p2 = (Pacman) this.runners.get(1);
      for (int i = 0; i < GHOST_NUM; i++)
         this.runners.add(new Ghost(this.court, i, this.court.randPos(32, 40)));

      court.getChildren().addAll(list);
   }

   /* multiplayer ghosts */
   public void displayGhost() {
      for (int i = 0; i < GHOST_NUM; i++) {
         this.ghosts.add(new Ghost(this.court, i, this.court.randPos(32, 40)));
         this.runners.add(ghosts.get(i));
      }
      this.court.getChildren().addAll(ghosts);
   }

   /* multiplayer players */
   public void displayPlayers(int numPlayers) {
      for (int i = 0; i < numPlayers; i++) {
         if (i == 0) {
            this.runners.add(addPlayerControls(new Pacman(new Point2D(40, 40))));
            p = (Pacman) this.runners.get(0);
         } else {
            this.runners.add(new Pacman(new Point2D(40, 40)));
            p2 = (Pacman) this.runners.get(1);
         }
      }
      this.court.getChildren().addAll(runners);
   }

   /* display balls */
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
                  
                     if (!ifCollision
                           && (p.checkCollisionWithGhost((Ghost) r) || p2.checkCollisionWithGhost((Ghost) r))) {
                        ifCollision = true;
                        restart(court.stage);
                     }
                 

                  if (id == 0 || !isCoop) {
                     court.handleCollision((Ghost) r);
                     r.update();
                  }
               } else if (!court.isCollisionMap(r.getTranslateX(), r.getTranslateY(), r.height, r.width, r.angle))
                  r.update();

            }
            ballCollision();
            gameWin();

         }

      };
      timer.start();
   }

   public void ballCollision() {
      for (List<Ball> ballList : balls) {
         for (Iterator<Ball> iter = ballList.iterator(); iter.hasNext();) {
            Ball ball = iter.next();
            
               if (p.checkCollisionWithBall(ball) || p2.checkCollisionWithBall(ball)) {
                  ball.setVisible(false);
               }

            

            if (!ball.isVisible()) {
               iter.remove();
               p.score++;
               hud.update(p.score, p.lives);
            }
         }
      }
   }

   public void gameWin() {
      if (!ifCollision && p.score == 25) {
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

   public void cleanup() {
      Platform.runLater(() -> {
         if (hud.lives - 1 == 0) {
            System.exit(0);
         } else {
            timer.stop();
            ghosts.clear();
            balls.clear();
            runners.clear();
            court.getChildren().clear();
            p.score = 0;
         }
      });
   }

   public void restart(Stage stage) {
      if (!isCoop) {
         cleanup();
         Game g = new Game(new Court(stage));
         stage.setScene(new Scene(g, 1120, 700));
         stage.show();
         p.lives = g.hud.lives - 1;
         g.hud.lives = p.lives;
         System.out.println(g.hud.lives + " " + p.lives);
         g.hud.update(0, g.hud.lives);
      } else {
         String path = "video/lose.mp4";
         Media media = new Media(new File(path).toURI().toString());
         MediaPlayer player = new MediaPlayer(media);
         MediaView mediaView = new MediaView(player);
         court.getChildren().add(mediaView);
         player.play();
         player.setOnEndOfMedia(() -> System.exit(0));
      }
   }

   void alertLater(AlertType type, String header, String message) {
      Alert a = new Alert(type, message);
      a.setHeaderText(header);
      a.showAndWait();
   }
} // end class Races