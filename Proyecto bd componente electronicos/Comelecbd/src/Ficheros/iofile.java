/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ficheros;

import comelecbd.CRUD;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tablasvo.t_datasheet;

/**
 *
 * @author HECTOR
 */
public class iofile {
    
    
    public int cuenta_lineas_ficheros(String lee) throws FileNotFoundException, IOException
    {
        String cadena = new String();
         int contador =0;
         
          BufferedReader leer_fichero  = new BufferedReader(new FileReader(lee));
           cadena = leer_fichero.readLine();
           contador++;
         while (cadena !=null)
         {cadena = leer_fichero.readLine();
          contador++;}
         leer_fichero.close();
     return contador;}
    
    public ArrayList<String> listado_datasheet  (String directorio) 
     {int i=0;
      t_datasheet datasheet = new t_datasheet();
     int tam_num_ficheros =0;
     String cad = new String();
     File fichero = new File(directorio);
        tam_num_ficheros=fichero.list().length;
        String[] num_ficheros = new String[tam_num_ficheros];
        num_ficheros = fichero.list();
        ArrayList<String> list =   new ArrayList();
   
      for(i=0;i<tam_num_ficheros;i++)  
       { list.add(num_ficheros[i]);}
      
      for(i=0;i<tam_num_ficheros;i++)  
       { cad =list.get(i);
         if(cad.length() == datasheet.getTa_datasheet()[4]-5)
          {list.remove(i);
           tam_num_ficheros=list.size();
           i--;}
            }
      
   return list;}      
    
    
    
    
    
    
    
    
    
    
    
}
