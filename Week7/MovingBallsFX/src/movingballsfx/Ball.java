package movingballsfx;

import java.util.Random;
import javafx.scene.paint.Color;

public class Ball {

    private String name;
    private int xPos, yPos, speed;
    private int minX, maxX;
    private Color color;
    private int minCsX;
    private int maxCsX;
    

    public Ball(int minX, int maxX, int minCsX, int maxCsX, int yPos, Color color, String name) {
        this.xPos = minX;
        this.yPos = yPos;
        this.minX = minX;
        this.maxX = maxX;
        this.minCsX = minCsX;
        this.maxCsX = maxCsX;
        this.speed = 10 + (new Random()).nextInt(5);
        this.color = color;
        this.name = name;
    }

    public void move() {
        xPos++;
        if (xPos > maxX) {
            xPos = minX;
        }
    }

    public String getName(){
        return name;
    }
    
    public int getXPos() {
        return xPos;
    }

   public int getYPos() {
        return yPos;
    }

    public Color getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isEnteringCs() {
        return xPos == minCsX;
    }
    
    public boolean isLeavingCs() {
        return xPos == maxCsX;
    }
    
    public boolean isInCs(){
        return xPos >= minCsX && xPos <= maxCsX;
    }
}
