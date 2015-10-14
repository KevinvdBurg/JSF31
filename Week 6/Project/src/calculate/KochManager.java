/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 *
 * @author Kevin van der Burg en Milton van de Sanden
 */
public class KochManager implements Observer{
    
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private ArrayList<Edge> edges;
    public int count = 0;
    private TimeStamp CalctimeStamp;
    CyclicBarrier cyclicBarrier;
    public int size;
    private ExecutorService pool;
    
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        koch = new KochFractal();
        koch.addObserver(this);
        edges = new ArrayList<>();
        CalctimeStamp = new TimeStamp();
        size = 4;
        pool = Executors.newFixedThreadPool(size);
        cyclicBarrier = new CyclicBarrier(size);
    }
    
    public void changeLevel(int nxt) throws BrokenBarrierException {
        koch.setLevel(nxt);
        edges.clear();
        
        CalctimeStamp.setBegin("Start Calc");

        KochFractalLeft kochFractalLeft = new KochFractalLeft(koch.getLevel(), koch.getNrOfEdges(), this, cyclicBarrier);
        
        Thread threadLeft = new Thread(kochFractalLeft, "LeftFractel");
        kochFractalLeft.addObserver(this);
        
        
        KochFractalBottom kochFractalBottom = new KochFractalBottom(koch.getLevel(), koch.getNrOfEdges(), this, cyclicBarrier);
        Thread threadBottom = new Thread(kochFractalBottom, "BottomFractel");
        kochFractalBottom.addObserver(this);
        
        
        KochFractalRight kochFractalRight = new KochFractalRight(koch.getLevel(), koch.getNrOfEdges(), this, cyclicBarrier);
        Thread threadRight = new Thread(kochFractalRight, "RightFractel");
        kochFractalRight.addObserver(this);
        
        
        System.out.println("Starting both the services at"+new Date());
//        pool.execute(kochFractalLeft);
//        pool.execute(kochFractalBottom);
//        pool.execute(kochFractalRight);
        threadLeft.start();
        threadBottom.start();
        threadRight.start();
        
        try {
                cyclicBarrier.await();
        } catch (InterruptedException e) {
                System.out.println("Main Thread interrupted!");
                e.printStackTrace();
        } catch (BrokenBarrierException e) {
                System.out.println("Main Thread interrupted!");
                e.printStackTrace();
        }
        System.out.println("Ending both the services at"+new Date());

        pool.shutdown();
//        threadLeft.interrupt();
//        threadBottom.interrupt();
//        threadRight.interrupt();
        
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
        

        
        
        /*
        koch.setLevel(nxt);
        edges.clear();
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Start Calc");
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();
        timeStamp.setEnd("End Calc");      
        drawEdges();
        application.setTextCalc(timeStamp.toString());
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
                */
    }
    
    public synchronized void drawEdges() {
        application.clearKochPanel();
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Start Drawing");
        for(Edge e : edges){
            application.drawEdge(e);
        }
        timeStamp.setEnd("End Drawing");
        application.setTextDraw(timeStamp.toString());
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        edges.add((Edge)arg);
        //application.drawEdge((Edge)arg);
    }
    
    public synchronized void increaseCount(){
        count++;
        System.out.print(count);
        
        if (count == 3)
        {
            CalctimeStamp.setEnd("End Calc");
            application.setTextCalc(CalctimeStamp.toString());
            application.requestDrawEdges();  
            count = 0;
        }
    }
    
}
