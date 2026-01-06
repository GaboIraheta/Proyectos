package org.parcialfinal_poo.models.DataBase.Inserts;

import javafx.scene.control.Alert;
import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;
import org.parcialfinal_poo.models.DataBase.Selects.Select;

import java.sql.Date;
import java.sql.SQLException;

public class Insert extends DataBaseInserts {

    private static Insert instance; //00021223 instancia unica de la clase (implementacion de Singleton)

    private Insert() { //00021223 constructor privado de la clase para que solo pueda ser instanciada por ella misma

    }

    public static Insert getInstance() { //00021223 metodo para obtener la instancia unica de la clase
        if(instance == null) { //00021223 se verifica que la instancia no sea nula
            instance = new Insert(); //00021223 si la instancia es nula entonces crea el unico objeto que existira de la clase
        }
        return instance; //00021223 retorna la instancia unica de la clase
    }

    @Override
    public void registrarCliente(String nombres, String apellidos, String direccion, String numTelefono) {
        //00021223 se define metodo abstracto que se encarga de registrar a un cliente en la base de datos, recibe todos los campos necesarios
        //que debe guardar la tabla de Cliente en la bbdd

        int lastID = Select.getInstance().selectCliente().getLast().getId(); //00021223 se obtiene el ID del ultimo registro de cliente almacenado

        try { //00021223 se realiza el control de excepciones requerido para consultas a bases de datos

            connection = getConnection(); //00021223 se establece la conexion a la base de datos por medio del
            //metodo estatico getConnection

            preparedStatement = connection.prepareStatement(DataBaseInserts.getInsertQuery(0)); //00021223 se prepara la query que se va a ejecutar
            //dicha query espera parametros los cuales son los valores de los campos a insertar

            preparedStatement.setInt(1, lastID + 1); //00021223 se manda el primer valor (ID del cliente)
            preparedStatement.setString(2, nombres); //00021223 se manda el segundo valor (nombres del cliente)
            preparedStatement.setString(3, apellidos); //00021223 tercer valor (apellidos del cliente)
            preparedStatement.setString(4, direccion); //00021223 cuarto valor (direccion del cliente)
            preparedStatement.setString(5, numTelefono); //00021223 quinto valor (telefono del cliente)

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor
            //de retorno en una variable de tipo entero, dado que retorna los el número de campos afectados

            alerta = new Alert(Alert.AlertType.INFORMATION); //00021223 se inicializa la alerta
            alerta.setTitle("Registro de Cliente"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Cliente registrado exitosamente"
                    : "Registro de cliente fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //0021223 se inserta el texto para el contenido de la alerta
            alerta.showAndWait(); //00021223 muestra la alerta

        } catch (SQLException e) { //00021223 control de excepciones, atrapa un SQLException que sería cualquier fallo en la ejecucion de la consulta
            Alert alert = new Alert(Alert.AlertType.ERROR);//00088023 Se muestra una alerta en caso de que exista un error con los datos
            alert.setTitle("Error de datos");
            alert.setHeaderText(null);
            alert.setContentText("Uno de los datos ingresados no esta en un formato aceptado");

        }
    }

    @Override
    public void registrarTarjeta(int clienteID, String numTarjeta, Date fechaExp,
                                 TipoTarjeta tipo, Facilitador facilitador) {
        //00021223 se define metodo abstracto pare registrar una tarjeta en la base de datos, recibe todos los campos necesarios que
        //debe guardar la tabla Tarjeta en la bbdd
        Select select = Select.getInstance();

        int lastID = select.selectTarjeta().getLast().getId(); //00021223 se obtiene el ID del ultimo registro
            //de tarjeta almacenado en la lista de tarjetas del cliente con ID = clienteID

        try { //00021223 control de excepciones requerido para realizar consultas a bbdd

            connection = getConnection(); //00021223 se establece la conexion a la bbdd por medio del metodo estatico getConnection

            preparedStatement = connection.prepareStatement(DataBaseInserts.getInsertQuery(1)); //00021223 se prepara la query que se va a realizar
            //la query espera como parametros los valores que se van a insertar en la tabla Tarjeta

            preparedStatement.setInt(1, lastID + 1); //0021223 se manda el primer valor (ID de la tarjeta)
            preparedStatement.setInt(2, clienteID); //00021223 se manda el segundo valor (ID del cliente al que esta asociada la tarjeta)
            preparedStatement.setString(3, numTarjeta); //00021223 tercer valor (numero de la tarjeta)
            preparedStatement.setDate(4, fechaExp); //00021223 cuarto valor (fecha de expiracion)
            preparedStatement.setString(5, String.valueOf(tipo)); //00021223 quinto valor (tipo de tarjeta: credito o debito)

            if(facilitador == Facilitador.Visa) { //00021223 se verifica si el facilitador es Visa para mandarle facilitadorID = 1
                preparedStatement.setInt(6, 1); //00021223 quinto valor (ID del facilitador al que esta asociada la tarjeta)
            } else if(facilitador == Facilitador.MasterCard) { //00021223 se verifica si el facilitador es MasterCard para mandarle facilitadorID = 2
                preparedStatement.setInt(6, 2); //00021223 quinto valor (ID del facilitador al que esta asociada la tarjeta)
            } else if(facilitador == Facilitador.AmericanExpress) { //00021223 se verifica si el facilitador es AmericanExpress para mandarle facilitadorID = 3
                preparedStatement.setInt(6, 3); //00021223 quinto valor (ID del facilitador al que esta asociada la tarjeta)
            }

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la consulta y se almacena en una variable de tipo entero

            alerta = new Alert(Alert.AlertType.INFORMATION); //0021223 se inicializa una alerta
            alerta.setTitle("Registro de Tarjeta"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Tarjeta registrada exitosamente"
                    : "Registro de tarjeta fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //00021223 se insertan el numero de campos afectados en el contenido de la alerta
            alerta.showAndWait(); //00021223 muestra la alerta

        } catch (SQLException e) { //00021223 control de excepciones para atrapar cualquier excepcion referente a las consultas
            //todo se debe agregar una alerta
            System.out.println(e.getMessage()); //00021223 se imprime el mensaje de error
            e.printStackTrace();
        }
    }

    @Override
    public void registrarCompra(Date fechaCompra, double monto, String descripcion, int tarjetaID) {
        //00021223 se define metodo abstracto para registrar una compra en la base de datos, recibe todos los campos necesarios
        //que debe guardar la tabla Compra en la bbdd

        int lastID = Select.getInstance().selectCompra().getLast().getCodigo(); //00021223 se inicializa una variable para guardar el ID del ultimo registro de compra de la tarjeta con ID = tarjetaID

        try { //00021223 control de excepciones requerido para realizar consultas a bbdd

            connection = getConnection(); //00021223 se establece la conexion a la bbdd por medio del metodo estatico getConnection

            preparedStatement = connection.prepareStatement(DataBaseInserts.getInsertQuery(2)); //00021223 se prepara la query que se va a realizar
            //la query espera como parametros los valores que se van a insertar en la tabla Compra

            preparedStatement.setInt(1, lastID + 1); //00021223 se manda el primer valor (ID de la compra)
            preparedStatement.setDate(2, fechaCompra); //00021223 se manda el segundo valor (fecha de la compra)
            preparedStatement.setDouble(3, monto); //00021223 tercer valor (monto)
            preparedStatement.setString(4, descripcion); //00021223 cuarto valor (descripcion)
            preparedStatement.setInt(5, tarjetaID); //00021223 quinto valor (ID de la tarjeta que realiza la compra)

            int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la consulta y se almacena en una variable de tipo entero

            alerta = new Alert(Alert.AlertType.INFORMATION); //0021223 se inicializa una alerta
            alerta.setTitle("Registro de Compra"); //00021223 se establece el titulo de la alerta
            alerta.setHeaderText(affectedRows > 0 ? "Compra registrada exitosamente"
                    : "Registro de compra fallido"); //00021223 se inserta la informacion de la ejecucion de la consulta en el cuerpo de la alerta
            alerta.setContentText(affectedRows + "campos afectados"); //00021223 se insertan el numero de campos afectados en el contenido de la alerta
            alerta.showAndWait(); //00021223 muestra la alerta

        } catch (SQLException e) { //00021223 control de excepciones para atrapar cualquier excepcion referente a las consultas
            //todo se debe agregar una alerta
            System.out.println(e.getMessage()); //00021223 se imprime el mensaje de error
        }
    }
}
