/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getinfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import timeutil.TimeStamp;




/**
 *
 * @author jsf3
 */
public class GetInfo { 
    public static void main(String[] args) {
        TimeStamp ts = new TimeStamp();
        ts.setBegin("All");
        if(args.length != 0){
            for( int i = 0; i < args.length; i++)
            {
                switch (args[i]) {
                    case "info":                        
                        SystemInfo();
                        break;
                    case "loop":{
                        SystemInfo();
                        HelloVaak( );
                        break;
                        }
                    case "loopgc":{
                        SystemInfo();
                        String s;
                        System.out.println("/----------------- Running Loop -----------------\\" );
                        for (int j = 0; j < 1000000; j++) {
                            s = "Hello"+j;
                            //System.out.println(s);
                        }       System.out.println("\\----------------- Running Loop -----------------/" );
                            RunGC();
                            SystemInfo();
                            break;
                        }
                    /*case "firefox":
                        RunProgress();
                        SystemInfo();
                        break; */
                    case "command":
                        InputRunProgress();
                        SystemInfo();
                        break;
                    case "all":{
                            SystemInfo();
                            HelloVaak( );
                            RunGC();
                            SystemInfo();
                            RunProgress();
                            SystemInfo();
                            InputRunProgress();
                            SystemInfo();
                            break;
                        }
                    case "help":
                        System.out.println("----------------- Mogelijkheden -----------------" );
                        System.out.println("info");
                        System.out.println("loop");
                        System.out.println("loopgc");
                        System.out.println("firefox");
                        System.out.println("command");
                        System.out.println("all");
                        System.out.println("help");
                        break;
                    default:
                        //System.out.println("help voor alle opties" );
                        //System.out.println(args[0]);
                        
                        RunProcess runProcess = new RunProcess(args[i]);
                        Thread thread = new Thread(runProcess);
                        thread.start();
                        while (runProcess.p.isAlive())
                        {
                            
                        }
                        thread.interrupt();
                        break;
                }
            }
             
        }
        else{
            System.out.println("help voor alle opties" );
        }
        ts.setEnd("End All");
        
        System.out.println(ts.toString());
        
    }
    
    public static void HelloVaak() {
        String s;
        System.out.println("/----------------- Running Loop -----------------\\" );
        for (int j = 0; j < 1000000; j++) {
            s = "Hello"+j;
            //System.out.println(s);
        }       System.out.println("\\----------------- Running Loop -----------------/" );
        SystemInfo();
    }
    
    public static void SystemInfo(){
        Runtime runTime = Runtime.getRuntime();
        long maxMemory = runTime.maxMemory();
        long allocatedMemory = runTime.totalMemory();
        long freeMemory = runTime.freeMemory();
        System.out.println("/----------------- Systeem Info -----------------\\" );
        System.out.println("Cores: " + runTime.availableProcessors());
        System.out.println("Max Memory: " + runTime.maxMemory());
        System.out.println("Total Memory: " + runTime.totalMemory());
        System.out.println("Free Memory: " + runTime.freeMemory());
        System.out.println("Total Free Memory: " + freeMemory + (maxMemory - allocatedMemory));
        System.out.println("\\----------------- Systeem Info -----------------/" );
    }
    
    public static void RunGC(){
        Runtime runTime = Runtime.getRuntime();
        System.out.println("/----------------- Garbage Collerctor -----------------\\" );
        System.out.println("----------------- Running -----------------" );
        runTime.gc();
        System.out.println("\\----------------- Garbage Collerctor -----------------/" );
    }
    
    public static void RunProgress(){
        System.out.println("/----------------- Start Firefox -----------------\\" );
        Runtime runTime = Runtime.getRuntime();
        TimeStamp ts = new TimeStamp();
        ts.setBegin("FireFox");
        try {
            Process process = runTime.exec("firefox");
            Thread.sleep(6000);
            process.destroy();
        } catch (Exception e) {
            
        }
        ts.setEnd("....");
        System.out.println("\\----------------- Stop Firefox - Time: "+ ts.toString() +"-----------------/" );
    }
    
    public static void InputRunProgress(){
        System.out.println("/----------------- Start Input Command -----------------\\" );
        Runtime runTime = Runtime.getRuntime();
        
        TimeStamp ts = new TimeStamp();
        
        ts.setBegin("Process");
        try {
            Process process = runTime.exec("pwd");
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            
            String line;
            
            while( (line = bufferedReader.readLine()) != null){
                System.out.println(line);
                }
            bufferedReader.close();
            Thread.sleep(6000);
            process.destroy();
            }
        
        catch (Exception e) {
            
        }
        ts.setEnd("....");
        System.out.println("\\----------------- Stop Input Command - Time: "+ ts.toString() +"-----------------/" );
    }
}

