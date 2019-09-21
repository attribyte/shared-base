/*
 * Copyright 2010-2019 Attribyte, LLC
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

package org.attribyte.api;

/**
 * Defines logging methods.
 */
public interface Logger {

   /**
    * Logs a debug message.
    * @param msg The message.
    */
   public void debug(String msg);

   /**
    * Logs an info message.
    * @param msg The message.
    */
   public void info(String msg);

   /**
    * Logs a notice.
    * @param msg The message.
    */
   public default void notice(String msg) {
      info(msg);
   }

   /**
    * Logs a warning message.
    * @param msg The message.
    */
   public void warn(String msg);

   /**
    * Logs a warning message with an exception.
    * @param msg The message.
    * @param t The exception.
    */
   public void warn(String msg, Throwable t);

   /**
    * Logs an alert message.
    * @param msg The message.
    */
   public default void alert(String msg) {
      warn(msg);
   }

   /**
    * Logs an alert message with an exception.
    * @param msg The message.
    * @param t The exception.
    */
   public default void alert(String msg, Throwable t) {
      warn(msg, t);
   }

   /**
    * Logs an error message.
    * @param msg The message.
    */
   public void error(String msg);

   /**
    * Logs an error with an exception.
    * @param msg The message.
    * @param t The exception.
    */
   public void error(String msg, Throwable t);

   /**
    * Logs a critical error message.
    * @param msg The message.
    */
   public default void critical(String msg) {
      error(msg);
   }

   /**
    * Logs a critical error with an exception.
    * @param msg The message.
    * @param t The exception.
    */
   public default void critical(String msg, Throwable t) {
      error(msg, t);
   }

   /**
    * Logs an emergency error message.
    * @param msg The message.
    */
   public default void emergency(String msg) {
      error(msg);
   }

   /**
    * Logs an emergency error with an exception.
    * @param msg The message.
    * @param t The exception.
    */
   public default void emergency(String msg, Throwable t) {
      error(msg, t);
   }

   /**
    * Immediately flush any deferred messages, if any.
    */
   public default void flush() {}

   /**
    * Shutdown the logger.
    */
   public default void shutdown() {}
}