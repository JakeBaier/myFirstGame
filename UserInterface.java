import java.awt.*;
import javax.swing.*;

class UserInterface {
  //main window is created
  JFrame mainWindow = new JFrame("Don't Touch the Clouds");
  //size of the frame
  public static int WIDTH =1000;
  public static int HEIGHT = 700;
  //new class to show the content
  public void createAndShowGUI() {
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Container container = mainWindow.getContentPane();
    //game panel and key listener are made
    GamePanel gamePanel = new GamePanel();
    gamePanel.addKeyListener(gamePanel);
    gamePanel.setFocusable(true);
    //changes the background color to blue
    gamePanel.setBackground(Color.CYAN);
    
    container.setLayout(new BorderLayout());
    
    container.add(gamePanel, BorderLayout.CENTER);
    //setting frame with width and height
      //GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      //GraphicsDevice gd = ge.getDefaultScreenDevice();
      //gd.setFullScreenWindow(mainWindow);
    mainWindow.setSize(WIDTH,HEIGHT);
    //changing from false to true
    mainWindow.setResizable(true);
    mainWindow.setVisible(true);
  }
  //main class calls UserInterface.createAndShowGUI
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new UserInterface().createAndShowGUI();
      }
    });
  }
}