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

import com.google.common.collect.Lists;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Collections;
import java.util.List;

/**
 * Utilities for (built-into-java) DOM manipulation.
 * @author Matt Hamer - Attribyte, LLC
 */
public class DOMUtil {

   /**
    * Gets the first child element with the specified name.
    * @param parentElem The parent element.
    * @param childName The child name.
    * @return The first element or <tt>null</tt> if none found.
    */
   public static final Element getFirstChild(final Element parentElem, final String childName) {
      NodeList childList = parentElem.getElementsByTagName(childName);
      if(childList.getLength() > 0) {
         Node childNode = childList.item(0);
         if(childNode instanceof Element && childNode.getParentNode() == parentElem) {
            return (Element)childNode;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static final List<Element> getChildElementsByTagName(final Element parentElem, final String childName) {
      NodeList childList = parentElem.getElementsByTagName(childName);
      if(childList.getLength() > 0) {
         List<Element> childElements = Lists.newArrayListWithExpectedSize(childList.getLength());
         for(int i = 0; i < childList.getLength(); i++) {
            Node curr = childList.item(i);
            if(curr instanceof Element && curr.getParentNode() == parentElem) {
               childElements.add((Element)curr);
            }
         }
         return childElements;
      } else {
         return Collections.emptyList();
      }
   }

   /**
    * Gets the text content of a child element.
    * @param parentElem The parent element.
    * @param childName The child element name.
    * @return The text content or <tt>null</tt> if none.
    */
   public static final String getChildText(final Element parentElem, final String childName) {
      NodeList childList = parentElem.getElementsByTagName(childName);
      if(childList.getLength() > 0) {
         Node child = childList.item(0);
         return child.getTextContent();
      } else {
         return null;
      }
   }
}
