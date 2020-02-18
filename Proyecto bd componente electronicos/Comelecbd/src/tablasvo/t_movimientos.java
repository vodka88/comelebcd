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
public class t_movimientos {
    
  int[]ta_movimientos ={6,2,160,10,2,20};  
  
  String clave;
 String codigotabla;
 String valormodantes;
 String tipomod;
 String fecha;
 String descripcion;

    public int[] getTa_movimientos() {
        return ta_movimientos;
    }

    public void setTa_movimientos(int[] ta_movimientos) {
        this.ta_movimientos = ta_movimientos;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCodigotabla() {
        return codigotabla;
    }

    public void setCodigotabla(String codigotabla) {
        this.codigotabla = codigotabla;
    }

    public String getValormodantes() {
        return valormodantes;
    }

    public void setValormodantes(String valormodantes) {
        this.valormodantes = valormodantes;
    }

    public String getTipomod() {
        return tipomod;
    }

    public void setTipomod(String tipomod) {
        this.tipomod = tipomod;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
   
 
}
