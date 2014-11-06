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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.BitSet;

import com.google.common.base.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;

/**
 * Utility methods for decoding URIs.
 */
public class URIEncoder {

   private static final BitSet unreservedBitSet = new BitSet();
   private static final BitSet gendelimBitSet = new BitSet();
   private static final BitSet subdelimBitSet = new BitSet();
   private static final BitSet authorityBitSet = new BitSet();
   private static final BitSet pathBitSet = new BitSet();
   private static final BitSet queryBitSet = new BitSet();

   static {

      unreservedBitSet.set(48, 58);
      unreservedBitSet.set(65, 91);
      unreservedBitSet.set(97, 123);
      unreservedBitSet.set('-');
      unreservedBitSet.set('_');
      unreservedBitSet.set('.');
      unreservedBitSet.set('~');

      // gen-delims  = ":" / "/" / "?" / "#" / "[" / "]" / "@"

      gendelimBitSet.set(':');
      gendelimBitSet.set('/');
      gendelimBitSet.set('?');
      gendelimBitSet.set('#');
      gendelimBitSet.set('[');
      gendelimBitSet.set(']');
      gendelimBitSet.set('@');

      //sub-delims  = "!" / "$" / "&" / "'" / "(" / ")"
      // "*" / "+" / "," / ";" / "="      

      subdelimBitSet.set('!');
      subdelimBitSet.set('$');
      subdelimBitSet.set('&');
      subdelimBitSet.set('\'');
      subdelimBitSet.set('(');
      subdelimBitSet.set(')');
      subdelimBitSet.set('*');
      subdelimBitSet.set('+');
      subdelimBitSet.set(',');
      subdelimBitSet.set(';');
      subdelimBitSet.set('=');

      authorityBitSet.or(unreservedBitSet);
      authorityBitSet.or(subdelimBitSet);
      authorityBitSet.set(':');
      authorityBitSet.set('@');
      authorityBitSet.set('[');
      authorityBitSet.set(']');

      pathBitSet.or(unreservedBitSet);
      pathBitSet.or(subdelimBitSet);
      pathBitSet.set('/');
      pathBitSet.set(':');
      pathBitSet.set('@');

      queryBitSet.or(unreservedBitSet);
      queryBitSet.or(subdelimBitSet);
      queryBitSet.set('?');
      queryBitSet.set('/');
      queryBitSet.set(':');
      queryBitSet.set('@');
   }

   public static void main(String[] args) throws Exception {
      String test = "http://attribyte.com/%54est1/%3Fest2?x=y&url=http://cnnfn.com?site=12345";
      System.out.println("original=" + test);
      System.out.println("decoded=" + new URIEncoder().recode(test));
   }

   /**
    * Decodes all <em>non-reserved</em> characters in a URL.
    * @param url The url to decode.
    * @return The decoded URL.
    * @throws java.net.URISyntaxException if URL is invalid.
    */
   public String recode(String url) throws URISyntaxException {
      return recodeURL(url);
   }

   /**
    * Decodes all <em>non-reserved</em> characters in a URL,
    * then recodes the components.
    * @param url The url to decode.
    * @return The decoded URL.
    * @throws java.net.URISyntaxException if URL is invalid.
    */
   public static String recodeURL(String url) throws URISyntaxException {
      URI uri = new URI(url);
      return recode(uri);
   }

   /**
    * Recodes a URI.
    * @param uri The uri.
    * @return The recoded URI as a string.
    */
   public static String recode(URI uri) {
      return encode(uri.getScheme(), uri.getAuthority(), uri.getPath(), uri.getQuery(), uri.getFragment());
   }

   /**
    * Encodes a URI from raw components. Any component may be null, but
    * resulting string may not be a valid URI.
    * @param scheme The scheme.
    * @param authority The authority.
    * @param path The path.
    * @param qs The query string.
    * @param fragment The fragment.
    * @return The encoded URI as a string.
    */
   public static String encode(final String scheme,
                               final String authority,
                               final String path,
                               final String qs,
                               final String fragment) {
      StringBuilder buf = new StringBuilder();
      if(scheme != null) {
         buf.append(scheme);
         buf.append("://");
      }

      byte[] bytes;

      if(authority != null) {
         bytes = URLCodec.encodeUrl(authorityBitSet, authority.getBytes(Charsets.UTF_8));
         buf.append(new String(bytes, Charsets.US_ASCII));
      }

      if(path != null) {
         bytes = URLCodec.encodeUrl(pathBitSet, path.getBytes(Charsets.UTF_8));
         buf.append(new String(bytes, Charsets.US_ASCII));
      }

      if(qs != null) {
         buf.append('?');
         bytes = URLCodec.encodeUrl(queryBitSet, qs.getBytes(Charsets.UTF_8));
         buf.append(new String(bytes, Charsets.US_ASCII));
      }

      if(fragment != null) {
         buf.append('#');
         bytes = URLCodec.encodeUrl(queryBitSet, fragment.getBytes(Charsets.UTF_8));
         buf.append(new String(bytes, Charsets.US_ASCII));
      }

      return buf.toString();
   }

   /**
    * Encodes the path component, excluding the query string.
    * @param path The path.
    * @return The encoded path.
    */
   public static final String encodePath(final String path) {
      byte[] bytes = URLCodec.encodeUrl(pathBitSet, path.getBytes(Charsets.UTF_8));
      return new String(bytes, Charsets.US_ASCII);
   }

   /**
    * Encodes a query string component.
    * @param qs The query string.
    * @return The encoded path.
    */
   public static final String encodeQueryString(final String qs) {
      byte[] bytes = URLCodec.encodeUrl(queryBitSet, qs.getBytes(Charsets.UTF_8));
      return new String(bytes, Charsets.US_ASCII);
   }

   /**
    * Encodes the path fragment.
    * @param fragment The fragment.
    * @return The encoded path.
    */
   public static final String encodeFragment(final String fragment) {
      return encodeQueryString(fragment);
   }

   /**
    * Recodes a query string.
    * @param qs The query string.
    * @return The recoded string or <tt>null</tt> if invalid.
    */
   public static final String recodeQueryString(final String qs) {
      if(qs == null) {
         return null;
      }

      try {
         byte[] decoded = URLCodec.decodeUrl(qs.getBytes(Charsets.UTF_8));
         return new String(URLCodec.encodeUrl(queryBitSet, decoded), Charsets.US_ASCII);
      } catch(DecoderException de) {
         return null;
      }
   }
}
