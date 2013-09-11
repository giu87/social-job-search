/*
 * @(#)DateUtils.java   1.0   21/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;

public class DateUtils {

   protected static Logger log = Facade.log;

   /**
    * Twitter date format. Example: {@code Sat Apr 21 12:14:28 +0000 2012}
    */
   public static final String TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

   /**
    * Returns a Java representation of a {@code date} string.
    * 
    * @param date
    *           Date in string format.
    * @return Java date representation of the given {@code date} string or {@code null} if
    *         {@code date} is {@code null} or invalid.
    */

   public static Date toDateFromFormat(String date) {
      Date parsedDate = toDateWithFormatString(date, TWITTER_DATE_FORMAT);

      return parsedDate;
   }

   private static Date toDateWithFormatString(String date, String format) {
      if (date == null)
         return null;

      try {
         return new SimpleDateFormat(format, Locale.ENGLISH).parse(date);
      } catch (ParseException e) {

         log.debug("Unable to parse date '" + date + "' using format string '" + format
               + "': " + e);

         return null;
      }
   }
}
