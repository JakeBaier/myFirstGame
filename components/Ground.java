package components;

import utility.Resource;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;


public class Ground {
  
  private class SpaceImage {
    BufferedImage image;
    int x;
  }
  private class GroundImage {
    BufferedImage DesertImage;
    int x;
  }
  
  public static int GROUND_Y;
  
  private BufferedImage image, DesertImage;
  
  private ArrayList<SpaceImage> spaceImageSet;
  private ArrayList<GroundImage> groundImageSet;
  
  public Ground(int panelHeight) {
    //moves the ground position on the screen. 1 is the top and 0 is below the bottom
    //changing 0.25 to 0.05
    GROUND_Y = (int)(panelHeight - 0.25 * panelHeight);
    
    try{
      image = new Resource().getResourceImage("../images/space.png");
    } catch(Exception e) {e.printStackTrace();}
    try {
      DesertImage = new Resource().getResourceImage("../images/Desert-2.png");
    } catch(Exception e) {e.printStackTrace();}
    
    spaceImageSet = new ArrayList<SpaceImage>();
    groundImageSet = new ArrayList<GroundImage>();
    
    //first ground image:
    for(int i=0; i<3; i++) {
      SpaceImage obj = new SpaceImage();
      obj.image = image;
      obj.x = 0;
      spaceImageSet.add(obj);
    }
    for(int i=0; i<3; i++) {
      GroundImage obj = new GroundImage();
      obj.DesertImage = DesertImage;
      obj.x = 0;
      groundImageSet.add(obj);
    }
  }
  //public Sky(int panelHeight) {
  //    SKY_BLUE = (int) (panelHeight - 0 * panelHeight);

  //    try{
  //        image= new Resource().getResourceImage("../images/skyBlue.png");
  //    } catch(Exception e) {e.printStackTrace();}
  //}
  
  public void update() {
    Iterator<SpaceImage> looper = spaceImageSet.iterator();
    SpaceImage first = looper.next();
    //the speed of the ground image moving from right to left
    first.x -= 10;
    
    int previousX = first.x;
    while(looper.hasNext()) {
      SpaceImage next = looper.next();
      next.x = previousX + image.getWidth();
      previousX = next.x;
    }
    
    if(first.x < -image.getWidth()) {
      spaceImageSet.remove(first);
      first.x = previousX + image.getWidth();
      spaceImageSet.add(first);
    }
    
  }
  
  public void create(Graphics g) {
		for(SpaceImage img : spaceImageSet) {
			g.drawImage(img.image, (int) img.x, 0, null);
		}
	}
}