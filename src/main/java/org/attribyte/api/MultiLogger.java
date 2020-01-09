/*
 * Copyright 2020 Attribyte, LLC
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

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Logs to a sequence of loggers.
 */
public class MultiLogger implements Logger {

   public MultiLogger(final List<Logger> loggers) {
      this.loggers = loggers != null ? ImmutableList.copyOf(loggers) : ImmutableList.of();
   }

   @Override
   public void debug(String msg) {
      loggers.forEach(logger -> logger.debug(msg));
   }

   @Override
   public void info(String msg) {
      loggers.forEach(logger -> logger.info(msg));
   }

   @Override
   public void notice(String msg) {
      loggers.forEach(logger -> logger.notice(msg));
   }

   @Override
   public void warn(String msg) {
      loggers.forEach(logger -> logger.warn(msg));
   }

   @Override
   public void warn(String msg, Throwable t) {
      loggers.forEach(logger -> logger.warn(msg, t));
   }

   @Override
   public void alert(String msg) {
      loggers.forEach(logger -> logger.alert(msg));
   }

   @Override
   public void alert(String msg, Throwable t) {
      loggers.forEach(logger -> logger.alert(msg, t));
   }

   @Override
   public void error(String msg) {
      loggers.forEach(logger -> logger.error(msg));
   }

   @Override
   public void error(String msg, Throwable t) {
      loggers.forEach(logger -> logger.error(msg, t));
   }

   @Override
   public void critical(String msg) {
      loggers.forEach(logger -> logger.critical(msg));
   }

   @Override
   public void critical(String msg, Throwable t) {
      loggers.forEach(logger -> logger.critical(msg, t));
   }

   @Override
   public void emergency(String msg) {
      loggers.forEach(logger -> logger.emergency(msg));
   }

   @Override
   public void emergency(String msg, Throwable t) {
      loggers.forEach(logger -> logger.emergency(msg, t));
   }

   @Override
   public void flush() {
      loggers.forEach(Logger::flush);
   }

   @Override
   public void shutdown() {
      loggers.forEach(Logger::shutdown);
   }

   /**
    * The loggers.
    */
   private final ImmutableList<Logger> loggers;
}
