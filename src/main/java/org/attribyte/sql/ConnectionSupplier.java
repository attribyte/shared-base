package org.attribyte.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * An interface that identifies objects that supply JDBC connections (like pools).
 * @author Matt Hamer - Attribyte, LLC
 */
public interface ConnectionSupplier {

   /**
    * Gets a connection.
    * @return A connection.
    * @throws SQLException If the connection was not acquired.
    */
   public Connection getConnection() throws SQLException;

}
