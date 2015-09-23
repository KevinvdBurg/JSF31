/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaenvironmentvariable;

import java.io.FileOutputStream;
import java.io.File;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author jsf3
 */
public class WriteToFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try 
        {
            String variable = System.getenv("TestEnviron");
            System.out.println("TestEnviron: " + variable);
            variable = "TestEnviron=" + variable;

            Properties properties = new Properties();

            File file = new File("Variables.prop");
            FileOutputStream fileout = new FileOutputStream(file);
            properties.store(fileout, variable);
            fileout.close();

        } 
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
        
       
    }
    
}
