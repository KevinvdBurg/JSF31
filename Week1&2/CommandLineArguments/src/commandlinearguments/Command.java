/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandlinearguments;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author jsf3
 */
public class Command {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            File file = new File("Variables.prop");
            FileOutputStream fileout = new FileOutputStream(file);
        
            for(int i =0;i<args.length;i++)
            {
               
               String variable = args[i] + "=" + args[i+1];
               properties.store(fileout, variable);
               i++;
               System.out.println(variable);
            }
        
        fileout.close();
            
        } 
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
    
         
    
    }
    
}
