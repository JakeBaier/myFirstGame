import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import components.Ground;
import components.Plane;
import components.Obstacles;

class GamePanel extends JPanel implements KeyListener, Runnable {

  public static int WIDTH;
  public static int HEIGHT;
  private Thread animator;

  private boolean running = false;
  private boolean gameOver = false;

  Ground ground;
  Plane plane;
  Obstacles obstacles;

  private int score;

  public int getScore() {
    return score;
  }

  public GamePanel() {
    WIDTH = UserInterface.WIDTH;
    HEIGHT = UserInterface.HEIGHT;

   ground = new Ground(HEIGHT);
    plane = new Plane();
    obstacles = new Obstacles((int)(WIDTH * 1.5));

    score = 0;

    setSize(WIDTH, HEIGHT);
    setVisible(true);
  }

  public void paint(Graphics g) {
    super.paint(g);
    g.setFont(new Font("Courier New", Font.BOLD, 25));
    g.drawString(Integer.toString(score), getWidth()/2 - 5, 100);
   ground.create(g);
    plane.create(g);
    obstacles.create(g);
  }

  public void run() {
    running = true;

    while(running) {
      updateGame();
      repaint();
      try {
        //sleep for 50 milliseconds. changes speed  of the ground. 50 seems optimum
        Thread.sleep(50);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void updateGame() {
    //increases score timer
    //the speed is based off of the score at maxes out when score hits 400
    score += 1;
    if (score<400) {
      obstacles.setMovementSpeed((score / 20) + 10);
    }
    ground.update();
    //does not work
    //plane.update();
    obstacles.update();

    if (obstacles.hasCollided()) {
      plane.die();
      repaint();
      running = false;
      gameOver = true;
      System.out.println("collide");
      if (gameOver) reset();
    }
  }

  public void reset() {
    score = 0;
    System.out.println("reset");
   obstacles.resume();
    gameOver = true;
  }

   //changed the controls to use up and down arrows
    public void keyPressed(KeyEvent e) {
      // System.out.println(e);
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        if (animator == null || !running) {
          System.out.println("Game starts");
          animator = new Thread(this);
          //animator starts running
          animator.start();
          plane.startRunning();
        } else {
          plane.up();
        }
      }
      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        //running vs !running breaks the game
        if (animator == null || !running) {
          animator = new Thread(this);
          //animator starts running
          animator.start();
          plane.startRunning();
        } else {
          plane.down();
        }
      }

    }
    //is required to run for some reason
    public void keyTyped(KeyEvent e) {
        // System.out.println("keyPressed: "+e);

    }
    public void keyReleased(KeyEvent e) {
      //stops the plane from continuing to move up or down
      plane.cruise();
    }
}