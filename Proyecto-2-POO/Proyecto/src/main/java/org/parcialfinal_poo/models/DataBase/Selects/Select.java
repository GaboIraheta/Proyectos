package org.parcialfinal_poo.models.DataBase.Selects;

import javafx.scene.control.Alert;
import org.parcialfinal_poo.models.Banco.Cliente;
import org.parcialfinal_poo.models.Banco.Compra;
import org.parcialfinal_poo.models.Banco.Tarjetas.Facilitador;
import org.parcialfinal_poo.models.Banco.Tarjetas.Tarjeta;
import org.parcialfinal_poo.models.Banco.Tarjetas.TipoTarjeta;

import javax.xml.validation.SchemaFactoryConfigurationError;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Select extends DataBaseSelect {

    private static Select instance; //00021223 instancia unica de la clase

    private Select() { //00021223 constructor privado para que la clase solo pueda ser instanciada por ella misma

    }

    public static Select getInstance() { //00021223 metodo para obtener la instancia unica de la clase
        if (instance == null) { //00021223 verifica si la instancia es nula
            instance = new Select(); //00021223 si es nula crea el objeto
        }
        return instance; //00021223 retorna la instancia
    }

    @Override
    public ArrayList<Cliente> selectCliente() {
        //00021223 se define el metodo abstracto para consultar los clientes, los registros son devueltos almacenados en una lista

        ArrayList<Cliente> clientes = new ArrayList<>(); //00021223 se inicializa un arraylist para almacenar los registros obtenidos de la base de datos

        try { //00021223 control de excepciones requerido para ejecutar consultas a la base de datos

            connection = getConnection(); //00021223 se establece la conexion a la base de datos

            Statement statement = connection.createStatement(); //00021223 se crea el statement para poder ejecutar la consulta

            resultSet = statement.executeQuery(DataBaseSelect.getSelectQuery(0)); //00021223 se ejecuta la query requerida
            //y se guarda el resultado en un objeto ResultSet para poder acceder y manipular los datos obtenidos

            while(resultSet.next()) { //00021223 bucle while para recorrer el resultSet, la condicion es que el resultSet tenga un siguiente

                Cliente cliente = new Cliente(); //00021223 se inicializa un objeto Cliente para almacenarlo en el arraylist

                //00021223 se obtienen cada uno de sus campos del resultSet actual en la iteracion
                cliente.setId(resultSet.getInt("id")); //00021223 settea el ID del cliente
                cliente.setNombres(resultSet.getString("nombres")); //00021223 settea los nombres del cliente
                cliente.setApellidos(resultSet.getString("apellidos")); //00021223 settea los apellidos del cliente
                cliente.setDireccion(resultSet.getString("direccion")); //00021223 settea la direccion del cliente
                cliente.setTelefono(resultSet.getString("numTelefono")); //00021223 settea el telefono del cliente

                clientes.add(cliente); //00021223 agrega el cliente obtenido a la lista de clientes que se va a retornar
            }

            return clientes; //00021223 luego de haber obtenido todos los registros de clientes de la base de datos, retorna la lista con cada uno de los registros como objetos Cliente

        } catch (SQLException ex) { //00021223 control de excepciones, atrapa un SQLException el cual seria cualquier error durante la ejecucion de la consulta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
            return null; //00021223 si ocurre una excepcion, retorna una lista de clientes nula
        }
    }

    @Override
    public ArrayList<Tarjeta> selectTarjeta() {
        //00021223 se define el metodo abstracto para consultar las tarjetas, los registros son devueltos almacenados en una lista

        ArrayList<Tarjeta> tarjetas = new ArrayList<>(); //00021223 se inicializa un arraylist para almacenar los registros obtenidos de la base de datos

        try { //00021223 control de excepciones requerido para ejecutar consultas a la base de datos

            connection = getConnection(); //00021223 se establece la conexion a la base de datos

            Statement statement = connection.createStatement(); //00021223 se crea el statement para poder ejecutar la consulta

            resultSet = statement.executeQuery(DataBaseSelect.getSelectQuery(1)); //00021223 se ejecuta la query requerida
            //y se guarda el resultado en un objeto ResultSet para poder acceder y manipular los datos obtenidos

            while(resultSet.next()) { //00021223 bucle while para recorrer el resultSet, la condicion es que el resultSet tenga un siguiente

                Tarjeta tarjeta = new Tarjeta(); //00021223 se inicializa un objeto tarjeta para almacenarlo en el arraylist

                //00021223 se obtienen cada uno de sus campos del resultSet actual en la iteracion
                tarjeta.setId(resultSet.getInt("id")); //00021223 settea el ID de la tarjeta
                tarjeta.setClienteID(resultSet.getInt("clienteID")); //00021223 settea el ID del cliente al que esta asociada la tarjeta
                tarjeta.setNumeroTarjeta(resultSet.getString("numTarjeta")); //00021223 settea el numero de la tarjeta
                tarjeta.setFechaExpiracion(resultSet.getDate("fechaExp")); //00021223 settea la fecha de expiracion de la tarjeta
                tarjeta.setTipo(TipoTarjeta.valueOf(resultSet.getString("tipo"))); //00021223 settea el tipo de tarjeta

                Facilitador facilitador = null; //00021223 se inicializa una variable de tipo Facilitador en nulo que sera para almacenar
                //el facilitador de la tarjeta segun el ID de facilitador que este almacenado en el campo facilitadorID de la tabla Tarjeta en la bbdd

                if(resultSet.getInt("facilitadorID") == 1) { //00021223 se verifica si el ID es de Visa
                    facilitador = Facilitador.Visa; //00021223 si es de Visa entonces se almacena ese valor en la variable facilitador
                } else if(resultSet.getInt("facilitadorID") == 2) { //00021223 se verifica si el ID es de MasterCard
                    facilitador = Facilitador.MasterCard; //00021223 si es de MasterCard entonces se almacena ese valor en la variable facilitador
                } else if(resultSet.getInt("facilitadorID") == 3) { //00021223 se verifica si el ID es de AmericanExpress
                    facilitador = Facilitador.AmericanExpress; //00021223 si es de AmericanExpress se almacena ese valor en la variable facilitador
                }
                tarjeta.setFacilitador(facilitador); //00021223 settea el facilitador de la tarjeta

                tarjetas.add(tarjeta); //00021223 agrega la tarjeta obtenido a la lista de tarjetas que se va a retornar
            }

            return tarjetas; //00021223 luego de haber obtenido todos los registros de tarjetas de la base de datos, retorna la lista con cada uno de los registros como objetos Tarjeta

        } catch (SQLException ex) { //00021223 control de excepciones, atrapa un SQLException el cual seria cualquier error durante la ejecucion de la consulta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
            return null; //00021223 si ocurre una excepcion, retorna una lista de tarjetas nula
        }
    }

    @Override
    public ArrayList<Compra> selectCompra() {
        //00021223 se define el metodo abstracto para consultar las compras, los registros son devueltos almacenados en una lista

        ArrayList<Compra> compras = new ArrayList<>(); //00021223 se inicializa un arraylist para almacenar los registros obtenidos de la base de datos

        try { //00021223 control de excepciones requerido para ejecutar consultas a la base de datos

            connection = getConnection(); //00021223 se establece la conexion a la base de datos

            Statement statement = connection.createStatement(); //00021223 se crea el statement para poder ejecutar la consulta

            resultSet = statement.executeQuery(DataBaseSelect.getSelectQuery(2)); //00021223 se ejecuta la query requerida
            //y se guarda el resultado en un objeto ResultSet para poder acceder y manipular los datos obtenidos

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

            return compras; //00021223 luego de haber obtenido todos los registros de compras de la base de datos, retorna la lista con cada uno de los registros como objetos Compra

        } catch (SQLException ex) { //00021223 control de excepciones, atrapa un SQLException el cual seria cualquier error durante la ejecucion de la consulta
            System.out.println(ex.getMessage()); //00021223 se imprime el mensaje de error atrapado
            return null; //00021223 si ocurre una excepcion, retorna una lista de compras nula
        }

    }

    public ArrayList<String> selectCustomers(){//00088023 Método para regresar un arrayList con el id y los nombres de los clientes
        ArrayList<String> customers = new ArrayList<>();//00088023 Se inicializa el array a retornar
        Connection connection = null;//00088023 Se inicializa la conexión como null

        try {
            connection = getConnection(); //00088023 Se inicializa la conexión
            Statement stm = connection.createStatement();//00088023 Se prepara un statement
            ResultSet rs = stm.executeQuery("select id, nombres from Cliente"); //00088023 Se hace la query del ID y los nombres

            while (rs.next()){//00088023 Mientras el result set tenga datos, entonces agregara los datos al arrayList
                customers.add(rs.getString("id") + " " + rs.getString("nombres"));
            }

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR); //00088023 En caso de que algo haya salido mal, entonces avisa con una alerta
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Error al realizar la query");
            alert.showAndWait();
            System.out.println(e.getMessage());
        }
        finally {
            if(connection != null){
                try{
                    connection.close(); //00088023 Finalmente si la conexión fue exitosa, cierra la conexión
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return customers; //00088023 retorna el array cargado de los clientes.
    }
}
