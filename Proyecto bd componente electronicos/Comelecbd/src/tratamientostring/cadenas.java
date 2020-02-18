/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tratamientostring;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import tablasvo.t_clase;
import tablasvo.t_datasheet;
import tablasvo.t_empaquetado;
import tablasvo.t_estados;
import tablasvo.t_movimientos;

/**
 *
 * @author HECTOR
 */
public class cadenas {
    
    
    public boolean comparacadena(String cadena1)
    { 
      char [] car_cadena1 = cadena1.toCharArray();
      int tamtoken = car_cadena1.length;
      String compara = new String();
      int ciertos=0;
     
       int i=0;
       for (i=0;i<tamtoken;i++)
       { compara = Character.toString(car_cadena1[i]);
         if (esnumero(compara)==true)
         {ciertos++;}}
      if (ciertos==tamtoken)
         {return true;}
         else{return false;}
     }     
    
    
    public String unstring(String token, String caracter)
    {
      char [] cadena_caracter = token.toCharArray();
      String temp = new String();
      String car= new String();
      int tam = cadena_caracter.length;
      int i=0;
      for(i=0;i<tam;i++)
      { car = Character.toString(cadena_caracter[i]);
          if(car.equals(caracter)==false)
          { if (i==0)
             {temp=car;}
          else{
                temp = temp +car;}}}  
      return temp;
    
    }
    
    
    
   public String[][] desenpaquetatabla(String []tabla,int []tam_campos)
     {int num_registro = tabla.length;
      int num_elementos = tam_campos.length;
   
      int puntero=0;
      int i=0;
       int j=0;
       String cad = new String();
      // String []cadena = new String[1];
       String[][] temp_2d = new String[num_registro][num_elementos]; 
      
       for(i=0;i<num_registro;i++)
        {for(j=0;j<num_elementos;j++)
          {if (j==0)
            {temp_2d[i][j]=tabla[i].substring(puntero,tam_campos[j]);
              puntero= puntero + tam_campos[j];}
            else {
              temp_2d[i][j]=tabla[i].substring(puntero,tam_campos[j]+puntero);
              puntero= puntero + tam_campos[j];}}} 
   
        for(i=0;i<num_registro;i++)
         {for(j=0;j<num_elementos;j++)
           {  if (j!=num_elementos-1)
               {if(esnumero(temp_2d[i][j])==true)
                 {puntero=Integer.parseInt(temp_2d[i][j]);
                  temp_2d[i][j]=Integer.toString(puntero);} 
                 else if (temp_2d[i][j]!=null)
                   {         
                      cad= temp_2d[i][j];
                       cad = unstring(cad,"*");      
                       temp_2d[i][j]=cad;}}}}
    return temp_2d;}  
    
   public String [][] act_tra_preelim(String[][]arrayneta,int [] ind_duplicados,int ultregistro,int campotabla)
    {
      int in_dup= ind_duplicados.length;
      int tam_x=arrayneta.length;
      int tam_y=arrayneta[0].length;
      String [][] temp=new String [tam_x][tam_y]; 
      int in=0;
      int x =0;
      int y=0;
      
       for(x=0;x<in_dup;x++)
        {if(ind_duplicados[x]!= -1)
          {for(y=0;y<tam_y;y++)
            {in =ind_duplicados[x];
             arrayneta[in][y]=null; }}}
      
      for(x=0;x<tam_x;x++)
      { if(arrayneta[x][0]!=null)
         { for(y=0;y<tam_y;y++)
            {temp[x][y]= arrayneta[x][y];}
             temp[x][0]= cero_izq(x+ultregistro,campotabla);}}
          return temp;   
    } 
   
    public String[] concatpreeli(String [][] valor) 
    {int tamx = valor.length;
     int tamy =valor[0].length;
     String[] cadena = new String[tamx];
     String token = new String();
     int ind_array=0;
     int columy =0;
      while(true)
        { if(valor[ind_array][0]!= null)
            { for(columy=0;columy <tamy;columy++)
                { if(columy==0)
                   {token = valor[ind_array][columy];
                     cadena[ind_array]=token;}
                else{token = valor[ind_array][columy];
                     cadena[ind_array]= cadena[ind_array]+token;}}}
          else {break;}
        ind_array++;
        }
    
    return cadena; } 
    
 public boolean esnumero(String valor)
 {char caracter;
  if (valor.length()>0)  
   {caracter=valor.charAt(0);
     return Character.isDigit(caracter);}
     else {return false;}}
    
   public String cero_izq(int valor, int tamaño_campo)
    { String cadena = new String();
      String cadena1 = new String();
      int i =0;
       cadena1 = Integer.toString(valor);
      int tamaño_cadena1 = cadena1.length();
      if ( tamaño_cadena1 <=tamaño_campo)
       { for (i=0;i<tamaño_campo-tamaño_cadena1;i++)
         {cadena = cadena + "0";}
         cadena= cadena + cadena1;
         return cadena;}
      else {return null;}}    
    
    public String cero_izq(String valor, int tamaño_campo)
    { String cadena = new String();
      String cadena1 = new String();
      int i =0;
      cadena1 = valor;
      int tamaño_cadena1 = cadena1.length();
      if ( tamaño_cadena1 <=tamaño_campo)
       { for (i=0;i<tamaño_campo-tamaño_cadena1;i++)
         {cadena = cadena + "0";}
         cadena= cadena + cadena1;
         return cadena;}
      else {return null;}}    
    
     public String aster_izq(String valor, int tamaño_campo)
    { String cadena = new String();
      String cadena1 = new String();
      int i =0;
      cadena1 = valor;
      int tamaño_cadena1 = cadena1.length();
      if ( tamaño_cadena1 <=tamaño_campo)
       { for (i=0;i<tamaño_campo-tamaño_cadena1;i++)
         {cadena = cadena + "*";}
         cadena= cadena + cadena1;
         return cadena;}
      else {return null;}} 
           
  public String [][] matrizlimpia(ArrayList lista1)
    {  int filasx =0;
        int columnasy=0;
        int tamaño_lista=0;
        int num_elementos=0;
        int ind_array=0;
        
        ArrayList lista =new ArrayList();
        tamaño_lista= lista.size();
    
        lista=lista1;
         tamaño_lista= lista.size();
         String []arraybruta= new String[tamaño_lista];
       
        for (filasx=0;filasx<tamaño_lista;filasx++)
        {arraybruta[filasx]=(String)lista.get(filasx);}
        
        num_elementos=arraybruta[0].split(";").length;
        String [][]arrayneta= new String[tamaño_lista][num_elementos];
        String[] cadena = new String[num_elementos];
        filasx=0;
        ind_array=0;
         for (filasx=0;filasx<tamaño_lista;filasx++)
           {cadena = arraybruta[filasx].split(";");
            if(cadena.length!= 0)
              {for(columnasy=0;columnasy <num_elementos;columnasy++)
                {arrayneta[ind_array][columnasy]=cadena[columnasy];}
                 ind_array++;
              // System.out.println("dentro del segundo for (ind_array)" + ind_array);
             //  System.out.println("dentro del segundo for (filax)" + filasx);
              }
          // System.out.println("dentro del primero (ind_array) for" + ind_array);
         //      System.out.println("dentro del primero(filax) for" + filasx);
           
           }
         
    return arrayneta;  
    }
  
   public String[][] tra_preeliminar(String arrayneta[][],int []tamcapos)
    {
     Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
        String cadena= new String(); 
        int ind_array=0;
       int columnasy=0;
       int filasx=0;
       int tamaño_lista1 = arrayneta.length;
       int num_elementos1= arrayneta[0].length;
        
       String [][]preeliminar= new String[tamaño_lista1][num_elementos1+2];
       
        while(true)
        {
            if(arrayneta[ind_array][0]!= null)
              {filasx=1;
               for(columnasy=0;columnasy <num_elementos1;columnasy++)
                {if (arrayneta[ind_array][columnasy]!=null)
                  {if (esnumero(arrayneta[ind_array][columnasy])==true)
                   {cadena= cero_izq(arrayneta[ind_array][columnasy],tamcapos[columnasy+1]);
                    preeliminar[ind_array][filasx]=cadena;}
                  else {cadena= aster_izq(arrayneta[ind_array][columnasy],tamcapos[columnasy+1]);
                      preeliminar[ind_array][filasx]=cadena;}}
                 filasx++;}
        
            preeliminar[ind_array][0]= cero_izq(ind_array,tamcapos[0]);
            preeliminar[ind_array][num_elementos1+1]= formatfecha.format(fecha);
            ind_array++;}
            
            else {break;}
             }
       
    return preeliminar;}
  
   public String[][] tra_movimientos(String preeliminar[][],int ultimoregistro)
    {
     int ind_array=0;
     int columnasy=0;
     int tamaño_lista1 = preeliminar.length;
     String [][]movimientos= new String[tamaño_lista1][6];
        
      while(true)
        { if (preeliminar[ind_array][0]!=null)
             { movimientos[ind_array][0]=cero_izq(ind_array + ultimoregistro,6);
               movimientos[ind_array][1]= cero_izq("8",2); //codigo tabla
               movimientos[ind_array][2]= aster_izq("vacio",72); //valor anterior
               movimientos[ind_array][3]= preeliminar[ind_array][5]; //Fecha modificacion
               movimientos[ind_array][4]= cero_izq("0",2); //Tipo modificacion
               movimientos[ind_array][5]= aster_izq("Esto es una prueba",20); //Descripcion de la tabla
               ind_array++;}
          else {break;}
         
         }
    return movimientos;
    }
   
   public String[][] tra_movimientosb(String preeliminar[][],int ultimoregistro,int codtabla,int codmod)
    {
     int ind_array=0;
     int columnasy=0;
     int tamaño_lista1 = preeliminar.length;
     String [][]movimientos= new String[tamaño_lista1][6];
       Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");  
      while(true)
        { if (preeliminar[ind_array][0]!=null)
             { movimientos[ind_array][0]=cero_izq(ind_array + ultimoregistro,6);
               movimientos[ind_array][1]= cero_izq(codtabla,2); //codigo tabla
               movimientos[ind_array][2]= aster_izq("vacio",72); //valor anterior
               movimientos[ind_array][3]= formatfecha.format(fecha); //Fecha modificacion
               movimientos[ind_array][4]= cero_izq(codmod,2); //Tipo modificacion
               movimientos[ind_array][5]= aster_izq("Esto es una prueba",20); //Descripcion de la tabla
               ind_array++;}
          else {break;}
         
         }
    return movimientos;
    }
   
   
   
   
   
     
   
   public String[] formatoSQL(String [][] preeliminar)
    {
     int tamaño_lista1 = preeliminar.length;
      int tam_listay1 = preeliminar[0].length;
      int ind_array=0;
      int columnasy=0;
      
      String [] tablavalues= new String[tamaño_lista1] ;
            
        while(true)
         { if(preeliminar[ind_array][0]!=null) 
            { tablavalues[ind_array] = "(";
              for(columnasy=0;columnasy <tam_listay1;columnasy++)
               {if(columnasy !=tam_listay1-1 )
                 {tablavalues[ind_array]= tablavalues[ind_array]+ "'" + preeliminar[ind_array][columnasy] + "',";}
                else if (columnasy ==tam_listay1-1)
                   {tablavalues[ind_array]=tablavalues[ind_array]+"'" + preeliminar[ind_array][columnasy] + "')";}}
             ind_array++;}
            else {break;}}
    
    return tablavalues;
    }
     
 public String[][] tra_movimientosb(String preeliminar[],int ultimoregistro,String codtabla,String codmod,String mensaje)
    {
     int ind_array=0;
     int columnasy=0;
     t_movimientos movi = new t_movimientos();
     Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
     int tamaño_lista1 = preeliminar.length;
     String [][]movimientos= new String[tamaño_lista1+1][6];

       movimientos[ind_array][0]=cero_izq(ind_array + ultimoregistro,movi.getTa_movimientos()[0]);
       movimientos[ind_array][1]= cero_izq(codtabla,movi.getTa_movimientos()[1]); //codigo tabla
       movimientos[ind_array][2]= aster_izq(preeliminar[0],movi.getTa_movimientos()[2]); //valor anterior
       movimientos[ind_array][3]= formatfecha.format(fecha); //Fecha modificacion
       movimientos[ind_array][4]= cero_izq(codmod,movi.getTa_movimientos()[4]); //Tipo modificacion
       movimientos[ind_array][5]= aster_izq(mensaje,movi.getTa_movimientos()[5]); //Descripcion de la tabla
           
    return movimientos;
    }   
  
  public String[] formatoupdateSQL(String [][] preeliminar)
    {
     int tamaño_lista1 = preeliminar.length;
      int tam_listay1 = preeliminar[0].length;
      int ind_array=0;
      int columnasy=0;
      
      String [] tablavalues= new String[tamaño_lista1] ;
            
        while(true)
         { if(preeliminar[ind_array][0]!=null) 
            { tablavalues[ind_array] = "(";
              for(columnasy=0;columnasy <tam_listay1;columnasy++)
               {if(columnasy !=tam_listay1-1 )
                 {tablavalues[ind_array]= tablavalues[ind_array]+ "'" + preeliminar[ind_array][columnasy] + "',";}
                else if (columnasy ==tam_listay1-1)
                   {tablavalues[ind_array]=tablavalues[ind_array]+"'" + preeliminar[ind_array][columnasy] + "')";}}
             ind_array++;}
            else {break;}}
    
    return tablavalues;
    }
 
   public String[][] tra_preeliminar_datasheet(String arrayneta[][],int []tamcapos)
    {
     Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
        String cadena= new String(); 
        int ind_array=0;
       int columnasy=0;
       int filasx=0;
       int tamaño_lista1 = arrayneta.length;
       int num_elementos1= arrayneta[0].length;
        
       String [][]preeliminar= new String[tamaño_lista1][num_elementos1+3];
       
        while(true)
        {
            if(arrayneta[ind_array][0]!= null)
              {filasx=2;
               for(columnasy=0;columnasy <num_elementos1;columnasy++)
                {if (arrayneta[ind_array][columnasy]!=null)
                  {if (esnumero(arrayneta[ind_array][columnasy])==true)
                   {cadena= cero_izq(arrayneta[ind_array][columnasy],tamcapos[columnasy+1]);
                    preeliminar[ind_array][filasx]=cadena;}
                  else {cadena= aster_izq(arrayneta[ind_array][columnasy],tamcapos[columnasy+1]);
                      preeliminar[ind_array][filasx]=cadena;}}
                 filasx++;}
        
            preeliminar[ind_array][0]= cero_izq(ind_array,tamcapos[0]);
            preeliminar[ind_array][1]= cero_izq(ind_array,tamcapos[1]);
            preeliminar[ind_array][num_elementos1+1]= formatfecha.format(fecha);
            ind_array++;}
            
            else {break;}
             }
       
    return preeliminar;}
 
   public ArrayList<String> quita_repetidos(ArrayList<String> lista)
   {
      // ArrayList<String> temp_lista = new ArrayList<String>();
       int tamaño_lista=0;
       int i=0;
       String puntero1= new String();
       String puntero2=new String();
                
       Collections.sort(lista);
       tamaño_lista=lista.size();
       
       for (i=0;i<tamaño_lista;i++)
        {if (i+1 !=tamaño_lista)
          {puntero1=lista.get(i);
           puntero2 = lista.get(i+1);
           
           if (puntero1.equals(puntero2)==true)
            {lista.remove(i);
             tamaño_lista=lista.size();
              i--;}}}
      
   return lista;
   }
   
    public String[] concatpreeli(String [][] valor,int primer_registro,int quitaultregistro) 
    {int tamx = valor.length;
     int tamy =valor[0].length;
     String[] cadena = new String[tamx];
     String token = new String();
     int ind_array=0;
     int columy =0;
      while(true)
        { if(valor[ind_array][0]!= null)
            { for(columy=primer_registro;columy <tamy-quitaultregistro;columy++)
                { if(columy==primer_registro)
                   {token = valor[ind_array][columy];
                     cadena[ind_array]=token;}
                else{token = valor[ind_array][columy];
                     cadena[ind_array]= cadena[ind_array]+token;}}}
          else {break;}
        ind_array++;
        }
        return cadena; } 
    
    public String[] tra_datasheet(ArrayList<String> lista , String dire)
    {
     Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
      
        t_datasheet datasheet = new t_datasheet();
        int i =0;
        int tamaño_lista=lista.size();
        int tamaño_datasheet=datasheet.getTa_datasheet().length;
        String [][]temp_mat = new String[tamaño_lista][tamaño_datasheet]; 
        
        for (i=0;i<tamaño_lista;i++)
        {
            temp_mat[i][0]=cero_izq(i,datasheet.getTa_datasheet()[0]);
            temp_mat[i][1]=cero_izq(0,datasheet.getTa_datasheet()[1]);
            temp_mat[i][2]=aster_izq("vacio",datasheet.getTa_datasheet()[2]);
            temp_mat[i][3]=aster_izq(dire+"\\"+lista.get(i),datasheet.getTa_datasheet()[3]);
            temp_mat[i][4]=aster_izq(lista.get(i),datasheet.getTa_datasheet()[4]); 
            temp_mat[i][5]=aster_izq(formatfecha.format(fecha),datasheet.getTa_datasheet()[5]); 
        }
        
      return formatoSQL2(temp_mat) ;
    }
        
     
   public String[] tra_movimientos2(int ultimoregistro ,int tamaño_lista)
      {
      int i =0;
     Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
    
     String [][]movimientos= new String[tamaño_lista][6];
        
      for (i=0;i<tamaño_lista;i++)
        {  movimientos[i][0]=cero_izq(i + ultimoregistro,6);
               movimientos[i][1]= cero_izq("6",2); //codigo tabla
               movimientos[i][2]= aster_izq("vacio",72); //valor anterior
               movimientos[i][3]= formatfecha.format(fecha); //Fecha modificacion
               movimientos[i][4]= cero_izq("0",2); //Tipo modificacion
               movimientos[i][5]= aster_izq("Esto es una prueba",20); //Descripcion de la tabla
               }
              
     return formatoSQL2(movimientos) ;
    }
       
  public String[] formatoSQL2(String [][] preeliminar)
    {
     int tamaño_lista1 = preeliminar.length;
      int tam_listay1 = preeliminar[0].length;
      int ind_array=0;
      int columnasy=0;
      int i =0;
      
      String [] tablavalues= new String[tamaño_lista1] ;
            
        for (i=0;i<tamaño_lista1;i++)
         { tablavalues[i] = "(";
              for(columnasy=0;columnasy <tam_listay1;columnasy++)
               {if(columnasy !=tam_listay1-1 )
                 {tablavalues[i]= tablavalues[i]+ "'" + preeliminar[i][columnasy] + "',";}
                else if (columnasy ==tam_listay1-1)
                   {tablavalues[i]=tablavalues[i]+"'" + preeliminar[i][columnasy] + "')";}}
            }
        
    return tablavalues;
    }      
       
   public String[][] tra_T_altaclase(String preeliminar,int ultimoregistro)
    {
         Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
         t_clase clase= new t_clase();
        String [] elementos= preeliminar.split("[=\\'\\ ]");
        int tam_clase = clase.getTa_clase().length;
        
     
        String [][]tabla= new String[2][tam_clase];
            
              tabla[0][0]=cero_izq(ultimoregistro,clase.getTa_clase()[0]);
               tabla[0][1]= aster_izq("modificar",clase.getTa_clase()[1]); 
               tabla[0][2]= elementos[2]; 
               tabla[0][3]= elementos[7]; 
               tabla[0][4]= formatfecha.format(fecha); 
              
    return tabla;
    }    
       
    public String[][] tra_T_altaestado(String preeliminar,int ultimoregistro)
    {
         Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
         t_estados estado= new t_estados();
        String [] elementos= preeliminar.split("[=\\'\\ ]");
        int tam_clase = estado.getTa_estados().length;
        
       String [][]tabla= new String[2][tam_clase];
            
              tabla[0][0]=cero_izq(ultimoregistro,estado.getTa_estados()[0]);
              tabla[0][2]= elementos[1]; 
              tabla[0][2]= formatfecha.format(fecha); 
              
    return tabla;
    }           
    
    public String[][] tra_T_altapaquete(String preeliminar,int ultimoregistro)
    {
         Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
        t_empaquetado paquete = new t_empaquetado();
        String [] elementos= preeliminar.split("[=\\'\\ ]");
        int tam_clase = paquete.getTa_empaquetado().length;
        
        String [][]tabla= new String[2][tam_clase];
            
              tabla[0][0]=cero_izq(ultimoregistro,paquete.getTa_empaquetado()[0]);
              tabla[0][1]= elementos[2];
               tabla[0][2]= aster_izq("modificar",paquete.getTa_empaquetado()[2]); 
               tabla[0][3]= elementos[6]; 
               tabla[0][4]= aster_izq("modificar",paquete.getTa_empaquetado()[4]); 
               tabla[0][5]= formatfecha.format(fecha); 
              
    return tabla;
    }    
    
    public String[][] tra_T_altadatasheet(String preeliminar,int ultimoregistro,int codigo_prin)
    {
         Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
        t_datasheet datasheet = new t_datasheet();
        int tam_elemento=0;
      //  String [] elementos= preeliminar.split("[=\\'\\ ]");
        String [] elementos= new String[2];
        elementos=unstringSQL(preeliminar,2);
        
        int tam_clase = datasheet.getTa_datasheet().length;
        tam_elemento=elementos.length;
        String [][]tabla= new String[2][tam_clase];
            
              tabla[0][0]=cero_izq(ultimoregistro,datasheet.getTa_datasheet()[0]);
              tabla[0][1]= cero_izq(codigo_prin,datasheet.getTa_datasheet()[1]);
              tabla[0][2]= elementos[0]; 
              tabla[0][3]= elementos[1] ;
              tabla[0][4]= aster_izq("modificar",datasheet.getTa_datasheet()[4]); 
              tabla[0][5]= formatfecha.format(fecha); 
              
    return tabla;
    }    
   
  public String[] unstringSQL(String token, int valores)
    {
      char [] cadena_caracter = token.toCharArray();
      String temp = new String();
      String [] valor= new String[valores];
      String car= new String();
      int con_apostrofes=0;
      int tam = cadena_caracter.length;
      int i=0;
      int j=0;
      
       for(i=0;i<valores;i++)
       { valor[i]="?";}
      
      for(i=0;i<tam;i++)
      { car = Character.toString(cadena_caracter[i]);
         if(car.equals("'")==true)
          { con_apostrofes++;}
          
         if(con_apostrofes==1)
          { valor[j]=valor[j]+car;}
          else if (con_apostrofes==2)
                {j++;
                 con_apostrofes=0;} }  
    
      for(i=0;i<valores;i++)
       { valor[i]=unstring(valor[i],"?");
         valor[i]=unstring(valor[i],"'");
       
       }  
      
      return valor;
    
    }
    
    
}