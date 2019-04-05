package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import utility.Resource;

public class Obstacles {
  public void setMovementSpeed(int movementSpeed) {
    this.movementSpeed = movementSpeed;
  }

  private class Obstacle {
    BufferedImage image;
    int x;
    int y;

    Rectangle getObstacle() {
      Rectangle obstacle = new Rectangle();
      obstacle.x = x;
      obstacle.y = y;
      obstacle.width = image.getWidth();
      obstacle.height = image.getHeight();

      return obstacle;
    }
  }
  
  private int firstX;
  private int obstacleInterval;
  private double movementSpeed;
  
  private ArrayList<BufferedImage> imageList;
  private ArrayList<Obstacle> obList;

  private Obstacle blockedAt;
  
  public Obstacles(int firstPos) {
    obList = new ArrayList<Obstacle>();
    imageList = new ArrayList<BufferedImage>();

    firstX = firstPos;
    //distance between objects
    obstacleInterval = 400;
    //changed movementSpeed from 11 to 15. obstacles move too fast
    movementSpeed =11;

    //this is the order the objects appear on the screen
    imageList.add(new Resource().getResourceImage("../images/Cloud-2.png"));
    imageList.add(new Resource().getResourceImage("../images/Cloud-1.png"));
    imageList.add(new Resource().getResourceImage("../images/Cloud-3.png"));
    imageList.add(new Resource().getResourceImage("../images/Cactus-1.png"));
    imageList.add(new Resource().getResourceImage("../images/Cactus-3.png"));
    imageList.add(new Resource().getResourceImage("../images/Cactus-4.png"));
    imageList.add(new Resource().getResourceImage("../images/Cactus-5.png"));
    
    int x = firstX;
    
    for(BufferedImage bi : imageList) {
      
      Obstacle ob = new Obstacle();
      
      ob.image = bi;
      ob.x = x;
      //uppermost bound plus random generation with range of 350 cloud
      ob.y = 20 + ((int)(Math.random()*350+1));
      x += obstacleInterval;
      
      obList.add(ob);
    }
  }
  
  public void update() {
    Iterator<Obstacle> looper = obList.iterator();

    Obstacle firstOb = looper.next();
    firstOb.x -= movementSpeed;
    
    while(looper.hasNext()) {
      Obstacle ob = looper.next();
      ob.x -= movementSpeed;
    }
    
    Obstacle lastOb = obList.get(obList.size() - 1);
    
    if(firstOb.x < -firstOb.image.getWidth()) {
      obList.remove(firstOb);
      //changing obList.size() -1); () -5);
      firstOb.x = obList.get(obList.size() - 5).x + obstacleInterval;
      obList.add(firstOb);
    }
  }
  
  public void create(Graphics g) {
    for(Obstacle ob : obList) {
      g.setColor(Color.red);
      g.drawRect(ob.getObstacle().x, ob.getObstacle().y, ob.getObstacle().width, ob.getObstacle().height);
      g.drawImage(ob.image, ob.x, ob.y, null);
    }
  }
  
  public boolean hasCollided() {
    for(Obstacle ob : obList) {
      //if the plane hitbox crosses the obstacle hitbox
        if(Plane.getPlane().intersects(ob.getObstacle())) {
        System.out.println("Plane = " + Plane.getPlane() + "\nObstacle = " + ob.getObstacle() + "\n\n");
        blockedAt = ob;
        return true;
      }   
    }
    return false;
  }

  public void resume() {
      this.obList = null;
    int x = firstX/2;   
    obList = new ArrayList<Obstacle>();
    
    for(BufferedImage bi : imageList) {
      
      Obstacle ob = new Obstacle();
      
      ob.image = bi;
      ob.x = x;
      //uppermost bound plus random generation with range of 350 cloud
      ob.y = 69 + ((int)(Math.random()*350+1));
      x += obstacleInterval;
      
      obList.add(ob);
    }
  }
  
}