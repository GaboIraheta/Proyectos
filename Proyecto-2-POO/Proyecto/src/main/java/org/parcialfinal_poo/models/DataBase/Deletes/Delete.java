package org.parcialfinal_poo.models.DataBase.Deletes;

import javafx.scene.control.Alert;

import java.sql.SQLException;

public class Delete extends DataBaseDelete {

    private static Delete instance; //0021223 instancia unica de la clase

    private Delete() { //00021223 constructor privado para que la clase pueda ser instanciada unicamente por ella misma

    }

    public static Delete getInstance() { //00021223 metodo estatico para obtener la instancia unica de la clase
        if (instance == null) { //00021223 se verifica si la instancia es nula
            instance = new Delete(); //00021223 si es nula entonces se crea el objeto
        }
        return instance; //00021223 retorna la instancia unica
    }

    // 00022423 Método  para verificar si un ID existe en una tabla específica
    private boolean existeID(String tabla, int id){
        try {
            //00022423  Establece la conexión a la base de datos
            connection = getConnection();
            // 00022423 Prepara la consulta SQL para contar el número de registros con el ID especificado
            String text = "select count(*) from " + tabla + " where id = ?";
            preparedStatement = connection.prepareStatement(text);
            // 00022423 Asigna el ID al primer parámetro de la consulta
            preparedStatement.setInt(1,id);
            // 00022423b Ejecuta la consulta
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            // 00022423 Si ocurre un error, se lanza una RuntimeException
            throw new RuntimeException(e);
        }
        try {
            // 00022423 Devuelve verdadero si el conteo es mayor que cero, lo que indica que el ID existe
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            // 00022423 Si ocurre un error, se lanza una RuntimeException
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCliente(int id) {
        //00021223 se define metodo abstracto para ejecutar la consulta para eliminar registros en la tabla cliente en la bbdd


        try { //00021223 control de excepciones requerido para ejecutar consultas a bbdd

            if(existeID("Cliente",id)) {
                connection = getConnection(); //00021223 se establece la conexion a la base de datos

                preparedStatement = connection.prepareStatement(DataBaseDelete.getDeleteQuery(0)); //00021223 se prepara la query
                //la cual espera un unico parametro que es el ID del cliente que se desea eliminar

                preparedStatement.setInt(1, id); //00021223 se le manda el unico parametro que espera la query (ID del cliente)

                int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor de retorno
                //en una variable de tipo entero, retorna el numero de campos afectados

                // 0002423 Ejecuta la consulta y obtiene el número de filas afectadas.
                return affectedRows > 0;

            }else{
                //00022423 crea una nueva alerta de tipo error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                //00022423 establece el titulo de la alerta
                alert.setTitle("Error");
                //00022423 Establece el encabezado de la alerta como nulo para no mostrar ningún encabezado en la alerta.
                alert.setHeaderText(null);
                //00022423 indica el mensaje que contiene la alerta
                alert.setContentText("Error, el cliente con ese id no existe");
                // 00022423 Muestra la alerta y espera hasta que el usuario la cierre.
                alert.showAndWait();
                //00022423 retorna falso indicando que no se pudo eliminar
                return false;
            }
        } catch (SQLException ex) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
            return false;
        }
    }

    @Override
    public boolean deleteTarjeta(int id) {
        //00021223 se define metodo abstracto para ejecutar la consulta para eliminar registros en la tabla tarjeta en la bbdd
        //unicamente recibe el ID de la tarjeta que se desea eliminar

        try { //00021223 control de excepciones requerido para ejecutar consultas a bbdd

            if (existeID("Tarjeta", id)) {
                connection = getConnection(); //00021223 se establece la conexion a la base de datos

                preparedStatement = connection.prepareStatement(DataBaseDelete.getDeleteQuery(1)); //00021223 se prepara la query
                //la cual espera un unico parametro el cual es el ID de la tarjeta que se desea eliminar

                preparedStatement.setInt(1, id); //00021223 se le manda el unico parametro que espera la query (ID de la tarjeta)

                int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor de retorno
                //en una variable de tipo entero, retorna el numero de campos afectados

                return affectedRows > 0; // 0002423 Ejecuta la consulta y obtiene el número de filas afectadas.
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR); //00022423 crea una nueva alerta de tipo error
                alert.setTitle("Error");//00022423 establece el titulo de la alerta
                alert.setHeaderText(null);  //00022423 Establece el encabezado de la alerta como nulo para no mostrar ningún encabezado en la alerta.
                alert.setContentText("Error, el id de la tarjeta no existe"); //00022423 indica el mensaje de error
                alert.showAndWait(); //00022423 Muestra la alerta y espera hasta que el usuario la cierre.
                return false; //00022423 retorna falso indicando que no se pudo eliminar
            }
        } catch (SQLException e) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(e.getMessage()); //00021223 se imprime el mensaje de error atrapado
            return false;
        }
    }

    public boolean deleteCompra(int id) {
        //00021223 se define metodo abstracto para ejecutar la consulta para eliminar registros en la tabla compra en la bbdd
        //unicamente recibe el ID de la compra que se desea eliminar

        try { //00021223 control de excepciones requerido para ejecutar consultas a bbdd
            if (existeID("Compra", id)) {
                connection = getConnection(); //00021223 se establece la conexion a la base de datos

                preparedStatement = connection.prepareStatement(DataBaseDelete.getDeleteQuery(2)); //00021223 se prepara la query
                //la cual espera un unico parametro el cual es el ID de la compra que se desea eliminar

                preparedStatement.setInt(1, id); //00021223 se le manda el unico parametro que espera la query (ID de la compra)

                int affectedRows = preparedStatement.executeUpdate(); //00021223 se ejecuta la query y se guarda el valor de retorno
                //en una variable de tipo entero, retorna el numero de campos afectados

                // 0002423 Ejecuta la consulta y obtiene el número de filas afectadas.
                return affectedRows > 0;

            } else {
                //00022423 crea una alerta de tipo error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                //00022423 establece el titulo de la alerta
                alert.setTitle("Error");
                //00022423 Establece el encabezado de la alerta como nulo para no mostrar ningún encabezado en la alerta.
                alert.setHeaderText(null);
                //00022423 indica el mensaje que contiene la alerta
                alert.setContentText("Error, el id no existe");
                //00022423 Muestra la alerta y espera hasta que el usuario la cierre.
                alert.showAndWait();
                //00022423 retorna falso indicando que no se pudo eliminar
                return false;
            }
        } catch (
                SQLException ex) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
            return false;
        }
    }
}
