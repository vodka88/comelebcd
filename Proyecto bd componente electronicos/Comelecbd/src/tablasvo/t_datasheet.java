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
public class t_datasheet {
     int[]ta_datasheet ={6,6,80,80,20,10};  
  
  String clave;
 String codigotabla;
 String url;
 String datasheet;
 String descripcion;
 String fecha;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int[] getTa_datasheet() {
        return ta_datasheet;
    }

    public void setTa_datasheet(int[] ta_datasheet) {
        this.ta_datasheet = ta_datasheet;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatasheet() {
        return datasheet;
    }

    public void setDatasheet(String datasheet) {
        this.datasheet = datasheet;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
