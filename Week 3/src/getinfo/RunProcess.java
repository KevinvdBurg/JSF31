/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getinfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import timeutil.TimeStamp;

/**
 *
 * @author jsf3
 */
public class RunProcess implements Runnable{
    private String command;
    public Process p;
    public RunProcess(String command)
    {
        this.command = command;
    }
    public void run()
    {   
        try {
            p = Runtime.getRuntime().exec(this.command);
            System.out.println("running " + this.command);
            p.destroy();
            System.out.println("closed " + this.command);            
        } catch (Exception e){
            System.out.println(command + " not recognised");
        }
    }
}