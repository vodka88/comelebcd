/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlbd;

import comelecbd.CRUD;
import static controlbd.mani_bd.st;
import tablasvo.t_empaquetado;
import tratamientostring.cadenas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import tablasvo.t_clase;
import tablasvo.t_datasheet;
import tablasvo.t_estados;
import tablasvo.t_movimientos;
import tablasvo.t_principal;

/**
 *
 * @author HECTOR
 */
public class mani_bd {

static Connection cn;
static Statement st;
static ResultSet rs;
  
   public String[][] comprobacion(String tabla1,String [][] valor,int ultregistro,int campotabla)
   {
      cadenas cadena = new cadenas(); 
     String []leidabd =new String[ultregistro];
     int tam = valor.length;
     String []tablapre = new String[tam];
     int []ind_dupli= new int[3*ultregistro];
     
     leidabd= lecturabd(tabla1,2);
     tablapre =cadena.concatpreeli(valor);
     ind_dupli=buscaduplicados(leidabd,tablapre,ultregistro);
     return cadena.act_tra_preelim(valor,ind_dupli,ultregistro,campotabla);
     
     
   
   
   }
  
    public int[] buscaduplicados(String [] leidoBD, String [] valor,int num_registro)
    {
        int tam_x = valor.length;
        int tam_i= leidoBD.length;
       
        int []datos_duplicados = new int[3*num_registro] ;
        String cadenai=new String();
         String cadenax=new String();
        
        int i=0;
        int x=0;
        
        for(i=0;i<3*num_registro;i++)
        { datos_duplicados[i]=-1; }
        
        for (i=0;i<tam_i;i++)
         { cadenai=leidoBD[i];
             for (x=0;x<tam_x;x++)
             {  if (valor[x]!=null)
                 {cadenax=valor[x].substring(6);
                  if(cadenai.equals(cadenax)) 
                   {
                       datos_duplicados[x]=x;}}}}
            
        
        return datos_duplicados;   
    }
   
    public void conectar()
     {try
       {String url = new String ("jdbc:oracle:thin:@localhost:1525:probora");
        cn = DriverManager.getConnection(url,"system","naticris");
        st = cn.createStatement();}
      catch(Exception e )
       {JOptionPane.showMessageDialog(null,"No es posible conectarse a la base de datos \n"+e);}}   
    
    public ArrayList listado  (String directorio) 
   {int i=0;
     int tam_num_ficheros =0;
     File fichero = new File(directorio);
        tam_num_ficheros=fichero.list().length;
        String[] num_ficheros = new String[tam_num_ficheros];
        num_ficheros = fichero.list();
        ArrayList list =   new ArrayList();
   
      for(i=0;i<tam_num_ficheros;i++)  
        { try { 
               String linea = new String("333");
               BufferedReader leer_fichero  = new BufferedReader(new FileReader(directorio+"\\"+ num_ficheros[i]));
               linea = leer_fichero.readLine();         
                while (linea!=null)
                 {list.add(linea);
                  linea = leer_fichero.readLine();}
                leer_fichero.close();}
           catch (FileNotFoundException ex) {Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);}
           catch (IOException ex) {Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);}
          }
   return list;}
    
    public void insertomasivobd(String tabla,String movimientos, String [] movimientosvalues,String [] tablavalues)
    {
      int tam_listay=movimientosvalues.length;
      String result = "INSERT INTO "+tabla+ " VALUES";
      String result1 = "INSERT INTO "+ movimientos+" VALUES";      
      
      try
       {conectar();
        for(int ind_array=0;ind_array <tam_listay;ind_array++)
        {  if (movimientosvalues[ind_array] != null)
            {
            st.execute(result+tablavalues[ind_array]);
             st.execute(result1+movimientosvalues[ind_array]);
            System.out.println(result);
            }
        }
        st.execute(result);
        System.out.println(result);
        cn.close();}
               
     catch(Exception e )
      {//JOptionPane.showMessageDialog(null, result +e);
         System.out.println(result);
         System.out.println(result1);
         JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
                                              
    
    
    }
    
    public String[] lecturabd(String tabla,int columnaaempezar)
    {int cantidadFilas=0;
     int cantidadColumnas=0;
     int y=0;
     String elemento = new String();
    
      try 
      {conectar();
       rs=st.executeQuery("SELECT count(*) FROM "+ tabla);
       if(rs.next())
        { cantidadFilas=rs.getInt("count(*)"); }  
       else{cantidadFilas=0;}
       
       String [] cadenas= new String[cantidadFilas];
       rs=st.executeQuery("SELECT * FROM "+tabla);
       ResultSetMetaData rsMd = rs.getMetaData();
       cantidadColumnas = rsMd.getColumnCount();
      
       
       while(rs.next())
        {for(int i=columnaaempezar;i <= cantidadColumnas;i++)
          {if(i==columnaaempezar)
           {elemento =(String)rs.getObject(i);
           cadenas[y]=elemento;}
          else{elemento =(String)rs.getObject(i);
               cadenas[y]= cadenas[y]+elemento;}}
               y++;}
        rs.close();
        return cadenas;  }
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);
        return null;
        }}
    
     public int numeroregistro(String tabla) throws SQLException
      {int cantidadFilas=0;
         
        try 
          {conectar();
           rs=st.executeQuery("SELECT count(*) FROM "+ tabla);
           if(rs.next())
            {cantidadFilas=rs.getInt("count(*)"); }  
           else{cantidadFilas=0;}
          rs.close();
          }
              
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);}
    return cantidadFilas;
    }
    
     public String[][] primer_registro(String tabla,int [] tam_elementos)
     {
         cadenas cadena = new cadenas();
         //t_empaquetado paquete = new t_empaquetado();
       int cantidadColumnas=0;  
       String elemento=new String();
        
      try 
      {conectar();
             
       rs=st.executeQuery("SELECT * FROM "+tabla+ " WHERE CLAVE='000000'");
       rs.next();
       ResultSetMetaData rsMd = rs.getMetaData();
       cantidadColumnas = rsMd.getColumnCount();
       String[][] linea = new String[0][cantidadColumnas];
        String[] fila = new String[1];
    
      
        for(int i=1;i <= cantidadColumnas;i++)
          { if(i==1)
             {elemento =(String)rs.getObject(i);
              fila[0]=elemento;}
            else{elemento =(String)rs.getObject(i);
               fila[0]= fila[0]+elemento;
               }}
        linea=cadena.desenpaquetatabla(fila,tam_elementos );
        rs.close();
        return linea;}
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);}
       return null;}
     
     
     
     public String[][] siguiente_registro(String tabla,String clave,int [] tam_elementos) throws SQLException
     {
     
      int cantidadColumnas=0;
      String clave_primaria=new String();
      int temp1 =0;
      cadenas cadena = new cadenas();
        String elemento = new String();
        String[] fila = new String[1];
       
     
     try 
      {conectar();
         temp1= Integer.parseInt(clave);
        temp1 = temp1 +1;
        clave_primaria= cadena.cero_izq(temp1,tam_elementos[0]);
        rs=st.executeQuery("SELECT * FROM "+tabla + " WHERE CLAVE='"+ clave_primaria+"'");
        rs.next();
        ResultSetMetaData rsMd = rs.getMetaData();
        cantidadColumnas = rsMd.getColumnCount();
        String[][] linea = new String[0][cantidadColumnas];
             
          for(int i=1;i <= cantidadColumnas;i++)
            {elemento=(String)rs.getObject(i);
             if(i==1)
              {fila[0]=elemento;}
              else{elemento =(String)rs.getObject(i);
                   fila[0]= fila[0]+elemento;}}
        linea =cadena.desenpaquetatabla(fila, tam_elementos);
        rs.close();
       return linea;}
          
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);}
      return null;}     
             
     public String[][] anterior_registro(String tabla,String clave,int [] tam_elementos)
     {
     
      int cantidadColumnas=0;
      String clave_primaria=new String();
      int temp1 =0;
      cadenas cadena = new cadenas();
        String elemento = new String();
        String[] fila = new String[1];
       
     try 
      {conectar();
         temp1= Integer.parseInt(clave);
        temp1 = temp1 -1;
        clave_primaria= cadena.cero_izq(temp1,tam_elementos[0]);
        rs=st.executeQuery("SELECT * FROM "+tabla + " WHERE CLAVE='"+ clave_primaria+"'");
        rs.next();
        ResultSetMetaData rsMd = rs.getMetaData();
        cantidadColumnas = rsMd.getColumnCount();
        String[][] linea = new String[0][cantidadColumnas];
             
          for(int i=1;i <= cantidadColumnas;i++)
            {elemento=(String)rs.getObject(i);
             if(i==1)
              {fila[0]=elemento;}
              else{elemento =(String)rs.getObject(i);
                   fila[0]= fila[0]+elemento;}}
        linea =cadena.desenpaquetatabla(fila, tam_elementos);
        rs.close();
       return linea;}
          
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);}
      return null;}     
         
     public String[][] final_registro(String tabla,int [] tam_elementos)
     {
           int cantidadColumnas=0;
      String clave_primaria=new String();
      int temp1 =0;
      cadenas cadena = new cadenas();
        String elemento = new String();
        String[] fila = new String[1];
       
      try 
      {conectar();
         temp1=numeroregistro(tabla);
        clave_primaria= cadena.cero_izq(temp1-1,tam_elementos[0]);
        rs=st.executeQuery("SELECT * FROM "+tabla + " WHERE CLAVE='"+ clave_primaria+"'");
        rs.next();
        ResultSetMetaData rsMd = rs.getMetaData();
        cantidadColumnas = rsMd.getColumnCount();
        String[][] linea = new String[0][cantidadColumnas];
             
          for(int i=1;i <= cantidadColumnas;i++)
            {elemento=(String)rs.getObject(i);
             if(i==1)
              {fila[0]=elemento;}
              else{elemento =(String)rs.getObject(i);
                   fila[0]= fila[0]+elemento;}}
        linea =cadena.desenpaquetatabla(fila, tam_elementos);
        rs.close();
       return linea;}
          
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);}
      return null;}     
     
     public void insertomasivobd(String movimientos, String [] movimientosvalues)
    {
      int tam_listay=movimientosvalues.length;
   //   String result = "INSERT INTO "+tabla+ " VALUES";
      String result1 = "INSERT INTO "+ movimientos+" VALUES";      
      
      try
       {conectar();
        for(int ind_array=0;ind_array <tam_listay;ind_array++)
        {  if (movimientosvalues[ind_array] != null)
            { st.execute(result1+movimientosvalues[ind_array]);
                }
        }
       cn.close();}
               
     catch(Exception e )
      {  System.out.println(result1);
         JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}}  
     
     public String altasbd (String [][] datos, String tabla, int[] tamclave) throws SQLException
     {  String nombre_movimientos = new String("MOVIMIENTOS");
       int num_regmov =0;
       int num_regtab=0;
       int tamx = datos.length;
       int tamy = datos[0].length;
       String [] tramassql = new String[tamx];
       String [] tramasqlmov =  new String[tamx];
       cadenas cadena = new cadenas();
        //t_empaquetado paquete = new t_empaquetado();
         t_movimientos moviments = new t_movimientos(); 
         int tammove =moviments.getTa_movimientos().length;
       String [][] movimientos =new String[1][tammove];
       String[][] intermedio = new String[tamx][tamy+2];
       
       intermedio = cadena.tra_preeliminar(datos, tamclave);
       num_regmov = numeroregistro(nombre_movimientos);
       num_regtab=numeroregistro(tabla);
       movimientos = cadena.tra_movimientos(intermedio, num_regmov);
       intermedio =comprobacion(tabla,intermedio,num_regtab,tamclave[0]);
       tramassql= cadena.formatoSQL(intermedio);
       tramasqlmov= cadena.formatoSQL(movimientos);
       insertomasivobd(tabla,nombre_movimientos,  tramasqlmov,tramassql);
     
     return null;
     }
     
      public String bajabd (String tabla,String clave, String[][] recupera,String codtabla) throws SQLException
      {   String nombre_movimientos = new String("MOVIMIENTOS");
          int tamx = recupera[0].length;
          t_movimientos moviments = new t_movimientos(); 
          int tammove =moviments.getTa_movimientos().length;
            int num_regmov =0;
            int num_conci=0;
            int num_conci1=0;
            ArrayList<String> lista = new ArrayList();
          String[] cad = new String[tamx];
          String[][] movimiento = new String[tamx][tammove];
          
          cadenas cadena = new cadenas();
          
          num_regmov = numeroregistro(nombre_movimientos);
          cad = cadena.concatpreeli(recupera);
          movimiento = cadena.tra_movimientosb(cad, num_regmov, codtabla,"1","Esto es un mensaje de prueba");
          cad = cadena.formatoSQL(movimiento);
          num_conci1=Integer.parseInt(clave);
          lista=listar_campos_empaquetado("PRINCIPAL",num_conci1,6);
          num_conci = lista.size();
          clave = cadena.cero_izq(clave,6);
         
       if (num_conci==0)
       {   
        String result = "DELETE FROM " +  tabla +" WHERE CLAVE='"+clave+"'";
        
         try
          {conectar();
           st.execute(result);
           cn.close();}
               
          catch(Exception e )
          { System.out.println(result);
           JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
    
           insertomasivobd(nombre_movimientos,cad);
           actualizaindices_empaquetados(tabla, clave);
       }
       
       else {return "Hay "+  Integer.toString(num_conci)+ " en la tabla principal ";}
       
       
      return null;}
      
       public String actualizaindices (String tabla,String clave) throws SQLException
       {   int nuevo=0;
           int i =0;
           int numero_registro = numeroregistro(tabla);
            cadenas cadena = new cadenas();
           String clave_antigua= new String(cadena.cero_izq(clave, 6));
           String clave_nueva= new String();
       //   numero_registro=numero_registro+3;
           nuevo = Integer.parseInt(clave_antigua);
           nuevo=nuevo+1;
           clave_nueva=cadena.cero_izq(nuevo, 6);
            String result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
           
           
             try
              {conectar();
               for (i=nuevo;i<numero_registro+1;i++)
               {  
                clave_nueva=cadena.cero_izq(i, 6);
                result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
                st.execute(result);
                clave_antigua =cadena.cero_izq(i, 6); }
                
       cn.close();}
               
          catch(Exception e )
           { System.out.println(result);
            JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
          
       return null;
       }
     
        public String modificacionbd (String [][] datos, String tabla, int[] tamclave,String primaria,String codtabla,String codmod,String sql) throws SQLException
        {
            
            String nombre_movimientos = new String("MOVIMIENTOS");
       int num_regmov =0;
       int num_regtab=0;
       int tamx = datos.length;
       int tamy = datos[0][0].length();
       String [] tramassql = new String[tamx];
       String [] tramasqlmov =  new String[tamx];
       String []recupera = new String[2];
       cadenas cadena = new cadenas();
        //t_empaquetado paquete = new t_empaquetado();
         t_movimientos moviments = new t_movimientos(); 
         int tammove =moviments.getTa_movimientos().length;
       String[][] movimientos =new String[2][tammove];
       String[][] intermedio = new String[tamx][tamy+2];
       
       intermedio = cadena.tra_preeliminar(datos, tamclave);
       num_regmov = numeroregistro(nombre_movimientos);
       num_regtab=numeroregistro(tabla);
       recupera = lee_linea(tabla,primaria);
       movimientos = cadena.tra_movimientosb(recupera, num_regmov,codtabla,codmod,"esto es una prueba");
 
       intermedio =comprobacion(tabla,intermedio,num_regtab,tamclave[0]);
       if (intermedio != null)
        { try
              {conectar();
                st.execute(sql); }
               
           catch(Exception e )
            {JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}  
           cn.close();
            
            tramasqlmov= cadena.formatoSQL(movimientos);
           insertomasivobd(nombre_movimientos,tramasqlmov);}
       
       else {return null;}
     
     return null;      
      
        }
     public String[] lee_linea(String tabla,String clave) throws SQLException
        {int cantidadColumnas=0;
        cadenas cadena = new cadenas();
        String elemento = new String();
        String[] fila = new String[2];
       
     
     try 
      {conectar();
         
        rs=st.executeQuery("SELECT * FROM "+tabla + " WHERE CLAVE='"+ clave+"'");
        rs.next();
        ResultSetMetaData rsMd = rs.getMetaData();
        cantidadColumnas = rsMd.getColumnCount();
      
        for(int i=2;i <= cantidadColumnas-1;i++)
            {elemento=(String)rs.getObject(i);
             if(i==2)
              {fila[0]=elemento;}
              else{elemento =(String)rs.getObject(i);
                   fila[0]= fila[0]+elemento;}}
      
        rs.close();
       return fila;}
          
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);}
      return null;}     
        
        
     public String altasbd_estado (String [][] datos, String tabla, int[] tamclave) throws SQLException
     {  String nombre_movimientos = new String("MOVIMIENTOS");
       int num_regmov =0;
       int num_regtab=0;
       String linea[]= new String[1];
       linea[0]="VACIO";
       int tamx = datos.length;
       int tamy = datos[0].length;
       String [] tramassql = new String[tamx];
       String [] tramasqlmov =  new String[tamx];
       cadenas cadena = new cadenas();
        //t_empaquetado paquete = new t_empaquetado();
         t_movimientos moviments = new t_movimientos(); 
         int tammove =moviments.getTa_movimientos().length;
       String [][] movimientos =new String[1][tammove];
       String[][] intermedio = new String[tamx][tamy+2];
       
       intermedio = cadena.tra_preeliminar(datos, tamclave);
       num_regmov = numeroregistro(nombre_movimientos);
       num_regtab=numeroregistro(tabla);
       movimientos = cadena.tra_movimientosb(linea, num_regmov,"7","0","esto es una prueba");
       intermedio =comprobacion(tabla,intermedio,num_regtab,tamclave[0]);
       tramassql= cadena.formatoSQL(intermedio);
       tramasqlmov= cadena.formatoSQL(movimientos);
       insertomasivobd(tabla,nombre_movimientos,  tramasqlmov,tramassql);
     
     return null;
     } 
        
        public String bajabd_estado (String tabla,String clave, String[][] recupera,String codtabla) throws SQLException
        {   String nombre_movimientos = new String("MOVIMIENTOS");
          int tamx = recupera[0].length;
          t_movimientos moviments = new t_movimientos(); 
          int tammove =moviments.getTa_movimientos().length;
            int num_regmov =0;
          String[] cad = new String[tamx];
          String[][] movimiento = new String[tamx][tammove];
          
          cadenas cadena = new cadenas();
          
          num_regmov = numeroregistro(nombre_movimientos);
          cad = cadena.concatpreeli(recupera);
          movimiento = cadena.tra_movimientosb(cad, num_regmov, codtabla,"1","Esto es un mensaje de prueba");
          cad = cadena.formatoSQL(movimiento);
        int num_conci1,num_conci =0;
         num_conci1=Integer.parseInt(clave);
          num_conci=listar_campos("PRINCIPAL",num_conci1,6).size();
          clave = cadena.cero_izq(clave,6);
         
       if (num_conci>0)
       {   
        String result = "DELETE FROM " +  tabla +" WHERE CLAVE='"+clave+"'";
        
         try
          {conectar();
           st.execute(result);
           cn.close();}
               
          catch(Exception e )
          { System.out.println(result);
           JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
    
           insertomasivobd(nombre_movimientos,cad);
           actualizaindices_estado(tabla, clave);
       }
       
       else {return "Hay "+  Integer.toString(num_conci)+ " en la tabla principal ";}
       
       
      return null;}
        
        public String modificacionbd_estado (String [][] datos, String tabla, int[] tamclave,String primaria,String codtabla,String codmod,String sql) throws SQLException
        {
            
            String nombre_movimientos = new String("MOVIMIENTOS");
       int num_regmov =0;
       int num_regtab=0;
       int tamx = datos.length;
       int tamy = datos[0][0].length();
       String [] tramassql = new String[tamx];
       String [] tramasqlmov =  new String[tamx];
       String []recupera = new String[2];
       cadenas cadena = new cadenas();
        //t_empaquetado paquete = new t_empaquetado();
         t_movimientos moviments = new t_movimientos(); 
         int tammove =moviments.getTa_movimientos().length;
       String[][] movimientos =new String[2][tammove];
       String[][] intermedio = new String[tamx][tamy+2];
       
       intermedio = cadena.tra_preeliminar(datos, tamclave);
       num_regmov = numeroregistro(nombre_movimientos);
       num_regtab=numeroregistro(tabla);
       recupera = lee_linea(tabla,primaria);
       movimientos = cadena.tra_movimientosb(recupera, num_regmov,codtabla,codmod,"esto es una prueba");
 
       intermedio =comprobacion(tabla,intermedio,num_regtab,tamclave[0]);
       if (intermedio != null)
        { try
              {conectar();
                st.execute(sql); }
               
           catch(Exception e )
            {JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}  
           cn.close();
            
            tramasqlmov= cadena.formatoSQL(movimientos);
           insertomasivobd(nombre_movimientos,tramasqlmov);}
       
       else {return null;}
     
     return null; 
        }     
        
       public String altasbd_datasheet (String [][] datos, String tabla, int[] tamclave) throws SQLException
     {  String nombre_movimientos = new String("MOVIMIENTOS");
       int num_regmov =0;
       int num_regtab=0;
       String linea[]= new String[1];
       linea[0]="VACIO";
       int tamx = datos.length;
       int tamy = datos[0].length;
       String [] tramassql = new String[tamx];
       String [] tramasqlmov =  new String[tamx];
       cadenas cadena = new cadenas();
        //t_empaquetado paquete = new t_empaquetado();
         t_movimientos moviments = new t_movimientos(); 
         int tammove =moviments.getTa_movimientos().length;
       String [][] movimientos =new String[1][tammove];
       String[][] intermedio = new String[tamx][tamy+2];
       
       intermedio = cadena.tra_preeliminar_datasheet(datos, tamclave);
       num_regmov = numeroregistro(nombre_movimientos);
       num_regtab=numeroregistro(tabla);
       movimientos = cadena.tra_movimientosb(linea, num_regmov,"6","0","esto es una prueba");
       intermedio =comprobacion(tabla,intermedio,num_regtab,tamclave[0]); //SUSTITUIR POR COMPROBACION2
       tramassql= cadena.formatoSQL(intermedio);
       tramasqlmov= cadena.formatoSQL(movimientos);
       insertomasivobd(tabla,nombre_movimientos,  tramasqlmov,tramassql);
     
     return null;
     }   
        
         public String bajabd_datasheet (String tabla,String clave, String[][] recupera,String codtabla) throws SQLException
        {   String nombre_movimientos = new String("MOVIMIENTOS");
          int tamx = recupera[0].length;
          t_movimientos moviments = new t_movimientos(); 
          int tammove =moviments.getTa_movimientos().length;
            int num_regmov =0;
            int num_conci1,num_conci =0;
          String[] cad = new String[tamx];
          String[][] movimiento = new String[tamx][tammove];
          
          cadenas cadena = new cadenas();
          
          num_regmov = numeroregistro(nombre_movimientos);
          cad = cadena.concatpreeli(recupera);
          movimiento = cadena.tra_movimientosb(cad, num_regmov, codtabla,"1","Esto es un mensaje de prueba");
          cad = cadena.formatoSQL(movimiento);
          num_conci1=Integer.parseInt(clave);
          num_conci=listar_campos("PRINCIPAL",num_conci1,6).size();
          clave = cadena.cero_izq(clave,6);
         
       if (num_conci>0)
       {   
        String result = "DELETE FROM " +  tabla +" WHERE CLAVE='"+clave+"'";
        
         try
          {conectar();
           st.execute(result);
           cn.close();}
               
          catch(Exception e )
          { System.out.println(result);
           JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
    
           insertomasivobd(nombre_movimientos,cad);
          actualizaindices_DATASHEET(tabla, clave);
       }
       
       else {return "Hay "+  Integer.toString(num_conci)+ " en la tabla principal ";}
       
       
      return null;}

   
       public String altasbd_clase (String [][] datos, String tabla, int[] tamclave) throws SQLException
     {  String nombre_movimientos = new String("MOVIMIENTOS");
       int num_regmov =0;
       int num_regtab=0;
       String linea[]= new String[1];
       linea[0]="VACIO";
       int tamx = datos.length;
       int tamy = datos[0].length;
       String [] tramassql = new String[tamx];
       String [] tramasqlmov =  new String[tamx];
       cadenas cadena = new cadenas();
        //t_empaquetado paquete = new t_empaquetado();
         t_movimientos moviments = new t_movimientos(); 
         int tammove =moviments.getTa_movimientos().length;
       String [][] movimientos =new String[1][tammove];
       String[][] intermedio = new String[tamx][tamy+2];
       
       intermedio = cadena.tra_preeliminar(datos, tamclave);
       num_regmov = numeroregistro(nombre_movimientos);
       num_regtab=numeroregistro(tabla);
       intermedio[0][0]=cadena.cero_izq(num_regtab,tamclave[0]);
       movimientos = cadena.tra_movimientosb(linea, num_regmov,"5","0","esto es una prueba");
       
       if (comprobacion2(tabla,intermedio)== false)
        {
            tramassql= cadena.formatoSQL(intermedio);
         tramasqlmov= cadena.formatoSQL(movimientos);
         insertomasivobd(tabla,nombre_movimientos,  tramasqlmov,tramassql);
         return "Registro grabado";}
       else {
           return "Registro ya existe en la base datos";}
     }   
        
        public String bajabd_clase (String tabla,String clave, String[][] recupera,String codtabla) throws SQLException
        {   String nombre_movimientos = new String("MOVIMIENTOS");
          int tamx = recupera[0].length;
          t_movimientos moviments = new t_movimientos(); 
          int tammove =moviments.getTa_movimientos().length;
            int num_regmov =0;
          String[] cad = new String[tamx];
          String[][] movimiento = new String[tamx][tammove];
          
          cadenas cadena = new cadenas();
          
          num_regmov = numeroregistro(nombre_movimientos);
          cad = cadena.concatpreeli(recupera);
          movimiento = cadena.tra_movimientosb(cad, num_regmov, codtabla,"1","Esto es un mensaje de prueba");
          cad = cadena.formatoSQL(movimiento);
         int num_conci1,num_conci =0;
          num_conci1=Integer.parseInt(clave);
          num_conci=listar_campos_clase("PRINCIPAL",num_conci1,6).size();
          clave = cadena.cero_izq(clave,6);
         
       if (num_conci==0)
       {   
        String result = "DELETE FROM " +  tabla +" WHERE CLAVE='"+clave+"'";
        
         try
          {conectar();
           st.execute(result);
           cn.close();}
               
          catch(Exception e )
          { System.out.println(result);
           JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
    
           insertomasivobd(nombre_movimientos,cad);
           actualizaindices_clase (tabla, clave);
       }
       
       else {return "Hay "+  Integer.toString(num_conci)+ " en la tabla principal ";}
       
       
      return null;}
        
     public ArrayList listar_campos (String tabla,int numero,int tamaño_campo)
        {
           ArrayList lista = new ArrayList(); 
          int cantidadFilas=0;
          int cantidadColumnas=0;
          int y=0;
          String elemento = new String();
          cadenas cadena = new cadenas();
      
          try 
           {conectar();
            rs=st.executeQuery("SELECT * FROM "+tabla);
             ResultSetMetaData rsMd = rs.getMetaData();
                  
            while(rs.next())
             { elemento =(String)rs.getObject(numero);
               if (cadena.esnumero(elemento)== true)
                {elemento= cadena.unstring(elemento, "*");///REVISAR ESTA MAL
                    lista.add(y, elemento);} 
               else{elemento= cadena.unstring(elemento, "*");
                   lista.add(y,elemento );
               } 
                 y++;}
        rs.close();
        return lista;  }
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);
        return null;
        }}
        
       public ArrayList<String> lecturabd2(String tabla,int columnaaempezar, int columnafinal)
    {int cantidadFilas=0;
     int cantidadColumnas=0;
     int y=0;
     ArrayList<String> temp_list = new ArrayList<String>();
     String elemento = new String();
    
      try 
      {conectar();
       rs=st.executeQuery("SELECT count(*) FROM "+ tabla);
       if(rs.next())
        { cantidadFilas=rs.getInt("count(*)"); }  
       else{cantidadFilas=0;}
       
       String [] cadenas= new String[cantidadFilas];
       rs=st.executeQuery("SELECT * FROM "+tabla);
       ResultSetMetaData rsMd = rs.getMetaData();
       cantidadColumnas = rsMd.getColumnCount();
      
       
       while(rs.next())
        {for(int i=columnaaempezar;i <= cantidadColumnas-columnafinal;i++)
          {if(i==columnaaempezar)
           {elemento =(String)rs.getObject(i);
           cadenas[y]=elemento;}
          else{elemento =(String)rs.getObject(i);
               cadenas[y]= cadenas[y]+elemento;}}
            temp_list.add(cadenas[y]);
        y++;
        
        }
        rs.close();
        return temp_list;  }
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);
        return null;
        }}  
        
  public boolean comprobacion2(String tabla1,String [][] valor)
   {
      cadenas cadena = new cadenas(); 
     ArrayList<String> leidabd =new ArrayList<String>();
     int tam = valor.length;
      String []tablapre = new String[tam];
      
     leidabd= lecturabd2(tabla1,2,1);
     Collections.sort(leidabd);
     tablapre =cadena.concatpreeli(valor,1,1);
       if (Collections.binarySearch(leidabd, tablapre[0])>0)
        {return true;}
       else{
            return false;}}
        
        
  public String modificacionbd_clase (String [][] datos, String tabla, int[] tamclave,String primaria,String codtabla,String codmod,String sql) throws SQLException
        {
            
            String nombre_movimientos = new String("MOVIMIENTOS");
       int num_regmov =0;
       int num_regtab=0;
       int tamx = datos.length;
       int tamy = datos[0][0].length();
       String [] tramassql = new String[tamx];
       String [] tramasqlmov =  new String[tamx];
       String []recupera = new String[2];
       cadenas cadena = new cadenas();
        //t_empaquetado paquete = new t_empaquetado();
         t_movimientos moviments = new t_movimientos(); 
         int tammove =moviments.getTa_movimientos().length;
       String[][] movimientos =new String[2][tammove];
       String[][] intermedio = new String[tamx][tamy+2];
       
       intermedio = cadena.tra_preeliminar(datos, tamclave);
       num_regmov = numeroregistro(nombre_movimientos);
       num_regtab=numeroregistro(tabla);
       recupera = lee_linea(tabla,primaria);   ///Salva la linea modificada antes de la actualizacion para colocarla en movimentos
       movimientos = cadena.tra_movimientosb(recupera, num_regmov,codtabla,codmod,"esto es una prueba");
 
      if (comprobacion2(tabla,intermedio)== false)
       {if (intermedio != null)
        { try
              {conectar();
                st.execute(sql); }
               
           catch(Exception e )
            {JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}  
           cn.close();
            
            tramasqlmov= cadena.formatoSQL(movimientos);
           insertomasivobd(nombre_movimientos,tramasqlmov);
        return "Registro modificado";
        }
       
       else {return "fallo registro";}}
      
      else return "Registro ya existe";}
     
  
  public String[] lee_tablas_conjuntas(String [] claves_tablas)
  {      
       String[] temp = new String[7];
  
       temp[0]= lee_campo_porclave("CLASE",claves_tablas[2],3);
       temp[1]= lee_campo_porclave("CLASE",claves_tablas[2],4);
       temp[2]= lee_campo_porclave("DATASHEET",claves_tablas[3],3);
       temp[3]= lee_campo_porclave("DATASHEET",claves_tablas[3],4);
       temp[4]= lee_campo_porclave("ESTADO",claves_tablas[5],2);
       temp[5]= lee_campo_porclave("EMPAQUETADO",claves_tablas[6],2);
       temp[6]= lee_campo_porclave("EMPAQUETADO",claves_tablas[6],4);
  return temp;
  }     
      
  public String lee_campo_porclave( String tabla,String clave,int elcampo)
         
   {      int cantidadFilas=0;
          int cantidadColumnas=0;
          int y=0;
          String elemento = new String();
          cadenas cadena = new cadenas();
          String sql = "SELECT * FROM "+tabla+ " WHERE CLAVE='"+clave+"'";
                
          try 
           {conectar();
            rs=st.executeQuery("SELECT * FROM "+tabla+ " WHERE CLAVE='"+clave+"'");
            rs.next(); 
            ResultSetMetaData rsMd = rs.getMetaData();
            elemento =(String)rs.getObject(elcampo);
                     
        rs.close();
        return elemento;  }
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);
        return null;}     
   }   
        
   public String[] primer_reg_t_principal(String tabla) throws SQLException 
     {
         cadenas cadena = new cadenas();
         //t_empaquetado paquete = new t_empaquetado();
       int cantidadColumnas=0;  
       String elemento=new String();
        String [] tablasecundarias=new String[7]; 
        int i=0; 
        int j=0;
      conectar();
             
      // rs=st.executeQuery("SELECT * FROM "+tabla);
      rs=st.executeQuery("SELECT * FROM "+tabla + " WHERE CLAVE='000000'");
       rs.next();
       ResultSetMetaData rsMd = rs.getMetaData();
       cantidadColumnas = rsMd.getColumnCount();
       String[] fila = new String[cantidadColumnas-1];
       String[] fila2 = new String[10];
        for( i=1;i <= cantidadColumnas-1;i++)
          {elemento =(String)rs.getObject(i);
           fila[j]=elemento;
          j++;}
                     
        rs.close();
        j=0;
        tablasecundarias=lee_tablas_conjuntas(fila);
        
        fila2[0] =fila[0];
        fila2[1] =fila[1];
        fila2[2] =tablasecundarias[0];
        fila2[3] =tablasecundarias[1];
        fila2[4] =tablasecundarias[2];
        fila2[5] =tablasecundarias[3];
        fila2[6] =fila[4];
        fila2[7] =tablasecundarias[4];
        fila2[8] =tablasecundarias[5];
        fila2[9] =tablasecundarias[6];
          j=0;
       for (i=0;i<cantidadColumnas+2;i++)
        { if (cadena.esnumero(fila2[i])==true)
           { j= Integer.parseInt(fila2[i]);
             fila2[i]= Integer.toString(j);}
          else{fila2[i]= cadena.unstring(fila2[i],"*"); }
         }
      return fila2;}
      
  
  public String[] siguiente_reg_t_principal(String tabla,String clave) throws SQLException 
     {
         cadenas cadena = new cadenas();
        int temp1=0;
        String clave_primaria = new String();
         
         
       int cantidadColumnas=0;  
       String elemento=new String();
        String [] tablasecundarias=new String[7]; 
        int i=0; 
        int j=0;
      conectar();
         temp1= Integer.parseInt(clave);
        temp1 = temp1 +1;
        clave_primaria= cadena.cero_izq(temp1,6);
        rs=st.executeQuery("SELECT * FROM "+tabla + " WHERE CLAVE='"+ clave_primaria+"'");
       rs.next();
       ResultSetMetaData rsMd = rs.getMetaData();
       cantidadColumnas = rsMd.getColumnCount();
       String[] fila = new String[cantidadColumnas-1];
       String[] fila2 = new String[10];
        for( i=1;i <= cantidadColumnas-1;i++)
          {elemento =(String)rs.getObject(i);
           fila[j]=elemento;
          j++;}
                     
        rs.close();
        j=0;
        tablasecundarias=lee_tablas_conjuntas(fila);
        
        fila2[0] =fila[0];
        fila2[1] =fila[1];
        fila2[2] =tablasecundarias[0];
        fila2[3] =tablasecundarias[1];
        fila2[4] =tablasecundarias[2];
        fila2[5] =tablasecundarias[3];
        fila2[6] =fila[4];
        fila2[7] =tablasecundarias[4];
        fila2[8] =tablasecundarias[5];
        fila2[9] =tablasecundarias[6];
        
       for (i=0;i<cantidadColumnas+2;i++)
        { 
            if (cadena.esnumero(fila2[i])==true)
           { j= Integer.parseInt(fila2[i]);
             fila2[i]= Integer.toString(j);}
          else{fila2[i]= cadena.unstring(fila2[i],"*"); }
         }
      return fila2;}
  
   public String[] atras_reg_t_principal(String tabla,String clave) throws SQLException 
     {
         cadenas cadena = new cadenas();
        int temp1=0;
        String clave_primaria = new String();
         
         
       int cantidadColumnas=0;  
       String elemento=new String();
        String [] tablasecundarias=new String[7]; 
        int i=0; 
        int j=0;
      conectar();
         temp1= Integer.parseInt(clave);
        temp1 = temp1 -1;
        clave_primaria= cadena.cero_izq(temp1,6);
        rs=st.executeQuery("SELECT * FROM "+tabla + " WHERE CLAVE='"+ clave_primaria+"'");
       rs.next();
       ResultSetMetaData rsMd = rs.getMetaData();
       cantidadColumnas = rsMd.getColumnCount();
       String[] fila = new String[cantidadColumnas-1];
       String[] fila2 = new String[10];
         for( i=1;i <= cantidadColumnas-1;i++)
          {elemento =(String)rs.getObject(i);
           fila[j]=elemento;
          j++;}
                     
        rs.close();
        j=0;
        tablasecundarias=lee_tablas_conjuntas(fila);
        
        fila2[0] =fila[0];
        fila2[1] =fila[1];
        fila2[2] =tablasecundarias[0];
        fila2[3] =tablasecundarias[1];
        fila2[4] =tablasecundarias[2];
        fila2[5] =tablasecundarias[3];
        fila2[6] =fila[4];
        fila2[7] =tablasecundarias[4];
        fila2[8] =tablasecundarias[5];
        fila2[9] =tablasecundarias[6];
        
        for (i=0;i<cantidadColumnas+2;i++)
        { if (cadena.esnumero(fila2[i])==true)
           { j= Integer.parseInt(fila2[i]);
             fila2[i]= Integer.toString(j);}
          else{fila2[i]= cadena.unstring(fila2[i],"*"); }
         }
      return fila2;}
  
  public String[] final_reg_t_principal(String tabla) throws SQLException 
     {
         cadenas cadena = new cadenas();
         //t_empaquetado paquete = new t_empaquetado();
       int cantidadColumnas=0; 
       int temp1=0;
        String clave_primaria = new String();
       String elemento=new String();
        String [] tablasecundarias=new String[7]; 
        int i=0; 
        int j=0;
      conectar();
       temp1=numeroregistro(tabla);
        clave_primaria= cadena.cero_izq(temp1-1,6);   
            
       rs=st.executeQuery("SELECT * FROM "+tabla + " WHERE CLAVE='"+ clave_primaria+"'");
       rs.next();
       ResultSetMetaData rsMd = rs.getMetaData();
       cantidadColumnas = rsMd.getColumnCount();
       String[] fila = new String[cantidadColumnas-1];
       String[] fila2 = new String[10];
         for( i=1;i <= cantidadColumnas-1;i++)
          {elemento =(String)rs.getObject(i);
           fila[j]=elemento;
          j++;}
                     
        rs.close();
        j=0;
        tablasecundarias=lee_tablas_conjuntas(fila);
        
        fila2[0] =fila[0];
        fila2[1] =fila[1];
        fila2[2] =tablasecundarias[0];
        fila2[3] =tablasecundarias[1];
        fila2[4] =tablasecundarias[2];
        fila2[5] =tablasecundarias[3];
        fila2[6] =fila[4];
        fila2[7] =tablasecundarias[4];
        fila2[8] =tablasecundarias[5];
        fila2[9] =tablasecundarias[6];
        
        for (i=0;i<cantidadColumnas+2;i++)
        { if (cadena.esnumero(fila2[i])==true)
           { j= Integer.parseInt(fila2[i]);
             fila2[i]= Integer.toString(j);}
          else{fila2[i]= cadena.unstring(fila2[i],"*"); }
         }
      return fila2;}
  
  
   public String lee_campo_porelemento( String tabla,String nombre,int elcampo)
         
   {      int cantidadFilas=0;
          int cantidadColumnas=0;
          int y=0;
          String elemento = new String();
          cadenas cadena = new cadenas();
      
          try 
           {conectar();
            rs=st.executeQuery("SELECT * FROM "+tabla+ " WHERE " + nombre);
            rs.next(); 
            ResultSetMetaData rsMd = rs.getMetaData();
            elemento =(String)rs.getObject(elcampo);
                     
        rs.close();
        return elemento;  }
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);
        return null;}     
   }   
  
   public void insertomasivobd2(String tabla,String movimientos, String  movimientosvalues,String  tablavalues)
    {
      
      String result = "INSERT INTO "+tabla+ " VALUES";
      String result1 = "INSERT INTO "+ movimientos+" VALUES";      
      
      try
       {conectar();
       
      
             st.execute(result+tablavalues);
      
          st.execute(result1+movimientosvalues);
            System.out.println(result);
          
        System.out.println(result);
        cn.close();}
               
     catch(Exception e )
      {//JOptionPane.showMessageDialog(null, result +e);
         System.out.println(result);
         System.out.println(result1);
         JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
                                              
    
    
    }
  
  
  
  
  
   public String altasbd_principal (String [] datos, String[] tabla) throws SQLException
     {  String nombre_movimientos = new String("MOVIMIENTOS");
        String nombre_principal = new String("PRINCIPAL");
       cadenas cadena = new cadenas();
        ArrayList <String> lista = new ArrayList();
        ArrayList <String> sabla = new ArrayList();
        String clavetablas = new String();
         String [] tablasalt_mov = new String[4];
        String [] tablasalt = new String[4];
         Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
         t_principal principal = new t_principal();
        
        
        boolean [] tablas = new boolean [4];
        int i =0;
        int j=0;
        for(i=2;i<6;i++)
        {
          clavetablas=lee_campo_porelemento(tabla[j],datos[i],1);
           if (clavetablas!=null)
            {tablas[j]=false;
              sabla.add(j,clavetablas);}  
           else{
                 tablas[j]=true;
                 sabla.add(j,"?");}
              j++;}
        
        int num_registromov=numeroregistro(nombre_movimientos);
        
       int num_registropri=numeroregistro(nombre_principal );
        lista.removeAll(lista);
       lista.add(0,cadena.cero_izq(num_registropri, principal.getTa_principal()[0])); //CLAVE
       lista.add(1,datos[0]); //Componente
       lista.add(2, sabla.get(0)); //Clase
       lista.add(3,sabla.get(3));//Datasheet
       lista.add(4,datos[1]); //cantidad
       lista.add(5,sabla.get(1)); //estado
       lista.add(6,sabla.get(2)); //paquete
       lista.add(7,formatfecha.format(fecha));
       
       
       
        if (tablas[0]==true)
           {  int num=numeroregistro(tabla[0]);//clase
               lista.remove(2);
               lista.add(2,cadena.cero_izq(num, principal.getTa_principal()[2]));
              String [][] temp = new String[2][num];
              String [][] temp_mov = new String[2][6];
              temp=cadena.tra_T_altaclase(datos[2], num);
              temp_mov= cadena.tra_movimientosb(temp, num_registromov,5,0);
              tablasalt[0]= cadena.formatoSQL(temp)[0];
              tablasalt_mov[0]= cadena.formatoSQL(temp_mov)[0];
                 num_registromov++;
           }  
        else {tablasalt[0]= "$";
              tablasalt_mov[0]= "$";}
        
     
        if (tablas[1]==true)
           {  int num=numeroregistro(tabla[1]); //estado
               lista.remove(5);
               lista.add(5,cadena.cero_izq(num, principal.getTa_principal()[5]));
              String [][] temp = new String[2][num];
              String [][] temp_mov = new String[2][6];
              temp=cadena.tra_T_altaestado(datos[3], num);
              temp_mov= cadena.tra_movimientosb(temp, num_registromov,7,0);
              tablasalt[1]= cadena.formatoSQL(temp)[0];
              tablasalt_mov[1]= cadena.formatoSQL(temp_mov)[0];
                 num_registromov++;
           }  
         
        else {tablasalt[1]= "$";
              tablasalt_mov[1]= "$";} 
        
             
        if (tablas[2]==true)
           {  int num=numeroregistro(tabla[2]);//paquete
               lista.remove(6);
               lista.add(6,cadena.cero_izq(num, principal.getTa_principal()[6]));
              String [][] temp = new String[2][num];
              String [][] temp_mov = new String[2][6];
              temp=cadena.tra_T_altapaquete(datos[4], num);
              temp_mov= cadena.tra_movimientosb(temp, num_registromov,8,0);
              tablasalt[2]= cadena.formatoSQL(temp)[0];
              tablasalt_mov[2]= cadena.formatoSQL(temp_mov)[0];
               num_registromov++;
           }  
        else {tablasalt[2]= "$";
              tablasalt_mov[2]= "$";}
        
       
        if (tablas[3]==true)
           {  int num=numeroregistro(tabla[3]);//datasheet
              lista.remove(3);
               lista.add(3,cadena.cero_izq(num, principal.getTa_principal()[3]));
              String [][] temp = new String[2][num];
              String [][] temp_mov = new String[2][6];
              temp=cadena.tra_T_altadatasheet(datos[5], num,num_registropri);
              temp_mov= cadena.tra_movimientosb(temp, num_registromov,7,0);
              tablasalt[3]= cadena.formatoSQL(temp)[0];
              tablasalt_mov[3]= cadena.formatoSQL(temp_mov)[0];
              num_registromov++;
              JOptionPane.showMessageDialog(null,"Valores datasheet "+ datos[3]+" \n");
           }  
        else {tablasalt[3]= "$";
              tablasalt_mov[3]= "$";}
        
  
        String [][] principa = new String[2][lista.size()];
        
        for(i=0;i<lista.size();i++)
        {  principa[0][i]=lista.get(i); }
          
         String [][] prin_mov = new String[2][6];
          prin_mov = cadena.tra_movimientosb(principa, num_registromov,1,0);
        
          String[] prin = new String [2];
          String[] mov = new String [2];
          
          mov[0]= cadena.formatoSQL(prin_mov)[0];
          prin[0]=cadena.formatoSQL(principa)[0];
          JOptionPane.showMessageDialog(null,"Valores SQL "+ prin[0]+" \n");
          
          for(i=0;i<4;i++)
          {    if (tablasalt[i]!="$")
                {
                    insertomasivobd2(tabla[i],nombre_movimientos,tablasalt_mov[i],tablasalt[i]);}
                }  
          
         insertomasivobd(nombre_principal ,nombre_movimientos,mov,prin);

          
              
           return null;}
   
   
     public String bajabd_principal (String tabla,String clave,String codtabla) throws SQLException
        {   String nombre_movimientos = new String("MOVIMIENTOS");
         
          t_movimientos moviments = new t_movimientos(); 
          int tammove =moviments.getTa_movimientos().length;
            int num_regmov =0;
          String[] cad = new String[2];
          String[][] movimiento = new String[2][tammove];
          cadenas cadena = new cadenas();
          clave = cadena.cero_izq(clave,6);      
          cad=lee_linea(tabla,clave);
          num_regmov = numeroregistro(nombre_movimientos);
          movimiento = cadena.tra_movimientosb(cad, num_regmov, codtabla,"1","Esto es un mensaje de prueba");
          cad = cadena.formatoSQL(movimiento);
          
                    
       String result = "DELETE FROM " +  tabla +" WHERE CLAVE='"+clave+"'";
        
     try
      {conectar();
       st.execute(result);
       cn.close();}
               
     catch(Exception e )
      { System.out.println(result);
        JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
    
      insertomasivobd(nombre_movimientos,cad);
          
      return null;}     
   
   
   
  
     public String modificacionbd_principal (String [] datos, String[] tabla,String clave) throws SQLException
     {  String nombre_movimientos = new String("MOVIMIENTOS");
        String nombre_principal = new String("PRINCIPAL");
       cadenas cadena = new cadenas();
        ArrayList <String> lista = new ArrayList();
        ArrayList <String> sabla = new ArrayList();
        String clavetablas = new String();
         String [] tablasalt_mov = new String[4];
        String [] tablasalt = new String[4];
         Date fecha = new Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat("dd-MM-yyyy");
         t_principal principal = new t_principal();
         String []recupera = new String[2];
         
         clave = cadena.cero_izq(clave,6);  
        recupera =lee_linea(nombre_principal,clave); 
        boolean [] tablas = new boolean [4];
        int i =0;
        int j=0;
        for(i=2;i<6;i++)
        {
          clavetablas=lee_campo_porelemento(tabla[j],datos[i],1);
           if (clavetablas!=null)
            {tablas[j]=false;
              sabla.add(j,clavetablas);}  
           else{
                 tablas[j]=true;
                 sabla.add(j,"?");}
              j++;}
        
        int num_registromov=numeroregistro(nombre_movimientos);
        
       int num_registropri=numeroregistro(nombre_principal );
        lista.removeAll(lista);
       lista.add(0,cadena.cero_izq(num_registropri, principal.getTa_principal()[0])); //CLAVE
       lista.add(1,datos[0]); //Componente
       lista.add(2, sabla.get(0)); //Clase
       lista.add(3,sabla.get(3));//Datasheet
       lista.add(4,datos[1]); //cantidad
       lista.add(5,sabla.get(1)); //estado
       lista.add(6,sabla.get(2)); //paquete
       lista.add(7,formatfecha.format(fecha));
       
       
       
        if (tablas[0]==true)
           {  int num=numeroregistro(tabla[0]);//clase
               lista.remove(2);
               lista.add(2,cadena.cero_izq(num, principal.getTa_principal()[2]));
              String [][] temp = new String[2][num];
              String [][] temp_mov = new String[2][6];
              temp=cadena.tra_T_altaclase(datos[2], num);
              temp_mov= cadena.tra_movimientosb(temp, num_registromov,5,0);
              tablasalt[0]= cadena.formatoSQL(temp)[0];
              tablasalt_mov[0]= cadena.formatoSQL(temp_mov)[0];
                 num_registromov++;
           }  
        else {tablasalt[0]= "$";
              tablasalt_mov[0]= "$";}
        
     
        if (tablas[1]==true)
           {  int num=numeroregistro(tabla[1]); //estado
               lista.remove(5);
               lista.add(5,cadena.cero_izq(num, principal.getTa_principal()[5]));
              String [][] temp = new String[2][num];
              String [][] temp_mov = new String[2][6];
              temp=cadena.tra_T_altaestado(datos[3], num);
              temp_mov= cadena.tra_movimientosb(temp, num_registromov,7,0);
              tablasalt[1]= cadena.formatoSQL(temp)[0];
              tablasalt_mov[1]= cadena.formatoSQL(temp_mov)[0];
                 num_registromov++;
           }  
         
        else {tablasalt[1]= "$";
              tablasalt_mov[1]= "$";} 
        
             
        if (tablas[2]==true)
           {  int num=numeroregistro(tabla[2]);//paquete
               lista.remove(6);
               lista.add(6,cadena.cero_izq(num, principal.getTa_principal()[6]));
              String [][] temp = new String[2][num];
              String [][] temp_mov = new String[2][6];
              temp=cadena.tra_T_altapaquete(datos[4], num);
              temp_mov= cadena.tra_movimientosb(temp, num_registromov,8,0);
              tablasalt[2]= cadena.formatoSQL(temp)[0];
              tablasalt_mov[2]= cadena.formatoSQL(temp_mov)[0];
               num_registromov++;
           }  
        else {tablasalt[2]= "$";
              tablasalt_mov[2]= "$";}
        
       
        if (tablas[3]==true)
           {  int num=numeroregistro(tabla[3]);//datasheet
              lista.remove(3);
               lista.add(3,cadena.cero_izq(num, principal.getTa_principal()[3]));
              String [][] temp = new String[2][num];
              String [][] temp_mov = new String[2][6];
              
              temp=cadena.tra_T_altadatasheet(datos[5], num,Integer.parseInt(clave));
              temp_mov= cadena.tra_movimientosb(temp, num_registromov,7,0);
              tablasalt[3]= cadena.formatoSQL(temp)[0];
              tablasalt_mov[3]= cadena.formatoSQL(temp_mov)[0];
              num_registromov++;
           }  
        else {tablasalt[3]= "$";
              tablasalt_mov[3]= "$";}
        
  
        String [][] principa = new String[2][lista.size()];
        
      /*  for(i=0;i<lista.size();i++)
        {  principa[0][i]=lista.get(i); }*/
          
         String [][] prin_mov = new String[2][6];
          prin_mov = cadena.tra_movimientosb(recupera, num_registromov, "1","1","Esto es un mensaje de prueba");
                      
          String[] prin = new String [2];
          String[] mov = new String [2];
          
          mov[0]= cadena.formatoSQL(prin_mov)[0];
          
          String result = "UPDATE "+ nombre_principal+ " SET COMPONENTE='"+lista.get(1)+"',"
              + "CLASE = '"+lista.get(2)+"',"
              + "DATASHEET = '"+lista.get(3)+ "',"
              + "CANTIDAD = '"+lista.get(4)+ "',"  
              + "ESTADO = '"+lista.get(5)+ "',"   
              + "EMPAQUETADO = '"+lista.get(6)+ "',"    
              + "FECHA = '"+lista.get(7)+ "' WHERE CLAVE='"+clave+"'";
          
         
          for(i=0;i<4;i++)
          {    if (tablasalt[i]!="$")
                {
                    insertomasivobd2(tabla[i],nombre_movimientos,tablasalt_mov[i],tablasalt[i]);}
                }  
          
       try{ conectar();
            st.execute(result);}
               
           catch(Exception e )
            {JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}  
           cn.close();
                      
           insertomasivobd(nombre_movimientos,  mov);
          return null;}
   
  public String actualizaindices_principal (String tabla,String clave) throws SQLException
       {   int nuevo=0;
           int i =0;
           String datasheet = new String();
           datasheet= "DATASHEET";
          
           int numero_registro = numeroregistro(tabla);
            cadenas cadena = new cadenas();
           String clave_antigua= new String(cadena.cero_izq(clave, 6));
           String clave_nueva= new String();
       //   numero_registro=numero_registro+3;
           nuevo = Integer.parseInt(clave_antigua);
           nuevo=nuevo+1;
           clave_nueva=cadena.cero_izq(nuevo, 6);
            String result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
            String result1 = "UPDATE " +  datasheet +" SET CODTABLAPRIM='"+clave_antigua + "' WHERE CODTABLAPRIM='"+clave_nueva+"'";
           
             try
              {conectar();
               for (i=nuevo;i<numero_registro+1;i++)
               {  
                clave_nueva=cadena.cero_izq(i, 6);
                result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
                result1 = "UPDATE " +  datasheet +" SET CODTABLAPRIM='"+clave_antigua + "' WHERE CODTABLAPRIM='"+clave_nueva+"'";
                st.execute(result);
                st.execute(result1);
                clave_antigua =cadena.cero_izq(i, 6); }
                
       cn.close();}
               
          catch(Exception e )
           { System.out.println(result);
              System.out.println(result1);
            JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
          
       return null;
       }  
   
   public ArrayList listar_campos_empaquetado (String tabla,int numero,int tamaño_campo)
        {
           ArrayList lista = new ArrayList(); 
          int numer=0;
          int cantidadColumnas=0;
          int y=0;
          String elemento = new String();
          cadenas cadena = new cadenas();
           String  clave_primaria= cadena.cero_izq(numero,6);
          try 
           {conectar();
            rs=st.executeQuery("SELECT * FROM "+tabla + " WHERE EMPAQUETADO='"+clave_primaria+"'");
             ResultSetMetaData rsMd = rs.getMetaData();
                  
            while(rs.next())
             { elemento =(String)rs.getObject(7);
               if (cadena.esnumero(elemento)== true)
                { numer= Integer.parseInt(elemento);
                    elemento = Integer.toString(numer);
                    lista.add(y, elemento);} 
               else{elemento= cadena.unstring(elemento, "*");
                   lista.add(y,elemento );
               } 
                 y++;}
        rs.close();
        return lista;  }
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);
        return null;
        }}
  
    public String actualizaindices_empaquetados (String tabla,String clave) throws SQLException
       {   int nuevo=0;
           int i =0;
           int numero_registro = numeroregistro(tabla);
            cadenas cadena = new cadenas();
           String clave_antigua= new String(cadena.cero_izq(clave, 6));
           String clave_nueva= new String();
       //   numero_registro=numero_registro+3;
           nuevo = Integer.parseInt(clave_antigua);
           nuevo=nuevo+1;
           clave_nueva=cadena.cero_izq(nuevo, 6);
            String result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
            String result1 = "UPDATE PRINCIPAL SET EMPAQUETADO='"+clave_antigua + "' WHERE EMPAQUETADO='"+clave_nueva+"'";
           
             try
              {conectar();
               for (i=nuevo;i<numero_registro+1;i++)
               {  
                clave_nueva=cadena.cero_izq(i, 6);
                result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
                result1 = "UPDATE PRINCIPAL SET EMPAQUETADO='"+clave_antigua + "' WHERE EMPAQUETADO='"+clave_nueva+"'";
                st.execute(result);
                st.execute(result1);
                clave_antigua =cadena.cero_izq(i, 6); }
                
       cn.close();}
               
          catch(Exception e )
           { System.out.println(result);
             System.out.println(result1);
            JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
          
       return null;
       }
  
  public ArrayList listar_campos_clase (String tabla,int numero,int tamaño_campo)
        {
           ArrayList lista = new ArrayList(); 
          int numer=0;
          int cantidadColumnas=0;
          int y=0;
          String elemento = new String();
          cadenas cadena = new cadenas();
           String  clave_primaria= cadena.cero_izq(numero,6);
          try 
           {conectar();
            rs=st.executeQuery("SELECT * FROM "+tabla + " WHERE EMPAQUETADO='"+clave_primaria+"'");
             ResultSetMetaData rsMd = rs.getMetaData();
                  
            while(rs.next())
             { elemento =(String)rs.getObject(3);
               if (cadena.esnumero(elemento)== true)
                { numer= Integer.parseInt(elemento);
                    elemento = Integer.toString(numer);
                    lista.add(y, elemento);} 
               else{elemento= cadena.unstring(elemento, "*");
                   lista.add(y,elemento );
               } 
                 y++;}
        rs.close();
        return lista;  }
       catch(Exception e )
        {JOptionPane.showMessageDialog(null,"Error no se puede consultar a la base datos \n"+e);
        return null;
        }}
  
   public String actualizaindices_INV (String tabla,String clave) throws SQLException
       {   int nuevo=0;
           int i =0;
           int numero_registro = numeroregistro(tabla);
            cadenas cadena = new cadenas();
           String clave_antigua= new String(cadena.cero_izq(clave, 6));
           String clave_nueva= new String();
       //   numero_registro=numero_registro+3;
           nuevo = Integer.parseInt(clave_antigua);
           nuevo=nuevo+1;
           clave_nueva=cadena.cero_izq(nuevo, 6);
            String result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
           
           
             try
              {conectar();
              // for (i=nuevo;i<numero_registro+1;i--)
              for (i=nuevo;i>13;i--)
               {  
                clave_nueva=cadena.cero_izq(i, 6);
                result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
                st.execute(result);
                clave_antigua =cadena.cero_izq(i, 6); }
                
       cn.close();}
               
          catch(Exception e )
           { System.out.println(result);
            JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
          
       return null;
       }
    
    public String actualizaindices_clase (String tabla,String clave) throws SQLException
       {   int nuevo=0;
           int i =0;
           int numero_registro = numeroregistro(tabla);
            cadenas cadena = new cadenas();
           String clave_antigua= new String(cadena.cero_izq(clave, 6));
           String clave_nueva= new String();
       //   numero_registro=numero_registro+3;
           nuevo = Integer.parseInt(clave_antigua);
           nuevo=nuevo+1;
           clave_nueva=cadena.cero_izq(nuevo, 6);
            String result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
            String result1 = "UPDATE PRINCIPAL SET CLASE='"+clave_antigua + "' WHERE CLASE='"+clave_nueva+"'";
           
             try
              {conectar();
               for (i=nuevo;i<numero_registro+1;i++)
               {  
                clave_nueva=cadena.cero_izq(i, 6);
                result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
                result1 = "UPDATE PRINCIPAL SET CLASE='"+clave_antigua + "' WHERE CLASE='"+clave_nueva+"'";
                st.execute(result);
                st.execute(result1);
                clave_antigua =cadena.cero_izq(i, 6); }
                
       cn.close();}
               
          catch(Exception e )
           { System.out.println(result);
             System.out.println(result1);
            JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
          
       return null;
       }
    
    public String bajabd_paquete (String tabla,String clave, String[][] recupera,String codtabla) throws SQLException
        {   String nombre_movimientos = new String("MOVIMIENTOS");
          int tamx = recupera[0].length;
          t_movimientos moviments = new t_movimientos(); 
          int tammove =moviments.getTa_movimientos().length;
            int num_regmov =0;
            int num_conci1,num_conci =0;
          String[] cad = new String[tamx];
          String[][] movimiento = new String[tamx][tammove];
          
          cadenas cadena = new cadenas();
          
          num_regmov = numeroregistro(nombre_movimientos);
          cad = cadena.concatpreeli(recupera);
          movimiento = cadena.tra_movimientosb(cad, num_regmov, codtabla,"1","Esto es un mensaje de prueba");
          cad = cadena.formatoSQL(movimiento);
          num_conci1=Integer.parseInt(clave);
          num_conci=listar_campos("PRINCIPAL",num_conci1,6).size();
          clave = cadena.cero_izq(clave,6);
         
       if (num_conci>0)
       {   
        String result = "DELETE FROM " +  tabla +" WHERE CLAVE='"+clave+"'";
        
         try
          {conectar();
           st.execute(result);
           cn.close();}
               
          catch(Exception e )
          { System.out.println(result);
           JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
    
           insertomasivobd(nombre_movimientos,cad);
           actualizaindices_empaquetados(tabla, clave);
       }
       
       else {return "Hay "+  Integer.toString(num_conci)+ " en la tabla principal ";}
       
       
      return null;}
    
     public String actualizaindices_DATASHEET (String tabla,String clave) throws SQLException
       {   int nuevo=0;
           int i =0;
           int numero_registro = numeroregistro(tabla);
            cadenas cadena = new cadenas();
           String clave_antigua= new String(cadena.cero_izq(clave, 6));
           String clave_nueva= new String();
       //   numero_registro=numero_registro+3;
           nuevo = Integer.parseInt(clave_antigua);
           nuevo=nuevo+1;
           clave_nueva=cadena.cero_izq(nuevo, 6);
            String result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
            String result1 = "UPDATE PRINCIPAL SET DATASHEET='"+clave_antigua + "' WHERE DATASHEET='"+clave_nueva+"'";
           
             try
              {conectar();
               for (i=nuevo;i<numero_registro+1;i++)
               {  
                clave_nueva=cadena.cero_izq(i, 6);
                result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
                result1 = "UPDATE PRINCIPAL SET DATASHEET='"+clave_antigua + "' WHERE DATASHEET='"+clave_nueva+"'";
                st.execute(result);
                st.execute(result1);
                clave_antigua =cadena.cero_izq(i, 6); }
                
       cn.close();}
               
          catch(Exception e )
           { System.out.println(result);
             System.out.println(result1);
            JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
          
       return null;
       }
   
      public String actualizaindices_estado (String tabla,String clave) throws SQLException
       {   int nuevo=0;
           int i =0;
           int numero_registro = numeroregistro(tabla);
            cadenas cadena = new cadenas();
           String clave_antigua= new String(cadena.cero_izq(clave, 6));
           String clave_nueva= new String();
       //   numero_registro=numero_registro+3;
           nuevo = Integer.parseInt(clave_antigua);
           nuevo=nuevo+1;
           clave_nueva=cadena.cero_izq(nuevo, 6);
            String result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
            String result1 = "UPDATE PRINCIPAL SET ESTADO='"+clave_antigua + "' WHERE ESTADO='"+clave_nueva+"'";
           
             try
              {conectar();
               for (i=nuevo;i<numero_registro+1;i++)
               {  
                clave_nueva=cadena.cero_izq(i, 6);
                result = "UPDATE " +  tabla +" SET CLAVE='"+clave_antigua + "' WHERE CLAVE='"+clave_nueva+"'";
                result1 = "UPDATE PRINCIPAL SET ESTADO='"+clave_antigua + "' WHERE ESTADO='"+clave_nueva+"'";
                st.execute(result);
                st.execute(result1);
                clave_antigua =cadena.cero_izq(i, 6); }
                
       cn.close();}
               
          catch(Exception e )
           { System.out.println(result);
             System.out.println(result1);
            JOptionPane.showMessageDialog(null,"Error no se puede carga los datos a la base datos \n"+e);}
          
       return null;
       }
}
    








 
  
  
  
    
    
    
    

