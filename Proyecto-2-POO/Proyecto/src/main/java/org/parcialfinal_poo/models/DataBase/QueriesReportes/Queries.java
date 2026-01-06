package org.parcialfinal_poo.models.DataBase.QueriesReportes;

import javafx.scene.control.Alert;
import org.parcialfinal_poo.models.Banco.Cliente;
import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.DataBase.Selects.Select;

import java.sql.*;
import java.util.ArrayList;

public class Queries extends DataBaseQueries {

    private static Queries instance; //00021223 instancia unica de la clase (implementacion de Singleton)

    private Queries() { //00021223 constructor privado de la clase para que solo pueda ser instanciada por ella misma

    }

    public static Queries getInstance() { //00021223 metodo para obtener la instancia unica de la clase
        if(instance == null) { //00021223 se verifica que la instancia no sea nula
            instance = new Queries(); //00021223 si la instancia es nula entonces crea el unico objeto que existira de la clase
        }
        return instance; //00021223 retorna la instancia unica de la clase
    }

    @Override
    public ArrayList<Compra> generarReporteA(int clienteID, Date fecha1, Date fecha2) {
        //00021223 se define metodo abstracto que se encarga de realizar la consulta a la base de datos para generar reporte A

        ArrayList<Compra> compras = new ArrayList<>(); //00021223 se inicializa un arraylist para almacenar los registros obtenidos de la base de datos

        try { //00021223 se realiza el control de excepciones requerido para consultas a bases de datos

            connection = getConnection(); //00021223 se establece la conexion a la base de datos por medio del
            //metodo estatico getConnection

            preparedStatement = connection.prepareStatement(DataBaseQueries.getReportQuery(1)); //00021223 se crea prepara a consulta para si ejecucion

            preparedStatement.setInt(1, clienteID);
            preparedStatement.setDate(2, fecha1);
            preparedStatement.setDate(3, fecha2);

            resultSet = preparedStatement.executeQuery(); //00021223 se ejecuta la consulta y el resultado de registros se almacena en el resultSet

            while(resultSet.next()) { //00021223 bucle while para recorrer el resultSet, la condicion es que el resultSet tenga un siguiente

                Compra compra = new Compra(); //00021223 se inicializa un objeto Compra para almacenarlo en el arraylist

                //00021223 se obtienen cada uno de sus campos del resultSet actual en la iteracion
                compra.setCodigo(resultSet.getInt("id")); //00021223 settea el ID de la compra
                compra.setFechaCompra(resultSet.getDate("fechaCompra")); //00021223 settea la fecha de la compra
                compra.setMonto(resultSet.getDouble("monto")); //00021223 settea el monto total de la compra
                compra.setDescripcion(resultSet.getString("descripcion")); //00021223 settea la descripcion de la compra
                compra.setTarjetaID(resultSet.getInt("tarjetaID")); //00021223 settea el ID de la tarjeta a la que esta asociada la compra

                compras.add(compra); //00021223 agrega el cliente obtenido a la lista de compras que se va a retornar
            }

            connection.close();

            return compras; //00021223 luego de haber obtenido todos los registros de compras de la base de datos, retorna la lista con cada uno de los registros como objetos Compra

        } catch (SQLException e) { //00021223 control de excepciones, cacha un SQLException que seria cualquier fallo en la ejecucion de la consulta
            //todo se debe mandar el mensaje de error por medio de una alerta
            System.out.println(e.getMessage()); //00021223 se imprime el mensaje de error atrapado

            return null; //00021223 si ocurre una excepcion, retorna una lista de compras nula
        }
    }

    @Override
    public double generarReporteB(int clienteID, int year, int month) {
        //00022423 se define metodo abstracto que se encarga de realizar la consulta a la base de datos para generar reporte A


        double total = 0;//00022423 se declara una variable llamada total y se inicializa en 0, esta almacenará el total gastado

        try {

            connection = getConnection(); //00022423 se establece la conexion a la base de datos a través del método getconnection()

            // 00022423 Se prepara una declaración SQL utilizando una consulta obtenida del método getReportQuery(2) de la clase DataBaseQueries.
            PreparedStatement prepareStatement = connection.prepareStatement(DataBaseQueries.getReportQuery(2));
            //00022423 Se establece el primer parámetro de la consulta preparada como el ID del cliente (clienteID).
            prepareStatement.setInt(1,clienteID);
            // 00022423 Se establece el segundo parámetro de la consulta preparada como el año de la fecha proporcionada.
            prepareStatement.setInt(2,year);
            //00022423 Se establece el tercer parámetro de la consulta preparada como el mes de la fecha proporcionada.
            prepareStatement.setInt(3,month);
            // 00022423 Se ejecuta la consulta y se obtiene el resultado en un objeto ResultSet.
            ResultSet rs = prepareStatement.executeQuery();

            boolean flag = true; //00042823 Se crea una bandera ya levantada para verificar si existe el cliente, asumiendo que clienteID contiene un ID de un cliente que no existe

            for (Cliente c : Select.getInstance().selectCliente()){ //00042823 Llamamos a la base de datos para verificar si, en efecto, existe el cliente
                if (clienteID == c.getId()){ //00042823 Verifica si clienteID coincide con el ID de algún cliente
                    flag = false; //00042823 Si encuentra una coincidencia, se baja la bandera
                    break; //00042823 Se rompe el bucle, ya no es necesario buscar más
                }
            }

            if (rs.next()){
                if (flag){ //Si la bandera está alzada...
                    total = -1; // 00042823 Se coloca -1 para indicar que, no solo no gasto nada, sino que no existe el cliente para empezar
                }
                else {
                    // 00022423 Si hay un resultado, se obtiene el valor de la primera columna y se asigna a la variable total.
                    total = rs.getDouble(1);
                }
            }


        }catch (SQLException e){
            // 00022423 En caso de que ocurra una excepción SQL, se imprime el mensaje de error.
            System.out.println(e.getMessage());
        }
        //00022423 Se retorna el valor total obtenido en la consulta
        return total;
    }

    @Override
    public void generarReporteC(int clienteID, ArrayList<String> tarjetasCredito, ArrayList<String> tarjetasDebito) { //00088023 método que se encarga de realizar el reporte C
        Connection connection = null; //00088023 Se inicia una conexión nula que servirá más adelante
        try {
            connection = getConnection();//00088023 Recibe la conexión a la base de datos

            PreparedStatement stm = connection.prepareStatement(DataBaseQueries.getReportQuery(3)); //00088023 Prepara el statement de la query
            stm.setString(1, "Credito");//00088023 Le agrega el primer parámetro, siendo este el tipo de tarjeta
            stm.setInt(2, clienteID);//00088023 Le agrega el segundo parámetro, el id del cliente
            ResultSet rs = stm.executeQuery();//00088023 Se hace un result set que devuelve las tarjetas de crédito
            fillArraysTarjetas(rs, tarjetasCredito);//00088023 manda a llamar al método para llenar el array de tarjetas de crédito

            stm.setString(1, "Debito"); //00088023 Cambia el statement para recibir las tarjetas de débito
            rs = stm.executeQuery();//00088023 Ejecuta la query y lo almacena en el result set
            fillArraysTarjetas(rs, tarjetasDebito);//00088023 manda a llamar a la función para llenar el array de tarjetas de crédito


        }catch (Exception e){ //00088023 En el caso de que sucesa un error en la conexión manda una alerta
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se encontro el cliente o hubo un error al ingresar los datos");
            alert.showAndWait();
        } finally {
            if (connection != null){ //00088023 Al finalizar verifica si la conexión es nula
                try{
                    connection.close();//00088023 Si se logro inicializar la conexión, entonces la cierra
                } catch (SQLException e) { //00088023 Si hubo un error al cerrar la conexión, entonces alerta al usuario
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error al cerrar el archivo");
                    alert.showAndWait();
                }
            }
        }
    }

    public ResultSet generarReporteD(Facilitador facilitador) { //00042823 General el resultSet del reporte D, que se usará directamente en el GUI
        try { //00042823 Para capturar errores que ocurran respecto a la conexión con la base de datos
            connection = getConnection(); //00042823 Se abre la conexión con la base de datos

            PreparedStatement preparedStatement = connection.prepareStatement(DataBaseQueries.getReportQuery(4)); //00042823 Crea una forma segura de agregar parámetros a las consultas, siendo la consulta la necesaria para el reporte D
            preparedStatement.setString(1, facilitador.toString()); //00042823 De manera segura se agrega el parámetro como atributo de un objeto, esto en el primer (y único) parámetro para la consulta

            return preparedStatement.executeQuery(); //00042823 El método devuelve un objeto ResultSet, el cual es el valor de la función. Esto para luego obtener datos y usarlos directamente
        }
        catch (SQLException e){ //00042823 Si llegara a haber algún tipo de error en la conexión o en lógica de la consulta...
            e.printStackTrace(); //00042823 ... Se imprime la cadena de excepciones que produjo el error
            return null; //00042823 Estoy forzado a devolver algo, así que si algo sale mal, la función devuelve nulo (rompemos el programa de todas formas)
        }

    }


    private void fillArraysTarjetas(ResultSet rs, ArrayList<String> tarjetas) throws SQLException { //00088023 método encargado de llenar los arrays de tarjetas
        while (rs.next()){//00088023 mientras el resultset todavia tenga datos seguira realizando el bucle
            StringBuilder num = new StringBuilder();//00088023 Se crea un string builder para realizar la censura
            String[] Tarjeta = rs.getString("numTarjeta").split(" "); //00088023 Obtiene el numero de la tarjeta del result set y la separa por espacios
            for (int i = 0; i < Tarjeta.length; i++){ //00088023 crea un bucle que se repetira tantas veces como elementos tenga
                if (i == Tarjeta.length - 1){//00088023 Si es la última iteración, entonces agrega los últimos cuatro dígitos de la tarjeta
                    num.append(Tarjeta[i]);
                } else {
                    num.append("XXXX "); //00088023 En el caso que sea cualquier otra iteración, entonces ecnsurará los números agregando un XXXX a la tarjeta
                }
            }

            tarjetas.add(String.valueOf(num));//00088023 finalmente agrega el resultado al array de tarjetas
        }
    }
}
