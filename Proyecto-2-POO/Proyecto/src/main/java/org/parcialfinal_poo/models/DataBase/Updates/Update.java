package org.parcialfinal_poo.models.DataBase.Updates;

import javafx.scene.control.Alert;
import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;

import java.sql.Date;
import java.sql.SQLException;

public class Update extends DataBaseUpdate {

    private static Update instance; //00021223 instancia unica de la clase

    private Update() { //00021223 constructor privado para que la clase solo pueda ser instanciada por ella misma

    }

    public static Update getInstance() { //00021223 metodo para obtener la instancia unica de la clase
        if (instance == null) { //00021223 verifica que la instancia no sea nula
            instance = new Update(); //00021223 si la instancia es nula crea el objeto de la clase
        }
        return instance; //00021223 retorna la instancia unica
    }

    @Override
    public void updateCliente(String nombres, String apellidos, String direccion, String numTelefono, int id) {
        //00021223 se define metodo abstracto para ejecutar la consulta para actualizar registros en la tabla cliente en la bbdd
        //recibe los campos que se desean actualizar del registro y el ID del registro que se desea actualizar

        try { //00021223 control de excepciones requerido para ejecutar consultas a bbdd

            connection = getConnection(); //00021223 se establece la conexion a la base de datos

            preparedStatement = connection.prepareStatement(DataBaseUpdate.getUpdateQuery(0)); //00021223 se prepara la query
            //la cual espera parametros que son los nuevos valores de los campos a actualizar

            preparedStatement.setString(1, nombres); //00021223 se manda el primer valor (nuevo nombre del cliente)
            preparedStatement.setString(2, apellidos); //00021223 segundo valor (nuevos nombres del cliente)
            preparedStatement.setString(3, direccion); //00021223 tercer valor (nueva direccion del cliente)
            preparedStatement.setString(4, numTelefono); //00021223 cuarto valor (nuevo telefono del cliente)
            preparedStatement.setInt(5, id); //00021223 ID del cliente del cual se quiere actualizar sus datos

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor de retorno
            //en una variable de tipo entero, retorna el numero de campos afectados

            alerta = new Alert(Alert.AlertType.INFORMATION); //00021223 se inicializa la alerta
            alerta.setTitle("Registro de Cliente"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Datos de cliente actualizados exitosamente"
                    : "Actualizacion de datos de cliente fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //0021223 se inserta el texto para el contenido de la alerta
            alerta.showAndWait(); //00021223 muestra la alerta

        } catch (SQLException ex) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
        }
    }

    @Override
    public void updateTarjeta(String numTarjeta, Date fechaExp, TipoTarjeta tipo, Facilitador facilitador, int clienteID, int id) {
        //00021223 se define metodo abstracto para ejecutar la consulta para actualizar registros en la tabla tarjeta en la bbdd
        //recibe los campos que se requieren actualizar en un registro y el ID del registro que se desea actualizar

        try { //00021223 control de excepciones requerido para ejecutar consultas a bbdd

            connection = getConnection(); //00021223 se establece la conexion a la base de datos

            preparedStatement = connection.prepareStatement(DataBaseUpdate.getUpdateQuery(1)); //00021223 se prepara la query
            //la cual espera parametros que son los nuevos valores de los campos a actualizar

            preparedStatement.setString(1, numTarjeta); //00021223 se manda el primer valor (nuevo numero de tarjeta)
            preparedStatement.setDate(2, fechaExp); //00021223 segundo valor (nueva fecha de expiracion de la tarjeta)
            preparedStatement.setString(3, String.valueOf(tipo)); //00021223 tercer valor (nuevo tipo de tarjeta)

            if(facilitador == Facilitador.Visa) { //00021223 se verifica si el facilitador es Visa para mandarle facilitadorID = 1
                preparedStatement.setInt(4, 1); //00021223 cuarto valor (nuevo ID de facilitador)
            } else if(facilitador == Facilitador.MasterCard) { //00021223 se verifica si el facilitador es MasterCard para mandarle facilitadorID = 2
                preparedStatement.setInt(4, 2); //00021223 cuarto valor (nuevo ID de facilitador)
            } else if(facilitador == Facilitador.AmericanExpress) { //00021223 se verifica si el facilitador es AmericanExpress para mandarle facilitadorID = 3
                preparedStatement.setInt(4, 3); //00021223 cuarto valor (nuevo ID de facilitador)
            }

            preparedStatement.setInt(5, clienteID);
            preparedStatement.setInt(6, id); //00021223 ID de la tarjeta de la cual se quiere actualizar sus datos

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor de retorno
            //en una variable de tipo entero, retorna el numero de campos afectados

            alerta = new Alert(Alert.AlertType.INFORMATION); //00021223 se inicializa la alerta
            alerta.setTitle("Registro de Tarjetas"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Datos de tarjeta actualizados exitosamente"
                    : "Actualizacion de datos de tarjeta fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //0021223 se inserta el texto para el contenido de la alerta
            alerta.showAndWait(); //00021223 muestra la alerta


        } catch (SQLException ex) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
        }
    }

    @Override
    public void updateCompra(Date fechaCompra, double monto, String descripcion, int tarjetaID, int id) {
        //00021223 se define metodo abstracto para ejecutar la consulta para actualizar registros en la tabla compra en la bbdd
        //recibe los campos que se requieren actualizar en un registro y el ID del registro que se desea actualizar

        try { //00021223 control de excepciones requerido para ejecutar consultas a bbdd

            connection = getConnection(); //00021223 se establece la conexion a la base de datos

            preparedStatement = connection.prepareStatement(DataBaseUpdate.getUpdateQuery(2)); //00021223 se prepara la query
            //la cual espera parametros que son los nuevos valores de los campos a actualizar

            preparedStatement.setDate(1, fechaCompra); //00021223 se manda el primer valor (nuevo fecha de compra)
            preparedStatement.setDouble(2, monto); //00021223 segundo valor (nuevo monto total de la compra realizada)
            preparedStatement.setString(3, descripcion); //00021223 tercer valor (nueva descripcion de la tarjeta)
            preparedStatement.setInt(4, tarjetaID); //00021223 cuarto valor (nuevo ID de tarjeta al que esta asociada la compra)
            preparedStatement.setInt(5, id); //00021223 ID de la compra de la cual se quiere actualizar sus datos

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor de retorno
            //en una variable de tipo entero, retorna el numero de campos afectados

            alerta = new Alert(Alert.AlertType.INFORMATION); //00021223 se inicializa la alerta
            alerta.setTitle("Registro de Compras"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Datos de compra actualizados exitosamente"
                    : "Actualizacion de datos de compra fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //0021223 se inserta el texto para el contenido de la alerta
            alerta.showAndWait(); //00021223 muestra la alerta

        } catch (SQLException ex) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
        }
    }
}
