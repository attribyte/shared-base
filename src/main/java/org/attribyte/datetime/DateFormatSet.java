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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

public class DateFormatSet {

   /**
    * A named {@code DateTimeFormatter}.
    */
   public static class NamedFormatter {

      /**
       * Create a named formatter.
       * @param name The name.
       * @param formatter The formatter.
       * @param aliases A set of aliases (alternative names).
       */
      public NamedFormatter(final String name, final DateTimeFormatter formatter,
                            final Set<String> aliases) {
         this.name = name;
         this.formatter = formatter;
         this.aliases = aliases != null ? ImmutableSet.copyOf(aliases) : ImmutableSet.of();
      }

      /**
       * Create a named formatter.
       * @param name The name.
       * @param formatter The formatter.
       * @param aliases An arbitrary array of aliases.
       * @throws IllegalArgumentException If any duplicate aliases are supplied.
       */
      public NamedFormatter(final String name, final DateTimeFormatter formatter,
                            final String... aliases) {
         this.name = name;
         this.formatter = formatter;
         this.aliases = aliases != null ? ImmutableSet.copyOf(aliases) : ImmutableSet.of();
      }

      /**
       * Change the time zone and locale for this formatter.
       * @param tz The new time zone.
       * @param locale The new locale.
       * @return The formatter with time zone and locale changed.
       */
      public NamedFormatter withTimeZone(DateTimeZone tz, final Locale locale) {
         return new NamedFormatter(this.name, formatter.withZone(tz).withLocale(locale), this.aliases);
      }

      /**
       * Change the time zone for this formatter.
       * @param tz The new time zone.
       * @return The formatter with time zone changed.
       */
      public NamedFormatter withTimeZone(final DateTimeZone tz) {
         return new NamedFormatter(this.name, formatter.withZone(tz), this.aliases);
      }

      /**
       * Change the locale for this formatter.
       * @param locale The locale.
       * @return The formatter with time zone changed.
       */
      public NamedFormatter withLocale(final Locale locale) {
         return new NamedFormatter(this.name, formatter.withLocale(locale), this.aliases);
      }

      @Override
      public String toString() {
         return MoreObjects.toStringHelper(this)
                 .add("name", name)
                 .add("formatter", formatter)
                 .add("aliases", aliases)
                 .toString();
      }

      /**
       * The name.
       */
      public final String name;

      /**
       * The formatter.
       */
      public final DateTimeFormatter formatter;

      /**
       * A set of aliases (alternative names).
       */
      public final ImmutableSet<String> aliases;
   }

   /**
    * Creates a set with the default formatters.
    */
   public DateFormatSet() {
      this(DEFAULT_FORMATTERS);
   }

   /**
    * Creates a format set from a collection of formatters with default time zone and locale.
    * @param formatters The collection of formatters.
    * @throws IllegalArgumentException if more than one formatter has the same name or alias.
    * @throws NullPointerException if input formatters is {@code null}, or any formatter has a {@code null} name.
    */
   public DateFormatSet(final Collection<NamedFormatter> formatters) {
      this(formatters, null, null);
   }

   /**
    * Creates a format set from a collection of formatters with time zone and default locale.
    * @param formatters The collection of formatters.
    * @param dtz The time zone.
    * @throws IllegalArgumentException if more than one formatter has the same name or alias.
    * @throws NullPointerException if input formatters is {@code null}, or any formatter has a {@code null} name.
    */
   public DateFormatSet(final Collection<NamedFormatter> formatters, final DateTimeZone dtz) {
      this(formatters, dtz, null);
   }

   /**
    * Creates a set from properties.
    * <ul>
    *    <li>Values must be a valid pattern</li>
    *    <li>Default formatters are added when there is no conflict with property names.</li>
    * </ul>
    * @param props The properties.
    * @throws IllegalArgumentException on invalid pattern.
    */
   public DateFormatSet(final Properties props, final DateTimeZone dtz, final Locale locale) throws IllegalArgumentException {
      this(fromProperties(props), dtz, locale);
   }

   /**
    * Creates a list that contains custom formatters and default formatters
    * that do not conflict.
    * @param props The properties.
    * @return The list of formatters.
    */
   private static List<NamedFormatter> fromProperties(Properties props) {

      List<NamedFormatter> formatters = Lists.newArrayList();
      for(String name : props.stringPropertyNames()) {
         String pattern = props.getProperty(name);
         DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
         formatters.add(new NamedFormatter(name, formatter, ImmutableSet.of()));
      }

      for(NamedFormatter formatter : DEFAULT_FORMATTERS) {
         if(!props.keySet().contains(formatter.name) && Sets.intersection(props.keySet(), formatter.aliases).isEmpty()) {
            formatters.add(formatter);
         }
      }

      return formatters;
   }

   /**
    * Creates a format set from a collection of formatters.
    * @param formatters The collection of formatters.
    * @param dtz The time zone. If {@code null}, the default time zone.
    * @param locale The locale. If {@code null}, the default locale.
    * @throws IllegalArgumentException if more than one formatter has the same name or alias.
    * @throws NullPointerException if input formatters is {@code null}, or any formatter has a {@code null} name.
    */
   public DateFormatSet(final Collection<NamedFormatter> formatters,
                        DateTimeZone dtz,
                        Locale locale) throws IllegalArgumentException {

      this.timeZone = dtz != null ? dtz : DateTimeZone.getDefault();
      this.locale = locale != null ? locale : Locale.getDefault();

      Map<String, NamedFormatter> builder = Maps.newHashMapWithExpectedSize(formatters.size());
      for(NamedFormatter _formatter : formatters) {
         NamedFormatter formatter = _formatter.withTimeZone(this.timeZone, this.locale);
         if(!builder.containsKey(formatter.name)) {
            builder.put(formatter.name, formatter);
            for(String alias : formatter.aliases) {
               if(!builder.containsKey(alias)) {
                  builder.put(alias, formatter);
               } else {
                  throw new IllegalArgumentException(String.format("Duplicate formatter for alias, '%s'", alias));
               }
            }
         } else {
            throw new IllegalArgumentException(String.format("Duplicate formatter for name, '%s'", formatter.name));
         }
      }
      this.formatters = ImmutableMap.copyOf(builder);
   }

   /**
    * Creates a new set with time zone and locale changed.
    * @param dtz The time zone.
    * @param locale The locale.
    * @return The date format set with time zone and locale changed.
    */
   public DateFormatSet withTimeZone(final DateTimeZone dtz, final Locale locale) {
      DateTimeZone checkZone = dtz != null ? dtz : DateTimeZone.getDefault();
      Locale checkLocale = locale != null ? locale : Locale.getDefault();
      if(checkZone.equals(timeZone) && checkLocale.equals(locale)) {
         return this;
      }
      return new DateFormatSet(uniqueFormatters(), dtz, locale);
   }

   /**
    * Creates a new set with Java time zone and locale changed.
    * @param tz The time zone.
    * @param locale The locale.
    * @return The date format set with time zone and locale changed.
    */
   public DateFormatSet withTimeZone(final TimeZone tz, final Locale locale) {
      return withTimeZone(DateTimeZone.forTimeZone(tz), locale);
   }

   /**
    * Creates a new set with a time zone changed.
    * @param dtz The time zone.
    * @return The date format set with time zone changed.
    */
   public DateFormatSet withTimeZone(final DateTimeZone dtz) {
      DateTimeZone checkZone = dtz != null ? dtz : DateTimeZone.getDefault();
      if(checkZone.equals(timeZone)) {
         return this;
      }
      return new DateFormatSet(uniqueFormatters(), dtz, locale);
   }

   /**
    * Creates a new set with Java time zone changed.
    * @param tz The time zone.
    * @return The date format set with time zone changed.
    */
   public DateFormatSet withTimeZone(final TimeZone tz) {
      return withTimeZone(DateTimeZone.forTimeZone(tz));
   }

   /**
    * Creates a new set with locale changed.
    * @param locale The locale.
    * @return The date format set with locale changed.
    */
   public DateFormatSet withLocale(final Locale locale) {
      Locale checkLocale = locale != null ? locale : Locale.getDefault();
      if(checkLocale.equals(locale)) {
         return this;
      }
      return new DateFormatSet(uniqueFormatters(), timeZone, locale);
   }

   /**
    * Gets a formatter by name.
    * @param name The name.
    * @return The formatter or {@code null} if none.
    */
   public DateTimeFormatter formatter(final String name) {
      NamedFormatter formatter = formatters.get(name);
      return formatter != null ? formatter.formatter : null;
   }

   /**
    * Gets a formatter.
    * @param format The format.
    * @return The formatter or {@code null} if none.
    */
   public DateTimeFormatter formatter(final Format format) {
      return formatter(format.name);
   }

   /**
    * An immutable list of the default formatters.
    */
   public static final ImmutableList<NamedFormatter> DEFAULT_FORMATTERS = ImmutableList.copyOf(defaultFormatters());

   /**
    * The default date format set.
    */
   public static final DateFormatSet DEFAULT = new DateFormatSet(DEFAULT_FORMATTERS);

   /**
    * Creates a list of the default formatters.
    * @return The list of formatters.
    */
   public static List<NamedFormatter> defaultFormatters() {

      List<NamedFormatter> formatters = Lists.newArrayList();
      formatters.add(new NamedFormatter("shortTime", DateTimeFormat.shortTime(),"st", "short_time"));
      formatters.add(new NamedFormatter("medTime", DateTimeFormat.mediumTime(),"mt", "med_time"));
      formatters.add(new NamedFormatter("longTime", DateTimeFormat.longTime(),"lt", "long_time"));
      formatters.add(new NamedFormatter("fullTime", DateTimeFormat.fullTime(),"ft", "full_time"));

      formatters.add(new NamedFormatter("shortDate", DateTimeFormat.shortTime(),"sd", "short_date"));
      formatters.add(new NamedFormatter("medDate", DateTimeFormat.mediumTime(),"md", "med_date"));
      formatters.add(new NamedFormatter("longDate", DateTimeFormat.longTime(),"ld", "long_date"));
      formatters.add(new NamedFormatter("fullDate", DateTimeFormat.fullTime(),"fd", "full_date"));

      formatters.add(new NamedFormatter("shortDateTime", DateTimeFormat.shortTime(),"sdt", "short_date_time"));
      formatters.add(new NamedFormatter("medDateTime", DateTimeFormat.mediumTime(),"mdt", "med_date_time"));
      formatters.add(new NamedFormatter("longDateTime", DateTimeFormat.longTime(),"ldt", "long_date_time"));
      formatters.add(new NamedFormatter("fullDateTime", DateTimeFormat.fullTime(),"fdt", "full_date_time"));

      formatters.add(new NamedFormatter("isoDateTime", ISODateTimeFormat.dateTimeNoMillis(),"idt", "iso_date_time"));

      formatters.add(new NamedFormatter("dayOfWeek", DateTimeFormat.forPattern("EEEE"),"dow", "day_of_week"));

      formatters.add(new NamedFormatter("monthDay", DateTimeFormat.forPattern("MMM d"),"month_day"));

      formatters.add(new NamedFormatter("ymdCSV", DateTimeFormat.forPattern("YYYY,MM,d"),"ymdcsv", "ymd_csv"));

      formatters.add(new NamedFormatter("ymd", DateTimeFormat.forPattern("YYYYMMd"), ImmutableSet.of()));

      formatters.add(new NamedFormatter("year", DateTimeFormat.forPattern("YYYY"), "y"));

      formatters.add(new NamedFormatter("timeDayMonth",  DateTimeFormat.forPattern("h:mma EEEE d MMMM"), "tmd", "time_day_month"));

      return formatters;
   }

   /**
    * @return A list of uniquely-named formatters.
    */
   private List<NamedFormatter> uniqueFormatters() {
      Set<String> seen = Sets.newHashSet();
      List<NamedFormatter> formatters = Lists.newArrayList();
      for(NamedFormatter formatter : this.formatters.values()) {
         if(!seen.contains(formatter.name)) {
            formatters.add(formatter);
            seen.add(formatter.name);
         }
      }
      return formatters;
   }

   /**
    * The current time zone for this set.
    */
   public final DateTimeZone timeZone;

   /**
    * The current locale for this set.
    */
   public final Locale locale;

   /**
    * The map of formatters.
    */
   public final ImmutableMap<String, NamedFormatter> formatters;
}
