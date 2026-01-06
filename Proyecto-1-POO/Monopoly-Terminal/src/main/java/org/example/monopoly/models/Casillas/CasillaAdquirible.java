package org.example.monopoly.models.Casillas;

import org.example.monopoly.models.Jugadores.Jugador;

import java.util.ArrayList;

public abstract class CasillaAdquirible extends Casilla {

    /*Atributos generales para las casillas adquiribles
    * Esta clase se extiende casilla dado que es un tipo
    * especial de casilla, se le añaden atributos
    * */
    protected int precio;
    protected ArrayList<Integer> renta;
    protected Jugador propietario;
    protected boolean isHipotecado;
    protected final int hipoteca;

    /**
     * constructor de las clases adquiribles, se realiza el super para acceder a los atributos desde la clase padre
     * @param nombre
     * @param descripcion
     * @param precio
     * @param renta
     * @param hipoteca
     */
    public CasillaAdquirible(String nombre, String descripcion, int precio, ArrayList<Integer> renta, int hipoteca) {
        super.nombre = nombre;
        super.descripcion = descripcion;
        this.precio = precio;
        this.renta = renta;
        propietario = null;
        this.hipoteca = hipoteca;
        isHipotecado = false;
    }

    @Override
    public String toString() {
        return nombre;
    }

    //setters y getters
    public ArrayList<Integer> getRenta() {
        return renta;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    public boolean isHipotecado() {
        return isHipotecado;
    }

    public void setHipotecado(boolean hipotecado) {
        isHipotecado = hipotecado;
    }

    public int getHipoteca() {
        return hipoteca;
    }
}
