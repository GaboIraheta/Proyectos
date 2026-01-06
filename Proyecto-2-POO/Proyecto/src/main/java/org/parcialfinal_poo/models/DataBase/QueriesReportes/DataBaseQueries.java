package org.parcialfinal_poo.models.DataBase.QueriesReportes;

import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;
import org.parcialfinal_poo.models.DataBase.DataBase;

import java.sql.Date;
import java.util.ArrayList;

public abstract class DataBaseQueries extends DataBase {

    public static String getReportQuery(int query) {
        //00021223 metodo estatico para obtener la query de reporte requerida en base a un parametro entero
        if(query == 1) { //00021223 se verifica si la query requerida es para el reporte A
            return """
                    select c.* from Compra c inner join Tarjeta t on t.id = c.tarjetaID
                    inner join Cliente on t.ClienteID = ?\s
                    where c.fechaCompra between ? and ?"""; //00021223 retorna la query para consultar un reporte A
        } else if(query == 2) { //00021223 se verifica si la query requerida es para el reporte B
            return """
                    select sum(c.monto) 
                    from Compra c 
                    inner join Tarjeta t on t.id = c.tarjetaID
                    inner join Cliente cl on t.clienteID = cl.id 
                    where cl.id = ? and year(c.fechaCompra) = ? and month(c.fechaCompra) = ?;
                   """; //00021223 retorna la query para consultar un reporte B
        } else if(query == 3) { //00021223 se verifica si la query requerida es para el reporte C
            return "select numTarjeta from Tarjeta \n" +
                    "where tipo like ? and ClienteID = ?"; //00021223 retorna la query para consultar el reporte C
        } else if(query == 4) { //00021223 se verifica si la query requerida es para el reporte D
            return """
                    with TarjetaIDS (id) as\s
                    (select t.id from Tarjeta t inner join Facilitador f on t.facilitadorID = f.id where f.facilitador = ?),
                    ComprasCliente (id, numCompras, totalGastado) as\s
                    (select t.id, count(c.id), sum(c.monto) from Compra c inner join TarjetaIDS t on t.id = c.tarjetaID group by t.id),
                    Clientes (id, compras, total) as
                    (select c.clienteID, t.numCompras, t.totalGastado from ComprasCliente t inner join Tarjeta c on t.id = c.id)
                    select c.id, c.nombres, c.apellidos, c.numTelefono, t.compras, t.total
                    from Cliente c inner join Clientes t on t.id = c.id"""; //00021223 retorna la query para consultar el reporte D
        } else {
            return ""; //00021223 si no se le manda un parametro correcto retorna una cadena vacia
        }
    }

    public abstract ArrayList<Compra> generarReporteA(int clienteID, Date fecha1, Date fecha2);

    public abstract double generarReporteB(int clienteID, int year,int month);

    public abstract void generarReporteC(int clienteID, ArrayList<String> tarjetasCredito, ArrayList<String> tarjetasDebito);

    //public abstract
}
