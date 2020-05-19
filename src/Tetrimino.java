public class Tetrimino
{
    private Mino[] minos;
    private int x = 0;
    private int y = 0;
    private boolean solid = false;
    
    public Tetrimino()
    {
      solid = true;
    }
    
    public Tetrimino(Mino[] minos_)
    {
      minos = new Mino[4];
      for(int i = 0; i < 4; i++) {
        minos[i] = minos_[i];
      }
      x = 0;
      y = 0;
      solid = false;
      spawn(240+90, 0);
    }

    public void spawn(int xPos, int yPos)
    {      
      x = xPos;
      y = yPos;
      for(int i = 0; i < 4; i++) {
        minos[i].setX(minos[i].getX()+xPos);
        minos[i].setY(minos[i].getY()+yPos);
      }
    }

    public void clearBlock()
    {
      for(int i = 0; i < 4; i++) {
        minos[i].clearMino();
      }
    }

    public boolean blockFall()
    {
      if(!solid) {
        // test
        for(int i = 0; i < 4; i++) {
          if (minos[i].testOverlap(minos[i].getX(), minos[i].getY()+TetrisGame.SCALE_FACTOR, true)) {
            solidifyBlock(); 
            return false;
          }
        }

        // do
        if(!solid) {
          for(int i = 0; i < 4; i++) {
            minos[i].minoFall();
          }
          y += TetrisGame.SCALE_FACTOR;
        }
      }
      return true;
    }

    public void rotateBlock(boolean dir)
    {
      if(!solid) {
        // test
        for(int i = 0; i < 4; i++) {
          if (minos[i].testRotate(dir, x, y)) return;
        }
        
        // do
        for(int i = 0; i < 4; i++) {
          minos[i].rotate(dir, x, y);
        }
      }
    }

    public void move(boolean dir)
    {
      if(!solid) {
        // test
        for(int i = 0; i < 4; i++) {
          if (minos[i].testMoveMino(dir)) return;
        }
        
        // do
        x += dir ? TetrisGame.SCALE_FACTOR : -TetrisGame.SCALE_FACTOR;
        for(int i = 0; i < 4; i++) {
          minos[i].moveMino(dir);
        }
      }
    }

    public Mino[] getBlocks()
    {
      return minos;
    }

    public boolean getSolid()
    {
      return solid;
    }

    private void solidifyBlock()
    {
      if(!solid) {
        solid = true;
        for(int i = 0; i < 4; i++) {
          BlockUtils.solidify(minos[i]);
        }
        BlockUtils.startClear();
      }
    }
}