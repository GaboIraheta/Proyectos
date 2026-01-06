package org.parcialfinal_poo.models.DataBase.Updates;

import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;
import org.parcialfinal_poo.models.DataBase.DataBase;

import java.sql.Date;

public abstract class DataBaseUpdate extends DataBase {

    public static String getUpdateQuery(int query) {
        //00021223 metodo estatico para obtener la query de actualizar requerida en base a un parametro entero
        if(query == 0) { //00021223 se verifica si la query requerida es para actualizar en la tabla Cliente
            return "UPDATE Cliente SET nombres = ?, apellidos = ?, direccion = ?," +
                    " numTelefono = ? WHERE id = ?"; //00021223 retorna la query para actualizar en la tabla Cliente
        } else if(query == 1) { //00021223 se verifica si la query requerida es para actualizar en la tabla tarjeta
            return "UPDATE Tarjeta SET numTarjeta = ?, fechaExp = ?, tipo = ?, facilitadorID = ?, clienteID = ? WHERE id = ?"; //00021223 retorna la query para actualizar en la tabla Tarjeta
        } else if(query == 2) { //00021223 se verifica si la query requerida es para actualizar en la tabla Compra
            return "UPDATE Compra SET fechaCompra = ?, monto = ?, descripcion = ?, tarjetaID = ? WHERE id = ?"; //00021223 retorna la query para actualizar en la tabla Compra
        } else {
            return ""; //00021223 si no se le manda un parametro correcto retorna vacio
        }
    }

    public abstract void updateCliente(String nombres, String apelllidos, String direccion, String numTelefono, int id);
    //00021223 metodo abstracto para actualizar datos de la tabla Cliente

    public abstract void updateTarjeta(String numTarjeta, Date fechaExp, TipoTarjeta tipo, Facilitador facilitador, int clienteID, int id);
    //00021223 metodo abstracto para actualizar datos en la tabla Tarjeta

    public abstract void updateCompra(Date fechaCompra, double monto, String descripcion, int tarjetaID, int id);
    //00021223 metodo abstracto para actualizar datos en la tabla Compra
}
