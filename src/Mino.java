import java.awt.Color;

public class Mino
{
    private int x;
    private int y;
    private int width;
    private int height;
    private Color col;
    private boolean inUse = false;
    
    public Mino()
    {
      x = 0;
      y = 0;
      width = TetrisGame.SCALE_FACTOR;
      height = TetrisGame.SCALE_FACTOR;
      col = new Color(102, 0, 153); // t- purple is defualt
      TetrisGame.minos.add(this);
    }

    public Mino(int x_, int y_, Color c, int width_, int height_) 
    {
      x= x_;
      y = y_;
      width = width_;
      height = height_;
      col = c;
      TetrisGame.minos.add(this);
    }

    public void clearMino()
    {
      col = Color.BLACK;
    }
    
    public void unuseMino()
    {
      inUse = false;
      clearMino();
    }

    public void useMino()
    {
      inUse = true;
    }

    public void minoFall()
    {
      y+=TetrisGame.SCALE_FACTOR;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public Color getColor() {return col;}

    public void setX(int x_) 
    {
      x=x_;
    }
    
    public void setY(int y_) 
    {
      y=y_;
    }
    
    public void setColor(Color c)
    {
      col = c;
    }
    
    public boolean getUse()
    {
      return inUse;
    }

    public void rotate(boolean dir, int curX, int curY)
    {
      if(dir && col != Color.YELLOW) {
        int temp = x;
        x = -(y-curY)+curX;
        y = temp-curX+curY;
      } else if(col!=Color.YELLOW){
        int temp = x;
        x = (y-curY)+curX;
        y = -(temp-curX)+curY;
      }
    }
    
    public boolean testOverlap(int x_, int y_, boolean b)
    {
      return (x_ < 180 || x_ > 180+9*TetrisGame.SCALE_FACTOR || y_ > 20*TetrisGame.SCALE_FACTOR-TetrisGame.SCALE_FACTOR) || (b && BlockUtils.testBlockOverlap(x_, y_));
    }
    
    public boolean testRotate(boolean dir, int curX, int curY)
    {
      int newX;
      int newY;
      if(dir) {
        newX = -(y-curY)+curX;
        newY = x-curX+curY;
      } else {
        newX = y-curY+curX;
        newY = -(x-curY)+curX;
      }

      return testOverlap(newX, newY, true);
    }
    
    public boolean testMoveMino(boolean dir)
    {
      return testOverlap(dir ? x+TetrisGame.SCALE_FACTOR : x-TetrisGame.SCALE_FACTOR, y, true);
    }

    public void moveMino(boolean dir)
    {
      setX(dir ? x+TetrisGame.SCALE_FACTOR : x-TetrisGame.SCALE_FACTOR);
    }
    
    public void reset(int x_, int y_, Color c)
    {
      inUse = true;
      setX(x_);
      setY(y_);
      setColor(c);
    }
}