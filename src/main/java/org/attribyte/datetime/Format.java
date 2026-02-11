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

/**
 * An enumeration of all available formats.
 * @deprecated This enum is part of the Joda-Time based datetime package.
 * Use {@link java.time.format.DateTimeFormatter} and {@link java.time.format.FormatStyle}
 * from the {@code java.time} API instead.
 */
@Deprecated
public enum Format {

   SHORT_TIME("st"),
   MED_TIME("mt"),
   LONG_TIME("lt"),
   FULL_TIME("ft"),
   SHORT_DATE("sd"),
   MED_DATE("md"),
   LONG_DATE("ld"),
   FULL_DATE("fd"),
   SHORT_DATE_TIME("sdt"),
   MED_DATE_TIME("mdt"),
   LONG_DATE_TIME("ldt"),
   FULL_DATE_TIME("fdt"),
   ISO_DATE_TIME("idt"),
   DAY_OF_WEEK("dayOfWeek"),
   MONTH_DAY("monthDay"),
   YMD_CSV("ymdCSV"),
   YMD("ymd"),
   YEAR("year"),
   AGO("ago"),
   DAYS_AGO("daysAgo"),
   TIME_DAY_MONTH("timeDayMonth");

   Format(final String name) {
      this.name = name;
   }

   /**
    * The default name.
    */
   final String name;
}
