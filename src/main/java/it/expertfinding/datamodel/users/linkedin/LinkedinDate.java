/*
 * @(#)LinkedinDate.java   1.0   17/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinDate {

   @Linkedin
   private Long year;
   @Linkedin
   private Long month;
   @Linkedin
   private Long day;

   public Long getYear() {
      return this.year;
   }

   public void setYear(Long year) {
      this.year = year;
   }

   public Long getMonth() {
      return this.month;
   }

   public void setMonth(Long month) {
      this.month = month;
   }

   public Long getDay() {
      return this.day;
   }

   public void setDay(Long day) {
      this.day = day;
   }
}
