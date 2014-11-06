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

/**
 * A pair of objects.
 * @param <K> The key class
 * @param <V> The value class
 * @author Matt Hamer - Attribyte, LLC
 */
public class Pair<K, V> {

   /**
    * Create the key, value pair.
    * @param key The key.
    * @param value The value.
    */
   public Pair(final K key, final V value) {
      this.key = key;
      this.value = value;
   }

   /**
    * Gets the key.
    * @return The key.
    */
   public K getKey() {
      return key;
   }

   /**
    * Gets the value.
    * @return The value.
    */
   public V getValue() {
      return value;
   }

   final K key;
   final V value;
}