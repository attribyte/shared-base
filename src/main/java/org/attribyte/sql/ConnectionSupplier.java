/*
 * Copyright 2010 Attribyte, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

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
