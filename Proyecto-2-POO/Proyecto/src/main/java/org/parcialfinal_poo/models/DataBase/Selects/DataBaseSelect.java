package org.parcialfinal_poo.models.DataBase.Selects;

import org.parcialfinal_poo.models.Banco.Cliente;
import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.Banco.Tarjetas.Tarjeta;
import org.parcialfinal_poo.models.DataBase.DataBase;

import java.util.ArrayList;

public abstract class DataBaseSelect extends DataBase {

    public static String getSelectQuery(int query) {
        //00021223 metodo estatico para obtener la query para consultar registros requerida en base a un parametro entero
        if(query == 0) { //00021223 se verifica si la query requerida es para consultar la tabla Clientes
            return "SELECT * FROM Cliente"; //00021223 retorna la query para consultar a tabla Cliente
        } else if(query == 1) { //00021223 se verifica si la query requerida es para consultar la tabla Tarjeta
            return "SELECT * FROM Tarjeta"; //00021223 retorna la query para consultar la tabla Tarjeta
        } else if(query == 2) { //00021223 se veririca si la query requerida es para consulta la tabla Compra
            return "SELECT * FROM Compra"; //00021223 retorna la query para consultar la tabla Compra
        } else {
            return ""; //00021223 si ningun parametro coincide entonces le manda una cadena vacia
        }
    }

    public abstract ArrayList<Cliente> selectCliente();
    //00021223 metodo abstracto para consultar la tabla Cliente, retorna una lista de clientes la cual almacena todos los registros obtenidos de la base de datos

    public abstract ArrayList<Tarjeta> selectTarjeta();
    //00021223 metodo abstracto para consultar la tabla Tarjeta, retorna una lista de tarjetas la cual almacena todos los registros obtenidos de la base de datos

    public abstract ArrayList<Compra> selectCompra();
    //00021223 metodo abstracto para consultar la tabla Compra, retorna una lista de compras la cual almacena todos los registros obtenidos de la base de datos
}
