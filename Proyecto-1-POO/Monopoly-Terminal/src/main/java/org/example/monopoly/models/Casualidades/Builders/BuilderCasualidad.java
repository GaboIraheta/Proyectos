package org.example.monopoly.models.Casualidades.Builders;

import org.example.monopoly.models.Casualidades.Casualidades;

/*
 * Clase abstracta de la que extienden cada uno de los builders que construyen cada tipo de casualidad
 */
public abstract class BuilderCasualidad {
    //atributo que almacena el tipo de casualidad que se desea construir
    protected Casualidades casualidad;

    /**Metodo que permite actualizar el builder segun el tipo de casualidad a construir
    * el cual realiza un override del metodo abstracto de realizar efecto de la clase casualidad
    * dado que el builder utiliza el atributo {@code casualidad} para almacenar la casualidad
     * que se requiere en el momento, reset permite re definir el metodo segun el builder
     * que ha sido llamado para que el objeto casualidad realice la accion requerida*/
    public abstract void reset();

    //metodo para construir el nombre de la casualidad
    public void buildName(String name){
        casualidad.setName(name);
    }

    //metodo para construir la descripcion de la casualidad
    public void buildDescription(String description){
        casualidad.setDescription(description);
    }

    /* metodo que retorna la casualidad que se necesita, que es la casualidad que se almacena
     * en las llamadas de los builders
     */
    public Casualidades getCasualidad(){
        return casualidad;
    }
}
