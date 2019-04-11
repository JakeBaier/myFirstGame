package components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import utility.Resource;

public class Plane {
  //declared integers for plane size/location
  private static int planeBaseY, planeTopY, planeStartX, planeEndX, planeTop, planeBottom;
  private static boolean topPointReached;
  //changing jumFactor from 20 to 15. games plays much better at 15
  private static int jumpFactor = 15;
  //declared integers for the state of the plane
  public static final int STAND_STILL = 1,
                    FLYING = 2,
                    UP = 3,
                    CRUISE = 4,
                    DOWN = 5,
                    DIE = 6;
  private static int state;
  //declare image name
  static BufferedImage image;
  static BufferedImage planeUP;
  //above BufferedImage are used to gather image resources


  public Plane() {
    //plane image
    image = new Resource().getResourceImage("../images/Jet-Plane-3.png");
    planeUP = new Resource().getResourceImage("../images/Jet-Plane-Tilt_UP.png");
    planeBaseY = 50;
    planeTopY = 50;
    planeStartX = 50;
    //PIXEL 100 plus width of image
    planeEndX = planeStartX + image.getWidth();

    state = 0;
  }

  public void create(Graphics g) {
    planeBottom = planeTop + image.getHeight();
    g.setColor(Color.red);
    //g.drawRect(getPlane().x, getPlane().y, getPlane().width, getPlane().height);
      //g.drawRect(getPlane(),image.100,image.76);
    //g.drawRect(getPlane().x, getPlane().y,image.getWidth(),image.getHeight());

    switch(state) {
//starting state of the game
      case STAND_STILL:
        System.out.println("stand");
        g.drawImage(image, planeStartX, planeTopY, null);
        //g.drawImage(image, planeStartX, 50, null);
        break;
      case CRUISE:
        g.drawImage(image,planeStartX, planeTop, null);
        break;

      case UP:
        //cutting out planeTop
          if(getPlane().getY()>= 65) {
              g.drawImage(image, planeStartX, planeTop, null);
              planeTop -= 13;
              break;
          }else{
              cruise();
          }

      case DOWN:
          if(getPlane().getY()< 500) {
              g.drawImage(image, planeStartX, planeTop, null);
              planeTop += 13;
              break;
          }else {
              cruise();
          }

      case DIE: 
        g.drawImage(image, planeStartX, planeTop, null);
        break;
    }
  }

  public void die() {
    state = DIE;
  }

  public static Rectangle getPlane() {
    Rectangle plane = new Rectangle();
    plane.x = planeStartX;

    if(state == UP && !topPointReached) plane.y = planeTop - jumpFactor;
    else if(state == UP && topPointReached) plane.y = planeTop + jumpFactor;
    else if(state != UP) plane.y = planeTop;

    plane.width = image.getWidth();
    plane.height = image.getHeight();

    return plane;
  }

  public void startRunning() {
    planeTop = 200;
    state = CRUISE;
  }

  public void up() {
      System.out.println(getPlane().getY());
      if(getPlane().getY() >= 65) {
          topPointReached = false;
          state = UP;
      }
  }
  public void down() {
      System.out.println(getPlane().getY());
      if(getPlane().getY() < 500) {
          topPointReached = false;
          state = DOWN;
      }
  }
  public void cruise() {
    topPointReached = false;
    state = CRUISE;
  }
}