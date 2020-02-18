/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablasvo;

/**
 *
 * @author HECTOR
 */
public class t_estados {
    
     int[]ta_estados ={6,20,10};  
  
  String clave;
 String estado;
  String fecha;

    public int[] getTa_estados() {
        return ta_estados;
    }

    public void setTa_estados(int[] ta_movimientos) {
        this.ta_estados = ta_movimientos;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    
    
}
