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
                RunProcess runProcess = new RunProcess(args[i]);
                Thread thread = new Thread(runProcess);
                thread.start();
                
                while(runProcess.p.isAlive())
                {
                    
                }
                thread.interrupt();
                
                /*
                switch (args[i]) {
                    case "info":
                        RunProcess runProcess = new RunProcess(args[i]);
                        Thread thread = new Thread(runProcess);
                        thread.start();
                        while (runProcess.p.isAlive())
                        {
                            
                        }
                        thread.interrupt();

                        //SystemInfo();
                        break;
                    case "loop":
                        RunProcess runProcess2 = new RunProcess(args[i]);
                        Thread thread2 = new Thread(runProcess2);
                        thread2.start();
                        while (runProcess2.p.isAlive())
                        {
                            
                        }
                        thread2.interrupt();
                        
                        //SystemInfo();
                        //HelloVaak( );
                        break;
                    case "loopgc":
                        RunProcess runProcess3 = new RunProcess(args[i]);
                        Thread thread3 = new Thread(runProcess3);
                        thread3.start();
                        while (runProcess3.p.isAlive())
                        {
                            
                        }
                        thread3.interrupt();
                        break;
                        
                        /*SystemInfo();
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
                        
                    case "firefox":
                        RunProcess runProcess4 = new RunProcess(args[i]);
                        Thread thread4 = new Thread(runProcess4);
                        thread4.start();
                        while (runProcess4.p.isAlive())
                        {
                            
                        }
                        thread4.interrupt();
                        
                        //RunProgress();
                        //SystemInfo();
                        break;
                    case "command":
                        RunProcess runProcess5 = new RunProcess(args[i]);
                        Thread thread5 = new Thread(runProcess5);
                        thread5.start();
                        while (runProcess5.p.isAlive())
                        {
                            
                        }
                        thread5.interrupt();

                        
                        //InputRunProgress();
                        //SystemInfo();
                        break;
                    case "all":{
                        RunProcess runProcess6 = new RunProcess(args[i]);
                        Thread thread6 = new Thread(runProcess6);
                        thread6.start();
                        while (runProcess6.p.isAlive())
                        {
                            
                        }
                        thread6.interrupt();

                        /*
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
                        RunProcess runProcess7 = new RunProcess(args[i]);
                        Thread thread7 = new Thread(runProcess7);
                        thread7.start();
                        while (runProcess7.p.isAlive())
                        {
                            
                        }
                        thread7.interrupt();
                        
                        /*
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
                        System.out.println("help voor alle opties" );
                        System.out.println(args[0]);
                }
                */
            
            }             
        }
        else{
            System.out.println("help voor alle opties" );
        }
        ts.setEnd("End All");
        
        System.out.println(ts.toString());
        
    }

    /*
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
    */
}

