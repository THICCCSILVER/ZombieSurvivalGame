import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;


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

    //static int[][] map = new int[64][64];uj

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

    static boolean standLeft = false;
    static boolean standRight = false;
    static boolean standUp = false;
    static boolean standDown = false;

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
    
    static double elapsedTimeMario = 0.0;  
    static long start = System.currentTimeMillis();
    static long end = 0;

    static double elapsedTimeBullet = 0.0;  
    static long startBullet = System.currentTimeMillis();
    static long endBullet = 0;

    static int numBullets = 40;    
    static int[] bulletRightX = new int[numBullets];
    static int[] bulletRightY = new int[numBullets];
    static Rectangle[] bulletRightHitBox = new Rectangle[numBullets];
    static boolean[] bulletRightVisible = new boolean[numBullets];
    static int bulletW = 10;
    static int bulletH = 6;
    static int bulletSpeed = 10;
    static int currentBulletRight = 0;

    static int[] bulletLeftX = new int[numBullets];
    static int[] bulletLeftY = new int[numBullets];
    static Rectangle[] bulletLeftHitBox = new Rectangle[numBullets];
    static boolean[] bulletLeftVisible = new boolean[numBullets];
    static int currentBulletLeft = 0;

    static int[] bulletUpX = new int[numBullets];
    static int[] bulletUpY = new int[numBullets];
    static Rectangle[] bulletUpHitBox = new Rectangle[numBullets];
    static boolean[] bulletUpVisible = new boolean[numBullets];
    static int currentBulletUp = 0;

    static int[] bulletDownX = new int[numBullets];
    static int[] bulletDownY = new int[numBullets];
    static Rectangle[] bulletDownHitBox = new Rectangle[numBullets];
    static boolean[] bulletDownVisible = new boolean[numBullets];
    static int currentBulletDown = 0;

    static boolean shooting = false;
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
            try  {Thread.sleep(15);} catch(Exception e){}
            
            // move Mario in horizontal direction
            if (elapsedTimeBullet/150 > 1){
                if ((shooting) && (ninjaVy == 0) && ((ninjaPicNum == 6) || (goingRight))){
                    bulletRightX[currentBulletRight] = ninjaX + ninjaW/2 - bulletW/2;
                    bulletRightY[currentBulletRight] = ninjaY + 25;
                    bulletRightVisible[currentBulletRight] = true;
                    currentBulletRight = (currentBulletRight + 1)%numBullets;
                } else if ((shooting) && (ninjaVy == 0) && ((ninjaPicNum == 4) || (goingLeft))){
                    bulletLeftX[currentBulletLeft] = ninjaX + ninjaW/2 - bulletW/2;
                    bulletLeftY[currentBulletLeft] = ninjaY + 25;
                    bulletLeftVisible[currentBulletLeft] = true;
                    currentBulletLeft = (currentBulletLeft + 1)%numBullets;
                } else if ((shooting) && (ninjaVx == 0) && ((ninjaPicNum == 0) || (goingDown))){
                    bulletDownX[currentBulletDown] = ninjaX + ninjaW/2 - bulletW/2;
                    bulletDownY[currentBulletDown] = ninjaY + 25;
                    bulletDownVisible[currentBulletDown] = true;
                    currentBulletDown = (currentBulletDown + 1)%numBullets;
                } else if ((shooting) && (ninjaVx == 0) && ((ninjaPicNum == 10) || (goingUp))){
                    bulletUpX[currentBulletUp] = ninjaX + ninjaW/2 - bulletW/2;
                    bulletUpY[currentBulletUp] = ninjaY + 25;
                    bulletUpVisible[currentBulletUp] = true;
                    currentBulletUp = (currentBulletUp + 1)%numBullets;
                }
                startBullet = System.currentTimeMillis();  
            }   
            endBullet = System.currentTimeMillis();
            elapsedTimeBullet = endBullet - startBullet; 

            for (int i=0; i<numBullets; i++){
                if (bulletRightVisible[i]){
                    bulletRightX[i] = bulletRightX[i] + bulletSpeed;
                    bulletRightHitBox[i] = new Rectangle(bulletRightX[i], bulletRightY[i], bulletW, bulletH);
                    if (checkCollision(bulletRightHitBox[i])) {
                        bulletRightVisible[i] = false;
                    }
                }
                if (bulletLeftVisible[i]){
                    bulletLeftX[i] = bulletLeftX[i] - bulletSpeed;
                    bulletLeftHitBox[i] = new Rectangle(bulletLeftX[i], bulletLeftY[i], bulletW, bulletH);
                    if (checkCollision(bulletLeftHitBox[i])) {
                        bulletLeftVisible[i] = false;
                    }
                }
                if (bulletDownVisible[i]){
                    bulletDownY[i] = bulletDownY[i] + bulletSpeed;
                    bulletDownHitBox[i] = new Rectangle(bulletDownX[i], bulletDownY[i], bulletW, bulletH);
                    if (checkCollision(bulletDownHitBox[i])) {
                        bulletDownVisible[i] = false;
                    }
                }
                if (bulletUpVisible[i]){
                    bulletUpY[i] = bulletUpY[i] - bulletSpeed;
                    bulletUpHitBox[i] = new Rectangle(bulletUpX[i], bulletUpY[i], bulletW, bulletH);
                    if (checkCollision(bulletUpHitBox[i])) {
                        bulletUpVisible[i] = false;
                    }
                }
            }     

            if (goingLeft) {
                ninjaVx = -RUN_SPEED;
            } 
            if (goingRight) {
                ninjaVx = RUN_SPEED;
            } 
            if (goingUp) {
                ninjaVy = -RUN_SPEED;
            } 
            if (goingDown) {
                ninjaVy = RUN_SPEED;
            }

            futureLeftHitBox.setLocation(ninjaX - 2, ninjaY);
            if ((checkCollision(futureLeftHitBox)) && (goingLeft)){
                ninjaVx = 0;
            }

            futureRightHitBox.setLocation(ninjaX + 2, ninjaY);

            if ((checkCollision(futureRightHitBox)) && (goingRight)){
                ninjaVx = 0;
            }
            
            futureUpHitBox.setLocation(ninjaX, ninjaY - 2);

            if ((checkCollision(futureUpHitBox)) && (goingUp)){
                ninjaVy = 0;
            }

            futureDownHitBox.setLocation(ninjaX, ninjaY + 2);

            if ((checkCollision(futureDownHitBox)) && (goingDown)){
                ninjaVy = 0;
            }

            ninjaX = ninjaX + ninjaVx;
            ninjaY = ninjaY + ninjaVy;
            playerBox.setLocation(ninjaX,ninjaY);
            
            // select ninja's picture   
            if (elapsedTimeMario/80 > 1){
                if ((ninjaVx == 0) && (ninjaVy == 0)){
                    if (standLeft) {
                        ninjaPicNum = 4;
                    } else if (standRight) {
                        ninjaPicNum = 6;
                    } else if (standUp) {
                        ninjaPicNum = 10;
                    } else if (standDown) {
                        ninjaPicNum = 0;
                    } 
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
            elapsedTimeMario = end - start; 
        }
    } // runGameLoop method end

    
        



    static class GraphicsPanel extends JPanel{
        public GraphicsPanel(){
            setFocusable(true);
            requestFocusInWindow();
        }
        public void paintComponent(Graphics g){ 

            
            super.paintComponent(g);
            g.setColor(Color.red);

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

            for (int i=0; i<numBullets; i++){
                if (bulletRightVisible[i]) {
                    g.fillOval(bulletRightX[i],bulletRightY[i],bulletW,bulletH);
                }
                if (bulletLeftVisible[i]) {
                    g.fillOval(bulletLeftX[i],bulletLeftY[i],bulletW,bulletH);
                }
                if (bulletDownVisible[i]) {
                    g.fillOval(bulletDownX[i],bulletDownY[i],bulletH,bulletW);
                }
                if (bulletUpVisible[i]) {
                    g.fillOval(bulletUpX[i],bulletUpY[i],bulletH,bulletW);
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
                goingLeft = true;
                goingRight = false;
            } else if (key == KeyEvent.VK_RIGHT){
                goingRight = true;
                goingLeft = false;

            }
            if (key == KeyEvent.VK_UP){ 
                goingUp = true;   
                goingDown = false;

            } else if (key == KeyEvent.VK_DOWN){
                goingDown = true;
                goingUp = false;
            } 
            if (key == KeyEvent.VK_SPACE){
                // assign the coordinates of the top middle point of the ship to the current bulletRight
                shooting = true;
            }            
        }
        public void keyReleased(KeyEvent e){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                ninjaVx = 0;
                goingLeft = false;
                standLeft = true;
                standRight = false;
                standUp = false;
                standDown = false;
                if (goingRight){
                    ninjaVx = RUN_SPEED;
                }
            }
            if (key == KeyEvent.VK_RIGHT) {
                ninjaVx = 0;
                goingRight = false;
                standRight = true;
                standLeft = false;
                standUp = false;
                standDown = false;
                if (goingLeft){
                    ninjaVx = -RUN_SPEED;
                }
            }
            if (key == KeyEvent.VK_UP) {
                ninjaVy = 0;
                goingUp = false;
                standUp = true;
                standRight = false;
                standLeft = false;
                standDown = false;
                if (goingDown){
                    ninjaVy = RUN_SPEED;
                } 
            }
            if (key == KeyEvent.VK_DOWN) {
                ninjaVy = 0;
                goingDown = false;
                standDown = true;
                standRight = false;
                standUp = false;
                standLeft = false;
                if (goingUp){
                    ninjaVy = -RUN_SPEED;
                } 
            }
            if (key == KeyEvent.VK_SPACE){
                // assign the coordinates of the top middle point of the ship to the current bulletRight
                shooting = false;
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