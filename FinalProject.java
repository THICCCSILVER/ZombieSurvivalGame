import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    static BufferedImage playerImage;

     static int[][] map ={{0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0},
                          {0, 6, 6, 6, 6, 6,9,0,0,0,0,0,0},
                          {0, 4, 3, 2, 1, 2,5,0,0,0,0,0,0},
                          {0, 4, 2, 3, 2, 3,5,0,0,0,0,0,0},
                          {0, 4, 1, 2, 3, 1,5,0,0,0,0,0,0},
                          {0, 4, 2, 1, 2, 2,5,0,0,0,0,0,0},
                          {0, 7, 7, 7, 7, 7,8,0,0,0,0,0,0},
                          {0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0}
    };  

    //static int[][] map = new int[64][64];

    static BufferedImage[] tileID = new BufferedImage[10];
    static MyKeyListener keyListener = new MyKeyListener();

        // character properties
 
    static final int RUN_SPEED = 2;
    static final int GRAVITY = 2;
    
    static int ninjaH = 35;
    static int ninjaW = 35;
    static int ninjaX = 75;
    static int ninjaY = 75;
    static int ninjaVx = 0;
    static int ninjaVy = 0;
    static int ninjaPicNum = 1;
    static BufferedImage[] ninjaPic = new BufferedImage[17];
    static int[] nextLeftPic  = {1, 2, 3, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    static int[] nextRightPic = {5, 5, 5, 5, 5, 6, 7, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5}; 
    static int[] nextUpPic = {9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 11, 12, 9, 9, 9, 9, 9};
    static int[] nextDownPic = {13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 15, 16, 13};

    static boolean goingLeft = false;
    static boolean goingRight = false;
    static boolean goingUp = false;
    static boolean goingDown = false;
    
    static String lastMovement = "";


    static int wallH = 32;
    static int wallW = 32;

    static int playerW = 32;
    static int playerH = 32;

    static Rectangle playerBox = new Rectangle(ninjaX, ninjaY, ninjaW, ninjaH);
    static Rectangle futureLeftHitBox = new Rectangle(ninjaX, ninjaY, ninjaW, ninjaH);
    static Rectangle futureRightHitBox = new Rectangle(ninjaX, ninjaY, ninjaW, ninjaH);
    static Rectangle futureUpHitBox = new Rectangle(ninjaX, ninjaY, ninjaW, ninjaH);
    static Rectangle futureDownHitBox = new Rectangle(ninjaX, ninjaY, ninjaW, ninjaH);



    static Rectangle[] wallBox;
    
    static double elapsedTimeSentence = 0.0;  
    static long start = System.currentTimeMillis();
    static long end = 0;

    public static void main(String[] args){

        


        

        int wallCount = 0;
        int wallBoxCount = 0;

        gameWindow = new JFrame("Game Window");
        gameWindow.setSize(WIDTH,HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        canvas = new GraphicsPanel();   
        canvas.addKeyListener(keyListener);     
        gameWindow.add(canvas); 





        for(int i = 0; i < map.length; i++){

            wallCount = wallCount + amountInArray(map[i], 4) + amountInArray(map[i], 5) + amountInArray(map[i], 6) + amountInArray(map[i], 7) + amountInArray(map[i], 8) +  amountInArray(map[i], 9);
        }

        

        wallBox = new Rectangle[wallCount];


        for(int i = 0; i < map.length; i++){

            for(int count = 0; count < map[0].length; count++){
                
                if(map[i][count] == 4 || map[i][count] == 5 || map[i][count] == 6 || map[i][count] == 7 || map[i][count] == 8 || map[i][count] == 9){

                    wallBox[wallBoxCount] = new Rectangle(i*32, count*32, wallH, wallW);
                    
                    wallBoxCount++;
                   
                    
                  
                }


            }


        }
       

       

        

        for(int i = 0; i < 10; i++){

            try{
                tileID[i] = ImageIO.read(new File("images/tile" + Integer.toString(i)+ ".png"));
            }
            catch(IOException ex){}


        }

        for (int i=0; i<17; i++){
            try {             
                ninjaPic[i] = ImageIO.read(new File("images/ninja" + Integer.toString(i)+ ".png"));
            } catch (IOException ex){} 
        }

  
         gameWindow.setVisible(true);
         runGameLoop();
    
    }

    public static void runGameLoop(){

       while (true) {
            gameWindow.repaint();
            try  {Thread.sleep(20);} catch(Exception e){}
            
            // move Mario in horizontal direction

            futureLeftHitBox.setLocation(ninjaX - 2, ninjaY);
            if ((checkCollision(futureLeftHitBox)) && (goingLeft)){
                ninjaVx = 0;
                System.out.print("4");
            }

            futureRightHitBox.setLocation(ninjaX + 2, ninjaY);

            if ((checkCollision(futureRightHitBox)) && (goingRight)){
                System.out.print("3");
                ninjaVx = 0;
            }
            
            futureUpHitBox.setLocation(ninjaX, ninjaY - 2);

            if ((checkCollision(futureUpHitBox)) && (goingUp)){
                ninjaVy = 0;
                System.out.print("2");
            }

            futureDownHitBox.setLocation(ninjaX, ninjaY + 2);

            if ((checkCollision(futureDownHitBox)) && (goingDown)){
                ninjaVy = 0;
                System.out.print("2");
            }

            ninjaX = ninjaX + ninjaVx;
            ninjaY = ninjaY + ninjaVy;
            playerBox.setLocation(ninjaX,ninjaY);

            // select ninja's picture   
            if (elapsedTimeSentence/80 > 1){
                if ((ninjaVy == 0) && (ninjaVx == 0)){
                    ninjaPicNum = 0;
                } else if (ninjaVx < 0){
                    ninjaPicNum = nextLeftPic[ninjaPicNum]; 
                } else if (ninjaVx > 0){                    
                    ninjaPicNum = nextRightPic[ninjaPicNum];  
                } else if (ninjaVy < 0){
                    ninjaPicNum = nextUpPic[ninjaPicNum];
                } else if (ninjaVy > 0){
                    ninjaPicNum = nextDownPic[ninjaPicNum];
                }    
                start = System.currentTimeMillis();  
            }   
            end = System.currentTimeMillis();
            elapsedTimeSentence = end - start; 
        }
    } // runGameLoop method end

    
        



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
                    

                
                    
                  
                }

            }


            for(int i = 0; i < map.length; i++){

                for(int count = 0; count < map[0].length; count++){
                
                    if(map[i][count] == 4 || map[i][count] == 5 || map[i][count] == 6 || map[i][count] == 7 || map[i][count] == 8 || map[i][count] == 9){

                         g.drawRect(i*32,  count*32, wallH, wallW);

                    }


                }
            }

            g.drawImage(ninjaPic[ninjaPicNum],ninjaX,ninjaY,this);

            g.drawRect(ninjaX,ninjaY,ninjaW, ninjaH);
        }

    }

    static class MyKeyListener implements KeyListener{      
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT ){
            
                ninjaVx = -RUN_SPEED;
                goingLeft = true;

            } 
            if (key == KeyEvent.VK_RIGHT){
                ninjaVx = RUN_SPEED;
                goingRight = true;
                lastMovement = "right";


            }
            if (key == KeyEvent.VK_UP){
                ninjaVy = -RUN_SPEED;   
                goingUp = true;    
                lastMovement = "up";

            }
            if (key == KeyEvent.VK_DOWN){
                ninjaVy = RUN_SPEED;
                goingDown = true;
                lastMovement = "down";
            }             
        }
        public void keyReleased(KeyEvent e){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                ninjaVx = 0;
                goingLeft = false;
                if (goingRight){
                    ninjaVx = RUN_SPEED;
                }
            }
            if (key == KeyEvent.VK_RIGHT) {
                ninjaVx = 0;
                goingRight = false;
                if (goingLeft){
                    ninjaVx = -RUN_SPEED;
                }
            }
            if (key == KeyEvent.VK_UP) {
                ninjaVy = 0;
                goingUp = false;
                if (goingDown){
  
                    ninjaVy = RUN_SPEED;
                } 
            }
            if (key == KeyEvent.VK_DOWN) {
                ninjaVy = 0;
                goingDown = false;
                if (goingUp){
   
                    ninjaVy = -RUN_SPEED;
                } 
            }
        }       
        public void keyTyped(KeyEvent e){
        }        
    }

       




    public static int amountInArray(int[] array, int value){
        int count = 0;

        for(int i = 0; i < array.length; i++){
            
            if(array[i] == value){
                count++;
            }


            
        }

        return count;



    }


    public static boolean checkCollision(Rectangle collisionObject){

        for(int i = 0; i < wallBox.length; i++){

            if(wallBox[i].intersects(collisionObject)){
                return true;
            }


        }

        return false;
    
    }
    
}