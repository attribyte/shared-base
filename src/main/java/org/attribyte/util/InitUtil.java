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

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.attribyte.api.InitializationException;

import java.util.*;

/**
 * Convenience methods for initializing from properties.
 * @author Matt Hamer - Attribyte, LLC.
 */
public class InitUtil {

   /**
    * Creates a properties init util with case-insensitive names.
    * @param prefix The prefix appended to all property names.
    * @param props The properties.
    */
   public InitUtil(final String prefix, final Properties props) {
      this(prefix, props, false);
   }

   @SuppressWarnings("unchecked")
   /**
    * Creates a properties init util with optionally case-insensitive names.
    * @param prefix The prefix appended to all property names.
    * @param props The properties.
    * @param lowercaseNames Should property names be lower-cased? That is, property methods are case-insensitive.
    */
   public InitUtil(final String prefix, final Properties props, final boolean lowercaseNames) {
      this.prefix = prefix;
      this.props = new Properties();
      this.lowercaseNames = lowercaseNames;

      //Include only properties with the specified prefix, maybe lower-case the property name and trim values.

      Set<String> propertyNames = Sets.newHashSetWithExpectedSize(8);
      Enumeration pn = props.propertyNames();
      while(pn.hasMoreElements()) {
         propertyNames.add((String)pn.nextElement());
      }

      for(String propertyName : propertyNames) {
         if(propertyName.startsWith(prefix)) {
            String newName = lowercaseNames ? propertyName.substring(prefix.length()).toLowerCase() : propertyName.substring(prefix.length());
            String val = props.getProperty(propertyName).trim();
            this.props.setProperty(newName, val);
         }
      }
   }

   /**
    * Gets new properties containing only those that begin with the initialized prefix.
    * <p>Prefix is removed from key name in returned properties.</p>
    * @return The new properties.
    */
   public Properties getProperties() {
      Properties newProps = new Properties();
      newProps.putAll(props);
      return newProps;
   }

   /**
    * Gets a map of properties vs name where name is supplied as a prefix, e.g.
    * <p>
    * <tt>prefix.[name].property=value</tt>.
    * </p>
    * @return The map of properties vs name.
    */
   public Map<String, Properties> split() {
      Map<String, Properties> propMap = Maps.newHashMapWithExpectedSize(4);
      for(Object k : props.keySet()) {
         String key = (String)k;
         int index = key.indexOf('.');
         if(index > 0 && index != (key.length() - 1)) {
            String name = key.substring(0, index);
            String newKey = key.substring(index + 1);
            String val = props.getProperty(key);
            Properties namedProps = propMap.get(name);
            if(namedProps == null) {
               namedProps = new Properties();
               propMap.put(name, namedProps);
            }
            namedProps.put(newKey, val);
         }
      }

      return propMap;
   }

   /**
    * Determine if a non-empty property exists.
    * @param propertyName The property name.
    * @return Does the property exist?
    */
   public boolean hasProperty(final String propertyName) {
      return !Strings.isNullOrEmpty(getProperty(propertyName));
   }

   /**
    * Gets a property.
    * @param propertyName The property name.
    * @return The property, or <tt>null</tt> if unspecified.
    */
   public final String getProperty(final String propertyName) {
      return props.getProperty(lowercaseNames ? propertyName.toLowerCase() : propertyName);
   }

   /**
    * Gets a property.
    * @param propertyName The property name.
    * @param defaultValue The default value.
    * @return The property, or the default value if unspecified.
    */
   public final String getProperty(final String propertyName, final String defaultValue) {
      return props.getProperty(lowercaseNames ? propertyName.toLowerCase() : propertyName, defaultValue);
   }

   /**
    * Gets a multi-valued property.
    * <p>
    * Index values are separated by ','. If index exceeds
    * the number of values, the last is returned.
    * </p>
    * @param propertyName The property name.
    * @param index The index.
    * @param defaultValue The default value. Used only if the property does not exist.
    * @return The property, or the default value if unspecified.
    */
   public final String getProperty(final String propertyName, final int index, final String defaultValue) {
      String value = props.getProperty(lowercaseNames ? propertyName.toLowerCase() : propertyName, defaultValue);
      if(value == null) {
         return null;
      }

      String[] values = value.split(",");
      if(index >= values.length) {
         return values[values.length - 1].trim();
      } else {
         return values[index].trim();
      }
   }

   /**
    * Gets a multi-valued integer property.
    * <p>
    * Index values are separated by ','. If index exceeds
    * the number of values, the last is returned.
    * </p>
    * @param propertyName The property name.
    * @param index The index.
    * @param defaultValue The default value. Used only if the property does not exist.
    * @return The property, or the default value if unspecified.
    * @throws org.attribyte.api.InitializationException if property is not an integer.
    */
   public final int getIntProperty(final String propertyName, final int index, final int defaultValue) throws InitializationException {
      String value = getProperty(propertyName, index, null);
      if(value == null) {
         return defaultValue;
      }

      Integer intVal = Ints.tryParse(value);
      if(intVal != null) {
         return intVal;
      } else {
         throw new InitializationException(String.format("The '%s%s' must be an integer", prefix, propertyName));
      }
   }

   /**
    * Gets a property as an integer.
    * @param propertyName The property name.
    * @param defaultValue The default value.
    * @return The property, or the default value if unspecified.
    * @throws InitializationException if property is not an integer.
    */
   public final int getIntProperty(final String propertyName, final int defaultValue) throws InitializationException {
      String value = getProperty(propertyName, "").trim();
      if(value.isEmpty()) {
         return defaultValue;
      }

      Integer intVal = Ints.tryParse(value);
      if(intVal != null) {
         return intVal;
      } else {
         throw new InitializationException(String.format("The '%s%s' must be an integer", prefix, propertyName));
      }
   }

   /**
    * Gets a property as a long.
    * @param propertyName The property name.
    * @param defaultValue The default value.
    * @return The property, or the default value if unspecified.
    * @throws InitializationException if property is not an integer.
    */
   public final long getLongProperty(final String propertyName, final long defaultValue) throws InitializationException {
      String value = getProperty(propertyName, "").trim();
      if(value.isEmpty()) {
         return defaultValue;
      }

      Long longVal = Longs.tryParse(value);
      if(longVal != null) {
         return longVal;
      } else {
         throw new InitializationException(String.format("The '%s%s' must be an integer", prefix, propertyName));
      }
   }

   /**
    * Create an instance of a class.
    * @param propertyName The property containing the class name.
    * @param expectedClass The expected class.
    * @return An instance of the class or null if the property was not specified.
    * @throws InitializationException if the class could not be found or instantiated.
    */
   public final Object initClass(final String propertyName, final Class<?> expectedClass) throws InitializationException {
      String className = getProperty(propertyName, "");
      return initClass(propertyName, className, expectedClass);
   }

   /**
    * Creates a list of instances of a class.
    * @param propertyName The name of a property containing a space-separated list of classes.
    * @param expectedClass The expected class.
    * @return The list containing instances of the class or an empty list if no property value.
    * @throws InitializationException if class could not be found or instantiated.
    */
   public final List<Object> initClassList(final String propertyName, final Class<?> expectedClass) throws InitializationException {

      String classList = props.getProperty(lowercaseNames ? propertyName.toLowerCase() : propertyName);
      if(classList == null || classList.length() == 0) {
         return Collections.emptyList();
      }

      List<String> classNames = Splitter.on(' ').omitEmptyStrings().trimResults().splitToList(classList);
      List<Object> objList = Lists.newArrayListWithExpectedSize(classNames.size());
      for(String className : classNames) {
         objList.add(initClass(propertyName, className, expectedClass));
      }
      return objList;
   }

   private Object initClass(final String propertyName, String className,
                            final Class<?> expectedClass) throws InitializationException {

      if(Strings.nullToEmpty(className).isEmpty()) {
         return null;
      }

      className = className.trim();

      try {
         Class<?> c = Class.forName(className);
         Object o = c.newInstance();
         if(!expectedClass.isInstance(o)) {
            throw new InitializationException(
                    String.format("The class: '%s' specified for '%s%s' does not implement %s",
                            className, prefix, propertyName, expectedClass.getName())
            );
         }
         return o;
      } catch(ClassNotFoundException ce) {
         throw new InitializationException(
                 String.format("The class: '%s' specified for '%s%s' was not found in the classpath", className, prefix, propertyName),
                 ce);
      } catch(InstantiationException | IllegalAccessException ie) {
         throw new InitializationException(
                 String.format("The class: '%s' specified for '%s%s' could not be initialized", className, prefix, propertyName),
                 ie);
      }
   }

   /**
    * Throws initialization exception indicating property is required.
    * @param propertyName The property name.
    * @throws InitializationException if called.
    */
   public final void throwRequiredException(final String propertyName) throws InitializationException {
      throw new InitializationException("The '" + prefix + propertyName + "' property must be specified");
   }

   /**
    * Throws initialization exception indicating property is not a positive integer.
    * @param propertyName The property name.
    * @throws InitializationException if called.
    */
   public final void throwPositiveIntRequiredException(final String propertyName) throws InitializationException {
      throw new InitializationException("The '" + prefix + propertyName + "' property must be specified and > 0");
   }

   /**
    * Processes command-line arguments by treating those beginning with '-' as properties
    * of the format -[name]=[value].
    * <p>
    * Properties with no specified value (e.g. -debug) are added to the properties
    * with a value of "true".
    * </p>
    * @param args The input arguments.
    * @param props The properties to which command line properties are added.
    * @return The arguments with properties removed.
    */
   public static String[] fromCommandLine(final String[] args, final Properties props) {

      if(args == null || args.length == 0) {
         return args;
      }

      List<String> argList = Lists.newArrayListWithExpectedSize(args.length);
      for(String arg : args) {
         if(arg.startsWith("-")) {
            arg = arg.substring(1);
            int index = arg.indexOf("=");
            if(index == -1 || index == arg.length() - 1) {
               props.setProperty(arg, "true");
            } else {
               String name = arg.substring(0, index);
               String val = arg.substring(index + 1);
               props.setProperty(name, val);
            }
         } else {
            argList.add(arg);
         }
      }

      return argList.toArray(new String[argList.size()]);
   }

   /**
    * Gets a time in milliseconds from a string that allows the units 'ms', 's', 'm', 'h', 'd' at the end.
    * @param time The time string.
    * @return The milliseconds, or Long.MIN_VALUE if units are unspecified or not recognized.
    * @throws InitializationException if the time is not an integer.
    */
   public static final long millisFromTime(String time) throws InitializationException {

      long mult = 0L;
      if(time.endsWith("ms")) {
         time = time.substring(0, time.length() - 2);
         mult = 1L;
      } else if(time.endsWith("s")) {
         time = time.substring(0, time.length() - 1);
         mult = 1000L;
      } else if(time.endsWith("m")) {
         time = time.substring(0, time.length() - 1);
         mult = 60000L;
      } else if(time.endsWith("h")) {
         time = time.substring(0, time.length() - 1);
         mult = 3600L * 1000L;
      } else if(time.endsWith("d")) {
         time = time.substring(0, time.length() - 1);
         mult = 3600L * 1000L * 24L;
      }

      if(mult == 0L) {
         return Long.MIN_VALUE;
      }

      try {
         long val = Long.parseLong(time.trim());
         return val * mult;
      } catch(Exception e) {
         throw new InitializationException("Invalid time, '" + time + "'");
      }
   }

   /**
    * Convert properties to a collection of string pairs.
    * @param props The properties.
    * @return The collection of pairs.
    */
   public static final Collection<Pair<String, String>> toPairCollection(final Properties props) {
      if(props == null) {
         return Collections.emptyList();
      }

      Enumeration pn = props.propertyNames();
      List<Pair<String, String>> pairs = Lists.newArrayListWithExpectedSize(props.size());
      while(pn.hasMoreElements()) {
         final String name = (String)pn.nextElement();
         final String value = props.getProperty(name);
         pairs.add(new Pair<>(name, value));
      }
      return pairs;
   }

   /**
    * The prefix applied to the original properties.
    */
   final String prefix;

   /**
    * The properties with prefix removed.
    */
   final Properties props;

   /**
    * Were property names added as lower-case?
    */
   final boolean lowercaseNames;
}
