package org.parcialfinal_poo.models.DataBase;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionInterface {
    Connection getConnection() throws SQLException;
}
