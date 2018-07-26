/*
 * Copyright 2018 Attribyte, LLC
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

package org.attribyte.datetime;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.joda.time.DateTimeZone;

import java.util.Locale;
import java.util.TimeZone;

public class DFSCache {

   /**
    * A cache key.
    */
   public static final class Key {

      /**
       * Creates a key.
       * @param dtz The time zone.
       * @param locale The locale.
       */
      public Key(final DateTimeZone dtz, final Locale locale) {
         this.timeZone = dtz;
         this.locale = locale;
      }


      /**
       * Creates a new key with time zone and locale changed.
       * @param dtz The time zone.
       * @param locale The locale.
       * @return The key with time zone and locale changed.
       */
      public Key withTimeZone(final DateTimeZone dtz, final Locale locale) {
         DateTimeZone checkZone = dtz != null ? dtz : this.timeZone;
         Locale checkLocale = locale != null ? locale : this.locale;
         if(checkZone.equals(timeZone) && checkLocale.equals(locale)) {
            return this;
         }
         return new Key(dtz, locale);
      }

      /**
       * Creates a new key with a time zone changed.
       * @param dtz The time zone.
       * @return The key with time zone changed.
       */
      public Key withTimeZone(final DateTimeZone dtz) {
         DateTimeZone checkZone = dtz != null ? dtz : this.timeZone;
         if(checkZone.equals(timeZone)) {
            return this;
         }
         return new Key(dtz, locale);
      }

      /**
       * Creates a new key with locale changed.
       * @param locale The locale.
       * @return The date format set with time zone and locale changed.
       */
      public Key withLocale(final Locale locale) {
         Locale checkLocale = locale != null ? locale : this.locale;
         if(checkLocale.equals(locale)) {
            return this;
         }
         return new Key(timeZone, locale);
      }

      @Override
      public String toString() {
         return MoreObjects.toStringHelper(this)
                 .add("timeZone", timeZone)
                 .add("locale", locale)
                 .toString();
      }

      @Override
      public boolean equals(final Object o) {
         if(this == o) return true;
         if(o == null || getClass() != o.getClass()) return false;
         final Key key = (Key)o;
         return Objects.equal(timeZone, key.timeZone) &&
                 Objects.equal(locale, key.locale);
      }

      @Override
      public int hashCode() {
         return Objects.hashCode(timeZone, locale);
      }

      /**
       * The time zone.
       */
      public final DateTimeZone timeZone;

      /**
       * The locale.
       */
      public final Locale locale;
   }

   /**
    * Creates a cache with a non-standard default date format set.
    * @param defaultSet The default set.
    */
   public DFSCache(final DateFormatSet defaultSet) {
      this.defaultSet = defaultSet;
      this.defaultKey = new Key(defaultSet.timeZone, defaultSet.locale);
      this.dfsCache = CacheBuilder.newBuilder()
              .concurrencyLevel(4)
              .build(new CacheLoader<Key, DateFormatSet>() {
                 @Override
                 public DateFormatSet load(final Key key) {
                    return defaultSet.withTimeZone(key.timeZone, key.locale);
                 }
              });
   }

   /**
    * The default cache created with {@code DateFormatSet.DEFAULT}.
    */
   public static final DFSCache DEFAULT = new DFSCache(DateFormatSet.DEFAULT);

   /**
    * Gets a new set with a pre-built key.
    * @param key The key.
    * @return The date format set.
    */
   public DateFormatSet forKey(final Key key) {
      return dfsCache.getUnchecked(key);
   }

   /**
    * Creates a new set with time zone and locale changed from the default.
    * @param dtz The time zone.
    * @param locale The locale.
    * @return The date format set with time zone and locale changed.
    */
   public DateFormatSet withTimeZone(final DateTimeZone dtz, final Locale locale) {
      return dfsCache.getUnchecked(defaultKey.withTimeZone(dtz, locale));
   }

   /**
    * Creates a new set with Java time zone and locale changed from the default.
    * @param tz The time zone.
    * @param locale The locale.
    * @return The date format set with time zone and locale changed.
    */
   public DateFormatSet withTimeZone(final TimeZone tz, final Locale locale) {
      return withTimeZone(DateTimeZone.forTimeZone(tz), locale);
   }

   /**
    * Creates a new set with a time zone changed and default locale.
    * @param dtz The time zone.
    * @return The date format set with time zone changed.
    */
   public DateFormatSet withTimeZone(final DateTimeZone dtz) {
      return dfsCache.getUnchecked(defaultKey.withTimeZone(dtz));
   }

   /**
    * Creates a new set with Java time zone changed and default locale.
    * @param tz The time zone.
    * @return The date format set with time zone changed.
    */
   public DateFormatSet withTimeZone(final TimeZone tz) {
      return withTimeZone(DateTimeZone.forTimeZone(tz));
   }

   /**
    * Creates a new set with locale changed and default time zone.
    * @param locale The locale.
    * @return The date format set with locale changed.
    */
   public DateFormatSet withLocale(final Locale locale) {
      return dfsCache.getUnchecked(defaultKey.withLocale(locale));
   }

   /**
    * The default date format set.
    */
   public final DateFormatSet defaultSet;

   /**
    * The default key.
    */
   private final Key defaultKey;

   /**
    * The cache.
    */
   private final LoadingCache<Key, DateFormatSet> dfsCache;

}
