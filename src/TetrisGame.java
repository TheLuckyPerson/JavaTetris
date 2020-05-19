import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.net.URL; 
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.Image;


/**
Class which controls the majority of the game
*/
public class TetrisGame extends JPanel implements ActionListener, KeyListener
{
   Timer tm;
   
   public static int windowHeight;
   public static int windowWidth;
   public static final int SCALE_FACTOR = 30;
   
   int currentFallRate = 250;
   int falingRateTick = 250;
   
   int fallingRateTicks = 0;
   
   public static ArrayList<Mino> minos;
   
   final int KEY_LEFT = 65;
   final int KEY_RIGHT = 68;
   final int KEY_DOWN = 83;
   final int KEY_CLOCKWISE = 37;
   final int KEY_COUNTER_CLOCKWISE = 39;
   
   boolean leftWasReleased = true;
   boolean rightWasReleased = true;
   boolean clockWasReleased = true;
   boolean counterWasReleased = true;
   
   int lastKeyPressed = -1;
   
   Mino test;
   
   Mino[] minoPool;
   ArrayList<Integer> bag;
   Tetrimino mainPiece;
   
   
   private boolean initialized = false;
      
   /**
   @param width and height of desired game window
   Initializes the timer which controls frame rate
   Initializes JPanel
   initializes both red and blue scores
   initializes all paddles and balls and adds them to colliders list
   */
   public TetrisGame(int winWidth, int winHeight)
   {
      tm = new Timer(1,this);
      tm.start();
      addKeyListener(this);
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
      
      this.setOpaque(false);
      
      windowHeight = winHeight;
      windowWidth = winWidth;
      
      bag = new ArrayList<Integer>();
      minos = new ArrayList<Mino>();
      minoPool = new Mino[204];

      mainPiece = new Tetrimino();
      
      new BlockUtils();
      createMinoPool();
      initialized = true;
   }
   
   void createMinoPool()
   {
     for(int i = 0; i < 204; i++) {
       minoPool[i] = (new Mino());
     }
   }
   
   int constructMinoFromPool(int x, int y, Color c)
   {
     int index = -1;
     for(int i = 0; i < 204; i++) {
       if(!minoPool[i].getUse()) {
         index = i;
         break;
       }
     }
     minoPool[index].reset(x, y, c);
     return index;
   }
   
   Tetrimino createTPiece(
     int x1, int y1, 
     int x2, int y2, 
     int x3, int y3, 
     int x4, int y4, 
     Color c)
   {
     Mino[] temp = {
       minoPool[constructMinoFromPool(x1, y1, c)],
       minoPool[constructMinoFromPool(x2, y2, c)],
       minoPool[constructMinoFromPool(x3, y3, c)],
       minoPool[constructMinoFromPool(x4, y4, c)],
     };
   
     return new Tetrimino(temp);
   }
   
   void remakeBag()
   {
     for(int i = 0; i < 7; i++)
     {
       bag.add(i);
     }
   }
   
   void removeFromBag(int index)
   {
     ArrayList<Integer> newBag = new ArrayList<Integer>();
     for(int i = 0; i < bag.size(); i++) {
       if(bag.get(i)!=index) newBag.add(bag.get(i));
     }
     bag = newBag;
   }
   
   Tetrimino generatePiece()
   {
     if(bag.size() <= 0) remakeBag();
     int random = (int)(Math.random() * bag.size());
     int pieceNum = bag.get(random);
     removeFromBag(pieceNum);
     //random = 6;
     switch (pieceNum) {
       case 0: // T piemce
         return createTPiece(0, 0, -SCALE_FACTOR, 0, SCALE_FACTOR, 0, 0, -SCALE_FACTOR, new Color(102, 0, 153));
       case 1: // O pience
         return createTPiece(SCALE_FACTOR, SCALE_FACTOR, 0, SCALE_FACTOR, SCALE_FACTOR, 0,
         0, 0, Color.YELLOW);
       case 2: // I pience
         return createTPiece(0, 0, SCALE_FACTOR, 0, -SCALE_FACTOR, 0,
         -SCALE_FACTOR*2, 0, new Color(100, 100, 255));
       case 3: // L piece
         return createTPiece(0, 0, -SCALE_FACTOR, 0, SCALE_FACTOR, 0,
         SCALE_FACTOR, -SCALE_FACTOR, Color.ORANGE);
       case 4: // J piece
         return createTPiece(0, 0, -SCALE_FACTOR, 0, SCALE_FACTOR, 0,
         -SCALE_FACTOR, -SCALE_FACTOR, Color.BLUE);
       case 5: // Z piece
         return createTPiece(0, 0, SCALE_FACTOR, 0, -SCALE_FACTOR, -SCALE_FACTOR,
         0, -SCALE_FACTOR, Color.RED);
       case 6: // S piece
         return createTPiece(0, 0, -SCALE_FACTOR, 0, SCALE_FACTOR, -SCALE_FACTOR,
         0, -SCALE_FACTOR, Color.GREEN);
       default:
         return createTPiece(0, 0, -SCALE_FACTOR, 0, SCALE_FACTOR, 0, 0, -SCALE_FACTOR, new Color(102, 0, 153));
     } 
   }
      
   /**
   paints graphics components representing both paddles and ball
   */
   public void paintComponent(Graphics g1)
   {
      super.paintComponent(g1);
      
      // draw bg
      for(int x = 0; x < 10*SCALE_FACTOR; x+=SCALE_FACTOR) {
         for(int y = 0; y < 20*SCALE_FACTOR; y+=SCALE_FACTOR) {
            g1.setColor(Color.WHITE);
            g1.fillRect(x+180, y, SCALE_FACTOR, SCALE_FACTOR);
            g1.setColor(Color.BLACK);
            g1.fillRect(x+181, y+1, SCALE_FACTOR-2, SCALE_FACTOR-2);
         }
      }
      
      for(int i = 0; i < minos.size(); i++) {
         Mino m = minos.get(i);
         if(m.getUse()) {
            g1.setColor(m.getColor());
            g1.fillRect(m.getX()+1,m.getY()+1,SCALE_FACTOR-2,SCALE_FACTOR-2);
         }
      }
   }
   
   /**
   updates movement of all objects then repaints their graphics objects
   */
   public void actionPerformed(ActionEvent e)
   {
      if(initialized) {
         if(mainPiece.getSolid()) {
            mainPiece = generatePiece();
         }
         
         if(fallingRateTicks > currentFallRate) {
            mainPiece.blockFall();
            fallingRateTicks = 0;
         }
         
         fallingRateTicks++;
        
         repaint();
      }
   }
   /**
   handles key pressed event
   */
   public void keyPressed(KeyEvent e)
   {
      int c = e.getKeyCode();

      if(c == KEY_LEFT && leftWasReleased)
      {
         mainPiece.move(false);
         leftWasReleased = false;
      }
      if(c == KEY_RIGHT && rightWasReleased)
      {
         mainPiece.move(true);
         rightWasReleased = false;
      }
      
      if(c == KEY_CLOCKWISE && clockWasReleased) {
         mainPiece.rotateBlock(true);
         clockWasReleased = false;
      } 
      if(c == KEY_COUNTER_CLOCKWISE && counterWasReleased) {
         mainPiece.rotateBlock(false);
         counterWasReleased = false;
      } 
      
      if(c == KEY_DOWN) currentFallRate = 20;
   }
   /**
   handles key typed event
   */
   public void keyTyped(KeyEvent e){}
   /**
      handles on release events for keys
   */
   public void keyReleased(KeyEvent e)
   {
      int c = e.getKeyCode();
      
      if(c == KEY_LEFT) leftWasReleased = true;
      if(c == KEY_RIGHT) rightWasReleased = true;
      if(c == KEY_CLOCKWISE) clockWasReleased = true;
      if(c == KEY_COUNTER_CLOCKWISE) counterWasReleased = true;
      if(c == KEY_DOWN) currentFallRate = falingRateTick;
   }
}