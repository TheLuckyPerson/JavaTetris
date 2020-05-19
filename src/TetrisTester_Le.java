// Assignment: SP-A2: Demo Program
// @author Tyler Le, Samuel Sovi

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.net.URL; 
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.Image;

public class TetrisTester_Le
{
   public static void main(String[] args) throws Exception
   {  
      TetrisGame tetrisGameInstance = new TetrisGame(1470,970);
      
      JFrame jf = new JFrame();
      
      JLayeredPane lp = jf.getLayeredPane();      
      
      jf.setTitle("Tetris");
      jf.setSize(700,640); 
      
      jf.setResizable(false);     
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      jf.getContentPane().setBackground(Color.BLACK);
      
      tetrisGameInstance.setBounds(0,0,700,640);
      
      lp.add(tetrisGameInstance, new Integer(2));
      //switch the numbers in the above 2 lines
      
      jf.setVisible(true);
   }
}