/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht.pkg4.pkg1.calculate;

import calculate.Edge;
import calculate.KochFractal;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author kvdb
 */
public class Opdracht41Calculate implements Observer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        Opdracht41Calculate op = new  Opdracht41Calculate();
        
        KochFractal kochFractal = new KochFractal();
        kochFractal.addObserver(op);   
        kochFractal.setLevel(2);
        kochFractal.generateBottomEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateRightEdge();
    }    

    @Override
    public void update(Observable o, Object arg) {
        Edge edge = (Edge) arg;
        System.out.println("X1:" + edge.X1 +"Y1:"+ edge.Y1 +" - X2:"+ edge.X2 +" Y2:"+ edge.Y2 +"\r\n");
        
    }
}