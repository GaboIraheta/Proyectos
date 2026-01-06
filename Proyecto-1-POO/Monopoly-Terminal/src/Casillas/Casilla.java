package Casillas;


import java.io.IOException;

public abstract class Casilla implements CasillaMethods {

    //Atributos generales para todas las casillas, independientemente del tipo de casilla
    protected String nombre;
    protected String descripcion;

    /**
     * Se declara el método de mostrar info y se declara abstracto, para que todas las clases
     * derivadas de la casilla posean el método y muestren la info de esta
     * */
    public abstract void mostrarInfo() throws IOException;

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
