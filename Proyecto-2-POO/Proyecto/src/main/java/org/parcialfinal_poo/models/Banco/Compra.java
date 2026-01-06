package org.parcialfinal_poo.models.Banco;

import java.sql.Date;


public class Compra {

    private int codigo; //00021223 campo para almcenar el ID de las compras realizadas
    private Date fechaCompra; //00021223 campo para almacenar la fecha que se realizo la compra
    private double monto; //00021223 campo para almacenar el monto total de la compra
    private String descripcion; //00021223 campo para almacenar la descripcion de la compra
    private int tarjetaID; //00021223 campo para almacenar la tarjeta asociada con la que se realizo la compra

    public Compra() { //00042832 Se crea un constructor sin parámetros
    }

    public Compra(int codigo, Date fechaCompra, double monto, String descripcion, int tarjetaID) { //00042823 Se crea un constructor con todos los atributos como parámetros
        this.codigo = codigo; //00042823 El valor del parámetro código se guarda como atributo código
        this.fechaCompra = fechaCompra; //00042823 El valor del parámetro fechaCompra se guarda como atributo fechaCompra
        this.monto = monto; //00042823 El valor del parámetro monto se guarda como atributo monto
        this.descripcion = descripcion; //00042823 El valor del parámetro descripción se guarda como atributo descripción
        this.tarjetaID = tarjetaID; //00042823 El valor del parámetro tarjetaID se guarda como atributo tarjetaID
    }

    //Gabriel (00021223) se encargará de llenar de comentarios el resto, porque quiso usar una librería externa :)

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTarjetaID() {
        return tarjetaID;
    }

    public void setTarjetaID(int tarjetaID) {
        this.tarjetaID = tarjetaID;
    }
}
