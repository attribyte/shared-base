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

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A {@code java.util.Date} with various built-in formatting options.
 */
public class FormattedDate extends Date {

   /**
    * The associated date format set.
    */
   private final DateFormatSet formatSet;

   public FormattedDate(final Date date, final DateFormatSet formatSet) {
      super(date.getTime());
      this.formatSet = formatSet;
   }

   public FormattedDate(final long time, final DateFormatSet formatSet) {
      super(time);
      this.formatSet = formatSet;
   }

   public FormattedDate(final long time, final TimeZone tz) {
      super(time);
      this.formatSet = tz == null ? DateFormatSet.DEFAULT : DateFormatSet.DEFAULT.withTimeZone(tz);
   }


   public FormattedDate(final FormattedDate other, final TimeZone tz) {
      super(other.getTime());
      this.formatSet = other.formatSet.withTimeZone(tz);
   }

   public FormattedDate(final FormattedDate other, final TimeZone tz, final Locale locale) {
      super(other.getTime());
      this.formatSet = other.formatSet.withTimeZone(tz, locale);
   }

   /**
    * Gets the time zone.
    * @return The time zone.
    */
   public DateTimeZone getDateTimeZone() {
      return formatSet.timeZone;
   }

   /**
    * Formats using the named format.
    * @param formatName The format name.
    * @return The formatted date-time, or {@code null} if the format name is invalid.
    */
   public String format(final String formatName) {

      switch(Strings.nullToEmpty(formatName.toLowerCase())) {
         case "ago":
         case "a":
            return getAgo();
         case "da":
         case "days_ago":
            return getDaysAgo();
         default:
            DateTimeFormatter formatter = formatSet.formatter(formatName);
            return formatter != null ? formatter.print(getTime()) : null;
      }
   }

   /**
    * Formats using the enumerated format type.
    * @param fmt The format type.
    * @return The formatted date-time.
    */
   public String format(final Format fmt) {
      switch(fmt) {
         case AGO: return getAgo();
         case DAYS_AGO: return getDaysAgo();
         default: return formatSet.formatter(fmt).print(getTime());
      }
   }

   /**
    * Formats using the short time format.
    * @return The formatted time.
    */
   public String getShortTime() {
      return formatSet.formatter(Format.SHORT_TIME).print(getTime());
   }

   /**
    * Formats using the medium time format.
    * @return The formatted time.
    */
   public String getMedTime() {
      return formatSet.formatter(Format.MED_TIME).print(getTime());
   }

   /**
    * Formats using the long time format.
    * @return The formatted time.
    */
   public String getLongTime() {
      return formatSet.formatter(Format.LONG_TIME).print(getTime());
   }

   /**
    * Formats using the full time format.
    * @return The formatted time.
    */
   public String getFullTime() {
      return formatSet.formatter(Format.LONG_TIME).print(getTime());
   }

   /**
    * Formats using the short date format.
    * @return The formatted time.
    */
   public String getShortDate() {
      return formatSet.formatter(Format.SHORT_DATE).print(getTime());
   }

   /**
    * Formats using the medium date format.
    * @return The formatted time.
    */
   public String getMedDate() {
      return formatSet.formatter(Format.MED_DATE).print(getTime());
   }

   /**
    * Formats using the long date format.
    * @return The formatted time.
    */
   public String getLongDate() {
      return formatSet.formatter(Format.LONG_DATE).print(getTime());
   }

   /**
    * Formats using the full date format.
    * @return The formatted time.
    */
   public String getFullDate() {
      return formatSet.formatter(Format.FULL_DATE).print(getTime());
   }

   /**
    * Formats using the short date/time format.
    * @return The formatted time.
    */
   public String getShortDateTime() {
      return formatSet.formatter(Format.SHORT_DATE_TIME).print(getTime());
   }

   /**
    * Formats using the medium date/time format.
    * @return The formatted time.
    */
   public String getMedDateTime() {
      return formatSet.formatter(Format.MED_DATE_TIME).print(getTime());
   }

   /**
    * Formats using the long date/time format.
    * @return The formatted time.
    */
   public String getLongDateTime() {
      return formatSet.formatter(Format.LONG_DATE_TIME).print(getTime());
   }

   /**
    * Formats using the full date/time format.
    * @return The formatted time.
    */
   public String getFullDateTime() {
      return formatSet.formatter(Format.FULL_DATE_TIME).print(getTime());
   }

   /**
    * Formats using the ISO8601 standard.
    * @return The formatted time.
    */
   public String getIsoDateTime() {
      return formatSet.formatter(Format.ISO_DATE_TIME).print(getTime());
   }

   /**
    * The locale-formatted name for the day of the week.
    * @return The day of week name.
    */
   public String getDayOfWeekName() {
      return formatSet.formatter(Format.DAY_OF_WEEK).print(getTime());
   }

   /**
    * The locale-formatted name for the day of the week.
    * @return The day of week name.
    */
   public String getMonthDay() {
      return formatSet.formatter(Format.MONTH_DAY).print(getTime());
   }

   /**
    * Gets the YYYY,MM,DD
    */
   public String getYMDCSV() {
      return formatSet.formatter(Format.YMD_CSV).print(getTime());
   }

   /**
    * Gets the YYYYMMDD
    */
   public String getYMD() {
      return formatSet.formatter(Format.YMD).print(getTime());
   }

   /**
    * Gets the YYYY
    * @return the YYYY string.
    */
   public String getYYYY() {
      return formatSet.formatter(Format.YEAR).print(getTime());
   }

   /**
    * Gets the format 7:50 PM Wed 4 November
    * @return The format.
    */
   public String getTimeDateMonth() {
      return formatSet.formatter(Format.TIME_DAY_MONTH).print(getTime());
   }


   private static final long ONE_DAY_MINUTES = 60L * 24L;

   /**
    * Formats using words like "14 Minutes Ago"
    * @return The ago string.
    */
   public String getAgo() {
      long currTime = System.currentTimeMillis();
      long timeAgoMillis = currTime - getTime();
      long timeAgoMinutes = timeAgoMillis / 60000L;

      if(timeAgoMinutes <= 1L) {
         return "1 Minute Ago";
      } else if(timeAgoMinutes < 60L) {
         return Long.toString(timeAgoMinutes) + " Minutes Ago";
      } else if(timeAgoMinutes < ONE_DAY_MINUTES) {
         long hoursAgo = timeAgoMinutes / 60L;
         if(hoursAgo == 1L) {
            return "1 Hour Ago";
         } else {
            return Long.toString(hoursAgo)+" Hours Ago";
         }
      } else {
         long daysAgo = timeAgoMinutes / ONE_DAY_MINUTES;
         if(daysAgo == 1L) {
            return "Yesterday";
         } else {
            return Long.toString(daysAgo)+" Days Ago";
         }
      }
   }

   /**
    * Formats with "Today", "Yesterday", "Two Days Ago".
    * @return The days ago string.
    */
   public String getDaysAgo() {

      DateTime midnightToday = new DateTime(System.currentTimeMillis(), formatSet.timeZone).withTimeAtStartOfDay();
      DateTime midnightYesterday = midnightToday.minusDays(1);

      if(getTime() >= midnightToday.getMillis()) {
         return "Today";
      } else if(getTime() >= midnightYesterday.getMillis()) {
         return "Yesterday";
      } else {
         long timeAgoMillis = System.currentTimeMillis() - getTime();
         long timeAgoMinutes = timeAgoMillis / 60000L;
         long daysAgo = timeAgoMinutes / ONE_DAY_MINUTES;
         if(daysAgo == 1L) {
            return "Yesterday";
         } else {
            return Long.toString(daysAgo)+" Days Ago";
         }
      }
   }

   /**
    * Determine if the date is in the current year.
    * @return Is the date in this year?
    */
   public boolean isThisYear() {
      DateTime currTime = new DateTime().withTimeAtStartOfDay();
      DateTime checkTime = new DateTime(getTime()).withTimeAtStartOfDay();
      return currTime.year().get() == checkTime.year().get();
   }

   /**
    * Determine if the date is in the current day.
    * @return Is the date today?
    */
   public boolean isToday() {
      DateTime currTime = new DateTime(formatSet.timeZone).withTimeAtStartOfDay();
      DateTime checkTime = new DateTime(getTime(), formatSet.timeZone).withTimeAtStartOfDay();
      return (currTime.year().get() == checkTime.year().get() &&
              currTime.dayOfYear().get() == checkTime.dayOfYear().get());
   }

   /**
    * Determine if the date is from the previous day.
    * @return Is the date from the previous day?
    */
   public boolean isYesterday() {
      DateTime currTime = new DateTime(formatSet.timeZone).withTimeAtStartOfDay().minusDays(1);
      DateTime checkTime = new DateTime(getTime(), formatSet.timeZone).withTimeAtStartOfDay();
      return (currTime.year().get() == checkTime.year().get() &&
              currTime.dayOfYear().get() == checkTime.dayOfYear().get());
   }
}
