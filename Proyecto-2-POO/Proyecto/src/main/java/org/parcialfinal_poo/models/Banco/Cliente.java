package org.parcialfinal_poo.models.Banco;

import org.parcialfinal_poo.models.Banco.Tarjetas.Tarjeta;
import org.parcialfinal_poo.models.DataBase.Selects.Select;

import java.util.ArrayList;

public class Cliente {

    private int id; //00021223 campo para almacenar el id de los clientes
    private String nombres; //00021223 campo para almacenar los nombres de cada cliente
    private String apellidos; //00021223 campo para almacenar los apellidos de cada cliente
    private String direccion; //00021223 campo para almacenar la direccion de cada cliente
    private String telefono; //00021223 campo para almacenar el telefono de cada cliente

    public Cliente(int id, String nombres, String apellidos, String direccion, String telefono, ArrayList<Tarjeta> tarjetas) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Cliente() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
