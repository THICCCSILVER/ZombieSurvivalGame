
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.EventQueue;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class FinalProject{

    static JFrame gameWindow;
    static GraphicsPanel canvas;
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static int tileX = 0;
    static int tileY = 0;
    static BufferedImage test;

    static int[][] map = {{0, 0, 0, 0, 0, 0,0,0},
                          {0, 6, 6, 6, 6, 6,9,0},
                          {0, 4, 3, 2, 1, 2,5,0},
                          {0, 4, 2, 3, 2, 3,5,0},
                          {0, 4, 1, 2, 3, 1,5,0},
                          {0, 4, 2, 1, 2, 2,5,0},
                          {0, 7, 7, 7, 7, 7,8,0},
                          {0, 0, 0, 0, 0, 0,0,0}
    };

    static BufferedImage[] tileID = new BufferedImage[10];

    


    public static void main(String[] args){

        gameWindow = new JFrame("Game Window");
        gameWindow.setSize(WIDTH,HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        canvas = new GraphicsPanel();        
        gameWindow.add(canvas); 

        

        for(int i = 0; i < 10; i++){

            try{
                tileID[i] = ImageIO.read(new File("images/tile" + Integer.toString(i)+ ".png"));
            }
            catch(IOException ex){}


            }

            try{
               test = ImageIO.read(new File("images/Character" + ".png"));
            }
            catch(IOException ex){
    


            }


            gameWindow.setVisible(true);
    
        }
        


    




    static class GraphicsPanel extends JPanel{
        public GraphicsPanel(){
            setFocusable(true);
            requestFocusInWindow();
        }
        public void paintComponent(Graphics g){ 

            
            super.paintComponent(g);

            for(int i = 0;i < map.length; i++ ){
                tileX = i*32;
                for(int count =0 ; count < map[0].length;count++){

      
                    tileY = count*32;
    
                    g.drawImage(tileID[map[i][count]],tileX, tileY,this);
                    g.drawImage(test, 32,32,this);
                    
                  
                }

            }
        }

