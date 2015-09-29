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
 * @author kvdb
 */
public class KochManager implements Observer{
    
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private ArrayList<Edge> edges;
    
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
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();
        timeStamp.setEnd("End Calc");      
        drawEdges();
        application.setTextCalc(timeStamp.toString());
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
    }
    
    public void drawEdges() {
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
    public void update(Observable o, Object arg) {
        //application.drawEdge((Edge)arg);
        edges.add((Edge)arg);
    }

}
