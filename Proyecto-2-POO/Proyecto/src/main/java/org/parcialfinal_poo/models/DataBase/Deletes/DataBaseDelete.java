package org.parcialfinal_poo.models.DataBase.Deletes;

import org.parcialfinal_poo.models.DataBase.DataBase;

public abstract class DataBaseDelete extends DataBase {

    public static String getDeleteQuery(int query) {
        //00021223 metodo estatico para obtener la query de eliminar requerida en base a un parametro entero
        if(query == 0) { //00021223 se verifica si la query requerida es para eliminar en la tabla Cliente
            return "DELETE FROM Cliente WHERE id = ?"; //00021223 retorna la query para eliminar en la tabla Cliente
        } else if(query == 1) { //00021223 se verifica si la query requerida es para eliminar en la tabla tarjeta
            return "DELETE FROM Tarjeta WHERE Tarjeta.id = ?"; //00021223 retorna la query para eliminar en la tabla Tarjeta
        } else if(query == 2) { //00021223 se verifica si la query requerida es para eliminar en la tabla Compra
            return "DELETE FROM Compra WHERE Compra.id = ?"; //00021223 retorna la query para eliminar en la tabla Compra
        } else {
            return ""; //00021223 si no se le manda un parametro correcto retorna vacio
        }
    }

    public abstract boolean deleteCliente(int id);
    //00021223 metodo abstracto para eliminar registros de la tabla Cliente

    public abstract boolean deleteTarjeta(int id);
    //00021223 metodo abstracto para eliminar registros de la tabla Tarjeta

    public abstract boolean deleteCompra(int id);
    //00021223 metodo abstracto para eliminar registros de la tabla Compra
}
