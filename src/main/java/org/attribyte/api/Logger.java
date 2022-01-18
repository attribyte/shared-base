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

import static com.google.common.base.Strings.lenientFormat;

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
    * Logs a debug message with a template.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    */
   public default void debug(final String msgTemplate, final Object... messageArgs) {
      debug(lenientFormat(msgTemplate, messageArgs));
   }

   /**
    * Logs an info message.
    * @param msg The message.
    */
   public void info(String msg);

   /**
    * Logs an info message with a template.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    */
   public default void info(final String msgTemplate, final Object... messageArgs) {
      info(lenientFormat(msgTemplate, messageArgs));
   }

   /**
    * Logs a notice.
    * @param msg The message.
    */
   public default void notice(String msg) {
      info(msg);
   }

   /**
    * Logs a notice with a template.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    */
   public default void notice(final String msgTemplate, final Object... messageArgs) {
      notice(lenientFormat(msgTemplate, messageArgs));
   }

   /**
    * Logs a warning message.
    * @param msg The message.
    */
   public void warn(String msg);

   /**
    * Logs a warning with a template.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    */
   public default void warn(final String msgTemplate, final Object... messageArgs) {
      warn(lenientFormat(msgTemplate, messageArgs));
   }

   /**
    * Logs a warning message with an exception.
    * @param msg The message.
    * @param t The exception.
    */
   public void warn(String msg, Throwable t);

   /**
    * Logs a warning with a template and exception.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    * @param t The exception.
    */
   public default void warn(final String msgTemplate, Throwable t, final Object... messageArgs) {
      warn(lenientFormat(msgTemplate, messageArgs), t);
   }

   /**
    * Logs an alert message.
    * @param msg The message.
    */
   public default void alert(String msg) {
      warn(msg);
   }

   /**
    * Logs an alert with a template.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    */
   public default void alert(final String msgTemplate, final Object... messageArgs) {
      alert(lenientFormat(msgTemplate, messageArgs));
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
    * Logs an alert with a template and exception.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    * @param t The exception.
    */
   public default void alert(final String msgTemplate, Throwable t, final Object... messageArgs) {
      alert(lenientFormat(msgTemplate, messageArgs), t);
   }

   /**
    * Logs an error message.
    * @param msg The message.
    */
   public void error(String msg);


   /**
    * Logs an error message with a template.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    */
   public default void error(final String msgTemplate, final Object... messageArgs) {
      error(lenientFormat(msgTemplate, messageArgs));
   }

   /**
    * Logs an error with an exception.
    * @param msg The message.
    * @param t The exception.
    */
   public void error(String msg, Throwable t);

   /**
    * Logs an error with a template and exception.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    * @param t The exception.
    */
   public default void error(final String msgTemplate, Throwable t, final Object... messageArgs) {
      error(lenientFormat(msgTemplate, messageArgs), t);
   }

   /**
    * Logs a critical error message.
    * @param msg The message.
    */
   public default void critical(String msg) {
      error(msg);
   }

   /**
    * Logs a critical error message with a template.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    */
   public default void critical(final String msgTemplate, final Object... messageArgs) {
      critical(lenientFormat(msgTemplate, messageArgs));
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
    * Logs a critical error with a template and exception.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    * @param t The exception.
    */
   public default void critical(final String msgTemplate, Throwable t, final Object... messageArgs) {
      critical(lenientFormat(msgTemplate, messageArgs), t);
   }

   /**
    * Logs an emergency error message.
    * @param msg The message.
    */
   public default void emergency(String msg) {
      error(msg);
   }

   /**
    * Logs an emergency error message with a template.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    */
   public default void emergency(final String msgTemplate, final Object... messageArgs) {
      emergency(lenientFormat(msgTemplate, messageArgs));
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
    * Logs an emergency error with a template and exception.
    * @param msgTemplate The message template.
    * @param messageArgs The template args.
    * @param t The exception.
    */
   public default void emergency(final String msgTemplate, Throwable t, final Object... messageArgs) {
      emergency(lenientFormat(msgTemplate, messageArgs), t);
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