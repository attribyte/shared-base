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

package org.attribyte.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * SQL utilities.
 * @author Matt Hamer - Attribyte, LLC
 * @deprecated Use try-with-resources instead. Since Java 7, {@link java.sql.Connection},
 * {@link java.sql.Statement}, and {@link java.sql.ResultSet} all implement {@link AutoCloseable}.
 * For example:
 * <pre>{@code
 * try (Connection conn = dataSource.getConnection();
 *      PreparedStatement stmt = conn.prepareStatement(sql);
 *      ResultSet rs = stmt.executeQuery()) {
 *    // use rs
 * }
 * }</pre>
 */
@Deprecated
public class SQLUtil {

   /**
    * Quietly closes a connection, statement and result set.
    * <p>
    * Catches and ignores any exceptions.
    * </p>
    * @param conn The connection.  May be null.
    * @param stmt The statement. May be null.
    * @param rs The result set. May be null.
    */
   public static final void closeQuietly(final Connection conn, final Statement stmt, final ResultSet rs) {

      if(rs != null) {
         try {
            rs.close();
         } catch(Exception e) {
            //Ignore
         }
      }

      if(stmt != null) {
         try {
            stmt.close();
         } catch(Exception e) {
            //Ignore
         }
      }

      if(conn != null) {
         try {
            conn.close();
         } catch(Exception e) {
            //Ignore
         }
      }
   }

   /**
    * Quietly closes a connection and a statement.
    * <p>
    * Catches and ignores any exceptions.
    * </p>
    * @param conn The connection.  May be null.
    * @param stmt The statement. May be null.
    */
   public static final void closeQuietly(final Connection conn, final Statement stmt) {

      if(stmt != null) {
         try {
            stmt.close();
         } catch(Exception e) {
            //Ignore
         }
      }

      if(conn != null) {
         try {
            conn.close();
         } catch(Exception e) {
            //Ignore
         }
      }
   }

   /**
    * Quietly closes a connection.
    * <p>
    * Catches and ignores any exceptions.
    * </p>
    * @param conn The connection.  May be null.
    */
   public static final void closeQuietly(final Connection conn) {

      if(conn != null) {
         try {
            conn.close();
         } catch(Exception e) {
            //Ignore
         }
      }
   }

   /**
    * Quietly closes a statement and result set.
    * <p>
    * Catches and ignores any exceptions.
    * </p>
    * @param stmt The statement. May be null.
    * @param rs The result set. May be null.
    */
   public static final void closeQuietly(final Statement stmt, final ResultSet rs) {

      if(rs != null) {
         try {
            rs.close();
         } catch(Exception e) {
            //Ignore
         }
      }

      if(stmt != null) {
         try {
            stmt.close();
         } catch(Exception e) {
            //Ignore
         }
      }
   }

   /**
    * Quietly closes a statement.
    * <p>
    * Catches and ignores any exceptions.
    * </p>
    * @param stmt The statement. May be null.
    */
   public static final void closeQuietly(final Statement stmt) {

      if(stmt != null) {
         try {
            stmt.close();
         } catch(Exception e) {
            //Ignore
         }
      }
   }

   /**
    * Quietly closes a result set.
    * <p>
    * Catches and ignores any exceptions.
    * </p>
    * @param rs The result set. May be null.
    */
   public static final void closeQuietly(final ResultSet rs) {

      if(rs != null) {
         try {
            rs.close();
         } catch(Exception e) {
            //Ignore
         }
      }
   }
}
