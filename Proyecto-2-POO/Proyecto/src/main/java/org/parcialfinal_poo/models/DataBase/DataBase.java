package org.parcialfinal_poo.models.DataBase;

import javafx.scene.control.Alert;

import java.sql.*;

public abstract class DataBase implements ConnectionInterface { //00021223 clase para manejar todos los aspectos de consultas a la bbdd

    protected Alert alerta; //00021223 variable para almacenar el tipo de alerta y ejecutar una alerta

    private static final String url = "jdbc:mysql://localhost:3306/dbBCN";
    private static final String user = "gabo7";
    private static final String password = "Afb092ebbf$";

    protected static Connection connection; //00021223 campo para almacenar la conexion a la base de datos

    protected static PreparedStatement preparedStatement; //00021223 campo para preparar y ejecutar cada consulta requerida

    protected static ResultSet resultSet; //00021223 campo para almacenar los resultados de las consultas que lo requieran

    @Override
    public Connection getConnection() throws SQLException{ //00021223 metodo para obtener la conexion a la base de datos
        return DriverManager.getConnection(url, user, password); //00021223 retorna la conexion a la base de datos
    }



    //todo metodo para actualizar registros en la base de datos

    //todo metodo para eliminar registros en la base de datos

    //todo metodo para ejecutar consulta para reporte A

    //todo metodo para ejecutar consulta para reporte B

    //todo metodo para ejecutar consulta para reporte C

    //todo metodo para ejecutar consulta para reporte D

}
