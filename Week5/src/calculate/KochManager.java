/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
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
    
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        koch = new KochFractal();
        koch.addObserver(this);
        edges = new ArrayList<>();
    }
    
    public void changeLevel(int nxt) {
        koch.setLevel(nxt);
        edges.clear();
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Start Calc");

        KochFractalLeft kochFractalLeft = new KochFractalLeft(koch.getLevel(), koch.getNrOfEdges(), this);
        Thread threadLeft = new Thread(kochFractalLeft, "LeftFractel");
        kochFractalLeft.addObserver(this);
        
        KochFractalBottom kochFractalBottom = new KochFractalBottom(koch.getLevel(), koch.getNrOfEdges(), this);
        Thread threadBottom = new Thread(kochFractalBottom, "BottomFractel");
        kochFractalBottom.addObserver(this);
        
        KochFractalRight kochFractalRight = new KochFractalRight(koch.getLevel(), koch.getNrOfEdges(), this);
        Thread threadRight = new Thread(kochFractalRight, "RightFractel");
        kochFractalRight.addObserver(this);
        
        threadLeft.start();
        threadBottom.start();
        threadRight.start();
        
        threadLeft.interrupt();
        threadBottom.interrupt();
        threadRight.interrupt();
        
        timeStamp.setEnd("End Calc");

        application.setTextCalc(timeStamp.toString());
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
        
        for(Edge e : edges){
            application.drawEdge(e);
        }
        
        
        
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
            synchronized(this){
               TimeStamp timeStamp = new TimeStamp();
               timeStamp.setBegin("Start Drawing");
               
               application.requestDrawEdges(); 
                
               timeStamp.setEnd("End Drawing");
               application.setTextDraw(timeStamp.toString());
               count = 0;
            }
        }
    }
    
}
