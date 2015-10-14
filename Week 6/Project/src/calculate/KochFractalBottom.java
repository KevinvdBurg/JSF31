/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import javafx.scene.paint.Color;

/**
 *
 * @author Kevin van der Burg en Milton van de Sanden
 */
public class KochFractalBottom /*extends Observable*/ implements Callable<ArrayList<Edge>>{

    private int level;      // The current level of the fractal
    private int nrOfEdges;  // The number of edges in the current level of the fractal
    private float hue;          // Hue value of color for next edge
    private boolean cancelled;  // Flag to indicate that calculation has been cancelled 
    private KochManager kochManager;
    private final CyclicBarrier cyclicBarrier;
    
    private ArrayList<Edge> edges = new ArrayList<>();

    /*
    private double ax;
    private double ay;
    private double bx;
    private double by;
    private int n;
    */
    
    private boolean done;

//    public KochFractalBottom(int level, int nrOfEdges,  KochManager aThis)
//    {
//        this.level = level;
//        this.nrOfEdges = nrOfEdges;
//        this.kochManager = aThis;
//        done = false;
//    }

    KochFractalBottom(int level, int nrOfEdges, KochManager aThis, CyclicBarrier cyclicBarrier)
    {
        this.level = level;
        this.nrOfEdges = nrOfEdges;
        this.kochManager = aThis;
        this.cyclicBarrier = cyclicBarrier;
        done = false;
    }
    
    @Override
    public ArrayList<Edge> call()
    {
        //while(true)
        //{
            System.out.println("Generate Bottom Fractel...");
            generateBottomEdge();
            System.out.println("Generate Bottom has finished its work... waiting for others...");
            
            
            try {
                    cyclicBarrier.await();
            } catch (InterruptedException e) {
                    System.out.println("Generate Bottom interrupted!");
                    e.printStackTrace();
            } catch (BrokenBarrierException e) {
                    System.out.println("Generate Bottom interrupted!");
                    e.printStackTrace();
            }
            System.out.println("The wait is over, lets complete Generate Bottom!");
            
            return edges;
//            generateBottomEdge();
//            
//            if(Thread.currentThread().isInterrupted() && this.done)
//            {
////                System.out.println("Bottom interupted");
//                break;
//            }
//        }        
    }
    
    /*
    @Override
    public void run()
    {
//        while(true)
//        {
            System.out.println("Generate Bottom Fractel...");
            generateBottomEdge();
            System.out.println("Generate Bottom has finished its work... waiting for others...");
            try {
                    cyclicBarrier.await();
            } catch (InterruptedException e) {
                    System.out.println("Generate Bottom interrupted!");
                    e.printStackTrace();
            } catch (BrokenBarrierException e) {
                    System.out.println("Generate Bottom interrupted!");
                    e.printStackTrace();
            }
            System.out.println("The wait is over, lets complete Generate Bottom!");
//            generateBottomEdge();
//            
//            if(Thread.currentThread().isInterrupted() && this.done)
//            {
////                System.out.println("Bottom interupted");
//                break;
//            }
//        }        
    }
    */
    
    private void drawKochEdge(double ax, double ay, double bx, double by, int n) {
        if (!cancelled) {
            if (n == 1) {
                hue = hue + 1.0f / nrOfEdges;
                Edge e = new Edge(ax, ay, bx, by, Color.hsb(hue*360.0, 1.0, 1.0));
                
                edges.add(e);
                
                //this.setChanged();
                //this.notifyObservers(e);                    
            } else {
                double angle = Math.PI / 3.0 + Math.atan2(by - ay, bx - ax);
                double distabdiv3 = Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay)) / 3;
                double cx = Math.cos(angle) * distabdiv3 + (bx - ax) / 3 + ax;
                double cy = Math.sin(angle) * distabdiv3 + (by - ay) / 3 + ay;
                final double midabx = (bx - ax) / 3 + ax;
                final double midaby = (by - ay) / 3 + ay;
                drawKochEdge(ax, ay, midabx, midaby, n - 1);
                drawKochEdge(midabx, midaby, cx, cy, n - 1);
                drawKochEdge(cx, cy, (midabx + bx) / 2, (midaby + by) / 2, n - 1);
                drawKochEdge((midabx + bx) / 2, (midaby + by) / 2, bx, by, n - 1);
            }
        }
        done = true;
    }
   
    public synchronized void generateBottomEdge() {
        if(!this.done)
        {
            this.hue = 1f / 3f;
            this.cancelled = false;
      
            /*
            ax = ((1 - Math.sqrt(3.0) / 2.0) / 2);
            ay = 0.75;
            bx = (1 + Math.sqrt(3.0) / 2.0) /2;
            by = 0.75;
            n = level;
                */
            
            drawKochEdge((1 - Math.sqrt(3.0) / 2.0) / 2, 0.75, (1 + Math.sqrt(3.0) / 2.0) / 2, 0.75, this.level);  
            kochManager.increaseCount();
        }
    }

    public void cancel() {
        cancelled = true;
    }
    
    public boolean isCancelled()
    {
        return cancelled;
    }
    
    public float getHue()
    {
        return hue;
    }

    public void setLevel(int lvl) {
        level = lvl;
        nrOfEdges = (int) (3 * Math.pow(4, level - 1));
    }

    public int getLevel() {
        return level;
    }

    public int getNrOfEdges() {
        return nrOfEdges;
    }
    
    public boolean getDone()
    {
        return this.done;
    }
}
