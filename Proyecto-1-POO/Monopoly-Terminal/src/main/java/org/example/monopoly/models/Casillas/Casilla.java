package org.example.monopoly.models.Casillas;

public abstract class Casilla implements CasillaMethods {

    //Atributos generales para todas las casillas, independientemente del tipo de casilla
    protected String nombre;
    protected String descripcion;

    //setters y getters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
