/*
 * Copyright 2010,2014 Attribyte, LLC
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


import com.google.common.io.BaseEncoding;

import java.util.zip.Deflater;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.zip.InflaterOutputStream;
import java.util.zip.DeflaterOutputStream;

/**
 * Various encoding-related methods.
 * @author Matt Hamer - Attribyte, LLC
 */
public class EncodingUtil {

   /**
    * Upper-case hex alphabet.
    */
   private static final char[] hexChars = new char[]{
           '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
           'A', 'B', 'C', 'D', 'E', 'F'
   };

   /**
    * Indicates characters outside the hex alphabet.
    * Accepts both upper and lower-case.
    */
   private static final int INVALID_HEX_DIGIT = -1;

   private static final int[] hexVals = new int[256];

   static {
      for(int i = 0; i < hexVals.length; i++) {
         switch(i) {
            case '0':
               hexVals[i] = 0;
               break;
            case '1':
               hexVals[i] = 1;
               break;
            case '2':
               hexVals[i] = 2;
               break;
            case '3':
               hexVals[i] = 3;
               break;
            case '4':
               hexVals[i] = 4;
               break;
            case '5':
               hexVals[i] = 5;
               break;
            case '6':
               hexVals[i] = 6;
               break;
            case '7':
               hexVals[i] = 7;
               break;
            case '8':
               hexVals[i] = 8;
               break;
            case '9':
               hexVals[i] = 9;
               break;
            case 'A':
            case 'a':
               hexVals[i] = 10;
               break;
            case 'B':
            case 'b':
               hexVals[i] = 11;
               break;
            case 'C':
            case 'c':
               hexVals[i] = 12;
               break;
            case 'D':
            case 'd':
               hexVals[i] = 13;
               break;
            case 'E':
            case 'e':
               hexVals[i] = 14;
               break;
            case 'F':
            case 'f':
               hexVals[i] = 15;
               break;
            default:
               hexVals[i] = INVALID_HEX_DIGIT;
         }
      }
   }

   /**
    * Convert a string of hex to bytes.
    * @param hex The hex string.
    * @return The bytes.
    */
   public static final byte[] fromHex(final String hex) {

      char[] ch = hex.toCharArray();

      if(ch.length % 2 != 0) {
         throw new UnsupportedOperationException("The hex string must contain an even number of digits");
      }

      byte[] b = new byte[ch.length / 2];
      char ch0, ch1;

      try {

         for(int i = 0; i < ch.length; i += 2) {
            ch0 = ch[i];
            ch1 = ch[i + 1];

            int v1 = hexVals[ch0];
            if(v1 == INVALID_HEX_DIGIT) {
               throw new UnsupportedOperationException("The character, '" + ch0 + "' is not a hex digit");
            }

            int v2 = hexVals[ch1];
            if(v2 == INVALID_HEX_DIGIT) {
               throw new UnsupportedOperationException("The character, '" + ch1 + "' is not a hex digit");
            }

            b[i / 2] = (byte)((v1 << 4) | v2);
         }

         return b;

      } catch(IndexOutOfBoundsException e) {
         throw new UnsupportedOperationException("The hex string contains an invalid hex digit");
      }
   }

   private static final int lMask = 0x0F;
   private static final int hMask = lMask << 4;

   /**
    * Converts bytes to a string of hex.
    * @param bytes The bytes
    * @return The bytes as a hex string.
    */
   public static final String toHex(final byte[] bytes) {

      char[] buf = new char[bytes.length * 2];
      int pos = 0;
      for(final byte aByte : bytes) {
         int index = (aByte & hMask) >> 4;
         char currChar = hexChars[index];
         buf[pos++] = currChar;
         index = (aByte & lMask);
         currChar = hexChars[index];
         buf[pos++] = currChar;
      }

      return new String(buf);
   }

   /**
    * The Base64 codec.
    */
   static final BaseEncoding base64 = BaseEncoding.base64();

   /**
    * Encode bytes as base64.
    * @param b The bytes.
    * @return The encoded string.
    */
   public static final String encodeBase64(final byte[] b) {
      return base64.encode(b);
   }

   /**
    * Decode base64 to bytes.
    * @param s The encoded string.
    * @return The bytes.
    */
   public static final byte[] decodeBase64(final String s) {
      return base64.decode(s);
   }

   /**
    * Supported <tt>Deflate</tt> strategies.
    */
   public static enum DeflateStrategy {

      /**
       * No compression.
       */
      NONE(-1, (byte)0),

      /**
       * Best compression.
       */
      BEST_COMPRESSION(Deflater.BEST_COMPRESSION, (byte)1),

      /**
       * Best speed.
       */
      BEST_SPEED(Deflater.BEST_SPEED, (byte)2),

      /**
       * Best for data consisting mostly of small values with a somewhat random distribution.
       */
      FILTERED(Deflater.FILTERED, (byte)3),

      /**
       * Huffman coding only.
       */
      HUFFMAN_ONLY(Deflater.HUFFMAN_ONLY, (byte)4);

      private DeflateStrategy(final int strategy, final byte id) {
         this.strategy = strategy;
         this.id = id;
      }

      /**
       * Gets a byte that identifies the strategy.
       * @return The strategy id byte.
       */
      public byte getId() {
         return id;
      }

      final int strategy;
      final byte id;
   }

   /**
    * Applies ZLIB compression.
    * @param b The bytes to deflate.
    * @param strategy The deflate strategy.
    * @return The deflated bytes.
    */
   public static final byte[] deflate(final byte[] b, final DeflateStrategy strategy) {
      return deflate(b, 0, b.length, strategy);
   }

   /**
    * Applies ZLIB compression.
    * @param b The bytes to deflate.
    * @param offset The offset.
    * @param len The number of bytes.
    * @param strategy The deflate strategy.
    * @return The deflated bytes.
    */
   public static final byte[] deflate(final byte[] b, final int offset, final int len, final DeflateStrategy strategy) {
      try {
         Deflater deflater = new Deflater(strategy.strategy, false);
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         DeflaterOutputStream dos = new DeflaterOutputStream(baos, deflater);
         dos.write(b, offset, len);
         dos.close();
         return baos.toByteArray();
      } catch(IOException ioe) {
         throw new AssertionError("I/O exception on in-memory stream");
      }
   }

   /**
    * Inflate from ZLIB compression.
    * @param b The bytes to inflate.
    * @return The inflated bytes.
    */
   public static final byte[] inflate(final byte[] b) {
      return inflate(b, 0, b.length);
   }

   /**
    * Inflate from ZLIB compression.
    * @param b The bytes to inflate.
    * @param offset The offset.
    * @param len The number of bytes.
    * @return The inflated bytes.
    */
   public static final byte[] inflate(final byte[] b, final int offset, final int len) {

      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         InflaterOutputStream ios = new InflaterOutputStream(baos);
         ios.write(b, offset, len);
         ios.close();
         return baos.toByteArray();
      } catch(IOException ioe) {
         throw new AssertionError("I/O exception on in-memory stream");
      }
   }
}