/*
 * Copyright 2010, 2014 Attribyte, LLC
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

import java.nio.ByteBuffer;


/**
 * Convenience methods for <code>ByteBuffer</code>.
 * @author Matt Hamer - Attribyte, LLC
 */
public class ByteBufferUtil {

   /**
    * Gets a byte array from a buffer.
    * <p>
    * If the buffer is backed by an array, simply returns the array.
    * Note that this allows modification to the underlying array.
    * Otherwise, the buffer is fully read and returned as a new array.
    * </p>
    * @param buf The buffer. If <code>null</code>, <code>null</code> is returned.
    * @return The array of bytes.
    */
   public static final byte[] array(final ByteBuffer buf) {
      if(buf == null) {
         return null;
      } else if(buf.hasArray()) {
         return buf.array();
      } else {
         byte[] arr = new byte[buf.remaining()];
         buf.get(arr);
         return arr;
      }
   }
}