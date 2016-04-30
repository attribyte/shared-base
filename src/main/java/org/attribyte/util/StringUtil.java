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

import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import java.text.Normalizer;
import java.security.SecureRandom;

/**
 * Convenience methods for string operations.
 * @author Matt Hamer - Attribyte, LLC.
 */
public class StringUtil {

   /**
    * Shared secure random instance.
    */
   private static final SecureRandom rnd = new SecureRandom();

   /**
    * Determines if a string is non-null, non-empty.
    * @param str The string.
    * @return Does the string have content?
    * @deprecated Use Guava.
    */
   public static final boolean hasContent(final String str) {
      return str != null && str.length() > 0;
   }

   /**
    * The random character pool.
    */
   private static final char[] rndChars = {
           '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
           'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
           'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
           'u', 'v', 'w', 'x', 'y', 'z',
           'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
           'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
           'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
   };

   /**
    * Gets a string of random characters.
    * @param numChars The maximum number of characters.
    * @return The random string.
    */
   public static final String randomString(final int numChars) {
      StringBuilder buf = new StringBuilder();
      for(int i = 0; i < numChars; i++) {
         buf.append(rndChars[rnd.nextInt(rndChars.length)]);
      }

      return buf.toString();
   }

   /**
    * A string equals that allows <tt>null</tt> values
    * and treats them as equivalent to empty strings.
    * @param str1 The first string.
    * @param str2 The second string.
    * @return Are the strings equal?
    */
   @SuppressWarnings({"StringEquality"})
   public static boolean equals(final String str1, final String str2) {
      return str1 == str2 || Strings.nullToEmpty(str1).equals(Strings.nullToEmpty(str2));
   }

   /**
    * Normalize a string potentially containing Unicode to NFC form.
    * @param str The input string.
    * @return The normalized string.
    */
   public static final String normalizeUnicode(final String str) {
      if(Normalizer.isNormalized(str, Normalizer.Form.NFC)) {
         return str;
      } else {
         return Normalizer.normalize(str, Normalizer.Form.NFC);
      }
   }

   /**
    * Parse an integer.
    * @param intStr The string to parse.
    * @param defaultValue The value to return if the input is null, empty, or not an integer.
    * @return The int value.
    */
   public static final int parseInt(String intStr, int defaultValue) {
      if(!Strings.isNullOrEmpty(intStr)) {
         Integer val = Ints.tryParse(intStr);
         return val != null ? val : defaultValue;
      } else {
         return defaultValue;
      }
   }

   /**
    * Parse a long.
    * @param intStr The string to parse.
    * @param defaultValue The value to return if the input is null, empty, or not an integer.
    * @return The long value.
    */
   public static final long parseLong(String intStr, long defaultValue) {
      if(!Strings.isNullOrEmpty(intStr)) {
         Long val = Longs.tryParse(intStr);
         return val != null ? val : defaultValue;
      } else {
         return defaultValue;
      }
   }

   /**
    * Parse a boolean.
    * <p>
    *    Case-insensitive. Strings other than 'true' return <code>false</code>.
    * </p>
    * @param bStr The string to parse.
    * @param defaultValue The value to return if the input is null, empty, or not a boolean.
    * @return The boolean value.
    */
   public static final boolean parseBoolean(String bStr, boolean defaultValue) {
      if(!Strings.isNullOrEmpty(bStr)) {
         return bStr.equalsIgnoreCase("true");
      } else {
         return defaultValue;
      }
   }

   /**
    * Parse a float.
    * @param floatStr The string to parse.
    * @param defaultValue The value to return if the input is null, empty, or not a floating point value.
    * @return The float value.
    */
   public static final float parseFloat(String floatStr, float defaultValue) {
      if(!Strings.isNullOrEmpty(floatStr)) {
         Float val = Floats.tryParse(floatStr);
         return val != null ? val : defaultValue;
      } else {
         return defaultValue;
      }
   }

   /**
    * Parse a double.
    * @param doubleStr The string to parse.
    * @param defaultValue The value to return if the input is null, empty, or not a floating point value.
    * @return The double value.
    */
   public static final double parseDouble(String doubleStr, float defaultValue) {
      if(!Strings.isNullOrEmpty(doubleStr)) {
         Double val = Doubles.tryParse(doubleStr);
         return val != null ? val : defaultValue;
      } else {
         return defaultValue;
      }
   }
}