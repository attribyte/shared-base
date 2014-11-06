package org.attribyte.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * SQL utilities.
 * @author Matt Hamer - Attribyte, LLC
 */
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
