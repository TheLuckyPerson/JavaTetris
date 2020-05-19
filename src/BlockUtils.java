import java.util.ArrayList;

public class BlockUtils
{
   static ArrayList<Mino>[] placedMinos;
   
   public BlockUtils()
   {
      placedMinos = new ArrayList[20];
      for(int i = 0; i < placedMinos.length; i++) {
         placedMinos[i] = new ArrayList<Mino>();
      }
   }
   
   static boolean empty = true;
  
   public static void clearMinoLine(int linePosition)
   {  
     //set all in line to unused
     for(int i = 0; i < 10; i++) {
       placedMinos[linePosition].get(i).unuseMino();
       placedMinos[linePosition].get(i).setX(-TetrisGame.SCALE_FACTOR);
       placedMinos[linePosition].get(i).setY(-TetrisGame.SCALE_FACTOR);
     }
     placedMinos[linePosition] = new ArrayList<Mino>();
     // move above down
     for(int i = linePosition; i > 0; i--) { // line where minos are
       for(int j = 0; j < placedMinos[i].size(); j++) { // minos in line
         placedMinos[i].get(j).setY(placedMinos[i].get(j).getY()+TetrisGame.SCALE_FACTOR);
         //placedMinos[i][j].
         /*printf("new Y: %i\n", placedMinos[i][j].getY());
         printf("new X: %i\n", placedMinos[i][j].getX());*/
       }
       placedMinos[i] = placedMinos[i-1];
       //placedMinos[i] = placedMinos[i-1];
     }
   }
   
   public static void startClear()
   {
     for(int i = 0; i < 20; i++) {
       if(placedMinos[i].size() == 10) {
         clearMinoLine(i);
       }
     }
   }
   
   public static boolean testBlockOverlap(int x_, int y_)
   {
     if(empty) {
       return false;
     }
     for(int i = 0; i < 20; i++)
     {
       for(int j = 0; j < placedMinos[i].size(); j++)
       {
         if(placedMinos[i].get(j).getX() == x_ && placedMinos[i].get(j).getY() == y_) {
           return true;
         }
       }
     }
     return false;
   }
   
   public static void solidify(Mino m)
   {
     empty = false;
     int linePosition = m.getY()/TetrisGame.SCALE_FACTOR;
     placedMinos[linePosition].add(m);
   }
}