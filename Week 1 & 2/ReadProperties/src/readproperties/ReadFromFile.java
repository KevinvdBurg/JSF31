/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readproperties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;

/**
 *
 * @author jsf3
 */
public class ReadFromFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try 
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/jsf3/NetBeansProjects/JavaEnvironmentVariable/dist/Variables.prop"));
            String line;

            while((line = bufferedReader.readLine()) != null)
            {
                System.out.println(line);
            }

        } 
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
        
    }
    
}
