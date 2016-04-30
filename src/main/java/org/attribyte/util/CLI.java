/*
 * Copyright 2016 Attribyte, LLC
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

import org.attribyte.api.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Creates a command line interface with properties and a logger.
 */
public abstract class CLI {

   /**
    * The default system property for the install directory ('install.dir').
    */
   public static final String DEFAULT_INSTALL_DIR_PROP = "install.dir";

   /**
    * Creates the command line interface.
    * @param args The command line arguments.
    */
   protected CLI(final String appName, final String args[]) throws IOException {
      this(appName, DEFAULT_INSTALL_DIR_PROP, args);
   }

   /**
    * Creates the command line interface with a custom system property name for the install directory.
    * @param installDirSystemProperty A system property that indicates the install directory.
    * @param args The command line arguments.
    */
   protected CLI(final String appName, final String installDirSystemProperty, String args[]) throws IOException {
      this.appName = appName;
      Properties commandLineOverrides = new Properties();
      args = InitUtil.fromCommandLine(args, commandLineOverrides);
      loadProperties(args, props, logProps, installDirSystemProperty);
      props.putAll(commandLineOverrides);
      logProps.putAll(commandLineOverrides);
      this.logger = initLogger();
   }

   /**
    * Loads properties from filenames supplied on the command line.
    * @param filenames The filenames.
    * @param props The properties to fill.
    * @param logProps The properties to fill if the original file starts with 'log.'.
    * @throws IOException on load error.
    */
   private void loadProperties(final String[] filenames,
                               final Properties props,
                               final Properties logProps,
                               String installDirSystemProperty) throws IOException {

      for(String filename : filenames) {

         File f = new File(filename);

         if(!f.exists()) {
            logError(String.format("Start-up error: The configuration file, '%s' does not exist", f.getAbsolutePath()));
            System.exit(0);
         }

         if(!f.canRead()) {
            logError(String.format("Start-up error: The configuration file, '%s' is not readable", f.getAbsolutePath()));
            System.exit(0);
         }

         FileInputStream fis = null;
         Properties currProps = new Properties();

         try {
            fis = new FileInputStream(f);
            currProps.load(fis);
            if(f.getName().startsWith("log.")) {
               logProps.putAll(resolveRelativeFiles(currProps, installDirSystemProperty));
            } else {
               props.putAll(resolveRelativeFiles(currProps, installDirSystemProperty));
            }
         } finally {
            if(fis != null) {
               fis.close();
            }
         }
      }
   }

   /**
    * Examines configuration keys for those end in '.file'.
    * If their values don't begin with '/', the system install directory
    * is prepended.
    *
    * @param props The properties.
    * @return The properties with modified values.
    */
   private Properties resolveRelativeFiles(final Properties props, final String installDirSystemProperty) {

      Properties filteredProps = new Properties();
      String systemInstallDir = systemInstallDir(installDirSystemProperty);

      for(String key : props.stringPropertyNames()) {
         if(key.toLowerCase().endsWith(".file")) {
            String filename = props.getProperty(key);
            if(filename.startsWith("/")) {
               filteredProps.put(key, filename);
            } else if(installDirSystemProperty != null) {
               filteredProps.put(key,  systemInstallDir + filename);
            }
         } else {
            filteredProps.put(key, props.getProperty(key));
         }
      }

      return filteredProps;
   }

   /**
    * Gets the system install directory (always ends with '/').
    * @param installDirSystemProperty The system property containing the install directory.
    * @return The directory.
    */
   private String systemInstallDir(String installDirSystemProperty) {
      String systemInstallDir = System.getProperty(installDirSystemProperty, "").trim();
      if(systemInstallDir.length() > 0 && !systemInstallDir.endsWith("/")) {
         systemInstallDir = systemInstallDir + "/";
      }
      return systemInstallDir;
   }

   /**
    * Initialize the logger.
    * @return The logger.
    */
   protected abstract Logger initLogger();

   /**
    * The application name.
    */
   public final String appName;

   /**
    * The properties.
    */
   public final Properties props = new Properties();

   /**
    * The logger configuration properties.
    */
   public final Properties logProps = new Properties();

   /**
    * The application logger.
    */
   public final Logger logger;

   /**
    * Logs to both the console and the log file.
    * @param message The message.
    */
   public final void logInfo(final String message) {
      if(logger != null) {
         logger.info(message);
      }
      System.out.println(message);
   }

   /**
    * Logs an error to both the console and log file.
    * @param message The error message.
    */
   public final void logError(final String message) {
      if(logger != null) {
         logger.error(message);
      }
      System.err.println(message);
   }

   /**
    * Logs an error to both the console and log file.
    * @param message The error message.
    * @param t A throwable.
    */
   public final void logError(final String message, final Throwable t) {
      if(logger != null) {
         logger.error(message, t);
      }
      System.err.println(message);
      t.printStackTrace();
   }
}
