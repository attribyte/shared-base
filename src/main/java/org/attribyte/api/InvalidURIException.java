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

package org.attribyte.api;

import java.net.URISyntaxException;

/**
 * An exception raised when an attempt is made to create an invalid URI.
 */
@SuppressWarnings("serial")
public class InvalidURIException extends java.io.IOException {

   public InvalidURIException(String message) {
      super(message);
   }

   public InvalidURIException(String message, Throwable t) {
      super(message, t);
   }

   public InvalidURIException(URISyntaxException use) {
      super(use.getMessage(), use.getCause());
   }
}
